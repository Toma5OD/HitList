// package ie.setu.hitlist.models

// import android.content.Context
// import android.net.Uri
// import com.google.gson.*
// import com.google.gson.reflect.TypeToken
// import ie.setu.hitlist.helpers.*
// import timber.log.Timber
// import timber.log.Timber.i
// import java.lang.reflect.Type
// import java.util.*

// // declare the filename
// const val JSON_FILE = "targets.json"
// // declaring a utility to serialize a java class (pretty printing it)
// val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
//     .registerTypeAdapter(Uri::class.java, UriParser())
//     .create()
// // create an object to convert JSON string to a java collection
// val listType: Type = object : TypeToken<ArrayList<HitModel>>() {}.type

// fun generateRandomId(): Long {
//     return Random().nextLong()
// }

// class HitJSONStore(private val context: Context) : HitStore {

//     var targets = mutableListOf<HitModel>()

//     init {
//         if (exists(context, JSON_FILE)) {
//             deserialize()
//         }
//     }

//     override fun findAll(): List<HitModel> {
//         logAll()
//         return targets
//     }

//     override fun findById(id: Long): HitModel? {
//         TODO("Not yet implemented")
//     }

//     override fun create(target: HitModel) {
//         target.id = generateRandomId()
//         targets.add(target)
//         serialize()
//     }


//     override fun update(target: HitModel) {
//         val targetsList = findAll() as ArrayList<HitModel>
//         var foundTarget: HitModel? = targetsList.find { t -> t.id == target.id }
//         if (foundTarget != null) {
//             foundTarget.title = target.title
//             foundTarget.description = target.description
//             foundTarget.rating = target.rating
//             foundTarget.image = target.image
//         }
//         serialize()
//     }

//     override fun delete(target: HitModel) {
//         val targetsList = findAll() as ArrayList<HitModel>
//         var foundTarget: HitModel? = targetsList.find { t -> t.id == target.id }
//         if (foundTarget != null) {
//             targets.remove(target)
//             i("Target: ${targets} removed")
//         }
//         serialize()
//     }

//     private fun serialize() {
//         val jsonString = gsonBuilder.toJson(targets, listType)
//         write(context, JSON_FILE, jsonString)
//     }

//     private fun deserialize() {
//         val jsonString = read(context, JSON_FILE)
//         targets = gsonBuilder.fromJson(jsonString, listType)
//     }

//     private fun logAll() {
//         targets.forEach { Timber.i("$it") }
//     }
// }
// // parse uri image property in HitModel.
// class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
//     override fun deserialize(
//         json: JsonElement?,
//         typeOfT: Type?,
//         context: JsonDeserializationContext?
//     ): Uri {
//         return Uri.parse(json?.asString)
//     }
//     // Utility to format a java class (pretty printing it)
//     override fun serialize(
//         src: Uri?,
//         typeOfSrc: Type?,
//         context: JsonSerializationContext?
//     ): JsonElement {
//         return JsonPrimitive(src.toString())
//     }
// }