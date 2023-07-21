package com.yama.ui.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yama.domain.classes.Anime
import com.yama.domain.classes.Episode
import com.yama.ui.screen.home.repo.RepositorioDeTesteo.animeTests
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _anime = MutableStateFlow(listOf<Anime>())

    private val _episodes = MutableStateFlow(listOf<Episode>())

    var screenUbication by mutableStateOf("Titles")

    private val _selectedAnime = MutableStateFlow(Anime(0, " ", " ", emptyList(), emptyList(), " "))
    val selectedAnime = _selectedAnime.asStateFlow()


    @OptIn(FlowPreview::class)
    val anime = searchText
        .debounce(500L)
        .combine(_anime) { text, anime ->

            if (text.isBlank()) {
                anime
            } else {
                anime.filter {
                    _isSearching.value = true
                    it.doesMatchSearchQuery(text)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _anime.value
        )

    @OptIn(FlowPreview::class)
    val episode = searchText
        .debounce(500L)
        .combine(_episodes) { text, episode ->

            if (text.isBlank()) {
                episode
            } else {
                episode.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _episodes.value
        )

    private val _isClicked = MutableStateFlow(false)
    val isClicked = _isClicked.asStateFlow()

    private val _isPressed = MutableStateFlow(false)
    val isPressed = _isPressed.asStateFlow()

    init {
        _anime.value = animeTests
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun isClicked() {
        _isClicked.value = !_isClicked.value
    }

    fun isPressed() {
        _isPressed.value = !_isPressed.value
    }

    fun isSearching() {
        _isSearching.value = !_isSearching.value
    }

    fun isNotPressed() {
        _isPressed.value = false
    }

    fun animeSelected(anime: Anime) {
        _selectedAnime.value = anime
        _episodes.value = _selectedAnime.value.episodes
    }
}