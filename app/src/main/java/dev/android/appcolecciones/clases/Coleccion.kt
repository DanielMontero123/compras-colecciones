package dev.android.appcolecciones.clases

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "coleccion")
class Coleccion (
    var nombre:String,
    var detalle:String,
    val precio:Double,
    val imagen:Int,
    @PrimaryKey(autoGenerate = true)
    var idMenu:Int = 0): Serializable