package ie.setu.hitlist.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.setu.hitlist.firebase.FirebaseDBManager
import ie.setu.hitlist.models.HitModel
import timber.log.Timber

class HitListViewModel : ViewModel() {

    // raw data accessing the hit model list
    private val targetList = MutableLiveData<List<HitModel>>()

    var readOnly = MutableLiveData(false)

    // expose the hit target list with get accessor
    val observableTargetList: LiveData<List<HitModel>>
        get() = targetList
    
    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    // call load func when we initialise
    init { load()
        Timber.i("HitViewModel created!")
    }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, targetList)
            Timber.i("Hit Target Load Success : ${targetList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid, id)
            Timber.i("Delete Success of $id by $userid")
        }
        catch (e: Exception) {
            Timber.i("Delete Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(targetList)
            Timber.i("LoadAll Success : ${targetList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("LoadAll Error : $e.message")
        }
    }

}