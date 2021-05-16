package valera.jesus.proyectomikinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        var b_aceptar: Button = findViewById(R.id.b_aceptar)

        b_aceptar.setOnClickListener {
            this.valida_ingreso()
        }

        var b_registrar: Button = findViewById(R.id.b_registrarse)
        b_registrar.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun valida_ingreso(){


        var correo: String = this.et_correo.text.toString()
        var contra: String = this.et_contrasenia.text.toString()

        if(!correo.isNullOrBlank() && !contra.isNullOrBlank()){
            ingresaFirebase(correo, contra)
        }else{
            Toast.makeText(this, "Ingresar datos",
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun ingresaFirebase(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val user = auth.currentUser
                        var intent = Intent(this, InicioActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.


                        Toast.makeText(baseContext, "La contraseña o el correo son incorrectas.",
                                Toast.LENGTH_SHORT).show()

                    }
                }
    }
}