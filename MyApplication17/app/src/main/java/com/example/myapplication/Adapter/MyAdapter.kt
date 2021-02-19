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

class MyAdapter(var mCtx:Context,var resources:Int, var items:ArrayList<Model>):ArrayAdapter<Model>(mCtx,resources,items)
{
    override fun getView(position:Int,coverterView: View?,parent:ViewGroup):View
    {
        val layoutInflater:LayoutInflater= LayoutInflater.from(mCtx)
        val view:View=layoutInflater.inflate(resources,null)

        val img:ImageView=view.findViewById<ImageView>(R.id.image)
        val nameQ:TextView = view.findViewById<TextView>(R.id.textView1)
        val valQ:TextView = view.findViewById<TextView>(R.id.textView2)
        val date:TextView = view.findViewById<TextView>(R.id.textView3)
        val valueosnova:TextView = view.findViewById<TextView>(R.id.textView4)

        nameQ.text= items[position].NameQ
        valQ.text= items[position].ValueQ
        date.text= items[position].Date
        valueosnova.text= items[position].ValueOsnova
        img.setImageDrawable(mCtx.resources.getDrawable(items[position].Img))
        return view
    }
}