package com.example.stockdigger.view.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stockdigger.model.StockRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(
    private val stockRepo: StockRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(
                stockRepo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel in MainActivityViewModelFactory")
    }

}