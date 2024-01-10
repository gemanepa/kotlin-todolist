// src/main/java/com/example/todolist/MainActivity.kt
package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var editTextTask: EditText
    private lateinit var buttonAdd: Button
    private lateinit var listViewTasks: ListView
    private lateinit var tasks: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTask = findViewById(R.id.editTextTask)
        buttonAdd = findViewById(R.id.buttonAdd)
        listViewTasks = findViewById(R.id.listViewTasks)

        tasks = mutableListOf()

        val adapter = TaskAdapter(this, R.layout.task_list_item, tasks)
        listViewTasks.adapter = adapter

        buttonAdd.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        val task = editTextTask.text.toString().trim()
        if (task.isNotEmpty()) {
            tasks.add(task)
            (listViewTasks.adapter as TaskAdapter).notifyDataSetChanged()
            editTextTask.text.clear()
        }
    }

    // Custom ArrayAdapter for tasks with a delete icon
    private class TaskAdapter(
        context: MainActivity,            // The context in which the adapter is used
        private val layoutResourceId: Int, // The resource ID for the layout of each item
        private val data: MutableList<String> // The list of tasks
    ) : ArrayAdapter<String>(context, layoutResourceId, data) {

        // Override the getView method to customize the appearance of each item in the ListView
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var row = convertView
            val holder: ViewHolder

            // Check if a recycled view is available; if not, inflate a new one
            if (row == null) {
                val inflater = LayoutInflater.from(context)
                row = inflater.inflate(layoutResourceId, parent, false)

                // Create a ViewHolder and associate it with the row
                holder = ViewHolder()
                holder.taskTextView = row.findViewById(R.id.textViewTask)
                holder.deleteImageView = row.findViewById(R.id.imageViewDelete)

                row.tag = holder
            } else {
                // If a recycled view is available, reuse the ViewHolder from the tag
                holder = row.tag as ViewHolder
            }

            // Get the task at the current position
            val task = data[position]

            // Set the task text to the TextView
            holder.taskTextView.text = task

            // Set up click listener for the delete icon
            holder.deleteImageView.setOnClickListener {
                removeTask(position)
            }

            return row!!
        }

        // Function to remove a task at the specified position and notify the adapter
        private fun removeTask(position: Int) {
            data.removeAt(position)
            notifyDataSetChanged()
        }

        // ViewHolder class to hold references to the views for efficient recycling
        private class ViewHolder {
            lateinit var taskTextView: TextView
            lateinit var deleteImageView: ImageView
        }
    }

}
