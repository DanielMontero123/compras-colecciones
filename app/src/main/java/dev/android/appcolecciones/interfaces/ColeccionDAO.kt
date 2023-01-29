package dev.android.appcolecciones.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.android.appcolecciones.clases.Coleccion

@Dao
interface ColeccionDAO {

    @Query("SELECT * FROM coleccion")
    fun obtenerMenus():LiveData<List<Coleccion>>

    @Query("SELECT * FROM coleccion WHERE idMenu = :id")
    fun obtenerMenu(id:Int):LiveData<Coleccion>

    @Insert
    fun insertarMenu(vararg menu:Coleccion):List<Long>

    @Update
    fun actualizarMenu(menu:Coleccion)

    @Delete
    fun eliminarMenu(menu:Coleccion)
}