package com.example.ekohort_android.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.carousel.CarouselRepository
import com.example.ekohort_android.domain.carousel.model.Carousel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CarouselRepository) : ViewModel() {
    private val _carouselState: MutableStateFlow<Carousel> = MutableStateFlow(Carousel())
    val carouselState: StateFlow<Carousel> = _carouselState

    fun fetchCounts() {
        viewModelScope.launch(Dispatchers.IO) {
            _carouselState.value = Carousel(
                repository.getIbuCount(),
                repository.getAnakCount(),
                repository.getNakesCount()
            )
        }
    }
}