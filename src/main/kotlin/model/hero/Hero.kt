package model.hero

import model.item.HeroItem
import model.map_character.MapCharacter

sealed class Hero() : MapCharacter {
    abstract var heroItem: HeroItem
    abstract val name: String

    data class MagicHero(val magicPoints: Int, override val name: String, override var health: Int = 60, override var heroItem: HeroItem): Hero() {
        fun castSpell(){
            println("$name cast a spell")
        }

        override fun attack(opponent: MapCharacter) {
            println("$name attacked")
            //maybe we could it could loose magicPoints when it attacks
        }
    }

    data class WarriorHero(val strength: Int, override val name: String, override var health: Int = 60, override var heroItem: HeroItem): Hero() {
        override fun attack(opponent: MapCharacter) {
            println("$name attacks with power $strength")
        }
    }

    data class ShieldHero(val defense: Int, override val name: String, override var health: Int = 500, override var heroItem: HeroItem): Hero() {
        override fun attack(opponent: MapCharacter) {
            println("$name did one attack damage :C")
        }

        fun defend(opponent: MapCharacter){
            println("$name defended. Opponent attack was useless.")
        }
    }

    data class BowHero(val stealthPoints: Int, override val name: String, override var health: Int = 60, override var heroItem: HeroItem): Hero() {
        override fun attack(opponent: MapCharacter) {
            println("Long range attack")
        }
    }

}

