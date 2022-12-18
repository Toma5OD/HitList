package ie.setu.hitlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.hitlist.databinding.CardHittaskBinding
import ie.setu.hitlist.models.HitModel

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