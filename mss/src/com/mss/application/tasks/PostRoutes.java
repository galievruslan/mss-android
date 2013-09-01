package com.mss.application.tasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.infrastructure.data.IRepository;
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
					webServer.Post(url, ToJSON(route, IterableHelpers.toArray(RoutePoint.class, points)));
			result.getStatusCode();
		}
		
		return true;
	}
	
	private JSONObject ToJSON(Route route, RoutePoint points[]) throws JSONException {
		JSONObject routePointsAttributes = new JSONObject();		
		for (int i = 0; i < points.length; i++) {
			JSONObject jsonRoutePoint = new JSONObject();
			jsonRoutePoint.put("shipping_address_id", points[i].getShippingAddressId());
			jsonRoutePoint.put("status_id", points[i].getStatusId());
			routePointsAttributes.put(String.valueOf(i), jsonRoutePoint);
		}
		
		JSONObject routeAttributes = new JSONObject();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		routeAttributes.put("date", dateFormat.format(route.getDate()));
		routeAttributes.put("route_points_attributes", routePointsAttributes);
		
		JSONObject routeObject = new JSONObject();		
		routeObject.put("route", routeAttributes);
				
		return routeObject;		
	}
}
