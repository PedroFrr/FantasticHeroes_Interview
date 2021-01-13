package model.game_state

/**
 * Sealed class with the states the Game can go trough
 */
sealed class GameState{
    object GameWon: GameState()
    object GameLost: GameState()
    object GameOngoing: GameState()
}
