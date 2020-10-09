package com.example.stockdigger.view.mainActivity

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stockdigger.model.Stock

class StocksAdapter(
    private val itemClickListener: StockItemViewClickListener
) : PagedListAdapter<Stock, RecyclerView.ViewHolder>(STOCK_COMPARATOR) {

    companion object {
        private val STOCK_COMPARATOR = object : DiffUtil.ItemCallback<Stock>() {
            override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockViewHolder.create(
            parent,
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val stock = getItem(position)
        stock?.let {
            (holder as StockViewHolder).bind(it)
        }
    }
}