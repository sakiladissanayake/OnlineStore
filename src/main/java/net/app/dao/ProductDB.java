package net.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.app.rest.domain.Product;
import net.app.rest.domain.commom.Status;

public class ProductDB {
	 
	private static Map<Integer, Product> productDB = new ConcurrentHashMap<Integer, Product>();
	private static AtomicInteger idCounter = new AtomicInteger();
	
    public static Integer createProduct(String name, Integer unitspercarton, Double price, Status status){
        Product product = new Product();
        product.setId(idCounter.incrementAndGet());
        product.setName(name);
        product.setUnitspercarton(unitspercarton);
        product.setPrice(price);
        product.setStatus(status);
        productDB.put(product.getId(), product);
         
        return product.getId();
    }
    
    public static Product getProduct(Integer id) {
    	return productDB.get(id);
    }
    
    public static List<Product> getAllProducts() {
    	return new ArrayList<Product>(productDB.values());
    }
    
    public static Product removeProduct(Integer id){
        return productDB.remove(id);
    }
     
    public static Product updateProduct(Integer id, Product product){
        return productDB.put(id, product);
    }
    

}
