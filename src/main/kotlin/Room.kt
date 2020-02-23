import Action.*

abstract class Room(val name: String, val description: String) {

    open val adjacentRooms: MutableMap<Action, Room> = mutableMapOf()

    open val items: MutableList<Item> = mutableListOf()

    fun describe(): String {
        return """$name
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
                println(this.listItems())
                GameState(this, false)
            }
            else -> { GameState(this, false) }
        }
    }

    fun listItems(): String {
        return if (items.isEmpty()) "" else { "You can see..." +
            items.joinToString { "\n\t" }
        }
    }

}

class StartingRoom : Room("Basement", """
    |This windowless basement is dim and still.
    |In the center of the room, surrounded by a pile of loose earth, is a large, roughly-hewn hole which plunges into absolute darkness.
    |The hole looks big enough to accommodate a person.""".trimMargin()) {
}

class HoleEntrance : Room("The Hole", """You are in a large, earthen hole, several feet deep. The bare soil is loose and moist. It does not appear to be man-made.
    |Above, a naked bulb emits weak yellow light.
    |Below, a tunnel curves downward.
""".trimMargin()) {
}

class DarkTunnel : Room("Dark Tunnel", """Muddy and dark, the tunnel is only large enough to crawl through.
    |As it slopes further downward, it becomes impossible to back up. There's no going back.
    |Further down, the air feels crisp and fresh."""".trimMargin())

class CaveEnd : Room("Cave End", """This is a dumb place""")



// FIXME: replace with DSL?

class World {
    val start = StartingRoom()
    private val holeEntrance = HoleEntrance()
    private val darkTunnel = DarkTunnel()
    private val caveEnd = CaveEnd()

    init {
        start.adjacentRooms[Action.MOVE_DOWN] = holeEntrance
        holeEntrance.adjacentRooms[Action.MOVE_UP] = start
        holeEntrance.adjacentRooms[Action.MOVE_DOWN] = darkTunnel
        darkTunnel.adjacentRooms[Action.MOVE_DOWN] = caveEnd
    }
}




