package com.example.ekohort_android.presentation.ibu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.ibu.model.Ibu
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.ibu.IbuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListIbuViewModel(private val repository: IbuRepository) : ViewModel() {
    private val _data: MutableStateFlow<Result<List<Ibu>>> = MutableStateFlow(Result.Idle())
    val data: StateFlow<Result<List<Ibu>>> = _data

    private val _manipState: MutableStateFlow<Result<Unit>> = MutableStateFlow(Result.Idle())
    val manipState: StateFlow<Result<Unit>> = _manipState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getAllIbuAsFlow().collect {
                    _data.emit(Result.Success(it))
                }
            }
        }
    }

    fun delete(data: Ibu) {
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