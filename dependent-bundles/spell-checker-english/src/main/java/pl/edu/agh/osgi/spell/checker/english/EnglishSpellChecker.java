package pl.edu.agh.osgi.spell.checker.english;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Dummy implementation of Polish Spell Checker
 *
 * @author kpietak
 *
 */
public class EnglishSpellChecker {

	private static final String[] ACCEPTED_WORDS = { "hello", "world", "let",
			"start", "the", "show" };

	private HashSet<String> dictionary;

	public EnglishSpellChecker() {
		initDictionary();
	}

	public boolean checkIfCorrect(String word) {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		return dictionary.contains(word.trim().toLowerCase());
	}

	protected void initDictionary() {
		dictionary = new HashSet<String>(Arrays.asList(ACCEPTED_WORDS));
	}

}
