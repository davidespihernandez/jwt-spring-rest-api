package com.urbieta.domains

import com.urbieta.JwtDemoApplication
import com.urbieta.repository.CustomerRepository

import javax.persistence.*

@Entity
@Table(name = "customer")
class Customer implements Serializable{

	@Id
    @GeneratedValue
    Long id

	@Column(name = "name", nullable = false, unique = true)
	String name

	@Column(name = "city", nullable = true)
	String city

	@Column(name = "address", nullable = true)
	String address

	@Column(name = "zip_code", nullable = true)
	String zipCode

	@Column(name = "contact_name", nullable = true)
	String contactName

	@Column(name = "contact_phone", nullable = true)
	String contactPhone

	@Column(name = "contact_email", nullable = true)
	String contactEmail

	boolean equals(other) {
		if (!(other instanceof Customer)) {
			return false
		}
		other.name == name
	}
	
	String toString(){
		return("Customer: $name")
	}

	static Customer get(Long id){
		CustomerRepository customerRepository = JwtDemoApplication.ctx.getBean('customerRepository')
		customerRepository.findOneById(id)
	}

	Customer save(){
		CustomerRepository customerRepository = JwtDemoApplication.ctx.getBean('customerRepository')
		customerRepository.save(this)
	}

	def delete(){
		CustomerRepository customerRepository = JwtDemoApplication.ctx.getBean('customerRepository')
		customerRepository.delete(this.getId())
	}

	static List<Customer> findAll(){
		CustomerRepository customerRepository = JwtDemoApplication.ctx.getBean('customerRepository')
		customerRepository.findAll()
	}

	static Customer findByName(String name){
		CustomerRepository customerRepository = JwtDemoApplication.ctx.getBean('customerRepository')
		customerRepository.findOneByName(name)
	}

	static List<Customer> filterCustomers(String filter=""){
		CustomerRepository customerRepository = JwtDemoApplication.ctx.getBean('customerRepository')
		customerRepository.filterCustomers(filter)
	}

	String getFullAddress(){
		String endAddress = address
		if(city && city.trim()!=""){
			endAddress += ", " + city + " FL"
		}
		if(zipCode && zipCode.trim()!=""){
			endAddress += " " + zipCode
		}
		return(endAddress)
	}

	String getFullContact(){
		String endContact = contactName
		if(contactPhone && contactPhone.trim()!=""){
			endContact += ", " + contactPhone
		}
		if(contactEmail && contactEmail.trim()!=""){
			endContact += ", " + contactEmail
		}
		return(endContact)
	}


}
