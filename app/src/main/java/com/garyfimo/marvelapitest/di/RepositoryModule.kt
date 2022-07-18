package com.garyfimo.marvelapitest.di

import com.garyfimo.marvelapitest.data.api.MarvelService
import com.garyfimo.marvelapitest.data.repository.MarvelRepositoryImpl
import com.garyfimo.marvelapitest.data.util.HashGenerator
import com.garyfimo.marvelapitest.domain.character.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMarvelRepository(
        marvelService: MarvelService,
        hashGenerator: HashGenerator,
        @Named("public_key") publicKey: String,
        @Named("private_key") privateKey: String
    ): MarvelRepository =
        MarvelRepositoryImpl(marvelService, hashGenerator, publicKey, privateKey)
}