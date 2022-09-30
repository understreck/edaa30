package textproc;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class GeneralWordCounter implements TextProcessor {
    private final Map<String, Integer> m_wordCount = new TreeMap<>();
    private final Set<String> m_forbiddenWords;

    GeneralWordCounter(Set<String> forbiddenWords) {
        m_forbiddenWords = forbiddenWords;
    }

    public void process(String w) {
        if(m_forbiddenWords.contains(w)) return;

        if (m_wordCount.containsKey(w)) {
            m_wordCount.put(w, m_wordCount.get(w) + 1);
        } else {
            m_wordCount.put(w, 1);
        }
    }

    public void report() {
        var wordSet = m_wordCount.entrySet();
        var wordList =
                new ArrayList<>(wordSet);
        wordList.sort((lhs, rhs) -> {
            var valDiff = rhs.getValue() - lhs.getValue();
            if(valDiff != 0) return valDiff;

            return rhs.getKey().compareTo(lhs.getKey());
        });
        for(int i = 0; i < 5; ++i) {
            var element = wordList.get(i);
            System.out.printf("%s: %d\n", element.getKey(), element.getValue());
        }
    }
}
