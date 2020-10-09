package com.example.stockdigger.view.addNewStock

import androidx.lifecycle.ViewModel
import com.example.stockdigger.model.Stock
import com.example.stockdigger.model.StockRepository

class AddStockActivityViewModel(
    private val stockRepo: StockRepository
) : ViewModel() {

    fun addToDatabase(stock: Stock) {
        stockRepo.insert(stock)
    }

}