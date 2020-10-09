package com.example.stockdigger.view.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.stockdigger.model.Stock
import com.example.stockdigger.model.StockRepository

class MainActivityViewModel(private val stockRepo: StockRepository) : ViewModel() {

    private val orderBy = MutableLiveData<String>()

    init {
        orderBy.postValue("")
    }

    val allStocks: LiveData<PagedList<Stock>>
        get() = this._allStocks
    private val _allStocks: LiveData<PagedList<Stock>> = Transformations.switchMap(orderBy) {
        stockRepo.getAllStocksOrderBy(it)
    }

    fun clearTable() {
        stockRepo.clearTable()
    }

    fun setOrderBy(order: String) {
        orderBy.postValue(order)
    }

    fun updateStock(stock: Stock) {
        stockRepo.updateStock(stock)
    }

    fun deleteStock(stock: Stock) {
        stockRepo.deleteStock(stock)
    }

}