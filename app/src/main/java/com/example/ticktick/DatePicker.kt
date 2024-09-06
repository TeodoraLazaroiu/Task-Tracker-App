package com.example.ticktick

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.nextButton.setOnClickListener(){
            parentActivity.setDate(getDateFromPicker())
            navigateToFragment()
        }
        return binding.root
    }

    fun navigateToFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.action_datePicker2_to_timePicker22)
    }

    fun getDateFromPicker(): String {
        var day = binding.datePicker.dayOfMonth
        var month = binding.datePicker.month
        var year = binding.datePicker.year
        return("$day-$month-$year")
    }
}