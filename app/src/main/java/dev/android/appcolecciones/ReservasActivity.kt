package dev.android.appcolecciones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.android.appcolecciones.databinding.ActivityReservasBinding

class ReservasActivity : AppCompatActivity(), FragmentColeccionesDos.ComunicadorFragmentos {

    lateinit var binding: ActivityReservasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)

        binding = ActivityReservasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVisualizar.setOnClickListener {
            val transaccion = supportFragmentManager.beginTransaction()
            val fragmento = FragmentColeccionesUno()
            transaccion.replace(R.id.contenedor,fragmento)
            transaccion.addToBackStack(null)
            transaccion.commit()
        }

        binding.btnReservar.setOnClickListener {
            val transaccion = supportFragmentManager.beginTransaction()
            val fragmento = FragmentColeccionesDos()
            transaccion.replace(R.id.contenedor,fragmento)
            transaccion.addToBackStack(null)
            transaccion.commit()
        }

        binding.btnInformacion.setOnClickListener {
            val transaccion = supportFragmentManager.beginTransaction()
            val fragmento = FragmentColeccionesTres()
            transaccion.replace(R.id.contenedor,fragmento)
            transaccion.addToBackStack(null)
            transaccion.commit()
        }
    }

    override fun enviarDatos(dato: String, apellido: String) {
        binding.txtNombreReservas.setText(dato + " " + apellido)
    }

    override fun enviarTelefono(telefono: String) {
        binding.txtTelefonoReservas.setText(telefono)
    }

    override fun enviarFigura(figura: String) {
        binding.txtFiguraReservas.setText(figura)
    }


}