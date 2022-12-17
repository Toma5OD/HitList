package ie.setu.hitlist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.hitlist.R
import ie.setu.hitlist.databinding.ActivityHitBinding
import ie.setu.hitlist.models.HitModel
import timber.log.Timber
import timber.log.Timber.i

class HitActivity : AppCompatActivity() {

    // ActivityHitBinding augmented class needed to access diff View
    // objects on a particular layout
    private lateinit var binding: ActivityHitBinding
    var hit = HitModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate layout using binding class
        binding = ActivityHitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_hit)

        Timber.plant(Timber.DebugTree())

        i("Hit Activity Started..")

        binding.btnAdd.setOnClickListener() {
            hit.title = binding.hitTitle.text.toString()
            if (hit.title.isNotEmpty()) {
                i("add Button Pressed: $hit.title")
            }
            else {
                Snackbar
                    .make(it,"Please enter target name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}