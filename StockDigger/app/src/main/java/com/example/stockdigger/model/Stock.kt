package com.example.stockdigger.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stockdigger.Constants

@Entity(tableName = "stocks")
data class Stock(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "pbList") val pbList: String = "0.0",
    @ColumnInfo(name = "peList") val peList: String = "0.0",
    @ColumnInfo(name = "pegList") val pegList: String = "0.0",
    @ColumnInfo(name = "pb") val pb: Double = Constants.ZERO,
    @ColumnInfo(name = "pe") val pe: Double = Constants.ZERO,
    @ColumnInfo(name = "peg") val peg: Double = Constants.ZERO
)
