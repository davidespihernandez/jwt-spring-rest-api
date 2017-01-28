package com.urbieta.controllers

import com.urbieta.domains.Customer
import com.urbieta.domains.FuelProduct
import com.urbieta.domains.OtherProduct
import com.urbieta.services.CustomerService
import com.urbieta.services.FuelOrdersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by david on 1/26/2017.
 */
@RestController
@RequestMapping(path = "/api/product")
@CrossOrigin
class ProductController {
    @Autowired FuelOrdersService fuelOrdersService

    @RequestMapping(path = "fuel", method = RequestMethod.GET)
    List<FuelProduct> listFuelProduct(@RequestParam(value = "query", defaultValue = "") String query){
        fuelOrdersService.findAllFuelProduct(query)
    }

    @RequestMapping(path = "other", method = RequestMethod.GET)
    List<OtherProduct> listOtherProduct(@RequestParam(value = "query", defaultValue = "") String query){
        fuelOrdersService.findAllOtherProduct(query)
    }

    @RequestMapping(path = "fuel", method = RequestMethod.POST)
    FuelProduct createFuelProduct(@RequestBody FuelProduct fuelProduct){
        fuelOrdersService.createFuelProduct(fuelProduct)
    }

    @RequestMapping(path = "other", method = RequestMethod.POST)
    OtherProduct createOtherProduct(@RequestBody OtherProduct otherProduct){
        fuelOrdersService.createOtherProduct(otherProduct)
    }

    @RequestMapping(path = "other", method = RequestMethod.PATCH)
    OtherProduct updateOtherProduct(@RequestBody OtherProduct otherProduct){
        fuelOrdersService.updateOtherProduct(otherProduct)
    }

    @RequestMapping(path = "fuel", method = RequestMethod.PATCH)
    FuelProduct updateFuelProduct(@RequestBody FuelProduct fuelProduct){
        fuelOrdersService.updateFuelProduct(fuelProduct)
    }

    @RequestMapping(path = "other/{id}", method = RequestMethod.GET)
    OtherProduct getOtherProduct(@PathVariable Long id){
        fuelOrdersService.findOtherProductById(id)
    }

    @RequestMapping(path = "fuel/{id}", method = RequestMethod.GET)
    FuelProduct getFuelProduct(@PathVariable Long id){
        fuelOrdersService.findFuelProductById(id)
    }

    @RequestMapping(path = "other/{id}", method = RequestMethod.DELETE)
    OtherProduct deleteOtherProduct(@PathVariable Long id){
        fuelOrdersService.deleteOtherProduct()
    }

    @RequestMapping(path = "fuel/{id}", method = RequestMethod.DELETE)
    FuelProduct deleteFuelProduct(@PathVariable Long id){
        fuelOrdersService.deleteOtherProduct(id)
    }


}
