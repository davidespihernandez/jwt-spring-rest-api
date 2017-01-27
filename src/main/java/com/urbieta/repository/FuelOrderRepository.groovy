package com.urbieta.repository

import com.urbieta.domains.Customer
import com.urbieta.domains.FuelOrder
import com.urbieta.domains.FuelOrderStatus
import com.urbieta.domains.SecUser
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@CompileStatic
@Transactional
interface FuelOrderRepository extends JpaRepository<FuelOrder, Long>{
	FuelOrder findOneById(Long id);
	List<FuelOrder> findByCustomer(Customer customer);
	List<FuelOrder> findBySalesmanAndFuelOrderStatusOrderById(SecUser salesman, FuelOrderStatus fuelOrderStatus);
	List<FuelOrder> findByDispatcherAndFuelOrderStatusOrderById(SecUser dispatcher, FuelOrderStatus fuelOrderStatus);
	List<FuelOrder> findByFuelOrderStatusInOrderById(List<FuelOrderStatus> fuelOrderStatuses);
}
