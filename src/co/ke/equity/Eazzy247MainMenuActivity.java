
package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for feature 2 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class Eazzy247MainMenuActivity extends ParentActivity 
{
	//Variable declaration
	Button airtimeTopupButton=null;
	Button balanceEnquiryButton=null;
	Button miniStatementButton=null;
	Button fundsTransferButton=null;
	Button billPaymentButton=null;
	Button requestButton=null;
	Button settingsButton=null;
	Button logoutButton=null;
	
/**
 * onCreate
 *
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 * @param savedInstanceState Bundle
 */

protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_eazzy247_mainmenu);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    airtimeTopupButton = (Button)findViewById(R.id.btn_airtime_menu);
    balanceEnquiryButton = (Button)findViewById(R.id.btn_balance_enquiry);
    miniStatementButton = (Button)findViewById(R.id.btn_ministatement);
    fundsTransferButton = (Button)findViewById(R.id.btn_funds_transafer);
    billPaymentButton = (Button)findViewById(R.id.btn_bill_payment);
    requestButton = (Button)findViewById(R.id.btn_requests);
    settingsButton = (Button)findViewById(R.id.btn_settings);
    logoutButton = (Button)findViewById(R.id.btn_logout_eazyy247_main_menu);
    
    //Add onClick Listeners to Logout button
    logoutButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Logout user and go to login screen
			
			//logout logic
			
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
			
		}
	});//end of method
    
    //Add onClick() Listener to buyairtimeButton
    airtimeTopupButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Navigate to airtime topup screen
			startActivity (new Intent(getApplicationContext(), AirtimeTopUpMainMenuOptionActivity.class));
		}
	});//end of anonymous inner class
    
    //Add onClick() Listener for getBalance button
    balanceEnquiryButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Navigate to get account balance screen
			startActivity (new Intent(getApplicationContext(), BalanceEnquiryMainMenuOptionActivity.class));
		}
	});//end of anonymous inner class
    
    //Add onClick() listener for mini statement button
    miniStatementButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Navigates to get mini statement button
			startActivity (new Intent(getApplicationContext(), MiniStatementMainMenuOption.class));
			
		}
	});//end of anonymous inner class
    
    //Add onClick() Listener for fundsTransfer Button
    fundsTransferButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Navigates to funds transfer section
			startActivity (new Intent(getApplicationContext(), ReauthenticationFundsTransferMainMenuOptionActivity.class));
			
		}
	});//end of anonymous inner class
    
    //Add onClick() for billPayment button
    billPaymentButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Navigates to bill payment section
			startActivity (new Intent(getApplicationContext(), PayBillMainMenuActivity.class));
		}
	});//end of anonymous inner class
    
    //Add onClick() listener for requests button
    requestButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Navigates to Requets and alerts button
			startActivity (new Intent(getApplicationContext(), RequestsMainMenuOptionActivity.class));
		}
	});//end of onClick()
    
    //Add onClick() listener for settings button
    settingsButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity (new Intent(getApplicationContext(), SettingsMainMenuOptionsActivity.class));
		}
	});//end of anonymous inner class
    
}//end of onCreate()
    
} // end class
