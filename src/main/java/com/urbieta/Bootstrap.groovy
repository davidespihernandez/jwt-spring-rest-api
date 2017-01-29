package com.urbieta

import com.urbieta.domains.SecRole
import com.urbieta.domains.SecUser
import com.urbieta.services.FuelOrdersService
import com.urbieta.services.SecurityService
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment

class Bootstrap {

	public static void initialize(ApplicationContext ctx) throws Exception {
		Environment environment = ctx.getBean('environment')
//		println "rest url -> " + environment.getProperty('urbieta.rest.baseurl')
		SecurityService securityService = ctx.getBean("securityService")
		FuelOrdersService fuelOrdersService = ctx.getBean("fuelOrdersService")
		def roles = [
                [authority: SecurityService.ROLE_ADMIN, description: "Administrator"],
                [authority: SecurityService.ROLE_SALESMAN, description: "Salesman"],
                [authority: SecurityService.ROLE_COLLECTOR, description: "Collector"],
                [authority: SecurityService.ROLE_DISPATCHER, description: "Dispatcher"],
                [authority: SecurityService.ROLE_ACCESS, description: "Application access"]
					]
		roles.each{ Map roleInfo ->
			SecRole role = securityService.findSecRoleByAuthority(roleInfo.authority)
			if(role == null){
				role = securityService.createRole(roleInfo.authority, roleInfo.description)
			}
		}

		SecUser adminUser = securityService.findSecUserByUsername('admin')
		if(adminUser == null){
			adminUser = securityService.createSecUser(
					username: 'admin',
					password: 'admin2016',
					userEmail: 'info@atkloud.com',
					firstName: 'Administration',
					lastName: "User",
					address: 'Miami',
					phoneNumber: '')
		}

		securityService.grantRole(adminUser, securityService.findSecRoleByAuthority(SecurityService.ROLE_ADMIN))
		securityService.grantRole(adminUser, securityService.findSecRoleByAuthority(SecurityService.ROLE_ACCESS))
		fuelOrdersService.createMasterInfo()
		println "Finished bootstrap!"
	}
}
