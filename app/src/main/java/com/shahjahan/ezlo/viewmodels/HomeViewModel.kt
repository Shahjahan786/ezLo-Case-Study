package com.shahjahan.ezlo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahjahan.ezlo.models.Device
import com.shahjahan.ezlo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    //For loading screen => progress bar
    enum class ApiStatus { LOADING, DONE, ERROR }

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    //repository
    val devices = repository.devices



    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                repository.refreshItems()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                e.printStackTrace()
            }
        }
    }


    fun remove(id: Int) {
        viewModelScope.launch {
            repository.remove(id)
        }
    }

    fun update(device: Device) {
        viewModelScope.launch {
            repository.update(device)
        }
    }

    suspend fun getDeviceById(id: Int): LiveData<Device> {

        return repository.getDeviceById(id)

    }
}