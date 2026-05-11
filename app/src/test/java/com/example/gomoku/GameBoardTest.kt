package com.example.gomoku

import android.content.Context
import com.example.gomoku.game.GameBoard
import com.example.gomoku.ui.GomokuBoardView
import org.junit.Test
import org.junit.Assert.*

class GameBoardTest {
    @Test
    fun testInitialState() {
        val board = GameBoard()
        assertEquals(GameBoard.BLACK, board.currentPlayer)
        assertFalse(board.gameOver)
        assertEquals(GameBoard.EMPTY, board.winner)
    }

    @Test
    fun testPlaceStone() {
        val board = GameBoard()
        assertTrue(board.placeStone(7, 7))
        assertEquals(GameBoard.BLACK, board.getStone(7, 7))
        assertEquals(GameBoard.WHITE, board.currentPlayer)
    }

    @Test
    fun testInvalidPlacement() {
        val board = GameBoard()
        board.placeStone(7, 7)
        assertFalse(board.placeStone(7, 7))
    }
}
