package com.assignment.di

import android.content.Context
import com.assignment.data.local.AppDatabase
import com.assignment.data.local.MatchRequestDao
import com.assignment.data.local.UserDao
import com.assignment.data.remote.UserDataService
import com.assignment.data.remote.UserRemoteDataSource
import com.assignment.data.repository.UserDataRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideUserDataService(retrofit: Retrofit): UserDataService =
        retrofit.create(UserDataService::class.java)

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(userDataService: UserDataService) =
        UserRemoteDataSource(userDataService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()


    @Singleton
    @Provides
    fun provideMatchRequestDao(db: AppDatabase) = db.matchRequestDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: UserRemoteDataSource,
        localDataSource: UserDao,
        matchRequestDao: MatchRequestDao
    ) =
        UserDataRepository(remoteDataSource, localDataSource, matchRequestDao)

}