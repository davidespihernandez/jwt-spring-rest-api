package com.urbieta.domains


import com.urbieta.JwtDemoApplication
import com.urbieta.repository.FuelOrderRepository

import javax.persistence.*

@Entity
@Table(name = "fuel_order")
class FuelOrder implements Serializable{
	@Id
    @GeneratedValue
    Long id;

	@Column(name = "creation_date", nullable = false)
	Date creationDate

	@Column(name = "delivery_date", nullable = false)
	Date deliveryDate

	@ManyToOne
	@JoinColumn(name = "fuel_order_status_id", referencedColumnName = "id", nullable = false)
	FuelOrderStatus fuelOrderStatus

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
	Customer customer

	@ManyToOne
	@JoinColumn(name = "salesman_id", referencedColumnName = "id", nullable = false)
	SecUser salesman

	@ManyToOne
	@JoinColumn(name = "dispatcher_id", referencedColumnName = "id", nullable = true)
	SecUser dispatcher

	@Column(name = "dispatched_date", nullable = true)
	Date dispatchedDate

	@Column(name = "cancelled_date", nullable = true)
	Date cancelledDate

	@Column(name = "finished_date", nullable = true)
	Date finishedDate

	@Column(name = "city", nullable = true)
	String city

	@Column(name = "address", nullable = false)
	String address

	@Column(name = "zip_code", nullable = true)
	String zipCode

	@Column(name = "contact_name", nullable = false)
	String contactName

	@Column(name = "contact_phone", nullable = true)
	String contactPhone

	@Column(name = "contact_email", nullable = true)
	String contactEmail

	@Column(name = "comments", nullable = true)
	String comments

	@Column(name = "dispatcher_comments", nullable = true)
	String dispatcherComments

	@OneToMany(mappedBy = "fuelOrder", cascade = CascadeType.ALL)
	List<FuelOrderFuel> fuelOrderFuels = new ArrayList<FuelOrderFuel>();

	List<FuelOrderFuel> getFuelOrderFuels(){
		FuelOrderFuel.findAllByFuelOrder(this)
	}

	@OneToMany(mappedBy = "fuelOrder", cascade = CascadeType.ALL)
	List<FuelOrderOther> fuelOrderOthers = new ArrayList<FuelOrderOther>();

	List<FuelOrderOther> getFuelOrderOthers(){
		FuelOrderOther.findAllByFuelOrder(this)
	}

	boolean equals(other) {
		if (!(other instanceof FuelOrder)) {
			return false
		}
		other.id == id
	}
	
	String toString(){
		return("FuelOrder: $id - $creationDate")
	}

	static FuelOrder get(Long id){
		FuelOrderRepository fuelOrderRepository = JwtDemoApplication.ctx.getBean('fuelOrderRepository')
		fuelOrderRepository.findOneById(id)
	}

	FuelOrder save(){
		FuelOrderRepository fuelOrderRepository = JwtDemoApplication.ctx.getBean('fuelOrderRepository')
		fuelOrderRepository.save(this)
	}

	def delete(){
		FuelOrderRepository fuelOrderRepository = JwtDemoApplication.ctx.getBean('fuelOrderRepository')
		fuelOrderRepository.delete(this.getId())
	}

	static List<FuelOrder> findAll(){
		FuelOrderRepository fuelOrderRepository = JwtDemoApplication.ctx.getBean('fuelOrderRepository')
		fuelOrderRepository.findAll()
	}

	static List<FuelOrder> findAllBySalesmanAndFueldOrderStatus(SecUser salesman, FuelOrderStatus fuelOrderStatus){
		FuelOrderRepository fuelOrderRepository = JwtDemoApplication.ctx.getBean('fuelOrderRepository')
		fuelOrderRepository.findBySalesmanAndFuelOrderStatusOrderById(salesman, fuelOrderStatus)
	}

	static List<FuelOrder> findAllByDispatcherAndFueldOrderStatus(SecUser dispatcher, FuelOrderStatus fuelOrderStatus){
		FuelOrderRepository fuelOrderRepository = JwtDemoApplication.ctx.getBean('fuelOrderRepository')
		fuelOrderRepository.findByDispatcherAndFuelOrderStatusOrderById(dispatcher, fuelOrderStatus)
	}

	String descriptionLine1(){
		return("#" + id + " - Created: " + creationDate.format('MM-dd-yyyy hh:mm:ss a') + ": " + customer.getName())
	}

	String descriptionLine2(){
		return("Deliver: " + deliveryDate.format('MM-dd-yyyy hh:mm:ss a') + ": " + contactName + " - " + contactPhone + " - " + getFullAddress())
	}

	String descriptionLine3(){
		return("Products: " + getAllProductsString())
	}

	String getFullContactInfo(){
		return(contactName + " - " + contactPhone + " - " + contactEmail)
	}

	String getFuelOrderOthersString(){
		List<FuelOrderOther> others = getFuelOrderOthers()
		return(others.collect{ it.getOtherProduct().getName() + ": " + it.getQuantity()?.toString() }.join(', '))
	}

	String getFuelOrderFuelsString(){
		List<FuelOrderFuel> fuels = getFuelOrderFuels()
		return(fuels.collect{ it.getFuelProduct().getName() + ": " + it.getGallons()?.toString() }.join(', '))
	}

	String getAllProductsString(){
		return(getFuelOrderFuelsString() + " - " + getFuelOrderOthersString())
	}

	String getFullAddress(){
		String endAddress = address
		if(city && city.trim()!=""){
			endAddress += ", " + city + " FL"
		}
		if(zipCode && zipCode.trim()!=""){
			endAddress += " " + zipCode
		}
		return(endAddress)
	}

	def deleteAllFuelOrderFuels(){
		FuelOrderFuel.deleteAll(getFuelOrderFuels())
	}

	def deleteAllFuelOrderOthers(){
		FuelOrderOther.deleteAll(getFuelOrderOthers())
	}

	String dispatcherDescriptionLine1(){
		return("#" + id + " - Created: " + creationDate.format('MM-dd-yyyy hh:mm:ss a') + ": " + salesman.getFullName())
	}

	String dispatcherDescriptionLine2(){
		return("Deliver: " + customer.getName() + " - " + deliveryDate.format('MM-dd-yyyy hh:mm:ss a') + ": " + contactName + " - " + contactPhone + " - " + getFullAddress())
	}

	String dispatcherDescriptionLine3(){
		return("Products: " + getAllProductsString())
	}

	String searchDescriptionLine1(){
		return("#" + id + " (" + fuelOrderStatus.getName() + ")" + " - Created: " + creationDate.format('MM-dd-yyyy hh:mm:ss a') + ": " + salesman.getFullName())
	}

	String searchDescriptionLine2(){
		return("Deliver: " + customer.getName() + " - " + deliveryDate.format('MM-dd-yyyy hh:mm:ss a') + ": " + contactName + " - " + contactPhone + " - " + getFullAddress())
	}

	String searchDescriptionLine3(){
		return("Products: " + getAllProductsString())
	}

}
