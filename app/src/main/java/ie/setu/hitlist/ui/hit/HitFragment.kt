package ie.setu.hitlist.ui.hit


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.squareup.picasso.Picasso
import ie.setu.hitlist.R
import ie.setu.hitlist.databinding.FragmentHitBinding
import ie.setu.hitlist.models.HitModel
import timber.log.Timber
import com.google.android.material.snackbar.Snackbar
import ie.setu.hitlist.helpers.showImagePicker
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ie.setu.hitlist.HitEditFragmentArgs
import ie.setu.hitlist.ui.auth.LoggedInViewModel
import ie.setu.hitlist.ui.list.HitListViewModel

class HitFragment : Fragment(), View.OnClickListener {

    private var _fragBinding: FragmentHitBinding? = null
    private val fragBinding get() = _fragBinding!!
    //private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var hitViewModel: HitViewModel
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val hitListViewModel: HitListViewModel by activityViewModels()
//    private val args by navArgs<Hit>()
    var target = HitModel()

    var edit = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("Hit Fragment started...")
        setHasOptionsMenu(true)
//        registerImagePickerCallback()   // initialise the image picker callback func.

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _fragBinding = FragmentHitBinding.inflate(inflater, container, false)
        val view = fragBinding.root
        getString(R.string.action_hit).also { activity?.title = it }

        hitViewModel = ViewModelProvider(this).get(HitViewModel::class.java)
//        hitViewModel.observableHitTarget.observe(viewLifecycleOwner, Observer { render() })
        hitViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let { render(status) }
        })

        addNewTargetButtonListener(fragBinding)

        fragBinding.btnPhoto.setOnClickListener {
            Timber.i("Take Photo")
            val action = HitFragmentDirections.actionHitFragmentToCameraFragment()
            findNavController().navigate(action)
        }
        return view
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to target list
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.createTargetError), Toast.LENGTH_LONG).show()
        }
    }

    fun addNewTargetButtonListener(layout: FragmentHitBinding) {
        layout.btnAdd.setOnClickListener {
            val title = fragBinding.targetTitle.text.toString()
            val description = fragBinding.description.text.toString()

            val rgRating: String = if (fragBinding.rgRating.checkedRadioButtonId == R.id.easyBtn) {
                "Easy"
            } else if(fragBinding.rgRating.checkedRadioButtonId == R.id.hardBtn) {
                "Hard"
            } else "Very Hard"
            val rating = rgRating
            Timber.i("Difficulty Rating $rgRating")

            if(title.isEmpty()) {
                Snackbar
                    .make(it, R.string.enter_hitTarget_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                hitViewModel.addHitTarget(loggedInViewModel.liveFirebaseUser, HitModel(title = title,
                    description = description, rating = rating,
                    email = loggedInViewModel.liveFirebaseUser.value?.email!!))
                Timber.i("add Button Pressed: $target.title")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_hittarget, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
        requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        findNavController().navigate(R.id.cameraFragment)
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            HitFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
    }
}