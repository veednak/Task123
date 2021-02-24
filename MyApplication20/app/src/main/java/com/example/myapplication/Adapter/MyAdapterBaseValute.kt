package com.example.myapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import java.util.*

class MyAdapterBaseValute(var mCtx: Context, var resources: Int, var items: ArrayList<ModelBaseValute>) : ArrayAdapter<ModelBaseValute>(mCtx, resources, items) {
    override fun getView(position: Int, coverterView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resources, null)

        val img: ImageView = view.findViewById<ImageView>(R.id.image)
        val nameB: TextView = view.findViewById<TextView>(R.id.textView1)


        nameB.text = items[position].NameB
        img.setImageDrawable(mCtx.resources.getDrawable(items[position].ImgB))
        return view
    }
}