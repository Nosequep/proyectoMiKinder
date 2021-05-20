package valera.jesus.proyectomikinder

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_registrar_clase.*
import java.text.SimpleDateFormat
import java.util.*

class RegistrarClase : AppCompatActivity() {
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario : FirebaseAuth
    private lateinit var alumnos: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_clase)
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
        var materia: String = et_materia.text.toString()
        var maestro: String = et_maestro.text.toString()
        var hora: String = btn_time.text.toString()
        //var correos: String = et_correos.text.toString()

        if(!materia.isNullOrBlank() && !maestro.isNullOrBlank() && !hora.isNullOrBlank()){

            registrarClase()
        }else{
            Toast.makeText(this, "Favor de llenar todos los campos.", Toast.LENGTH_SHORT).show()
        }

    }



    private fun registrarClase(){

        val clase = hashMapOf(
            "maestro" to et_maestro.text.toString(),
            "materia" to et_materia.text.toString(),
            "hora" to btn_time.text.toString(),
            "alumnos" to this.alumnos
        )
        storage.collection("clases")
            .add(clase)
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