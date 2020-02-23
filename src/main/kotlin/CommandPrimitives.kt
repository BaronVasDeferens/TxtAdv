val ignoreThese = listOf("the", "go")

enum class Action(val params: List<String> = listOf()) {
    MOVE_NORTH(listOf("north", "n")),
    MOVE_EAST(listOf("east", "e")),
    MOVE_SOUTH(listOf("south", "s")),
    MOVE_WEST(listOf("west", "w")),
    TAKE(listOf("take")),
    DROP(listOf("drop")),
    EXAMINE(listOf("examine", "look")),
    WAIT(listOf("wait", "z")),
    NOTHING();
}

class Item(val name: String, val description: String)

data class Command(val action: Action = Action.NOTHING, val target: Item? = null)
