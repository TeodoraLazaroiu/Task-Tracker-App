package com.example.ticktick

import android.R
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.ticktick.databinding.FragmentTimePickerBinding


class TimePicker : Fragment() {
    private lateinit var parentActivity: AddTaskActivity
    private var _binding: FragmentTimePickerBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as AddTaskActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimePickerBinding.inflate(inflater, container, false)
        binding.setButton.setOnClickListener() {
            parentActivity.setTime(getTimeFromPicker())
        }
        return binding.root
    }

    fun getTimeFromPicker(): String {
        val hour = binding.timePicker.hour
        val minute = binding.timePicker.minute
        return ("$hour:$minute:00.00")
    }
}