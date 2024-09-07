package com.example.ticktick.fragment

import android.content.Context
import android.content.Intent
import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresExtension
import com.example.ticktick.LoginActivity
import com.example.ticktick.TaskListActivity
import com.example.ticktick.data.api.ApiClientFactory
import com.example.ticktick.databinding.FragmentActionSheetBinding
import com.example.ticktick.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActionSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentActionSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentActivity: TaskListActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as TaskListActivity
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
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

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun importTasks() {
        val service = ApiClientFactory.makeRetrofitService(Constants.TASKS_API_URL)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getTasks()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        // TODO put them in reciclerview / db / w/e and delete line below :)
                        Log.d("APIRESPONSE", response.body()?.get(0)?.description.toString())
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