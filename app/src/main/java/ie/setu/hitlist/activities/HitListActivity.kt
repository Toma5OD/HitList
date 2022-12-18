package ie.setu.hitlist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // In order to present the toolbar - we must explicitly enable it
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        // Retrieving and storing a reference to the MainApp object (for future use!)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
//      binding.recyclerView.adapter = HitAdapter(app.tasks)
        binding.recyclerView.adapter = HitAdapter(app.tasks.findAll(),this)
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
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHitClick(task: HitModel) {
        val launcherIntent = Intent(this, HitActivity::class.java)
        startActivityForResult(launcherIntent,0)
    }
}