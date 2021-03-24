package com.example.myapplication

import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open abstract class Abstract: AppCompatActivity() {
    fun kek(cur: Context, progressBar: ProgressBar) {

        var q: String = ""
        Thread.setDefaultUncaughtExceptionHandler { thread, ex ->
            Log.e(cur.toString(), ex.message!!)
            q = ex.message!!
            progressBar.postDelayed(Runnable {
                progressBar.visibility = ProgressBar.INVISIBLE
                Toast.makeText(cur, q, Toast.LENGTH_LONG).show()
            }, 10)
        }

    }
}