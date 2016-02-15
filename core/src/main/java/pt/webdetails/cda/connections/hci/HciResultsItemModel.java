package pt.webdetails.cda.connections.hci;

import java.util.LinkedHashMap;
import java.util.List;

public class HciResultsItemModel {
	
	private String id;
	private String title;
	private String link;
	private List<String> previewText;
	private Double relevance;
	private LinkedHashMap<String, List<String>> metadata;
	private List<DisplayFieldModel> displayFields; 
	private List<HciFacetResultModel> facets;
	
	public HciResultsItemModel() {}
	
	public HciResultsItemModel(String id, String title, String link, 
								List<String> previewText, List<DisplayFieldModel> displayFields,
								Double relevance, LinkedHashMap<String, List<String>> metadata,
								List<HciFacetResultModel> facets) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.previewText = previewText;
		this.displayFields = displayFields;
		this.relevance = relevance;
		this.facets = facets;
		this.metadata = metadata;
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

	public List<String> getPreviewText() {
		return previewText;
	}

	public void setPreviewText(List<String> previewText) {
		this.previewText = previewText;
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

	public LinkedHashMap<String, List<String>> getMetadata() {
		return metadata;
	}

	public void setMetadata(LinkedHashMap<String, List<String>> metadata) {
		this.metadata = metadata;
	}

	public List<DisplayFieldModel> getDisplayFields() {
		return displayFields;
	}

	public void setDisplayFields(List<DisplayFieldModel> displayFields) {
		this.displayFields = displayFields;
	}

}
