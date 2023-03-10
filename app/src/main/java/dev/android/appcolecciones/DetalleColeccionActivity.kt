package dev.android.appcolecciones

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detalle_coleccion.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleColeccionActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var menu: dev.android.appcolecciones.clases.Coleccion
    private lateinit var menuLiveData: LiveData<dev.android.appcolecciones.clases.Coleccion>
    private val EDITAR = 2

    private var uriImagen: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_coleccion)
        cargarColeccion()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.opEliminarColeccion -> {
                eliminarMenu()
                true
            }
            R.id.opEditarColeccion -> {
                editarMenu()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.acciones_menu, menu)
        return true
    }


    private fun cargarColeccion(){
        database = AppDatabase.getDatabase(this)
        val idMenu = intent.getIntExtra("id", 0)

        menuLiveData = database.menus().obtenerMenu(idMenu)
        menuLiveData.observe(this, Observer {
            menu = it

            txtDetalleMenuNombre.setText(menu.nombre)
            txtDetalleMenuDetalle.setText(menu.detalle)
            txtDetalleMenuPrecio.setText("$ ${menu.precio}")

            val uriImagen = ControladorImagen.obtenerUriImagen(this,idMenu.toLong())
            imgDetalleMenu.setImageURI(uriImagen)
        })
    }

    private fun eliminarMenu(){

        menuLiveData.removeObservers(this)
        CoroutineScope(Dispatchers.IO).launch {
            database.menus().eliminarMenu(menu)
            this@DetalleColeccionActivity.finish()
        }
    }

    private fun editarMenu() {

        database = AppDatabase.getDatabase(this)

        menu.nombre = txtDetalleMenuNombre.text.toString()
        menu.detalle = txtDetalleMenuDetalle.text.toString()

        var idMenu = menu.idMenu
        CoroutineScope(Dispatchers.IO).launch {
            database.menus().actualizarMenu(menu)

            uriImagen?.let {
                ControladorImagen.guardarImagen(this@DetalleColeccionActivity, idMenu.toLong(), it)
            }
            this@DetalleColeccionActivity.finish()
        }
    }
}