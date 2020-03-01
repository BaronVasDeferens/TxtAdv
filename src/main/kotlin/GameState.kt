import Action.*

data class GameState(
        val currentRoom: GameRoom,
        val success: Boolean = true) {

    companion object {
        fun doublePrint(msg: String) {
            println("$msg\n")
        }
    }

    val carriedItems: MutableList<InteractiveObject> = mutableListOf()

    fun describeCurrentRoom() {
        doublePrint(currentRoom.describe())
    }

    fun displayInventory() {
        var msg = "You are holding ${carriedItems.size} items..."
        carriedItems.forEach { msg += "\n\t${it.name}" }
        doublePrint(msg)
    }

    fun performAction(command: Command): GameState {

        return when (command.action) {
            NOTHING -> {
                doublePrint("I don't know what you mean...")
                this
            }
            INVENTORY -> {
                displayInventory()
                this
            }
            LOOK -> {
                describeCurrentRoom()
                this
            }

            TAKE -> {
                if (command.target != null) {
                    val x =currentRoom.interactiveObjects.remove(command.target)
                    val y = carriedItems.add(command.target)
                    doublePrint("Taken. $x $y")
                    this
                } else {
                    doublePrint("Say what, now?")
                    this
                }
            }

            ACTIVATE,
            DEACTIVATE,
            EXAMINE -> {
                if (command.target != null) {
                    val objectAction = command.target.objectActions.firstOrNull { it.action == command.action && command.target.state == it.startState }

                    objectAction?.let { command.target.triggerAction(it) }
                } else {
                    doublePrint("You can't do that.")
                }
                this
            }
            WAIT -> {
                doublePrint(WAIT.display)
                this
            }
            QUIT -> {
                this
            }

            MOVE_NORTH,
            MOVE_EAST,
            MOVE_SOUTH,
            MOVE_WEST,
            MOVE_UP,
            MOVE_DOWN,
            MOVE_IN,
            MOVE_OUT -> {
                this.copy(currentRoom = move(command.action))
            }

            else -> {
                doublePrint("I don't understand that.")
                this
            }
        }
    }

    fun move(moveAction: Action): GameRoom {

        val moveToHere: GameRoom? = currentRoom.adjacentRooms[moveAction]

        return if (moveToHere != null) {
            if (moveToHere.beenVisited == false) {
                doublePrint(moveToHere.describe())
                moveToHere.beenVisited = true
            }
            moveAction.display
            moveToHere
        } else {
            doublePrint("You can't go that way.")
            currentRoom
        }
    }

}