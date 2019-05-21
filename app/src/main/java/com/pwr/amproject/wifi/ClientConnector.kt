package com.pwr.amproject.wifi

import android.app.Activity
import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.net.Uri
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.provider.Settings
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket



class ClientConnector(private val context: Context, private val CODE_LOCATION_PERMISSION : Int, private val port : Int = 44444) {
    private val  wifiManager =  context.getSystemService(WIFI_SERVICE) as WifiManager
    private var socket  = Socket()
    private var thread = AcceptThread()
    private lateinit var output : DataOutputStream
    private lateinit var input : DataInputStream
    private val ipAdress = "192.168.43.1"

    fun readInfo() :String{
        return input.readUTF()
    }

    fun sendInfo(message : String){
        output.writeUTF(message)
    }

    /**connect to hotspot with given SSID.
     * It demends localisation turn ON
     * It should be used before function makeSocketConnection
     */
    fun connectToHotspot(netSSID : String ,netPass:String ) : Boolean {
        getPermLoc(context as Activity)
        var wifiConf = WifiConfiguration()
        var scanResultList = wifiManager.getScanResults()

        wifiManager.setWifiEnabled(true)
        if (wifiManager.isWifiEnabled()) {
            for (result in scanResultList) {
                if (result.SSID.equals(netSSID)) {

                    var mode = getSecurityMode(result);// get mode of hospot
                    wifiConf.SSID = "\"" + netSSID + "\""

                    if (mode.toLowerCase().equals("open")) {
                        wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    } else
                        if (mode.toLowerCase().equals("WEP")) {
                        wifiConf.wepKeys[0] = "\"" + netPass + "\"";
                        wifiConf.wepTxKeyIndex = 0;
                        wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                        wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

                    } else {
                        wifiConf.preSharedKey = "\"" + netPass + "\"";
                        wifiConf.hiddenSSID = true;
                        wifiConf.status = WifiConfiguration.Status.ENABLED;
                        wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                        wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                        wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                        wifiConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                        wifiConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                        wifiConf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

                    }
                    var res = wifiManager.addNetwork(wifiConf)
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(res, true);
                    wifiManager.reconnect();
                    wifiManager.setWifiEnabled(true)

                    return true;
                }
            }
        }
        return false
    }

    fun makeSocketConnection(){
        thread.start()
    }

    inner class AcceptThread : Thread(){
        override fun run() {
            try {
                socket.connect(InetSocketAddress(ipAdress,port))
                output = DataOutputStream(socket!!.getOutputStream() )
                input = DataInputStream(socket!!.getInputStream())
            }catch(e:Exception){
                Log.d("WIFIEDEK",e.toString())
            }
        }
    }

    // get Security Mode
    fun getSecurityMode(scanResult: ScanResult) :String {
        val cap = scanResult.capabilities
        val  modes = arrayOf("WEP", "EAP", "WPA")

        for (i in 0..modes.size-1 ) {
            if (cap.contains(modes[i])) {
                return modes[i];
            }
        }
        return "OPEN"
    }

    /**Function ask user for permissions
     */
    fun getPermLoc(context: Activity) {
        val permission: Boolean
        var i = Intent()
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.packageName));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivityForResult(i, CODE_LOCATION_PERMISSION)
    }
}