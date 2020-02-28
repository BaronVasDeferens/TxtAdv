import Action.*

class TxtAdv {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val gameMaster = GameMaster("test_world_01.json")

            val sin = System.`in`.bufferedReader()
            var playerInput = ""
            var parsedCommand = Command(NOTHING)

            while (parsedCommand.action != QUIT) {
                playerInput = sin.readLine()
                parsedCommand = gameMaster.parseText(playerInput)
                gameMaster.executeCommand(parsedCommand)
            }

            println("Goodbye.")
        }
    }
}

