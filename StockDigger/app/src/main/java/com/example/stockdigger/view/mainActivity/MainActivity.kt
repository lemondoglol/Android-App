package com.example.stockdigger.view.mainActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockdigger.Injection
import com.example.stockdigger.R
import com.example.stockdigger.model.Stock
import com.example.stockdigger.view.addNewStock.AddStockActivity
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.stock_graph_layout.view.*
import kotlinx.android.synthetic.main.update_stock_layout.view.*
import kotlin.math.min

class MainActivity : AppCompatActivity(), StockItemViewClickListener {

    lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: StocksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(this, AddStockActivity::class.java)
            startActivity(intent)
        }

        /**
         * add breaking line to recyclerView
         * */
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        table_recyclerView.addItemDecoration(decoration)

        setUpViewModel()

        setUpAdapter()

        setUpHeaderClickListener()

        // testing only
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_table -> {
                createAlertDialog(
                    "Are you sure you want to permanently delete this table?"
                ) { viewModel.clearTable() }
                true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.getMainActivityViewModelFactory(this)
        ).get(MainActivityViewModel::class.java)
    }

    private fun setUpAdapter() {
        adapter = StocksAdapter(this)
        // this is required !!!!!
        table_recyclerView.layoutManager = LinearLayoutManager(this)
        table_recyclerView.adapter = adapter

        viewModel.allStocks.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun createAlertDialog(message: String, callBack: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Warning!")
            .setMessage(message)
            .setIcon(R.drawable.ic_delete_forever_black_24dp)
            .setPositiveButton(android.R.string.yes) { _, _ ->
                callBack()
            }.show()
    }

    private fun setUpHeaderClickListener() {
        header_pb.setOnClickListener {
            viewModel.setOrderBy("pb")
        }
        header_pe.setOnClickListener {
            viewModel.setOrderBy("pe")
        }
        header_peg.setOnClickListener {
            viewModel.setOrderBy("peg")
        }
    }

    /**
     * Create custom AlertDialog, and add click listener
     * */
    @SuppressLint("InflateParams")
    override fun onItemClick(stock: Stock) {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.update_stock_layout, null)
        val alertDialog = builder.setView(view).create()
        var newPb = stock.pb
        var newPe = stock.pe
        var newPeg = stock.peg

        view.stock_name.text = stock.id
        view.pb_editText.hint = newPb.toString()
        view.pe_editText.hint = newPe.toString()
        view.peg_editText.hint = newPeg.toString()

        view.confirm_button.setOnClickListener {
            val pb = view.pb_editText.text.toString()
            val pe = view.pe_editText.text.toString()
            val peg = view.peg_editText.text.toString()

            if (pb.isNotBlank() || pe.isNotBlank() || peg.isNotBlank()) {
                var pbList = stock.pbList
                var peList = stock.peList
                var pegList = stock.pegList
                if (pb.isNotBlank()) {
                    pbList = String.format("%s,%s", stock.pbList, pb)
                    newPb = pb.toDouble()
                }
                if (pe.isNotBlank()) {
                    peList = String.format("%s,%s", stock.peList, pe)
                    newPe = pe.toDouble()
                }
                if (peg.isNotBlank()) {
                    pegList = String.format("%s,%s", stock.pegList, peg)
                    newPeg = peg.toDouble()
                }
                val updatedStock = Stock(
                    id = stock.id,
                    pb = newPb,
                    pe = newPe,
                    peg = newPeg,
                    pbList = pbList,
                    peList = peList,
                    pegList = pegList
                )
                viewModel.updateStock(updatedStock)
                Toast.makeText(this, updatedStock.toString(), Toast.LENGTH_LONG).show()
                alertDialog.dismiss()
            }
        }
        view.cancel_button.setOnClickListener { alertDialog.dismiss() }

        alertDialog.show()
    }

    override fun onItemLongClick(stock: Stock) {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.stock_graph_layout, null)
        val alertDialog = builder.setView(view).create()
        view.stock_name_graph.text = stock.id
        val pbList = stock.pbList.split(",")
        val peList = stock.peList.split(",")
        val pegList = stock.pegList.split(",")

        view.pb_graph.title = getString(R.string.PB)
        view.pb_graph.addSeries(makeGraph(pbList))

        view.pe_graph.title = getString(R.string.PE)
        view.pe_graph.addSeries(makeGraph(peList))

        view.peg_graph.title = getString(R.string.PEG)
        view.peg_graph.addSeries(makeGraph(pegList))
        view.peg_graph.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onItemNameClickListener(stock: Stock) {
        createAlertDialog(
            "Are you sure you want to delete ${stock.id}"
        ) { viewModel.deleteStock(stock) }
    }

    private fun makeGraph(lst: List<String>): LineGraphSeries<DataPoint> {
        val max = min(5, lst.size)
        val series = LineGraphSeries<DataPoint>()
        for (i in 0 until max) {
            val point = DataPoint(i.toDouble(), lst[i].toDouble())
            series.appendData(point, true, max)
        }
        return series
    }

}
