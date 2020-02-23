object TxtAdv {



    @JvmStatic
    fun main (args: Array<String>) {

        println("""TITLE OF GAME
            |A Text Adventure by Scott West
            |2020 Derp Interactive
            |
        """.trimMargin())

        val gm = GameMaster()

        val sin = System.`in`.bufferedReader()
        var playerInput = sin.readLine()

        while (!playerInput.contentEquals("q")) {
            val parsedCommand = gm.parseText(playerInput)
            val newState = gm.executeCommand(parsedCommand, gm.state)
            if (newState.success) {
                gm.state = newState
            }

            playerInput = sin.readLine()
        }

        println("You have quit the game.")

    }


}