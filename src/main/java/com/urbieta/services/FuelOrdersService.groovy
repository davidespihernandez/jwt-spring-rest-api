package com.urbieta.services

import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.tool.xml.XMLWorkerHelper
import com.urbieta.domains.*
import com.urbieta.repository.FuelOrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnit
import javax.persistence.Query

@Service
@Transactional
class FuelOrdersService {

	private EntityManagerFactory emf;

	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	static final String STATUS_IN_PROGRESS = "In progress"
	static final String STATUS_FINISHED = "Finished"
	static final String STATUS_IN_ROUTE = "In route"
	static final String STATUS_CANCELLED = "Cancelled"
	static final String STATUS_DISPATCHING = "Dispatching"

	@Autowired FuelOrderRepository fuelOrderRepository
	@Autowired SecurityService securityService

	FuelOrder findFuelOrderById(Long id){
		FuelOrder fuelOrder = FuelOrder.get(id)
		return fuelOrder
	}

	def createDefaultFuelProducts(){
		def products = [
			[name: 'Unleaded', code: '1', color: '#FFFFFF'],  //white
			[name: 'Diesel low', code: '2', color: '#00FFFF'], //yellow
			[name: 'Rec 90', code: '3', color: '#0000FF'], //blue
			[name: 'Red dyed', code: '4', color: '#FF0000'], //red
			[name: 'Hyd68', code: '5', color: '#A52A2A'], //brown
			[name: '15w40C54', code: '6', color: '#800080'] //purple
		]
		products.each{ Map productInfo ->
			FuelProduct product = FuelProduct.findByName(productInfo.name)
			if(!product){
				product = new FuelProduct(productInfo).save()
			}
		}
	}

	def createDefaultOtherProducts(){
		def products = [
			[name: 'Lubes-bulk plant']
		]
		products.each{ Map productInfo ->
			OtherProduct product = OtherProduct.findByName(productInfo.name)
			if(!product){
				product = new OtherProduct(productInfo).save()
			}
		}
	}

	def createOrderStatuses(){
		def statuses = [
				[name: STATUS_IN_PROGRESS, isFinished: false, isInProgress: true, isInRoute: false, isCancelled: false],
				[name: STATUS_FINISHED, isFinished: true, isInProgress: false, isInRoute: false, isCancelled: false],
				[name: STATUS_IN_ROUTE, isFinished: false, isInProgress: false, isInRoute: true, isCancelled: false],
				[name: STATUS_CANCELLED, isFinished: false, isInProgress: false, isInRoute: false, isCancelled: true],
				[name: STATUS_DISPATCHING, isFinished: false, isInProgress: false, isInRoute: false, isCancelled: false]
		]
		statuses.each{ Map statusInfo ->
			FuelOrderStatus status = FuelOrderStatus.findByName(statusInfo.name)
			if(!status){
				status = new FuelOrderStatus(statusInfo).save()
			}
		}
	}

	def createMasterInfo(){
		createDefaultFuelProducts()
		createDefaultOtherProducts()
		createOrderStatuses()
	}

	List<OtherProduct> findAllOtherProduct(String filter=null){
		List<OtherProduct> allOtherProduct = OtherProduct.findAll().sort { OtherProduct a, OtherProduct b -> a.getName()<=>b.getName() }
		if(filter && filter.trim()!=""){
			allOtherProduct = allOtherProduct.findAll{ it.getName().toUpperCase().contains(filter.toUpperCase()) }
		}
		return(allOtherProduct)
	}

	OtherProduct findOtherProductById(Long id){
		OtherProduct otherProduct = OtherProduct.get(id)
		return otherProduct
	}

	OtherProduct findOtherProductByName(String name){
		OtherProduct otherProduct = OtherProduct.findByName(name)
		return otherProduct
	}

	OtherProduct updateOtherProduct(parameters){
		OtherProduct otherProduct = findOtherProductById(parameters.otherProductId)
		if(otherProduct){
			otherProduct.setName(parameters.name)
			otherProduct.save()
		}
		return(otherProduct)
	}

	OtherProduct createOtherProduct(parameters){
		OtherProduct otherProduct = findOtherProductByName(parameters.name)
		if(otherProduct){
			return(otherProduct)
		}
		otherProduct = new OtherProduct(name: parameters.name)
		otherProduct.save()
		return(otherProduct)
	}

