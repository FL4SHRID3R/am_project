package com.pwr.amproject

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ArrayAdapterPlayers(context: Context, var data: ArrayList<GUIPlayerInfo>): ArrayAdapter<GUIPlayerInfo>(context, R.layout.list_layout, data)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_layout, parent, false)
        }

        view!!.findViewById<TextView>(R.id.textPlayer).text ="Gracz ${data[position].id}"
        view!!.findViewById<TextView>(R.id.textStackOwn).text ="Gracz ${data[position].stackOwn}"
        view!!.findViewById<TextView>(R.id.textStackGame).text ="Gracz ${data[position].stackGame}"

        when(data[position].stateOwn){
            0 -> {(view!!.findViewById<TextView>(R.id.textState)).setBackgroundColor(Color.RED)}
            1 -> {(view!!.findViewById<TextView>(R.id.textState)).setBackgroundColor(Color.BLUE)}
            else -> {(view!!.findViewById<TextView>(R.id.textState)).setBackgroundColor(Color.WHITE)}
        }

        return view
    }
}
