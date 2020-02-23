object TxtAdv {



    @JvmStatic
    fun main (args: Array<String>) {

        val gm = GameMaster()
        val sin = System.`in`.bufferedReader()
        var playerInput = sin.readLine()

        while (!playerInput.contentEquals("q")) {
            val response = gm.parseText(playerInput)
            println(response.action.display)
            playerInput = sin.readLine()
        }


    }


}