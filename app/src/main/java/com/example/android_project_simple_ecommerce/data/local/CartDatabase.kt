package com.example.android_project_simple_ecommerce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_project_simple_ecommerce.data.model.CartItem

@Database(entities = [CartItem::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
