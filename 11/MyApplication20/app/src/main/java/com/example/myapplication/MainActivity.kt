
package com.example.myapplication


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.Model
import com.example.myapplication.Adapter.ModelBaseValute
import com.example.myapplication.Adapter.MyAdapter
import com.example.myapplication.BlrPars.CurrencyBelarus
import com.example.myapplication.BlrPars.RetrofitInterface
import com.example.myapplication.Const.Constant
import com.example.myapplication.Const.Constant.cod
import com.example.myapplication.Const.Constant.course
import com.example.myapplication.Const.Constant.draw
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
import java.text.SimpleDateFormat
import java.util.*


private fun Test() {
    val array = arrayOfNulls<Int>(100000)
    array.forEach { println(it) }
}

class MainActivity : Abstract() {

    var sPref: SharedPreferences? = null

    var counter:Int=0
    var q:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar!!.title = "Курс валют"
        val cur = this
        kek(this, progressBar)
        Log.d("bababoi", "создал")
        var arr: ModelBaseValute

        buttonC.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, Constant.CHOOSE_THIEF)
        }

        buttonPanel.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, Constant.CHOOSE_THIEF)
        }
        loadText()
        data()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("bababoi", "onResult")

        if (requestCode == Constant.CHOOSE_THIEF && resultCode == RESULT_OK) {
            Constant.cod = data!!.getStringExtra(Constant.charCode)!!
            Log.e("zxc", cod)
            Constant.course = data.getDoubleExtra(Constant.newValue, 1.0)!!
            Log.e("mom", Constant.course.toString())
        } else {
            Log.e("bababoi", "ФГУ")
        }
        data()
    }

    private fun saveText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sPref!!.edit()
        ed.putString("asd", EnumFlag.valueOf(cod).name)
        ed.putFloat("qwe", course.toFloat())
        ed.commit()
       // ed.putInt("qwe", EnumFlag.valueOf(cod).draw)
       // ed.commit()
    }


    private fun loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val savedText: String = sPref!!.getString("asd", "BYN")!!
        val s:Float=sPref!!.getFloat("qwe", 1f)
        cod =savedText
        course=s.toDouble()
    }



    override fun onStart() {
        super.onStart()
        Log.d("bababoi", "Start")

    }


   private fun data() {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = "Дата получения С ЦББ:" + sdf.format(Date())
        // создаем список для помещения данных с сайтов бнб и рнб
        var cur = this
        var mainArr: ArrayList<Model> = ArrayList()
        var check1= ""
        var check2=""
       val adapter = MyAdapter(mainArr)
        val rw: RecyclerView = findViewById(R.id.list)
       progressBar.visibility = ProgressBar.VISIBLE
        // достаем данные с бнб
           CoroutineScope(Dispatchers.IO).launch {
               var rf: Retrofit = Retrofit.Builder()
                       .baseUrl(RetrofitInterface.BASE_URL)
                       .addConverterFactory(GsonConverterFactory.create()).build()
               var API = rf.create(RetrofitInterface::class.java)
               var call = API.posts
               call?.enqueue(object : Callback<List<CurrencyBelarus?>?> {
                   override fun onFailure(call: Call<List<CurrencyBelarus?>?>, t: Throwable) {
                       Toast.makeText(cur, "Ошибка загрузки данных", Toast.LENGTH_LONG).show()
                       progressBar.visibility = ProgressBar.INVISIBLE

                   }

                   override fun onResponse(
                       call: Call<List<CurrencyBelarus?>?>,
                       response: Response<List<CurrencyBelarus?>?>

                   ) {
                       // добавляем данные в список
                       val postlist: List<CurrencyBelarus>? =
                           response.body() as List<CurrencyBelarus>
                       val items = ArrayList<Model>()

                       for (i in postlist!!.indices) {

                           var res: Double = 1.0
                           res = postlist[i].Cur_OfficialRate!! / course
                           var draw = postlist[i].Cur_Abbreviation.draw
                           items.add(
                               Model(
                                   postlist[i].Cur_Name,
                                   postlist[i].Cur_Scale,
                                   postlist[i].Cur_Abbreviation.name,
                                   res,
                                   cod,
                                   currentDate,
                                   draw
                               )
                           )
                       }

                       //добавляем наш список в общий список
                       mainArr.addAll(items)
                       //выводим на экран
                       runOnUiThread {

                           rw.layoutManager = LinearLayoutManager(cur)
                           rw.setHasFixedSize(true)
                           rw.adapter = adapter
                           progressBar.visibility = ProgressBar.INVISIBLE
                       }


                       Log.e("bababoi", "First courutine done!");
                   }
               }
               )
           }
           CoroutineScope(Dispatchers.IO).launch {
               // достаем данные с рнб
               val con = URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream()
               val o = XmlMapper().readValue(con, ValCurs::class.java)
               // создаем список куда и будем записывать данные
               val items = ArrayList<Model>()
               check2="1"

               // Кладем данные в список
               for (i in o.valute!!.indices) {
                   o.valute!![i].Value = o.valute!![i].Value.replace(",", ".");
                   var res: Double = 1.0
                   res = o.valute!![i].Value.toDouble() / course
//                    val formattedDouble: String = DecimalFormat("#0.000").format(newCurrency)
                   val draw = o.valute!![i].CharCode.draw
                   items.add(
                       Model(
                           o.valute!![i].Name,
                           o.valute!![i].Nominal,
                           o.valute!![i].CharCode.name,
                           res,
                           cod,
                           currentDate,
                           draw
                       )
                   )
               }
               // кладем полученный список в основной список маинарр
               mainArr.addAll(items)
               // выводим на экран
               Log.e("bababoi", "Second courutine done!");
           }
       }


    override fun onResume() {
        super.onResume()
        Log.e("bababoi", "resume")

        //достаем данные по курсу и названию выбранной валюты
        buttonPanel.setImageResource(EnumFlag.valueOf(cod).draw)
        Log.e("mom", Constant.course.toString())

        // создаем сегодняшнее значение даты
        refresh.setOnRefreshListener {
            data()
            refresh.isRefreshing = false
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("bababoi", "Pause")
    }

    override fun onStop() {
        super.onStop()
        saveText()
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