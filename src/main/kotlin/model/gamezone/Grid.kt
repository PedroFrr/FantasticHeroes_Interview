package model.gamezone

data class Grid(val size: Int) {
    var cells: Array<Array<Cell>> = arrayOf()

    init {
        (0 until size).forEach { i ->
            var row = arrayOf<Cell>()
            (0 until size).forEach { j ->
                row += Cell(i, j)
            }
            cells += row
        }
    }


}
