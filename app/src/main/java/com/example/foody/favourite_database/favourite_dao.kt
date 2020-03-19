package com.example.foody.favourite_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cart_database.menuitem

@Dao
interface favourite_dao {
    @Insert
    fun insert(favouriteEntity: favourite_entity)

    @Delete
    fun delet(favouriteEntity: favourite_entity)

    @Query("SELECT * FROM favourite_table WHERE resid =:res_id")
    fun favourit_by_id(res_id: String): favourite_entity

    @Query("SELECT * FROM favourite_table")
    fun get_all_favourite():List<favourite_entity>


}