package utils

import model.hero.Hero
import model.item.HeroItem
import model.monster.Monster

val longSword = HeroItem.Weapon("Long sword", power = 100, defense = 10)
val shortSword = HeroItem.Weapon("Short sword", power = 80, defense = 20)
val spear = HeroItem.Weapon("Spear", power = 150, defense = 5)
val bagFullOfSwords = HeroItem.Bag(listOf(longSword, shortSword, spear))

val allMighty = Hero.WarriorHero(name = "All mighty", heroItem = spear)
val oneForAll = Hero.BowHero(name = "One for all", heroItem = bagFullOfSwords)

val carnivoreMonster = Monster(health = 100.0)
val herbivoreMonster = Monster(health = 200.0)
val bossMonster = Monster(health = 5000.0)
