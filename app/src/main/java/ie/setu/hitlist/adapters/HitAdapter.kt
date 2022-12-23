package ie.setu.hitlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.hitlist.R
import ie.setu.hitlist.databinding.CardHittargetBinding
import ie.setu.hitlist.models.HitModel

// interface will represent click events on the target Card.
interface HitClickListener {
    fun onHitClick(target: HitModel)
}

// Adapter - accepts and installs an event handler based on the interface
class HitAdapter(private var targets: ArrayList<HitModel>,
                 private val listener: HitClickListener) :
    RecyclerView.Adapter<HitAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardHittargetBinding    // initialise view
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding = binding)    // return holder view
    }

    fun removeAt(position: Int) {
        targets.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val target = targets[holder.adapterPosition]
        holder.bind(target, listener)
    }

    override fun getItemCount(): Int = targets.size

    inner class MainHolder(val binding : CardHittargetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(target: HitModel, listener: HitClickListener) {
            // update target data element with the individual target that is passed to main holder class
            binding.root.tag = target.uid
            binding.target = target
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onHitClick(target) }
            binding.executePendingBindings() // force bindings to happen immediately
        }
    }
}