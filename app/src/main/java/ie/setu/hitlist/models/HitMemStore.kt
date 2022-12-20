package ie.setu.hitlist.models

import timber.log.Timber
import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HitMemStore: HitStore {

    val targets = ArrayList<HitModel>()

    override fun findAll(): List<HitModel> {
        return targets
    }

    override fun create(target: HitModel) {
        target.id = getId()
        targets.add(target)
        logAll()
    }

    override fun update(target: HitModel) {
        var foundTarget: HitModel? = targets.find { t -> t.id == target.id }
        if (foundTarget != null) {
            foundTarget.title = target.title
            foundTarget.description = target.description
            foundTarget.rating = target.rating
            foundTarget.image = target.image
            logAll()
        }
    }

    fun logAll() {
        Timber.v("** Hit List **")
        targets.forEach{ i("${it}")}
    }
}