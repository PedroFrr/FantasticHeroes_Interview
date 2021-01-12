package controller

import model.directions.Directions
import model.gamezone.Cell
import model.gamezone.Gamezone
import model.hero.Hero
import model.map_character.MapCharacter
import model.monster.Monster
import kotlin.random.Random

/**
 * The controller for the Gamezone actions.
 *
 */
class GamezoneController(private val gamezone: Gamezone) {

    private val gridSize = gamezone.grid.size

    //Places a Character on the Gamezone for the first time
    //For the first time the Character will be put on the Gamezone at random (on the unoccupied positions)
    fun putCharacterOnGamezoneFirstTime(character: MapCharacter) {
        val randomEmptyCell = gamezone.cellValues.filterValues { it == null }
            .map { Cell(it.key.xCoordinate, it.key.yCoordinate) }
            .random()

        gamezone.addCharacterToCell(randomEmptyCell, character)

    }

    //TODO Refactor this method. It has too many responsibilities and too many conditions
    //Moves Character (Heroes and Monsters) on the map after all the validations are good
    fun moveCharacter(character: MapCharacter, direction: Directions) {

        val characterCellBeforeMoving = gamezone.getCharacterCell(character)
        if (characterCanMove(characterCellBeforeMoving, direction)) {
            val cellCharacterWillMoveTo = getCellToWhichTheCharacterIsMoving(character, direction)
            val characterOccupyingOtherCell = gamezone.getCellValue(cellCharacterWillMoveTo)
            //if the cell the character is moving to is empty he will just move there. But if it isn't empty he has to fight if the occupying character is of different class (Hero or Monster)
            if (characterOccupyingOtherCell != null) {
                if (gamezone.isDifferentMapCharacterOnCell(character, characterCellBeforeMoving)) {

                    setFightBetweenOpponents(firstOpponent = character, secondOpponent = characterOccupyingOtherCell)

                }
                //If after the fight (if it even happened) the character has health, it will replace the Cell
                if (character.health > 0) {
                    moveCharacterFromOneCellToAnother(character, cellCharacterWillMoveTo)
                }

            }else{
                moveCharacterFromOneCellToAnother(character, cellCharacterWillMoveTo)
            }

        } else {
            //if character can't move do an early return (nothing happens)
            return
        }

    }

    //Returns true if Character can move on the map
    //Being able to move means that the character isn't at the edge of the map
    private fun characterCanMove(characterCell: Cell, direction: Directions): Boolean {

        val characterXCoordinate = characterCell.xCoordinate
        val characterYCoordinate = characterCell.yCoordinate
        return when (direction) {
            Directions.South -> characterYCoordinate != 0
            Directions.North -> characterYCoordinate != gridSize
            Directions.West -> characterXCoordinate != 0
            Directions.East -> characterXCoordinate != gridSize
        }
    }

    //moves Character on the Gamezone
    //South and North move the Character on the y axis
    //West and East move the Character on the x axis
    //if the actual coordinates of the Character don't allow it to move, the position remains the same (we could return an error message, but I'm keeping it simple)
    //the Cell where the Character ends up on the Gamezone is returned on this method
    private fun getCellToWhichTheCharacterIsMoving(character: MapCharacter, direction: Directions): Cell {
        val characterCell = gamezone.getCharacterCell(character)
        val xCoordinate = characterCell.xCoordinate
        val yCoordinate = characterCell.yCoordinate

        return when (direction) {
            Directions.North -> characterCell.copy(yCoordinate = yCoordinate + 1)

            Directions.East -> characterCell.copy(xCoordinate = xCoordinate + 1)

            Directions.South -> characterCell.copy(yCoordinate = yCoordinate - 1)

            Directions.West -> characterCell.copy(xCoordinate = xCoordinate - 1)

        }
    }

    fun getNumberOfHeroesOnTheMap() = gamezone.getNumberOfHeroesOnTheMap()

    fun getNumberOfCharactersOnTheMap() = gamezone.getNumberOfCharactersOnTheMap()

    //TODO maybe still refactor
    //On a real app I would this processing asynchronously as to not block the User actions or set a loading state or something
    fun setFightBetweenOpponents(firstOpponent: MapCharacter, secondOpponent: MapCharacter) {

        val randomTurn = (0..1).random()
        //defines who is the first opponent to attack based on the randomTurn number
        val firstTurnOpponent = if(randomTurn == 0){ firstOpponent }else {secondOpponent}
        val secondTurnOpponent = if(randomTurn == 0){ secondOpponent }else {firstOpponent}


        while (firstOpponent.health > 0 && secondOpponent.health > 0) {
            firstTurnOpponent.attack(secondTurnOpponent)

            //if the second opponent died he cannot attack
            if (secondTurnOpponent.health > 0) {
                secondTurnOpponent.attack(firstTurnOpponent)
                if (firstTurnOpponent.health <= 0){
                    val firstOpponentCell = gamezone.getCharacterCell(firstTurnOpponent)
                    gamezone.setCellAsEmpty(firstOpponentCell)
                }
            }else{
                val secondOpponentCell = gamezone.getCharacterCell(secondTurnOpponent)
                gamezone.setCellAsEmpty(secondOpponentCell)
            }

        }

    }

    private fun moveCharacterFromOneCellToAnother(character: MapCharacter, cell: Cell) {
        val characterCellBeforeMoving = gamezone.getCharacterCell(character)
        gamezone.addCharacterToCell(cell, character)  //updates Cell with value for the given Character
        gamezone.setCellAsEmpty(characterCellBeforeMoving) //after the Character moves in any given Direction its old Cell must be emptied (set to null)
    }


}