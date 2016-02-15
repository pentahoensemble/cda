package pt.webdetails.cda.connections.hci;

import java.util.ArrayList;

public class HciFacetRequests {
	
	private String fieldName;
	private int minCount;
	private int maxCount;
	private ArrayList<String> termFilters;
	
	public HciFacetRequests() {}
	
	public HciFacetRequests(String fieldName, int minCount, int maxCount,
							ArrayList<String> termFilters) {
		this.fieldName = fieldName;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.termFilters = termFilters;
	}
	
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getMinCount() {
		return minCount;
	}

	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public ArrayList<String> getTermFilters() {
		return termFilters;
	}

	public void setTermFilters(ArrayList<String> termFilters) {
		this.termFilters = termFilters;
	}
	
	

}
