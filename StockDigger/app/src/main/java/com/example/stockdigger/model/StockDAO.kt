package com.example.stockdigger.model

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface StockDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stock: Stock)

    @Query("SELECT * FROM stocks order by pb ASC")
    fun getAllStocks(): DataSource.Factory<Int, Stock>

    // note!!! you can't pass string as parameter into Dao
    @Query("SELECT * FROM stocks order by pb ASC")
    fun getAllStocksOrderByPB(): DataSource.Factory<Int, Stock>

    @Query("SELECT * FROM stocks order by pe ASC")
    fun getAllStocksOrderByPE(): DataSource.Factory<Int, Stock>

    @Query("SELECT * FROM stocks order by peg ASC")
    fun getAllStocksOrderByPEG(): DataSource.Factory<Int, Stock>

    @Query("delete from stocks")
    suspend fun clearTable()

    @Update
    suspend fun updatePBListQuery(stock: Stock)

    @Delete
    suspend fun deleteStock(stock: Stock)
}