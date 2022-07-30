package flashcards;

class FlashCard {
    private final String term;
    private final String definition;
    private int mistakes;

    FlashCard(String term, String definition) {
        this.term = term;
        this.definition = definition;
        this.mistakes = 0;
    }

    FlashCard(String term, String definition, int mistakes) {
        this(term, definition);
        this.mistakes = mistakes;
    }

    String getTerm() {
        return this.term;
    }

    String getDefinition() {
        return this.definition;
    }

    int getMistakes() {
        return this.mistakes;
    }

    void setMistakes(int mistakes) {
        this.mistakes += mistakes;
    }

    void eraseMistakes() {
        this.mistakes = 0;
    }
}