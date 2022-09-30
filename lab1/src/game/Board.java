package game;

public class Board {
    private int m_nrPins = 0;

    public void setUp(int nrPins) {
        m_nrPins = nrPins;
    }

    public void takePins(int n) {
        if (n != 1 && n != 2) {
            throw new IllegalArgumentException("You are only allowed to take " +
                    "one or 2 pins");
        }

        m_nrPins -= n;
        if (m_nrPins < 0) {
            m_nrPins = 0;
        }
    }

    public int getNrPins() {
        return m_nrPins;
    }
}
