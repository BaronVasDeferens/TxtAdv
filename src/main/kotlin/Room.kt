open class Room (val name: String, val description: String) {

    val adjacentRooms : MutableList<Room> = mutableListOf()

    fun describe(): String {
        return description
    }

}

val startingRoom = Room("", "")