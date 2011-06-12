package pl.edu.agh.osgi.services.spell.checker.english;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import pl.edu.agh.osgi.services.spell.checker.ISpellChecker;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private ServiceRegistration spellCheckerRegistration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Starting English Spell Checker bundle");
		Activator.context = bundleContext;
		// create service object
		EnglishSpellChecker spellChecker = new EnglishSpellChecker();
		// register the object as a service in the OSGi Service Registry
		spellCheckerRegistration = bundleContext.registerService(ISpellChecker.class.getName(), spellChecker, null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stopping English Spell Checker bundle");
		// this line is only to show how to manually unregister a service
		// services are automatically unregistered while stopping a bundle
		spellCheckerRegistration.unregister();
		Activator.context = null;
	}

}
