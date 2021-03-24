package com.example.myapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.row.view.*
import java.util.*

class MyAdapter(private val exampleList: List<Model>) :
    RecyclerView.Adapter<MyAdapter.ExampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row,
            parent, false
        )
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.imageView.setImageResource(currentItem.Img)
        holder.textView1.text = currentItem.NameQ
        holder.textView2.text =
            currentItem.A1.toString() + " " + currentItem.Cod1 + " = " + currentItem.ValueQ + " " + currentItem.Cod2
        holder.textView3.text = currentItem.Date
    }

    override fun getItemCount() = exampleList.size
    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.countryImage
        val textView1: TextView = itemView.countryName
        val textView2: TextView = itemView.countryValue
        val textView3: TextView = itemView.countryDate
    }
}