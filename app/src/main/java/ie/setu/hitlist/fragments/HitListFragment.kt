package ie.setu.hitlist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.hitlist.R
import ie.setu.hitlist.adapters.HitAdapter
import ie.setu.hitlist.adapters.HitListener
import ie.setu.hitlist.databinding.FragmentHitListBinding
import ie.setu.hitlist.main.MainApp
import ie.setu.hitlist.models.HitModel


/**
 * A simple [Fragment] subclass.
 * Use the [HitListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HitListFragment : Fragment(), HitListener {
    lateinit var app: MainApp // ref to mainApp object (1)
    private var _fragBinding: FragmentHitListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = activity?.application as MainApp
        setHasOptionsMenu(true)
        registerRefreshCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentHitListBinding.inflate(inflater, container, false)
        val view = fragBinding.root
        activity?.title = getString(R.string.action_hitList)
        loadHitTargets()
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onHitClick(target: HitModel) {
        findNavController().navigate(R.id.action_hitListFragment_to_hitFragment)
    }

    private fun loadHitTargets() {
        showHitTargets(app.targets.findAll())
    }

    fun showHitTargets (targets: List<HitModel>) {
        fragBinding.recyclerView.adapter = HitAdapter(targets, this)
        fragBinding.recyclerView.adapter?.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HitListFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.action_hitList)
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.recyclerView.adapter = HitAdapter(app.targets.findAll(),this)

    }

    // Register the callback
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadHitTargets() }
    }
}
