package model.hero

import model.item.HeroItem

sealed class Hero() {
    abstract val heroItem: HeroItem
    abstract val name: String

    data class MagicHero(val magicPoints: Int, override val name: String, override val heroItem: HeroItem): Hero() {

    }

    data class WarriorHero(val strength: Int, override val name: String, override val heroItem: HeroItem): Hero() {

    }

    data class ShieldHero(val defense: Int, override val name: String, override val heroItem: HeroItem): Hero() {

    }

    data class BowHero(val stealthPoints: Int, override val name: String, override val heroItem: HeroItem): Hero() {

    }

}

