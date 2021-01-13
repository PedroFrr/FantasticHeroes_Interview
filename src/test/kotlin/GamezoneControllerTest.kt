import controller.GamezoneController
import model.directions.Directions
import model.gamezone.Cell
import model.gamezone.Gamezone
import model.gamezone.Grid
import model.hero.Hero
import model.monster.Monster
import org.junit.Before
import org.junit.Test
import utils.longSword
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

//TODO Refactor tests as I think some things can be in the setup since they repeat
class GamezoneControllerTest {

    private lateinit var grid: Grid
    private lateinit var gamezone: Gamezone
    private lateinit var hero: Hero
    private lateinit var gamezoneController: GamezoneController
    private lateinit var monster: Monster

    @Before
    fun setup() {
        grid = Grid(size = 4) //setup grid 4x4
        gamezone = Gamezone(grid = grid) //setup Gamezone with 4x4 grid
        gamezoneController = GamezoneController(gamezone)
        hero = Hero.BowHero(name = "All Mighty",stealthPoints = 50, heroItem = longSword)
        monster = Monster(5000.0)

    }

    @Test
    fun `moving Character North from (0,0) position must update grid with Character at position (0,1)`() {
        gamezone.addCharacterToCell(Cell(0,0), hero)
        gamezoneController.moveCharacter(hero, Directions.North)
        val cellAtExpectedPosition = Cell(0, 1)
        val cellValue = gamezone.getCellValue(cellAtExpectedPosition)

        assertNotNull(cellValue)

    }

    @Test
    fun `moving Character East from (0,0) position must update grid with Character at position (1,0)`() {
        gamezone.addCharacterToCell(Cell(0,0), hero)
        gamezoneController.moveCharacter(hero, Directions.East)
        val cellAtExpectedPosition = Cell(1, 0)
        val cellValue = gamezone.getCellValue(cellAtExpectedPosition)

        assertNotNull(cellValue)

    }

    //TODO Refactor. Depends on the move character. I don't think I need to do getCellValue so many times
    @Test
    fun `when moving character from Cell, the Cell he moved from should be null`() {
        //I have to force this position otherwise the Hero might be randomly put on a edge and in this case he will not move (meaning its previous cell will not become null)
        gamezone.addCharacterToCell(Cell(0,0), hero)
        val characterCell = gamezone.getCharacterCell(hero)
        gamezoneController.moveCharacter(hero, Directions.East)
        val getValueForCellWhereCharacterWas = gamezone.getCellValue(characterCell)

        assertNull(getValueForCellWhereCharacterWas)
    }

    @Test
    fun `after putting a MapCharacter on the map its position shouldn't be null`(){
        val shieldHero = Hero.ShieldHero(name = "One for all", defense = 9000, heroItem = longSword)
        gamezoneController.putCharacterOnGamezoneFirstTime(shieldHero)
        //after the Hero is put on the Gamezone its position should stop being null
        assertNotNull(shieldHero)
    }

    @Test
    fun `if MapCharacter is at xCoordinate edge he cannot move East`(){
        val shieldHero = Hero.ShieldHero(name = "One for all", defense = 9000, heroItem = longSword)
        val cellAtTheEdge = Cell(3,3) //since the map is 4x4, this represents the edge of the gamezone
        gamezone.addCharacterToCell(cellAtTheEdge, shieldHero)

        gamezoneController.moveCharacter(shieldHero, Directions.East)
        val mapCharacterCellAfterMovement = gamezone.getCharacterCell(shieldHero)

        //TODO maybe he shouldn't remain on the same place but instead return an error or something like that
        assertEquals(cellAtTheEdge, mapCharacterCellAfterMovement) //the Character should remain on the same place

    }

    @Test
    fun `when the gamezone has 1 hero the total count of heroes must be 1`(){
        gamezoneController.putCharacterOnGamezoneFirstTime(hero)
        val numberOfMapHeroes = gamezoneController.getNumberOfHeroesOnTheMap()

        assertEquals(1, numberOfMapHeroes)

    }

    @Test
    fun `when the gamezone has 1 hero and 1 monster the total count of heroes must be 1`(){
        gamezoneController.putCharacterOnGamezoneFirstTime(hero)
        gamezoneController.putCharacterOnGamezoneFirstTime(monster)
        val numberOfMapHeroes = gamezoneController.getNumberOfHeroesOnTheMap()

        assertEquals(1, numberOfMapHeroes)

    }

    @Test
    fun `when the gamezone has 1 hero and 1 monster the total number of characters on the map must be 2`(){
        gamezoneController.putCharacterOnGamezoneFirstTime(hero)
        gamezoneController.putCharacterOnGamezoneFirstTime(monster)
        val numberOfMapCharacters = gamezoneController.getNumberOfCharactersOnTheMap()

        assertEquals(2, numberOfMapCharacters)
    }

    @Test
    fun `when the monster wins the combat between a hero the hero health must be equals or less than 0`(){
        gamezoneController.putCharacterOnGamezoneFirstTime(hero)
        gamezoneController.setFightBetweenOpponents(hero, monster)

        assertTrue(hero.health <= 0)
    }

    @Test
    fun `if an Hero fights with a Monster and looses it must disappear from the Gamezone`(){
        gamezoneController.putCharacterOnGamezoneFirstTime(hero)
        val heroCell = gamezone.getCharacterCell(hero)
        val getCellValueBeforeCombat = gamezone.getCellValue(heroCell)
        assertNotNull(getCellValueBeforeCombat)

        gamezoneController.setFightBetweenOpponents(hero, monster)
        val getCellValueAfterCombat = gamezone.getCellValue(heroCell)

        assertNull(getCellValueAfterCombat)
    }

    
}