package cuponproject.loginmaneger;

import cuponproject.alltheconnectionpool.ConnectionPool;
import cuponproject.allthefasad.AdminFacade;
import cuponproject.allthefasad.ClientFacade;
import cuponproject.allthefasad.CompanyFacade;
import cuponproject.allthefasad.CustomerFacade;
import cuponproject.dailyCleaningTask.DailyCleaningTask;

public class LoginManeger {
	private DailyCleaningTask dailyClean;
	private static LoginManeger instance = null;

	private LoginManeger() {
		new Thread(dailyClean).start();
	}
	public DailyCleaningTask getDailyTask() {
		return dailyClean;
	}

	public void setDailyTask(DailyCleaningTask dailyTask) {
		this.dailyClean = dailyTask;
	}

	public static LoginManeger getInstance() {
		if (instance == null) {
			synchronized (LoginManeger.class) {
				if (instance == null) {
					instance = new LoginManeger();
				}
			}
		}
		return instance;
	}

	public ClientFacade login(String name, String password,
			ClientType clientType) throws Exception {
	
		AdminFacade adminfacade = new AdminFacade();
		CompanyFacade companyfacade = new CompanyFacade();
		CustomerFacade customerfacade = new CustomerFacade();

		switch (clientType) {
		case ADMINISTRATOR:
			adminfacade = (AdminFacade) adminfacade.login(name, password);
			return adminfacade;

		case COMPANY:
			companyfacade = (CompanyFacade) companyfacade.login(name, password);
			return companyfacade;

		case CUSTOMER:
			customerfacade = (CustomerFacade) customerfacade.login(name, password);
			return customerfacade;

		default:
			return null;

		}
	}
	
	public void shutdown() {
		dailyClean.setActive(false);
		try {
			ConnectionPool.getSingeltonInstance().closeAllConnections();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	

