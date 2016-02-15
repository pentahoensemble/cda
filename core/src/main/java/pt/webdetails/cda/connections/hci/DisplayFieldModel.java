package pt.webdetails.cda.connections.hci;

public class DisplayFieldModel {
	
	private String name;
	private String type;
	private String value;
	private boolean highlight;
	
	public DisplayFieldModel() {}
	
	public DisplayFieldModel(String name, String type, String value, boolean highlight) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.highlight = highlight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	
	

}
