package dev.android.appcolecciones

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dev.android.appcolecciones.clases.Coleccion
import dev.android.appcolecciones.clases.ColeccionAdapter
import kotlinx.android.synthetic.main.activity_colecciones.*

data class MenuColecciones(var nombre:String, var detalle:String)

class ColeccionesActivity : AppCompatActivity() {

    val menuColecciones = arrayListOf(
        MenuColecciones("Iron Man a Escala 1/6th", "Versión con su traje dañado durante la batalla en la película Endgame"),
        MenuColecciones("Spiderman a Escala 1/6th", "Versión de la película Spiderman: Far From Home"),
        MenuColecciones("Darth Vader a Escala 1/4th", "Versión de la película El Regreso del Jedi")
    )

    private lateinit var database: AppDatabase
    private lateinit var menu:Coleccion
    private lateinit var menuLiveData: LiveData<Coleccion>
    private val EDITAR = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colecciones)
        // crearLista()
        click()
        cargarListaColecciones()

    }

    private fun crearLista(){

        val adaptador = Adaptador(applicationContext, menuColecciones)
        lstMenu.adapter = adaptador
    }

    private class Adaptador(context: Context, datos:ArrayList<MenuColecciones>): BaseAdapter(){

        val datosMenu = datos
        val ctx = context

        private inner class ViewHolder(){
            internal var nombre: TextView?=null
            internal var detalle: TextView?=null
            internal var flecha: ImageView?=null
        }

        override fun getCount(): Int {
            return datosMenu.size
        }

        override fun getItem(position: Int): Any {
            return datosMenu[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, row: View?, parent: ViewGroup?): View {
            var viewHolder:ViewHolder
            var rowView = row

            if(rowView == null){
                viewHolder = ViewHolder()
                val inflater = LayoutInflater.from(ctx)
                rowView = inflater.inflate(R.layout.fila_personalizada, parent, false)

                viewHolder.nombre = rowView.findViewById(R.id.txtNombre) as TextView
                viewHolder.detalle = rowView.findViewById(R.id.txtDetalle) as TextView
                viewHolder.flecha = rowView.findViewById(R.id.imgFlecha) as ImageView
                rowView.tag = viewHolder

            } else {
                viewHolder = rowView.tag as ViewHolder
            }

            viewHolder.nombre!!.setText(datosMenu.get(position).nombre)
            viewHolder.detalle!!.setText(datosMenu.get(position).detalle)
            viewHolder.flecha!!.setImageResource(R.drawable.ic_flecha_derecha)

            return rowView!!
        }

    }

    private fun click(){
        btnNuevaColeccion.setOnClickListener {
            val intent = Intent(applicationContext, NuevaColeccionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarListaColecciones(){
        var listaColecciones = emptyList<Coleccion>()
        val database = AppDatabase.getDatabase(this)

        database.menus().obtenerMenus().observe(this, Observer {
            listaColecciones = it
            val adaptador = ColeccionAdapter(this,listaColecciones)
            lstMenu.adapter = adaptador
        })

        lstMenu.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetalleColeccionActivity::class.java)
            intent.putExtra("id", listaColecciones[position].idMenu)
            startActivity(intent)
        }
    }
}