package textproc;

import java.util.HashMap;
import java.util.Map;

public class MultiWordCounter implements TextProcessor {
    private final Map<String, Integer> m_wordCount = new HashMap<>();

    MultiWordCounter(String[] words) {
        for (var w : words) {
            m_wordCount.put(w, 0);
        }
    }

    public void process(String w) {
        m_wordCount.put(w, m_wordCount.get(w) + 1);
    }

    public void report() {
        m_wordCount.forEach(
                (k, v) -> System.out.printf("%s: %d", k, v)
        );
    }
}