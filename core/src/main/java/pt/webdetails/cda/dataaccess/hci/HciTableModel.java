package pt.webdetails.cda.dataaccess.hci;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import pt.webdetails.cda.connections.hci.HciResultsItemModel;

public class HciTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 327163363729345509L;
	
	private List<HciResultsItemModel> searchData = new ArrayList<HciResultsItemModel>();

	private static Map<Integer, Method> columnMap = new HashMap<Integer, Method>();
	
	public HciTableModel() {}
	
	public HciTableModel(List<HciResultsItemModel> searchData) {
		this.searchData = searchData;
		if (columnMap.isEmpty()) {
			int count = 0;
			try {
				for (PropertyDescriptor pd : Introspector.getBeanInfo(HciResultsItemModel.class).getPropertyDescriptors()) {
					if (pd.getReadMethod() != null && !"HciResultsItemModel".equals(pd.getName()) && !"class".equals(pd.getName())) {
						  columnMap.put(count++, pd.getReadMethod());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int getRowCount() {
		return searchData.size();
	}

	@Override
	public int getColumnCount() {
		return searchData.get(0).getClass().getDeclaredFields().length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HciResultsItemModel item = searchData.get(rowIndex);
		Object value =  null;
		try {
			value = columnMap.get(columnIndex).invoke(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
