package com.example.ekohort_android.presentation.ibu.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.ibu.IbuRepository
import com.example.ekohort_android.domain.ibu.model.Ibu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataIbuViewModel(val repository: IbuRepository): ViewModel() {
    private val _state: MutableStateFlow<Result<Boolean>> = MutableStateFlow(Result.Idle())
    val state: StateFlow<Result<Boolean>> = _state

    fun insert(ibu: Ibu) {
        viewModelScope.launch {
            _state.emit(Result.Idle(false))
            try {
                repository.insert(ibu)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.emit(Result.Error("Failed to insert ${ibu.name}", e))
            }
        }
    }

    fun update(id: String, newData: Ibu) {
        viewModelScope.launch {
            _state.emit(Result.Idle(false))
            try {
                repository.update(id, newData)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.emit(Result.Error("Failed to update ${newData.name} ($id)", e))
            }
        }
    }
}