package com.android.paging3demo.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.paging3demo.data.repository.Repository
import com.android.paging3demo.model.UnsplashImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class SearchViewModel @Inject constructor(
    private val repository: Repository,
): ViewModel() {

    var query by mutableStateOf("")
    var searchedImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())

    fun searchImages() {
        viewModelScope.launch {
            repository.searchImages(query).cachedIn(viewModelScope).collect {
                searchedImages.value = it
            }
        }
    }
}