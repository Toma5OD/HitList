package ie.setu.hitlist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.hitlist.R
import ie.setu.hitlist.databinding.ActivityHitListBinding
import ie.setu.hitlist.adapters.HitListener
import ie.setu.hitlist.adapters.HitAdapter
import ie.setu.hitlist.main.MainApp
import ie.setu.hitlist.models.HitModel

class HitListActivity : AppCompatActivity(), HitListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityHitListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // In order to present the toolbar - we must explicitly enable it
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        // Retrieving and storing a reference to the MainApp object
        app = this.application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
//      binding.recyclerView.adapter = HitAdapter(app.targets)
        binding.recyclerView.adapter = HitAdapter(app.targets.findAll(),this)
    
    registerRefreshCallback()
    }

    

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // menu event handler - if the event is item_add, we start the HitListActivity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                // expose intent to permit activity to be launched
                val launcherIntent = Intent(this, HitActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHitClick(target: HitModel) {
        val launcherIntent = Intent(this, HitActivity::class.java)
        // passing the target to the actvity, enabled via the parcelable mechanism
        launcherIntent.putExtra("target_edit", target)
        refreshIntentLauncher.launch(launcherIntent)
    }

    // Register the callback
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}