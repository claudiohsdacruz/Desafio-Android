package com.example.desfioandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val ADD = 1
const val EDIT = 2
class MainActivity : AppCompatActivity() {
    private lateinit var fbtAdd: FloatingActionButton
    private lateinit var lvCores: ListView
    private lateinit var dao: CorDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.dao = CorDAO(this)
        this.lvCores = findViewById(R.id.lvmCores)
        this.fbtAdd = findViewById(R.id.btnadd)

        fbtAdd.setOnClickListener({ addColor(it) })

        this.lvCores.setOnItemClickListener(ClickLista())
        this.lvCores.setOnItemLongClickListener(ClickLongLista())

        val layout = android.R.layout.simple_list_item_1
        this.lvCores.adapter = CorAdapter(this,this.dao.select())

    }

    private fun addColor(view:View){
        val intent = Intent(this,FormActivity::class.java)
        startActivityForResult(intent,ADD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD){
                val cor = data?.getSerializableExtra("COR") as Cor
                Log.i("BANCO_LOG",cor.toString())
                this.lvCores.adapter = CorAdapter(this,this.dao.select())
            } else if(requestCode == EDIT){
                val cor: Cor = data?.getSerializableExtra("COR") as Cor
                dao.update(cor)
                dao.delete(cor.id)
                this.lvCores.adapter = CorAdapter(this,this.dao.select())

            }
        }
    }

    inner class ClickLista: AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val cor = parent?.getItemAtPosition(position) as Cor
            val intent = Intent(this@MainActivity,FormActivity::class.java)
            intent.putExtra("COR", cor)
            dao.update(cor)
            Log.i("BANCO_LOG",cor.toString())
            startActivityForResult(intent,EDIT)
        }
    }

    inner class ClickLongLista: AdapterView.OnItemLongClickListener{
        override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
            val cor= parent?.getItemAtPosition(position) as Cor
            dao.delete(cor.id)
            lvCores.adapter = CorAdapter(this@MainActivity,dao.select())
            Log.i("BANCO_LOG",dao.select().toString())
            return true
        }
    }
}