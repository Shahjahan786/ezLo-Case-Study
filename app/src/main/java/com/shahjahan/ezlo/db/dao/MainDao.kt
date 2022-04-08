package com.shahjahan.ezlo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shahjahan.ezlo.models.Device

@Dao
interface MainDao {

    @Query("select * from device order by PK_Device")
    fun getAllDevices(): LiveData<List<Device>>

    @Query("select * from device where id=:id")
    fun getDeviceById(id: Int):LiveData<Device>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDevices(products: List<Device>)


    @Query("select * from device")
    fun getCartForAccount(): List<Device>



    @Query("select * from device")
    fun getDevices(): MutableList<Device>
    @Query("select * from device")
    fun getDevicesForRecView(): LiveData<List<Device>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevice( device:Device)
    @Query("delete from device where id=:id")
    fun remove(id: Int)
    @Query("delete from device")
    fun deleteAll()

    @Update
    fun update(device: Device)



}