	List<FuelProduct> findAllFuelProduct(String filter=null){
		List<FuelProduct> allFuelProduct = FuelProduct.findAll().sort { FuelProduct a, FuelProduct b -> a.getName()<=>b.getName() }
		if(filter && filter.trim()!=""){
			allFuelProduct = allFuelProduct.findAll{ it.getName().toUpperCase().contains(filter.toUpperCase()) }
		}
		return(allFuelProduct)
	}

	FuelProduct findFuelProductById(Long id){
		FuelProduct fuelProduct = FuelProduct.get(id)
		return fuelProduct
	}

	FuelProduct findFuelProductByName(String name){
		FuelProduct fuelProduct = FuelProduct.findByName(name)
		return fuelProduct
	}

	FuelProduct updateFuelProduct(parameters){
		FuelProduct fuelProduct = findFuelProductById(parameters.fuelProductId)
		if(fuelProduct){
			fuelProduct.setName(parameters.name)
			fuelProduct.setCode(parameters.code)
			fuelProduct.setColor(parameters.color)
			fuelProduct.save()
		}
		return(fuelProduct)
	}

	FuelProduct createFuelProduct(parameters){
		FuelProduct fuelProduct = findFuelProductByName(parameters.name)
		if(fuelProduct){
			return(fuelProduct)
		}
		fuelProduct = new FuelProduct(name: parameters.name, code: parameters.code, color: parameters.color)
		fuelProduct.save()
		return(fuelProduct)
	}

	def deleteOtherProduct(Long otherProductId){
		OtherProduct otherProduct = findOtherProductById(otherProductId)
		otherProduct.delete()
	}

	def deleteFuelProduct(Long fuelProductId){
		FuelProduct fuelProduct = findFuelProductById(fuelProductId)
		fuelProduct.delete()
	}

	FuelOrderStatus findFuelOrderStatusByName(String name){
		return(FuelOrderStatus.findByName(name))
	}

	FuelOrderStatus findFuelOrderStatusById(Long id){
		return(FuelOrderStatus.get(id))
	}

	FuelOrderStatus getStatusInProgress(){
		findFuelOrderStatusByName(STATUS_IN_PROGRESS)
	}

	FuelOrderStatus getStatusFinished(){
		findFuelOrderStatusByName(STATUS_FINISHED)
	}

	FuelOrderStatus getStatusInRoute(){
		findFuelOrderStatusByName(STATUS_IN_ROUTE)
	}

	FuelOrderStatus getStatusCancelled(){
		findFuelOrderStatusByName(STATUS_CANCELLED)
	}

	FuelOrderStatus getStatusDispatching(){
		findFuelOrderStatusByName(STATUS_DISPATCHING)
	}

	List<FuelOrder> findAllFuelOrdersInProgress(SecUser secUser){
		return(FuelOrder.findAllBySalesmanAndFueldOrderStatus(secUser, getStatusInProgress()))
	}

