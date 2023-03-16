package com.adi.magicspacex.di

import com.adi.magicspacex.repository.SpacexRepository
import com.adi.magicspacex.repository.remote.SpacexService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(spacexService: SpacexService): SpacexRepository =
        SpacexRepository(spacexService)

}