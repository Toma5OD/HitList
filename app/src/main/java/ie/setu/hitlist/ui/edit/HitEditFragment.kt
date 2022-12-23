package ie.setu.hitlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import ie.setu.hitlist.databinding.HitEditFragmentBinding
import ie.setu.hitlist.ui.edit.HitEditViewModel
import timber.log.Timber

class HitEditFragment: Fragment() {

    private var _fragBinding: HitEditFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var hitEditViewModel: HitEditViewModel
    private val args by navArgs<HitEditFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = HitEditFragmentBinding.inflate(inflater, container, false)
        val view = fragBinding.root
        getString(R.string.edit_hit).also { activity?.title = it }

        hitEditViewModel = ViewModelProvider(this).get(HitEditViewModel::class.java)
        hitEditViewModel.observableHitTarget.observe(viewLifecycleOwner, Observer { render() })
        hitEditViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let { renderStatus(status) }
        })

        fragBinding.editTargetButton.setOnClickListener {
            Timber.i("EDIT TARGET ${fragBinding.hittargetvm?.observableHitTarget!!.value!!}")
            hitEditViewModel.editTarget(fragBinding.hittargetvm?.observableHitTarget!!.value!!)
        }
        fragBinding.deleteTargetButton.setOnClickListener {
            Timber.i("DELETE TARGET")
            hitEditViewModel.deleteTarget(fragBinding.hittargetvm?.observableHitTarget!!.value!!)
        }


        return view
    }
    private fun renderStatus(status : Boolean) {
        when (status) {
            true -> {
                view?.let {
                    findNavController().popBackStack()
                }
            }
            false -> {}
        }
    }

    private fun render() {
        fragBinding.hittargetvm = hitEditViewModel
    }

    override fun onResume() {
        super.onResume()
        hitEditViewModel.getHitTarget(args.targetid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}