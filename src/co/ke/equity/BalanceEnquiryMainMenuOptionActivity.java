package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for balance enquiry in the Eazzy 24/7 main menu.It enables one to view his
 * or her bank account balance
 */

public class BalanceEnquiryMainMenuOptionActivity extends ParentActivity 
{
	//Variable declarations
	Button mGetBalanceButton = null;
	Button mCancelButton = null;
	Button mLogoutButton = null;

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
    setContentView (R.layout.activity_balance_enquiry_main_menu_option);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    mGetBalanceButton = (Button)findViewById(R.id.btn_get_balance_balance_enquiry_mm_option);
    mCancelButton = (Button)findViewById(R.id.btn_cancel_balance_enquiry_mm_option);
    mLogoutButton = (Button)findViewById(R.id.btn_logout_balance_enquiry_main_menu_option);
    
    //Add onClick listener for get balance button
    mGetBalanceButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Code for retrieving bank accounts balance
			
		}
	});//end of anonymous inner class
    
    //Add onClick listener for cancel button
    mCancelButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO code for cancelling all edit fields
			
		}
	});//end of anonymous inner class
    
    //Add onClick listener for logout button
    mLogoutButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Saves current state and logs out a user
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
			
		}
	});//end of anonymous inner class
    
}//end of onCreate()
    
} // end class
