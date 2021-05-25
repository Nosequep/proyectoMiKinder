package valera.jesus.proyectomikinder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_agenda.b_regresar
import kotlinx.android.synthetic.main.activity_agenda.listview

import java.util.ArrayList

class AgendaActivity : AppCompatActivity() {
    private lateinit var agendas: ArrayList<Agenda>
    private var adaptador: AgendaActivity.AdaptadorAgendas? = null
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)
        this.agendas = ArrayList<Agenda>()
        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()
        b_regresar.setOnClickListener {
            var intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }

        fillAgendas()


        this.listview.setOnItemClickListener{ parent, view, position, id ->
            var element = listview.getItemAtPosition(position)
            var intent = Intent(this, MostrarRecurso::class.java)
            startActivity(intent)
        }
    }

    fun fillAgendas(){
        storage.collection("agenda")
            .get()
            .addOnSuccessListener {
                it.forEach {
                    var alumnos: ArrayList<String> = it.get("alumnos") as ArrayList<String>
                    if(alumnos.contains(this.usuario.currentUser.email)){
                        agendas!!.add(Agenda("28/05/2021 - " + it.getString("fechaLimite")!!, "", false, it.getBoolean("musica")!!, it.getBoolean("matematicas")!!, it.getBoolean("espanol")!!))
                    }

                }
                this.adaptador = AgendaActivity.AdaptadorAgendas(this, agendas)
                this.listview.adapter = adaptador
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: intente de nuevo", Toast.LENGTH_SHORT).show()
            }

    }

    private class AdaptadorAgendas: BaseAdapter {
        var agendas = ArrayList<Agenda>()
        var contexto: Context? = null

        constructor(contexto: Context, agendas: ArrayList<Agenda>){
            this.agendas = agendas
            this.contexto = contexto
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var recurso = agendas[position]
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.agenda_view, null)

            var fechaLimite = vista.findViewById(R.id.tv_fecha) as TextView
            var espanol = vista.findViewById(R.id.ib_escritura) as ImageButton
            var matematicas = vista.findViewById(R.id.ib_matematicas) as ImageButton
            var musica = vista.findViewById(R.id.ib_musica) as ImageButton
            fechaLimite.setText(recurso.fechaLimite)

            if(recurso.espanol == false){
                espanol.setVisibility(View.GONE)
            }
            if(recurso.matematicas == false){
                matematicas.setVisibility(View.GONE)
            }
            if(recurso.musica == false){
                musica.setVisibility(View.GONE)
            }
            return vista
        }

        override fun getItem(position: Int): Any {
            return agendas[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.agendas.size
        }
    }
}