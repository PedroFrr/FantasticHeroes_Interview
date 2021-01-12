package model.hero

import model.item.HeroItem
import model.map_character.MapCharacter

sealed class Hero : MapCharacter {
    abstract var heroItem: HeroItem
    abstract val name: String

    //set heroPower based on its specific Hero Type attributes
    private val heroPower: Double
        get() = when(this) {
        is MagicHero -> (magicPoints * health).toDouble()
        is WarriorHero -> strength.toDouble()
        is ShieldHero -> defense * 0.5
        is BowHero -> (stealthPoints + name.length).toDouble()
    }

    override fun attack(opponent: MapCharacter) {
        opponent.health -= heroPower
        println("Monster was attacked he now has ${opponent.health} health points. Ouch! ")
    }

    data class MagicHero(val magicPoints: Int, override val name: String, override var health: Double = 60.0, override var heroItem: HeroItem): Hero() {
        fun castSpell(){
            println("$name cast a spell")
        }
    }

    data class WarriorHero(val strength: Int, override val name: String, override var health: Double = 60.0, override var heroItem: HeroItem): Hero()

    data class ShieldHero(val defense: Int, override val name: String, override var health: Double = 500.0, override var heroItem: HeroItem): Hero() {

        fun defend(opponent: MapCharacter){
            println("$name defended. Opponent attack was useless.")
        }
    }

    data class BowHero(val stealthPoints: Int, override val name: String, override var health: Double = 100.0, override var heroItem: HeroItem): Hero()

}

