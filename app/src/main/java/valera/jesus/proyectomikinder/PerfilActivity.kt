package valera.jesus.proyectomikinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.activity_perfil.*

class PerfilActivity : AppCompatActivity() {
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

        this.cargarPerfil()
        this.b_regresar.setOnClickListener {
            var intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarPerfil() {
        storage.collection("usuarios")
            .whereEqualTo("correo", usuario.currentUser.email)
            .get()
            .addOnSuccessListener {

                it.forEach {
                    this.nombre.text = it.getString("nombres") + " "  + it.getString("apellidos")
                    this.correo.text = it.getString("correo")
                    this.fechaNac.text = it.getString("fechaNacimiento")

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: intente de nuevo", Toast.LENGTH_SHORT).show()

            }
    }
}