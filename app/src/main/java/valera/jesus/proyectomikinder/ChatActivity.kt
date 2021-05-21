package valera.jesus.proyectomikinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        b_regresar.setOnClickListener {
            var intent = Intent(this, MaestrosActivity::class.java)
            startActivity(intent)
        }
    }
}