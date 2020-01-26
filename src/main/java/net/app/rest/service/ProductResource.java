package net.app.rest.service;

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
import net.app.rest.domain.Product;
import net.app.rest.domain.Products;
import net.app.rest.domain.commom.Message;
import net.app.rest.domain.commom.Status;

@Path("/products")
@Produces("application/xml")
public class ProductResource {
	
	@Context
    UriInfo uriInfo;
	
    @GET
    public Products getProduts() {
          
        List<Product> list = ProductDB.getAllProducts();
          
        Products products = new Products();
        products.setProducts(list);
        products.setSize(list.size());
          
        //Set link for primary collection
        Link link = Link.fromUri(uriInfo.getPath()).rel("uri").build();
        products.setLink(link);
          
        //Set links in product items
        for(Product product: list){
            Link lnk = Link.fromUri(uriInfo.getPath() + "/" + product.getId()).rel("self").build();
            product.setLink(lnk);
        }
        return products;
    }
    
    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Integer id){
        Product product = ProductDB.getProduct(id);
         
        if(product == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }
          
        if(product != null){
            UriBuilder builder = UriBuilder.fromResource(ProductResource.class)
                                            .path(ProductResource.class, "getProductById");
            Link link = Link.fromUri(builder.build(id)).rel("self").build();
            product.setLink(link);
        }
          
        return Response.status(javax.ws.rs.core.Response.Status.OK).entity(product).build();
    }
    
    @POST
    @Consumes("application/xml")
    public Response createProduct(Product product){
        if(product.getName() == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                            .entity(new Message("Product name not found"))
                            .build();
        }
        
        if(product.getUnitspercarton() == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                            .entity(new Message("Product Unit per carton not found"))
                            .build();
        }
        
        if(product.getPrice() == null)  {
            return Response.status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                            .entity(new Message("Product price not found"))
                            .build();
        }
 
        Integer id = ProductDB.createProduct(product.getName(), product.getUnitspercarton(), product.getPrice(), product.getStatus());
        Link lnk = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
        return Response.status(javax.ws.rs.core.Response.Status.CREATED).entity(new Message("Product Added Successfully")).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes("application/xml")
    public Response updateProduct(@PathParam("id") Integer id, Product product){
         
        Product originalProduct = ProductDB.getProduct(id);
        if(originalProduct == null) {
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
        return Response.status(javax.ws.rs.core.Response.Status.OK).entity(new Message("Product Updated Successfully")).build();
    }
     
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Integer id){
         
        Product originalProduct = ProductDB.getProduct(id);
        if(originalProduct == null) {
            return Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
        }
         
        ProductDB.removeProduct(id);
        return Response.status(javax.ws.rs.core.Response.Status.OK).entity(new Message("Product Deleted Successfully")).build();
    }
      
    /**
     * Initialize the application with these two default configurations
     * */
    static {
        ProductDB.createProduct("Penguin-ears", 20, 175.00, Status.ACTIVE);
        ProductDB.createProduct("Horseshoe", 5, 825.00, Status.ACTIVE);
    }

}
