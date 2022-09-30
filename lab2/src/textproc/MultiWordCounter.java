package textproc;

import java.util.Map;
import java.util.TreeMap;

public class MultiWordCounter implements TextProcessor {
    private final Map<String, Integer> m_wordCount = new TreeMap<>();

    MultiWordCounter(String[] words) {
        for (var w : words) {
            m_wordCount.put(w, 0);
        }
    }

    public void process(String w) {
        if (m_wordCount.containsKey(w)) {
            m_wordCount.put(w, m_wordCount.get(w) + 1);
        }
    }

    public void report() {
        m_wordCount.forEach(
                (k, v) -> System.out.printf("%s: %d\n", k, v)
        );
    }
}
