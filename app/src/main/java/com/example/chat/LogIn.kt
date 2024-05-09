package com.example.chat
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.google.firebase.auth.FacebookAuthProvider

class LogIn : AppCompatActivity() {
    private lateinit var edt_email: EditText
    private lateinit var edt_password: TextInputEditText
    private lateinit var btn_login: Button
    private lateinit var btn_SignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var btn_googlelogin: Button
    private lateinit var btn_loginfacebook: Button
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        edt_email = findViewById(R.id.edt_email)
        edt_password = findViewById(R.id.contraseña)
        btn_login = findViewById(R.id.btn_login)
        btn_SignUp = findViewById(R.id.btn_register)
        btn_googlelogin = findViewById(R.id.btn_loginwithgoogle)
        val web = "969090455696-h9jl4k11rsve5ibtuv3r0lnm4fe5gjag.apps.googleusercontent.com"
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(web)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        btn_googlelogin.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }


        btn_SignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)

            startActivity(intent)
        }
        btn_login.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()

            login(email, password);
        }

    }


    private fun login(email: String, password: String) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG1, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG1, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                    val intent = Intent(this@LogIn, Menu::class.java).apply {
                        putExtra("email", "no email")
                    }
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        // Update the UI based on the user's sign-in status
        if (user != null) {
            // User is signed in
            // ...
        } else {
            // User is not signed in
            // ...
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    companion object {
        private const val TAG1 = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
        private const val TAG = "FacebookLogin"
    }
}