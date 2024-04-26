package com.example.chat.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chat.R

private val REQUEST_ENABLE_BT:Int = 1;
private val REQUEST_DISCOVER_BT:Int = 2;
class Bluetooth : AppCompatActivity() {
    private val REQUEST_BLUETOOTH_ENABLE=1
    private val REQUEST_BLUETOOTH_PERMISSIONS=2
    private val BLUETOOTH_CONNECT_PERMISSION_REQUEST_CODE=1

    lateinit var btnOn: Button
    lateinit var btnOff: Button
    lateinit var btnPaired: Button
    lateinit var btnDect: Button
    lateinit var mBtAdapter: BluetoothAdapter
    lateinit var bluetoothStatusTv: TextView
    lateinit var bluetoothIv: ImageView
    lateinit var pairedTv: TextView
    lateinit var deviceslist:ListView
    lateinit var devicesAdapter: ArrayAdapter<String>
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
        deviceslist = findViewById(R.id.listviewdevices)

        mBtAdapter=BluetoothAdapter.getDefaultAdapter()
        devicesAdapter= ArrayAdapter(this, android.R.layout.simple_list_item_1)
        deviceslist.adapter=devicesAdapter

        btnOn.setOnClickListener { enableBluetooth() }
        btnOff.setOnClickListener { disableBluetooth() }
        btnDect.setOnClickListener { makeDiscoverable() }
        btnPaired.setOnClickListener { viewPairedDevices() }

        if (mBtAdapter==null){
            bluetoothStatusTv.text="Bluetooh apagado"
        }
        else{
            bluetoothStatusTv.text="Bluetooh encendido"
        }
        if(mBtAdapter.isEnabled){
            bluetoothStatusTv.text="Bluetooh encendido"
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
        }else{
            bluetoothStatusTv.text="Bluetooh apagado"
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
        }
        registerReceiver(bluetoothReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
    }
    private fun enableBluetooth(){
        hasBluetoothPermission()
        requestBluetoothConnectPermission()
        if (!mBtAdapter.isEnabled){
            val enableIntent= Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

            startActivityForResult(enableIntent, REQUEST_ENABLE_BT)
        }else{
            Toast.makeText(this, "Bluetooth ya está encendido", Toast.LENGTH_SHORT).show()
        }
        if(mBtAdapter.isEnabled){
            bluetoothStatusTv.text="Bluetooh encendido"
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
        }else{
            bluetoothStatusTv.text="Bluetooh apagado"
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
        }
    }
    private fun disableBluetooth(){
        hasBluetoothPermission()
        requestBluetoothConnectPermission()
        if (mBtAdapter.isEnabled){
            mBtAdapter.disable()
            Toast.makeText(this, "Bluetooth apagado", Toast.LENGTH_SHORT).show()
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
        }else{
            Toast.makeText(this, "Bluetooth ya está apagado", Toast.LENGTH_SHORT).show()
            bluetoothStatusTv.text="Bluetooh apagado"
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
        }
        if(mBtAdapter.isEnabled){
            bluetoothStatusTv.text="Bluetooh encendido"
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
        }else{
            bluetoothStatusTv.text="Bluetooh apagado"
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
        }
    }
    private fun makeDiscoverable(){
       if (ContextCompat.checkSelfPermission(
           this,
           Manifest.permission.ACCESS_FINE_LOCATION
       ) != PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(
               this,
               arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
               REQUEST_BLUETOOTH_PERMISSIONS
           )
       }else{
              val discoverableIntent=Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
              discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
              startActivity(discoverableIntent)
       }
    }
    private fun viewPairedDevices(){
        devicesAdapter.clear()
        if (mBtAdapter.isEnabled){
            val pairedDevices= mBtAdapter.bondedDevices
            if (pairedDevices.isNotEmpty()){
                for (device in pairedDevices){

                    devicesAdapter.add("Nombre: ${device.name}, Dirección: ${device.address}")
                }
            }
        }else{
            Toast.makeText(this, "no se econtraron dispositivos emparejados", Toast.LENGTH_SHORT).show()
        }
    }
    private val bluetoothReceiver=object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val action=intent?.action
            if (BluetoothDevice.ACTION_FOUND==action){
                val device=intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

                devicesAdapter.add("Nombre: ${device?.name}, Dirección: ${device?.address}")
            }
        }
    }
    private fun hasBluetoothPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestBluetoothConnectPermission() {
        if (!hasBluetoothPermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                BLUETOOTH_CONNECT_PERMISSION_REQUEST_CODE
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray)
        {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                if (requestCode == BLUETOOTH_CONNECT_PERMISSION_REQUEST_CODE) {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Bluetooth permission granted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReceiver)
    }
}