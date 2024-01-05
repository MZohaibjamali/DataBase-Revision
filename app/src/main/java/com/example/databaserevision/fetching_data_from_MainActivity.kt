package com.example.databaserevision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class fetching_data_from_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_data_from_main)

        var id_textView = findViewById<TextView>(R.id.tvid)
        var name_textView = findViewById<EditText>(R.id.tvName)
        var email_textView = findViewById<EditText>(R.id.tvEmail)
        val dataFetchingBtn = findViewById<Button>(R.id.updateFetchingdata)

        var id = 0
        id = intent.getIntExtra("ID", -1)
        Log.d("gettingValue", "ID from Intent: $id")

        val name: String? = intent.getStringExtra("NAME")
        val email: String? = intent.getStringExtra("EMAIL")

        Log.e("name_textView", " Name $name_textView Email $email_textView")
        id_textView.text = id.toString()
        name_textView.setText(name)
        email_textView.setText(email)

        dataFetchingBtn.setOnClickListener {


            val updatedID = id_textView.text.toString().toInt()
            val updatedName = name_textView.text.toString()
            val updatedEmail = email_textView.text.toString()

            if (updatedName.isNotEmpty() && updatedEmail.isNotEmpty()) {
                val db = SQLite_CreationClass(this)
                db.updateData(updatedID, updatedName, updatedEmail)
                
                Log.e("updatedData", "ID: $updatedID, Name: $updatedName, Email: $updatedEmail")
                Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Name and Email cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
