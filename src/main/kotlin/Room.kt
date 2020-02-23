import Action.*

abstract class Room(val name: String, val description: String) {

    open val adjacentRooms: MutableMap<Action, Room> = mutableMapOf()

    open val items: MutableList<Item> = mutableListOf()

    fun describe(): String {
        return """
            |$name
            |$description
            |${listItems()}""".trimMargin()
    }

    fun performAction(command: Command): GameState {
        return when (command.action) {
            MOVE_UP,
            MOVE_DOWN,
            MOVE_NORTH,
            MOVE_EAST,
            MOVE_SOUTH,
            MOVE_WEST -> {
                val resultRoom = move(command.action)
                return if (resultRoom != null) {
                    GameState(resultRoom)
                } else {
                    GameState(this, false)
                }
            }
            else -> {
                executeCommand(command)
            }
        }
    }

    fun move(moveAction: Action): Room? {
        val moveToHere: Room? = adjacentRooms[moveAction]

        return if (moveToHere != null) {
            moveAction.display
            moveToHere
        } else {
            println("You can't go that way.")
            null
        }
    }

    fun executeCommand(command: Command): GameState {

        return when (command.action) {
            TAKE,
            DROP,
            INVENTORY,
            EXAMINE -> {
                GameState(this, false)
            }
            LOOK -> {
                println(this.describe())
                GameState(this, false)
            }
            else -> {
                GameState(this, false)
            }
        }
    }

    fun listItems(): String {
        return if (items.isEmpty()) "" else {
            "You can see..." +
                    items.joinToString { "\n\t" }
        }
    }

}

class StartingRoom : Room("Cellar", """
    |This windowless cellar is dim and still.
    |In the center of the room, surrounded by a ring of loose soil, is a large, roughly-hewn hole in the earthen floor.
    |The hole looks big enough to accommodate a person.""".trimMargin()) {
}

class HoleEntrance : Room("The Hole",
        """You are in a large, earthen hole. Your head just barely peeks over the top. The bare soil is loose and moist.
    |Above, a naked bulb emits weak yellow light.
    |Below, by your feet, a narrow tunnel curves downward and plunges into absolute darkness.
""".trimMargin()) {
}

class DarkTunnel : Room("Dark Tunnel", """Muddy and dark, the tunnel is only just large enough to crawl through.
    |The steep slope makes it impossible to back up. There's no going back.
    |The dark tunnel continues downward.""".trimMargin())

class DarkTunnelEnd : Room("Tunnel End", """
    You find yourself at the end of a rocky cave. It's quite dark. Moisture gently glints off the rough stone of the cave walls.
    You can see the outline of a muddy hole in the loose rock above.
    To the east, a dim light.
    """.trimIndent())


// FIXME: replace with DSL?

class World {
    val start = StartingRoom()
    private val holeEntrance = HoleEntrance()
    private val darkTunnel = DarkTunnel()
    private val caveEnd = DarkTunnelEnd()

    init {
        start.adjacentRooms[Action.MOVE_DOWN] = holeEntrance
        holeEntrance.adjacentRooms[Action.MOVE_UP] = start
        holeEntrance.adjacentRooms[Action.MOVE_DOWN] = darkTunnel
        darkTunnel.adjacentRooms[Action.MOVE_DOWN] = caveEnd
    }
}




