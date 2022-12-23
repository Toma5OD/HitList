package ie.setu.hitlist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import ie.setu.hitlist.R

class SplashScreenActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Toast.makeText(this, "Loading HitList App", Toast.LENGTH_SHORT).show()

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
            finish()
        }, 3000) // set delay 3sec before opening HitActivity
    }
}