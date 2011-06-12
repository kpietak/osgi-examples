package pl.edu.agh.osgi.services.email.sender;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pl.edu.agh.osgi.services.spell.checker.ISpellChecker;

/**
 * Sample Email Sender which utilises {@link EnglishSpellChecker} to verify if
 * the given sample messages contain correct words.
 *
 * @author kpietak
 *
 */
public class EmailSender extends Thread {

	private static final String[] SAMPLE_MESSAGES = { "Hello OSGi world", "Let's start the show" };

	private boolean active = true;

	private BundleContext context;

	public EmailSender(BundleContext context) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		this.context = context;
	}

	@Override
	public void run() {
		for (String msg : SAMPLE_MESSAGES) {

			System.out.println(String.format("Checking spelling of \"%s\"", msg));
			String[] words = msg.split(" ");

			for (String word : words) {
				if (active) {

					// acquire spell checker
					ISpellChecker spellChecker = manuallyConsumeSpellCheckerService();

					// check spelling of particular words
					String res = spellChecker.checkIfCorrect(word) ? "OK" : "WRONG";
					System.out.println(String.format("Word \"%s\" is %s", word, res));

					// i'm exhausted - waiting a moment
					threadSleep(3000);
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

	/**
	 * Manually acquire {@link ISpellChecker} service instance using
	 * {@link ServiceReference}
	 *
	 * @return {@link ISpellChecker} instance or <code>null</code> if no spell
	 *         checker is registered
	 */
	private ISpellChecker manuallyConsumeSpellCheckerService() {
		ISpellChecker spellChecker = null;
		while (spellChecker == null) {
			ServiceReference ref = context.getServiceReference(ISpellChecker.class.getName());
			if (ref != null) {
				spellChecker = (ISpellChecker) context.getService(ref);
				if (spellChecker != null) {
					break;
				}
			}
			System.out.println("Cannot find any spell checker");
			threadSleep(1000);
		}
		return spellChecker;
	}

	private void threadSleep(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			System.out.println(e);
			return;
		}
	}
}