	FuelOrder createOrUpdateFuelOrder(parameters){
		Long fuelOrderId = parameters.fuelOrderId
		Customer customer = parameters.customer
		SecUser salesman = parameters.salesman
		String city = parameters.city
		String address = parameters.address
		String zipCode = parameters.zipCode
		String contactName = parameters.contactName
		String contactPhone = parameters.contactPhone
		String contactEmail = parameters.contactEmail
		String comments = parameters.comments
		Map fuelProducts = parameters.fuelProducts
		Map otherProducts = parameters.otherProducts
		Date deliveryDate = parameters.deliveryDate
		FuelOrder fuelOrder = findFuelOrderById(fuelOrderId)
		String historyComment
		if(fuelOrderId && !fuelOrder){
			return(null)
		}
		FuelOrderStatus fuelOrderStatus = getStatusInProgress()
		Date creationDate = new Date()
		if(!fuelOrder){
			historyComment = "Created"
			fuelOrder = new FuelOrder()
		}
		else{
			historyComment = "Updated"
			if(STATUS_IN_PROGRESS!=fuelOrder.getFuelOrderStatus().getName()){
				throw new Exception("This order can't be updated since it's being dispatched")
			}
			fuelOrder.deleteAllFuelOrderFuels()
			fuelOrder.deleteAllFuelOrderOthers()
			fuelOrderStatus = fuelOrder.getFuelOrderStatus()
			creationDate = fuelOrder.getCreationDate()
		}

		fuelOrder.setCustomer(customer)
		fuelOrder.setSalesman(salesman)
		fuelOrder.setCity(city)
		fuelOrder.setAddress(address)
		fuelOrder.setZipCode(zipCode)
		fuelOrder.setContactName(contactName)
		fuelOrder.setContactPhone(contactPhone)
		fuelOrder.setContactEmail(contactEmail)
		fuelOrder.setComments(comments)
		fuelOrder.setDeliveryDate(deliveryDate)
		fuelOrder.setFuelOrderStatus(fuelOrderStatus)
		fuelOrder.setCreationDate(creationDate)
		fuelOrder = fuelOrder.save()
		fuelProducts.each{ Long fuelProductId, Double gallons ->
			FuelOrderFuel fuelOrderFuel = new FuelOrderFuel(fuelOrder: fuelOrder, fuelProduct: FuelProduct.get(fuelProductId), gallons: gallons).save()
		}
		otherProducts.each{ Long otherProductId, Double quantity ->
			FuelOrderOther fuelOrderOther = new FuelOrderOther(fuelOrder: fuelOrder, otherProduct: OtherProduct.get(otherProductId), quantity: quantity).save()
		}
		addToHistory(fuelOrder, historyComment)
		return(fuelOrder)
	}

	FuelOrder cancelOrder(Long fuelOrderId){
		FuelOrder fuelOrder = findFuelOrderById(fuelOrderId)
		if(!fuelOrder){
			return
		}
		if(STATUS_IN_PROGRESS!=fuelOrder.getFuelOrderStatus().getName()){
			throw new Exception("This order can't be cancelled since it's in status: " + fuelOrder.getFuelOrderStatus().getName())
		}
		fuelOrder.setFuelOrderStatus(getStatusCancelled())
		fuelOrder.setCancelledDate(new Date())
		fuelOrder = fuelOrder.save()
		addToHistory(fuelOrder, "Cancelled")
		return(fuelOrder)
	}

	List<FuelOrder> findAllFuelOrdersToDispatch(){
		return(fuelOrderRepository.findByFuelOrderStatusInOrderById([getStatusInProgress(), getStatusDispatching()]))
	}

	FuelOrder dispatchOrder(parameters){
		Long fuelOrderId = parameters.fuelOrderId
		String comments = parameters.comments
		SecUser dispatcher = parameters.dispatcher
		FuelOrder fuelOrder = findFuelOrderById(fuelOrderId)
		if(!fuelOrder){
			return
		}
		fuelOrder.setDispatcher(dispatcher)
		fuelOrder.setDispatchedDate(new Date())
		fuelOrder.setFuelOrderStatus(getStatusInRoute())
		fuelOrder.setDispatcherComments(comments)
		fuelOrder = fuelOrder.save()
		addToHistory(fuelOrder, "Dispatched")
		return(fuelOrder)
	}

	FuelOrder changeToDispatching(Long fuelOrderId){
		FuelOrder fuelOrder = findFuelOrderById(fuelOrderId)
		if(!fuelOrder){
			return
		}
		fuelOrder.setFuelOrderStatus(getStatusDispatching())
		fuelOrder = fuelOrder.save()
		addToHistory(fuelOrder, "Started dispatching")
		return(fuelOrder)
	}

	FuelOrder finishOrder(Long fuelOrderId){
		FuelOrder fuelOrder = findFuelOrderById(fuelOrderId)
		if(!fuelOrder){
			return
		}
		fuelOrder.setFuelOrderStatus(getStatusFinished())
		fuelOrder = fuelOrder.save()
		addToHistory(fuelOrder, "Finished")
		return(fuelOrder)
	}

	FuelOrderHistory addToHistory(FuelOrder fuelOrder, String description){
		return(new FuelOrderHistory(fuelOrder: fuelOrder, secUser: securityService.getPrincipal(), date: new Date(), description: description).save())
	}

