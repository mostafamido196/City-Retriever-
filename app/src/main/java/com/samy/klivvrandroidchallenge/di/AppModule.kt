package com.samy.klivvrandroidchallenge.di

import android.content.Context
import com.samy.klivvrandroidchallenge.data.repository.CityRepositoryImpl
import com.samy.klivvrandroidchallenge.domain.repository.CityRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCityRepository(@ApplicationContext context: Context): CityRepository {
        return CityRepositoryImpl(context)
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}