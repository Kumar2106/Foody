package cart_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface cart_dao {
    @Insert
    fun insert_menu_item(menuitem: menuitem)

    @Delete
    fun delet_menu_item(menuitem: menuitem)

    @Query("SELECT * FROM menuitem")
    fun get_all_menu_item(): List<menuitem>
    @Query("SELECT * FROM menuitem WHERE menu_id =:menu_Id")
    fun get_menu_item_by_id(menu_Id:String):menuitem
    @Query("DELETE FROM menuitem")
    fun deletall()


}