package com.example.gomoku.game

/**
 * 游戏棋盘类
 * 管理棋盘的状态和逻辑
 */
class GameBoard {
    companion object {
        const val BOARD_SIZE = 15
        const val EMPTY = 0
        const val BLACK = 1
        const val WHITE = 2
        const val WIN_COUNT = 5
    }

    private val board = Array(BOARD_SIZE) { IntArray(BOARD_SIZE) { EMPTY } }
    var currentPlayer = BLACK
    var gameOver = false
    var winner = EMPTY
    var moveHistory = mutableListOf<Pair<Int, Int>>()

    /**
     * 放置棋子
     */
    fun placeStone(row: Int, col: Int): Boolean {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false
        }
        if (board[row][col] != EMPTY || gameOver) {
            return false
        }

        board[row][col] = currentPlayer
        moveHistory.add(Pair(row, col))

        // 检查是否赢了
        if (checkWin(row, col)) {
            winner = currentPlayer
            gameOver = true
        }

        // 切换玩家
        currentPlayer = if (currentPlayer == BLACK) WHITE else BLACK
        return true
    }

    /**
     * 检查是否赢了
     */
    private fun checkWin(row: Int, col: Int): Boolean {
        val player = board[row][col]
        
        // 检查四个方向: 水平, 垂直, 对角线1, 对角线2
        val directions = listOf(
            Pair(0, 1),  // 水平
            Pair(1, 0),  // 垂直
            Pair(1, 1),  // 对角线
            Pair(1, -1)  // 反对角线
        )

        for ((dx, dy) in directions) {
            var count = 1

            // 向正方向计数
            var x = row + dx
            var y = col + dy
            while (x in 0 until BOARD_SIZE && y in 0 until BOARD_SIZE && board[x][y] == player) {
                count++
                x += dx
                y += dy
            }

            // 向负方向计数
            x = row - dx
            y = col - dy
            while (x in 0 until BOARD_SIZE && y in 0 until BOARD_SIZE && board[x][y] == player) {
                count++
                x -= dx
                y -= dy
            }

            if (count >= WIN_COUNT) {
                return true
            }
        }

        return false
    }

    /**
     * 获取指定位置的棋子
     */
    fun getStone(row: Int, col: Int): Int {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return EMPTY
        }
        return board[row][col]
    }

    /**
     * 重置游戏
     */
    fun reset() {
        for (i in 0 until BOARD_SIZE) {
            for (j in 0 until BOARD_SIZE) {
                board[i][j] = EMPTY
            }
        }
        currentPlayer = BLACK
        gameOver = false
        winner = EMPTY
        moveHistory.clear()
    }

    /**
     * 撤销上一步
     */
    fun undoMove(): Boolean {
        if (moveHistory.isEmpty()) {
            return false
        }

        val (row, col) = moveHistory.removeAt(moveHistory.size - 1)
        board[row][col] = EMPTY
        currentPlayer = if (currentPlayer == BLACK) WHITE else BLACK
        gameOver = false
        winner = EMPTY
        return true
    }
}
