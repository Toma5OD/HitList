package ie.setu.hitlist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.hitlist.databinding.ActivityHitBinding
import ie.setu.hitlist.models.HitModel
import ie.setu.hitlist.main.MainApp
import timber.log.Timber
import timber.log.Timber.i

class HitActivity : AppCompatActivity() {

    // ActivityHitBinding augmented class needed to access diff View
    // objects on a particular layout
    private lateinit var binding: ActivityHitBinding
    var task = HitModel()
    lateinit var app: MainApp // ref to the mainApp object (1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate layout using binding class
        binding = ActivityHitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp    // initialise mainApp (2)
        i("Hit Activity Started..")

        binding.btnAdd.setOnClickListener() {
            task.title = binding.taskTitle.text.toString()
            task.description = binding.description.text.toString()
            if(task.title.isNotEmpty()) {
                i("add Button Pressed: $task.title")
                app.tasks.add(task.copy())    // use mainApp (3)
                for (i in app.tasks.indices) {
                    i("Hit Job[$i]: ${this.app.tasks[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please enter target name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}