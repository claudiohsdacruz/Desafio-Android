package com.example.desfioandroid

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast

class FormActivity : AppCompatActivity() {
    private lateinit var redSeekBar: SeekBar
    private lateinit var greenSeekBar: SeekBar
    private lateinit var blueSeekBar: SeekBar
    private lateinit var nomeCor: EditText
    private lateinit var btfColor: Button
    private lateinit var salvar: Button
    private lateinit var cancelar: Button
    private var codigoCor: Int = 0
    private lateinit var dao: CorDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        this.dao = CorDAO(this)

        this.nomeCor = findViewById(R.id.etForm)
        this.btfColor = findViewById(R.id.btfColor)
        this.redSeekBar = findViewById(R.id.af_redseek)
        this.greenSeekBar = findViewById(R.id.af_greenseek)
        this.blueSeekBar = findViewById(R.id.af_blueseek)
        this.salvar = findViewById(R.id.btfSave)
        this.cancelar = findViewById(R.id.btfCancel)


        salvar.setOnClickListener( {saveColor (it)})
        cancelar.setOnClickListener( {cancel(it)} )
        btfColor.setOnClickListener( {registerColor(it)} )

        // recebendo e exibindo os dados de uma cor no formulário para atualização
        if(intent.hasExtra("COR")){
            var cor = intent.getSerializableExtra("COR") as Cor
            this.nomeCor.setText(cor.nome)
            this.btfColor.setText(cor.toHex())
            redSeekBar.progress = Color.red(cor.codigo)
            greenSeekBar.progress = Color.green(cor.codigo)
            blueSeekBar.progress = Color.blue(cor.codigo)
            btfColor.setBackgroundColor(cor.codigo)
        }

        redSeekBar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               setSeekListenerValues()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        greenSeekBar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setSeekListenerValues()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        blueSeekBar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setSeekListenerValues()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }

    private fun setSeekListenerValues() {
        val red = String.format("#%02X", (0xFF and redSeekBar.progress))
        val green = String.format("%02X", (0xFF and greenSeekBar.progress))
        val blue = String.format("%02X", (0xFF and blueSeekBar.progress))
        btfColor.text = "${red}${green}${blue}"
        btfColor.setBackgroundColor(Color.rgb(redSeekBar.progress,greenSeekBar.progress,blueSeekBar.progress))
        this.codigoCor = Color.rgb(redSeekBar.progress,greenSeekBar.progress,blueSeekBar.progress)
    }

    private fun registerColor(view: View?) {
        Toast.makeText(this,"Copiado: ${btfColor.text}",Toast.LENGTH_SHORT).show()
        // copiando para área de transferência
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text", "${btfColor.text}")
        clipboard.setPrimaryClip(clip)

    }

    private fun cancel(view: View?) {
        finish()
    }

    private fun saveColor(view: View){
        val nome: String = this.nomeCor.text.toString()
        val codigo = this.codigoCor

        val cor = if(intent.hasExtra("COR")){
            val c = intent.getSerializableExtra("COR") as Cor
            c.nome = nome
            c.codigo = codigo
            c
        }else{
            Cor(nome,codigo)
        }


        this.dao.insert(cor)

        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("COR",cor)
        setResult(Activity.RESULT_OK, intent)
        Log.i("BANCO_LOG",cor.toString())
        Toast.makeText(this,"cor salva com sucesso",Toast.LENGTH_SHORT).show()
        finish()

    }

}


