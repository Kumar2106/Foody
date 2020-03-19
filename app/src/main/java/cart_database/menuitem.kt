package cart_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menuitem")
class menuitem (
    @PrimaryKey val menu_id:String,
    @ColumnInfo(name = "menu_item_name") val menu_item_name:String,
    @ColumnInfo(name = "menu_item_cost")val menu_item_cost:String,
    @ColumnInfo(name = "menu_item_restaurants_id")val menu_item_restaurants_id:String
)