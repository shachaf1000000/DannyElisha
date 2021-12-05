package com.example.c_p.maneger;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.c_p.service.ClientService;
import com.example.c_p.service.ServiceAdmin;
import com.example.c_p.service.ServiceCompany;
import com.example.c_p.service.ServiceCustomer;

@Service
public class LogInManeger {
	private final ApplicationContext ctx;
	private final ServiceAdmin adminService;
	
	public LogInManeger(ApplicationContext ctx, ServiceAdmin adminService) {
		this.ctx = ctx;
		this.adminService = adminService;
	}
	
	public ClientService login(String email, String password, ClientType clientType)  {
		ClientService clientService = null;
		switch (clientType) {
		case ADMINISTRATOR:
			clientService = (ClientService) adminService;
			break;
		case COMPANY:
			clientService = (ClientService) ctx.getBean(ServiceCompany.class);
			break;
		case CUSTOMER:
			clientService = (ClientService) ctx.getBean(ServiceCustomer.class);
			break;
		}
		return clientService;
	}
}
