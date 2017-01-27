package com.urbieta.domains


import com.urbieta.JwtDemoApplication
import com.urbieta.repository.FuelOrderHistoryRepository

import javax.persistence.*

@Entity
@Table(name = "fuel_order_history")
class FuelOrderHistory implements Serializable{
	@Id
    @GeneratedValue
    Long id;

	@Column(name = "date", nullable = false)
	Date date

	@ManyToOne
	@JoinColumn(name = "sec_user_id", referencedColumnName = "id", nullable = false)
	SecUser secUser

	@ManyToOne
	@JoinColumn(name = "fuel_order_id", referencedColumnName = "id", nullable = false)
	FuelOrder fuelOrder

	@Column(name = "description", nullable = false)
	String description

	boolean equals(other) {
		if (!(other instanceof FuelOrderHistory)) {
			return false
		}
		other.date == date && other.description == description
	}
	
	String toString(){
		return("FuelOrderHistory: $id - $date")
	}

	static FuelOrderHistory get(Long id){
		FuelOrderHistoryRepository fuelOrderHistoryRepository = JwtDemoApplication.ctx.getBean('fuelOrderHistoryRepository')
		fuelOrderHistoryRepository.findOneById(id)
	}

	FuelOrderHistory save(){
		FuelOrderHistoryRepository fuelOrderHistoryRepository = JwtDemoApplication.ctx.getBean('fuelOrderHistoryRepository')
		fuelOrderHistoryRepository.save(this)
	}

	def delete(){
		FuelOrderHistoryRepository fuelOrderHistoryRepository = JwtDemoApplication.ctx.getBean('fuelOrderHistoryRepository')
		fuelOrderHistoryRepository.delete(this.getId())
	}

	static List<FuelOrderHistory> findAll(){
		FuelOrderHistoryRepository fuelOrderHistoryRepository = JwtDemoApplication.ctx.getBean('fuelOrderHistoryRepository')
		fuelOrderHistoryRepository.findAll()
	}

	static List<FuelOrderHistory> findAllByFuelOrder(FuelOrder fuelOrder){
		FuelOrderHistoryRepository fuelOrderHistoryRepository = JwtDemoApplication.ctx.getBean('fuelOrderHistoryRepository')
		fuelOrderHistoryRepository.findByFuelOrderOrderById(fuelOrder)
	}

}
