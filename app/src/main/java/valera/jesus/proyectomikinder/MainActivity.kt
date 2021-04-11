package valera.jesus.proyectomikinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var b_aceptar: Button = findViewById(R.id.b_aceptar);

        b_aceptar.setOnClickListener {
            var intent = Intent(this, PruebaMenu::class.java)
            startActivity(intent)
        }

    }
}