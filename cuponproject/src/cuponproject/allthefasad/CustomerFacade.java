package cuponproject.allthefasad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import cuponproject.allthebeans.Category;
import cuponproject.allthebeans.Coupon;
import cuponproject.allthebeans.Customer;
import cuponproject.allthedbdao.CustomerdbDAO;

public class CustomerFacade extends ClientFacade implements LoginIn{

	int CustomerID;
	private int loggedCustomer = 0;


	public int getLoggedCustomer() {
		return loggedCustomer;
	}

	public void setLoggedCustomer(int customerID) {
		this.loggedCustomer = customerID;
	}
	public int getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}

	public CustomerFacade(int customerID) {
		super();
		CustomerID = customerID;
	}

	public CustomerFacade() {
		
	}

	
	public void purchaseCoupon(Coupon coupon) throws SQLException {
		Coupon targetCoupon = new Coupon();
		try {
		targetCoupon = cuponsdbDAO.getCoupon(coupon.getId());
		Collection<Integer> customerCoupons = new ArrayList<>();
		customerCoupons = customerdbDAO.getCustomerCoupons(getCustomerID());
		if (!customerCoupons.contains(targetCoupon))
			if (targetCoupon.getEndDate().after(new Date(System.currentTimeMillis())))
				if (targetCoupon.getAmount() > 0) {
					targetCoupon.setAmount(targetCoupon.getAmount() - 1);
					cuponsdbDAO.updateCoupon(targetCoupon);
					customerdbDAO.linkCustomerCoupon(getCustomerID(), targetCoupon.getId());
					System.out.println("Coupon " + targetCoupon.getTitle() + " " 
					+ targetCoupon.getId() + " Purchased!");
					System.out.println("Expires at: " + targetCoupon.getEndDate().toString());
				}
		}
		catch(SQLException e){
			throw new SQLException("Failed to purchase Coupon. Please contact our support team.");
		}
	}
	
	public Collection<Integer> getAllPurchasedCoupons() throws SQLException  {
		Collection<Integer> purchased = null;
		try {
			purchased = customerdbDAO.getCustomerCoupons(CustomerID);
			System.out.println("Purchased coupons retrieved!");
		} catch (SQLException e) {
			throw new SQLException("Failed to retrieve purchased Coupons. Please contact our"
					+ "support team.");
		}
		return purchased;
	}

	public Collection<Integer> getAllPurchasedCouponsByType(Category type) throws SQLException  {
		Collection<Integer> purchasedOfType = new ArrayList<>();
		try {
			purchasedOfType = customerdbDAO.getCustomerCoupons(CustomerID);
		} catch (SQLException e) {
			throw new SQLException("Failed to retrieve Coupons. Please contact our support team.");
		}
		Iterator<Integer> iter = purchasedOfType.iterator();
		Coupon coup = new Coupon();
		while (iter.hasNext()) {
			Category cat  = Coupon.getCategory();;
			if(!type.equals(cat)){
				iter.remove();
			}
		}
		System.out.println("Purchased coupons of type " + type + " retrieved!");
		return purchasedOfType;
	}

	public Collection<Integer> getAllPurchasedCouponsByPrice(double price) throws SQLException {
		Collection<Integer> purchasedOfPrice = new ArrayList<>();
		try {
			purchasedOfPrice = customerdbDAO.getCustomerCoupons(CustomerID);
		} catch (SQLException e) {
			throw new SQLException("Failed to retrieve Coupons. Please contact our support team.");
		}
		Iterator<Integer> iter = purchasedOfPrice.iterator();
		Coupon coup = new Coupon();
		double priceIter=coup.getPrice();
		while(iter.hasNext()){
			priceIter = iter.next();
			if (priceIter < price)
				iter.remove();
		}
		System.out.println("Purchased coupons that cost at least " + price + " retrieved!");
		return purchasedOfPrice;
	}

	@Override
	public String toString() {
		return "CustomerFacade [CustomerID=" + CustomerID + "]";
	}

	@Override
	public LoginIn login(String email, String password)  throws Exception {
		if (customerdbDAO.login(email, password)) {
			CustomerFacade custFacade = new CustomerFacade();
			custFacade.setLoggedCustomer(customerdbDAO.getLoginID());
			return custFacade;
		} else
			throw new Exception("Login FAILED. Please contact our support team.");
	}
	
}
