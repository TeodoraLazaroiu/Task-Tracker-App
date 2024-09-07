package com.example.ticktick

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ticktick.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var redirectToRegister: TextView

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        emailInput = findViewById(R.id.user_email)
        passwordInput = findViewById(R.id.user_password)
        loginButton = findViewById(R.id.login_button)
        redirectToRegister = findViewById(R.id.redirect_to_register)

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)

        if (firebaseAuth.currentUser == null) {

            loginButton.setOnClickListener {
                val email = emailInput.text.toString()
                val password = passwordInput.text.toString()

                if (email.isEmpty() or password.isEmpty()) {
                    Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            //store uid in shared prefs
                            val currentUser = firebaseAuth.currentUser
                            sharedPreferences.edit()
                                .putString("user_id", currentUser?.uid.toString()).apply()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Could not login user", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            redirectToRegister.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}