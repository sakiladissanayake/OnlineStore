package net.app.rest.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pricelist")
@XmlAccessorType(XmlAccessType.FIELD)
public class PriceList {
	
	@XmlElement
	private List<Price> price;

	public List<Price> getPrice() {
		return price;
	}

	public void setPrice(List<Price> price) {
		this.price = price;
	}
	
	
}
