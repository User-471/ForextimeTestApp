package com.forextimetestapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.forextimetestapp.model.TradingPair

@Dao
interface TradingPairsDao {

//    @Query("SELECT * from trading_pair ORDER BY id ASC")
    @Query("SELECT * from trading_pair")
    fun getAllTradingPairs(): LiveData<List<TradingPair>>

    @Query("SELECT * from trading_pair WHERE is_favourite = 1")
    fun getFavouriteTradingPairs(): LiveData<List<TradingPair>>

    @Insert
    suspend fun insertTradingPair(tradingPair: TradingPair)

    @Update
    suspend fun updateTradingPair(tradingPair: TradingPair)
}