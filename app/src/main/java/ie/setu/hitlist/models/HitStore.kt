package ie.setu.hitlist.models

import androidx.lifecycle.MutableLiveData

interface HitStore {
    fun findAll(): List<HitModel>
    fun findById(id: Long) : HitModel?
    fun create(target: HitModel)
    fun update(target: HitModel)
    fun delete(target: HitModel)
}