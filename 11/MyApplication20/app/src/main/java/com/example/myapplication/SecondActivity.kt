package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
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


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cur = this
        setContentView(R.layout.activity_second)
        val actionBar = supportActionBar
        actionBar!!.title = "Базовая валюта"
        Log.d("bababoi", "create2")
    }


    override fun onStart() {
        super.onStart()
        Log.d("bababoi", "Start2")
    }

    override fun onResume() {
        super.onResume()
        var newOfficialRate: Double = 0.0
        var newOfficialRateRus: Double = 0.0
        var abbreviatura: String = ""
        val cur = this
        val mainArr: ArrayList<ModelBaseValute> = ArrayList()

        CoroutineScope(Dispatchers.IO).launch {
            progressBar_Second_Activity.setVisibility(ProgressBar.VISIBLE);
            val rf: Retrofit = Retrofit.Builder()
                .baseUrl(RetrofitInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            val API = rf.create(RetrofitInterface::class.java)
            val call = API.posts
            call?.enqueue(object : Callback<List<CurrencyBelarus?>?> {
                override fun onFailure(call: Call<List<CurrencyBelarus?>?>, t: Throwable) {
                    Toast.makeText(cur, "Ошибка загрузки данных", Toast.LENGTH_LONG).show()
                    progressBar_Second_Activity.visibility = ProgressBar.INVISIBLE

                }

                @SuppressLint("WrongConstant")
                override fun onResponse(
                    call: Call<List<CurrencyBelarus?>?>,
                    response: Response<List<CurrencyBelarus?>?>
                ) {
                    var postlist: List<CurrencyBelarus>? = response.body() as List<CurrencyBelarus>
                    val items = ArrayList<ModelBaseValute>()
                    val adapter = MyAdapterBaseValute(cur, R.layout.row_second_activity, mainArr)
                    var draw = R.drawable.ic_launcher_background
                    for (i in postlist!!.indices) {

                        val draw = postlist!![i].Cur_Abbreviation.draw
                        items.add(ModelBaseValute(postlist!![i].Cur_Name, draw))
                    }
                    list2.onItemClickListener =
                        AdapterView.OnItemClickListener { adapterView, view, position, l ->
                            Toast.makeText(cur, "Выбрана базовая валюта", Toast.LENGTH_LONG).show()
                            newOfficialRate = postlist[position].Cur_OfficialRate!!
                            newOfficialRateRus = postlist[position].Cur_OfficialRate!!
                            abbreviatura = postlist[position].Cur_Abbreviation.name
                            val intent = Intent(cur, MainActivity::class.java)
                            intent.putExtra(Constant.charCode, abbreviatura)
                            intent.putExtra(Constant.newValue, newOfficialRate)
                            intent.putExtra(Constant.newValueRus, newOfficialRateRus)
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    mainArr.addAll(items)

                    runOnUiThread {
                        list2.postDelayed(
                            Runnable { list2.setSelection(EnumFlag.valueOf(Constant.cod).number - 1) },
                            120
                        )
                        list2.adapter = adapter
                    }
                    progressBar_Second_Activity.visibility = ProgressBar.INVISIBLE;

                }
            }
            )

        }


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
