 package com.example.ticktick

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import com.example.ticktick.data.DatabaseRepository
//import com.example.ticktick.data.IDatabaseRepository
import com.example.ticktick.model.User
import com.google.firebase.auth.FirebaseAuth
import io.realm.kotlin.Realm

 class RegisterActivity : AppCompatActivity() {
    private lateinit var emailInput : EditText
    private lateinit var passwordInput : EditText
    private lateinit var passwordConfirmInput : EditText
    private lateinit var registerButton : Button
    private lateinit var redirectToLogin : TextView

    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var repository: IDatabaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        emailInput = findViewById(R.id.user_email)
        passwordInput = findViewById(R.id.user_password)
        passwordConfirmInput = findViewById(R.id.user_confirm_password)
        registerButton = findViewById(R.id.register_button)
        redirectToLogin = findViewById(R.id.redirect_to_login)

        firebaseAuth = FirebaseAuth.getInstance()
//        repository = DatabaseRepository()

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPass = passwordConfirmInput.text.toString()

            var isValid: Boolean = true
            if (email.isEmpty() or password.isEmpty() or confirmPass.isEmpty())
            {
                isValid = false
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
            if (password != confirmPass)
            {
                isValid = false
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }

            if (isValid)
            {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = User(email, password)
                        //repository.createUser(user)
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, "Could not register user", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        redirectToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}