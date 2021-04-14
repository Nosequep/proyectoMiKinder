package valera.jesus.proyectomikinder

import android.content.Context
import android.content.Intent
import android.graphics.drawable.shapes.Shape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import java.util.*

class InicioActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var clases = ArrayList<Clase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        var drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        var toolbar: Toolbar = findViewById(R.id.toolbar)


        //Toolbar action
        /*
        setSupportActionBar(toolbar)
       var toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
       drawerLayout.addDrawerListener(toggle)
       toggle.syncState()
        */
        this.obtenerFechaInicial()
        this.agregarClases()

        var listview: ListView = findViewById(R.id.listview)
        var adaptador: AdaptadorClases = AdaptadorClases(this, clases)
        listview.adapter = adaptador

        listview.setOnItemClickListener{ parent, view, position, id ->
            var element = listview.getItemAtPosition(position)
            var intent = Intent(this, LlamadaActivity1::class.java)
            startActivity(intent)
        }

        var nav_view: NavigationView = findViewById(R.id.navigator)
        nav_view.bringToFront()

        nav_view.setNavigationItemSelectedListener { item ->
            var intent: Intent
            when (item.itemId) {
                R.id.nav_perfil -> {
                    intent = Intent(this, PerfilActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_trofeos -> {
                    intent = Intent(this, TrofeosActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_horario -> {
                    intent = Intent(this, TrofeosActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_agenda -> {
                    intent = Intent(this, AgendaActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_recursos ->{
                    intent = Intent(this, recursosActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean{
        Toast.makeText(this, "Mensaje", Toast.LENGTH_SHORT).show()

        return true
    }

    fun obtenerFechaInicial(){
        var fecha: Calendar = Calendar.getInstance()

        var tv_fecha: TextView = findViewById(R.id.tv_fecha)
        var dia: String = this.obtenerDiaSemana(fecha.get(Calendar.DAY_OF_WEEK))
        var diaM: String = fecha.get(Calendar.DAY_OF_MONTH).toString()
        var mes: String = this.obtenerMes(fecha.get(Calendar.MONTH))
        //El mes empieza desde el 0
        tv_fecha.setText("${dia} ${diaM} de ${mes}")
    }

    fun obtenerDiaSemana(dia: Int) : String{
        if(dia == 1){
            return "Domingo"
        }else if(dia == 2){
            return "Lunes"
        }else if(dia == 3){
            return "Martes"
        }else if(dia == 4){
            return "Miércoles"
        }else if(dia == 5){
            return "Jueves"
        }else if(dia == 6){
            return "Viernes"
        }else if(dia == 7){
            return "Sábado"
        }
        return ""
    }

    fun obtenerMes(mes: Int): String{
        if(mes == 0){
            return "Enero"
        }else if(mes == 1){
            return "Febrero"
        }else if(mes == 2){
            return "Marzo"
        }else if(mes == 3){
            return "Abril"
        }else if(mes == 4){
            return "Mayo"
        }else if(mes == 5){
            return "Junio"
        }else if(mes == 6){
            return "Julio"
        }else if(mes == 7){
            return "Agosto"
        }else if(mes == 8){
            return "Septiembre"
        }else if(mes == 9){
            return "Octubre"
        }else if(mes == 10){
            return "Noviembre"
        }else if(mes == 11){
            return "Diciembre"
        }
        return ""
    }
    fun agregarClases(){
        clases.add(Clase("Matemáticas", "8:30 a.m.", "Mtra. Natalia", 1))
        clases.add(Clase("Escritura", "10:00 a.m.", "Mtra. Agustina", 2))
        clases.add(Clase("Musica", "12 p.m.", "Mtra. Sofía", 3))
    }

    private class AdaptadorClases: BaseAdapter {
        var clases = ArrayList<Clase>()
        var contexto: Context? = null

        constructor(contexto: Context, clases: ArrayList<Clase>){
            this.clases = clases
            this.contexto = contexto
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var clase = clases[position]
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.clase_view, null)

            var image = vista.findViewById(R.id.iv_icono) as ImageView
            var materia = vista.findViewById(R.id.tv_materia) as TextView
            var hora = vista.findViewById(R.id.tv_hora) as TextView
            var maestro = vista.findViewById(R.id.tv_maestro) as TextView

            materia.setText(clase.materia)
            hora.setText(clase.hora)
            maestro.setText(clase.maestro)

            return vista
        }

        override fun getItem(position: Int): Any {
            return clases[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.clases.size
        }
    }


}