package com.epam.rd.autocode.concurrenttictactoe;

public class SimplePlayer implements Player {
    private static final char WHITESPACE = ' ';
    private final TicTacToe ticTacToe;
    private final char mark;
    private final PlayerStrategy strategy;

    public SimplePlayer(final TicTacToe ticTacToe, final char mark, final PlayerStrategy strategy) {
        this.ticTacToe = ticTacToe;
        this.mark = mark;
        this.strategy = strategy;
    }

    @Override
    public void run() {
        while (isNotFull() && gameIsNotEnded()) {
            synchronized (ticTacToe) {
                if (canMove()) {
                    Move move = strategy.computeMove(mark, ticTacToe);
                    ticTacToe.setMark(move.row, move.column, mark);
                }
            }
        }
    }

    private boolean canMove() {
        return isNotFull() && gameIsNotEnded() && ticTacToe.lastMark() != mark;
    }

    private boolean isNotFull() {
        for (int i = 0; i < ticTacToe.table().length; i++) {
            for (int j = 0; j < ticTacToe.table()[i].length; j++) {
                if (ticTacToe.table()[i][j] == WHITESPACE) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean gameIsEnded() {
        if (mainDiagonalIsDone() || auxiliaryDiagonalIsDone()) {
            return true;
        }
        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (rowIsDone(i) || columnIsDone(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean mainDiagonalIsDone() {
        char cell = ticTacToe.table()[0][0];
        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (ticTacToe.table()[i][i] != cell) {
                return false;
            }
            cell = ticTacToe.table()[i][i];
        }
        return cell != WHITESPACE;
    }

    private boolean auxiliaryDiagonalIsDone() {
        int lastRow = ticTacToe.table().length - 1;
        char cell = ticTacToe.table()[lastRow][0];
        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (ticTacToe.table()[lastRow - i][i] != cell) {
                return false;
            }
            cell = ticTacToe.table()[lastRow - i][i];
        }
        return cell != WHITESPACE;
    }

    private boolean rowIsDone(int row) {
        char cell = ticTacToe.table()[row][0];
        for (int i = 0; i < ticTacToe.table()[row].length; i++) {
            if (ticTacToe.table()[row][i] != cell) {
                return false;
            }
            cell = ticTacToe.table()[row][i];
        }
        return cell != WHITESPACE;
    }

    private boolean columnIsDone(int column) {
        char cell = ticTacToe.table()[0][column];
        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (ticTacToe.table()[i][column] != cell) {
                return false;
            }
            cell = ticTacToe.table()[i][column];
        }
        return cell != WHITESPACE;
    }

    private boolean gameIsNotEnded() {
        return !gameIsEnded();
    }
}
