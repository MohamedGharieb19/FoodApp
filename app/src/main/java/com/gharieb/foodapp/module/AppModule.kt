package com.gharieb.foodapp.module

import android.app.Application
import androidx.room.Room
import com.gharieb.foodapp.api.MealApi
import com.gharieb.foodapp.database.MealDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): MealApi =
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)

    @Provides
    @Singleton
    fun provideDataBase(application: Application): MealDataBase =
        Room.databaseBuilder(application,MealDataBase::class.java,"meal.database").build()


}