package com.mrozon.core_impl.db

import android.content.Context
import androidx.room.Room
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.db.HealthDiaryDatabaseContract
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

private const val DATABASE_NAME = "HealthDiaryDB"

@Module
class DatabaseModule {

    @Provides
    @Reusable
    fun provideHabitsDao(healthDiaryDatabaseContract: HealthDiaryDatabaseContract): HealthDiaryDao {
        return healthDiaryDatabaseContract.healthDiaryDao()
    }

    @Provides
    @Singleton
    fun provideHabitsDatabase(context: Context): HealthDiaryDatabaseContract {
        return Room.databaseBuilder(
            context,
            HabitsDatabase::class.java, DATABASE_NAME
        ).build()
    }
}