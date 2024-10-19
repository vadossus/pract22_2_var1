package com.example.application_22_var_1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private lateinit var dateText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var calendarView: CalendarView
    private lateinit var button: AppCompatButton
    private lateinit var image: ImageView
    private var selectedDate: String = ""
    private lateinit var requestDatabase: RequestDatabase

    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        button = findViewById(R.id.result)

        calendarView = findViewById(R.id.calendarView)

        dateText = findViewById(R.id.date)

        descriptionText = findViewById(R.id.description)

        image = findViewById(R.id.imageView)

        requestDatabase = Room.databaseBuilder(
            applicationContext,
            RequestDatabase::class.java, "request_database"
        ).build()

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDateCalendar = Calendar.getInstance()
            selectedDateCalendar.set(year, month, dayOfMonth)
            val date_form = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDate = date_form.format(selectedDateCalendar.time)
            dateText.text = selectedDate
        }

        button.setOnClickListener {
            if (selectedDate.isNotEmpty()) {
                API_func(selectedDate)
            } else {
                Snackbar.make(findViewById(R.id.result), "Пожалуйста, выберите дату", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun API_func(date: String)
    {
        val key = "IkmYF4LfpTm3EMOBz1rfK9ZMcPYkP1wPgLlvsE7A"
        val url = "https://api.nasa.gov/planetary/apod?api_key=$key&date=$date"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                Log.d("Запрос", "Запрос выполнен")
                val date = obj.getString("date")
                val explanation = obj.getString("explanation")
                val imageS = obj.getString("url")

                calendarView.visibility = View.INVISIBLE
                dateText.text = date
                descriptionText.text = explanation
                Glide.with(this).load(imageS).into(image)
                image.visibility = View.VISIBLE

                CoroutineScope(Dispatchers.IO).launch {
                    val requestItem = RequestItem(0, date, explanation, imageS)
                    requestDatabase.requestDao().insert(requestItem)
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainActivity3::class.java)
                    startActivity(intent)
                }, 5000)
            },
            { error ->
                Log.d("Ошибка", "Volley ошибка: $error")
            }
        )
        queue.add(stringRequest)
    }
}