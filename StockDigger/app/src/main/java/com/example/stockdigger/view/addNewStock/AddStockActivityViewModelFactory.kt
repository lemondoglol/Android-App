package com.example.stockdigger.view.addNewStock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stockdigger.model.StockRepository

class AddStockActivityViewModelFactory(
    private val stockRepo: StockRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddStockActivityViewModel::class.java)) {
            return AddStockActivityViewModel(stockRepo) as T
        } else {
            throw IllegalArgumentException("AddStockActivityViewModelFactory error")
        }
    }
}