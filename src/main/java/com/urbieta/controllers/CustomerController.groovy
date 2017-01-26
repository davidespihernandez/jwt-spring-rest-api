package com.urbieta.controllers

import com.urbieta.domains.Customer
import com.urbieta.services.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by david on 1/26/2017.
 */
@RestController
@RequestMapping(path = "/api/customers")
@CrossOrigin
class CustomerController {
    @Autowired CustomerService customerService

    @RequestMapping(method = RequestMethod.GET)
    List<Customer> list(@RequestParam(value = "query", defaultValue = "") String query){
        customerService.findAllCustomers(query)
    }

}
