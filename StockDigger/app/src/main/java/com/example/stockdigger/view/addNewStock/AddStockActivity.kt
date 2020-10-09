package com.example.stockdigger.view.addNewStock

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.stockdigger.Injection
import com.example.stockdigger.R
import com.example.stockdigger.model.Stock
import com.example.stockdigger.Constants
import kotlinx.android.synthetic.main.activity_add_stock.*

class AddStockActivity : AppCompatActivity() {

    private lateinit var viewModel: AddStockActivityViewModel

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stock)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setUpViewModel()
        setButtonListener()

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.getAddStockActivityViewModelFactory(this)
        ).get(AddStockActivityViewModel::class.java)
    }

    private fun setButtonListener() {
        add_button.setOnClickListener {
            if (stock_tag.text.toString().isNotBlank()) {
                val stockTag = stock_tag.text.toString()
                val pbEditText = pb.text.toString()
                val peEditText = pe.text.toString()
                val pegEditText = peg.text.toString()
                val pb = if (pbEditText.isNotBlank()) pbEditText.toDouble() else Constants.ZERO
                val pe = if (peEditText.isNotBlank()) peEditText.toDouble() else Constants.ZERO
                val peg = if (pegEditText.isNotBlank()) pegEditText.toDouble() else Constants.ZERO
                val stock = Stock(
                    pbList = pb.toString(),
                    peList = pe.toString(),
                    pegList = peg.toString(),
                    id = stockTag,
                    pb = pb,
                    pe = pe,
                    peg = peg
                )
                viewModel.addToDatabase(stock)
                resetInputField()
                Toast.makeText(this, String.format("%s added to the Database", stockTag), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "You need to input Stock Tag", Toast.LENGTH_LONG).show()
            }
        }

        cancel_button.setOnClickListener {
            finish()
        }
    }

    private fun resetInputField() {
        stock_tag.setText("")
        pb.setText("")
        pe.setText("")
        peg.setText("")
    }
}
