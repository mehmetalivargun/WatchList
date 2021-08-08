package com.mehmetalivargun.watchlist.model

import android.content.Context
import androidx.room.Room
import com.mehmetalivargun.watchlist.data.api.TMDBapi
import com.mehmetalivargun.watchlist.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMovieDao(
        db:MovieDatabase
    )=db.movieDao()

    @Singleton
    @Provides
    fun provideTodoDatabase(
        @ApplicationContext context: Context
    )= Room.databaseBuilder(
        context,MovieDatabase::class.java,"watchlist"
    ).build()
}