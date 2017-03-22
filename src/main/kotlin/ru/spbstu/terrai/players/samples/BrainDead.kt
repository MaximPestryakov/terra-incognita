package ru.spbstu.terrai.players.samples

import ru.spbstu.terrai.core.AbstractPlayer
import ru.spbstu.terrai.core.Direction
import ru.spbstu.terrai.core.MoveResult
import ru.spbstu.terrai.core.WalkMove

open class BrainDead : AbstractPlayer() {

    protected var lastDirection = Direction.NORTH

    protected var lastSuccess = true

    protected open fun getDirection() =
            if (lastSuccess) lastDirection
            else lastDirection.turnRight()

    override fun getNextMove() = WalkMove(getDirection().apply { lastDirection = this })

    override fun setMoveResult(result: MoveResult) {
        lastSuccess = result.successful
    }
}