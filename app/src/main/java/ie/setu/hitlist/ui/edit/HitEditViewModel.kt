package ie.setu.hitlist.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.hitlist.firebase.FirebaseDBManager
import ie.setu.hitlist.models.HitModel
import timber.log.Timber

class HitEditViewModel : ViewModel() {
    // tracks individual target
    private val target = MutableLiveData<HitModel>()
    // expose public read-only hit target
    var observableHitTarget: MutableLiveData<HitModel>
        get() = target
        set(value) {target.value = value.value}

    fun getHitTarget(userid: String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, target)
            Timber.i("Edit View Render Success : ${
                target.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Edit View Error : $e.message")
        }
    }
    fun editTarget(userid:String, id: String, target: HitModel){
        try {
            FirebaseDBManager.update(userid, id, target)
            Timber.i("update() Success : $target")
            true
        } catch (e: IllegalArgumentException) {
            Timber.i("update() Error : $e.message")
        }
    }
}