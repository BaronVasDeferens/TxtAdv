class GameMaster(worldFile: String = "test_world_01.json") {

    var state = GameState(GameWorld(worldFile).startingRoom)

    init {
        state.describeCurrentRoom()
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
        state = state.performAction(command)
    }
}