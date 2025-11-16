package com.app.babycare.di

import android.content.Context
import com.app.babycare.data.auth.AuthInterceptor
import com.app.babycare.data.auth.EncryptedTokenStore
import com.app.babycare.data.auth.TokenStore
import com.app.babycare.remote.api.AuthApi
import com.app.babycare.remote.api.BabyApi
import com.app.babycare.remote.api.ParentApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://reimagined-journey-pjq4v4gqp9v42xxw-8000.app.github.dev/"

    @Provides
    @Singleton
    fun provideTokenStore(@ApplicationContext context: Context): TokenStore {
        return EncryptedTokenStore(context)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenStore: TokenStore): AuthInterceptor {
        return AuthInterceptor(tokenStore)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor) // añade Authorization automáticamente
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideParentsApi(retrofit: Retrofit): ParentApi =
        retrofit.create(ParentApi::class.java)

    @Provides
    @Singleton
    fun provideBabyApi(retrofit: Retrofit): BabyApi {
        return retrofit.create(BabyApi::class.java)
    }
}