package com.pwr.amproject

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.pwr.amproject.wifi.ClientConnector
import com.pwr.amproject.wifi.ServerConnector
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cards.*

class GameActivity : AppCompatActivity(), Cards.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
       //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var listAdapter: ArrayAdapterPlayers
    private lateinit var qlist: ArrayList<GUIPlayerInfo>
    private lateinit var serverConnector: ServerConnector
    private lateinit var clientConnector: ClientConnector
    private val setReqCode = 102
    private val locReqCode = 103
    val startStack = 500
    private var isServer  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        moveButton.setOnClickListener{goToMove()}

        qlist = ArrayList<GUIPlayerInfo>()


        findViewById<ImageView>(R.id.myCard1).setImageResource(R.drawable.r_blue_back)
        findViewById<ImageView>(R.id.myCard2).setImageResource(R.drawable.r_blue_back)

        findViewById<ImageView>(R.id.tableCard1).setImageResource(R.drawable.r_blue_back)
        findViewById<ImageView>(R.id.tableCard2).setImageResource(R.drawable.r_blue_back)
        findViewById<ImageView>(R.id.tableCard3).setImageResource(R.drawable.r_blue_back)
        findViewById<ImageView>(R.id.tableCard4).setImageResource(R.drawable.r_blue_back)
        findViewById<ImageView>(R.id.tableCard5).setImageResource(R.drawable.r_blue_back)
        findViewById<ImageView>(R.id.tableCard2).setImageResource(R.drawable.r_blue_back)

        var numPlayers = intent.getIntExtra("numPlayers", 2)
        var numBots = intent.getIntExtra("numBots", 2)
        isServer = intent.getBooleanExtra("isServer", false)
        var SSID =intent.getStringExtra("SSID")
        var password =intent.getStringExtra("password")



        for(i in 0..(numPlayers!!-1)){
            qlist.add(GUIPlayerInfo(i,startStack,0,1))
        }

        if(isServer){
            serverConnector = ServerConnector(this, setReqCode,44444,numPlayers-1-numBots)
            //serverConnector.setHotSpot(SSID,password)
            //serverConnector.startLookingForClients()
            magic1.setOnClickListener{serverConnector.setHotSpot(SSID,"")}
            magic2.setOnClickListener{serverConnector.startLookingForClients()}
        }else{
            clientConnector = ClientConnector(this, locReqCode,44444)
            magic1.setOnClickListener{clientConnector.connectToHotspot(SSID,"")}
            magic2.setOnClickListener{clientConnector.makeSocketConnection()}
            //clientConnector.connectToHotspot(SSID,password)
            //clientConnector.makeSocketConnection()
            //Log.d("ostatni", clientConnector.readInfo())
        }

        listAdapter = ArrayAdapterPlayers(this, qlist)
        listView.adapter = listAdapter
    }




    fun goToMove(){
        val intent = Intent(this, MoveActivity::class.java)
        startActivityForResult(intent, 100)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 100) {
            if(isServer){
                var s = data?.getStringExtra("action")
                //Log.d("ostatni",s)
               // serverConnector.writeClientByNum(0, s.toString())
                //Log.d("ostatni", "suk")
            }else{  
                //Log.d("ostatni", clientConnector.readInfo())
            }
        }

    }
}
