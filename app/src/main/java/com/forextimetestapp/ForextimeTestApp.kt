package com.forextimetestapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.forextimetestapp.injection.initKodein
import com.forextimetestapp.injection.module.appModule
import com.forextimetestapp.injection.module.netModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class ForextimeTestApp : Application(), KodeinAware {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    override val kodein: Kodein = Kodein.lazy {

        import(appModule(this@ForextimeTestApp))
        import(netModule())
    }

    override fun onCreate() {
        super.onCreate()

        initKodein(this)
    }
}