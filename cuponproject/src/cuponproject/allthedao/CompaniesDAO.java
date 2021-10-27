package cuponproject.allthedao;

import java.sql.SQLException;
import java.util.Collection;

import cuponproject.allthebeans.Company;



public interface CompaniesDAO {
	 boolean isCompanyExists(String email,String password)throws SQLException;
		
	 void addCompany(Company company)throws SQLException;
	 
	 void updateCompany(Company company)throws SQLException;
	
	 void deleteCompany(Company company)throws SQLException; 
	
	 public Collection<Company> getAllCompanies()throws SQLException;
	
	 Company getOneCompany(int companyID)throws SQLException;

}

