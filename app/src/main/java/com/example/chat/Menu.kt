package com.example.chat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.chat.bluetooth.Bluetooth
import com.example.chat.camara.Camara
import com.example.chat.chatear.MainActivity
import com.example.chat.datosusuarios.DatosUsuario
import com.example.chat.maps.Mapa

class Menu : AppCompatActivity() {
    lateinit var datosUsuarios: LinearLayout
    lateinit var chat: LinearLayout
    lateinit var maps: LinearLayout
    lateinit var camara: LinearLayout
    lateinit var Bluetooth2: LinearLayout
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        datosUsuarios = findViewById(R.id.datos_usuario)
        chat = findViewById(R.id.Chatear)
        maps = findViewById(R.id.maps)
        camara = findViewById(R.id.Camara)
        Bluetooth2 = findViewById(R.id.Bluetooth)
        val bundle=intent.extras
        val email=bundle?.getString("email")
        Bluetooth2.setOnClickListener {
            val intent = Intent(this, Bluetooth::class.java)//Bluetooth activity
            startActivity(intent)
        }
        camara.setOnClickListener {
            val intent = Intent(this, Camara::class.java)//camara activity
            startActivity(intent)
        }

        maps.setOnClickListener {
            val intent = Intent(this, Mapa::class.java)//Mapa activity
            startActivity(intent)
        }
        datosUsuarios.setOnClickListener{
            val intent = Intent(this , DatosUsuario::class.java).apply {
                putExtra("email", email)
            }
            //DatosUsuario activity

            startActivity(intent)
        }
        chat.setOnClickListener{
            val intent = Intent(this , MainActivity::class.java)//Chatear activity

            startActivity(intent)
        }
    }
}