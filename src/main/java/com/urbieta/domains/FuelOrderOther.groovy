package com.urbieta.domains

import com.urbieta.JwtDemoApplication
import com.urbieta.repository.FuelOrderOtherRepository

import javax.persistence.*

@Entity
@Table(name = "fuel_order_other")
class FuelOrderOther implements Serializable{
	@Id
    @GeneratedValue
    Long id;

	@ManyToOne
	@JoinColumn(name = "fuel_order_id", referencedColumnName = "id", nullable = false)
	FuelOrder fuelOrder

	@ManyToOne
	@JoinColumn(name = "other_product_id", referencedColumnName = "id", nullable = false)
	OtherProduct otherProduct

	@Column(name = "quantity", nullable = false)
	Double quantity

	boolean equals(other) {
		if (!(other instanceof FuelOrderOther)) {
			return false
		}
		other.fuelOrder == fuelOrder && other.otherProduct == otherProduct
	}
	
	String toString(){
		return("FuelOrderOther: $fuelOrder - $otherProduct")
	}

	static FuelOrderOther get(Long id){
		FuelOrderOtherRepository fuelOrderOtherRepository = JwtDemoApplication.ctx.getBean('fuelOrderOtherRepository')
		fuelOrderOtherRepository.findOneById(id)
	}

	FuelOrderOther save(){
		FuelOrderOtherRepository fuelOrderOtherRepository = JwtDemoApplication.ctx.getBean('fuelOrderOtherRepository')
		fuelOrderOtherRepository.save(this)
	}

	def delete(){
		FuelOrderOtherRepository fuelOrderOtherRepository = JwtDemoApplication.ctx.getBean('fuelOrderOtherRepository')
		fuelOrderOtherRepository.delete(this.getId())
	}

	static List<FuelOrderOther> findAll(){
		FuelOrderOtherRepository fuelOrderOtherRepository = JwtDemoApplication.ctx.getBean('fuelOrderOtherRepository')
		fuelOrderOtherRepository.findAll()
	}

	static List<FuelOrderOther> findAllByFuelOrder(FuelOrder fuelOrder){
		FuelOrderOtherRepository fuelOrderOtherRepository = JwtDemoApplication.ctx.getBean('fuelOrderOtherRepository')
		fuelOrderOtherRepository.findByFuelOrderOrderById(fuelOrder)
	}

	static def deleteAll(List<FuelOrderOther> list){
		FuelOrderOtherRepository fuelOrderOtherRepository = JwtDemoApplication.ctx.getBean('fuelOrderOtherRepository')
		fuelOrderOtherRepository.deleteInBatch(list)
	}

}
