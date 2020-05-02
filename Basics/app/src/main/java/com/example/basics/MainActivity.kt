package com.example.basics

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // display toast
        findViewById<Button>(R.id.toast_button).setOnClickListener {
            displayToasts()
        }

        // display snackBar
        findViewById<Button>(R.id.snackbar_button).setOnClickListener {
            displaySnackBar()
        }
    }

    fun displayToasts() {
        Toast.makeText(this, "Hello Toast!", Toast.LENGTH_LONG).show()
    }

    fun displaySnackBar() {
        val layout: ConstraintLayout = findViewById(R.id.my_constraint_layout)
        Snackbar.make(layout, "Hello Snack Bar!", Snackbar.LENGTH_LONG)
            .setAction("Close") {
                displayToasts()
            }
            .setActionTextColor(Color.CYAN)
            .show()
    }
}
