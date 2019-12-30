package com.forextimetestapp.injection

import android.app.Application
import com.forextimetestapp.injection.module.appModule
import com.forextimetestapp.injection.module.netModule
import org.kodein.di.Kodein

lateinit var di: Kodein

fun initKodein(app: Application) {

    di = Kodein {
        import(appModule(app))
        import(
            netModule()
        )
    }
}