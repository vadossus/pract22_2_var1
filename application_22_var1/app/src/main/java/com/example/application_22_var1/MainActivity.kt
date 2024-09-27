package com.example.application_22_var1

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var button: Button = findViewById<AppCompatButton>(R.id.button_enter)

        var login: EditText = findViewById<EditText>(R.id.login)

        var password: EditText = findViewById<EditText>(R.id.password)

        var sharedPreferences: SharedPreferences = getSharedPreferences("PREFS_FILE", MODE_PRIVATE)


        var log = sharedPreferences.getString("login","")
        var pass = sharedPreferences.getString("password","")
        login.setText(log)
        password.setText(pass)

        button.setOnClickListener{

            if (login.text.toString().isNullOrEmpty() || password.text.toString().isNullOrEmpty())
            {
                Snackbar.make((findViewById(R.id.button_enter)), "Поля Логин или Пароль не были заполнены", Snackbar.LENGTH_SHORT).show()
            }
            else
            {
                if (password.text.toString().length > 8)
                {
                    Snackbar.make((findViewById(R.id.button_enter)), "Пароль не должен превышать больше 8 символов",Snackbar.LENGTH_SHORT).show()
                }
                else
                {
                    var editor = sharedPreferences.edit()
                    editor.putString("login", login.text.toString())
                    editor.putString("password",password.text.toString())
                    editor.apply()

                    android.os.Handler().postDelayed({
                        val intent = Intent(this,MainActivity2::class.java)
                        startActivity(intent)
                    },2000)
                }
            }
        }

    }
}