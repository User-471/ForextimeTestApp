package com.forextimetestapp.db.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.forextimetestapp.db.dao.TradingPairsDao
import com.forextimetestapp.model.TradingPair

class TradingPairsRepository(private val tradingPairsDao: TradingPairsDao) {

    val tradingPairs: LiveData<List<TradingPair>> = tradingPairsDao.getAllTradingPairs()
    val favouriteTradingPairs: LiveData<List<TradingPair>> = tradingPairsDao.getFavouriteTradingPairs()

    @WorkerThread
    suspend fun insertTradingPair(tradingPair: TradingPair) {
        tradingPairsDao.insertTradingPair(tradingPair)
    }

    @WorkerThread
    suspend fun updateTradingPair(tradingPair: TradingPair) {
        tradingPairsDao.updateTradingPair(tradingPair)
    }
}