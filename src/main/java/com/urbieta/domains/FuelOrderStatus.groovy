package com.urbieta.domains

import com.urbieta.JwtDemoApplication
import com.urbieta.repository.FuelOrderStatusRepository

import javax.persistence.*

@Entity
@Table(name = "fuel_order_status")
class FuelOrderStatus implements Serializable{
	@Id
    @GeneratedValue
    Long id;

	@Column(name = "name", nullable = false, unique = true)
	String name

	@Column(name = "is_finished", nullable = true)
	Boolean isFinished

	@Column(name = "is_in_progress", nullable = true)
	Boolean isInProgress

	@Column(name = "is_in_route", nullable = true)
	Boolean isInRoute

	@Column(name = "is_cancelled", nullable = true)
	Boolean isCancelled

	boolean equals(other) {
		if (!(other instanceof FuelOrderStatus)) {
			return false
		}
		other.name == name
	}
	
	String toString(){
		return("Status: $name")
	}

	static FuelOrderStatus get(Long id){
		FuelOrderStatusRepository fuelOrderStatusRepository = JwtDemoApplication.ctx.getBean('fuelOrderStatusRepository')
		fuelOrderStatusRepository.findOneById(id)
	}

	FuelOrderStatus save(){
		FuelOrderStatusRepository fuelOrderStatusRepository = JwtDemoApplication.ctx.getBean('fuelOrderStatusRepository')
		fuelOrderStatusRepository.save(this)
	}

	def delete(){
		FuelOrderStatusRepository fuelOrderStatusRepository = JwtDemoApplication.ctx.getBean('fuelOrderStatusRepository')
		fuelOrderStatusRepository.delete(this.getId())
	}

	static List<FuelOrderStatus> findAll(){
		FuelOrderStatusRepository fuelOrderStatusRepository = JwtDemoApplication.ctx.getBean('fuelOrderStatusRepository')
		List<FuelOrderStatus> statuses = fuelOrderStatusRepository.findAll()
		statuses.sort{ FuelOrderStatus a, FuelOrderStatus b -> a.getName()<=>b.getName() }
	}

	static FuelOrderStatus findByName(String name){
		FuelOrderStatusRepository fuelOrderStatusRepository = JwtDemoApplication.ctx.getBean('fuelOrderStatusRepository')
		fuelOrderStatusRepository.findOneByName(name)
	}
}
