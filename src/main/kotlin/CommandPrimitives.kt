val ignoreThese = listOf("the", "go")

enum class Action(val keyWords: List<String> = listOf(), val display: String = "???") {
    MOVE_NORTH(listOf("north", "n"), "Going north..."),
    MOVE_EAST(listOf("east", "e"), "Going east..."),
    MOVE_SOUTH(listOf("south", "s"), "Going south..."),
    MOVE_WEST(listOf("west", "w"), "Going west..."),
    MOVE_UP(listOf("up", "u"),"Going up..."),
    MOVE_DOWN(listOf("down", "d"), "Heading down..."),

    TAKE(listOf("take"), "You take "),
    DROP(listOf("drop"), "You drop "),
    EXAMINE(listOf("examine"), "You look at "),

    LOOK(listOf("look", "l"), ""),

    INVENTORY(listOf("inventory", "i"), "You are carrying: "),
    WAIT(listOf("wait", "z"), "Time passes..."),

    NOTHING(emptyList(), "You can't do that.");
}

class Item(val name: String, val description: String)

data class Command(val action: Action = Action.NOTHING, val target: GameItem? = null)
