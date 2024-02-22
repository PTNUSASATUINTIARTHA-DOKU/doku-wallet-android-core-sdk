package com.dokuwallet.coresdk.di

import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.dokuwallet.coresdk.BuildConfig
import com.dokuwallet.coresdk.data.DataRepository
import com.dokuwallet.coresdk.data.local.LocalDataSource
import com.dokuwallet.coresdk.data.local.db.AppDatabase
import com.dokuwallet.coresdk.data.network.api.VPApiService
import com.dokuwallet.coresdk.data.network.api.WalletApiService
import com.dokuwallet.coresdk.data.network.datasource.VpDataSource
import com.dokuwallet.coresdk.data.network.datasource.WalletDataSource
import com.dokuwallet.coresdk.domain.repository.IDataRepository
import com.dokuwallet.coresdk.domain.usecase.DataInteractor
import com.dokuwallet.coresdk.domain.usecase.VPDataUseCase
import com.dokuwallet.coresdk.domain.usecase.WalletDataUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.util.concurrent.TimeUnit

internal val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_VERIFICATION_PAGE_SDK)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(VPApiService::class.java)
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_WALLET_SDK)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(WalletApiService::class.java)
    }

    single {
        Glide.get(get())
            .registry
            .prepend(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(get()))
    }
}

internal val databaseModule = module {
    factory { get<AppDatabase>().appDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            BuildConfig.DB_NAME_DOKU_SDK
        ).fallbackToDestructiveMigration().build()
    }
}

internal val repositoryModule = module {
    single { VpDataSource(get()) }
    single { WalletDataSource(get()) }
    single { LocalDataSource(get()) }
    single<IDataRepository> {
        DataRepository(get(), get(), get())
    }
}

internal val useCaseModule = module {
    factory<VPDataUseCase> { DataInteractor(get()) }
    factory<WalletDataUseCase> { DataInteractor(get()) }
}

object CoreModule {
    fun getAllModules(): MutableList<Module> = mutableListOf(
        networkModule,
        databaseModule,
        repositoryModule,
        useCaseModule
    )
}