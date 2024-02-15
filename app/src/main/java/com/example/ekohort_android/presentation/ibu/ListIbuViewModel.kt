package com.example.ekohort_android.presentation.ibu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.ibu.model.Ibu
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.ibu.IbuRepository
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListIbuViewModel(private val repository: IbuRepository) : ViewModel() {
    private val _data: MutableStateFlow<Result<List<Ibu>>> = MutableStateFlow(Result.Idle())
    val data: StateFlow<Result<List<Ibu>>> = _data

    private val _manipState: MutableStateFlow<Result<Unit>> = MutableStateFlow(Result.Idle())
    val manipState: StateFlow<Result<Unit>> = _manipState

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            if (_data.value !is Result.Idle) _data.emit(Result.Idle(false))
            try {
                val newData = repository.getAllIbu()
                _data.emit(Result.Success(newData))
            } catch (e: Exception) {
                e.printStackTrace()
                when (e) {
                    is FirebaseFirestoreException -> {
                        _data.emit(Result.Error("ERR: ${e.code} -", e))
                    }
                    else -> {
                        _data.emit(Result.Error("Something went wrong!", e))
                    }
                }
            }
        }
    }

    fun delete(ibu: Ibu) {
        viewModelScope.launch {
            _manipState.emit(Result.Idle())
            try {
                repository.delete(ibu.id)
                _manipState.emit(Result.Success())
            } catch (e: Exception) {
                e.printStackTrace()
                _manipState.emit(Result.Error("Failed to delete ${ibu.name}", e))
            }
        }
    }
}