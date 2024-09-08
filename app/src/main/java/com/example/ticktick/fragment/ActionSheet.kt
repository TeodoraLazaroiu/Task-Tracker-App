package com.example.ticktick.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ticktick.LoginActivity
import com.example.ticktick.TaskListActivity
import com.example.ticktick.api.ApiService
import com.example.ticktick.databinding.FragmentActionSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth

class ActionSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentActionSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentActivity: TaskListActivity
    private lateinit var apiService: ApiService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as TaskListActivity
        apiService = ApiService(this.parentActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActionSheetBinding.inflate(inflater, container, false)
        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this.parentActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.importTaskButton.setOnClickListener {
            apiService.importTasks()
        }
        return binding.root
    }
}