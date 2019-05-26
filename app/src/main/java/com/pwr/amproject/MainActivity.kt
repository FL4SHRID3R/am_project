package com.pwr.amproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun goToGame(view: View){
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("isServer",server.isChecked)
        intent.putExtra("SSID", textName.text.toString())
        intent.putExtra("password","" ) //textPassword.text.toString()
        intent.putExtra("numBots", textBots.text.toString().toInt())
        intent.putExtra("numPlayers", textPlayers.text.toString().toInt())

        startActivityForResult(intent, 123)
    }
}
