package com.shahjahan.ezlo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Device(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var title: String?,
    var PK_Device: Int,
    val MacAddress: String,
    val PK_DeviceType: Int,
    val PK_DeviceSubType: Int,
    val Firmware: String,
    val Server_Device: String,
    val Server_Event: String,
    val Server_Account: String,
    val InternalIP: String,
    val LastAliveReported: String,
    val Platform: String,
){
    fun getDeviceTitle(): String{
        if(title == null){
            return "Home Number $id"
        }
        return title as String;
    }

    fun setTitle(){
        title = "Home Number $id"
    }
}