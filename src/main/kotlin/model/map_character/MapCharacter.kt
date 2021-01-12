package model.map_character

/*
Interface with shared behaviour between Monsters and Heroes
 */
interface MapCharacter {
    var health: Double

    //TODO add behaviours shared by all Map Character
    fun attack(opponent: MapCharacter)


}