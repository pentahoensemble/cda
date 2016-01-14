package pt.webdetails.cda.dataaccess.hci;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import pt.webdetails.cda.connections.hci.HciResultsItemModel;

public class HciTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 327163363729345509L;
	
	private List<HciResultsItemModel> searchData = new ArrayList<HciResultsItemModel>();
	
	public HciTableModel() {}
	
	public HciTableModel(List<HciResultsItemModel> searchData) {
		this.searchData = searchData;
	}
	
	@Override
	public int getRowCount() {
		return searchData.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String value = null;
		HciResultsItemModel item = searchData.get(rowIndex);
		switch(columnIndex) {
			case 0:
				value = item.getId();
				break;
			case 1:
				value = item.getTitle();
				break;
			case 2: 
				value = item.getLink();
				break;
			default:
				value = String.format("Invalid columnIndex %d", columnIndex);
				break;
		}
		return value;
	}

}
