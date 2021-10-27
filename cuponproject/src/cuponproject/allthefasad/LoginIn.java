package cuponproject.allthefasad;

import java.sql.SQLException;

public interface LoginIn {
	LoginIn login(String email, String password) throws SQLException, Exception;
}
