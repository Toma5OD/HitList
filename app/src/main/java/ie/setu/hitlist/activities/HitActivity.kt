package ie.setu.hitlist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.hitlist.R
import ie.setu.hitlist.databinding.ActivityHitBinding
import ie.setu.hitlist.helpers.showImagePicker
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
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        //inflate layout using binding class
        binding = ActivityHitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // In order to present the toolbar - we must explicitly enable it
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        registerImagePickerCallback()   // initialise the image picker callback func

        app = application as MainApp    // initialise mainApp (2)
        i("Hit Activity Started..")

        if(intent.hasExtra("task_edit")) {
            edit = true
            task = intent.extras?.getParcelable("task_edit")!!
            binding.taskTitle.setText(task.title)
            binding.description.setText(task.description)
            binding.btnAdd.setText(R.string.save_target)
            Picasso.get()
                .load(task.image)
                .into(binding.taskImage)
        }

        binding.btnAdd.setOnClickListener {
            task.title = binding.taskTitle.text.toString()
            task.description = binding.description.text.toString()
            if(task.title.isEmpty()) {    
                Snackbar
                    .make(it, R.string.enter_hitTarget_title, Snackbar.LENGTH_LONG)
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
            binding.chooseImage.setOnClickListener {
            i("Select image")
            showImagePicker(imageIntentLauncher)    // trigger the image picker
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            // Only recovering uri when the result Code is RESULT_OK
                            task.image = result.data!!.data!!
                            Picasso.get()
                                .load(task.image)
                                .into(binding.taskImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}