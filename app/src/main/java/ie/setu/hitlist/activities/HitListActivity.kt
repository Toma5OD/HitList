package ie.setu.hitlist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.hitlist.R
import ie.setu.hitlist.databinding.ActivityHitListBinding
import ie.setu.hitlist.databinding.CardHittaskBinding
import ie.setu.hitlist.main.MainApp
import ie.setu.hitlist.models.HitModel

class HitListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityHitListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        // Retrieving and storing a reference to the MainApp object (for future use!)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = HitAdapter(app.tasks)
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
}

class HitAdapter constructor(private var tasks: List<HitModel>) :
    RecyclerView.Adapter<HitAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardHittaskBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val task = tasks[holder.adapterPosition]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    class MainHolder(private val binding : CardHittaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: HitModel) {
            binding.taskTitle.text = task.title
            binding.description.text = task.description
        }
    }
}