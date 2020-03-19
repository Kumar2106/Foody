package com.example.foody.favourite_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourite_table")
class favourite_entity(
    @PrimaryKey val resid: String,
    @ColumnInfo(name = "Favourit_res_img") val image: String,
    @ColumnInfo(name = "Favourit_res_name") val restaurants_name: String,
    @ColumnInfo(name = "Favourite_res_Cost") val restuarants_Cost: String,
    @ColumnInfo(name = "Favourite_res_rating") val restaurants_rating: String

)