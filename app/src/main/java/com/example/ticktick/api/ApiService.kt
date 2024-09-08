package com.example.ticktick.api

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.ticktick.TaskListActivity
import com.example.ticktick.model.Task
import com.example.ticktick.utils.Constants
import com.example.ticktick.utils.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("NewApi")
class ApiService(private val parentActivity: TaskListActivity) {
    fun importTasks() {
        val service = ApiClient.makeRetrofitService(Constants.TASKS_API_URL)
        val sharedPreferences = parentActivity.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "DEFAULT") ?: "DEFAULT"
        val helper = Helper()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getTasks()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val responseTasks = response.body()?.subList(0, 5)
                        val tasks = responseTasks?.stream()?.map { Task(userId, it.title,
                            helper.dateStringToRealmInstant(it.dueDate)) }?.toList()
                        if (tasks != null) {
                            parentActivity.saveImportedTasks(tasks)
                        }
                    } else {
                        Log.e("error", "${response.code()}")
                    }
                } catch (e: Throwable) {
                    Log.e("error", e.message.toString())
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun getRandomQuote(){
        val service = ApiClient.makeRetrofitService(Constants.QUOTE_API_URL)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getQuote()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        parentActivity.binding.quoteTextView.text = "\"${response.body()?.get(0)?.q.toString()}\" - ${response.body()?.get(0)?.a.toString()}"
                    } else {
                        Log.e("error", "${response.code()}")
                    }
                } catch (e: Throwable) {
                    Log.e("error",e.message.toString())
                }
            }
        }
    }
}