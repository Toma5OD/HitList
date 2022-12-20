package ie.setu.hitlist.models

interface HitStore {
    fun findAll(): List<HitModel>
    fun create(target: HitModel)
}