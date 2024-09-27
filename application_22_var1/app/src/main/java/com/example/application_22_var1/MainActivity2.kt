package com.example.application_22_var1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        var button: AppCompatButton = findViewById<AppCompatButton>(R.id.result)

        var calendarView: CalendarView = findViewById<CalendarView>(R.id.calendarView)

        val dateText: TextView = findViewById(R.id.date)

        val descriptionText: TextView = findViewById(R.id.description)

        button.setOnClickListener{
            Log.d("Кнопка","Кнопка нажата")
            val key = "IkmYF4LfpTm3EMOBz1rfK9ZMcPYkP1wPgLlvsE7A"
            val date = calendarView.date
            val date_form = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val prompt = date_form.format(date)
            val url = "https://api.nasa.gov/planetary/apod?api_key=$key&date=$prompt"
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                { responce ->
                    val obj = JSONObject(responce)
                    Log.d("Запрос","Запрос выполнен")
                    val date = obj.getString("date")
                    val explanation = obj.getString("explanation")

                    dateText.text = "$date"

                    descriptionText.text = "$explanation"
                },
                { error ->
                    Log.d("Ошибка", "Volley ошибка: $error")
                }
            )
            queue.add(stringRequest)

        }

    }
}