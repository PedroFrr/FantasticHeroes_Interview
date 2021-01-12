package controller

import model.directions.Directions
import model.gamezone.Cell
import model.gamezone.Gamezone
import model.hero.Hero
import model.map_character.MapCharacter
import model.monster.Monster

/*
Class that performs the actions on the Gamezone
If I were to do this on Android I would had an intermediate layer to access the data and not directly from the data model (a repository or something)
 */
class GamezoneController(val gamezone: Gamezone) {

    private val gridSize = gamezone.grid.size

    //TODO actions for the Gamezone

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

                    //TODO refactor this logic doesn't seem right
                    when (character) {
                        is Hero -> setHeroAndMonsterCombat(
                            hero = character,
                            monster = characterOccupyingOtherCell as Monster
                        )
                        is Monster -> setHeroAndMonsterCombat(
                            hero = characterOccupyingOtherCell as Hero,
                            monster = character
                        )
                    }

                }
                //If after the fight (if it even happened) the character has health, it will replace the Cell
                if (character.health <= 0) {
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

    fun setHeroAndMonsterCombat(hero: Hero, monster: Monster) {

        //On a real app I would this processing asynchronously as to not block the User actions or set a loading state or something
        while (hero.health > 0 && monster.health > 0) {
            hero.attack(monster)

            //if the monster died he cannot attack
            if (monster.health <= 0) {
                break
            }
            monster.attack(hero)

        }

    }

    private fun moveCharacterFromOneCellToAnother(character: MapCharacter, cell: Cell) {
        val characterCellBeforeMoving = gamezone.getCharacterCell(character)
        gamezone.addCharacterToCell(cell, character)  //updates Cell with value for the given Character
        gamezone.setCellAsEmpty(characterCellBeforeMoving) //after the Character moves in any given Direction its old Cell must be emptied (set to null)
    }


}