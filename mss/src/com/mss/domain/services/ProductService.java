package com.mss.domain.services;

import java.util.ArrayList;

import android.util.Log;

import com.mss.domain.models.Product;
import com.mss.domain.models.ProductUnitOfMeasure;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteProductRepository;
import com.mss.infrastructure.ormlite.OrmliteProductUnitOfMeasureRepository;

public class ProductService {
	private static final String TAG = ProductService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteProductRepository productRepo;
	private OrmliteProductUnitOfMeasureRepository productUoMRepo;
	public ProductService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		productRepo = new OrmliteProductRepository(this.databaseHelper);
		productUoMRepo = new OrmliteProductUnitOfMeasureRepository(this.databaseHelper);
	}
	
	public Product getById(long id) {
		try {
			return productRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}
	
	public Iterable<Product> getProducts(){		 
		try {
			return productRepo.find();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return new ArrayList<Product>();
		}
	}
	
	public ProductUnitOfMeasure getProductsUnitOfMeasure(long id){		 
		try {
			return productUoMRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}
	
	public Iterable<ProductUnitOfMeasure> getProductsUnitsOfMeasure(long productId){		 
		try {
			return productUoMRepo.findByProductId(productId);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return new ArrayList<ProductUnitOfMeasure>();
		}
	}
}
