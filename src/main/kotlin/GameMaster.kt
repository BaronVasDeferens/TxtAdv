class GameMaster(worldFile: String = "test_world_01.json") {

    val gameMap = GameMap(worldFile)
    val startingRoom = gameMap.startingRoom
    val carriedItems =  gameMap.carriedItems

    var state = GameState(startingRoom, carriedItems)

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
            state.currentRoom.interactiveObjects.firstOrNull { obj -> obj.hasKeyword(targetToken) }
                    ?: state.carriedItems.firstOrNull { item -> item.hasKeyword(targetToken) }
        } catch (e: Exception) {
            null
        }

        return Command(parsedAction, parsedTarget)
    }

    fun executeCommand(command: Command) {
        state = state.performAction(command)
    }
}