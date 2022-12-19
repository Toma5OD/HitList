package ie.setu.hitlist.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HitMemStore: HitStore {

    val tasks = ArrayList<HitModel>()

    override fun findAll(): List<HitModel> {
        return tasks
    }

    override fun create(task: HitModel) {
        task.id = getId()
        tasks.add(task)
        logAll()
    }

    fun update(task: HitModel) {
        var foundTask: HitModel? = tasks.find { t -> t.id == task.id }
        if (foundTask != null) {
            foundTask.title = task.title
            foundTask.description = task.description
            logAll()
        }
    }

    fun logAll() {
        tasks.forEach{ i("${it}")}
    }
}