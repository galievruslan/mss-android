package com.mss.infrastructure.ormlite;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.mss.domain.models.Constants;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.models.PriceListLine;
import com.mss.infrastructure.data.IReadonlyRepository;

public class OrmliteOrderPickupItemRepository implements IReadonlyRepository<OrderPickupItem> {
	
	private Dao<PriceListLine, Integer> priceListLineDao;		
	private String rawQuery;
		
	public OrmliteOrderPickupItemRepository(DatabaseHelper databaseHelper, 
			long priceListId, long warehouseId) throws Throwable {
				
		priceListLineDao = databaseHelper.getPriceListLineDao();
		rawQuery = "select " + 
				Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD  + " as [id], " +
				Constants.Tables.Product.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " as [product_id], " +
				Constants.Tables.Product.TABLE_NAME + "." + Constants.Tables.Product.NAME_FIELD + " as [product_name], " +
				Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.PriceListLine.PRICE_FIELD + " as [price], " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " as [product_uom_id], " +
				Constants.Tables.UnitOfMeasure.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " as [uom_id], " +
				Constants.Tables.UnitOfMeasure.TABLE_NAME + "." + Constants.Tables.UnitOfMeasure.NAME_FIELD + " as [uom_name], " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.COUNT_IN_BASE_FIELD + " as [uom_count_in_base], " +
				"ifnull(" + Constants.Tables.ProductRemainder.TABLE_NAME + "." + Constants.Tables.ProductRemainder.QUANTITY_FIELD + ", 0) as [quantity] " +
				" from " + Constants.Tables.PriceListLine.TABLE_NAME + " as " + Constants.Tables.PriceListLine.TABLE_NAME + " " +
				" inner join " + Constants.Tables.Product.TABLE_NAME + " as " + Constants.Tables.Product.TABLE_NAME + " on " +
				Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.PriceListLine.PRODUCT_FIELD + " = " +
				Constants.Tables.Product.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " AND " +
				Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.PriceListLine.PRICE_LIST_FIELD + " = " + String.valueOf(priceListId) +
				" inner join " + Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + " as " + Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + " on " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.PRODUCT_FIELD + " = " +
				Constants.Tables.Product.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " And " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.BASE_FIELD + " = 1 " +
				" inner join " + Constants.Tables.UnitOfMeasure.TABLE_NAME + " as " + Constants.Tables.UnitOfMeasure.TABLE_NAME + " on " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.UNIT_OF_MEASURE_FIELD + " = " +
				Constants.Tables.UnitOfMeasure.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD +
				" left join " + Constants.Tables.ProductRemainder.TABLE_NAME + " as " + Constants.Tables.ProductRemainder.TABLE_NAME + " on " +
				Constants.Tables.ProductRemainder.TABLE_NAME + "." + Constants.Tables.ProductRemainder.WAREHOUSE_FIELD + " = " + String.valueOf(warehouseId) + " AND " +
				Constants.Tables.ProductRemainder.TABLE_NAME + "." + Constants.Tables.ProductRemainder.PRODUCT_FIELD + " = " +
				Constants.Tables.Product.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " AND " + 
				Constants.Tables.ProductRemainder.TABLE_NAME + "." + Constants.Tables.ProductRemainder.UNIT_OF_MEASURE_FIELD + " = " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.UNIT_OF_MEASURE_FIELD;
				
	}

	@Override
	public OrderPickupItem getById(long id) throws Throwable {
		GenericRawResults<OrderPickupItem> rawResults =
				priceListLineDao.queryRaw(rawQuery + " where " + Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " = " + Long.toString(id) ,
				    new RawRowMapper<OrderPickupItem>() {
				            public OrderPickupItem mapRow(String[] columnNames,
				              String[] resultColumns) {				            	
				            	return new OrderPickupItem(Long.parseLong(resultColumns[0]),
				                		Long.parseLong(resultColumns[1]),
				                		resultColumns[2],
				                		Double.parseDouble(resultColumns[3]),
				                		Long.parseLong(resultColumns[4]),
				                		Long.parseLong(resultColumns[5]),
				                		resultColumns[6],
				                		Integer.parseInt(resultColumns[7]),
				                		Integer.parseInt(resultColumns[8]));
				        }
				    });
		
		OrderPickupItem orderPickupItem = rawResults.getFirstResult();
		rawResults.close();
		return orderPickupItem;
	}

	@Override
	public Iterable<OrderPickupItem> find() throws Throwable {
		GenericRawResults<OrderPickupItem> rawResults =
				priceListLineDao.queryRaw(rawQuery,
				    new RawRowMapper<OrderPickupItem>() {
				            public OrderPickupItem mapRow(String[] columnNames,
				              String[] resultColumns) {				            	
				            	return new OrderPickupItem(Long.parseLong(resultColumns[0]),
				                		Long.parseLong(resultColumns[1]),
				                		resultColumns[2],
				                		Double.parseDouble(resultColumns[3]),
				                		Long.parseLong(resultColumns[4]),
				                		Long.parseLong(resultColumns[5]),
				                		resultColumns[6],
				                		Integer.parseInt(resultColumns[7]),
				                		Integer.parseInt(resultColumns[8]));
				        }
				    });
		
		Iterable<OrderPickupItem> orderPickupItems = rawResults.getResults();
		rawResults.close();
		return orderPickupItems;
	}
	
	public Iterable<OrderPickupItem> findByPriceListId(Long[] categoryFilter) throws Throwable {		
		if (categoryFilter.length > 0) {
			String categoryFilterQuery = "(";
			for (int i = 0; i < categoryFilter.length; i++) {
				categoryFilterQuery = categoryFilterQuery + String.valueOf(categoryFilter[i]);
				if (i < categoryFilter.length - 1) {
					categoryFilterQuery = categoryFilterQuery + ", ";
				}				
			}
			categoryFilterQuery = categoryFilterQuery + ")";
			
			rawQuery = rawQuery + " WHERE " + Constants.Tables.Product.TABLE_NAME + "." + Constants.Tables.Product.CATEGORY_FIELD + " IN " + categoryFilterQuery; 
		}
		
		GenericRawResults<OrderPickupItem> rawResults =
				priceListLineDao.queryRaw(rawQuery,
				    new RawRowMapper<OrderPickupItem>() {
				            public OrderPickupItem mapRow(String[] columnNames,
				              String[] resultColumns) {
				            	
				            	return new OrderPickupItem(Long.parseLong(resultColumns[0]),
				                		Long.parseLong(resultColumns[1]),
				                		resultColumns[2],
				                		Double.parseDouble(resultColumns[3]),
				                		Long.parseLong(resultColumns[4]),
				                		Long.parseLong(resultColumns[5]),
				                		resultColumns[6],
				                		Integer.parseInt(resultColumns[7]),
				                		Integer.parseInt(resultColumns[8]));
				        }
				    });
		
		Iterable<OrderPickupItem> orderPickupItems = rawResults.getResults();
		rawResults.close();
		return orderPickupItems;
	}
	
	public Iterable<OrderPickupItem> findByPriceListId(Long[] categoryFilter, String searchCriteria) throws Throwable {		
		ArrayList<OrderPickupItem> filtredItems = new ArrayList<OrderPickupItem>();
		Pattern pattern = Pattern.compile(Pattern.quote(searchCriteria), Pattern.CASE_INSENSITIVE);
			
		Iterable<OrderPickupItem> items = findByPriceListId(categoryFilter);
		for (OrderPickupItem item : items) {
			if (pattern.matcher(item.getProductName()).find())
				filtredItems.add(item);
		}			
		return filtredItems;
	}
	
}
