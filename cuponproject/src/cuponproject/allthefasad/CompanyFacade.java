package cuponproject.allthefasad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import cuponproject.allthebeans.Category;
import cuponproject.allthebeans.Company;
import cuponproject.allthebeans.Coupon;
import cuponproject.allthedbdao.CompaniesdbDAO;

public class CompanyFacade extends ClientFacade implements LoginIn{
	int CompanyID;
	
	private long loggedCompany = 0;

	public CompanyFacade(int companyID) {
		super();
		CompanyID = companyID;
	}


	public CompanyFacade() {
		
	}


	
	public void createCoupon(Coupon coupon) throws Exception {
		if (cuponsdbDAO.getAllCoupons().contains(coupon)) {
			throw new Exception("Failed to create Coupon."
					+ " Adding a Coupon with an existing name is not allowed.");
		} else {
			cuponsdbDAO.addCoupon(coupon);
			companiesdbDAO.linkCompanyCoupon(CompanyID, coupon.getId());
		}
	}

	public void removeCoupon(Coupon coupon) throws SQLException {
		try{
			cuponsdbDAO.removeCompanyCoupon(coupon.getId());
			cuponsdbDAO.removeCustomerCoupon(coupon.getId());
			cuponsdbDAO.deleteCoupon(coupon);
		}catch(SQLException e){
			throw new SQLException("Failed to remove Coupon. "
								+ "Please consult with your administartor");
		}
	}

	public void updateCoupon(Coupon coupon) throws SQLException  {
		try {
			cuponsdbDAO.updateCoupon(coupon);
		} catch (SQLException e) {
			throw new SQLException("Failed to update Coupon."
					+ "Please consult with your administrator");
		}
	}

	public Coupon getCoupon(int id) throws SQLException {
		try {
			return cuponsdbDAO.getCoupon(id);
		} catch (SQLException e) {
			throw new SQLException("Failed to retrieve Coupon"
					+ "Please consult with yur administrator.");
		}
	}

	public Collection<Coupon> getAllCoupons() throws SQLException  {
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			coupons = cuponsdbDAO.getCompanyCoupons(CompanyID);
		} catch (SQLException e) {
			throw new SQLException("Failed to retrieve all Coupons. Please consult your"
					+ "administrator.");
			}
		return coupons;
	}

	public Collection<Coupon> getCouponByType(Category couponType) throws SQLException  {
		Collection<Coupon> couponsOfType = new ArrayList<>();
		try {
			couponsOfType = cuponsdbDAO.getCompanyCoupons(CompanyID);
		} catch (SQLException e) {
			throw new SQLException("Failed to retrieve Coupons according to type."
					+ "Please consult your administrator.");
		}
		Iterator<Coupon> iter = couponsOfType.iterator();
		while (iter.hasNext()) {
			Coupon coup = iter.next();
			if (!(coup.getCategory().equals(couponType)))
				iter.remove();
		}
		System.out.println("Purchased coupons of type " + couponType + " retrieved!");
		return couponsOfType;
	}


	@Override
	public String toString() {
		return "CompanyFacade [CompanyID=" + CompanyID + "]";
	}


	@Override
	public LoginIn login(String email, String password) throws Exception {
		
			if (companiesdbDAO.login(email, password)) {
				CompanyFacade compFacade = new CompanyFacade();
				compFacade.setLoggedCompany(companiesdbDAO.getLoginID());
				return compFacade;
			} else
				throw new Exception("Login FAILED. Please consult with your administrator.");
			
		
	}



	public long getLoggedCompany() {
		return loggedCompany;
	}


	public void setLoggedCompany(long loggedCompany) {
		this.loggedCompany = loggedCompany;
	}
	
}
