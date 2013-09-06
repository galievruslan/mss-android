package com.mss.application.tasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.infrastructure.ormlite.OrmliteRoutePointRepository;
import com.mss.infrastructure.ormlite.OrmliteRouteRepository;
import com.mss.infrastructure.web.WebServer;
import com.mss.infrastructure.web.WebServer.PostResult;
import com.mss.utils.IterableHelpers;

public class PostRoutes extends PostTask<com.mss.domain.models.Route> {

	private OrmliteRouteRepository routeRepo;
	private OrmliteRoutePointRepository routePointRepo;
	public PostRoutes(WebServer webServer, String url,
			OrmliteRouteRepository routeRepo,
			OrmliteRoutePointRepository routePointRepo) {
		super(webServer, url, routeRepo);
		this.routeRepo = routeRepo;
		this.routePointRepo = routePointRepo;
	}

	@Override
	protected Boolean doAttemptInBackground(Void... arg0) throws Throwable {
		Iterable<Route> routes = routeRepo.findNotSynchronized();
		for (Route route : routes) {
			Iterable<RoutePoint> points = routePointRepo.findByRouteId(route.getId());			
			PostResult result = 
					webServer.Post(url, ToPostParams(route, IterableHelpers.toArray(RoutePoint.class, points)));
			result.getStatusCode();
			
			Pattern pattern = Pattern.compile("\"code\":100|\"code\":101");
			Matcher matcher = pattern.matcher(result.getContent());
			if (matcher.find()) {
				for (RoutePoint routePoint : points) {
					routePoint.setIsSynchronized(true);
					routePointRepo.save(routePoint);
				}
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	private List<NameValuePair> ToPostParams(Route route, RoutePoint points[]) {		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nameValuePairs.add(new BasicNameValuePair("route[date]", dateFormat.format(route.getDate())));
				
		for (int i = 0; i < points.length; i++) {
			nameValuePairs.add(
					new BasicNameValuePair(
							"route[route_points_attributes][" + String.valueOf(i) + "][shipping_address_id]" , 
							String.valueOf(points[i].getShippingAddressId())));			
			nameValuePairs.add(
					new BasicNameValuePair(
							"route[route_points_attributes][" + String.valueOf(i) + "][status_id]" , 
							String.valueOf(points[i].getStatusId())));
		}
				
		return nameValuePairs;		
	}
}
