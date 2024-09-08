package com.example.ticktick

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
import com.example.ticktick.fragment.ActionSheet
import com.example.ticktick.model.Task
import com.example.ticktick.adapter.TaskAdapter
import com.example.ticktick.adapter.TaskClickListener
import com.example.ticktick.api.ApiService
import com.example.ticktick.fragment.DeleteDialog
import com.example.ticktick.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class TaskListActivity : AppCompatActivity(), TaskClickListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var addTaskButton: Button
    private lateinit var database: RealmDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var currentUserId: String
    private lateinit var apiService: ApiService
    lateinit var binding: ActivityTasklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTasklistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = RealmDatabase()

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
        currentUserId = sharedPreferences.getString("user_id", "DEFAULT") ?: "DEFAULT"

        val currentUser = firebaseAuth.currentUser!!
        val email = currentUser.email
        val greeting = "Hello ${email ?: "anonymous"}"
        binding.greetingTextView.text = greeting

        addTaskButton = binding.addTaskButton
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra("uid", currentUserId)
            startActivity(intent)
        }

        binding.greetingTextView.setOnClickListener() {
            ActionSheet().show(supportFragmentManager, "actionSheet")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setRecyclerView()

        apiService = ApiService(this)
        apiService.getRandomQuote()
    }

    private fun setRecyclerView() {
        val taskList = database.getTaskForUser(currentUserId)
        recyclerView = binding.tasksList
        recyclerView.setLayoutManager(LinearLayoutManager(applicationContext))
        recyclerView.setAdapter(TaskAdapter(taskList, this))
    }

    override fun completeTask(task: Task) {
        database.completeTask(task)
        setRecyclerView()
    }

    override fun deleteTask(task: Task) {
        DeleteDialog(task).show(supportFragmentManager, "dialog")
    }

    fun onDialogDeleteClick(task: Task) {
        database.deleteTask(task)
        setRecyclerView()
    }

    fun saveImportedTasks(tasks: List<Task>) {
        database.saveTasks(tasks)
        setRecyclerView()
    }
}