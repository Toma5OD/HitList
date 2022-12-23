package ie.setu.hitlist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.setu.hitlist.R
import ie.setu.hitlist.databinding.FragmentAboutusBinding
import ie.setu.hitlist.main.MainApp

class AboutUsFragment: Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentAboutusBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_aboutus, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_about, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}