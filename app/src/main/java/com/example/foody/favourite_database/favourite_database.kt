package com.example.foody.favourite_database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [favourite_entity::class],version = 1)
abstract class favourite_database:RoomDatabase(){
abstract fun favourite_Dao():favourite_dao

}