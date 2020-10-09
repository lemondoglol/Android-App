package com.example.stockdigger

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.stockdigger.model.StockDatabase
import com.example.stockdigger.model.StockRepository
import com.example.stockdigger.view.mainActivity.MainActivityViewModelFactory
import com.example.stockdigger.view.addNewStock.AddStockActivityViewModelFactory

object Injection {

    fun getMainActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        val stockRepository = getStockRepository(context)
        return MainActivityViewModelFactory(
            stockRepository
        )
    }

    fun getAddStockActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        val stockRepository = getStockRepository(context)
        return AddStockActivityViewModelFactory(stockRepository)
    }

    private fun getStockRepository(context: Context): StockRepository {
        val database = StockDatabase.getInstance(context)
        return StockRepository(database.dao())
    }
}