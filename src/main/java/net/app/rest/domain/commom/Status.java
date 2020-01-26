package net.app.rest.domain.commom;

public enum Status {
	ACTIVE("active"), INACTIVE("inactive");

	/**
	 * Value
	 */
	private String value;

	private Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
