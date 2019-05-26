package com.pwr.amproject

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_move.*

class MoveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move)
    }

    fun makeDecision(view: View){
        var v = view as Button
        val myIntent = Intent()

        myIntent.putExtra("action", v.text.toString())
        myIntent.putExtra("value", money.text)
        setResult(Activity.RESULT_OK, myIntent)
        finish()
    }
}
