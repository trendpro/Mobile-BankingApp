package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for adding a bill in the bill payment module
 *
 */

public class AddABillBillPaymentMenuOptionActivity extends ParentActivity 
{
	//Variables declaration
	Button mCancelButton = null;
	Button mAddABillButton = null;
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
    setContentView (R.layout.activity_add_a_bill_bill_payment_menu);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    mCancelButton = (Button)findViewById(R.id.btn_cancel_add_bill_menu_option);
    mAddABillButton = (Button)findViewById(R.id.btn_add_bill_add_bill_menu);
    mLogoutButton = (Button)findViewById(R.id.btn_logout_add_bill_menu_option);
    
    
    //Add onClick() Listener to logout button
    mLogoutButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Save state and logout user
			
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
			
		}
	});//end of anonymous inner class
    
}//end of onCreate()

    
}// end class

