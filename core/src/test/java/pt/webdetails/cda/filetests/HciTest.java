package pt.webdetails.cda.filetests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.localserver.LocalTestServer;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.Test;
import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pt.webdetails.cda.connections.hci.HciAuthResponse;
import pt.webdetails.cda.connections.hci.HciConnection;
import pt.webdetails.cda.connections.hci.HciFacetResultModel;
import pt.webdetails.cda.connections.hci.HciFacetTermCount;
import pt.webdetails.cda.connections.hci.HciResultsItemModel;
import pt.webdetails.cda.connections.hci.HciSearchResultsModel;
import pt.webdetails.cda.query.QueryOptions;
import pt.webdetails.cda.settings.CdaSettings;
import pt.webdetails.cda.test.util.TableModelChecker;
import pt.webdetails.cda.test.util.CdaTestHelper.SimpleTableModel;

public class HciTest extends CdaTestCase {
	
	  @Test
	  public void testHciSearchResultsQuery() throws Exception {

	    // Define an outputStream
	    final CdaSettings cdaSettings = parseSettingsFile( "sample-hci.cda" );
	    
	    QueryOptions queryOptions = new QueryOptions();
	    queryOptions.setDataAccessId( "1" );

		LocalTestServer server = new LocalTestServer(null, null);
	    server.register("/test/api/search/query", new TestHciHttpHandler());
	    server.register("/test/auth/oauth", new TestHciAuthHttpHandler());
	    server.start();
	    
	    String serverUrl = "http:/" + server.getServiceAddress() + "/test";
	    
	    HciConnection connection = (HciConnection) cdaSettings.getConnection("1");
	    
	    connection.getConnectionInfo().setUrl(serverUrl);
	    
	    TableModel result = doQuery( cdaSettings, queryOptions );
	    
	    TableModelChecker checker = new TableModelChecker();
	    Double relevance = 1.0;
	    checker.setDoubleComparison( 6, "1e-8" );
	    final SimpleTableModel expected = new SimpleTableModel(
	      new Object[] { null, null, "1",  "/opt/ensemble/content1", null, null, relevance, "SampleData1"},
	      new Object[] { null, null, "2",  "/opt/ensemble/content2", null, null, relevance, "SampleData2"});
	    
	    checker.assertEquals( expected, result );
	    Assert.assertEquals(8, result.getColumnCount());
	    Assert.assertEquals(2, result.getRowCount());
	    
	    server.stop();
	    
	  }
	  
	  @Test
	  public void testHciDataSourceFacetQuery() throws Exception {

	    // Define an outputStream
	    final CdaSettings cdaSettings = parseSettingsFile( "sample-hci.cda" );
	    
	    QueryOptions queryOptions = new QueryOptions();
	    queryOptions.setDataAccessId( "2" );

	    LocalTestServer server = new LocalTestServer(null, null);
	    server.register("/test/api/search/query", new TestHciHttpHandler());
	    server.register("/test/auth/oauth", new TestHciAuthHttpHandler());
	    server.start();
	    
	    String serverUrl = "http:/" + server.getServiceAddress() + "/test";
	    
	    HciConnection connection = (HciConnection) cdaSettings.getConnection("1");
	    
	    connection.getConnectionInfo().setUrl(serverUrl);
	    
	    TableModel result = doQuery( cdaSettings, queryOptions );
	    
	    TableModelChecker checker = new TableModelChecker();
	    checker.setDoubleComparison( 2, "1e-8" );
	    checker.setBigDecimalComparison( 3, "1e-14" );
	    final SimpleTableModel expected = new SimpleTableModel(
	      new Object[] { 11L, "jpeg"}, new Object[] {2L, "pdf"}, new Object[] {7L, "docx"});
	    
	    checker.assertEquals( expected, result );
	    Assert.assertEquals(2, result.getColumnCount());
	    Assert.assertEquals(3, result.getRowCount());
	    
	    server.stop();
	    
	  }
		
