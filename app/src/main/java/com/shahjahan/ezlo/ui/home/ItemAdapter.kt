package com.example.shopkart.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shahjahan.ezlo.R
import com.shahjahan.ezlo.models.Device
import com.shahjahan.ezlo.utility.bindImage



class ItemAdapter(private val dataSet: List<Device>, val actionlistener: Actionlistener) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    class ItemViewHolder(view: View, actionlistener: Actionlistener) :
        RecyclerView.ViewHolder(view) {
        val itemTitle: TextView
        val itemSubTitle: TextView
        val itemImage: ImageView
        val itemDetail: ImageView
        var actionlistener: Actionlistener
        val contentView: View
        val ivItemEdit: View

        init {
            // Define click listener for the ViewHolder's View.
            contentView = view;
            itemTitle = view.findViewById(R.id.tvTitle)
            itemSubTitle = view.findViewById(R.id.tvSubTitle)
            itemImage = view.findViewById(R.id.ivItemImage)
            itemDetail = view.findViewById(R.id.ivItemDetail)
            ivItemEdit = view.findViewById(R.id.ivItemEdit)
            this.actionlistener = actionlistener
        }

        fun bind(item: Device) {
            itemTitle.text = item.title
            itemSubTitle.text = "SN: ${item.PK_Device}"
            //itemImage.bindImage(item.as)

            contentView.setOnLongClickListener{
                actionlistener.onDeleteItem(item)
                true
            }
            itemDetail.setOnClickListener {
                actionlistener.onShowDeviceDetail(item)
            }

            contentView.setOnClickListener {
                actionlistener.onShowDeviceDetail(item)
            }

            ivItemEdit.setOnClickListener {
                actionlistener.onUpdateItem(item)
            }

            itemImage.bindImage(item.Platform)


        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_device, parent, false)

        return ItemViewHolder(view, actionlistener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataSet.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    interface Actionlistener {
        fun onDeleteItem(item: Device)
        fun onShowDeviceDetail(item: Device)
        fun onUpdateItem(item: Device)
    }
}