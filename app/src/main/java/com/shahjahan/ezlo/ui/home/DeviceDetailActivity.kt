package com.shahjahan.ezlo.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextUtils
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.shahjahan.ezlo.R
import com.shahjahan.ezlo.models.Device
import com.shahjahan.ezlo.utility.bindImage
import com.shahjahan.ezlo.utility.showToast
import com.shahjahan.ezlo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeviceDetailActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels();
    lateinit var tvTitle: EditText
    lateinit var tvSubTitle: TextView
    lateinit var ivItemImage: ImageView
    lateinit var tvMacAddress: TextView
    lateinit var tvFirmware: TextView
    lateinit var tvModel: TextView
    lateinit var btnSave: Button
    var editable: Boolean = false
    var device: Device? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_details)


        setTitle("Device Details")
        tvTitle = findViewById(R.id.tvTitle)
        tvSubTitle = findViewById(R.id.tvSubTitle)
        ivItemImage = findViewById(R.id.ivItemImage)
        tvMacAddress = findViewById(R.id.tvMacAddress)
        btnSave = findViewById(R.id.btnSave)

        tvFirmware = findViewById(R.id.tvFirmware)
        tvModel = findViewById(R.id.tvModel)

        val deviceId = intent.getIntExtra("deviceId", -1);
        editable = intent.getBooleanExtra("editable", false);

        lifecycleScope.launch {
            viewModel.getDeviceById(deviceId).observe(this@DeviceDetailActivity, Observer {
                it?.let {
                    bind(it)
                }
            })
        }


    }


    private fun bind(item: Device) {

        tvTitle.setText(item.title)
        tvSubTitle.text = "SN: ${item.PK_Device}"
        tvMacAddress.text = "MAC Address: ${item.MacAddress}"
        tvFirmware.text = "Firmware: ${item.Firmware}"
        tvModel.text = "Model: ${item.Platform}"
        ivItemImage.bindImage(item.Platform)

        if (!editable) {
            tvTitle.isCursorVisible = false
            tvTitle.isFocusableInTouchMode = false
            tvTitle.background = null
            tvTitle.isFocusable = false
            btnSave.visibility = INVISIBLE
        } else {
            tvTitle.isFocusable = true
            tvTitle.isFocusableInTouchMode = true
            tvTitle.requestFocus()
            btnSave.visibility = VISIBLE
        }

        btnSave.setOnClickListener {
            val title = tvTitle.text.toString()
            if (TextUtils.isEmpty(title)) {
                showToast("Title can not be empty")
                return@setOnClickListener
            }
            item.title = title

            viewModel.update(item)
            onBackPressed()
        }

    }


}
