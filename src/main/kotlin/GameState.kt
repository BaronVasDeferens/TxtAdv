import Action.*

data class GameState(
        val currentRoom: GameRoom,
        val carriedItems: MutableList<InteractiveObject> = mutableListOf()) {

    companion object {
        fun doublePrint(msg: String) {
            println("$msg\n")
        }
    }

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

            DEBUG -> {
                println(currentRoom)
                println(carriedItems)
                this
            }

            TAKE -> {
                if (command.target != null) {
                    if (currentRoom.interactiveObjects.remove(command.target)) {
                        carriedItems.add(command.target)
                        doublePrint(command.target.triggerAction(command.target.objectActions.first { it.action == TAKE }))
                    } else {
                        doublePrint("That item is either not here or in your inventory already.")
                    }

                    this.copy()
                } else {
                    doublePrint("Say what, now?")
                    this
                }
            }

            DROP -> {

                if (command.target != null && carriedItems.contains(command.target)) {
                    if (carriedItems.remove(command.target)) {
                        currentRoom.interactiveObjects.add(command.target)
                        doublePrint(command.target.triggerAction(command.target.objectActions.first { it.action == DROP }))
                    } else {
                        doublePrint("That item is not in your inventory...")
                    }
                } else {
                    doublePrint("That item is not in your inventory.")
                }

                this.copy()
            }

            ACTIVATE,
            DEACTIVATE,
            EXAMINE -> {
                if (command.target != null) {
                    val objectAction = command.target.objectActions.firstOrNull { it.action == command.action && command.target.state == it.startState }
                    objectAction?.let { doublePrint(command.target.triggerAction(it))}
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
        }
    }

    fun move(moveAction: Action): GameRoom {

        val moveToHere: GameRoom? = currentRoom.adjacentRooms[moveAction]

        return if (moveToHere != null) {
            if (!moveToHere.beenVisited) {
                doublePrint(moveToHere.describe())
                moveToHere.beenVisited = true
            } else {
                doublePrint(moveToHere.name)
            }
            moveToHere
        } else {
            doublePrint("You can't go that way.")
            currentRoom
        }
    }

}