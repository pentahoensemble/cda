package pt.webdetails.cda.dataaccess.hci;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.pentaho.reporting.engine.classic.core.ParameterDataRow;
import org.pentaho.reporting.engine.classic.core.cache.CachingDataFactory;
import org.pentaho.reporting.engine.classic.core.util.CloseableTableModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pt.webdetails.cda.connections.ConnectionCatalog.ConnectionType;
import pt.webdetails.cda.connections.hci.HciAuthResponse;
import pt.webdetails.cda.connections.hci.HciConnection;
import pt.webdetails.cda.connections.hci.HciFacetRequests;
import pt.webdetails.cda.connections.hci.HciSearchRequest;
import pt.webdetails.cda.connections.hci.HciSearchResultsModel;
import pt.webdetails.cda.dataaccess.QueryException;
import pt.webdetails.cda.dataaccess.SimpleDataAccess;
import pt.webdetails.cda.utils.HttpUtil;
import pt.webdetails.cda.utils.HttpUtil.Response;

public class HciDataAccess extends SimpleDataAccess {
	
	private static final Log logger = LogFactory.getLog( HciDataAccess.class );
	
	public HciDataAccess() {}
	
	public HciDataAccess(final Element element ) {
		super( element );
	}

	@Override
	protected IDataSourceQuery performRawQuery(ParameterDataRow parameterDataRow)
			throws QueryException {
		String url = null;
		HciDataSourceQuery query = null;
		HciConnection connection;
		try {
			connection = (HciConnection) getCdaSettings().getConnection( getConnectionId() );
			url = String.format("%s/api/search/query", connection.getConnectionInfo().getUrl());
			String jsonRequest = buildRequest();
			String authToken = getAuthToken(connection);
			Map<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");
			headerMap.put("Authorization", authToken);
			Response response = HttpUtil.doPost(url, jsonRequest, headerMap);
			if (response.getStatusCode() == 200) {
				String body = response.getBody();
				HciSearchResultsModel searchResults = (HciSearchResultsModel) deserializeFromJson(body, HciSearchResultsModel.class);
				HciTableModel model = new HciTableModel(searchResults.getResults());			
				query = new HciDataSourceQuery (model, null);
			}
		} catch (Exception e) {
			logger.debug("Error in performing HCI search query: " + e.getLocalizedMessage());
		}
		return query;
	}

	protected String getAuthToken(HciConnection connection) {
		String token = null;
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("grant_type", "password"));
		paramList.add(new BasicNameValuePair("username", connection.getConnectionInfo().getUsername()));
		paramList.add(new BasicNameValuePair("password", connection.getConnectionInfo().getPassword()));
		paramList.add(new BasicNameValuePair("scope", "*"));
		paramList.add(new BasicNameValuePair("client_secret", "hci-client"));
		paramList.add(new BasicNameValuePair("client_id", "hci-client"));
		
		String url = String.format("%s/auth/oauth", connection.getConnectionInfo().getUrl());
		
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/x-www-form-urlencoded");
		
		try {
			Response response = HttpUtil.doPost(url, new UrlEncodedFormEntity(paramList), headerMap);
			if (response.getStatusCode() == 200) {
				String body = response.getBody();
				HciAuthResponse responseModel = (HciAuthResponse) deserializeFromJson(body, HciAuthResponse.class);
				token = responseModel.getAccessToken();
			}
		} catch (Exception e) {
			logger.debug("Error in obtaining auth token: " + e.getLocalizedMessage());
		}
		
		return "Bearer " + token;
	}

	protected String buildRequest() {
		logger.debug( "Building HCI search request" );
		HciSearchRequest searchRequest = new HciSearchRequest();	
		SAXReader reader = new SAXReader();
		Document doc;
		try {
			doc = (Document) reader.read(new StringReader(getQuery()));
			parseXMLQuery(searchRequest, doc.getRootElement());
		} catch (Exception e) {
			logger.debug("Error in parsing XML query: " + e.getLocalizedMessage());
		} 
		
		String request = serializeToJson(searchRequest);
		return request;
	}

	@SuppressWarnings("unchecked")
	protected void parseXMLQuery(HciSearchRequest searchRequest, Element ele) {
		String indexName = (String) ele.selectObject( "string(./schemaName)" );
		String queryString = (String) ele.selectObject( "string(./query)" );
		searchRequest.setQueryString(queryString);
		searchRequest.setIndexName(indexName);
		ArrayList<HciFacetRequests> facetRequests = new ArrayList<HciFacetRequests>();
		List<Node> nodes = ele.selectNodes("./facetRequests/facet");
		
		for (Node node : nodes) {
			HciFacetRequests facets = new HciFacetRequests();
			facets.setFieldName(node.valueOf("@field"));
			facets.setMaxCount(Integer.parseInt(node.valueOf("@maxCount")));
			facets.setMinCount(Integer.parseInt(node.valueOf("@minCount")));
			List<Node> subNodes = node.selectNodes("termFilter");
			ArrayList<String> termFilters = new ArrayList<String>();
			for (Node subNode : subNodes) {
				termFilters.add(subNode.getText());
			}
			facets.setTermFilters(termFilters);
			facetRequests.add(facets);
		}
		String offset = (String) ele.selectObject( "string(./offset)" );
		if (offset != null && !offset.isEmpty()) {
			searchRequest.setOffset(Integer.parseInt(offset));
		}
		
		String itemsToReturn = (String) ele.selectObject( "string(./itemsToReturn)" );
		if (itemsToReturn != null && !itemsToReturn.isEmpty()) {
			searchRequest.setItemsToReturn(Integer.parseInt(itemsToReturn));
		} else {
			searchRequest.setItemsToReturn(100);
		}
		searchRequest.setFacetRequests(facetRequests);
	}

	protected String serializeToJson(Object obj) {
		Gson gson = null;
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		gson = builder.create();
		return gson.toJson(obj);
	}
	
	protected Object deserializeFromJson(String json, Class<?> classObject) {
		Gson gson = new Gson();
		return gson.fromJson(json, classObject);
	}

	@Override
	public String getType() {
		return "hci";
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.HCI;
	}
	
	protected static class HciDataSourceQuery implements IDataSourceQuery {

	    private TableModel tableModel;
	    private CachingDataFactory localDataFactory;

	    public HciDataSourceQuery( TableModel tm, CachingDataFactory df ) {
	      this.tableModel = tm;
	      this.localDataFactory = df;
	    }

	    @Override
	    public TableModel getTableModel() {
	      return tableModel;
	    }

	    @Override
	    public void closeDataSource() throws QueryException {
	    	if ( getTableModel() instanceof CloseableTableModel ) {
	            final CloseableTableModel ctm = (CloseableTableModel) getTableModel();
	            ctm.close();
	        }
	    }
	}

}
