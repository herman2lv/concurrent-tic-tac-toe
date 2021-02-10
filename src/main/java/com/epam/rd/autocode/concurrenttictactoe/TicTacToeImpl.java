package com.epam.rd.autocode.concurrenttictactoe;

import java.util.concurrent.locks.ReentrantLock;

public class TicTacToeImpl implements TicTacToe {
    private final char[][] table = new char[3][3];
    private char lastMark = 'O';
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void setMark(final int x, final int y, final char mark) {
        lock.lock();
        checkMark(x, y);
        lastMark = mark;
        table[x][y] = mark;
        lock.unlock();
    }

    private void checkMark(final int x, final int y) {
        if (table[x][y] != 0) {
            throw new IllegalArgumentException("There is a mark in the cell already");
        }
    }

    @Override
    public char[][] table() {
        return copyTable();
    }

    private char[][] copyTable() {
        char[][] newTable = new char[3][3];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == 0) {
                    newTable[i][j] = ' ';
                } else {
                    newTable[i][j] = table[i][j];
                }
            }
        }
        return newTable;
    }

    @Override
    public char lastMark() {
        return lastMark;
    }
}
