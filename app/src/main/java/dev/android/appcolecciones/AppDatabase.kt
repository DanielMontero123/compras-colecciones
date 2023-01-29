package dev.android.appcolecciones

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.android.appcolecciones.clases.Coleccion
import dev.android.appcolecciones.interfaces.ColeccionDAO

@Database(entities = [Coleccion::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun menus():ColeccionDAO

    companion object{

        @Volatile
        private var INSTANCIA:AppDatabase? = null

        fun getDatabase(contexto: Context):AppDatabase {
            val tempInstancia = INSTANCIA
            if(tempInstancia != null){
                return tempInstancia
            }

            synchronized(this){
                val instancia = Room.databaseBuilder(
                    contexto.applicationContext,
                    AppDatabase::class.java,
                    "app_colecciones"
                ).build()

                INSTANCIA = instancia

                return instancia
            }
        }

    }

}