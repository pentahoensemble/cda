package pt.webdetails.cda.dataaccess.hci;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.pentaho.reporting.engine.classic.core.ParameterDataRow;

import pt.webdetails.cda.connections.hci.HciConnection;
import pt.webdetails.cda.connections.hci.HciFacetResultModel;
import pt.webdetails.cda.connections.hci.HciFacetTermCount;
import pt.webdetails.cda.connections.hci.HciSearchResultsModel;
import pt.webdetails.cda.dataaccess.QueryException;
import pt.webdetails.cda.utils.HttpUtil;
import pt.webdetails.cda.utils.HttpUtil.Response;

public class HciFacetsDataAccess extends HciDataAccess {
	
	private String facetName;
	private static final Log logger = LogFactory.getLog( HciFacetsDataAccess.class );
	
	public HciFacetsDataAccess() {}
	
	public HciFacetsDataAccess(final Element element ) {
		super( element );
		this.facetName = element.selectSingleNode( "./FacetName" ).getText();
	}
	
	@Override
	protected IDataSourceQuery performRawQuery(ParameterDataRow parameterDataRow)
			throws QueryException {
		String url = null;
		HciDataSourceQuery query = null;
		HciConnection connection;
		HciFacetTableModel model = null;
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
				for (HciFacetResultModel result : searchResults.getFacets()) {
					if (result.getFieldName().equals(facetName)) {
						model = new HciFacetTableModel(result);
						break;
					}
				}
				if (model == null) {
					HciFacetResultModel resultModel = new HciFacetResultModel(null, new ArrayList<HciFacetTermCount>());
					model = new HciFacetTableModel(resultModel);
				}
				
				query = new HciDataSourceQuery (model, null);
			}
		} catch (Exception e) {
			logger.debug("Error in performing HCI facet search query: " + e.getLocalizedMessage());
		}
		return query;
	}

}
