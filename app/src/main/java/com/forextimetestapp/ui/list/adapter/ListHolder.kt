package com.forextimetestapp.ui.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.forextimetestapp.R
import com.forextimetestapp.model.TradingPair
import kotlinx.android.synthetic.main.item_trading_pair.view.*

class ListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(data: TradingPair,
             onItemClickListener: (TradingPair) -> Unit,
             onFavouriteClickListener: (TradingPair) -> Unit) {

        itemView.setOnClickListener { onItemClickListener(data) }
        itemView.iv_favourite.setOnClickListener { onFavouriteClickListener(data) }

        itemView.tv_trading_pair.text = data.title

        when(data.isFavourite) {
            true -> {
                itemView.iv_favourite.setImageResource(R.drawable.ic_favorite_24dp)
            }
            false -> {
                itemView.iv_favourite.setImageResource(R.drawable.ic_favorite_border_24dp)
            }
        }
    }
}