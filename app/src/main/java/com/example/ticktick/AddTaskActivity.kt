package com.example.ticktick

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ticktick.data.RealmDatabase
import com.example.ticktick.databinding.ActivityAddtaskBinding
import com.example.ticktick.model.Task
import com.example.ticktick.utils.Constants
import com.example.ticktick.utils.Helper

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddtaskBinding
    private lateinit var databaseRepository: RealmDatabase
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var helper: Helper
    private var date: String = ""
    private var time: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddtaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseRepository = RealmDatabase()
        sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)

        val addTaskButton = binding.addTaskButton
        addTaskButton.setOnClickListener {
            createTask()
            returnToMainActivity()
        }

        binding.reminderCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            showReminder(isChecked)
        }
        helper = Helper()
    }

    private fun createTask() {
        val taskName = binding.addTaskName.text.toString()
        val userId = sharedPreferences.getString("user_id", "DEFAULT") ?: "DEFAULT"
        val dateTime = getDateTime()
        val dueDate = helper.formatDateTime(dateTime)
        val task = Task(userId, taskName, dueDate)
        databaseRepository.saveTask(task)
    }

    private fun returnToMainActivity() {
        val intent = Intent(this, TaskListActivity::class.java)
        startActivity(intent)
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun setTime(time: String) {
        this.time = time
        this.binding.addTaskButton.isEnabled = true
    }

    fun getDate(): String{
        return this.date
    }

    fun getTime(): String{
        return this.time
    }

    private fun getDateTime(): String? {
        if (date.isNotEmpty() && time.isNotEmpty()) {
            return this.date + "T" + this.time
        } else {
            return null
        }
    }

    private fun showReminder(isChecked: Boolean) {
        if (isChecked) {
            binding.fragmentContainerView.visibility = View.VISIBLE
            binding.fragmentContainerView.visibility = View.VISIBLE
            binding.addTaskButton.isEnabled = false

            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.binding.root.windowToken, 0)
        } else {
            binding.fragmentContainerView.visibility = View.GONE
            binding.fragmentContainerView.visibility = View.GONE
            binding.addTaskButton.isEnabled = true
            this.setDate("")
            this.setTime("")
        }
    }
}