package com.forextimetestapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "trading_pair")
data class TradingPair(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
                       @ColumnInfo(name = "title") val title: String,
                       @ColumnInfo(name = "code") val code: String,
                       @ColumnInfo(name = "is_favourite") var isFavourite: Boolean = false): Parcelable