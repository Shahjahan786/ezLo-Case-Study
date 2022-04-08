package com.shahjahan.ezlo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopkart.ui.home.ItemAdapter
import com.example.shopkart.ui.home.ItemAdapter.Actionlistener
import com.shahjahan.ezlo.R
import com.shahjahan.ezlo.models.Device
import com.shahjahan.ezlo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), Actionlistener{

    private val viewModel: HomeViewModel by viewModels()

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        recyclerView = findViewById(R.id.recyclerView4);
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar =  findViewById(R.id.progressBar);

        viewModel.devices.observe(this, Observer {
            recyclerView.adapter = ItemAdapter(it, this);
        })


        viewModel.status.observe(this, Observer {
            when (it) {
                HomeViewModel.ApiStatus.LOADING -> progressBar.visibility = View.VISIBLE
                HomeViewModel.ApiStatus.ERROR -> progressBar.visibility = View.GONE
                HomeViewModel.ApiStatus.DONE -> progressBar.visibility = View.GONE
            }
        })

        viewModel.refreshDataFromRepository();

    }

    override fun onDeleteItem(item: Device) {
        val builder = AlertDialog.Builder(this@HomeActivity)
        builder.setMessage("The device will be removed, do you want to continue?")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, id ->
                viewModel.remove(item.id!!)
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    override fun onShowDeviceDetail(item: Device) {
        showDetailsScreen(item.id!!, false);
    }

    override fun onUpdateItem(item: Device) {
        showDetailsScreen(item.id!!, true);
    }

    fun showDetailsScreen(deviceId: Int, editable: Boolean) {
        val intent = Intent(this, DeviceDetailActivity::class.java).apply {
            putExtra("deviceId", deviceId)
            putExtra("editable", editable)
        }
        startActivity(intent)
    }
}
