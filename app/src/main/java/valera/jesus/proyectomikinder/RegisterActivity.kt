package valera.jesus.proyectomikinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        //Conexion a la bd
        storage = FirebaseFirestore.getInstance()
        //conexion al usuario gmail
        usuario = FirebaseAuth.getInstance()
        this.b_registrarse.setOnClickListener{
            valida_registro()
        }

    }

    private fun valida_registro(){
        var nombre: String = et_nombre.text.toString()
        var apellido: String = et_apellido.text.toString()
        var correo: String = et_correo.text.toString()
        var contra1: String = et_contrasenia1.text.toString()
        var contra2: String = et_contrasenia2.text.toString()

        if(!nombre.isNullOrBlank() && !apellido.isNullOrBlank() && !contra1.isNullOrBlank()){
            if(contra1 == contra2){
                registrarFirebase(correo, contra1)
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Favor de llenar todos los campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarFirebase(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    registrarUsuario()



                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun registrarUsuario(){
        val usuario = hashMapOf(
            "nombres" to et_nombre.text.toString(),
            "apellidos" to et_apellido.text.toString(),
            "correo" to et_correo.text.toString(),
            "contrasenia" to et_contrasenia1.text.toString(),
            "rol" to "alumno"
        )
        storage.collection("usuario")
            .add(usuario)
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