package ru.spbstu.terrai.players.samples

import org.junit.Test

class BrainDeadLeftTest : AbstractPlayerTest() {

    override fun createPlayer() = BrainDeadLeft()

    @Test
    fun testLab1() {
        doTestLab("labyrinths/lab1.txt", Controller.GameResult(8, exitReached = true))
    }

    @Test
    fun testLab2() {
        doTestLab("labyrinths/lab2.txt", Controller.GameResult(100, exitReached = false))
    }

    @Test
    fun testLab3() {
        doTestLab("labyrinths/lab3.txt", Controller.GameResult(9, exitReached = false))
    }

    @Test
    fun testLab4() {
        doTestLab("labyrinths/lab4.txt", Controller.GameResult(100, exitReached = false))
    }

    @Test
    fun testLab5() {
        doTestLab("labyrinths/lab5.txt", Controller.GameResult(100, exitReached = false))
    }

    @Test
    fun testLab6() {
        doTestLab("labyrinths/lab6.txt", Controller.GameResult(100, exitReached = false))
    }

    @Test
    fun testLab7() {
        doTestLab("labyrinths/lab7.txt", Controller.GameResult(100, exitReached = false))
    }
}