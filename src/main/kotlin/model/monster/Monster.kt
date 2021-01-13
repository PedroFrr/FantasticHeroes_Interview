package model.monster

import model.hero.Hero
import model.map_character.MapCharacter

data class Monster(override var health: Double = 200.0): MapCharacter {
    override fun attack(opponent: MapCharacter) {
        println("Monster attacked and the opponent lost 2 of its health points ")
        opponent.health -= 2
    }
}
