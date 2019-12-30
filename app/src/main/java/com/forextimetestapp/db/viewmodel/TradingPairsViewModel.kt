package com.forextimetestapp.db.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.forextimetestapp.db.database.TradingPairsDatabase
import com.forextimetestapp.db.repository.TradingPairsRepository
import com.forextimetestapp.model.TradingPair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TradingPairsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TradingPairsRepository
    val tradingPairs: LiveData<List<TradingPair>>
    val favouriteTradingPairs: LiveData<List<TradingPair>>

    init {
        val tradingPairsDao = TradingPairsDatabase.getDatabase(application, viewModelScope).tradingPairsDao()
        repository = TradingPairsRepository(tradingPairsDao)
        tradingPairs = repository.tradingPairs
        favouriteTradingPairs = repository.favouriteTradingPairs
    }

    fun insertTradingPair(tradingPair: TradingPair) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTradingPair(tradingPair)
    }

    fun updateTradingPair(tradingPair: TradingPair) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTradingPair(tradingPair)
    }
}