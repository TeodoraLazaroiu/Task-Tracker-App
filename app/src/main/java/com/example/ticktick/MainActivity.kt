package com.example.ticktick

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticktick.data.RealmDatabase
import com.example.ticktick.databinding.ActivityMainBinding
import com.example.ticktick.model.Task
import com.example.ticktick.task.TaskAdapter
import com.example.ticktick.task.TaskClickListener
import com.example.ticktick.task.TaskViewModel
import com.google.firebase.auth.FirebaseAuth

class  MainActivity : AppCompatActivity(), TaskClickListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var addTaskButton: Button
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var database: RealmDatabase

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = RealmDatabase()

        val currentUser = firebaseAuth.currentUser
        val email = currentUser?.email
        val greeting = "Hello ${email ?: "anonymous"}"
        binding.greetingTextView.text = greeting

        addTaskButton = binding.addTaskButton
        addTaskButton.text = "Add Task"
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra("uid", currentUser?.uid)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        // val taskList = database.getTaskForUser(currentUser?.uid)
        val taskList = database.getAllTasks()
        val recyclerView : RecyclerView = binding.tasksList
        recyclerView.setLayoutManager(LinearLayoutManager(applicationContext))
        recyclerView.setAdapter(TaskAdapter(taskList, this))

        setRecyclerView(taskList)
    }

    private fun setRecyclerView(taskList : List<Task>) {
        val mainActivity = this
//        taskViewModel.taskList.observe(this) {
//            binding.tasksList.apply {
//                layoutManager = LinearLayoutManager(applicationContext)
//                adapter = TaskAdapter(it, mainActivity)
//            }
//        }

//        binding.tasksList.apply {
//            layoutManager = LinearLayoutManager(applicationContext)
//            adapter = TaskAdapter(taskList, mainActivity)
//        }
    }

    override fun completeTask(task: Task) {
        task.completed = true
    }
}