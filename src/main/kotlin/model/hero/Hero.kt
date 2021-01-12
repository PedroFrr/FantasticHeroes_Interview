package model.hero

sealed class Hero()  {

    abstract val name: String

    data class MagicHero(val magicPoints: Int, override val name: String): Hero() {
        fun castSpell(){
            println("$name cast a spell")
        }

    }

    data class WarriorHero(val strength: Int, override val name: String): Hero() {

    }

    data class ShieldHero(val defense: Int, override val name: String): Hero() {

    }

    data class BowHero(val stealthPoints: Int, override val name: String): Hero() {

    }

}

