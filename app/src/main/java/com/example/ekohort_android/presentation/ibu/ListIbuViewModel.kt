package com.example.ekohort_android.presentation.ibu

import androidx.lifecycle.viewModelScope
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.ibu.IbuRepository
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.example.ekohort_android.presentation.base.BaseListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListIbuViewModel(private val repository: IbuRepository) : BaseListViewModel() {
    private val _data: MutableStateFlow<Result<List<Ibu>>> = MutableStateFlow(Result.Idle())
    val data: StateFlow<Result<List<Ibu>>> = _data

    private val _manipState: MutableStateFlow<Result<Unit>> = MutableStateFlow(Result.Idle())
    val manipState: StateFlow<Result<Unit>> = _manipState

    init {
        subscribe()

        viewModelScope.launch(Dispatchers.IO) {
            searchState.collectLatest {
                unsubscribe()
                subscribe()
            }
        }
    }

    override fun subscribe() {
        if (job != null) return
        job = viewModelScope.launch(Dispatchers.IO) {
            repository.getAllIbuAsFlow(searchState.value).cancellable().collect {
                _data.emit(Result.Success(it))
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