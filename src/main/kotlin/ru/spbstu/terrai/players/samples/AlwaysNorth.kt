package ru.spbstu.terrai.players.samples

import ru.spbstu.terrai.core.AbstractPlayer
import ru.spbstu.terrai.core.Direction
import ru.spbstu.terrai.core.MoveResult
import ru.spbstu.terrai.core.WalkMove

class AlwaysNorth : AbstractPlayer() {
    override fun getNextMove() = WalkMove(Direction.NORTH)

    override fun setMoveResult(result: MoveResult) {}
}