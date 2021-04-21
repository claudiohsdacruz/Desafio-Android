package com.example.desfioandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.AccessControlContext

class BancoHelper(context: Context) : SQLiteOpenHelper(context,"cor.db",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table cores(" +
                "id integer primary key autoincrement, " +
                "nome text, " +
                "codigo integer)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table cores")
        this.onCreate(db)
    }

}