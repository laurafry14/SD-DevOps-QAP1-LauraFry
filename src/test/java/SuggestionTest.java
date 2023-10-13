import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SuggestionTest {
    private SuggestionEngine suggestionEngine;
    @Test
    public void testLoadDictionaryData(){
        SuggestionEngine suggestionEngine = new SuggestionEngine();
        try {
            suggestionEngine.loadDictionaryData(Paths.get("src/main/resources/words.txt"));
            Map<String, Integer> wordSuggestionDB = suggestionEngine.getWordSuggestionDB();
            Assertions.assertFalse(wordSuggestionDB.isEmpty());
        } catch (Exception e) {
            Assertions.fail("Error while loading dictionary data: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateSuggestions() throws Exception {
        SuggestionEngine suggestionEngine = new SuggestionEngine();
        suggestionEngine.loadDictionaryData(Paths.get( ClassLoader.getSystemResource("words.txt").toURI()));

        // Correct Spelling
        String suggestionsCorrectSpelling = suggestionEngine.generateSuggestions("hello");
        Assertions.assertEquals("", suggestionsCorrectSpelling, "If suggestions are empty, the word is spelled correctly.");

        // Incorrect Spelling
        String suggestionsMisspelledWord = suggestionEngine.generateSuggestions("heello");
        Assertions.assertTrue(suggestionsMisspelledWord.contains("hello"), "Suggestions should contain 'hello'.");
    }

    @Test
    public void testGetWordSuggestionDB() {
        SuggestionEngine suggestionEngine = new SuggestionEngine();
        SuggestionsDatabase database = new SuggestionsDatabase();

        Map<String, Integer> wordMap = new HashMap<>();
        wordMap.put("hello", 5);
        database.setWordMap(wordMap);

        suggestionEngine.setWordSuggestionDB(database);
        Map<String,Integer> retrievedWordMap = suggestionEngine.getWordSuggestionDB();
        Assertions.assertEquals(wordMap, retrievedWordMap);
    }
}
