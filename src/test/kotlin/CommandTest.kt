import org.junit.Test

class CommandTest {

    @Test
    fun `should parse move commands`() {
        val gameMaster = GameMaster()
        assert(gameMaster.parseText("north").action == Action.MOVE_NORTH)
        assert(gameMaster.parseText("n").action == Action.MOVE_NORTH)
        assert(gameMaster.parseText("east").action == Action.MOVE_EAST)
        assert(gameMaster.parseText("w").action == Action.MOVE_WEST)
        assert(gameMaster.parseText("south").action == Action.MOVE_SOUTH)
        assert(gameMaster.parseText("go north").action == Action.MOVE_NORTH)
        assert(gameMaster.parseText("up").action == Action.MOVE_UP)
        assert(gameMaster.parseText("d").action == Action.MOVE_DOWN)


    }

    @Test
    fun `should parse misc commands`() {
        val gameMaster = GameMaster()
        assert(gameMaster.parseText("wait").action == Action.WAIT)
        assert(gameMaster.parseText("").action == Action.NOTHING)
    }

    @Test
    fun `should parse commands wth targets`() {

    }

}