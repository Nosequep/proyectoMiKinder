package valera.jesus.proyectomikinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_maestros.*

class MaestrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maestros)
        b_regresar.setOnClickListener {
            var intent = Intent(this, ContactoActivity::class.java)
            startActivity(intent)
        }

        this.im_maestra1.setOnClickListener {
            var intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}