package pl.edu.agh.osgi.services.spell.checker.english;

import java.util.Arrays;
import java.util.HashSet;

import pl.edu.agh.osgi.services.spell.checker.ISpellChecker;

/**
 * Dummy implementation of {@link ISpellChecker} for English language.
 *
 * @author kpietak
 *
 */
public class EnglishSpellChecker implements ISpellChecker {

	private static final String[] ACCEPTED_WORDS = { "hello", "world", "let",
			"start", "the", "show" };

	private HashSet<String> dictionary;

	public EnglishSpellChecker() {
		initDictionary();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * pl.edu.agh.osgi.services.spell.checker.ISpellChecker#checkIfCorrect(java
	 * .lang.String)
	 */
	@Override
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
