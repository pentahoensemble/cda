package pt.webdetails.cda.connections.hci;

public class HciFacetTermCount {

	private String term;
	private Long count;
	
	public HciFacetTermCount() {}
	
	public HciFacetTermCount(String term, Long count) {
		this.term = term;
		this.count = count;
	}
	
	public String getTerm() {
		return term;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
	
}
