package pl.edu.agh.osgi.services.email.sender;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private EmailSender emailSenderThread;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Starting Email Sender bundle");
		Activator.context = bundleContext;
		emailSenderThread = new EmailSender(context);
		emailSenderThread.start();

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stopping Email Sender bundle");
		Activator.context = null;
		emailSenderThread.stopEmailSender();
		emailSenderThread.join();
	}




}
