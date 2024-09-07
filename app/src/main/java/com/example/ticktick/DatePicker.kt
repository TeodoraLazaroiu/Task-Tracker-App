package com.example.ticktick

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ticktick.databinding.FragmentDatePickerBinding

class DatePicker : Fragment() {
    private lateinit var parentActivity: AddTaskActivity
    private var _binding: FragmentDatePickerBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as AddTaskActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDatePickerBinding.inflate(inflater, container, false)
        binding.nextButton.setOnClickListener {
            parentActivity.setDate(getDateFromPicker())
            navigateToFragment()
        }
        return binding.root
    }

    fun navigateToFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.action_datePicker2_to_timePicker22)
    }

    fun getDateFromPicker(): String {
        val day =
            if (binding.datePicker.dayOfMonth.toString().length < 2) "0" + binding.datePicker.dayOfMonth.toString() else binding.datePicker.dayOfMonth
        val month =
            if (binding.datePicker.month.toString().length < 2) "0" + (binding.datePicker.month + 1).toString() else (binding.datePicker.month + 1)
        val year = binding.datePicker.year.toString()
        return ("$year-$month-$day")
    }
}