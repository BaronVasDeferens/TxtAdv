class GameMaster(worldFile: String) {

    var state = GameState(GameWorld(worldFile).startingRoom)

    init {
        state.currentRoom.describe()
    }

    fun parseText(command: String): Command {

        val tokens: List<String> = command
                .split(" ")
                .filterNot { input -> ignoreThese.any { it == input } }
                .map { it.toLowerCase() }
                .toList()

        val parsedAction = Action.values()
                .toList()
                .firstOrNull { action: Action ->
                    action.keyWords.any { param -> param == tokens[0] }
                } ?: Action.NOTHING

        // Find target, if any
        val parsedTarget = try {
            val targetToken = tokens[1]
            if (state.currentRoom.interactiveObject?.keywords?.any { it == targetToken } == true) {
                state.currentRoom.interactiveObject
            } else null
        } catch (e: Exception) {
            null
        }

        return Command(parsedAction, parsedTarget)
    }

    fun executeCommand(command: Command) {

        // TODO: move into GameState

        state = when (command.action) {
            Action.NOTHING -> {
                println("You can't do that!")
                state
            }
            Action.INVENTORY -> {
                println(state.displayInventory())
                state
            }
            Action.ACTIVATE,
            Action.DEACTIVATE,
            Action.EXAMINE -> {
                if (command.target != null) {
                    val objectAction = command.target.objectActions.firstOrNull { it.action == command.action && command.target.state == it.startState }
                    objectAction?.let { command.target.triggerAction(it) }
                } else {
                    println("You can't do that to ${command.target!!.name}.")
                }
                state
            }
            Action.WAIT -> {
                println(Action.WAIT.display)
                state
            }
            Action.QUIT -> {
                state
            }
            Action.LOOK,
            Action.MOVE_NORTH,
            Action.MOVE_EAST,
            Action.MOVE_SOUTH,
            Action.MOVE_WEST,
            Action.MOVE_UP,
            Action.MOVE_DOWN,
            Action.MOVE_IN,
            Action.MOVE_OUT -> {
                state.performAction(command)
            }

            else -> {
                println("...?")
                state
            }
        }
    }
}