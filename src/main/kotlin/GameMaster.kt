class GameMaster {

    fun parseText(command: String): Command {

        val tokens: List<String> = command
                .split(" ")
                .filterNot { input -> ignoreThese.any { it == input } }
                .toList()

        val parsedAction = Action.values()
                .toList()
                .firstOrNull { action: Action ->
                    action.params.any { param -> param == tokens[0] }
                } ?: Action.NOTHING


        println("${tokens[0]} -> $parsedAction")

        return Command(parsedAction)
    }


}