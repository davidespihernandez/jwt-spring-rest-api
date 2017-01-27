package com.urbieta.domains

import com.urbieta.JwtDemoApplication
import com.urbieta.repository.FuelOrderFuelRepository

import javax.persistence.*

@Entity
@Table(name = "fuel_order_fuel")
class FuelOrderFuel implements Serializable{
	@Id
    @GeneratedValue
    Long id;

	@ManyToOne
	@JoinColumn(name = "fuel_order_id", referencedColumnName = "id", nullable = false)
	FuelOrder fuelOrder

	@ManyToOne
	@JoinColumn(name = "fuel_product_id", referencedColumnName = "id", nullable = false)
	FuelProduct fuelProduct

	@Column(name = "gallons", nullable = false)
	Double gallons

	boolean equals(other) {
		if (!(other instanceof FuelOrderFuel)) {
			return false
		}
		other.fuelOrder == fuelOrder && other.fuelProduct == fuelProduct
	}
	
	String toString(){
		return("FuelOrderFuel: $fuelOrder - $fuelProduct")
	}

	static FuelOrderFuel get(Long id){
		FuelOrderFuelRepository fuelOrderFuelRepository = JwtDemoApplication.ctx.getBean('fuelOrderFuelRepository')
		fuelOrderFuelRepository.findOneById(id)
	}

	FuelOrderFuel save(){
		FuelOrderFuelRepository fuelOrderFuelRepository = JwtDemoApplication.ctx.getBean('fuelOrderFuelRepository')
		fuelOrderFuelRepository.save(this)
	}

	def delete(){
		FuelOrderFuelRepository fuelOrderFuelRepository = JwtDemoApplication.ctx.getBean('fuelOrderFuelRepository')
		fuelOrderFuelRepository.delete(this.getId())
	}

	static List<FuelOrderFuel> findAll(){
		FuelOrderFuelRepository fuelOrderFuelRepository = JwtDemoApplication.ctx.getBean('fuelOrderFuelRepository')
		fuelOrderFuelRepository.findAll()
	}

	static List<FuelOrderFuel> findAllByFuelOrder(FuelOrder fuelOrder){
		FuelOrderFuelRepository fuelOrderFuelRepository = JwtDemoApplication.ctx.getBean('fuelOrderFuelRepository')
		fuelOrderFuelRepository.findByFuelOrderOrderById(fuelOrder)
	}

	static def deleteAll(List<FuelOrderFuel> list){
		FuelOrderFuelRepository fuelOrderFuelRepository = JwtDemoApplication.ctx.getBean('fuelOrderFuelRepository')
		fuelOrderFuelRepository.deleteInBatch(list)
	}

}
