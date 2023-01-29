package dev.android.appcolecciones.clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import dev.android.appcolecciones.R
import dev.android.appcolecciones.ControladorImagen
import kotlinx.android.synthetic.main.fila_coleccion.view.*

class ColeccionAdapter (
    private val contexto: Context,
    private val listaMenus:List<Coleccion>): ArrayAdapter<Coleccion>(contexto, 0, listaMenus) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(contexto).inflate(R.layout.fila_coleccion, parent, false)
        val menu = listaMenus[position]
        layout.txtFilaMenu.setText(menu.nombre)
        layout.txtFilaPrecio.setText("$ ${menu.precio}")
        val uriImagen = ControladorImagen.obtenerUriImagen(contexto,menu.idMenu.toLong())
        layout.imgFilaMenu.setImageURI(uriImagen)

        return layout
    }
}