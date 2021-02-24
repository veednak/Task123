package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapter.Model
import com.example.myapplication.Adapter.MyAdapter
import com.example.myapplication.BlrPars.CurrencyBelarus
import com.example.myapplication.BlrPars.RetrofitInterface

import com.example.myapplication.Const.Constant
import com.example.myapplication.R.drawable.bulgaria
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


    }

    override fun onStart() {
        super.onStart()
        Log.d("bababoi", "Start")
    }

    override fun onResume() {


        super.onResume()
        Log.e("bababoi", "resume")
        //достаем данные по курсу и названию выбранной валюты
        var newOfficialRate = ""
        var abbreviationBel = ""
        var abbreviationRus = ""
        val charCode = intent.getStringExtra(Constant.charCode)
        val newValue = intent.getStringExtra(Constant.newValue)
        newOfficialRate = if (newValue?.toString() == null) "1.0" else newValue.toString()
        abbreviationBel = if (charCode?.toString() == null) "BYN" else charCode.toString()
        abbreviationRus = if (charCode?.toString() == null) "RUB" else charCode.toString()

        // создаем сегодняшнее значение даты
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        // создаем список для помещения данных с сайтов бнб и рнб
        var cur = this
        var mainArr: ArrayList<Model> = ArrayList()
        val rw: ListView = findViewById(R.id.list)

        // достаем данные с бнб
        CoroutineScope(Dispatchers.IO).launch {
            var rf: Retrofit = Retrofit.Builder()
                    .baseUrl(RetrofitInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            var API = rf.create(RetrofitInterface::class.java)
            var call = API.posts
            call?.enqueue(object : Callback<List<CurrencyBelarus?>?> {
                override fun onFailure(call: Call<List<CurrencyBelarus?>?>, t: Throwable) {
                }

                override fun onResponse(
                        call: Call<List<CurrencyBelarus?>?>,
                        response: Response<List<CurrencyBelarus?>?>
                ) {
                    // добавляем данные в список
                    var postlist: List<CurrencyBelarus>? = response.body() as List<CurrencyBelarus>
                    val items = ArrayList<Model>()
                    val adapter = MyAdapter(cur, R.layout.row, mainArr)
                    for (i in postlist!!.indices) {
                        var res: Double = 1.0
                        res = postlist[i].Cur_OfficialRate!! / newOfficialRate.toDouble()
                        val formattedDouble: String = DecimalFormat("#0.000").format(res)
                        var draw = R.drawable.ic_launcher_background
                        when (postlist!![i].Cur_Abbreviation) {

                            "BGN" -> draw = bulgaria
                            "USD" ->  draw= R.drawable.usa
                            "AUD" ->  draw =  R.drawable.australia
                            "EUR" -> draw= R.drawable.ic_european_union
                            else -> {
                                draw= R.drawable.flag_belarusi
                            }
                        }
                        items.add(Model(postlist!![i].Cur_Name.toString(), postlist!![i].Cur_Scale + " " + postlist!![i].Cur_Abbreviation + "=" + formattedDouble + " " + abbreviationBel, "Дата получения С ЦББ:" + currentDate, formattedDouble, draw))

                    }
                    //добавляем наш список в общий список
                    mainArr.addAll(items)
                    //выводим на экран
                    runOnUiThread {
                        rw.adapter = adapter
                    }
                    Log.e("bababoi", "First courutine done!");
                }
            }
            )
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // достаем данные с рнб
                var con = URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream()
                val o = XmlMapper().readValue(con, ValCurs::class.java)
                // создаем список куда и будем записывать данные
                val items = ArrayList<Model>()
                val adapter = MyAdapter(cur, R.layout.row, mainArr)

                // Кладем данные в список
                for (i in o!!.valute!!.indices) {
                    o!!.valute!![i]!!.Value = o!!.valute!![i]!!.Value.replace(",", ".");
                    val newCurrency : Double = o!!.valute!![i]!!.Value.toDouble() / newOfficialRate.toDouble()
                    val formattedDouble: String = DecimalFormat("#0.000").format(newCurrency)
                    var draw = R.drawable.ic_launcher_background
                    when (o!!.valute!![i].CharCode) {
                        "AUD" ->  draw= R.drawable.australia
                        "AZN" -> draw= R.drawable.azerbaijan
                        "BGN" -> draw= bulgaria
                        "BYN" -> draw= R.drawable.flag_belarusi
                        "USD" -> draw=R.drawable.usa
                        "EUR" -> draw=  R.drawable.ic_european_union
                        else -> {
                            draw= R.drawable.ic_flag_of_russia__1_
                        }
                    }
                    items.add(Model(o!!.valute!![i].Name, o!!.valute!![i].Nominal + " " + o!!.valute!![i].CharCode + "=" + formattedDouble + " " + abbreviationRus, "Дата получения С ЦБР:" + currentDate, formattedDouble, draw))
                }
                // кладем полученный список в основной список маинарр
                mainArr.addAll(items)
                // выводим на экран
                runOnUiThread {

                    rw.adapter = adapter
                }
                Log.e("bababoi", "Second courutine done!");
            } catch (e: Exception) {
                Log.e("bebe2", e.message.toString());
            }
        }
        // кнопка для переключения на вторую активити
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d("bababoi", "Pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("bababoi", "Stop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("bababoi", "exit")
    }

    override fun onRestart() {
        super.onRestart()

        Log.e("bababoi", "restart")
    }
}