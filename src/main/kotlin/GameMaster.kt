class GameMaster {

    var state = GameState(World().start)

    init {
        state.currentRoom.describe()
    }

    fun parseText(command: String): Command {

        val tokens: List<String> = command
                .split(" ")
                .filterNot { input -> ignoreThese.any { it == input } }
                .map{ it.toLowerCase() }
                .toList()

        val parsedAction = Action.values()
                .toList()
                .firstOrNull { action: Action ->
                    action.keyWords.any { param -> param == tokens[0] }
                } ?: Action.NOTHING

        val parsedTarget = try {
            tokens[1]
        } catch (e: Exception) {
            null
        }

        return Command(parsedAction)
    }

    fun executeCommand(command: Command) {
        state = when (command.action) {
            Action.NOTHING -> {
                println("You can't do that!")
                state
            }
            Action.QUIT -> {
                state
            }
            else -> state.performAction(command)
        }
    }
}