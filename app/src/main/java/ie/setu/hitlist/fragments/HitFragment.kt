package ie.setu.hitlist.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.squareup.picasso.Picasso
import ie.setu.hitlist.R
import ie.setu.hitlist.databinding.FragmentHitBinding
import ie.setu.hitlist.main.MainApp
import ie.setu.hitlist.models.HitModel
import timber.log.Timber
import com.google.android.material.snackbar.Snackbar
import ie.setu.hitlist.helpers.showImagePicker


/**
 * A simple [Fragment] subclass.
 * Use the [HitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HitFragment : Fragment(), View.OnClickListener {

    // ActivityHitBinding augmented class needed to access diff view
    // of objects on a particular layout

    lateinit var app: MainApp // ref to mainApp object (1)
    private var _fragBinding: FragmentHitBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var navController: NavController
    var target = HitModel()
    var edit = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = activity?.application as MainApp
        Timber.i("Hit Activity started...")
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)
        registerImagePickerCallback()   // initialise the image picker callback func.

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _fragBinding = FragmentHitBinding.inflate(inflater, container, false)
        val view = fragBinding.root
        getString(R.string.action_hit).also { activity?.title = it }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity?.intent!!.hasExtra("target_edit")) {
            edit = true
            target = activity?.intent!!.extras?.getParcelable("target_edit")!!
            fragBinding.targetTitle.setText(target.title)
            fragBinding.description.setText(target.description)
            fragBinding.btnAdd.setText(R.string.save_target)
            fragBinding.btnPhoto.setText(R.string.take_photo)
            Picasso.get()
                .load(target.image)
                .into(fragBinding.targetImage)
            if (target.image != Uri.EMPTY) {
                fragBinding.chooseImage.setText(R.string.change_target_image)
            }
        }

        fragBinding.btnAdd.setOnClickListener {
            target.title = fragBinding.targetTitle.text.toString()
            target.description = fragBinding.description.text.toString()

            val rgRating: String = if (fragBinding.rgRating.checkedRadioButtonId == R.id.easyBtn) {
                "Easy"
            } else if(fragBinding.rgRating.checkedRadioButtonId == R.id.hardBtn) {
                "Hard"
            } else "Very Hard"
            target.rating = rgRating
            Timber.i("Difficulty Rating $rgRating")

            if(target.title.isEmpty()) {
                Snackbar
                    .make(it, R.string.enter_hitTarget_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.targets.update(target.copy())
                    findNavController().navigate(R.id.hitListFragment)
                } else {
                    app.targets.create(target.copy()) // use mainApp (3)
                    Timber.i("add Button Pressed: $target.title")
                }
                navController.navigate(R.id.hitListFragment)
            }
        }
        fragBinding.chooseImage.setOnClickListener {
            Timber.i("Select image")
            showImagePicker(imageIntentLauncher)    // trigger the image picker
        }

        fragBinding.btnPhoto.setOnClickListener {
            Timber.i("Take Photo")
            navController.navigate(R.id.action_hitFragment_to_cameraFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_hittarget, menu)
        if (edit) menu.getItem(1).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
        requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

     override fun onClick(v: View?) {
        navController.navigate(R.id.cameraFragment)
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            // Only recovering uri when the result Code is RESULT_OK
                            target.image = result.data!!.data!!
                            Picasso.get()
                                .load(target.image)
                                .into(fragBinding.targetImage)
                            // when an image is changed, also change the label
                            fragBinding.chooseImage.setText(R.string.change_target_image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> {
                    }
                    else -> { }
                }
            }
    }

}