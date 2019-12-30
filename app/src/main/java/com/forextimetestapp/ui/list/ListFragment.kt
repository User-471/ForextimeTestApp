package com.forextimetestapp.ui.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : BaseFragment() {

    // В приложении была заложена архитектура MVVM, так как эта архитектура хорошо подходит для приложения, в котором нужно отображать
    // большой массив данных, без сложной логики взаимодействия с ними. В нашем случае хранение торговых пар и отображение графика их соотношения
    // с помощью постоянно приходящей информации с сервера.

    private lateinit var tradingPairsViewModel: TradingPairsViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tradingPairsViewModel = ViewModelProviders.of(this).get(TradingPairsViewModel::class.java)

        initRecycler()
        observeTradingPairs()
        setOnTextChanged()
    }

    private fun setOnTextChanged() {
        search_field.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tradingPairsViewModel.tradingPairs.value?.let {
                    if(s?.length == 0) {
                        adapter.updateData(it)
                    }
                    else {
                        val list = it.filter { tradingPair -> tradingPair.title.toLowerCase().contains(s.toString().toLowerCase()) }
                        adapter.updateData(list)
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initRecycler() {
        adapter = ListAdapter(arrayListOf(), { onItemClicked(it) }, { onFavouriteClicked(it) })
        rv_list.layoutManager = LinearLayoutManager(activity!!)
        rv_list.adapter = adapter
    }

    private fun observeTradingPairs() {

        //Для хранения торговых пар

        tradingPairsViewModel.tradingPairs.observe(activity!!, Observer {
            it?.let {
                adapter.updateData(it)
            }
        })
    }

    private fun onItemClicked(tradingPair: TradingPair) {
        if (view != null) {
            val args = Bundle()
            args.putParcelable(KEY_TRADING_PAIR, tradingPair)
            findNavController().navigate(R.id.action_listFragment_to_tradingPairFragment, args)
        }
    }

    private fun onFavouriteClicked(tradingPair: TradingPair) {
        when(tradingPair.isFavourite) {
            true -> {
                tradingPair.isFavourite = false
            }
            false -> {
                tradingPair.isFavourite = true
            }
        }

        tradingPairsViewModel.updateTradingPair(tradingPair)
    }


}
