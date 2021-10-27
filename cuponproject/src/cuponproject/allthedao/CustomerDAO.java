package cuponproject.allthedao;
import java.sql.SQLException;
import java.util.ArrayList;

import cuponproject.allthebeans.Customer;


public interface CustomerDAO
{
	

	
		 boolean isCustomerExists(String email,String password)throws SQLException;
		 
		 void addCustomer(Customer customer) throws SQLException;
		 
		 void updateCustomer(Customer customer) throws SQLException;
		 
		 void deleteCustomer(Customer customer) throws SQLException;
		 
		  ArrayList<Customer> getAllCustomers()throws SQLException;
		 
		 Customer getOneCustomer(int CustomerID)throws SQLException;
	

}
