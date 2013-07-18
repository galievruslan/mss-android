package com.mss.application;

import com.mss.android.domain.models.RoutePoint;

import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RoutePointsAdapter extends ArrayAdapter<RoutePoint> {
  private final Context context;
  private final RoutePoint[] values;

  public RoutePointsAdapter(Context context, RoutePoint[] values) {
    super(context, R.layout.route_point_item_layout, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.route_point_item_layout, parent, false);
    TextView nameView = (TextView) rowView.findViewById(R.id.label_name);
    TextView addressView = (TextView) rowView.findViewById(R.id.label_address);
    nameView.setText(String.valueOf(values[position].getId()));
    addressView.setText(String.valueOf(values[position].getShippingAddressId()));

    return rowView;
  }
} 
