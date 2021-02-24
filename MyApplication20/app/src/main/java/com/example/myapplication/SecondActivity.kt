package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapter.ModelBaseValute
import com.example.myapplication.Adapter.MyAdapterBaseValute
import com.example.myapplication.BlrPars.CurrencyBelarus
import com.example.myapplication.BlrPars.RetrofitInterface
import com.example.myapplication.Const.Constant
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val THIEF = "content"

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


    }

    override fun onStart() {
        super.onStart()
        Log.d("bababoi", "Start2")
    }

    override fun onResume() {
        super.onResume()
        var newOfficialRate: String = ""
        var newOfficialRateRus: String = ""
        var abbreviatura: String = ""
        var cur = this
        var mainArr: ArrayList<ModelBaseValute> = ArrayList()

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
                    var postlist: List<CurrencyBelarus>? = response.body() as List<CurrencyBelarus>
                    val items = ArrayList<ModelBaseValute>()
                    val adapter = MyAdapterBaseValute(cur, R.layout.row, mainArr)
                    var draw = R.drawable.ic_launcher_background
                    for (i in postlist!!.indices) {
                        when (postlist!![i].Cur_Abbreviation) {

                            "BGN" -> draw = R.drawable.bulgaria
                            "USD" ->  draw= R.drawable.usa
                            "AUD" ->  draw =  R.drawable.australia
                            "EUR" -> draw= R.drawable.ic_european_union
                            else -> {
                                draw= R.drawable.flag_belarusi
                            }
                        }
                        items.add(ModelBaseValute(postlist!![i].Cur_Name.toString(), draw))
                    }
                    list2.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, l ->
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        newOfficialRate = postlist[position].Cur_OfficialRate!!.toString()
                        newOfficialRateRus = postlist[position].Cur_OfficialRate!!.toString()
                        abbreviatura = postlist[position].Cur_Abbreviation!!
                        val intent = Intent(cur, MainActivity::class.java)
                        intent.putExtra(Constant.charCode, abbreviatura)
                        intent.putExtra(Constant.newValue, newOfficialRate)
                        intent.putExtra(Constant.newValueRus, newOfficialRateRus)
                        finish()
                        startActivity(intent)

                    })
                    mainArr.addAll(items)
                    runOnUiThread {

                        list2.adapter = adapter
                    }
                }
            }
            )
        }
        val actionBar = supportActionBar
        actionBar!!.title = "Базовая валюта"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        Log.e("bababoi", "stop2")
    }

    override fun onPause() {
        super.onPause()
        Log.e("bababoi", "pause2")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.e("bababoi", "destroyyy")
    }
}
