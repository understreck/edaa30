package textproc;

public class MultiWordCounter implements TextProcessor {
    private final String[] m_words;
    private final int[] m_wordCounts;

    MultiWordCounter(String[] words) {
        m_words = words;
        m_wordCounts = new int[words.length];
    }

    public void process(String w) {
        for (int i = 0; i < m_words.length; ++i) {
            if (w.equals(m_words[i])) {
                ++m_wordCounts[i];
                return;
            }
        }
    }

    public void report() {
        for (int i = 0; i < m_words.length; ++i) {
            System.out.printf("%s: %d", m_words[i], m_wordCounts[i]);
        }
    }
}
