package pl.edu.agh.osgi.services.spell.checker;

/**
 * An interface for spell checkers.
 *
 * @author kpietak
 *
 */
public interface ISpellChecker {

	/**
	 * Checks if the given word is correct, i.e. is a valid word according to a
	 * spell checker language
	 *
	 * @param word
	 *            a word to be checked
	 * @return <code>true</code> if the word is correct, <code>false</code>
	 *         otherwise
	 */
	boolean checkIfCorrect(String word);
}
