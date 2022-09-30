package game;

import java.util.Scanner;

public abstract class Player {
    protected static Scanner io = new Scanner(System.in);
    private final String m_userId;

    public Player(String id) {
        m_userId = id;
    }

    public String getUserId() {
        return m_userId;
    }

    public abstract int takePins(Board b);
}