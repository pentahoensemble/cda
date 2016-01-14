package pt.webdetails.cda.dataaccess.hci;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import org.dom4j.Element;
import org.pentaho.reporting.engine.classic.core.ParameterDataRow;
import org.pentaho.reporting.engine.classic.core.cache.CachingDataFactory;
import pt.webdetails.cda.connections.ConnectionCatalog.ConnectionType;
import pt.webdetails.cda.connections.hci.HciConnection;
import pt.webdetails.cda.connections.hci.HciResultsItemModel;
import pt.webdetails.cda.dataaccess.QueryException;
import pt.webdetails.cda.dataaccess.SimpleDataAccess;

public class HciDataAccess extends SimpleDataAccess {
	
	private HciConnection connection = new HciConnection();
	
	public HciDataAccess() {}
	
	public HciDataAccess(final Element element ) {
		super( element );
	}

	@Override
	protected IDataSourceQuery performRawQuery(ParameterDataRow parameterDataRow)
			throws QueryException {
		String url = buildUrl();
		
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
		
		HciDataSourceQuery query = new HciDataSourceQuery (model, null);
		
		return query;
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
