package mss.android.infrastructure.ormlite;

import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.j256.ormlite.support.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.table.*;

import com.j256.ormlite.jdbc.*;
import com.mss.android.domain.models.*;


public class OrmLiteTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		String url = "jdbc:sqlite::memory:";
		
		ConnectionSource connectionSource = null;
		try {
			// create our data-source for the database
			connectionSource = new JdbcConnectionSource(url);
		
			setupDatabase(connectionSource);
			Dao<Customer, Integer> customerDao = DaoManager.createDao(connectionSource, Customer.class); 
			Dao<ShippingAddress, Integer> shippingAddressDao = DaoManager.createDao(connectionSource, ShippingAddress.class);
			
			for (int i = 0; i < 100; i++) {
				Customer customer = 
						new Customer(i, String.format("Customer #%s", i));
			
				customerDao.create(customer);
				for (int j = 0; j < 10; j ++) {
					int id = i*10+j;
					ShippingAddress shippingAddress = 
							new ShippingAddress(id, 
									String.format("Shipping address name #%s", id), 
									String.format("Shipping address address #%s", id),
									customer);
					shippingAddressDao.create(shippingAddress);
					customer.AddAddress(shippingAddress);
				}
			}	
			
			Iterator<Customer> iter = customerDao.iterator();
			while (iter.hasNext()) {
				Customer customer = iter.next();
				System.out.println(customer.getName());
			}
			
		} finally {
			// destroy the data source which should close underlying connections
			if (connectionSource != null) {
				connectionSource.close();
			}
		}
	}
	
	/**
 	* Setup our database and DAOs
	*/
	private void setupDatabase(ConnectionSource connectionSource) throws Exception {	

		// if you need to create the table
		TableUtils.createTable(connectionSource, Customer.class);
		TableUtils.createTable(connectionSource, ShippingAddress.class);
	}
}

