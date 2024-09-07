package com.example.ticktick.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.ticktick.model.Task

class DeleteDialog(private val task: Task, private val dialogListener: DialogListener) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Delete task")
                .setPositiveButton("Delete") { dialog, _ ->
                    dialogListener.onDialogDeleteClick(task, this)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException()
    }
}

interface DialogListener {
    fun onDialogDeleteClick(task: Task, dialog: DialogFragment)
}