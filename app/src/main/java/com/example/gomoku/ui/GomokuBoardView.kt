package com.example.gomoku.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.gomoku.R
import com.example.gomoku.game.GameBoard
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * 五子棋棋盘视图
 */
class GomokuBoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val board = GameBoard()
    private var boardListener: OnBoardChangeListener? = null

    private val boardPaint = Paint().apply {
        color = context.getColor(R.color.board_background)
        style = Paint.Style.FILL
    }

    private val linePaint = Paint().apply {
        color = context.getColor(R.color.grid_line)
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    private val blackStonePaint = Paint().apply {
        color = context.getColor(R.color.stone_black)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val whiteStonePaint = Paint().apply {
        color = context.getColor(R.color.stone_white)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val lastMovePaint = Paint().apply {
        color = context.getColor(R.color.red_500)
        strokeWidth = 4f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private var cellSize = 0f
    private var boardMargin = 0f
    private val stoneRadius get() = cellSize * 0.4f

    interface OnBoardChangeListener {
        fun onBoardChanged()
        fun onGameOver(winner: Int)
    }

    fun setOnBoardChangeListener(listener: OnBoardChangeListener) {
        this.boardListener = listener
    }

    fun getGameBoard(): GameBoard = board

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val usableWidth = w - paddingLeft - paddingRight
        val usableHeight = h - paddingTop - paddingBottom
        val size = minOf(usableWidth, usableHeight)
        cellSize = size.toFloat() / GameBoard.BOARD_SIZE
        boardMargin = cellSize * 0.5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBoard(canvas)
        drawGrid(canvas)
        drawStones(canvas)
    }

    private fun drawBoard(canvas: Canvas) {
        val left = paddingLeft + boardMargin
        val top = paddingTop + boardMargin
        val right = left + GameBoard.BOARD_SIZE * cellSize
        val bottom = top + GameBoard.BOARD_SIZE * cellSize
        canvas.drawRect(left, top, right, bottom, boardPaint)
    }

    private fun drawGrid(canvas: Canvas) {
        val startX = paddingLeft + boardMargin
        val startY = paddingTop + boardMargin
        val endX = startX + GameBoard.BOARD_SIZE * cellSize
        val endY = startY + GameBoard.BOARD_SIZE * cellSize

        // 绘制竖线
        for (i in 0..GameBoard.BOARD_SIZE) {
            val x = startX + i * cellSize
            canvas.drawLine(x, startY, x, endY, linePaint)
        }

        // 绘制横线
        for (i in 0..GameBoard.BOARD_SIZE) {
            val y = startY + i * cellSize
            canvas.drawLine(startX, y, endX, y, linePaint)
        }
    }

    private fun drawStones(canvas: Canvas) {
        val startX = paddingLeft + boardMargin
        val startY = paddingTop + boardMargin

        for (row in 0 until GameBoard.BOARD_SIZE) {
            for (col in 0 until GameBoard.BOARD_SIZE) {
                val stone = board.getStone(row, col)
                if (stone != GameBoard.EMPTY) {
                    val x = startX + col * cellSize
                    val y = startY + row * cellSize
                    val paint = if (stone == GameBoard.BLACK) blackStonePaint else whiteStonePaint
                    canvas.drawCircle(x, y, stoneRadius, paint)
                }
            }
        }

        // 绘制最后一步
        if (board.moveHistory.isNotEmpty()) {
            val (lastRow, lastCol) = board.moveHistory.last()
            val x = startX + lastCol * cellSize
            val y = startY + lastRow * cellSize
            canvas.drawCircle(x, y, stoneRadius * 1.1f, lastMovePaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null || board.gameOver) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y
                val row = findClosestPosition(y - (paddingTop + boardMargin))
                val col = findClosestPosition(x - (paddingLeft + boardMargin))

                if (row >= 0 && col >= 0) {
                    if (board.placeStone(row, col)) {
                        invalidate()
                        boardListener?.onBoardChanged()

                        if (board.gameOver) {
                            boardListener?.onGameOver(board.winner)
                        }
                    }
                }
                return true
            }
        }
        return false
    }

    private fun findClosestPosition(coordinate: Float): Int {
        val position = (coordinate / cellSize).roundToInt()
        return if (position in 0 until GameBoard.BOARD_SIZE) position else -1
    }

    fun resetGame() {
        board.reset()
        invalidate()
    }

    fun undoMove() {
        if (board.undoMove()) {
            invalidate()
            boardListener?.onBoardChanged()
        }
    }
}
