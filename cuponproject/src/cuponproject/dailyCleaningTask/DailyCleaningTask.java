package cuponproject.dailyCleaningTask;

import cuponproject.allthedbdao.CuponsdbDAO;

public class DailyCleaningTask implements Runnable {

	private boolean active = true;
	protected static CuponsdbDAO cuponsdbDAO= new CuponsdbDAO();

	public DailyCleaningTask() {
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void run() {
		while (active) {
			try {
				cuponsdbDAO.removeExpiredCoupon();
				Thread.sleep(60000);
			} catch (Exception e) {
				System.out.println("Error encountered while attempting to run daily task");
			}
		}

	}

}
