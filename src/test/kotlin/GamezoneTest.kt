import model.gamezone.Cell
import model.gamezone.Gamezone
import model.gamezone.Grid
import model.hero.Hero
import model.map_character.MapCharacter
import org.junit.Before
import org.junit.Test
import utils.shortSword
import kotlin.test.assertEquals

class GamezoneTest {

    private lateinit var grid: Grid
    private lateinit var gamezone: Gamezone
    private lateinit var mapCharacter: MapCharacter

    @Before
    fun setup() {
        grid = Grid(size = 4) //setup grid 4x4
        gamezone = Gamezone(grid = grid) //setup Gamezone with 4x4 grid
        mapCharacter = Hero.BowHero(name = "All Mighty",stealthPoints = 50, heroItem = shortSword)
    }

    @Test
    fun `character must be returned from cell with character`(){
        val cell = Cell(0,0)
        gamezone.addCharacterToCell(cell, mapCharacter)

        val getCellValue = gamezone.getCellValue(cell)

        assertEquals(mapCharacter, getCellValue)
    }


}