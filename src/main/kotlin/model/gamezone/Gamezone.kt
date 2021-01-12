package model.gamezone

import model.hero.Hero
import model.map_character.MapCharacter
import model.monster.Monster

/**
 * The Gamezone where monsters and heroes reside.
 *
 * This class allows the retrieval and definition of data on the Gamezone.
 */
class Gamezone(val grid: Grid) {

    //Represents a map with each cell and the corresponding Character (can be null if no Character exists at the position)
    val cellValues = mutableMapOf<Cell, MapCharacter?>()

    init {
        //Initialize the grid with empty (null) Cells
        grid.cells.forEach { unit -> unit.forEach { cell -> cellValues[cell] = null } }
    }


    //Given the Cell (key) returns the Character or null if Cell is empty
    fun getCellValue(cell: Cell): MapCharacter? = cellValues[cell]

    //Returns the Character cell
    fun getCharacterCell(character: MapCharacter): Cell = cellValues.filterValues { it == character }.keys.first()


    //Empties Value for the given cell
    fun setCellAsEmpty(cell: Cell) {
        cellValues[cell] = null
    }

    //Adds Character to the specified Cell on the Gamezone
    fun addCharacterToCell(cell: Cell, character: MapCharacter) {
        cellValues[cell] = character //updates Cell with given Character
    }

    fun getNumberOfHeroesOnTheMap(): Int = cellValues.filter { it.value is Hero }.count()

    fun getNumberOfCharactersOnTheMap(): Int = cellValues.filter { it.value != null }.count()

    fun isDifferentMapCharacterOnCell(character: MapCharacter, cell: Cell): Boolean {
        val characterOnCell = getCellValue(cell)
        return (character is Hero && characterOnCell is Monster) || (character is Monster && characterOnCell is Hero)
    }


}