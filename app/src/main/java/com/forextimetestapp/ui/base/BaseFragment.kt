package com.forextimetestapp.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.support.closestKodein

abstract class BaseFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    override val kodeinTrigger = KodeinTrigger()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodeinTrigger.trigger()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}