package net.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.app.rest.domain.Item;
import net.app.rest.domain.Product;
import net.app.rest.domain.commom.Status;

public class ProductDB {

	private static Map<Integer, Product> productDB = new ConcurrentHashMap<Integer, Product>();
	private static AtomicInteger idCounter = new AtomicInteger();

	public static Integer createProduct(String name, Integer unitspercarton, Double price, Status status) {
		Product product = new Product();
		product.setId(idCounter.incrementAndGet());
		product.setName(name);
		product.setUnitspercarton(unitspercarton);
		product.setPrice(price);
		product.setStatus(status);
		productDB.put(product.getId(), product);

		return product.getId();
	}

	/**
	 * @param id
	 * @return Product which is related to the given id
	 */
	public static Product getProduct(Integer id) {
		return productDB.get(id);
	}

	/**
	 * @return All Products
	 */
	public static List<Product> getAllProducts() {
		return new ArrayList<Product>(productDB.values());
	}

	/**
	 * @param id
	 * @return
	 */
	public static Product removeProduct(Integer id) {
		return productDB.remove(id);
	}

	/**
	 * @param id
	 * @param product
	 * @return
	 */
	public static Product updateProduct(Integer id, Product product) {
		return productDB.put(id, product);
	}

	/**
	 * @param item
	 * @return Total Price
	 */
	public static Double calculatePrice(Item item) {
		Integer totalUnits = 0;
		Double totalPrice = 0.0;
		Product originalProduct = ProductDB.getProduct(item.getId());
		if (item.getUnits() != null & item.getCartons() != null) {
			totalUnits = item.getUnits() + (item.getCartons() * originalProduct.getUnitspercarton());
		} else if (item.getUnits() != null) {
			totalUnits = item.getUnits();
		} else if (item.getCartons() != null) {
			totalUnits = (item.getCartons() * originalProduct.getUnitspercarton());
		}

		if (totalUnits > 0) {
			if (totalUnits < originalProduct.getUnitspercarton()) {
				totalPrice = ((originalProduct.getPrice() * 1.3) * totalUnits);
				return totalPrice;
			} else {
				Integer cartons = (totalUnits / originalProduct.getUnitspercarton());
				Integer singleUnits = (totalUnits % originalProduct.getUnitspercarton());

				if (cartons >= 3) {
					totalPrice = ((cartons * originalProduct.getPrice()) * 0.7
							+ ((originalProduct.getPrice() * 1.3) * singleUnits));
					return totalPrice;
				} else {
					totalPrice = ((cartons * originalProduct.getPrice())
							+ ((originalProduct.getPrice() * 1.3) * singleUnits));
					return totalPrice;
				}

			}
		}
		return 0.0;
	}
}
