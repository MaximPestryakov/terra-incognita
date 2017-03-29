package ru.spbstu.terrai.players.samples

import ru.spbstu.terrai.core.*
import ru.spbstu.terrai.lab.Controller
import ru.spbstu.terrai.lab.Labyrinth
import java.util.*

enum class Type { WALL, NONE, EXIT }

class SmartPlayer : AbstractPlayer() {

    var hasTreasure = false
    var hasExitLocation = false
    var wayToExit = mutableListOf<Direction>()
    val map = mutableMapOf<Location, Type>()
    val random = Random()
    var lastDirection = Direction.WEST
    var currentLocation = Location(0, 0)

    override fun getNextMove(): Move {
        if (hasTreasure && hasExitLocation) {
            if (wayToExit.isEmpty()) {
                val findExit = FindExit(map)
                wayToExit = findExit(currentLocation)!!
            }

            lastDirection = wayToExit.first()
            wayToExit.removeAt(0)
        } else {
            val possibleDirections = Direction.values().filter { !map.contains(it + currentLocation) }.toMutableList()

            if (possibleDirections.isEmpty()) {
                possibleDirections.addAll(Direction.values())
            }

            val directionIndex = random.nextInt(possibleDirections.size)
            lastDirection = possibleDirections[directionIndex]
        }

        return WalkMove(lastDirection)
    }

    override fun setMoveResult(result: MoveResult) {
        if (result.successful) {
            currentLocation = lastDirection + currentLocation
            map[currentLocation] = Type.NONE
        } else {
            map[lastDirection + currentLocation] = Type.WALL
        }

        when (result.room) {
            is WithContent -> {
                if (result.room.content != null) {
                    hasTreasure = true
                }
            }
            is Exit -> {
                map[currentLocation] = Type.EXIT
                hasExitLocation = true
            }
            is Wormhole -> {
                map.clear()
                hasExitLocation = false
            }
        }
    }
}

class FindExit(val map: Map<Location, Type>) {

    val visited = mutableSetOf<Location>()

    operator fun invoke(location: Location, path: MutableList<Direction> = mutableListOf()): MutableList<Direction>? {
        if (map.get(location) == Type.EXIT) {
            return path
        }
        visited.add(location)
        return Direction.values()
                .filter { !visited.contains(it + location) }
                .filter {
                    val type = map.get(it + location)
                    type == Type.NONE || type == Type.EXIT
                }
                .map { invoke(it + location, mutableListOf(*path.toTypedArray()).apply { add(it) }) }
                .filter { it != null }
                .minBy { it!!.size }
    }
}

fun main(args: Array<String>) {
    for (i in 1..7) {
        val lab = Labyrinth.createFromFile("labyrinths/lab$i.txt")
        val player = SmartPlayer()
        val controller = Controller(lab, player)
        val result = controller.makeMoves(1000)
        print("$i: ")
        if (result.exitReached) {
            println("You won!")
        } else {
            println("You lose!")
        }
    }
}
