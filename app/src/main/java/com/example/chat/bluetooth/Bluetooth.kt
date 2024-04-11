package com.example.chat.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.chat.R

private val REQUEST_ENABLE_BT:Int = 1;
private val REQUEST_DISCOVER_BT:Int = 2;
class Bluetooth : AppCompatActivity() {
    lateinit var btnOn: Button
    lateinit var btnOff: Button
    lateinit var btnPaired: Button
    lateinit var btnDect: Button
    lateinit var mBtAdapter: BluetoothAdapter
    lateinit var bluetoothStatusTv: TextView
    lateinit var bluetoothIv: ImageView
    lateinit var pairedTv: TextView
    @SuppressLint("MissingPermission", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)
        supportActionBar?.title = "Bluetooth"
        btnOn = findViewById(R.id.btnOn)
        btnOff = findViewById(R.id.btnOff)
        btnPaired = findViewById(R.id.btnPair)
        btnDect = findViewById(R.id.btnDect)
        bluetoothStatusTv = findViewById(R.id.bluetoothStatusTv)
        bluetoothIv = findViewById(R.id.bluetoothIv)
        pairedTv = findViewById(R.id.pairedTv)
    }
}