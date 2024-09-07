package com.example.ticktick.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ticktick.AddTaskActivity
import com.example.ticktick.databinding.FragmentShowDateTimeBinding

class ShowDateTime : Fragment() {
    private lateinit var parentActivity: AddTaskActivity
    private var _binding: FragmentShowDateTimeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as AddTaskActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowDateTimeBinding.inflate(inflater, container, false)
        binding.showSetDate.text="Reminder set for " + parentActivity.getDate() + " at " + parentActivity.getTime()
        return binding.root
    }
}