package ie.setu.hitlist.models

import timber.log.Timber
import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}


object HitManager: HitStore {

    private val targets = ArrayList<HitModel>()

    override fun findAll(): List<HitModel> {
//        targetList: MutableLiveData<List<HitModel>>
        return targets
    }

    override fun findById(id: Long): HitModel? {
        return targets.find { it.id == id }
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

    override fun delete(target: HitModel) {
        val targetsList = findAll() as java.util.ArrayList<HitModel>
        var foundTarget: HitModel? = targetsList.find { t -> t.id == target.id }
        if (foundTarget != null) {
            targets.remove(target)
            i("Target: ${targets} removed")
            logAll()
        }
    }

    fun logAll() {
        Timber.v("** Hit List **")
        targets.forEach{ i("${it}")}
    }
}

