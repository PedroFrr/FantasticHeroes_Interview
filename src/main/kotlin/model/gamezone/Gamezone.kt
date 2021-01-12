package model.gamezone

import model.directions.Directions
import model.map_character.MapCharacter

class Gamezone(val grid: Grid) {

    //Represents a map with each cell and the corresponding Character (can be null if no Character exists at the position)
    private val cellValues = mutableMapOf<Cell, MapCharacter?>()

    init {
        //Initialize the grid with empty (null) Cells
        grid.cells.forEach { unit -> unit.forEach { cell -> cellValues[cell] = null } }
    }

    //Moves Character (Heroes and Monsters) on the map after all the validations are good
    //TODO validation
    fun moveCharacter(character: MapCharacter, direction: Directions) {

        val characterCellBeforeMoving = getCharacterCell(character)

        //TODO validation before moving - if Character is at the edges of the gamezone or if there's already a Character on the Cell he will end up in
        //TODO moveCharacter
        //TODO emptyCellWhereCharacter was


    }

    //Given the Cell (key) returns the Character or null if Cell is empty
    fun getCellValue(cell: Cell): MapCharacter? = cellValues[cell]

    //Adds Character to the specified Cell on the Gamezone
    fun setCharacterAtCell(cell: Cell, character: MapCharacter) {
        cellValues[cell] = character //updates Cell with given Character
    }

    //Returns the Character cell
    fun getCharacterCell(character: MapCharacter): Cell = cellValues.filterValues { it == character }.keys.first()



}