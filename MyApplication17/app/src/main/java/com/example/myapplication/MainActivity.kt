package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapter.Model
import com.example.myapplication.Adapter.MyAdapter
import com.example.myapplication.BlrPars.Post
import com.example.myapplication.BlrPars.RetrofitInterface
import com.example.myapplication.RusPars.ValCurs
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


private fun Test() {
    val array = arrayOfNulls<Int>(100000)
    array.forEach { println(it) }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar!!.title = "Курс валют"
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        var curs: Double = 1.0
        var cur = this
        var mainArr: ArrayList<Model> = ArrayList()
        val rw: ListView = findViewById(R.id.list)

        // Что делается
        CoroutineScope(Dispatchers.IO).launch {
            var rf: Retrofit = Retrofit.Builder()
                    .baseUrl(RetrofitInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            var API = rf.create(RetrofitInterface::class.java)
            var call = API.posts
            call?.enqueue(object : Callback<List<Post?>?> {
                override fun onFailure(call: Call<List<Post?>?>, t: Throwable) {
                }

                override fun onResponse(
                        call: Call<List<Post?>?>,
                        response: Response<List<Post?>?>
                ) {
                    var postlist: List<Post>? = response.body() as List<Post>
                    val Items = ArrayList<Model>()
                    val adapter = MyAdapter(cur, R.layout.row, mainArr)
                    for (i in postlist!!.indices) {
                        var res: Double = (postlist[i].Cur_OfficialRate!! / qwe)

                        // Что делается
                        val formattedDouble: String = DecimalFormat("#0.000").format(res)
                        if (postlist!![i].Cur_Name == "Российских рублей") {
                            Items.add(Model(postlist!![i].Cur_Name.toString(), postlist!![i].Cur_Scale + " " + postlist!![i].Cur_Abbreviation + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦББ:" + currentDate, formattedDouble, R.drawable.rus))
                        } else if (postlist!![i].Cur_Name == "Доллар США") {
                            Items.add(Model(postlist!![i].Cur_Name.toString(), postlist!![i].Cur_Scale + " " + postlist!![i].Cur_Abbreviation + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦББ:" + currentDate, formattedDouble, R.drawable.usa))
                        } else if (postlist!![i].Cur_Name == "Евро") {
                            Items.add(Model(postlist!![i].Cur_Name.toString(), postlist!![i].Cur_Scale + " " + postlist!![i].Cur_Abbreviation + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦББ:" + currentDate, formattedDouble, R.drawable.es))
                        } else if (postlist!![i].Cur_Name == "Болгарский лев") {
                            Items.add(Model(postlist!![i].Cur_Name.toString(), postlist!![i].Cur_Scale + " " + postlist!![i].Cur_Abbreviation + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦББ:" + currentDate, formattedDouble, R.drawable.bg))
                        } else if (postlist!![i].Cur_Name == "Иен") {
                            Items.add(Model(postlist!![i].Cur_Name.toString(), postlist!![i].Cur_Scale + " " + postlist!![i].Cur_Abbreviation + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦББ:" + currentDate, formattedDouble, R.drawable.jp))
                        } else {
                            Items.add(Model(postlist!![i].Cur_Name.toString(), postlist!![i].Cur_Scale + " " + postlist!![i].Cur_Abbreviation + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦББ:" + currentDate, formattedDouble, R.drawable.flag_belarusi))
                        }
                    }
                    mainArr.addAll(Items)
                    runOnUiThread {
                        rw.adapter = adapter
                    }
                    Log.e("bababoi", "First courutine done!");
                    Log.e("bababoi", qwe.toString());
                }
            }
            )
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var con = URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream()
                val o = XmlMapper().readValue(con, ValCurs::class.java)
                val Items = java.util.ArrayList<Model>()
                val adapter = MyAdapter(cur, R.layout.row, mainArr)

                // Что делается
                for (i in o!!.valute!!.indices) {
                    o!!.valute!![i]!!.Value = o!!.valute!![i]!!.Value.replace(",", ".");
                    val newValute: Double = o!!.valute!![i]!!.Value.toDouble() / qwe
                    val formattedDouble: String = DecimalFormat("#0.000").format(newValute)
                    if (o!!.valute!![i].Name == "Белорусский рубль") {
                        Items.add(Model(o!!.valute!![i].Name, o!!.valute!![i].Nominal + " " + o!!.valute!![i].CharCode + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦБР:" + currentDate, formattedDouble, R.drawable.flag_belarusi))
                    } else if (o!!.valute!![i].Name == "Болгарский лев") {
                        Items.add(Model(o!!.valute!![i].Name, o!!.valute!![i].Nominal + " " + o!!.valute!![i].CharCode + "=:" + formattedDouble + " " + abbreviatura, "Дата получения С ЦБР:" + currentDate, formattedDouble, R.drawable.bg))
                    } else if (o!!.valute!![i].Name == "Доллар США") {
                        Items.add(Model(o!!.valute!![i].Name, o!!.valute!![i].Nominal + " " + o!!.valute!![i].CharCode + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦБР:" + currentDate, formattedDouble, R.drawable.usa))
                    } else if (o!!.valute!![i].Name == "Евро") {
                        Items.add(Model(o!!.valute!![i].Name, o!!.valute!![i].Nominal + " " + o!!.valute!![i].CharCode + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦБР:" + currentDate, formattedDouble, R.drawable.es))
                    } else if (o!!.valute!![i].Name == "Японских иен") {
                        Items.add(Model(o!!.valute!![i].Name, o!!.valute!![i].Nominal + " " + o!!.valute!![i].CharCode + "=" + formattedDouble + " " + abbreviatura, "Дата получения С ЦБР:" + currentDate, formattedDouble, R.drawable.jp))
                    } else {
                        Items.add(Model(o!!.valute!![i].Name, o!!.valute!![i].Nominal + " " + o!!.valute!![i].CharCode + "=" + formattedDouble + " " + abbreviatura, "Дата получения: С ЦБР " + currentDate, formattedDouble, R.drawable.rus))
                    }
                }
                mainArr.addAll(Items)
                runOnUiThread {

                    rw.adapter = adapter
                }
                Log.e("bababoi", "Second courutine done!");
            } catch (e: Exception) {
                Log.e("bebe2", e.message.toString());
            }
        }
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}

