package com.example.yomietask

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SliderAdapter(private val context: Context, private val arrayList: ArrayList<SliderItem>) :
    RecyclerView.Adapter<SliderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sliderItem = arrayList[position]
        val uri = "android.resource://" + context.getPackageName() + "/${sliderItem.value}"
        when (AppContants.getMimeType(sliderItem.value).toString()) {
            AppContants.MIME_TYPE_VIDEO -> {
                holder.imageView.visibility = View.GONE
                holder.webView.visibility = View.GONE
                holder.videoView.visibility = View.VISIBLE

                holder.videoView.setVideoURI(Uri.parse(uri))
                holder.videoView.start()
            }
            AppContants.MIME_TYPE_IMG -> {
                holder.videoView.visibility = View.GONE
                holder.webView.visibility = View.GONE
                holder.imageView.visibility = View.VISIBLE

                Glide.with(context).load(Uri.parse(uri)).centerCrop().into(holder.imageView)
            }
            else -> {
                holder.videoView.visibility = View.GONE
                holder.imageView.visibility = View.GONE
                holder.webView.visibility = View.VISIBLE

                val url = context.getString(sliderItem.value)
                holder.webView.loadUrl(url)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoView: VideoView = itemView.findViewById(R.id.vdoVw)
        val imageView: ImageView = itemView.findViewById(R.id.imgVw)
        val webView: WebView = itemView.findViewById(R.id.webVw)
    }
}