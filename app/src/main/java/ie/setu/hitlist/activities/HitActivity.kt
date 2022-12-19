package ie.setu.hitlist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.setu.hitlist.R
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
        var edit = false
        //inflate layout using binding class
        binding = ActivityHitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // In order to present the toolbar - we must explicitly enable it
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp    // initialise mainApp (2)
        i("Hit Activity Started..")

        if(intent.hasExtra("task_edit")) {
            edit = true
            task = intent.extras?.getParcelable("task_edit")!!
            binding.taskTitle.setText(task.title)
            binding.description.setText(task.description)
            binding.btnAdd.setText(R.string.save_task)
        }

        binding.btnAdd.setOnClickListener() {
            task.title = binding.taskTitle.text.toString()
            task.description = binding.description.text.toString()
            if(task.title.isEmpty()) {    
                Snackbar
                    .make(it, R.string.enter_hitTask_title, Snackbar.LENGTH_LONG)
                    .show()
                    } else {
                if (edit) {
                    app.tasks.update(task.copy())
                } else {
                    app.tasks.create(task.copy()) // use mainApp (3)
                    i("add Button Pressed: $task.title")
                }
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hittask, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}