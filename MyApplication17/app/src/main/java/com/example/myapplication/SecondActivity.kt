package com.example.myapplication

import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapter.ModelBaseValute
import com.example.myapplication.Adapter.MyAdapterBaseValute
import com.example.myapplication.BlrPars.Post
import com.example.myapplication.BlrPars.RetrofitInterface
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



var qwe:Double= 1.0
var abbreviatura:String=""

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var cur = this
        var mainArr: ArrayList<ModelBaseValute> = ArrayList()
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
                    val Items = ArrayList<ModelBaseValute>()
                    val adapter = MyAdapterBaseValute(cur, R.layout.row, mainArr)
                    for (i in postlist!!.indices) {
                        if (postlist!![i].Cur_Name == "Российских рублей") {
                            Items.add(ModelBaseValute(postlist!![i].Cur_Name.toString(), R.drawable.rus))
                        }
                        else if (postlist!![i].Cur_Name == "Доллар США") {
                            Items.add(ModelBaseValute(postlist!![i].Cur_Name.toString(), R.drawable.usa))
                        }
                        else if (postlist!![i].Cur_Name == "Евро") {
                            Items.add(ModelBaseValute(postlist!![i].Cur_Name.toString(), R.drawable.es))
                        }
                        else if (postlist!![i].Cur_Name == "Болгарский лев") {
                            Items.add(ModelBaseValute(postlist!![i].Cur_Name.toString(), R.drawable.bg))
                        }
                        else if (postlist!![i].Cur_Name == "Иен") {
                            Items.add(ModelBaseValute(postlist!![i].Cur_Name.toString(), R.drawable.jp))
                        }
                        else {
                            Items.add(ModelBaseValute(postlist!![i].Cur_Name.toString(), R.drawable.flag_belarusi))
                        }
                    }
                list2.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, l ->
                    if (position == 0) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[0].Cur_OfficialRate!!
                        abbreviatura=postlist[0].Cur_Abbreviation!!

                    }
                    else if (position == 1) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[1].Cur_OfficialRate!!
                        abbreviatura=postlist[1].Cur_Abbreviation!!
                    }
                    else if (position == 2) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[2].Cur_OfficialRate!!
                        abbreviatura=postlist[2].Cur_Abbreviation!!
                    }
                    else if (position == 3) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[3].Cur_OfficialRate!!
                        abbreviatura=postlist[3].Cur_Abbreviation!!
                    }
                    else if (position == 4) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[4].Cur_OfficialRate!!
                        abbreviatura=postlist[4].Cur_Abbreviation!!
                    }
                    else if (position == 5) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[5].Cur_OfficialRate!!
                        abbreviatura=postlist[5].Cur_Abbreviation!!
                    }
                    else if (position == 6) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[6].Cur_OfficialRate!!
                        abbreviatura=postlist[6].Cur_Abbreviation!!
                    }
                    else if (position == 7) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[7].Cur_OfficialRate!!
                        abbreviatura=postlist[7].Cur_Abbreviation!!
                    }
                    else if (position == 8) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[8].Cur_OfficialRate!!
                        abbreviatura=postlist[8].Cur_Abbreviation!!
                    }
                    else if (position == 9) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[9].Cur_OfficialRate!!
                        abbreviatura=postlist[9].Cur_Abbreviation!!
                    }
                    else if (position == 10) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[10].Cur_OfficialRate!!
                        abbreviatura=postlist[10].Cur_Abbreviation!!
                    }
                    else if (position == 11) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[11].Cur_OfficialRate!!
                        abbreviatura=postlist[11].Cur_Abbreviation!!
                    }
                    else if (position == 12) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[12].Cur_OfficialRate!!
                        abbreviatura=postlist[12].Cur_Abbreviation!!
                    }
                    else if (position == 13) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[13].Cur_OfficialRate!!
                        abbreviatura=postlist[13].Cur_Abbreviation!!
                    }
                    else if (position == 14) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[14].Cur_OfficialRate!!
                        abbreviatura=postlist[14].Cur_Abbreviation!!
                    }
                    else if (position == 15) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[15].Cur_OfficialRate!!
                        abbreviatura=postlist[15].Cur_Abbreviation!!
                    }
                    else if (position == 16) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[16].Cur_OfficialRate!!
                        abbreviatura=postlist[16].Cur_Abbreviation!!
                    }
                    else if (position == 17) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[17].Cur_OfficialRate!!
                        abbreviatura=postlist[17].Cur_Abbreviation!!
                    }
                    else if (position == 18) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[18].Cur_OfficialRate!!
                        abbreviatura=postlist[18].Cur_Abbreviation!!
                    }
                    else if (position == 19) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[19].Cur_OfficialRate!!
                        abbreviatura=postlist[19].Cur_Abbreviation!!
                    }
                    else if (position == 20) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[20].Cur_OfficialRate!!
                        abbreviatura=postlist[20].Cur_Abbreviation!!
                    }
                    else if (position == 21) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[21].Cur_OfficialRate!!
                        abbreviatura=postlist[21].Cur_Abbreviation!!
                    }
                    else if (position == 22) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[22].Cur_OfficialRate!!
                        abbreviatura=postlist[22].Cur_Abbreviation!!
                    }
                    else if (position == 23) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[23].Cur_OfficialRate!!
                        abbreviatura=postlist[23].Cur_Abbreviation!!
                    }
                    else if (position == 24) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[24].Cur_OfficialRate!!
                        abbreviatura=postlist[24].Cur_Abbreviation!!
                    }
                    else if (position == 25) {
                        Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                        qwe = postlist[25].Cur_OfficialRate!!
                        abbreviatura=postlist[25].Cur_Abbreviation!!
                    }
                })
                    mainArr.addAll(Items)
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
}


