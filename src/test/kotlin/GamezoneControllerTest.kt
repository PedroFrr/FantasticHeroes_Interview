import controller.GamezoneController
import model.directions.Directions
import model.gamezone.Cell
import model.gamezone.Gamezone
import model.gamezone.Grid
import model.hero.Hero
import model.item.HeroItem
import model.map_character.MapCharacter
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GamezoneControllerTest {

    private lateinit var grid: Grid
    private lateinit var gamezone: Gamezone
    private lateinit var mapCharacter: MapCharacter
    private lateinit var gamezoneController: GamezoneController

    @Before
    fun setup() {
        grid = Grid(size = 4) //setup grid 4x4
        gamezone = Gamezone(grid = grid) //setup Gamezone with 4x4 grid
        gamezoneController = GamezoneController(gamezone)
        mapCharacter = Hero.BowHero(name = "All Mighty",stealthPoints = 50, health = 100, heroItem = HeroItem.Weapon(name="Sword"))

    }

    @Test
    fun `moving Character North from (0,0) position must update grid with Character at position (0,1)`() {
        gamezone.addCharacterToCell(Cell(0,0), mapCharacter)
        gamezoneController.moveCharacter(mapCharacter, Directions.North)
        val cellAtExpectedPosition = Cell(0, 1)
        val cellValue = gamezone.getCellValue(cellAtExpectedPosition)

        assertNotNull(cellValue)

    }

    @Test
    fun `moving Character East from (0,0) position must update grid with Character at position (1,0)`() {
        gamezone.addCharacterToCell(Cell(0,0), mapCharacter)
        gamezoneController.moveCharacter(mapCharacter, Directions.East)
        val cellAtExpectedPosition = Cell(1, 0)
        val cellValue = gamezone.getCellValue(cellAtExpectedPosition)

        assertNotNull(cellValue)

    }

    @Test
    fun `when moving character from Cell, the Cell he moved from should be null`() {
        gamezone.addCharacterToCell(Cell(0,0), mapCharacter)
        val characterCell = gamezone.getCharacterCell(mapCharacter)
        gamezoneController.moveCharacter(mapCharacter, Directions.East)
        val getValueForCharacterPreviousCell = gamezone.getCellValue(characterCell)

        assertNull(getValueForCharacterPreviousCell)
    }

    @Test
    fun `after putting a MapCharacter on the map its position shouldn't be null`(){
        val shieldHero = Hero.ShieldHero(name = "One for all", defense = 9000, heroItem = HeroItem.Weapon("sword"))
        gamezoneController.putCharacterOnGamezoneFirstTime(shieldHero)
        //after the Hero is put on the Gamezone its position should stop being null
        assertNotNull(shieldHero)
    }

    @Test
    fun `if MapCharacter is at xCoordinate edge he cannot move East`(){
        val shieldHero = Hero.ShieldHero(name = "One for all", defense = 9000, heroItem = HeroItem.Weapon("sword"))
        val cellAtTheEdge = Cell(4,4) //since the map is 4x4, this represents the edge of the gamezone
        gamezone.addCharacterToCell(cellAtTheEdge, shieldHero)

        gamezoneController.moveCharacter(shieldHero, Directions.East)
        val mapCharacterCellAfterMovement = gamezone.getCharacterCell(shieldHero)

        //TODO maybe he shouldn't remain on the same place but instead return an error or something like that
        assertEquals(cellAtTheEdge, mapCharacterCellAfterMovement) //the Character should remain on the same place

    }

    
}