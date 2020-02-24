import org.junit.Ignore
import org.junit.Test

class CommandTest {

    @Test
    fun `should parse move commands`() {
        val gameMaster = GameMaster()
        assert(gameMaster.parseText("north").action == Action.MOVE_NORTH)
        assert(gameMaster.parseText("N").action == Action.MOVE_NORTH)
        assert(gameMaster.parseText("east").action == Action.MOVE_EAST)
        assert(gameMaster.parseText("w").action == Action.MOVE_WEST)
        assert(gameMaster.parseText("SOUTH").action == Action.MOVE_SOUTH)
        assert(gameMaster.parseText("go north").action == Action.MOVE_NORTH)
        assert(gameMaster.parseText("up").action == Action.MOVE_UP)
        assert(gameMaster.parseText("d").action == Action.MOVE_DOWN)
        assert(gameMaster.parseText("in").action == Action.MOVE_IN)
        assert(gameMaster.parseText("out").action == Action.MOVE_OUT)
    }

    @Test
    fun `should parse misc commands`() {
        val gameMaster = GameMaster()
        assert(gameMaster.parseText("i").action == Action.INVENTORY)
        assert(gameMaster.parseText("wait").action == Action.WAIT)
        assert(gameMaster.parseText("Z").action == Action.WAIT)
        assert(gameMaster.parseText("q").action == Action.QUIT)
        assert(gameMaster.parseText("QUIT").action == Action.QUIT)
        assert(gameMaster.parseText("").action == Action.NOTHING)
    }

    @Ignore
    @Test
    fun `should parse commands wth targets`() {

    }

}