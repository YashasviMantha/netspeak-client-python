package org.netspeak;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * @author marcel.gohsen@uni-weimar.de
 */
@JsonSerialize
public class SearchResults {
    private final List<Phrase> phrases;

    public SearchResults() {
        phrases = null;
    }

    public SearchResults(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    public List<Phrase> getPhrases() {
        return phrases;
    }

    @JsonSerialize
    public static class Phrase {
        private final long id;
        private final long frequency;
        private final List<String> words;

        public Phrase() {
            id = 0L;
            frequency = 0L;
            words = null;
        }

        public Phrase(final long id, final long frequency, final List<String> words) {
            this.id = id;
            this.frequency = frequency;
            this.words = words;
        }

        public long getId() {
            return id;
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
