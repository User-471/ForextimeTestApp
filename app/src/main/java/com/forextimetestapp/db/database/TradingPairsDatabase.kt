package com.forextimetestapp.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.forextimetestapp.db.dao.TradingPairsDao
import com.forextimetestapp.model.TradingPair
import com.forextimetestapp.utils.tradingPairs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(
    TradingPair::class), version = 2)
public abstract class TradingPairsDatabase : RoomDatabase() {

    abstract fun tradingPairsDao(): TradingPairsDao

    companion object {
        @Volatile
        private var INSTANCE: TradingPairsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TradingPairsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TradingPairsDatabase::class.java,
                    "trading_pairs_database")
                    .allowMainThreadQueries()
                    .addCallback(TradingPairsDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }

        private class TradingPairsDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        for(i in tradingPairs) {
                            database.tradingPairsDao().insertTradingPair(TradingPair(title = i.second, code = i.first))
                        }
                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.tradingPairsDao())
                    }
                }
            }
        }

        fun populateDatabase(tradingPairsDao: TradingPairsDao) {
            tradingPairsDao.getAllTradingPairs()
        }
    }
}