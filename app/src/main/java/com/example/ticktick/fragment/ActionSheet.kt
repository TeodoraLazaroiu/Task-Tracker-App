package com.example.ticktick.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.ticktick.LoginActivity
import com.example.ticktick.TaskListActivity
import com.example.ticktick.data.api.ApiClientFactory
import com.example.ticktick.databinding.FragmentActionSheetBinding
import com.example.ticktick.model.Task
import com.example.ticktick.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class ActionSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentActionSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentActivity: TaskListActivity
    private lateinit var sharedPreferences: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as TaskListActivity
        sharedPreferences = parentActivity.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActionSheetBinding.inflate(inflater, container, false)
        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this.parentActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.importTaskButton.setOnClickListener() {
            importTasks()
        }
        return binding.root
    }

    @SuppressLint("NewApi")
    private fun importTasks() {
        val service = ApiClientFactory.makeRetrofitService(Constants.TASKS_API_URL)
        val userId = sharedPreferences.getString("user_id", "DEFAULT") ?: "DEFAULT"

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getTasks()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val responseTasks = response.body()?.subList(0, 5)
                        val tasks = responseTasks?.stream()?.map { Task(userId, it.title,
                            dateStringToRealmInstant(it.dueDate)) }?.toList()
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

    private fun dateStringToRealmInstant(dateString: String) : RealmInstant {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(dateString, formatter)

        val localDateTime = LocalDateTime.of(localDate, java.time.LocalTime.MIDNIGHT)

        val instant = localDateTime.toInstant(ZoneOffset.UTC)
        return RealmInstant.from(instant.epochSecond, instant.nano)
    }
}