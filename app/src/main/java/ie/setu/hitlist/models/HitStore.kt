package ie.setu.hitlist.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface HitStore {
    fun findAll(targetList: MutableLiveData<List<HitModel>>)
    fun findAll(userid: String, targetList: MutableLiveData<List<HitModel>>)
    fun findById(userid: String, targetid: String, taarget: MutableLiveData<HitModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, target: HitModel)
    fun update(userid: String, targetid: String, target: HitModel)
    fun delete(userid: String, targetid: String)
}