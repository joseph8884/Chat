package com.example.chat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chat.chatear.MainActivity
import com.example.chat.chatear.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
class SignUp : AppCompatActivity() {
    private lateinit var edt_name: EditText
    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText
    private lateinit var btn_SignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        edt_name = findViewById(R.id.name)
        edt_email = findViewById(R.id.edt_email)
        edt_password=findViewById(R.id.contraseña)
        btn_SignUp=findViewById(R.id.btn_register)
        btn_SignUp.setOnClickListener {
            val name = edt_name.text.toString()
            val email = edt_email.text.toString()
            val password =edt_password.text.toString()
            singUp(name,email,password);
        }
    }
    private fun singUp(name: String,email: String, password: String){
//logica para crear usuarios
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@SignUp,
                        "Ha ocurrido un error.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    private fun addUserToDatabase (name: String,email: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}