	def searchOrders(parameters){
		EntityManager em = emf.createEntityManager();
		String jqlQuery = "from FuelOrder as f"
		Map<String, Object> queryParams = new HashMap<String, Object>()
		List<String> whereClauses = new ArrayList<String>()
		if(parameters.orderId){
			queryParams.put("orderId", Long.valueOf(parameters.orderId))
			whereClauses << "f.id=:orderId"
		}
		if(parameters.salesman){
			queryParams.put("salesman", parameters.salesman)
			whereClauses << "f.salesman=:salesman"
		}
		if(parameters.customer){
			queryParams.put("customer", parameters.customer)
			whereClauses << "f.customer=:customer"
		}
		if(parameters.dateCreatedFrom){
			queryParams.put("dateCreatedFrom", parameters.dateCreatedFrom)
			whereClauses << "f.creationDate>=:dateCreatedFrom"
		}
		if(parameters.dateCreatedUntil){
			queryParams.put("dateCreatedUntil", parameters.dateCreatedUntil)
			whereClauses << "f.creationDate<=:dateCreatedUntil"
		}
		if(parameters.fuelOrderStatus){
			queryParams.put("fuelOrderStatus", parameters.fuelOrderStatus)
			whereClauses << "f.fuelOrderStatus=:fuelOrderStatus"
		}

		if(parameters.dateDeliveredFrom){
			queryParams.put("dateDeliveredFrom", parameters.dateDeliveredFrom)
			whereClauses << "f.deliveryDate>=:dateDeliveredFrom"
		}
		if(parameters.dateDeliveredUntil){
			queryParams.put("dateDeliveredUntil", parameters.dateDeliveredUntil)
			whereClauses << "f.deliveryDate<=:dateDeliveredUntil"
		}

		if(whereClauses.size()>0){
			jqlQuery += " where " + whereClauses.join(' and ')
		}
		jqlQuery += " order by f.id desc"
		Query query = em.createQuery(jqlQuery);
		if(queryParams.size()>0){
			queryParams.each{ String parameterName, Object value ->
				query.setParameter(parameterName, value)
			}
		}
		query.setMaxResults(100)
		return(query.getResultList())
	}

	List<FuelOrderStatus> findAllFuelOrderStatus(){
		return(FuelOrderStatus.findAll())
	}

	String getFuelProductsHTMLTable(FuelOrder fuelOrder){
	    String table = """  <table style="border: 1px solid #ccc" width="100%">
	                            <thead style="background-color:#DDD">
	                            <tr>
	                                <th colspan="3" style="text-align:center;"><b>Fuel products</b></th>
	                            </tr>
	                            <tr>
	                                <th width="40px"></th>
	                                <th>Product</th>
	                                <th>Gallons</th>
	                            </tr>
	                            </thead>
	                            <tbody>
	                    """
	        List<FuelOrderFuel> fuelOrderFuels = fuelOrder.getFuelOrderFuels()
	        fuelOrderFuels.each{ FuelOrderFuel fuelOrderFuel ->
	            table += '<tr style="border: 1px solid #ccc"><td width="40px" style="background-color:' + fuelOrderFuel.getFuelProduct().getColor() + '"></td><td>' + fuelOrderFuel.getFuelProduct().getName() + "</td><td>" + fuelOrderFuel.getGallons() + "</td></tr>"
	        }
	    table += "</tbody></table>"
	    return(table)
	}

	String getOtherProductsHTMLTable(FuelOrder fuelOrder){
	    String table = """  <table style="border: 1px solid #ccc" width="100%">
	                            <thead style="background-color:#DDD">
	                            <tr>
	                                <th colspan="2" style="text-align:center;"><b>Other products</b></th>
	                            </tr>
	                            <tr>
	                                <th>Product</th>
	                                <th>Quantity</th>
	                            </tr>
	                            </thead>
	                            <tbody>
	                    """
	        List<FuelOrderOther> fuelOrderOthers = fuelOrder.getFuelOrderOthers()
	        fuelOrderOthers.each{ FuelOrderOther fuelOrderOther ->
	            table += "<tr style=\"border: 1px solid #ccc\"><td>" + fuelOrderOther.getOtherProduct().getName() + "</td><td>" + fuelOrderOther.getQuantity() + "</td></tr>"
	        }
	    table += "</tbody></table>"
	    return(table)
	}
	

