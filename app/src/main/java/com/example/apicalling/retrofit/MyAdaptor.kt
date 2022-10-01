package com.example.apicalling.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apicalling.controller.MainActivity
import com.example.apicalling.R

class MyAdaptor(val mainActivity: MainActivity, val list: List<ProductApiModelItem>) :
    RecyclerView.Adapter<MyAdaptor.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title_txt = itemView.findViewById<TextView>(R.id.title_txt)
        var category_txt = itemView.findViewById<TextView>(R.id.category_txt)
        var price_txt   = itemView.findViewById<TextView>(R.id.price_txt)
        var description_txt   = itemView.findViewById<TextView>(R.id.description_txt)
        var img_try = itemView.findViewById<ImageView>(R.id.img_try)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view =
            LayoutInflater.from(mainActivity).inflate(R.layout.item_design, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.title_txt.text = list[position].title
        holder.category_txt.text = list[position].category
        holder.price_txt.text = list[position].price.toString()
        holder.description_txt.text = list[position].description

        Glide.with(mainActivity).load(list[position].image).placeholder(R.drawable.ic_launcher_background).into(holder.img_try)

//        holder.img_try.setImageResource(list[position].image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}