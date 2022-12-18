package ie.setu.hitlist.adapters

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.hitlist.databinding.CardHittaskBinding
import ie.setu.hitlist.models.HitModel

// interface will represent click events on the task Card.
interface HitListener {
    fun onHitClick(task: HitModel)
}

// Adapter - accepts and installs an event handler based on the interface
class HitAdapter constructor(private var tasks: List<HitModel>,
                             private val listener: HitListener) :
    RecyclerView.Adapter<HitAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardHittaskBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val task = tasks[holder.adapterPosition]
        holder.bind(task, listener)
    }

    override fun getItemCount(): Int = tasks.size

    class MainHolder(private val binding : CardHittaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: HitModel, listener: HitListener) {
            binding.taskTitle.text = task.title
            binding.description.text = task.description
            binding.root.setOnClickListener { listener.onHitClick(task) }
        }
    }
}