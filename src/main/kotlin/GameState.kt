class GameState(val currentRoom: Room, val success: Boolean = true) {

    val carriedItems: MutableList<Item> = mutableListOf()


    init {
        if (success)
            println(onEnter())
    }

    fun onEnter(): String {
        return currentRoom.describe()
    }


}