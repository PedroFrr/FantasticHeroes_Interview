import controller.GamezoneController
import model.directions.Directions
import model.game_state.GameState
import model.gamezone.Gamezone
import model.gamezone.Grid
import utils.*

fun main() {
    val grid = Grid(size = 4)
    val gamezone = Gamezone(grid)
    val gamezoneController = GamezoneController(gamezone)

    //Sets some Characters on the Gamezone
    gamezoneController.putCharacterOnGamezoneFirstTime(allMighty)
    gamezoneController.putCharacterOnGamezoneFirstTime(dragon)
    gamezoneController.putCharacterOnGamezoneFirstTime(ogre)
    gamezoneController.putCharacterOnGamezoneFirstTime(angel)
    gamezoneController.putCharacterOnGamezoneFirstTime(elf)
    gamezoneController.putCharacterOnGamezoneFirstTime(bossMonster)


    println(gamezoneController.printGamezoneEntries())

    while(gamezoneController.getGameState() == GameState.GameOngoing){
        //Random Direction for the round
        val randomDirection = mutableListOf(Directions.North, Directions.East, Directions.South, Directions.West).shuffled().first()

        gamezoneController.moveCharacter(allMighty, randomDirection)

    }

    val message = when (gamezoneController.getGameState()){
        GameState.GameWon -> "Heroes WONN"
        GameState.GameLost -> "Monsters won :C"
        GameState.GameOngoing -> "Game ongoing"
    }

    println("-------------------------------------------------------------------------------------------")
    println(message)



}