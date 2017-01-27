package com.urbieta.domains


import com.urbieta.JwtDemoApplication
import com.urbieta.repository.OtherProductRepository

import javax.persistence.*

@Entity
@Table(name = "other_product")
class OtherProduct implements Serializable{
	@Id
    @GeneratedValue
    Long id;

	@Column(name = "name", nullable = false, unique = true)
	String name

	boolean equals(other) {
		if (!(other instanceof OtherProduct)) {
			return false
		}
		other.name == name
	}
	
	String toString(){
		return("OtherProduct: $name")
	}

	static OtherProduct get(Long id){
		OtherProductRepository otherProductRepository = JwtDemoApplication.ctx.getBean('otherProductRepository')
		otherProductRepository.findOneById(id)
	}

	OtherProduct save(){
		OtherProductRepository otherProductRepository = JwtDemoApplication.ctx.getBean('otherProductRepository')
		otherProductRepository.save(this)
	}

	def delete(){
		OtherProductRepository otherProductRepository = JwtDemoApplication.ctx.getBean('otherProductRepository')
		otherProductRepository.delete(this.getId())
	}

	static List<OtherProduct> findAll(){
		OtherProductRepository otherProductRepository = JwtDemoApplication.ctx.getBean('otherProductRepository')
		otherProductRepository.findAll()
	}

	static OtherProduct findByName(String name){
		OtherProductRepository otherProductRepository = JwtDemoApplication.ctx.getBean('otherProductRepository')
		otherProductRepository.findOneByName(name)
	}

}
