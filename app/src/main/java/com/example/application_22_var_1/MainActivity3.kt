package com.example.application_22_var_1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity3 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var requestAdapter: RequestAdapter
    private lateinit var requestDatabase: RequestDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        requestDatabase = Room.databaseBuilder(
            applicationContext,
            RequestDatabase::class.java, "request_database"
        ).build()

        requestDatabase.requestDao().getAllRequestItems().observe(this, Observer { requestList ->
            requestAdapter = RequestAdapter(this@MainActivity3, requestList)
            recyclerView.adapter = requestAdapter
        })

    }

    fun exit(view: View) {
        val sharedPreferences = getSharedPreferences("PREFS_FILE", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("login")
        editor.remove("password")
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}