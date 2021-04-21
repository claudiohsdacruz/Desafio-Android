package com.example.desfioandroid

import android.widget.ImageView
import java.io.Serializable
class Cor : Serializable{
    var id: Int
    var nome: String
    var codigo: Int
    constructor(nome: String, codigo: Int){
        this.id = -1
        this.nome = nome
        this.codigo = codigo
    }
    constructor(id:Int, nome: String, codigo: Int){
        this.id = id
        this.nome = nome
        this.codigo = codigo
    }

    fun toHex() : String{
        return String.format("#%06X", (0xFFFFFF and codigo))
    }
    override fun toString(): String {
        return "${id} - ${nome} - ${codigo} - ${toHex()}"
    }
}