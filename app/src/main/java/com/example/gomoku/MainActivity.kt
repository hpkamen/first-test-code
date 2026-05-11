package com.example.gomoku

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.example.gomoku.game.GameBoard
import com.example.gomoku.ui.GomokuBoardView

class MainActivity : AppCompatActivity() {

    private lateinit var boardView: GomokuBoardView
    private lateinit var statusTextView: TextView
    private lateinit var restartButton: Button
    private lateinit var undoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boardView = findViewById(R.id.board_view)
        statusTextView = findViewById(R.id.status_text)
        restartButton = findViewById(R.id.restart_button)
        undoButton = findViewById(R.id.undo_button)

        updateStatus()

        boardView.setOnBoardChangeListener(object : GomokuBoardView.OnBoardChangeListener {
            override fun onBoardChanged() {
                updateStatus()
            }

            override fun onGameOver(winner: Int) {
                showGameOverDialog(winner)
            }
        })

        restartButton.setOnClickListener {
            boardView.resetGame()
            updateStatus()
        }

        undoButton.setOnClickListener {
            boardView.undoMove()
        }
    }

    private fun updateStatus() {
        val board = boardView.getGameBoard()
        val currentPlayer = if (board.currentPlayer == GameBoard.BLACK) {
            getString(R.string.player_black)
        } else {
            getString(R.string.player_white)
        }
        statusTextView.text = "${getString(R.string.current_turn)} $currentPlayer"
    }

    private fun showGameOverDialog(winner: Int) {
        val winnerName = if (winner == GameBoard.BLACK) {
            getString(R.string.player_black)
        } else {
            getString(R.string.player_white)
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.game_over))
            .setMessage("$winnerName ${getString(R.string.player_won)}")
            .setPositiveButton(getString(R.string.restart_game)) { _, _ ->
                boardView.resetGame()
                updateStatus()
            }
            .setCancelable(false)
            .show()
    }
}
