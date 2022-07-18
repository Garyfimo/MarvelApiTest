package com.garyfimo.marvelapitest.di

import com.garyfimo.marvelapitest.data.repository.MarvelRepositoryFakeImpl
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class FakeRepositoryModule {

    @Singleton
    @Provides
    fun providesMarvelRepositoryFakeImpl(): MarvelRepository =
        MarvelRepositoryFakeImpl()
}