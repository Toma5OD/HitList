package ie.setu.hitlist.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.setu.hitlist.models.HitModel
import ie.setu.hitlist.models.HitStore
import timber.log.Timber

object FirebaseDBManager: HitStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(targetList: MutableLiveData<List<HitModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, targetList: MutableLiveData<List<HitModel>>) {
        database.child("user-targets").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<HitModel>()
                    val children = snapshot.children
                    children.forEach {
                        val target = it.getValue(HitModel::class.java)
                        localList.add(target!!)
                    }
                    database.child("user-targets").child(userid)
                        .removeEventListener(this)

                    targetList.value = localList
                }
            })
    }

    override fun findById(userid: String, targetid: String, target: MutableLiveData<HitModel>) {
        database.child("user-targets").child(userid)
            .child(targetid).get().addOnSuccessListener {
                target.value = it.getValue(HitModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, target: HitModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("targets").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        target.uid = key
        val hitValues = target.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/targets/$key"] = hitValues
        childAdd["/user-targets/$uid/$key"] = hitValues

        database.updateChildren(childAdd)
    }

    // func removes target from target collection and user-targets' collection
    override fun delete(userid: String, targetid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/targets/$targetid"] = null
        childDelete["/user-targets/$userid/$targetid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, targetid: String, target: HitModel) {
        val hitValues = target.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["targets/$targetid"] = hitValues
        childUpdate["user-targets/$userid/$targetid"] = hitValues

        database.updateChildren(childUpdate)
    }

    fun updateImageRef(userid: String,imageUri: String) {

        val userDonations = database.child("user-targets").child(userid)
        val allDonations = database.child("targets")

        userDonations.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        //Update Users imageUri
                        it.ref.child("profilepic").setValue(imageUri)
                        //Update all donations that match 'it'
                        val donation = it.getValue(HitModel::class.java)
                        allDonations.child(donation!!.uid!!)
                            .child("profilepic").setValue(imageUri)
                    }
                }
            })
    }
}