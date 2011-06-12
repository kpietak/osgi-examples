package pl.edu.agh.osgi.first.bundle;

public class DummyThread extends Thread {

	private boolean active = true;

	@Override
	public void run() {

		while (active) {
			System.out.println("Dummy thread is working");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}

	}

	public void stopDummyThread() {
		this.active = false;
	}

}
