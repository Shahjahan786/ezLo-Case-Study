package com.shahjahan.ezlo.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.shahjahan.ezlo.db.database.EzloDatabase
import com.shahjahan.ezlo.models.Device
import com.shahjahan.ezlo.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(private val database: EzloDatabase) {


    val devices: LiveData<List<Device>> = database.mainDao.getAllDevices()

    suspend fun refreshItems() {
        withContext(Dispatchers.IO) {
            val deviceData = Api.retrofitService.getAllDevicesAsync().await()
            val deviceList = deviceData.Devices;

            Log.d("DevicesList",deviceList.toString())

            with(database.mainDao) {
                deleteAll()
                insertAllDevices(setTitles(deviceData.Devices))

            }
        }
    }

    fun setTitles(deviceList: MutableList<Device>) : MutableList<Device>{
        for (i in 0 until deviceList.size){
            deviceList.get(i).title = "Home Number ${i+1}"
        }

        return deviceList;
    }

    fun getDeviceById(id: Int): LiveData<Device> {
        return database.mainDao.getDeviceById(id)
    }

    suspend fun remove(id: Int) {
        withContext(Dispatchers.IO) {
            database.mainDao.remove(id)
        }
    }

    suspend fun update(device: Device) {
        withContext(Dispatchers.IO) {
            database.mainDao.update(device)
        }
    }

}