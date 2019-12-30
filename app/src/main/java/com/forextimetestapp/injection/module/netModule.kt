package com.forextimetestapp.injection.module

import com.forextimetestapp.api.interceptors.LoggingInterceptor
import com.forextimetestapp.api.service.ForextimeTestService
import com.forextimetestapp.utils.BASE_URL
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun netModule() = Kodein.Module {
    bind<OkHttpClient>() with singleton { provideOkHttpClient() }
    bind<Retrofit>("1") with singleton { provideRestRetrofit(instance()) }
    bind<ForextimeTestService>() with singleton { instance<Retrofit>("1").create(ForextimeTestService::class.java) }
}

private fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(LoggingInterceptor())
        .build()

private fun provideRestRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()