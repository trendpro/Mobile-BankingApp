
package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for feature 1 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class StopACardRequestsMenuOptionActivity extends ParentActivity 
{
	//Variable declaration
	Button mStopACardButton = null;
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
    setContentView (R.layout.activity_stop_a_card_requests_menu_option);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    mStopACardButton = (Button)findViewById(R.id.btn_stop_a_card_stop_a_card_menu_option);
    mLogoutButton = (Button)findViewById(R.id.btn_logout_stop_card_menu_option);
    mCancelButton = (Button)findViewById(R.id.btn_cancel_stop_a_card_menu_option);
    
    //Add onClick() listener to mLogoutButton
    mLogoutButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
		}
	});//end of anonymous inner class
    
}//end of onCreate()
    
} // end class
