package com.example.ekohort_android.presentation.nakes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.nakes.NakesRepository
import com.example.ekohort_android.domain.nakes.model.Nakes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListNakesViewModel(private val repository: NakesRepository) : ViewModel() {
    private val _data: MutableStateFlow<Result<List<Nakes>>> = MutableStateFlow(Result.Idle())
    val data: StateFlow<Result<List<Nakes>>> = _data

    private val _manipState: MutableStateFlow<Result<Unit>> = MutableStateFlow(Result.Idle())
    val manipState: StateFlow<Result<Unit>> = _manipState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getAllNakesAsFlow().collect {
                    _data.emit(Result.Success(it))
                }
            }
        }
    }

    fun delete(data: Nakes) {
        viewModelScope.launch {
            _manipState.emit(Result.Idle())
            try {
                repository.delete(data.id)
                _manipState.emit(Result.Success())
            } catch (e: Exception) {
                e.printStackTrace()
                _manipState.emit(Result.Error("Failed to delete ${data.name}", e))
            }
        }
    }
}