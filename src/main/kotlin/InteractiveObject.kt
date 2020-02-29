import com.squareup.moshi.JsonClass

enum class ObjectState {
    ON,
    OFF,
    OPEN,
    CLOSED
}

@JsonClass(generateAdapter = true)
data class ObjectAction(val action: Action, val startState: ObjectState, val endState: ObjectState, val verbiage: String)


@JsonClass(generateAdapter = true)
data class InteractiveObject(val name: String,
                             val id: String,
                             val description: String,
                             var state: ObjectState,
                             val keywords: List<String>,
                             val objectActions: List<ObjectAction>) {

    fun hasKeyword(keyword: String): Boolean = keywords.any { it == keyword }

    fun triggerAction(action: ObjectAction) {
        println(action.verbiage)
        state = action.endState
    }
}