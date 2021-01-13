package model.map_character

/*
Interface with shared behaviour/properties between Monsters and Heroes
 */
interface MapCharacter {
    var health: Double

    fun attack(opponent: MapCharacter)


}