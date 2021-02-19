package hsj.shahram.film.di.module

import dagger.Module
import dagger.Provides
import hsj.shahram.sampleapi.data.remote.ApiService

import hsj.shahram.sampleapi.util.Const
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {


    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit{

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Const.BASE_URL)
            .build()


    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService
    {

        return retrofit.create(ApiService::class.java)
    }
}