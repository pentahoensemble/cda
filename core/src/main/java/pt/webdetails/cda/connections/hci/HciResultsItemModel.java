package pt.webdetails.cda.connections.hci;

import java.util.List;

public class HciResultsItemModel {
	
	private String id;
	private String title;
	private String link;
	private String previewText;
	private String customTextFields;
	private Double relevance;
	private List<HciFacetResultModel> facets;
	
	public HciResultsItemModel() {}
	
	public HciResultsItemModel(String id, String title, String link, 
								String previewText, String customTextFields, 
								Double relevance, List<HciFacetResultModel> facets) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.previewText = previewText;
		this.customTextFields = customTextFields;
		this.relevance = relevance;
		this.facets = facets;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPreviewText() {
		return previewText;
	}

	public void setPreviewText(String previewText) {
		this.previewText = previewText;
	}

	public String getCustomTextFields() {
		return customTextFields;
	}

	public void setCustomTextFields(String customTextFields) {
		this.customTextFields = customTextFields;
	}

	public Double getRelevance() {
		return relevance;
	}

	public void setRelevance(Double relevance) {
		this.relevance = relevance;
	}

	public List<HciFacetResultModel> getFacets() {
		return facets;
	}

	public void setFacets(List<HciFacetResultModel> facets) {
		this.facets = facets;
	}

}
