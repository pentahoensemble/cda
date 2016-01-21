package pt.webdetails.cda.connections.hci;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HciSearchRequest {
	
	@XmlElement(name="schemaName")
	private String schemaName;
	
	@XmlElement(name="query")
	private String queryString;
	
	private int offset;
	
	private int itemsToReturn;
	
	private ArrayList<HciFacetRequests> facetRequests;
	
	public HciSearchRequest() {}
	
	public HciSearchRequest(String schemaName, String queryString, int offset,
							int itemsToReturn, ArrayList<HciFacetRequests> facetRequests) {
		this.setSchemaName(schemaName);
		this.setQueryString(queryString);
		this.setOffset(offset);
		this.setItemsToReturn(itemsToReturn);
		this.setFacetRequests(facetRequests);
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getItemsToReturn() {
		return itemsToReturn;
	}

	public void setItemsToReturn(int itemsToReturn) {
		this.itemsToReturn = itemsToReturn;
	}

	public ArrayList<HciFacetRequests> getFacetRequests() {
		return facetRequests;
	}

	public void setFacetRequests(ArrayList<HciFacetRequests> facetRequests) {
		this.facetRequests = facetRequests;
	}	

}
