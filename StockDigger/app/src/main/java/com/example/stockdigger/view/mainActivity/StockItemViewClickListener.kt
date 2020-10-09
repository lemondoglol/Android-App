package com.example.stockdigger.view.mainActivity

import com.example.stockdigger.model.Stock

interface StockItemViewClickListener {
    fun onItemClick(stock: Stock)

    fun onItemLongClick(stock: Stock)

    fun onItemNameClickListener(stock: Stock)
}