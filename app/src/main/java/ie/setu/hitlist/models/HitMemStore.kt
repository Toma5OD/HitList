package ie.setu.hitlist.models

import timber.log.Timber.i

class HitMemStore: HitStore {

    val tasks = ArrayList<HitModel>()

    override fun findAll(): List<HitModel> {
        return tasks
    }

    override fun create(task: HitModel) {
        tasks.add(task)
        logAll()
    }

    fun logAll() {
        tasks.forEach{ i("${it}")}
    }
}