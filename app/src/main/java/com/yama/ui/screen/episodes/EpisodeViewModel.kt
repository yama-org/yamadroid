package com.yama.ui.screen.episodes

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yama.domain.classes.Anime
import com.yama.domain.classes.Episode
import com.yama.ui.screen.viewmodel.MainViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class EpisodeViewModel @Inject constructor() : ViewModel() {

    private val _isClicked = MutableStateFlow(false)
    val isClicked = _isClicked.asStateFlow()

    private val _episodes = MutableStateFlow(listOf<Episode>())
    val episodes = _episodes

    private val _isBottomBarActive = MutableStateFlow(false)
    val isBottomBarActive = _isBottomBarActive.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

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

    fun emptySearch() {
        _searchText.value = ""
    }

    fun getEpisodes(anime: Anime) {
        _episodes.value = anime.episodes
    }

    fun isClicked() {
        _isClicked.value = !_isClicked.value
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun activeBottomBar() {
        _isBottomBarActive.value = true
    }

    fun desactiveBottomBar() {
        _isBottomBarActive.value = false
    }

    fun getEpisodesSelected(): List<Episode> {
        return _episodes.value.filter { it.isSelected }
    }

    fun bottombarCheck() {

        if (getEpisodesSelected().isEmpty()) {
            desactiveBottomBar()
        }
    }

    fun emptyEpisodesSelected() {

        getEpisodesSelected().forEach {
            it.isSelected = false
        }
    }

    fun checkWatchEpisode() {

        getEpisodesSelected().forEach {
            it.watched = !it.watched
        }
        emptyEpisodesSelected()
        bottombarCheck()
    }

    fun episodeSelected(index: Int) {
        _episodes.value[index].isSelected = !_episodes.value[index].isSelected
    }
}
