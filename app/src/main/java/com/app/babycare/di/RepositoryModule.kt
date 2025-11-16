package com.app.babycare.di

import com.app.babycare.remote.api.AuthApi
import com.app.babycare.remote.repository.AuthRepositoryImpl
import com.app.babycare.domain.repository.AuthRepository
import com.app.babycare.domain.repository.BabyRepository
import com.app.babycare.domain.repository.DevTipsRepository
import com.app.babycare.domain.repository.ParentRepository
import com.app.babycare.remote.api.BabyApi
import com.app.babycare.remote.api.DevTipsApi
import com.app.babycare.remote.api.ParentApi
import com.app.babycare.remote.repository.BabyRepositoryImpl
import com.app.babycare.remote.repository.DevTipsRepositoryImpl
import com.app.babycare.remote.repository.ParentRepositoryImpl
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

    @Provides
    @Singleton
    fun provideBabyRepository(
        api: BabyApi
    ): BabyRepository {
        return BabyRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideParentRepositoryImpl(
        api: ParentApi
    ): ParentRepository {
        return ParentRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDevTipsRepository(
        api: DevTipsApi
    ): DevTipsRepository {
        return DevTipsRepositoryImpl(api)
    }
}