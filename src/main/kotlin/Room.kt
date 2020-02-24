import Action.*

sealed class Room(val name: String, val description: String) {

    open val adjacentRooms: MutableMap<Action, Room> = mutableMapOf()

    open val items: MutableList<GameItem> = mutableListOf()

    fun describe(): String {
        return """
            |$name
            |$description
            |${listItems()}""".trimMargin()
    }


    fun listItems(): String {
        return if (items.isEmpty()) "" else {
            "There is a " +
                    items.joinToString { "here.\n" }
        }
    }

}

class StartingRoom : Room("Cellar",
        """A windowless cellar, dim and still. The small room is lined with crude wooden shelves, all sagging under the weight 
    |of dusty, useless junk. 
    |In the center of the room, surrounded by a ring of loose soil, is a large, roughly-hewn hole in the earthen floor.
    |The hole is deep and wide enough to accommodate a person.""".trimMargin()) {

//    override val items = mutableListOf(GameItem("keycard", "A yellowed plastic key card with a mag-stripe on the back."))
}

class HoleEntrance : Room("The Hole",
        """You are in a large, earthen hole. Your head just barely peeks over the top. The bare soil is loose and moist.
    |Above, a naked bulb emits weak yellow light.
    |By your feet is a narrow tunnel that curves downward and plunges into absolute darkness.
""".trimMargin()) {
}

class DarkTunnel : Room("Dark Tunnel",
        """Muddy and dark, the tunnel is only just large enough to crawl through.
    |The steep slope makes it impossible to back up. There's no going back.
    |The dark tunnel continues downward.""".trimMargin())

class DarkTunnelEnd : Room("Tunnel End", """
    You find yourself at the end of a rocky cave. It's quite dark. Moisture gently glints off the rough stone of the cave walls.
    You can see the outline of a muddy hole in the loose rock above.
    To the east, a dim light flickers.
    """.trimIndent())


// FIXME: replace with DSL?

class World {
    val start = StartingRoom()
    private val holeEntrance = HoleEntrance()
    private val darkTunnel = DarkTunnel()
    private val caveEnd = DarkTunnelEnd()

    init {

        println("""TITLE OF GAME
            |A Text Adventure by Scott West
            |2020 Derp Interactive
            |
        """.trimMargin())

        start.adjacentRooms[MOVE_IN] = holeEntrance
        holeEntrance.adjacentRooms[MOVE_OUT] = start
        holeEntrance.adjacentRooms[MOVE_DOWN] = darkTunnel
        darkTunnel.adjacentRooms[MOVE_DOWN] = caveEnd
    }
}




