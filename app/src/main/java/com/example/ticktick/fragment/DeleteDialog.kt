package com.example.ticktick.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.ticktick.TaskListActivity
import com.example.ticktick.model.Task

class DeleteDialog(private val task: Task) : DialogFragment() {
    private lateinit var parentActivity: TaskListActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as TaskListActivity
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Delete task")
                .setPositiveButton("Delete") { _, _ ->
                    parentActivity.onDialogDeleteClick(task)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException()
    }


}