package ie.setu.hitlist.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import android.net.Uri

@Parcelize

data class HitModel(var id: Long = 0,
                    var title: String = "",
                    var description: String = "",
                    var rating: String = "",
                    var image: Uri = Uri.EMPTY) : Parcelable
