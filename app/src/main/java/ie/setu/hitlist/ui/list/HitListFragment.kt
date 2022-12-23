package ie.setu.hitlist.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ie.setu.hitlist.R
import ie.setu.hitlist.adapters.HitAdapter
import ie.setu.hitlist.adapters.HitClickListener
import ie.setu.hitlist.databinding.FragmentHitListBinding
import ie.setu.hitlist.main.MainApp
import ie.setu.hitlist.models.HitModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.setu.hitlist.utils.*
import timber.log.Timber


class HitListFragment : Fragment(), HitClickListener {


    lateinit var app: MainApp // ref to mainApp object (1)
    private var _fragBinding: FragmentHitListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var hitListViewModel:  HitListViewModel
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        loader = createLoader(requireActivity())
        _fragBinding = FragmentHitListBinding.inflate(inflater, container, false)
        val view = fragBinding.root

        // setting up linear layout manager to display list of hit targets

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        Timber.i("Called ViewModelProvider.get")
        hitListViewModel = ViewModelProvider(this).get(HitListViewModel::class.java)
        // observe public live data, when targets changes we call render.
        hitListViewModel.observableTargetList.observe(viewLifecycleOwner, Observer { targets ->
            targets?.let { render(targets as ArrayList<HitModel>) }
            hideLoader(loader)
            checkSwipeRefresh()
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Target")
                val adapter = fragBinding.recyclerView.adapter as HitAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onHitClick(viewHolder.itemView.tag as HitModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = HitListFragmentDirections.actionHitListFragmentToHitFragment()
            findNavController().navigate(action)
        }
        return view
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Hit List")
        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    private fun render(targetList: ArrayList<HitModel>) {
        // create adapter passing in the list of targets
        fragBinding.recyclerView.adapter = HitAdapter(targetList, this)
        if (targetList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.noTargetsFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.noTargetsFound.visibility = View.GONE
        }
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
        val action = HitListFragmentDirections.actionHitListFragmentToHitEditFragment(target.id)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        hitListViewModel.load()

    }

    // Register the callback
//    private fun registerRefreshCallback() {
//        refreshIntentLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
//            { loadHitTargets() }
//    }
}