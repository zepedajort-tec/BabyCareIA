package com.app.babycareia.di

import com.app.babycareia.data.remote.api.AuthApi
import com.app.babycareia.data.remote.repository.AuthRepositoryImpl
import com.app.babycareia.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi
    ): AuthRepository {
        return AuthRepositoryImpl(api)
    }
}