package pt.webdetails.cda.dataaccess.hci;

import org.dom4j.Element;
import org.pentaho.reporting.engine.classic.core.ParameterDataRow;

import pt.webdetails.cda.connections.ConnectionCatalog.ConnectionType;
import pt.webdetails.cda.dataaccess.QueryException;
import pt.webdetails.cda.dataaccess.SimpleDataAccess;

public class HciDataAccess extends SimpleDataAccess {
	
	public HciDataAccess() {}
	
	public HciDataAccess(final Element element ) {
		super( element );
	}

	@Override
	protected IDataSourceQuery performRawQuery(ParameterDataRow parameterDataRow)
			throws QueryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionType getConnectionType() {
		// TODO Auto-generated method stub
		return null;
	}

}
