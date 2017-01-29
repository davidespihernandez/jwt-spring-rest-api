package com.urbieta.controllers

import com.urbieta.domains.Customer
import com.urbieta.domains.FuelOrder
import com.urbieta.domains.SecUser
import com.urbieta.services.CustomerService
import com.urbieta.services.FuelOrdersService
import com.urbieta.services.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by david on 1/26/2017.
 */
@RestController
@RequestMapping(path = "/api/sales")
@CrossOrigin
class SalesController {
    @Autowired FuelOrdersService fuelOrdersService
    @Autowired SecurityService securityService
    @Autowired CustomerService customerService

    @RequestMapping(method = RequestMethod.GET)
    List<FuelOrder> searchOrders(@RequestBody Map parameters){
        if(parameters.salesmanId){
            parameters.put("salesman", securityService.findSecUserById(parameters.salesmanId))
        }
        if(parameters.customerId){
            parameters.put("customer", customerService.findCustomerById(parameters.customerId))
        }
        fuelOrdersService.searchOrders(parameters)
    }

    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PATCH])
    FuelOrder createOrUpdateOrder(@RequestBody Map parameters){
        if(parameters.salesmanId){
            parameters.put("salesman", securityService.findSecUserById(parameters.salesmanId))
        }
        if(parameters.customerId){
            parameters.put("customer", customerService.findCustomerById(parameters.customerId))
        }
        fuelOrdersService.createOrUpdateFuelOrder(parameters)
    }

    @RequestMapping(path = "{id}/cancel", method = RequestMethod.PATCH)
    FuelOrder cancelOrder(@PathVariable Long id){
        fuelOrdersService.cancelOrder(id)
    }

    @RequestMapping(path = "{id}/dispatch", method = RequestMethod.PATCH)
    FuelOrder dispatchOrder(@PathVariable Long id, @RequestBody Map parameters){
        if(parameters.dispatcherId){
            parameters.put("dispatcher", securityService.findSecUserById(parameters.dispatcherId))
        }
        parameters.put("fuelOrderId", id)
        fuelOrdersService.dispatchOrder(parameters)
    }

    @RequestMapping(path = "{id}/dispatching", method = RequestMethod.PATCH)
    FuelOrder changeOrderToDispatching(@PathVariable Long id){
        fuelOrdersService.changeToDispatching(id)
    }

    @RequestMapping(path = "{id}/finish", method = RequestMethod.PATCH)
    FuelOrder finishOrder(@PathVariable Long id){
        fuelOrdersService.finishOrder(id)
    }

}
