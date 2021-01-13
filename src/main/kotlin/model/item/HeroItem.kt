package model.item

/*
Sealed class with possible Hero Items
 */
sealed class HeroItem{
    data class Weapon(val name: String, val power: Int, val defense: Int): HeroItem()
    data class Bag(val items: List<HeroItem> = listOf()): HeroItem() //I assumed the Bag can contain other bags
}
