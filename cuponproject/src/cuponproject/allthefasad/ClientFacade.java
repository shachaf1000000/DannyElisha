package cuponproject.allthefasad;

import java.sql.SQLException;

import cuponproject.allthedao.CuponsDAO;
import cuponproject.allthedao.CustomerDAO;
import cuponproject.allthedbdao.CompaniesdbDAO;
import cuponproject.allthedbdao.CuponsdbDAO;
import cuponproject.allthedbdao.CustomerdbDAO;

public abstract class ClientFacade {
	protected static CompaniesdbDAO companiesdbDAO= new CompaniesdbDAO();
	protected static CuponsdbDAO cuponsdbDAO= new CuponsdbDAO();
	protected static CustomerdbDAO customerdbDAO= new CustomerdbDAO();
	
	

	
}
