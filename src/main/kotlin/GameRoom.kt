import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


@JsonClass(generateAdapter = true)
data class GameWorld(val gameName: String, val authorName: String, val year: Int, val openingText: String, val gameRooms: List<GameRoom>)

@JsonClass(generateAdapter = true)
data class Connection(val action: Action, val id: String)       // FIXME: choose a new name

@JsonClass(generateAdapter = true)
data class GameRoom(
        val name: String,
        val id: String,
        val description: String,
        val adjacentRoomIds: List<Connection>,
        var interactiveObjects: MutableList<InteractiveObject>
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
        var msg = ""
        interactiveObjects.forEach {
            msg += "${it.description} \n"
        }
        return msg
    }
}

class GameMap(worldFile: String = "test_world_01.json") {

    private var rooms: List<GameRoom>
    var startingRoom: GameRoom
    val carriedItems: MutableList<InteractiveObject> = mutableListOf()

    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    init {
        val data = GameWorld::class.java.getResourceAsStream(worldFile).bufferedReader().readText()
        val typeAdapter = Types.newParameterizedType(GameWorld::class.java, List::class.java, GameRoom::class.java, Connection::class.java, InteractiveObject::class.java, ObjectAction::class.java, ObjectState::class.java)
        val adapter = moshi.adapter<GameWorld>(typeAdapter)
        val gameWorld = adapter.fromJson(data)!!
        rooms = gameWorld.gameRooms

        val idToRoom = mutableMapOf<String, GameRoom>()
        rooms.forEach { idToRoom[it.id] = it }
        rooms.forEach { room: GameRoom ->
            room.adjacentRoomIds.forEach { conn -> room.adjacentRooms[conn.action] = idToRoom[conn.id]!! }
        }

        startingRoom = idToRoom["START"]!!

        println("""
            |
            |${gameWorld.gameName}
            |by ${gameWorld.authorName}
            |${gameWorld.year}
            |
            |${gameWorld.openingText}
            |
        """.trimMargin())
    }

}
