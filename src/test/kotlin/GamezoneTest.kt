import model.directions.Directions
import model.gamezone.Cell
import model.gamezone.Gamezone
import model.gamezone.Grid
import model.hero.Hero
import model.item.HeroItem
import model.map_character.MapCharacter
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull

class GamezoneTest {

    private lateinit var grid: Grid
    private lateinit var gamezone: Gamezone
    private lateinit var mapCharacter: MapCharacter

    @Before
    fun setup() {
        grid = Grid(size = 4) //setup grid 4x4
        gamezone = Gamezone(grid = grid) //setup Gamezone with 4x4 grid
        mapCharacter = Hero.BowHero(name = "All Mighty",stealthPoints = 50, health = 100, heroItem = HeroItem.Weapon(name="Sword"))

    }

    @Test
    fun `moving Character North from (0,0) position must update grid with Character at position (0,1)`() {
        gamezone.setCharacterAtCell(Cell(0,0), mapCharacter)
        gamezone.moveCharacter(mapCharacter, Directions.North)
        val cellAtExpectedPosition = Cell(0, 1)
        val cellValue = gamezone.getCellValue(cellAtExpectedPosition)

        assertNotNull(cellValue)

    }

    @Test
    fun `moving Character East from (0,0) position must update grid with Character at position (1,0)`() {
        gamezone.setCharacterAtCell(Cell(0,0), mapCharacter)
        gamezone.moveCharacter(mapCharacter, Directions.East)
        val cellAtExpectedPosition = Cell(1, 0)
        val cellValue = gamezone.getCellValue(cellAtExpectedPosition)

        assertNotNull(cellValue)

    }

}