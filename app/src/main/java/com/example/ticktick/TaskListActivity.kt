package com.example.ticktick

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticktick.data.RealmDatabase
import com.example.ticktick.databinding.ActivityTasklistBinding
import com.example.ticktick.model.Task
import com.example.ticktick.task.TaskAdapter
import com.example.ticktick.task.TaskClickListener
import com.example.ticktick.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class  TaskListActivity : AppCompatActivity(), TaskClickListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityTasklistBinding
    private lateinit var addTaskButton: Button
    private lateinit var database: RealmDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var currentUserId: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTasklistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = RealmDatabase()

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
        currentUserId = sharedPreferences.getString("user_id", "DEFAULT") ?: "DEFAULT"

        var currentUser = firebaseAuth.currentUser!!
        val email = currentUser.email
        val greeting = "Hello ${email ?: "anonymous"}"
        binding.greetingTextView.text = greeting

        addTaskButton = binding.addTaskButton
        addTaskButton.text = "Add Task"
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra("uid", currentUserId)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val taskList = database.getTaskForUser(currentUserId)

        recyclerView = binding.tasksList
        recyclerView.setLayoutManager(LinearLayoutManager(applicationContext))
        recyclerView.setAdapter(TaskAdapter(taskList, this))
    }

    override fun completeTask(task: Task) {
        database.completeTask(task)
        val taskList = database.getTaskForUser(currentUserId)
        recyclerView.setAdapter(TaskAdapter(taskList, this))
    }
}