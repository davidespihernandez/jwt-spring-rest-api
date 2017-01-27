package com.urbieta.domains


import com.urbieta.JwtDemoApplication
import com.urbieta.repository.FuelProductRepository

import javax.persistence.*

@Entity
@Table(name = "fuel_product")
class FuelProduct implements Serializable{
	@Id
    @GeneratedValue
    Long id;

	@Column(name = "name", nullable = false, unique = true)
	String name

	@Column(name = "code", nullable = false, unique = true)
	String code

	@Column(name = "color", nullable = true)
	String color

	boolean equals(other) {
		if (!(other instanceof FuelProduct)) {
			return false
		}
		other.name == name
	}
	
	String toString(){
		return("FuelProduct: $name")
	}

	static FuelProduct get(Long id){
		FuelProductRepository fuelProductRepository = JwtDemoApplication.ctx.getBean('fuelProductRepository')
		fuelProductRepository.findOneById(id)
	}

	FuelProduct save(){
		FuelProductRepository fuelProductRepository = JwtDemoApplication.ctx.getBean('fuelProductRepository')
		fuelProductRepository.save(this)
	}

	def delete(){
		FuelProductRepository fuelProductRepository = JwtDemoApplication.ctx.getBean('fuelProductRepository')
		fuelProductRepository.delete(this.getId())
	}

	static List<FuelProduct> findAll(){
		FuelProductRepository fuelProductRepository = JwtDemoApplication.ctx.getBean('fuelProductRepository')
		fuelProductRepository.findAll()
	}

	static FuelProduct findByName(String name){
		FuelProductRepository fuelProductRepository = JwtDemoApplication.ctx.getBean('fuelProductRepository')
		fuelProductRepository.findOneByName(name)
	}

	static FuelProduct findByCode(String code){
		FuelProductRepository fuelProductRepository = JwtDemoApplication.ctx.getBean('fuelProductRepository')
		fuelProductRepository.findOneByCode(code)
	}

}