	  @Test
	  public void testHciContentTypeFacetQuery() throws Exception {

	    // Define an outputStream
	    final CdaSettings cdaSettings = parseSettingsFile( "sample-hci.cda" );
	    
	    QueryOptions queryOptions = new QueryOptions();
	    queryOptions.setDataAccessId( "3" );

	    LocalTestServer server = new LocalTestServer(null, null);
	    server.register("/test/api/search/query", new TestHciHttpHandler());
	    server.register("/test/auth/oauth", new TestHciAuthHttpHandler());
	    server.start();
	    
	    String serverUrl = "http:/" + server.getServiceAddress() + "/test";
	    
	    HciConnection connection = (HciConnection) cdaSettings.getConnection("1");
	    
	    connection.getConnectionInfo().setUrl(serverUrl);
	    
	    TableModel result = doQuery( cdaSettings, queryOptions );
	    
	    TableModelChecker checker = new TableModelChecker();
	    checker.setDoubleComparison( 2, "1e-8" );
	    checker.setBigDecimalComparison( 3, "1e-14" );
	    final SimpleTableModel expected = new SimpleTableModel(
	      new Object[] { 400L, "Content1"});
	    
	    checker.assertEquals( expected, result );
	    Assert.assertEquals(2, result.getColumnCount());
	    Assert.assertEquals(1, result.getRowCount());
	    
	    server.stop();
	    
	  }
	 
	  private static class TestHciHttpHandler implements HttpRequestHandler {

		    @Override
		    public void handle(HttpRequest request, HttpResponse response,
		        HttpContext context) throws HttpException, IOException {
		      if (request.getRequestLine().toString().startsWith("POST")) {
		        if (request instanceof BasicHttpEntityEnclosingRequest) {
		        	response.setStatusCode(200);
			        response.setEntity(new StringEntity(buildResponseString()));
		        }
		      } else {
				response.setStatusCode(200);
		        response.setEntity(new StringEntity(buildResponseString()));
		      }

		    }

			private String buildResponseString() {
				List<HciResultsItemModel> itemsList = new ArrayList<HciResultsItemModel>();
		    	HciResultsItemModel resultItem = new HciResultsItemModel("1", "SampleData1", "/opt/ensemble/content1", null, null, 1.0, null, null);
		    	itemsList.add(resultItem);
		    	resultItem = new HciResultsItemModel("2", "SampleData2", "/opt/ensemble/content2", null, null, 1.0, null, null);
		    	itemsList.add(resultItem);
		    	
		    	List<HciFacetResultModel> facetsList = new ArrayList<HciFacetResultModel>();
		    	List<HciFacetTermCount> termCountList = new ArrayList<HciFacetTermCount>();
		    	HciFacetTermCount termCount = new HciFacetTermCount("jpeg", 11L);
		    	termCountList.add(termCount);
		    	termCount = new HciFacetTermCount("pdf", 2L);
		    	termCountList.add(termCount);
		    	termCount = new HciFacetTermCount("docx", 7L);
		    	termCountList.add(termCount);
		    	
		    	HciFacetResultModel facetItem = new HciFacetResultModel("Content-Type", termCountList);
		    	facetsList.add(facetItem);
		    	
		    	termCountList = new ArrayList<HciFacetTermCount>();
		    	termCount = new HciFacetTermCount("Content1", 400L);
		    	termCountList.add(termCount);
		    	
		    	facetItem = new HciFacetResultModel("HCI-dataSourceName", termCountList);
		    	facetsList.add(facetItem);
		    	
		    	HciSearchResultsModel searchResults = new HciSearchResultsModel(itemsList, facetsList);
				return serializeToJson(searchResults);
			}

			private String serializeToJson(Object obj) {
				Gson gson = null;
				GsonBuilder builder = new GsonBuilder();
				builder.setPrettyPrinting();
				gson = builder.create();
				return gson.toJson(obj);
			}
	  }
	  
	  private static class TestHciAuthHttpHandler implements HttpRequestHandler {

		    @Override
		    public void handle(HttpRequest request, HttpResponse response,
		        HttpContext context) throws HttpException, IOException {
		      if (request.getRequestLine().toString().startsWith("POST")) {
		        if (request instanceof BasicHttpEntityEnclosingRequest) {
		        	response.setStatusCode(200);
			        response.setEntity(new StringEntity(buildResponseString()));
		        }
		      } else {
				response.setStatusCode(200);
		        response.setEntity(new StringEntity(buildResponseString()));
		      }

		    }

			private String buildResponseString() {
				HciAuthResponse response = new HciAuthResponse("eyJraWQiOiI4YTA5YzZmOS1hZDY2LTQ", 7200);
				return serializeToJson(response);
			}

			private String serializeToJson(Object obj) {
				Gson gson = null;
				GsonBuilder builder = new GsonBuilder();
				builder.setPrettyPrinting();
				gson = builder.create();
				return gson.toJson(obj);
			}
	 }
}