	File generateOrderPDF(Long fuelOrderId){
		FuelOrder fuelOrder = findFuelOrderById(fuelOrderId)
		try {
			Document document = new Document(PageSize.LETTER);
			FileOutputStream fileOutputStream = new FileOutputStream("tmp/order_" + fuelOrderId + ".pdf")
			PdfWriter pdfWriter = PdfWriter.getInstance(document, fileOutputStream)
			document.open();
			document.addAuthor("Atkloud");
			document.addCreator("Atkloud");
			document.addSubject("Order #" + fuelOrderId);
			document.addCreationDate();
			document.addTitle("Order #" + fuelOrderId);
			String str = """<html>
			        <body>
			                <h1>Order ${'#' + fuelOrder.getId()}</h1>
			                Printed: ${new Date().format("MM/dd/yyyy HH:mm a")}
			                <table style="border: 1px solid #ccc" width="100%">
			                    <thead style="background-color:#DDD">
			                    <tr>
			                        <th colspan="2" style="text-align:center;"><b>Order info</b></th>
			                    </tr>
								</thead>
			                    <tbody>
			                        <tr style="border: 1px solid #ccc"><td><b>Date created</b></td><td>${fuelOrder.getCreationDate().format("MM/dd/yyyy HH:mm a")}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>Salesman</b></td><td>${fuelOrder.getSalesman().getFullName()}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>Status</b></td><td>${fuelOrder.getFuelOrderStatus().getName()}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>Salesman comments</b></td><td>${fuelOrder.getComments() ?: ""}</td></tr>
			                    </tbody>
							</table>
			                <table style="border: 1px solid #ccc" width="100%">
			                    <thead style="background-color:#DDD">
			                    <tr>
			                        <th colspan="2" style="text-align:center;"><b>Ship to</b></th>
			                    </tr>
								</thead>
			                    <tbody>
			                        <tr style="border: 1px solid #ccc"><td><b>Customer</b></td><td>${fuelOrder.getCustomer().getName()}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>City</b></td><td>${fuelOrder.getCity()}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>Address</b></td><td>${fuelOrder.getAddress()}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>Zip code</b></td><td>${fuelOrder.getZipCode() ?: ""}</td></tr>
			                    </tbody>
							</table>
			                <table style="border: 1px solid #ccc" width="100%">
			                    <thead style="background-color:#DDD">
			                    <tr>
			                        <th colspan="2" style="text-align:center;"><b>Contact</b></th>
			                    </tr>
								</thead>
			                    <tbody>
			                        <tr style="border: 1px solid #ccc"><td><b>Name</b></td><td>${fuelOrder.getContactName()}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>City</b></td><td>${fuelOrder.getContactPhone() ?: ""}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>Address</b></td><td>${fuelOrder.getContactEmail() ?: ""}</td></tr>
			                    </tbody>
							</table>
			                <table style="border: 1px solid #ccc" width="100%">
			                    <thead style="background-color:#DDD">
			                    <tr>
			                        <th colspan="2" style="text-align:center;"><b>Delivery</b></th>
			                    </tr>
								</thead>
			                    <tbody>
			                        <tr style="border: 1px solid #ccc"><td><b>Date</b></td><td>${fuelOrder.getDeliveryDate().format("MM/dd/yyyy")}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>Time</b></td><td>${fuelOrder.getDeliveryDate().format("HH:mm a")}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>Dispatched date</b></td><td>${fuelOrder.getDispatchedDate() ? fuelOrder.getDispatchedDate().format("MM/dd/yyyy HH:mm a") : ""}</td></tr>
			                        <tr style="border: 1px solid #ccc"><td><b>Dispatcher comments</b></td><td>${fuelOrder.getDispatcherComments() ?: ""}</td></tr>
			                    </tbody>
							</table>
			                ${getFuelProductsHTMLTable(fuelOrder)}
			                ${getOtherProductsHTMLTable(fuelOrder)}
				</body>
			    </html>
			"""
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			worker.parseXHtml(pdfWriter, document, new StringReader(str));
			document.close();
			return(new File("tmp/order_" + fuelOrderId + ".pdf"))
		}
		catch (Exception e) {
			e.printStackTrace();
			return(null)
		}
	}
}
