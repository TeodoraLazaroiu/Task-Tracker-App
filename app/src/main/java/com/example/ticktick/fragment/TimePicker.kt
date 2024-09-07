package com.example.ticktick.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ticktick.AddTaskActivity
import com.example.ticktick.R
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
    ): View {
        _binding = FragmentTimePickerBinding.inflate(inflater, container, false)
        binding.setButton.setOnClickListener {
            parentActivity.setTime(getTimeFromPicker())
            navigateToNextFragment()
        }
        return binding.root
    }

    fun getTimeFromPicker(): String {
        val hour =
            if (binding.timePicker.hour.toString().length < 2) "0" + binding.timePicker.hour.toString() else binding.timePicker.hour
        val minute =
            if (binding.timePicker.minute.toString().length < 2) "0" + binding.timePicker.minute.toString() else binding.timePicker.minute
        return ("$hour:$minute:00.00")
    }

    fun navigateToNextFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.action_timePicker2_to_showDateTime)
    }
}