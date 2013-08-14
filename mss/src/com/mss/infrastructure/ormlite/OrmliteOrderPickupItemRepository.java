package com.mss.infrastructure.ormlite;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

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
	public OrmliteOrderPickupItemRepository(DatabaseHelper databaseHelper) throws Throwable{
		priceListLineDao = databaseHelper.getPriceListLineDao();
		rawQuery = "select " + 
				Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD  + " as [id], " +
				Constants.Tables.Product.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " as [product_id], " +
				Constants.Tables.Product.TABLE_NAME + "." + Constants.Tables.Product.NAME_FIELD + " as [product_name], " +
				Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.PriceListLine.PRICE_FIELD + " as [price] " +
				"from " + Constants.Tables.PriceListLine.TABLE_NAME + " as " + Constants.Tables.PriceListLine.TABLE_NAME + " " +
				"left join " + Constants.Tables.Product.TABLE_NAME + " as " + Constants.Tables.Product.TABLE_NAME + " on " +
				Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.PriceListLine.PRODUCT_FIELD + " = " +
				Constants.Tables.Product.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD;
	}

	@Override
	public OrderPickupItem getById(long id) throws Throwable {
		GenericRawResults<OrderPickupItem> rawResults =
				priceListLineDao.queryRaw(rawQuery + " where " + Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " = " + Long.toString(id) ,
				    new RawRowMapper<OrderPickupItem>() {
				            public OrderPickupItem mapRow(String[] columnNames,
				              String[] resultColumns) {
				            	DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
				            	decimalFormat.setParseBigDecimal(true);
				            	BigDecimal price = null;
				            	try {
									price = (BigDecimal)decimalFormat.parse(resultColumns[3]);
								} catch (ParseException e) {
									price = new BigDecimal(0);
								}
				            	
				            	return new OrderPickupItem(Long.parseLong(resultColumns[0]),
				                		Long.parseLong(resultColumns[1]),
				                		resultColumns[2],
				                		price);
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
				            	DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
				            	decimalFormat.setParseBigDecimal(true);
				            	BigDecimal price = null;
				            	try {
									price = (BigDecimal)decimalFormat.parse(resultColumns[3]);
								} catch (ParseException e) {
									price = new BigDecimal(0);
								}
				            	
				            	return new OrderPickupItem(Long.parseLong(resultColumns[0]),
				                		Long.parseLong(resultColumns[1]),
				                		resultColumns[2],
				                		price);
				        }
				    });
		
		Iterable<OrderPickupItem> orderPickupItems = rawResults.getResults();
		rawResults.close();
		return orderPickupItems;
	}
	
	public Iterable<OrderPickupItem> findByPriceListId(long priceListId) throws Throwable {
		GenericRawResults<OrderPickupItem> rawResults =
				priceListLineDao.queryRaw(
						rawQuery + " where " + Constants.Tables.PriceListLine.TABLE_NAME + "." + Constants.Tables.PriceListLine.PRICE_LIST_FIELD + " = " + Long.toString(priceListId),
				    new RawRowMapper<OrderPickupItem>() {
				            public OrderPickupItem mapRow(String[] columnNames,
				              String[] resultColumns) {
				            	DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
				            	decimalFormat.setParseBigDecimal(true);
				            	BigDecimal price = null;
				            	try {
									price = (BigDecimal)decimalFormat.parse(resultColumns[3]);
								} catch (ParseException e) {
									price = new BigDecimal(0);
								}
				            	
				            	return new OrderPickupItem(Long.parseLong(resultColumns[0]),
				                		Long.parseLong(resultColumns[1]),
				                		resultColumns[2],
				                		price);
				        }
				    });
		
		Iterable<OrderPickupItem> orderPickupItems = rawResults.getResults();
		rawResults.close();
		return orderPickupItems;
	}
}
