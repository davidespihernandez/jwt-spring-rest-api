package com.urbieta.controllers

import com.urbieta.domains.Customer
import com.urbieta.services.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by david on 1/26/2017.
 */
@RestController
@RequestMapping(path = "/api/customer")
@CrossOrigin
class CustomerController {
    @Autowired CustomerService customerService

    @RequestMapping(method = RequestMethod.GET)
    List<Customer> list(@RequestParam(value = "query", defaultValue = "") String query){
        customerService.findAllCustomers(query)
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    Customer getCustomer(@PathVariable Long id){
        customerService.findCustomerById(id)
    }

    @RequestMapping(method = RequestMethod.POST)
    Customer createCustomer(@RequestBody Customer customer){
        customerService.createCustomer(customer)
    }

    @RequestMapping(method = RequestMethod.PATCH)
    Customer updateCustomer(@RequestBody Customer customer){
        customerService.updateCustomer(customer)
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    Customer deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id)
    }

}
