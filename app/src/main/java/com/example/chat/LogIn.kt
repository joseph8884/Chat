package com.example.chat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    private lateinit var edt_email:EditText
    private lateinit var edt_password:EditText
    private lateinit var btn_login:Button
    private lateinit var btn_SignUp: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        edt_email=findViewById(R.id.edt_email)
        edt_password=findViewById(R.id.contraseña)
        btn_login=findViewById(R.id.btn_login)
        btn_SignUp=findViewById(R.id.btn_register)


        btn_SignUp.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)

            startActivity(intent)
        }
        btn_login.setOnClickListener {
            val email = edt_email.text.toString()
            val password =edt_password.text.toString()

            login(email,password);
        }

    }
    private fun login(email: String, password: String){
    //login del usuario
        mAuth.signInWithEmailAndPassword(email, password)

            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LogIn, Menu::class.java).apply {
                        putExtra("email", email)
                    }

                    finish()

                    startActivity(intent)

                } else {

                    Toast.makeText(
                        this@LogIn,
                        "Error el usuario no existe.",

                        Toast.LENGTH_SHORT,

                        ).show()

                }
            }

    }

}