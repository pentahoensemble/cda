package pt.webdetails.cda.filetests;

import javax.swing.table.TableModel;

import org.junit.Test;

import pt.webdetails.cda.query.QueryOptions;
import pt.webdetails.cda.settings.CdaSettings;

public class HciTest extends CdaTestCase {
	
	  @Test
	  public void testHciQuery() throws Exception {

	    // Define an outputStream
	    final CdaSettings cdaSettings = parseSettingsFile( "sample-hci.cda" );

	    final QueryOptions queryOptions = new QueryOptions();
	    queryOptions.setDataAccessId( "2" );
	    
	    TableModel result = doQuery( cdaSettings, queryOptions );
	    
	    System.out.println("No. of columns: " + result.getColumnCount());
	    System.out.println("No. of rows: " + result.getRowCount());
	    System.out.println();
	    for (int j=0; j<result.getRowCount(); j++) {
		    for (int i=0; i<result.getColumnCount(); i++) {
		    	System.out.print("\t" + result.getValueAt(j, i));
		    }
		    System.out.println();
	    }
	    
	    System.out.println();
	    System.out.println();
	    System.out.println();
	    System.out.println();
	    
	    result = doQuery( cdaSettings, queryOptions );
	    
	    System.out.println("No. of columns: " + result.getColumnCount());
	    System.out.println("No. of rows: " + result.getRowCount());
	    System.out.println();
	    for (int j=0; j<result.getRowCount(); j++) {
		    for (int i=0; i<result.getColumnCount(); i++) {
		    	System.out.print("\t" + result.getValueAt(j, i));
		    }
		    System.out.println();
	    }
	    
	  }
}
