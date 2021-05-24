package valera.jesus.proyectomikinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mostrar_recurso.*

class MostrarRecurso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_recurso)
        b_regresar.setOnClickListener {
            var intent = Intent(this, recursosActivity::class.java)
            startActivity(intent)
        }
    }
}