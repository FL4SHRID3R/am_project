package com.pwr.amproject.wifi

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.*



/**Class used for creating hotspot, and communnication of server device with clients
 *
 */
class ServerConnector(private val context: Context, private val CODE_WRITE_SETTINGS_PERMISSION:Int, private val port : Int = 44444, var maxNumPlayers :Int = 1 ) {
    private val  wifiManager =  context.getSystemService(WIFI_SERVICE) as WifiManager
    private lateinit var thread: ServerThread
    private var sockets = arrayOfNulls<Socket>(maxNumPlayers)
    private var outputs = arrayOfNulls<DataOutputStream>(maxNumPlayers)
    private var inputs = arrayOfNulls<DataInputStream>(maxNumPlayers)
    private var clientsNum = 0
    private lateinit var serverSocket : ServerSocket
    private var msgListArr =  arrayOfNulls<LinkedList<String>>(maxNumPlayers)
    private var readThreads = arrayOfNulls<ReaderThread>(maxNumPlayers)
    private lateinit var t :Thread;

    inner class ReaderThread(var nr: Int) : Thread()
    {
        override fun  run(){
            while(true){
                (msgListArr[nr])?.add(inputs[nr]!!.readUTF())
            }
        }
    }


    inner class ServerThread() : Thread() {

        init {
            Log.d("Portowo", "$port")
            serverSocket = ServerSocket(port)
            Log.d("Portowo", "${serverSocket.localPort}")
        }

        override fun run() {

            while(clientsNum<maxNumPlayers){
                sockets[clientsNum] = serverSocket.accept()
                Log.d("Portowo","Zaakceptowano")
                outputs[clientsNum] = DataOutputStream(sockets[clientsNum]!!.getOutputStream())
                inputs[clientsNum] = DataInputStream(sockets[clientsNum]!!.getInputStream())
                clientsNum++
            }
        }
    }
    init {
        for(i in 0..(maxNumPlayers-1)){
            msgListArr[i] = LinkedList<String>()
            readThreads[i] = ReaderThread(i)
        }
    }

    fun readClientByNum(num : Int) : String{
        if(msgListArr[num]?.isEmpty()!!){
            return ""
        }
        return msgListArr[num]?.pop().toString()
    }

    fun writeClientByNum(num : Int,message : String){
        t = Thread({outputs[num]!!.writeUTF(message)})
        t.start()

    }

    fun startLookingForClients(){
        Log.d("Portowo","$port")
        thread = ServerThread()
        thread.start()

    }

    /**
     * This function should be called before startLookingForClients
     */
    fun setHotSpot(SSID : String, password : String) {
        Log.d("Portowo", "setHotSpot")
        youDesirePermissionCode(context as Activity)

        var wifiConfig =  WifiConfiguration()

        if (password == "") {
            wifiConfig.SSID = SSID;
            wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else {
            wifiConfig.SSID = SSID;
            wifiConfig.preSharedKey = password;
            wifiConfig.hiddenSSID = true;
            wifiConfig.status = WifiConfiguration.Status.ENABLED;
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        }

        var meths = wifiManager::javaClass.get().methods
        for(m in meths){
            if(m.name.equals("setWifiApEnabled")){
                m.invoke(wifiManager, wifiConfig, true)
                wifiManager.saveConfiguration()
            }
        }
    }

    private fun youDesirePermissionCode(context: Activity) {
        val permission: Boolean

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context)
        } else {
            permission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_SETTINGS
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (permission) {
            //do your code
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.setData(Uri.parse("package:" + context.packageName))
                context.startActivityForResult(intent, CODE_WRITE_SETTINGS_PERMISSION)
            } else {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.WRITE_SETTINGS),
                    CODE_WRITE_SETTINGS_PERMISSION

                )
            }
        }
    }

}