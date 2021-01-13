import controller.GamezoneController
import model.directions.Directions
import model.gamezone.Gamezone
import model.gamezone.Grid
import utils.*

fun main() {
    val grid = Grid(size = 4)
    val gamezone = Gamezone(grid)
    val gamezoneController = GamezoneController(gamezone)

    //Sets some Characters on the Gamezone
    gamezoneController.putCharacterOnGamezoneFirstTime(allMighty)
    gamezoneController.putCharacterOnGamezoneFirstTime(carnivoreMonster)
    gamezoneController.putCharacterOnGamezoneFirstTime(herbivoreMonster)
    gamezoneController.putCharacterOnGamezoneFirstTime(herbivoreMonster)
    gamezoneController.putCharacterOnGamezoneFirstTime(herbivoreMonster)
    gamezoneController.putCharacterOnGamezoneFirstTime(bossMonster)


    println(gamezoneController.printGamezoneEntries())

    while(gamezoneController.getNumberOfHeroesOnTheMap() > 0){
        //Random Direction for the round
        val randomDirection = mutableListOf(Directions.North, Directions.East, Directions.South, Directions.West).shuffled().first()

        gamezoneController.moveCharacter(allMighty, randomDirection)

    }

    println("Game END")



}