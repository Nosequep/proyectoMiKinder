package valera.jesus.proyectomikinder

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
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_recursos.*
import kotlinx.android.synthetic.main.activity_recursos.listview
import java.util.ArrayList

class recursosActivity : AppCompatActivity() {
    private lateinit var recursos: ArrayList<Recurso>
    private var adaptador: recursosActivity.AdaptadorRecursos? = null
    private lateinit var storage: FirebaseFirestore
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recursos)
        this.recursos = ArrayList<Recurso>()
        storage = FirebaseFirestore.getInstance()
        b_regresar.setOnClickListener {
            var intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }

        fillRecursos()


        this.listview.setOnItemClickListener{ parent, view, position, id ->
            var element = listview.getItemAtPosition(position)
            var intent = Intent(this, MostrarRecurso::class.java)
            startActivity(intent)
        }
    }

    fun fillRecursos(){
        storage.collection("recursos")
            .get()
            .addOnSuccessListener {
                it.forEach {
                    recursos!!.add(Recurso(it.getString("nombre")!!, it.getString("imagen")!!, it.getString("contenido")!!))

                }

                this.adaptador = recursosActivity.AdaptadorRecursos(this, recursos)
                this.listview.adapter = adaptador
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: intente de nuevo", Toast.LENGTH_SHORT).show()
            }

    }
    private class AdaptadorRecursos: BaseAdapter {
        var recursos = ArrayList<Recurso>()
        var contexto: Context? = null

        constructor(contexto: Context, recursos: ArrayList<Recurso>){
            this.recursos = recursos
            this.contexto = contexto
        }

        private fun getIcono(icono: String): Int{
            if(icono == "iconos13"){
                return R.drawable.iconos13
            }else if(icono == "iconos1"){
                return R.drawable.iconos1
            }else if(icono == "iconos37"){
                return R.drawable.iconos37
            }
            return R.drawable.iconos13
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var recurso = recursos[position]
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.recurso_view, null)

            var image = vista.findViewById(R.id.iv_icono) as ImageView
            var nombre = vista.findViewById(R.id.tv_recurso) as TextView
            nombre.setText(recurso.nombre)
            image.setImageDrawable(
                this.contexto?.getDrawable(this.getIcono(recurso.imagen)))

            return vista
        }

        override fun getItem(position: Int): Any {
            return recursos[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.recursos.size
        }
    }
}