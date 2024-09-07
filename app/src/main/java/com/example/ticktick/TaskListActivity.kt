package com.example.ticktick

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticktick.data.RealmDatabase
import com.example.ticktick.data.api.ApiClientFactory
import com.example.ticktick.databinding.ActivityTasklistBinding
import com.example.ticktick.fragment.ActionSheet
import com.example.ticktick.model.Task
import com.example.ticktick.task.TaskAdapter
import com.example.ticktick.task.TaskClickListener
import com.example.ticktick.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class  TaskListActivity : AppCompatActivity(), TaskClickListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityTasklistBinding
    private lateinit var addTaskButton: Button
    private lateinit var database: RealmDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var currentUserId: String

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
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

        val taskList = database.getTaskForUser(currentUserId)

        recyclerView = binding.tasksList
        recyclerView.setLayoutManager(LinearLayoutManager(applicationContext))
        recyclerView.setAdapter(TaskAdapter(taskList, this))

        getRandomQuote()
    }

    override fun completeTask(task: Task) {
        database.completeTask(task)
        val taskList = database.getTaskForUser(currentUserId)
        recyclerView.setAdapter(TaskAdapter(taskList, this))
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getRandomQuote(){
        val service = ApiClientFactory.makeRetrofitService(Constants.QUOTE_API_URL)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getQuote()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        binding.quoteTextView.text = "\"${response.body()?.get(0)?.q.toString()}\" - ${response.body()?.get(0)?.a.toString()}"
                    } else {
                        Log.e("APIERROR", "${response.code()}")
                    }
                } catch (e: HttpException) {
                    Log.e("APIEXCEPTION", "${e.message}")
                } catch (e: Throwable) {
                    Log.e("APIEXCEPTION","Ooops: Something else went wrong")
                }
            }
        }
    }
}