package com.example.ekohort_android.presentation.nakes.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.nakes.NakesRepository
import com.example.ekohort_android.domain.nakes.model.Nakes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DetailNakesViewModel(val repository: NakesRepository, val id: String) : ViewModel() {
    private val _state: MutableStateFlow<Result<Nakes>> = MutableStateFlow(Result.Idle())
    val state: StateFlow<Result<Nakes>> = _state

    init {
        viewModelScope.launch {
            repository.getNakesByIdAsFlow(id).collect {
                _state.value = if (it != null) Result.Success(it) else Result.Error("Nakes with id \"$id\" not found")
            }
        }
    }

    fun delete(data: Nakes) {
        viewModelScope.launch {
            try {
                repository.delete(data.id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}