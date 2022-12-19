package ie.setu.hitlist.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class HitModel(var id: Long = 0,
                    var title: String = "",
                    var description: String = "") : Parcelable
