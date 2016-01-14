package pt.webdetails.cda.connections.hci;

import java.util.List;

public class HciSearchResultsModel {
	private List<HciResultsItemModel> results;
	private List<HciFacetResultModel> facets;
	private Long hitCount;
	
	public HciSearchResultsModel() {}
	
	public HciSearchResultsModel(List<HciResultsItemModel> results, 
								 List<HciFacetResultModel> facets) {
		this.results = results;
		this.facets = facets;
	}
	
	public List<HciResultsItemModel> getResults() {
		return results;
	}
	
	public void setResults(List<HciResultsItemModel> results) {
		this.results = results;
	}

	public List<HciFacetResultModel> getFacets() {
		return facets;
	}

	public void setFacets(List<HciFacetResultModel> facets) {
		this.facets = facets;
	}

	public Long getHitCount() {
		return hitCount;
	}

	public void setHitCount(Long hitCount) {
		this.hitCount = hitCount;
	}	
}
