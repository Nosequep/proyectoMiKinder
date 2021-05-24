package valera.jesus.proyectomikinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contacto.*

class ContactoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)
        b_regresar.setOnClickListener {
            var intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }
        this.b_aceptar.setOnClickListener {
            var intent = Intent(this, MaestrosActivity::class.java)
            startActivity(intent)
        }
    }
}