package com.example.desfioandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CorAdapter (var context: Context, var lista:ArrayList<Cor>) : BaseAdapter() {
    override fun getCount(): Int {
        return lista.size
    }

    override fun getItem(position: Int): Any {
        return lista[position]
    }

    override fun getItemId(position: Int): Long {
        return lista[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cor = this.lista.get(position)
        val linha: View
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            linha = inflater.inflate(R.layout.lista, null)
        } else {
            linha = convertView
        }
        val tvCor = linha.findViewById<TextView>(R.id.lblCor)
        tvCor.text = cor.nome
        val tvCodigo = linha.findViewById<TextView>(R.id.lblCodigo)
        tvCodigo.text = cor.codigo.toString()
        val imgplt = linha.findViewById<ImageView>(R.id.ivColor)
        imgplt.setColorFilter(cor.codigo)
        return linha
    }

}