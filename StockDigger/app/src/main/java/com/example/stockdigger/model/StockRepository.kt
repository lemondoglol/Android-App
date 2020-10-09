package com.example.stockdigger.model

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StockRepository(private val dao: StockDAO) {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun insert(stock: Stock) {
        ioScope.launch {
            dao.insert(stock)
        }
    }

    fun clearTable() {
        ioScope.launch {
            dao.clearTable()
        }
    }

    fun getAllStocksOrderBy(orderBy: String): LiveData<PagedList<Stock>> {
        val factory = when (orderBy) {
            "pb" -> dao.getAllStocksOrderByPB()
            "pe" -> dao.getAllStocksOrderByPE()
            "peg" -> dao.getAllStocksOrderByPEG()
            else -> dao.getAllStocks()
        }
        return LivePagedListBuilder(factory, DATABASE_PAGE_SIZE).build()
    }

    fun updateStock(stock: Stock) {
        ioScope.launch {
            dao.updatePBListQuery(stock)
        }
    }

    fun deleteStock(stock: Stock) {
        ioScope.launch {
            dao.deleteStock(stock)
        }
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 25
    }

}