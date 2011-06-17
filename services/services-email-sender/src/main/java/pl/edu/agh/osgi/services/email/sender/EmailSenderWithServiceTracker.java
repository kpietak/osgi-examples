package pl.edu.agh.osgi.services.email.sender;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import pl.edu.agh.osgi.services.spell.checker.ISpellChecker;

/**
 * <p>Sample Email Sender which utilises {@link EnglishSpellChecker} to verify if
 * the given sample messages contain correct words.
 *
 * <p>Note that this class requires <code>irg.osgi.util.tracker</code> package.
 *
 * @author kpietak
 *
 */
public class EmailSenderWithServiceTracker extends EmailSender {

	private ISpellChecker spellChecker;

	private ServiceTracker serviceTracker;

	public EmailSenderWithServiceTracker(BundleContext context) {
		super(context);
	}

	@Override
	public void run() {

		// initialize and open service tracker
		serviceTracker = new ServiceTracker(context, ISpellChecker.class.getName(), new SPServiceCustomizer());
		serviceTracker.open();

		translateMessages(SAMPLE_MESSAGES);

		// finalize service tracker
		serviceTracker.close();

	}


	private class SPServiceCustomizer implements ServiceTrackerCustomizer {

		@Override
		public Object addingService(ServiceReference reference) {
			spellChecker = (ISpellChecker)context.getService(reference);
			return spellChecker;
		}

		@Override
		public void modifiedService(ServiceReference reference, Object service) {
			// Do nothing

		}

		@Override
		public void removedService(ServiceReference reference, Object service) {
			spellChecker = null;

		}
	}

	/**
	 * Manually acquire {@link ISpellChecker} service instance using
	 * {@link ServiceReference}
	 *
	 * @return {@link ISpellChecker} instance
	 */
	protected ISpellChecker consumeSpellCheckerService() {
		while (spellChecker == null) {
			// wait a while to check again if the service is available
			System.out.println("Cannot find any spell checker");
			threadSleep(1000);
		}
		return spellChecker;
	}

}
