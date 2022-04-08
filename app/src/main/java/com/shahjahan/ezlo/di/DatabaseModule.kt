package com.shahjahan.ezlo.di

import android.content.Context
import androidx.room.Room
import com.shahjahan.ezlo.db.database.DB_NAME
import com.shahjahan.ezlo.db.database.EzloDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideMainDao(appDatabase: EzloDatabase): com.shahjahan.ezlo.db.dao.MainDao {
        return appDatabase.mainDao
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): EzloDatabase {
        return Room.databaseBuilder(
            appContext,
            EzloDatabase::class.java,
            DB_NAME
        ).build()
    }
}