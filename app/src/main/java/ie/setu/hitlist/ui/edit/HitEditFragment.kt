package ie.setu.hitlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.setu.hitlist.databinding.HitEditFragmentBinding
import ie.setu.hitlist.ui.edit.HitEditViewModel
import ie.setu.hitlist.ui.auth.LoggedInViewModel
import ie.setu.hitlist.ui.list.HitListViewModel
import timber.log.Timber

class HitEditFragment: Fragment() {

    private var _fragBinding: HitEditFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var hitEditViewModel: HitEditViewModel
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val hitListViewModel: HitListViewModel by activityViewModels()
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
        
        fragBinding.editTargetButton.setOnClickListener {
            Timber.i("EDIT TARGET ${fragBinding.hittargetvm?.observableHitTarget!!.value!!}")
            hitEditViewModel.editTarget(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.targetid, fragBinding.hittargetvm?.observableHitTarget!!.value!!)
            hitListViewModel.load()
            findNavController().navigateUp()
            //findNavController().popBackStack()
        }

        fragBinding.deleteTargetButton.setOnClickListener {
            Timber.i("DELETE TARGET")
            hitListViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                hitEditViewModel.observableHitTarget.value?.uid!!)
            findNavController().popBackStack()
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
        Timber.i("Retrofit fragBinding.hittargetvm == $fragBinding.hittargetvm")
    }

    override fun onResume() {
        super.onResume()
        hitEditViewModel.getHitTarget(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.targetid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}