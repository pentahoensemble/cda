package pt.webdetails.cda.dataaccess.hci;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;
import org.pentaho.reporting.engine.classic.core.ParameterDataRow;

import pt.webdetails.cda.connections.hci.HciConnection;
import pt.webdetails.cda.connections.hci.HciSearchResultsModel;
import pt.webdetails.cda.dataaccess.QueryException;
import pt.webdetails.cda.utils.HttpUtil;
import pt.webdetails.cda.utils.HttpUtil.Response;

public class HciFacetsDataAccess extends HciDataAccess {
	
	public HciFacetsDataAccess() {}
	
	public HciFacetsDataAccess(final Element element ) {
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
			url = connection.getConnectionInfo().getUrl();
			String jsonRequest = buildRequest();
			Map<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");
			Response response = HttpUtil.doPost(url, jsonRequest, headerMap);
			if (response.getStatusCode() == 200) {
				String body = response.getBody();
				HciSearchResultsModel searchResults = (HciSearchResultsModel) deserializeFromJson(body, HciSearchResultsModel.class);
				HciTableModel model = new HciTableModel(searchResults.getResults());
				query = new HciDataSourceQuery (model, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
	
	

}
