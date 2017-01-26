package com.urbieta.services

import com.urbieta.domains.Customer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomerService {

	Customer findCustomerByName(String name){
		Customer customer = Customer.findByName(name)
		return customer
	}

	Customer findCustomerById(Long id){
		Customer customer = Customer.get(id)
		return customer
	}

	List<Customer> findAllCustomers(String filter=null){
		List<Customer> customers = Customer.filterCustomers(filter)
		return(customers)
	}

	Customer createCustomer(parameters){
		Customer existingCustomer = findCustomerByName(parameters.name)
		if(existingCustomer){
			return(existingCustomer)
		}
		Customer newCustomer = new Customer(
				name: parameters.name,
				city: parameters.city,
				address: parameters.address,
				zipCode: parameters.zipCode,
				contactName: parameters.contactName,
				contactPhone: parameters.contactPhone,
				contactEmail: parameters.contactEmail
			).save()
		return(newCustomer)
	}

	Customer updateCustomer(parameters){
		Customer customer = findCustomerById(parameters.id)
		if(!customer){
			return(null)
		}
		customer.setCity(parameters.city)
		customer.setAddress(parameters.address)
		customer.setZipCode(parameters.zipCode)
		customer.setContactName(parameters.contactName)
		customer.setContactPhone(parameters.contactPhone)
		customer.setContactEmail(parameters.contactEmail)
		customer = customer.save()
		return(customer)
	}

}
