package cart_database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [menuitem::class],version = 1)
abstract class database: RoomDatabase() {
    abstract fun cartdao():cart_dao

}