package com.mss.infrastructure.ormlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mss.domain.models.*;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "mss.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 3;

	public DatabaseHelper(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db, final ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Category.class);			
			TableUtils.createTable(connectionSource, Customer.class);
			TableUtils.createTable(connectionSource, ShippingAddress.class);
			TableUtils.createTable(connectionSource, UnitOfMeasure.class);
			TableUtils.createTable(connectionSource, Status.class);
			TableUtils.createTable(connectionSource, Warehouse.class);			
			TableUtils.createTable(connectionSource, Product.class);
			TableUtils.createTable(connectionSource, ProductRemainder.class);
			TableUtils.createTable(connectionSource, ProductUnitOfMeasure.class);
			TableUtils.createTable(connectionSource, PriceList.class);
			TableUtils.createTable(connectionSource, PriceListLine.class);
			TableUtils.createTable(connectionSource, RouteTemplate.class);
			TableUtils.createTable(connectionSource, RoutePointTemplate.class);
			TableUtils.createTable(connectionSource, Route.class);
			TableUtils.createTable(connectionSource, RoutePoint.class);
			TableUtils.createTable(connectionSource, RoutePointPhoto.class);
			TableUtils.createTable(connectionSource, Order.class);
			TableUtils.createTable(connectionSource, OrderItem.class);
			TableUtils.createTable(connectionSource, Preferences.class);
		} catch (java.sql.SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	* This is called when your application is upgraded and it has a higher version number. This allows you to adjust the various data to
	* match the new version number.
	*/
	@Override
	public void onUpgrade(final SQLiteDatabase db, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");		
			TableUtils.dropTable(connectionSource, Category.class, true);
			TableUtils.dropTable(connectionSource, Customer.class, true);
			TableUtils.dropTable(connectionSource, ShippingAddress.class, true);
			TableUtils.dropTable(connectionSource, UnitOfMeasure.class, true);
			TableUtils.dropTable(connectionSource, Status.class, true);
			TableUtils.dropTable(connectionSource, Warehouse.class, true);			
			TableUtils.dropTable(connectionSource, Product.class, true);
			TableUtils.dropTable(connectionSource, ProductRemainder.class, true);
			TableUtils.dropTable(connectionSource, ProductUnitOfMeasure.class, true);
			TableUtils.dropTable(connectionSource, PriceList.class, true);
			TableUtils.dropTable(connectionSource, PriceListLine.class, true);
			TableUtils.dropTable(connectionSource, RouteTemplate.class, true);
			TableUtils.dropTable(connectionSource, RoutePointTemplate.class, true);
			TableUtils.dropTable(connectionSource, Route.class, true);
			TableUtils.dropTable(connectionSource, RoutePoint.class, true);
			TableUtils.dropTable(connectionSource, RoutePointPhoto.class, true);
			TableUtils.dropTable(connectionSource, Order.class, true);
			TableUtils.dropTable(connectionSource, OrderItem.class, true);
			TableUtils.dropTable(connectionSource, Preferences.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (java.sql.SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	private Dao<Customer, Integer> customerDao = null;	
	public Dao<Customer, Integer> getCustomerDao() throws SQLException, java.sql.SQLException {
		if (this.customerDao == null) {
			this.customerDao = getDao(Customer.class);
		}
		return this.customerDao;
	}
	
	private Dao<Category, Integer> categoryDao = null;	
	public Dao<Category, Integer> getCategoryDao() throws SQLException, java.sql.SQLException {
		if (this.categoryDao == null) {
			this.categoryDao = getDao(Category.class);
		}
		return this.categoryDao;
	}
	
	private Dao<Order, Integer> orderDao = null;	
	public Dao<Order, Integer> getOrderDao() throws SQLException, java.sql.SQLException {
		if (this.orderDao == null) {
			this.orderDao = getDao(Order.class);
		}
		return this.orderDao;
	}
	
	private Dao<OrderItem, Integer> orderItemDao = null;	
	public Dao<OrderItem, Integer> getOrderItemDao() throws SQLException, java.sql.SQLException {
		if (this.orderItemDao == null) {
			this.orderItemDao = getDao(OrderItem.class);
		}
		return this.orderItemDao;
	}
	
	private Dao<PriceList, Integer> priceListDao = null;	
	public Dao<PriceList, Integer> getPriceListDao() throws SQLException, java.sql.SQLException {
		if (this.priceListDao == null) {
			this.priceListDao = getDao(PriceList.class);
		}
		return this.priceListDao;
	}
	
	private Dao<PriceListLine, Integer> priceListLineDao = null;	
	public Dao<PriceListLine, Integer> getPriceListLineDao() throws SQLException, java.sql.SQLException {
		if (this.priceListLineDao == null) {
			this.priceListLineDao = getDao(PriceListLine.class);
		}
		return this.priceListLineDao;
	}
	
	private Dao<Product, Integer> productDao = null;	
	public Dao<Product, Integer> getProductDao() throws SQLException, java.sql.SQLException {
		if (this.productDao == null) {
			this.productDao = getDao(Product.class);
		}
		return this.productDao;
	}
	
	private Dao<UnitOfMeasure, Integer> unitOfMeasureDao = null;	
	public Dao<UnitOfMeasure, Integer> getUnitOfMeasureDao() throws SQLException, java.sql.SQLException {
		if (this.unitOfMeasureDao == null) {
			this.unitOfMeasureDao = getDao(UnitOfMeasure.class);
		}
		return this.unitOfMeasureDao;
	}
	
	private Dao<ProductUnitOfMeasure, Integer> productUnitOfMeasureDao = null;	
	public Dao<ProductUnitOfMeasure, Integer> getProductUnitOfMeasureDao() throws SQLException, java.sql.SQLException {
		if (this.productUnitOfMeasureDao == null) {
			this.productUnitOfMeasureDao = getDao(ProductUnitOfMeasure.class);
		}
		return this.productUnitOfMeasureDao;
	}
	
	private Dao<Route, Integer> routeDao = null;	
	public Dao<Route, Integer> getRouteDao() throws SQLException, java.sql.SQLException {
		if (this.routeDao == null) {
			this.routeDao = getDao(Route.class);
		}
		return this.routeDao;
	}
	
	private Dao<RoutePoint, Integer> routePointDao = null;	
	public Dao<RoutePoint, Integer> getRoutePointDao() throws SQLException, java.sql.SQLException {
		if (this.routePointDao == null) {
			this.routePointDao = getDao(RoutePoint.class);
		}
		return this.routePointDao;
	}
	
	private Dao<RoutePointPhoto, Integer> routePointPhotoDao = null;	
	public Dao<RoutePointPhoto, Integer> getRoutePointPhotoDao() throws SQLException, java.sql.SQLException {
		if (this.routePointPhotoDao == null) {
			this.routePointPhotoDao = getDao(RoutePointPhoto.class);
		}
		return this.routePointPhotoDao;
	}
	
	private Dao<RouteTemplate, Integer> routeTemplateDao = null;	
	public Dao<RouteTemplate, Integer> getRouteTemplateDao() throws SQLException, java.sql.SQLException {
		if (this.routeTemplateDao == null) {
			this.routeTemplateDao = getDao(RouteTemplate.class);
		}
		return this.routeTemplateDao;
	}
	
	private Dao<RoutePointTemplate, Integer> routePointTemplateDao = null;	
	public Dao<RoutePointTemplate, Integer> getRoutePointTemplateDao() throws SQLException, java.sql.SQLException {
		if (this.routePointTemplateDao == null) {
			this.routePointTemplateDao = getDao(RoutePointTemplate.class);
		}
		return this.routePointTemplateDao;
	}
	
	private Dao<ShippingAddress, Integer> shippingAddressDao = null;	
	public Dao<ShippingAddress, Integer> getShippingAddressDao() throws SQLException, java.sql.SQLException {
		if (this.shippingAddressDao == null) {
			this.shippingAddressDao = getDao(ShippingAddress.class);
		}
		return this.shippingAddressDao;
	}
	
	private Dao<Status, Integer> statusDao = null;	
	public Dao<Status, Integer> getStatusDao() throws SQLException, java.sql.SQLException {
		if (this.statusDao == null) {
			this.statusDao = getDao(Status.class);
		}
		return this.statusDao;
	}
	
	private Dao<Warehouse, Integer> warehouseDao = null;	
	public Dao<Warehouse, Integer> getWarehouseDao() throws SQLException, java.sql.SQLException {
		if (this.warehouseDao == null) {
			this.warehouseDao = getDao(Warehouse.class);
		}
		return this.warehouseDao;
	}
	
	private Dao<ProductRemainder, Integer> productRemainderDao = null;	
	public Dao<ProductRemainder, Integer> getProductRemainderDao() throws SQLException, java.sql.SQLException {
		if (this.productRemainderDao == null) {
			this.productRemainderDao = getDao(ProductRemainder.class);
		}
		return this.productRemainderDao;
	}
	
	private Dao<Preferences, Integer> preferencesDao = null;	
	public Dao<Preferences, Integer> getPreferencesDao() throws SQLException, java.sql.SQLException {
		if (this.preferencesDao == null) {
			this.preferencesDao = getDao(Preferences.class);
		}
		return this.preferencesDao;
	}


	/**
	* Close the database connections and clear any cached DAOs.
	*/
	@Override
	public void close() {
		super.close();
		this.customerDao = null;
		this.categoryDao = null;
		this.orderDao = null;
		this.orderItemDao = null;
		this.priceListDao = null;
		this.priceListLineDao = null;
		this.productDao = null;
		this.unitOfMeasureDao = null;
		this.productUnitOfMeasureDao = null;
		this.routeDao = null;
		this.routePointDao = null;
		this.routePointDao = null;
		this.routeTemplateDao = null;
		this.routePointTemplateDao = null;
		this.shippingAddressDao = null;
		this.statusDao = null;
		this.warehouseDao = null;
		this.productRemainderDao = null;
		this.preferencesDao = null;
	}
	
	public void clear() throws java.sql.SQLException {
		Log.i(DatabaseHelper.class.getName(), "clear");		
		TableUtils.dropTable(connectionSource, Category.class, true);
		TableUtils.dropTable(connectionSource, Customer.class, true);
		TableUtils.dropTable(connectionSource, ShippingAddress.class, true);
		TableUtils.dropTable(connectionSource, UnitOfMeasure.class, true);
		TableUtils.dropTable(connectionSource, Status.class, true);
		TableUtils.dropTable(connectionSource, Warehouse.class, true);		
		TableUtils.dropTable(connectionSource, Product.class, true);
		TableUtils.dropTable(connectionSource, ProductRemainder.class, true);
		TableUtils.dropTable(connectionSource, ProductUnitOfMeasure.class, true);
		TableUtils.dropTable(connectionSource, PriceList.class, true);
		TableUtils.dropTable(connectionSource, PriceListLine.class, true);
		TableUtils.dropTable(connectionSource, RouteTemplate.class, true);
		TableUtils.dropTable(connectionSource, RoutePointTemplate.class, true);
		TableUtils.dropTable(connectionSource, Route.class, true);
		TableUtils.dropTable(connectionSource, RoutePoint.class, true);
		TableUtils.dropTable(connectionSource, RoutePointPhoto.class, true);
		TableUtils.dropTable(connectionSource, Order.class, true);
		TableUtils.dropTable(connectionSource, OrderItem.class, true);
		TableUtils.dropTable(connectionSource, Preferences.class, true);
		
		TableUtils.createTable(connectionSource, Category.class);
		TableUtils.createTable(connectionSource, Customer.class);
		TableUtils.createTable(connectionSource, ShippingAddress.class);
		TableUtils.createTable(connectionSource, UnitOfMeasure.class);
		TableUtils.createTable(connectionSource, Status.class);
		TableUtils.createTable(connectionSource, Warehouse.class);		
		TableUtils.createTable(connectionSource, Product.class);
		TableUtils.createTable(connectionSource, ProductRemainder.class);
		TableUtils.createTable(connectionSource, ProductUnitOfMeasure.class);
		TableUtils.createTable(connectionSource, PriceList.class);
		TableUtils.createTable(connectionSource, PriceListLine.class);
		TableUtils.createTable(connectionSource, RouteTemplate.class);
		TableUtils.createTable(connectionSource, RoutePointTemplate.class);
		TableUtils.createTable(connectionSource, Route.class);
		TableUtils.createTable(connectionSource, RoutePoint.class);
		TableUtils.createTable(connectionSource, RoutePointPhoto.class);
		TableUtils.createTable(connectionSource, Order.class);
		TableUtils.createTable(connectionSource, OrderItem.class);
		TableUtils.createTable(connectionSource, Preferences.class);
	}
}