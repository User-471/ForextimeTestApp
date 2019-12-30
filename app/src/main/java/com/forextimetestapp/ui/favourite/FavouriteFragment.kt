package com.forextimetestapp.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.forextimetestapp.R
import com.forextimetestapp.db.viewmodel.TradingPairsViewModel
import com.forextimetestapp.model.TradingPair
import com.forextimetestapp.ui.base.BaseFragment
import com.forextimetestapp.ui.list.adapter.ListAdapter
import com.forextimetestapp.utils.KEY_TRADING_PAIR
import kotlinx.android.synthetic.main.fragment_favourite.*


class FavouriteFragment : BaseFragment() {

    private lateinit var tradingPairsViewModel: TradingPairsViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tradingPairsViewModel = ViewModelProviders.of(this).get(TradingPairsViewModel::class.java)

        initRecycler()
        observeTradingPairs()
    }

    private fun initRecycler() {
        adapter = ListAdapter(arrayListOf(), { onItemClicked(it) }, { onFavouriteClicked(it) })
        rv_favourite.layoutManager = LinearLayoutManager(activity!!)
        rv_favourite.adapter = adapter
    }

    private fun observeTradingPairs() {
        tradingPairsViewModel.favouriteTradingPairs.observe(activity!!, Observer {
            if(view != null) {
                if (it != null) {
                    rv_favourite.visibility = View.VISIBLE
                    tv_empty_favourite.visibility = View.GONE

                    adapter.updateData(it)

                } else {
                    rv_favourite.visibility = View.GONE
                    tv_empty_favourite.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun onItemClicked(tradingPair: TradingPair) {
        if (view != null) {
            val args = Bundle()
            args.putParcelable(KEY_TRADING_PAIR, tradingPair)
            findNavController().navigate(R.id.action_favouriteFragment_to_tradingPairFragment, args)
        }
    }

    private fun onFavouriteClicked(tradingPair: TradingPair) {
        tradingPair.isFavourite = false

        tradingPairsViewModel.updateTradingPair(tradingPair)
    }
}
