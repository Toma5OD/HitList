package ie.setu.hitlist.adapters

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.hitlist.databinding.CardHittargetBinding
import ie.setu.hitlist.models.HitModel

// interface will represent click events on the target Card.
interface HitListener {
    fun onHitClick(target: HitModel)
}

// Adapter - accepts and installs an event handler based on the interface
class HitAdapter constructor(private var targets: List<HitModel>,
                             private val listener: HitListener) :
    RecyclerView.Adapter<HitAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardHittargetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val target = targets[holder.adapterPosition]
        holder.bind(target, listener)
    }

    override fun getItemCount(): Int = targets.size

    class MainHolder(private val binding : CardHittargetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(target: HitModel, listener: HitListener) {
            binding.targetTitle.text = target.title
            binding.description.text = target.description
            Picasso.get().load(target.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onHitClick(target) }
        }
    }
}