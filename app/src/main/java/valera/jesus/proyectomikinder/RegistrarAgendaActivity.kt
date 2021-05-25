package valera.jesus.proyectomikinder

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registrar_agenda.*
import kotlinx.android.synthetic.main.activity_registrar_agenda.b_agregar
import kotlinx.android.synthetic.main.activity_registrar_agenda.b_crear
import kotlinx.android.synthetic.main.activity_registrar_agenda.btn_time
import kotlinx.android.synthetic.main.activity_registrar_agenda.et_correoAlumno
import kotlinx.android.synthetic.main.activity_registrar_agenda.lv_alumnos
import kotlinx.android.synthetic.main.activity_registrar_clase.*

import java.text.SimpleDateFormat
import java.util.*

class RegistrarAgendaActivity : AppCompatActivity() {
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario : FirebaseAuth
    private lateinit var alumnos: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_agenda)
        //Conexion a la bd
        storage = FirebaseFirestore.getInstance()
        //conexion al usuario gmail
        usuario = FirebaseAuth.getInstance()

        this.b_crear.setOnClickListener {
            this.valida_registro()
        }

        btn_time.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                btn_time.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show()

        }

        alumnos= ArrayList<String>()
        var ADP: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alumnos)
        this.lv_alumnos.adapter = ADP
        this.b_agregar.setOnClickListener {
            alumnos.add(this.et_correoAlumno.text.toString())
            ADP.notifyDataSetChanged()
            this.et_correoAlumno.setText("")
        }
    }

    private fun valida_registro(){

        registrarClase()


    }

    private fun registrarClase(){

        val agenda = hashMapOf(
            "fechaLimite" to btn_time.text.toString(),
            "alumnos" to this.alumnos,
            "revisado" to false,
            "espanol" to cb_espanol.isChecked,
            "matematicas" to cb_matematicas.isChecked,
            "musica" to cb_musica.isChecked
        )
        storage.collection("agenda")
            .add(agenda)
            .addOnSuccessListener {
                Toast.makeText(baseContext, "Se ha registrado satisfactoriamente.",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Error en el registro.",
                    Toast.LENGTH_SHORT).show()
            }
    }
}