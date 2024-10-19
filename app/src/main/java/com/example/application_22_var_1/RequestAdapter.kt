package com.example.application_22_var_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley

class RequestAdapter(private val context: Context, private val requestList: List<RequestItem>) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.date)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_request, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request_i = requestList[position]
        holder.dateTextView.text = request_i.date
        holder.descriptionTextView.text = request_i.description

        val image = request_i.imageResource
        val imageRequest = ImageRequest(
            image,
            { bitmap -> holder.imageView.setImageBitmap(bitmap) },
            0,
            0,
            ImageView.ScaleType.CENTER_CROP,
            null,
            { error -> }
        )
        requestQueue.add(imageRequest)

    }

    override fun getItemCount(): Int {
        return requestList.size
    }
}