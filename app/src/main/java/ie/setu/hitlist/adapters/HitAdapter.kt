package ie.setu.hitlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import ie.setu.hitlist.R
import ie.setu.hitlist.databinding.CardHittargetBinding
import ie.setu.hitlist.models.HitModel
import ie.setu.hitlist.utils.customTransformation
import com.squareup.picasso.Picasso

// interface will represent click events on the target Card.
interface HitClickListener {
    fun onHitClick(target: HitModel)
}

// Adapter - accepts and installs an event handler based on the interface
class HitAdapter(private var targets: ArrayList<HitModel>,
                 private val listener: HitClickListener,
                 private val readOnly: Boolean) :
    RecyclerView.Adapter<HitAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardHittargetBinding    // initialise view
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding, readOnly)    // return holder view
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val target = targets[holder.adapterPosition]
        holder.bind(target, listener)
    }

    fun removeAt(position: Int) {
        targets.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = targets.size

    inner class MainHolder(val binding: CardHittargetBinding, private val readOnly: Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(target: HitModel, listener: HitClickListener) {
            // update target data element with the individual target that is passed to main holder class
            binding.root.tag = target
            binding.target = target
            Picasso.get().load(target.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onHitClick(target) }
            binding.executePendingBindings() // force bindings to happen immediately
        }
    }
}