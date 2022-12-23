package ie.setu.hitlist.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.hitlist.models.HitManager
import ie.setu.hitlist.models.HitModel

class HitEditViewModel : ViewModel() {
    // tracks individual target
    private var target = MutableLiveData<HitModel>()
    // expose public read-only hit target
    var observableHitTarget: MutableLiveData<HitModel>
        get() = target
        set(value) {target.value = value.value}

    private val status = MutableLiveData<Boolean>()
    val observableStatus: LiveData<Boolean>
        get() = status

    fun getHitTarget(id: Long) {
        target.value = HitManager.findById(id)
    }
    fun editTarget(hitTargetModel: HitModel){
        status.value = try {
            HitManager.update(hitTargetModel)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
    fun deleteTarget(hitTargetModel: HitModel){
        status.value = try {
            HitManager.delete(hitTargetModel)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}