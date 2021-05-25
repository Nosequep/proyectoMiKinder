package valera.jesus.proyectomikinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

        enviar.setOnClickListener{
            if(!mensaje.text.isEmpty()){
                mensajes.setText(mensaje.text.toString())
                registrarMensaje(mensajes.text.toString())
            }else{
                    Toast.makeText(baseContext, "Favor de escribir un mensaje!",
                        Toast.LENGTH_SHORT).show()
            }
        }
        b_regresar.setOnClickListener {
            var intent = Intent(this, MaestrosActivity::class.java)
            startActivity(intent)
        }
    }
    fun registrarMensaje(mensaje:String){
        val mensajes = hashMapOf(
            "alumno" to usuario.currentUser.email.toString(),
            "maestro" to "maestro1@gmail.com",
            "mensaje" to mensaje
        )
        storage.collection("chat")
            .add(mensajes)
            .addOnSuccessListener {
                Toast.makeText(baseContext, "Se ha enviado el mensaje satisfactoriamente.",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Error al enviar el mensaje.",
                    Toast.LENGTH_SHORT).show()
            }
    }
}