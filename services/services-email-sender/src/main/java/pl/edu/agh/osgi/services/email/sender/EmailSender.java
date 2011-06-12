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
	 * @return {@link ISpellChecker} instance
	 */
	private ISpellChecker manuallyConsumeSpellCheckerService() {
		ISpellChecker spellChecker = null;
		while (spellChecker == null) {
			// acquire service reference
			ServiceReference ref = context.getServiceReference(ISpellChecker.class.getName());

			// check if the service reference is not null, i.e. service is
			// registered/exists in the OSGi Service Registry
			if (ref != null) {

				// get reference to the service object
				spellChecker = (ISpellChecker) context.getService(ref);

				// we need to check once more if the spellChecker is not null
				// because the service can be registered between call of
				// context.getServiceReference and context.getService
				if (spellChecker != null) {
					break;
				}
			}

			// wait a while to check again if the service is available
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
