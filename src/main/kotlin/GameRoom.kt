import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@JsonClass(generateAdapter = true)
data class Connection(val action: Action, val id: String)       // FIXME: choose a new name

@JsonClass(generateAdapter = true)
data class GameRoom(
        val name: String,
        val id: String,
        val description: String,
        val adjacentRoomIds: List<Connection>,
        val interactiveObject: InteractiveObject? = null
        ) {

    var beenVisited = false

    val adjacentRooms = mutableMapOf<Action, GameRoom>()

    fun describe(): String {
        return """
            |$name
            |$description
            |${listItems()}""".trimMargin()
    }

    fun listItems(): String {
        return interactiveObject?.description ?: ""
    }
}

class GameWorld (worldFile: String = "test_world_01.json"){

    private var rooms: List<GameRoom>
    var startingRoom: GameRoom

    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    init {
        val data = GameWorld::class.java.getResourceAsStream(worldFile).bufferedReader().readText()
        val typeAdapter = Types.newParameterizedType(List::class.java, GameRoom::class.java, Connection::class.java, InteractiveObject::class.java, ObjectAction::class.java, ObjectState::class.java)
        val adapter = moshi.adapter<List<GameRoom>>(typeAdapter)
        rooms = adapter.fromJson(data)!!

        val idToRoom = mutableMapOf<String, GameRoom>()
        rooms.forEach { idToRoom[it.id] = it }
        rooms.forEach { room: GameRoom ->
            room.adjacentRoomIds.forEach { conn -> room.adjacentRooms[conn.action] = idToRoom[conn.id]!! }
        }

        startingRoom = idToRoom["START"]!!
    }

}