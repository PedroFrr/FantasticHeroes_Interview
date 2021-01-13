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
    val gamezoneEntries = mutableMapOf<Cell, MapCharacter?>()

    init {
        //Initialize the grid with empty (null) Cells
        grid.cells.forEach { unit -> unit.forEach { cell -> gamezoneEntries[cell] = null } }
    }


    //Given the Cell (key) returns the Character or null if Cell is empty
    fun getCellValue(cell: Cell): MapCharacter? = gamezoneEntries[cell]

    //Returns the Character cell
    fun getCharacterCell(character: MapCharacter): Cell = gamezoneEntries.filterValues { it == character }.keys.first()


    //Empties Value for the given cell
    fun setCellAsEmpty(cell: Cell) {
        gamezoneEntries[cell] = null
    }

    //Adds Character to the specified Cell on the Gamezone
    fun addCharacterToCell(cell: Cell, character: MapCharacter) {
        gamezoneEntries[cell] = character //updates Cell with given Character
    }

    fun getNumberOfHeroesOnTheMap(): Int = gamezoneEntries.filter { it.value is Hero }.count()

    fun getNumberOfCharactersOnTheMap(): Int = gamezoneEntries.filter { it.value != null }.count()

    fun isDifferentMapCharacterOnCell(character: MapCharacter, characterOccupyingOtherCell: MapCharacter): Boolean {
        return (character is Hero && characterOccupyingOtherCell is Monster) || (character is Monster && characterOccupyingOtherCell is Hero)
    }

    fun getGamezoneEntries() = gamezoneEntries.entries



}