package com.example.android_project_simple_ecommerce.di

import android.content.Context
import androidx.room.Room
import com.example.android_project_simple_ecommerce.data.local.CartDao
import com.example.android_project_simple_ecommerce.data.local.CartDatabase
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
    fun provideDatabase(@ApplicationContext app: Context): CartDatabase =
        Room.databaseBuilder(app, CartDatabase::class.java, "cart_db").build()

    @Provides
    fun provideCartDao(db: CartDatabase): CartDao = db.cartDao()
}
