package com.garyfimo.marvelapitest.di

import com.garyfimo.marvelapitest.BuildConfig
import com.garyfimo.marvelapitest.data.api.MarvelService
import com.garyfimo.marvelapitest.data.util.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named("base_url")
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Named("public_key")
    fun providePublicKey() = BuildConfig.PUBLIC_KEY

    @Provides
    @Named("private_key")
    fun providePrivateKey() = BuildConfig.PRIVATE_KEY

    @Provides
    fun provideApiKeyInterceptor(@Named("public_key") publicKey: String) =
        ApiKeyInterceptor(publicKey)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply { setLevel(getHttpLoggingInterceptor()) })
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("base_url") baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): MarvelService =
        retrofit.create(MarvelService::class.java)

    private fun getHttpLoggingInterceptor() =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
}