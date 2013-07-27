package com.mss.application.services;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public class AuthenticationService {
	private final String ACCOUNT_TYPE = "MSS_ACCOUNT";
	
	private Context context;
	public AuthenticationService(Context context) {
		this.context = context;
	}
	
	public boolean isAuthenticated(){
		boolean result = false;
		
		AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length > 1) {
        	for (Account account : accounts) {
        		accountManager.removeAccount(account, null, null);
			}
        } else if (accounts.length == 1) {
        	result = true;
        }
        
        return result;        		
	}
	
	public void setCredentials(Credentials credentials){
		Account account = new Account(credentials.getLogin(), ACCOUNT_TYPE);
    	AccountManager accountManager = AccountManager.get(context);            	
    	accountManager.addAccountExplicitly(account, credentials.getPassword(), null);	
	}
	
	public Credentials getCredentials() throws CredentialsNotFoundException{
		if (isAuthenticated()) {
			AccountManager accountManager = AccountManager.get(context);    
			Account account = accountManager.getAccountsByType(ACCOUNT_TYPE)[0];
    	
			return new Credentials(account.name, accountManager.getPassword(account));
		}
		
		throw new CredentialsNotFoundException();
	}	
	
	class CredentialsNotFoundException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 9124284645554858780L;
		
	}
}