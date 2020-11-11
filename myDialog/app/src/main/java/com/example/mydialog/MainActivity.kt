package com.example.mydialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*

// implement MyDialog listener interface
class MainActivity : AppCompatActivity(), MyDialogFragment.NoticeDialogListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openDialog.setOnClickListener {
            createDialog()
        }
    }

    // Create Custom Dialog and display
    private fun createDialog() {
        val customDialogFragment = MyDialogFragment()
        customDialogFragment.show(supportFragmentManager, "my_dialog")
    }

    // implement my dialog listener
    override fun onDialogPositiveClick(dialog: DialogFragment) {
        Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show()
    }
}