// src/main/java/com/example/todolist/MainActivity.kt
package com.example.todolist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    // Declare UI components
    private lateinit var editTextTask: EditText
    private lateinit var buttonAdd: Button
    private lateinit var listViewTasks: ListView

    // List to store tasks
    private lateinit var tasks: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        editTextTask = findViewById(R.id.editTextTask)
        buttonAdd = findViewById(R.id.buttonAdd)
        listViewTasks = findViewById(R.id.listViewTasks)

        // Initialize the list of tasks
        tasks = mutableListOf()

        // Set up an ArrayAdapter for the ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)
        listViewTasks.adapter = adapter

        // Set up a click listener for the "Add" button
        buttonAdd.setOnClickListener {
            addTask()
        }
    }

    // Function to add a task to the list
    private fun addTask() {
        // Get the text from the EditText and trim any leading/trailing whitespace
        val task = editTextTask.text.toString().trim()

        // Check if the task is not empty
        if (task.isNotEmpty()) {
            // Add the task to the list
            tasks.add(task)

            // Notify the ArrayAdapter that the data set has changed
            (listViewTasks.adapter as ArrayAdapter<*>).notifyDataSetChanged()

            // Clear the EditText for the next task
            editTextTask.text.clear()
        }
    }
}
