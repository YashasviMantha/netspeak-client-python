package org.netspeak;

import java.util.List;

/**
 * @author marcel.gohsen@uni-weimar.de
 */
public class SearchResults {
    private List<Phrase> phrases;

    public List<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    public static class Phrase {
        private long id;
        private long frequency;
        private List<String> words;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getFrequency() {
            return frequency;
        }

        public List<String> getWords() {
            return words;
        }

        @Override
        public String toString() {
            return String.format("%d | %30s | %d", id, String.join(" ", words), frequency);
        }
    }
}
