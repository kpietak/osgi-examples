package pl.edu.agh.osgi.email.sender;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private EmailSender emailSenderThread;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting Email Sender bundle");
		emailSenderThread = new EmailSender();
		emailSenderThread.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping Email Sender bundle");
		emailSenderThread.stopEmailSender();
		emailSenderThread.join();
	}

}
