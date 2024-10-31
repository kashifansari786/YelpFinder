package com.kashif.yelpfinder.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kashif.yelpfinder.data.local.YelpDao
import com.kashif.yelpfinder.data.local.YelpDatabase
import com.kashif.yelpfinder.data.local.YelpTypeConverter
import com.kashif.yelpfinder.data.manager.LocalUserManagerImpl
import com.kashif.yelpfinder.data.manager.PreferenceClass
import com.kashif.yelpfinder.data.remote.YelpAPI
import com.kashif.yelpfinder.data.repository.YelpRepositoryImpl
import com.kashif.yelpfinder.domain.manager.LocalUserManager
import com.kashif.yelpfinder.domain.repository.YelpRepository
import com.kashif.yelpfinder.domain.useCases.DeleteArticle
import com.kashif.yelpfinder.domain.useCases.GetSearchData
import com.kashif.yelpfinder.domain.useCases.SelectArticle
import com.kashif.yelpfinder.domain.useCases.SelectArticles
import com.kashif.yelpfinder.domain.useCases.UpdateArticle
import com.kashif.yelpfinder.domain.useCases.YelpUseCases
import com.kashif.yelpfinder.domain.useCases.app_entry.AppEntryUseCases
import com.kashif.yelpfinder.domain.useCases.app_entry.ReadAppEntry
import com.kashif.yelpfinder.domain.useCases.app_entry.SaveAppEntry
import com.kashif.yelpfinder.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */

// AppModule provides dependencies for dependency injection with Singleton scope
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides LocalUserManager instance with Application context
    @Provides
    @Singleton
    fun provideLocalUserManager(context: Application): LocalUserManager = LocalUserManagerImpl(context)

    // Provides OkHttpClient instance with headers for Yelp API
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "Bearer " + Constant.API_KEY) // Auth header with API key
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    // Provides YelpAPI instance using Retrofit with OkHttpClient and Gson converter
    @Provides
    @Singleton
    fun provideYelpApi(): YelpAPI {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(YelpAPI::class.java)
    }

    // Provides AppEntryUseCases instance, configured with read and save entry use cases
    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    // Provides YelpRepository implementation with YelpAPI and YelpDao instances
    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: YelpAPI, yelpDao: YelpDao): YelpRepository = YelpRepositoryImpl(newsApi, yelpDao)

    // Provides YelpUseCases configured with various use cases for YelpRepository
    @Provides
    @Singleton
    fun provideNewsUseCases(yelpRepository: YelpRepository): YelpUseCases {
        return YelpUseCases(
            getSearchData = GetSearchData(yelpRepository),
            updateArticle = UpdateArticle(yelpRepository),
            deleteArticle = DeleteArticle(yelpRepository),
            selectArticles = SelectArticles(yelpRepository),
            selectArticle = SelectArticle(yelpRepository)
        )
    }

    // Provides PreferenceClass instance for managing location data with Context
    @Provides
    @Singleton
    fun provideLocationPreferences(@ApplicationContext context: Context): PreferenceClass {
        return PreferenceClass(context)
    }

    // Provides YelpDatabase instance for Room database with fallback strategy and type converter
    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): YelpDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = YelpDatabase::class.java,
            name = Constant.YELP_DATABASE_NAME
        ).addTypeConverter(YelpTypeConverter()).fallbackToDestructiveMigration().build()
    }

    // Provides YelpDao instance from YelpDatabase
    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: YelpDatabase): YelpDao = newsDatabase.yelpDao
}