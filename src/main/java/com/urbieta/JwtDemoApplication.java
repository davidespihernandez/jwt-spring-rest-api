package com.urbieta;

import com.urbieta.domains.Customer;
import com.urbieta.repository.CustomerRepository;
import com.urbieta.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class JwtDemoApplication {
    @Autowired
    CustomerRepository customerRepository;
    static ApplicationContext ctx;
    public static void main(String[] args) throws Exception {
        ctx = SpringApplication.run(JwtDemoApplication.class, args);
        Bootstrap.initialize(ctx);
    }

//    @Override
//    public void run(String... strings) throws Exception {
//        Customer customer = new Customer();
//        customer.setName("Test customer 1");
//        customer.setCity("Customer 1 city");
//        customer.setAddress("Customer 1 address");
//        customer.setZipCode("122345");
//        customer.setContactName("Customer 1 contactName");
//        customer.setContactPhone("Customer 1 contactPhone");
//        customer.setContactEmail("Customer 1 contactEmail");
//        Customer existing = customerRepository.findOneByName("Test customer 1");
//        if(existing==null){
//            customerRepository.save(customer);
//        }
//        System.out.println("Done commandlinerunner");
//    }
}
