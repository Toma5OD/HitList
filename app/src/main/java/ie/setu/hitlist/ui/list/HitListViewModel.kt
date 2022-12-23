package ie.setu.hitlist.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.hitlist.models.HitManager
import ie.setu.hitlist.models.HitModel
import timber.log.Timber

class HitListViewModel : ViewModel() {

    // raw data accessing the hit model list
    private val targetList = MutableLiveData<List<HitModel>>()

    // expose the hit target list with get accessor
    val observableTargetList: LiveData<List<HitModel>>
        get() = targetList

    // call load func when we initialise
    init { load()
        Timber.i("HitViewModel created!")
    }

    fun load() {
        try {
            targetList.value = HitManager.findAll()
            Timber.i("Retrofit Success : $targetList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    // clean up resources when view model is detached or finished.
//    override fun OnCleared() {
//        super.onCleared()
//        Timber.i("HitListViewModel destroyed!")
//    }
}