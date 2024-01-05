package com.example.databaserevision

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val databaseHelper = SQLite_CreationClass(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addBtn = findViewById<Button>(R.id.addBtn)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)
        val updateBtn = findViewById<Button>(R.id.updateBtn)
        var u_Name = findViewById<TextView>(R.id.u_name)
        var u_id = findViewById<TextView>(R.id.u_id)
        var u_Email = findViewById<TextView>(R.id.u_email)

        recyclerView = findViewById(R.id.recyclierView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = recyclerViewAdapterClass(
            emptyList(),
            this,
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(position: Int, item: Model_of_DataBase_class) {

                    Toast.makeText(applicationContext, "Zohaib $position", Toast.LENGTH_SHORT)
                        .show()
                    val intent =
                        Intent(this@MainActivity, fetching_data_from_MainActivity::class.java)

                    intent.putExtra("ID", item.id)
                    intent.putExtra("NAME", item.name)
                    intent.putExtra("EMAIL", item.email)
                    startActivity(intent)

                }
            })
        recyclerView.adapter = adapter

        val click = findViewById<Button>(R.id.click)

        click.setOnClickListener {
            // Update the dataset with the new employee list
            val employeeList = databaseHelper.viewEmployee()
            adapter.arrEmp = employeeList

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged()

            recyclerView.visibility = View.VISIBLE
            Toast.makeText(this, "List Shown", Toast.LENGTH_SHORT).show()
        }

        deleteBtn.setOnClickListener {
            try {
                var id = u_id.text.toString().toInt()
                databaseHelper.deleteEmployee(id)

                // Update the dataset with the new employee list
                val employeeList = databaseHelper.viewEmployee()
                adapter.arrEmp = employeeList

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()

                Toast.makeText(this, "Refresh List", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                showToast("Enter id Number")
            }
        }

        updateBtn.setOnClickListener {
            showAlertDialogueBox()
        }

        addBtn.setOnClickListener {
            val name = u_Name.text.toString()
            val email = u_Email.text.toString()
            if (name.isEmpty() || email.isEmpty()) {
                showToast("Enter correct Information")
            } else {
                databaseHelper.insertData(name, email)

                // Update the dataset with the new employee list
                val employeeList = databaseHelper.viewEmployee()
                adapter.arrEmp = employeeList

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()

                showToast("inform is added")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

    private fun showAlertDialogueBox() {
        val customDialog = AlertDialog.Builder(this)
        val customLayout = layoutInflater.inflate(R.layout.update_alert_dialogue, null)
        var id = customLayout.findViewById<TextView>(R.id.updateId)
        var name = customLayout.findViewById<TextView>(R.id.updateName)
        var email = customLayout.findViewById<TextView>(R.id.updateEmail)
        val updateBtn = customLayout.findViewById<Button>(R.id.updateBtn)

        customDialog.setView(customLayout)
        val dialog = customDialog.create()
        dialog.show()

        updateBtn.setOnClickListener {
            try {
                val update_id = id.text.toString().toIntOrNull()
                if (update_id != null) {
                    var update_id = name.text.toString().toInt()
                    var update_Name = name.text.toString()
                    var update_Email = email.text.toString()
                    databaseHelper.updateData(update_id, update_Name, update_Email)

                    showToast("Number Updated")
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Enter Correct ID", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                showToast("$e")
            }
        }
    }
}
