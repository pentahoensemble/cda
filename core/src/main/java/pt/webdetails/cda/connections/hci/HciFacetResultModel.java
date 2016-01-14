package pt.webdetails.cda.connections.hci;

import java.util.List;

public class HciFacetResultModel {

	private String fieldName;
	private List<HciFacetTermCount> termCounts;
	
	public HciFacetResultModel() {}
	
	public HciFacetResultModel(String fieldName, List<HciFacetTermCount> termCounts) {
		this.fieldName = fieldName;
		this.termCounts = termCounts;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public List<HciFacetTermCount> getTermCounts() {
		return termCounts;
	}
	
	public void setTermCounts(List<HciFacetTermCount> termCounts) {
		this.termCounts = termCounts;
	}
	
}
