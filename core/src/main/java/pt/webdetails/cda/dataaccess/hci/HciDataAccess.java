package pt.webdetails.cda.dataaccess.hci;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.TableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.pentaho.reporting.engine.classic.core.ParameterDataRow;
import org.pentaho.reporting.engine.classic.core.cache.CachingDataFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pt.webdetails.cda.connections.ConnectionCatalog.ConnectionType;
import pt.webdetails.cda.connections.InvalidConnectionException;
import pt.webdetails.cda.connections.hci.HciConnection;
import pt.webdetails.cda.connections.hci.HciResultsItemModel;
import pt.webdetails.cda.connections.hci.HciSearchRequest;
import pt.webdetails.cda.dataaccess.QueryException;
import pt.webdetails.cda.dataaccess.SimpleDataAccess;

public class HciDataAccess extends SimpleDataAccess {
	
	private HciConnection connection; 
	private static int offset;
	
	public HciDataAccess() {}
	
	public HciDataAccess(final Element element ) {
		super( element );
		try {
			connection = new HciConnection(element);
		} catch (InvalidConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected IDataSourceQuery performRawQuery(ParameterDataRow parameterDataRow)
			throws QueryException {
		HciDataSourceQuery query = null;
//		String url = buildUrl();
//		String jsonRequest = buildRequest();
//		Map<String, String> headerMap = new HashMap<String, String>();
//		try {
//			Response response = HttpUtil.doPost(url, jsonRequest, headerMap);
//			if (response.getStatusCode() == HttpStatus.SC_CREATED) {
//				String body = response.getBody();
//				HciSearchResultsModel searchResults = (HciSearchResultsModel) deserializeFromJson(body, HciSearchResultsModel.class);
//				HciTableModel model = new HciTableModel(searchResults.getResults());			
//				query = new HciDataSourceQuery (model, null);
//			}
//		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// TODO Add query execution implementation.
		
		List<HciResultsItemModel> results = new ArrayList<HciResultsItemModel>();
		HciResultsItemModel result = new HciResultsItemModel("1", "Sample1", "http://10.76.16.60:8080/Sample1",
										null, null, 0.0, null);
		results.add(result);
		result = new HciResultsItemModel("2", "Sample2", "http://10.76.16.60:8080/Sample2",
										null, null, 0.0, null);
		results.add(result);
		result = new HciResultsItemModel("3", "Sample3", "http://10.76.16.60:8080/Sample3",
										null, null, 0.0, null);
		results.add(result);
		result = new HciResultsItemModel("4", "Sample4", "http://10.76.16.60:8080/Sample4",
										null, null, 0.0, null);
		results.add(result);
		
		HciTableModel model = new HciTableModel(results);
		
		query = new HciDataSourceQuery (model, null);
		
		return query;
	}

	private String buildRequest() {
		HciSearchRequest searchRequest = new HciSearchRequest();	
//		SAXReader reader = new SAXReader();
//		Document doc;
//		try {
//			doc = (Document) reader.read(new StringReader(getQuery()));
//			String rootElement = doc.getRootElement().asXML();
//			parseXMLQuery(searchRequest, doc.getRootElement());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} ]
		
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(HciSearchRequest.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			searchRequest = (HciSearchRequest) unmarshaller.unmarshal(new StringReader(getQuery()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String request = serializeToJson(searchRequest);
		
		return request;
	}

	private void parseXMLQuery(HciSearchRequest searchRequest, Element ele) {
		searchRequest.setQueryString((String) ele.selectObject( "string(./query)" ));
		searchRequest.setSchemaName((String) ele.selectObject( "string(./schemaName)" ));
	}

	private String serializeToJson(Object obj) {
		Gson gson = null;
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		gson = builder.create();
		return gson.toJson(obj);
	}
	
	public static Object deserializeFromJson(String json, Class<?> classObject) {
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
	
	private String buildUrl() {
		return connection.getConnectionInfo().getUrl();
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
	    public void closeDataSource() throws QueryException {}
	}

}
