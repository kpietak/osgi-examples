package pl.edu.agh.osgi.email.sender;

import pl.edu.agh.osgi.spell.checker.english.EnglishSpellChecker;

/**
 * Sample Email Sender which utilises {@link EnglishSpellChecker} to verify if
 * the given sample messages contain correct words.
 *
 * @author kpietak
 *
 */
public class EmailSender extends Thread {

	private static final String[] SAMPLE_MESSAGES = { "Hello OSGi world",
			"Let's start the show" };

	private boolean active = true;

	private EnglishSpellChecker spellChecker = new EnglishSpellChecker();

	@Override
	public void run() {
		for (String msg : SAMPLE_MESSAGES) {

			System.out.println(String
					.format("Checking spelling of \"%s\"", msg));
			String[] words = msg.split(" ");
			for (String word : words) {
				if (active) {
					String res = spellChecker.checkIfCorrect(word) ? "OK"
							: "WRONG";
					System.out.println(String
							.format("Word %s is %s", word, res));
					// i'm exhausted - waiting a moment
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						System.out.println(e);
						return;
					}
				} else {
					// thread should be stopped
					return;
				}
			}

		}
	}

	public void stopEmailSender() {
		this.active = false;
	}
}
