import Action.*

data class GameState(
        val currentRoom: Room,
        val success: Boolean = true) {

    val carriedItems: MutableList<Item> = mutableListOf()

    init {
        if (success)
            println(onEnter())
    }

    fun onEnter(): String {
        return currentRoom.describe()
    }

    fun displayInventory(): String {
        return "You are holding..." +
        if (carriedItems.isEmpty()) {
            "nothing!" }
        else {
            carriedItems.map { it.name }.joinToString { "\n" }
        }
    }


    fun performAction(command: Command): GameState {
        return when (command.action) {
            MOVE_UP,
            MOVE_DOWN,
            MOVE_IN,
            MOVE_OUT,
            MOVE_NORTH,
            MOVE_EAST,
            MOVE_SOUTH,
            MOVE_WEST -> {
                val resultRoom = move(command.action)
                return if (resultRoom != null) {
                    GameState(resultRoom)
                } else {
                    this
                }
            }
            else -> {
                executeCommand(command)
            }
        }
    }


    fun move(moveAction: Action): Room? {

        val moveToHere: Room? = currentRoom.adjacentRooms[moveAction]

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
            LOOK -> {
                println(currentRoom.describe())
                this
            }
            else -> {
                this
            }
        }
    }

}