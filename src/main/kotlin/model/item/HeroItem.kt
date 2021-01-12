package model.item

/*
Sealed class with possible Hero Items
 */
sealed class HeroItem{
    data class Weapon(val name: String): HeroItem()
    data class Bag(val items: List<Item> = listOf()): HeroItem()
}
