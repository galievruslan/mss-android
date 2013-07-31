package com.mss.application;

import com.mss.domain.models.Customer;

import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomersAdapter extends ArrayAdapter<Customer> {
  private final Context context;
  private final Customer[] values;

  public CustomersAdapter(Context context, Customer[] values) {
    super(context, R.layout.route_point_item_layout, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.customer_item_layout, parent, false);
    TextView nameView = (TextView) rowView.findViewById(R.id.label_name);
    TextView addressView = (TextView) rowView.findViewById(R.id.label_address);
    nameView.setText(String.valueOf(values[position].getName()));
    addressView.setText(String.valueOf(values[position].getAddress()));

    return rowView;
  }
} 
