package net.app.rest.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

	@XmlElement
    private Integer id;
	
	@XmlElement
    private Integer cartons;
	
	@XmlElement
    private Integer units;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCartons() {
		return cartons;
	}

	public void setCartons(Integer cartons) {
		this.cartons = cartons;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}
	
	
}
