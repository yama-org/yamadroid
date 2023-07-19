package com.yama.ui.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yama.domain.Media
import com.yama.ui.screen.home.repo.RepositorioDeTesteo.mediaTest
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
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _media = MutableStateFlow(listOf<Media>())

    @OptIn(FlowPreview::class)
    val media = searchText
        .debounce(500L)
        .combine(_media) { text, media ->

            if (text.isBlank()) {
                media
            } else {
                media.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _media.value
        )

    private val _isClicked = MutableStateFlow(false)
    val isClicked = _isClicked.asStateFlow()

    init {
        _media.value = mediaTest
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getMedia(): List<Media> {

        return _media.value
    }

    fun isClicked() {
        _isClicked.value = !_isClicked.value
    }
}