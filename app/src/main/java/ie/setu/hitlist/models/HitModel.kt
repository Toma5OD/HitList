package ie.setu.hitlist.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
@Parcelize
data class HitModel(
    var uid: String? = "",
    var title: String = "",
    var description: String = "",
    var rating: String = "",
    var profilepic: String = "",
    var email: String = "")
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "description" to description,
            "rating" to rating,
            "profilepic" to profilepic,
            "email" to email
        )
    }
}

