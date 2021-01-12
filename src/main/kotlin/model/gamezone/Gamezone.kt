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
    fun moveCharacter(character: MapCharacter, direction: Directions) {

        val characterCellBeforeMoving = getCharacterCell(character)
        if (characterCanMove(characterCellBeforeMoving, direction)) {
            val characterCellAfterMoving = moveCharacterAndReturnCharacterPosition(character, direction)
            setCharacterAtCell(characterCellAfterMoving, character)  //updates Cell with value for the given Character
            emptyCell(characterCellBeforeMoving) //after the Character moves in any given Direction its old Cell must be emptied (set to null)
        } else {
            //if character can't move do an early return (nothing happens)
            return
        }

    }

    //Given the Cell (key) returns the Character or null if Cell is empty
    fun getCellValue(cell: Cell): MapCharacter? = cellValues[cell]

    //Adds Character to the specified Cell on the Gamezone
    fun setCharacterAtCell(cell: Cell, character: MapCharacter) {
        cellValues[cell] = character //updates Cell with given Character
    }

    //Returns the Character cell
    fun getCharacterCell(character: MapCharacter): Cell = cellValues.filterValues { it == character }.keys.first()

    //Returns true if Character can move on the map
    //Being able to move means that the character isn't at the edge of the map
    private fun characterCanMove(characterCell: Cell, direction: Directions): Boolean {

        val characterXCoordinate = characterCell.xCoordinate
        val characterYCoordinate = characterCell.yCoordinate
        return when (direction) {
            Directions.South -> characterYCoordinate != 0
            Directions.North -> characterYCoordinate != grid.size
            Directions.West -> characterXCoordinate != 0
            Directions.East -> characterXCoordinate != grid.size
        }
    }

    //moves Character on the Gamezone
    //South and North move the Character on the y axis
    //West and East move the Character on the x axis
    //if the actual coordinates of the Character don't allow it to move, the position remains the same (we could return an error message, but I'm keeping it simple)
    //the Cell where the Character ends up on the Gamezone is returned on this method
    private fun moveCharacterAndReturnCharacterPosition(character: MapCharacter, direction: Directions): Cell {
        val characterCell = getCharacterCell(character)
        val xCoordinate = characterCell.xCoordinate
        val yCoordinate = characterCell.yCoordinate

        return when (direction) {
            Directions.North -> characterCell.copy(yCoordinate = yCoordinate + 1)

            Directions.East -> characterCell.copy(xCoordinate = xCoordinate + 1)

            Directions.South -> characterCell.copy(yCoordinate = yCoordinate - 1)

            Directions.West -> characterCell.copy(xCoordinate = xCoordinate - 1)

        }
    }

    //Empties Value for the given cell
    private fun emptyCell(cell: Cell) {
        cellValues[cell] = null
    }



}