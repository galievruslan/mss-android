package com.mss.application.tasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mss.domain.models.Order;
import com.mss.domain.models.OrderItem;
import com.mss.infrastructure.ormlite.OrmliteOrderItemRepository;
import com.mss.infrastructure.ormlite.OrmliteOrderRepository;
import com.mss.infrastructure.web.WebServer;
import com.mss.infrastructure.web.WebServer.PostResult;
import com.mss.utils.IterableHelpers;

public class PostOrders extends PostTask<com.mss.domain.models.Order> {

	private OrmliteOrderRepository orderRepo;
	private OrmliteOrderItemRepository orderItemRepo;
	public PostOrders(WebServer webServer, String url,
			OrmliteOrderRepository orderRepo,
			OrmliteOrderItemRepository orderItemRepo) {
		super(webServer, url, orderRepo);
		this.orderRepo = orderRepo;
		this.orderItemRepo = orderItemRepo;
	}

	@Override
	protected Boolean doAttemptInBackground(Void... arg0) throws Throwable {
		Iterable<Order> orders = orderRepo.findNotSynchronized();
		for (Order order : orders) {
			Iterable<OrderItem> items = orderItemRepo.findByOrderId(order.getId());			
			PostResult result = 
					webServer.Post(url, ToPostParams(order, IterableHelpers.toArray(OrderItem.class, items)));
			result.getStatusCode();
			
			Pattern pattern = Pattern.compile("\"code\":100|\"code\":101");
			Matcher matcher = pattern.matcher(result.getContent());
			if (matcher.find()) {
				order.setIsSynchronized(true);
				orderRepo.save(order);
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	private List<NameValuePair> ToPostParams(Order order, OrderItem items[]) {		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nameValuePairs.add(new BasicNameValuePair("order[date]", dateFormat.format(order.getOrderDate())));
		nameValuePairs.add(new BasicNameValuePair("order[shipping_date]", dateFormat.format(order.getShippingDate())));
		nameValuePairs.add(new BasicNameValuePair("order[shipping_address_id]", String.valueOf(order.getShippingAddressId())));
		nameValuePairs.add(new BasicNameValuePair("order[guid]", String.valueOf(order.getUid())));
		nameValuePairs.add(new BasicNameValuePair("order[warehouse_id]", String.valueOf(order.getWarehouseId())));
		nameValuePairs.add(new BasicNameValuePair("order[price_list_id]", String.valueOf(order.getPriceListId())));
		nameValuePairs.add(new BasicNameValuePair("order[comment]", order.getNote()));
				
		for (int i = 0; i < items.length; i++) {
			nameValuePairs.add(
					new BasicNameValuePair(
							"order[order_items_attributes][" + String.valueOf(i) + "][product_id]" , 
							String.valueOf(items[i].getProductId())));			
			nameValuePairs.add(
					new BasicNameValuePair(
							"order[order_items_attributes][" + String.valueOf(i) + "][unit_of_measure_id]" , 
							String.valueOf(items[i].getUnitOfMeasureId())));
			nameValuePairs.add(
					new BasicNameValuePair(
							"order[order_items_attributes][" + String.valueOf(i) + "][quantity]" , 
							String.valueOf(items[i].getCount())));
		}
				
		return nameValuePairs;		
	}
}