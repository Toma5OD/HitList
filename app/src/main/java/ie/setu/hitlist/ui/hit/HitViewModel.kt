package ie.setu.hitlist.ui.hit

/* View model survives config changes, so is a good place for data that needs to survive */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.setu.hitlist.firebase.FirebaseDBManager
import ie.setu.hitlist.firebase.FirebaseImageManager
import ie.setu.hitlist.models.HitModel

class HitViewModel: ViewModel() {
    // tracks individual target
    private var target = MutableLiveData<HitModel>()
    // expose public read-only hit target
    var observableHitTarget: MutableLiveData<HitModel>
        get() = target
        set(value) {target.value = value.value}

    private val status = MutableLiveData<Boolean>()
    val observableStatus: LiveData<Boolean>
        get() = status

    fun addHitTarget(firebaseUser: MutableLiveData<FirebaseUser>, target: HitModel) {
        status.value = try {
            target.profilepic = FirebaseImageManager.imageUri.value.toString()
            FirebaseDBManager.create(firebaseUser, target)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}