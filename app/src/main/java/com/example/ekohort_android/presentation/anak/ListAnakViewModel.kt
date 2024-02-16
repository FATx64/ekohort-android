package com.example.ekohort_android.presentation.anak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.anak.AnakRepository
import com.example.ekohort_android.domain.anak.model.Anak
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListAnakViewModel(private val repository: AnakRepository) : ViewModel() {
    private val _data: MutableStateFlow<Result<List<Anak>>> = MutableStateFlow(Result.Idle())
    val data: StateFlow<Result<List<Anak>>> = _data

    private val _manipState: MutableStateFlow<Result<Unit>> = MutableStateFlow(Result.Idle())
    val manipState: StateFlow<Result<Unit>> = _manipState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getAllAnakAsFlow().collect {
                    _data.emit(Result.Success(it))
                }
            }
        }
    }

    fun delete(data: Anak) {
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