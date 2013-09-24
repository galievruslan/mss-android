package com.mss.domain.models;

public class Constants {
	public static class Tables{
		public static class Entity{
			public static final String ID_FIELD = "id";
		}
		
		public static class Category {
			public static final String TABLE_NAME = "category";
			public static final String NAME_FIELD = "name";
			public static final String PARENT_CATEGORY_FIELD = "parent_id";
		}
		
		public static class Customer {
			public static final String TABLE_NAME = "customer";
			public static final String NAME_FIELD = "name";
			public static final String ADDRESS_FIELD = "address";
		}
		
		public static class ShippingAddress {
			public static final String TABLE_NAME = "shipping_address";
			public static final String NAME_FIELD = "name";
			public static final String ADDRESS_FIELD = "address";
			public static final String CUSTOMER_FIELD = "customer_id";
		}
		
		public static class Product {
			public static final String TABLE_NAME = "product";
			public static final String NAME_FIELD = "name";
			public static final String CATEGORY_FIELD = "category_id";
		}
		
		public static class UnitOfMeasure {
			public static final String TABLE_NAME = "unit_of_measure";
			public static final String NAME_FIELD = "name";
		}
		
		public static class PriceList {
			public static final String TABLE_NAME = "price_list";
			public static final String NAME_FIELD = "name";
		}
		
		public static class Warehouse {
			public static final String TABLE_NAME = "warehouse";
			public static final String NAME_FIELD = "name";
			public static final String ADDRESS_FIELD = "address";
			public static final String DEFAULT_FIELD = "default";
		}
		
		public static class Status {
			public static final String TABLE_NAME = "status";
			public static final String NAME_FIELD = "name";
		}
		
		public static class Order {
			public static final String TABLE_NAME = "order";
			public static final String ROUTE_POINT_FIELD = "route_point_id";
			public static final String ORDER_DATE_FIELD = "order_date";			
			public static final String SHIPPING_DATE_FIELD = "shipping_date";
			public static final String CUSTOMER_FIELD = "customer_id";
			public static final String CUSTOMER_NAME_FIELD = "customer_name";
			public static final String SHIPPING_ADDRESS_FIELD = "shipping_address_id";
			public static final String SHIPPING_ADDRESS_NAME_FIELD = "shipping_address_name";
			public static final String SHIPPING_ADDRESS_VALUE_FIELD = "shipping_address_value";
			public static final String PRICE_LIST_FIELD = "price_list_id";
			public static final String PRICE_LIST_NAME_FIELD = "price_list_name";
			public static final String WAREHOUSE_FIELD = "warehouse_id";
			public static final String WAREHOUSE_NAME_FIELD = "warehouse_name";
			public static final String AMOUNT_FIELD = "amount";
			public static final String NOTE_FIELD = "note";
			public static final String UID_FIELD = "uid";
			public static final String SYNCHRONIZED_FIELD = "synchronized";
		}
		
		public static class OrderItem {
			public static final String TABLE_NAME = "order_item";
			public static final String ORDER_FIELD = "order_id";
			public static final String PRODUCT_FIELD = "product_id";
			public static final String PRODUCT_NAME_FIELD = "product_name";
			public static final String PRODUCT_UNIT_OF_MEASURE_FIELD = "product_unit_of_measure_id";
			public static final String UNIT_OF_MEASURE_FIELD = "unit_of_measure_id";
			public static final String UNIT_OF_MEASURE_NAME_FIELD = "unit_of_measure_name";
			public static final String UNIT_OF_MEASURE_COUNT_FIELD = "unit_of_measure_count";
			public static final String COUNT_FIELD = "count";
			public static final String PRICE_FIELD = "price";
			public static final String AMOUNT_FIELD = "amount";
		}
		
		public static class PriceListLine {
			public static final String TABLE_NAME = "price_list_line";
			public static final String PRICE_LIST_FIELD = "price_list_id";
			public static final String PRODUCT_FIELD = "product_id";
			public static final String PRICE_FIELD = "price";
		}
		
		public static class ProductUnitOfMeasure {
			public static final String TABLE_NAME = "product_unit_of_measure";			
			public static final String PRODUCT_FIELD = "product_id";
			public static final String UNIT_OF_MEASURE_FIELD = "unit_of_measure_id";
			public static final String BASE_FIELD = "base";
			public static final String COUNT_IN_BASE_FIELD = "count_in_base";
		}
		
		public static class Route {
			public static final String TABLE_NAME = "route";			
			public static final String DATE_FIELD = "date";
		}
		
		public static class RoutePoint {
			public static final String TABLE_NAME = "route_point";			
			public static final String ROUTE_FIELD = "route_id";
			public static final String SHIPPING_ADDRESS_FIELD = "shipping_address_id";
			public static final String SHIPPING_ADDRESS_NAME_FIELD = "shipping_address_name";
			public static final String SHIPPING_ADDRESS_VALUE_FIELD = "shipping_address_value";
			public static final String STATUS_FIELD = "status_id";
			public static final String STATUS_NAME_FIELD = "status_name";
			public static final String SYNCHRONIZED_FIELD = "synchronized";
		}
		
		public static class RouteTemplate {
			public static final String TABLE_NAME = "route_template";			
			public static final String DAY_OF_WEEK_FIELD = "day_of_week";
		}
		
		public static class RoutePointTemplate {
			public static final String TABLE_NAME = "route_point_template";			
			public static final String ROUTE_TEMPLATE_FIELD = "route_template_id";
			public static final String SHIPPING_ADDRESS_FIELD = "shipping_address_id";
		}
		
		public static class Preferences {
			public static final String TABLE_NAME = "preferences";
			public static final String DEFAULT_STATUS_FIELD = "default_status_id";
			public static final String DEFAULT_ATTENDED_STATUS_FIELD = "default_attended_status_id";
			public static final String DEFAULT_PRICE_LIST = "default_price_list_id";
		}
	}
}
