package com.example.mydialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.my_custom_dialog.view.*
import java.lang.ClassCastException

class MyDialogFragment : DialogFragment() {

    private lateinit var listener: NoticeDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.my_custom_dialog, null)
            builder.setView(view)
            // .setPositiveButton to use the default one

            // use custom button
            view.positve_button.setOnClickListener {
                dismiss()
                listener.onDialogPositiveClick(this)
            }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("ClassCastException")
        }
    }



    /**
     * used for activity action
     * */
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
    }
}