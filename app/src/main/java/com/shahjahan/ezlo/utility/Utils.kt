package com.shahjahan.ezlo.utility


import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shahjahan.ezlo.R


//Loading image using Glide

fun ImageView.bindImage(platform: String?) {
    platform.let {
        Glide.with(this)
            .load(getImage(it))
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

fun ImageView.bindImageCircle(image: Int) {
    Glide.with(context).load(image).apply(RequestOptions.circleCropTransform()).into(this)
}


fun getImage(platform: String?): Int {
    var image = R.drawable.vera_edge_big;
    when (platform) {
        "Sercomm G450" -> image = R.drawable.vera_plus_big
        "Sercomm G550" -> image = R.drawable.vera_secure_big
        "MiCasaVerde VeraLite" -> image = R.drawable.vera_edge_big
        "Sercomm NA900" -> image = R.drawable.vera_edge_big
        "Sercomm NA301" -> image = R.drawable.vera_edge_big
        "Sercomm NA930" -> image = R.drawable.vera_edge_big
    }
    return image
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}










