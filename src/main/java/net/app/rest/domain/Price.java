package net.app.rest.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "price")
@XmlAccessorType(XmlAccessType.FIELD)
public class Price {

	/**
	 * Item id
	 */
	@XmlElement
	private Integer itemid;

	/**
	 * Item name
	 */
	@XmlElement
	private String name;

	/**
	 * Total price
	 */
	@XmlElement
	private Double price;

	public Integer getItemid() {
		return itemid;
	}

	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
