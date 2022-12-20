package ie.setu.hitlist.models

interface HitStore {
    fun findAll(): List<HitModel>
    fun create(target: HitModel)
    fun update(target: HitModel)
    fun delete(target: HitModel)
}