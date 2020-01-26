package net.app.rest.domain;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.app.rest.domain.commom.Status;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {

	/**
	 * Product id
	 */
	@XmlAttribute
	private Integer id;

	/**
	 * Link
	 */
	@XmlJavaTypeAdapter(Link.JaxbAdapter.class)
	@XmlElement
	private Link link;

	/**
	 * Product name
	 */
	@XmlElement
	private String name;

	/**
	 * Units per carton
	 */
	@XmlElement
	private Integer unitspercarton;

	/**
	 * Price of carton
	 */
	@XmlElement
	private Double price;

	/**
	 * Status
	 */
	@XmlElement
	private Status status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUnitspercarton() {
		return unitspercarton;
	}

	public void setUnitspercarton(Integer unitspercarton) {
		this.unitspercarton = unitspercarton;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
