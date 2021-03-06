package com.mss.infrastructure.ormlite;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.mss.domain.models.Constants;
import com.mss.domain.models.ProductUnitOfMeasure;

public class OrmliteProductUnitOfMeasureRepository extends OrmliteGenericRepository<ProductUnitOfMeasure> {

	private String rawQuery;
	public OrmliteProductUnitOfMeasureRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getProductUnitOfMeasureDao());
		
		rawQuery = "select " + 
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD  + " as [id], " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.PRODUCT_FIELD + " as [product_id], " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.UNIT_OF_MEASURE_FIELD + " as [uom_id], " +
				Constants.Tables.UnitOfMeasure.TABLE_NAME + "." + Constants.Tables.UnitOfMeasure.NAME_FIELD + " as [uom_name], " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.COUNT_IN_BASE_FIELD + " as [count_in_base], " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.BASE_FIELD + " as [base] " +
				"from " + Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + " as " + Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + " " +
				"left join " + Constants.Tables.UnitOfMeasure.TABLE_NAME + " as " + Constants.Tables.UnitOfMeasure.TABLE_NAME + " on " +
				Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.UNIT_OF_MEASURE_FIELD + " = " +
				Constants.Tables.UnitOfMeasure.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD;
	}
	
	@Override
	public ProductUnitOfMeasure getById(long id) throws Exception {
		GenericRawResults<ProductUnitOfMeasure> rawResults =
				dao.queryRaw(rawQuery + " where " + Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " = " + Long.toString(id) ,
				    new RawRowMapper<ProductUnitOfMeasure>() {
				            public ProductUnitOfMeasure mapRow(String[] columnNames,
				              String[] resultColumns) {	
				            	boolean isBase = false;
				            	if (resultColumns[5].equals("1"))
				            		isBase = true;
				            	return new ProductUnitOfMeasure(Long.parseLong(resultColumns[0]),
				            			Long.parseLong(resultColumns[1]),
				            			Long.parseLong(resultColumns[2]),
				            			resultColumns[3],
				                		isBase,
				                		Integer.parseInt(resultColumns[4]));
				        }
				    });
		
		ProductUnitOfMeasure productUnitOfMeasure = rawResults.getFirstResult();
		rawResults.close();
		return productUnitOfMeasure;
	}

	@Override
	public Iterable<ProductUnitOfMeasure> find() throws Exception {
		GenericRawResults<ProductUnitOfMeasure> rawResults =
				dao.queryRaw(rawQuery,
				    new RawRowMapper<ProductUnitOfMeasure>() {
				            public ProductUnitOfMeasure mapRow(String[] columnNames,
				              String[] resultColumns) {				
				            	boolean isBase = false;
				            	if (resultColumns[5].equals("1"))
				            		isBase = true;
				            	return new ProductUnitOfMeasure(Long.parseLong(resultColumns[0]),
				            			Long.parseLong(resultColumns[1]),
				            			Long.parseLong(resultColumns[2]),
				            			resultColumns[3],
				                		isBase,
				                		Integer.parseInt(resultColumns[4]));
				        }
				    });
		
		Iterable<ProductUnitOfMeasure> productUnitOfMeasureItems = rawResults.getResults();
		rawResults.close();
		return productUnitOfMeasureItems;
	}
	
	public Iterable<ProductUnitOfMeasure> findByProductId(long productId) throws Exception {
		GenericRawResults<ProductUnitOfMeasure> rawResults =
				dao.queryRaw(rawQuery + " where " + Constants.Tables.ProductUnitOfMeasure.TABLE_NAME + "." + Constants.Tables.ProductUnitOfMeasure.PRODUCT_FIELD + " = " + Long.toString(productId) ,
				    new RawRowMapper<ProductUnitOfMeasure>() {
				            public ProductUnitOfMeasure mapRow(String[] columnNames,
				              String[] resultColumns) {
				            	boolean isBase = false;
				            	if (resultColumns[5].equals("1"))
				            		isBase = true;
				            	return new ProductUnitOfMeasure(Long.parseLong(resultColumns[0]),
				            			Long.parseLong(resultColumns[1]),
				            			Long.parseLong(resultColumns[2]),
				            			resultColumns[3],
				            			isBase,
				            			Integer.parseInt(resultColumns[4]));
				        }
				    });
		
		Iterable<ProductUnitOfMeasure> productUnitOfMeasureItems = rawResults.getResults();
		rawResults.close();
		return productUnitOfMeasureItems;
	}
}
