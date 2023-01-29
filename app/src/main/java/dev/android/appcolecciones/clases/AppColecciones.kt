package dev.android.appreservas.clases

import android.app.Application

class AppColecciones:Application() {
    companion object{
        lateinit var preferencias:Preferencias
    }

    override fun onCreate() {
        super.onCreate()
        preferencias = Preferencias(applicationContext)
    }
}