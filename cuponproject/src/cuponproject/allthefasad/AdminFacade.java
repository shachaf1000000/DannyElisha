package cuponproject.allthefasad;

import java.sql.SQLException;
import java.util.Collection;

import cuponproject.allthebeans.Company;
import cuponproject.allthebeans.Coupon;
import cuponproject.allthebeans.Customer;
import cuponproject.allthedbdao.CompaniesdbDAO;

public class AdminFacade extends ClientFacade implements LoginIn{

	public AdminFacade() {
		super();
	}

	
	@Override
	public LoginIn login(String email, String password) throws Exception {
		if (email.equalsIgnoreCase("admin") && password.equals("1234"))
			return new AdminFacade();
		else
			throw new Exception("Login FAILED for admin");
	}
	

	public void createCompany(Company company) throws Exception {
		try {
			if (companiesdbDAO.getAllCompanies() != null)
				if (companiesdbDAO.getAllCompanies().contains(company)) {
					throw new Exception("Failed to create Company. Adding a Company with an existing name is not allowed.");
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		companiesdbDAO.addCompany(company);
	}

	public void removeCompany(Company company) throws Exception {
		try {
			Collection<Coupon> coupons = cuponsdbDAO.getCompanyCoupons(company.getId());
			for (Coupon c : coupons) {
				cuponsdbDAO.removeCompanyCoupon(c.getId());
				cuponsdbDAO.removeCustomerCoupon(c.getId());
				cuponsdbDAO.deleteCoupon(c);
			}
			companiesdbDAO.deleteCompany(company);
		} catch (Exception e) {
			throw new Exception("Failed to remove Company. Please check the cause for this.");
		}
	}

	public void updateCompany(Company company) throws Exception {
		try {
			companiesdbDAO.updateCompany(company);
		} catch (Exception e) {
			throw new Exception("Error encountered while attempting to update company.Please check the cause for this.");
		}
	}

	public Company getCompany(int id) throws Exception {
		try {
			return companiesdbDAO.getOneCompany(id);
		} catch (Exception e) {
			throw new Exception("Error encountered while attempting to retrieve company.Please check the cause for this.");
		}
	}

	public Collection<Company> getAllCompanies() throws Exception {
		try {
			return companiesdbDAO.getAllCompanies();
		} catch (Exception e) {
			throw new Exception("Error encountered while attempting to retrieve companies.Please check the cause for this.");
		}
	}

	public void createCustomer(Customer customer) throws Exception {
		if (customerdbDAO.getAllCustomers() != null)
			if (customerdbDAO.getAllCustomers().contains(customer)) {
				throw new Exception("Failed to create Customer. Adding a Custoemr with"
									+ " an existing name is not allowed.");
			}
		customerdbDAO.addCustomer(customer);
	}

	public void removeCustomer(Customer customer) throws Exception {
		try {
			Collection<Integer> couponIds = customerdbDAO.getCustomerCoupons(customer.getId());
			for (Integer c : couponIds) {
				cuponsdbDAO.removeCustomerCoupon(c);
			}
			customerdbDAO.deleteCustomer(customer);
		} catch (Exception e) {
			throw new Exception("Error encountered while attempting to delete customer. "
								+ "Please check the cause for this.");
		}
	}

	public void updateCustomer(Customer customer) throws Exception {
		try {
			customerdbDAO.updateCustomer(customer);
		} catch (Exception e) {
			throw new Exception("Error encountered while attempting to update customer."
					+ "Please check the cause for this.");
		}
	}

	public Customer getCustomer(int id) throws Exception {
		try {
			return customerdbDAO.getOneCustomer(id);
		} catch (Exception e) {
			throw new Exception("Error encountered while attempting to retrieve customer."
					+ "Please check the cause for this.");
		}
	}

	public Collection<Customer> getAllCustomers() throws Exception {
		try {
			return customerdbDAO.getAllCustomers();
		} catch (Exception e) {
			throw new Exception("Error encountered while attempting to retrieve all customers."
					+ "Please check the cause for this.");
		}
	}

	

	@Override
	public String toString() {
		return "admin";
	}
}
