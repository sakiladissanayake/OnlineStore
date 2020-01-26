package net.app.rest.domain;

import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Products {

	@XmlAttribute
	private Integer size;

	/**
	 * Link
	 */
	@XmlJavaTypeAdapter(Link.JaxbAdapter.class)
	@XmlElement
	private Link link;

	/**
	 * List of products
	 */
	@XmlElement
	private List<Product> products;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
