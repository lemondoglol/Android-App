package com.example.lemon1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        click_button.setOnClickListener {
            display_textView.text = "Thanks for clicking me!"

            Toast.makeText(this, "Hello Toast!", Toast.LENGTH_LONG).show()
        }
    }
}
