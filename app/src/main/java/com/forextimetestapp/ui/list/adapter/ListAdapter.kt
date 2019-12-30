package com.forextimetestapp.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forextimetestapp.R
import com.forextimetestapp.model.TradingPair

class ListAdapter(var list: List<TradingPair>,
                  val onItemClickListener: (TradingPair) -> Unit,
                  val onFavouriteClickListener: (TradingPair) -> Unit): RecyclerView.Adapter<ListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_trading_pair, parent, false)
        return ListHolder(v)
    }

    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holderDetail: ListHolder, position: Int) = holderDetail.bind(
        list[position],
        onItemClickListener,
        onFavouriteClickListener)

    fun updateData(list: List<TradingPair>) {
        this.list = list
        notifyDataSetChanged()
    }
}