
package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for feature 5 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class RequestsMainMenuOptionActivity extends ParentActivity 
{
	//Variables declaration
	Button mStopACardButton = null;
	Button mStopAChequeButton = null;
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
    setContentView (R.layout.activity_requests_main_menu_option);
    setTitleFromActivityLabel (R.id.title_text);
    
    //find views by id
    mStopACardButton = (Button)findViewById(R.id.btn_stop_a_card_request);
    mStopAChequeButton = (Button)findViewById(R.id.btn_stop_a_cheque_request);
    mLogoutButton = (Button)findViewById(R.id.btn_logout_requests_main_menu_option);
    
    //Add onClick() listener to stop a card button
    mStopACardButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO code for stopping a card
			startActivity (new Intent(getApplicationContext(), StopACardRequestsMenuOptionActivity.class));
		}
	});//end of onClick()
    
    //Add onClick() listener to stop a cheque button
    mStopAChequeButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Code for stopping a cheque
			startActivity (new Intent(getApplicationContext(), StopAChequeRequestsMenuOptionActivity.class));
		}
	});//end of onClick()
    
    //Add onClick() listener to logout button
    mLogoutButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Code for logout button
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
			
		}
	});//end of onClick()
    
}//end of onCreate()
    
}//end class

