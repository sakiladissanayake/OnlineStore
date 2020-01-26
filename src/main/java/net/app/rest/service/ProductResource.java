package net.app.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import net.app.dao.ProductDB;
import net.app.rest.domain.Item;
import net.app.rest.domain.Items;
import net.app.rest.domain.Price;
import net.app.rest.domain.PriceList;
import net.app.rest.domain.Product;
import net.app.rest.domain.Products;
import net.app.rest.domain.commom.Message;
import net.app.rest.domain.commom.Status;

@Path("/products")
@Produces("application/xml")
public class ProductResource {

	@Context
	UriInfo uriInfo;

	/**
	 * @return Use this method to get all products
	 */
	@GET
	public Products getProduts() {

		List<Product> list = ProductDB.getAllProducts();

		Products products = new Products();
		products.setProducts(list);
		products.setSize(list.size());

		// Set link for primary collection
		Link link = Link.fromUri(uriInfo.getPath()).rel("uri").build();
		products.setLink(link);

		// Set links in product items
		for (Product product : list) {
			Link lnk = Link.fromUri(uriInfo.getPath() + "/" + product.getId()).rel("self").build();
			product.setLink(lnk);
		}
		return products;
	}

	/**
	 * @param id
	 * @return Use this method to get product by id
	 */
	@GET
	@Path("/{id}")
	public Response getProductById(@PathParam("id") Integer id) {
		Product product = ProductDB.getProduct(id);

		if (product == null) {
			return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
		}

		if (product != null) {
			UriBuilder builder = UriBuilder.fromResource(ProductResource.class).path(ProductResource.class,
					"getProductById");
			Link link = Link.fromUri(builder.build(id)).rel("self").build();
			product.setLink(link);
		}

		return Response.status(javax.ws.rs.core.Response.Status.OK).entity(product).build();
	}

	/**
	 * @param product
	 * @return Use this method to create new product
	 */
	@POST
	@Consumes("application/xml")
	public Response createProduct(Product product) {
		if (product.getName() == null) {
			return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
					.entity(new Message("Product name not found")).build();
		}

		if (product.getUnitspercarton() == null) {
			return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
					.entity(new Message("Product Unit per carton not found")).build();
		}

		if (product.getPrice() == null) {
			return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
					.entity(new Message("Product price not found")).build();
		}

		Integer id = ProductDB.createProduct(product.getName(), product.getUnitspercarton(), product.getPrice(),
				product.getStatus());
		if (id == 1) {
			return Response.status(javax.ws.rs.core.Response.Status.CREATED)
					.entity(new Message("Product Added Successfully")).build();
		} else {
			return Response.status(javax.ws.rs.core.Response.Status.EXPECTATION_FAILED)
					.entity(new Message("Product Added Failed")).build();
		}

	}

	/**
	 * @param id
	 * @param product
	 * @return Use this method to update product
	 */
	@PUT
	@Path("/{id}")
	@Consumes("application/xml")
	public Response updateProduct(@PathParam("id") Integer id, Product product) {

		Product originalProduct = ProductDB.getProduct(id);
		if (originalProduct == null) {
			return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
		}

		if (product.getName() == null) {
			product.setName(originalProduct.getName());
		}

		if (product.getUnitspercarton() == null) {
			product.setUnitspercarton(originalProduct.getUnitspercarton());
		}

		if (product.getPrice() == null) {
			product.setPrice(originalProduct.getPrice());
		}

		if (product.getStatus() == null) {
			product.setStatus(originalProduct.getStatus());
		}

		if (product.getId() == null) {
			product.setId(originalProduct.getId());
		}

		if (product.getLink() == null) {
			product.setLink(originalProduct.getLink());
		}

		ProductDB.updateProduct(id, product);
		return Response.status(javax.ws.rs.core.Response.Status.OK).entity(new Message("Product Updated Successfully"))
				.build();
	}

	/**
	 * @param id
	 * @return Use this method to delete method
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteProduct(@PathParam("id") Integer id) {

		Product originalProduct = ProductDB.getProduct(id);
		if (originalProduct == null) {
			return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
		}

		ProductDB.removeProduct(id);
		return Response.status(javax.ws.rs.core.Response.Status.OK).entity(new Message("Product Deleted Successfully"))
				.build();
	}

	/**
	 * @param items
	 * @return Use this method to calculate total price
	 */
	@POST
	@Path("/calculatePrice")
	@Consumes("application/xml")
	public PriceList calculatePrice(Items items) {

		PriceList finalPriceList = new PriceList();
		List<Price> priceList = new ArrayList<>();

		for (Item item : items.getItem()) {
			Price price = new Price();

			if (item.getId() != null && ProductDB.getProduct(item.getId()) != null) {
				Double totalPrice = ProductDB.calculatePrice(item);
				price.setItemid(item.getId());
				price.setName(ProductDB.getProduct(item.getId()).getName());
				price.setPrice(totalPrice);
				priceList.add(price);
			}
		}

		finalPriceList.setPrice(priceList);
		return finalPriceList;

	}

	/**
	 * Initialize the application with these two default products
	 */
	static {
		ProductDB.createProduct("Penguin-ears", 20, 175.00, Status.ACTIVE);
		ProductDB.createProduct("Horseshoe", 5, 825.00, Status.ACTIVE);
	}

}
