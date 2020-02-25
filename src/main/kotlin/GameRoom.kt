import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@JsonClass(generateAdapter = true)
data class GameRoom(val name: String) {
}

class GameWorld {

    val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    //val typeAdapter

    init {
        val data = GameWorld::class.java.getResourceAsStream("test_world_01.json").bufferedReader().readText()

        val typeAdapter = Types.newParameterizedType(List::class.java, GameRoom::class.java)
        val adapter = moshi.adapter<List<GameRoom>>(typeAdapter)
        val rooms = adapter.fromJson(data)
        println(rooms)
    }

}