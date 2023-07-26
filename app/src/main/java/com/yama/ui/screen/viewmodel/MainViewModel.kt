package com.yama.ui.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yama.domain.classes.Anime
import com.yama.domain.classes.Episode
import com.yama.ui.screen.episodes.EpisodeViewModel
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

    var screenUbication by mutableStateOf("Titles")

    private val _selectedAnime = MutableStateFlow(Anime(0, "", "", emptyList(), emptyList(), ""))
    val selectedAnime = _selectedAnime.asStateFlow()

    private val _isClicked = MutableStateFlow(false)
    val isClicked = _isClicked.asStateFlow()


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


    init {
        _anime.value = animeTests
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun isClicked() {
        _isClicked.value = !_isClicked.value
    }

    fun emptySearch() {
        _searchText.value = ""
    }

    fun animeSelected(anime: Anime) {
        _selectedAnime.value = anime
    }

    fun getAnime(): Anime = _selectedAnime.value
}