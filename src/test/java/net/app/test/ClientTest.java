package net.app.test;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import junit.framework.Assert;
import net.app.rest.domain.Item;
import net.app.rest.domain.Items;
import net.app.rest.domain.PriceList;
import net.app.rest.domain.Product;

public class ClientTest {

	/**
	 * @throws Exception
	 *             This method is used for test getProductById end point.
	 */
	@Test
	public void testGetProductById() throws Exception {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/OnlineStore/online-store/products/{id}");
		Response response = target.resolveTemplate("id", "1").request().get();
		try {
			Assert.assertEquals(200, response.getStatus());
			Product product = response.readEntity(Product.class);
			Assert.assertEquals("Penguin-ears", product.getName());
		} finally {
			response.close();
			client.close();
		}
	}

	/**
	 * @throws Exception
	 *             This method is used for test calculatePrice end point.
	 */
	@Test
	public void testCalculatePrice() throws Exception {
		List<Item> itemList = new ArrayList<>();

		Item item = new Item();
		item.setId(1);
		item.setCartons(1);
		item.setUnits(1);

		itemList.add(item);

		Items items = new Items();
		items.setItem(itemList);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/OnlineStore/online-store/products/calculatePrice");
		PriceList response = target.request().post(Entity.entity(items, MediaType.APPLICATION_XML), PriceList.class);
		try {
			Assert.assertEquals(402.5, response.getPrice().get(0).getPrice());
		} finally {
			client.close();
		}
	}
}
