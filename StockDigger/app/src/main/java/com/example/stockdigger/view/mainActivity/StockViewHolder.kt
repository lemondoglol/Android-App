package com.example.stockdigger.view.mainActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stockdigger.R
import com.example.stockdigger.model.Stock
import kotlinx.android.synthetic.main.table_item.view.*

class StockViewHolder(
    private val view: View,
    private val itemClickListener: StockItemViewClickListener
) : RecyclerView.ViewHolder(view) {

    fun bind(stock: Stock?) {
        view.item_name.text = stock?.id
        view.item_pb.text = stock?.pb.toString()
        view.item_pe.text = stock?.pe.toString()
        view.item_peg.text = stock?.peg.toString()

        stock?.let { st ->
            view.setOnClickListener {
                itemClickListener.onItemClick(st)
            }
            view.setOnLongClickListener {
                itemClickListener.onItemLongClick(st)
                true
            }
            view.item_name.setOnClickListener {
                itemClickListener.onItemNameClickListener(st)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, clickListener: StockItemViewClickListener): StockViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.table_item, parent, false)
            return StockViewHolder(
                view,
                clickListener
            )
        }
    }

}