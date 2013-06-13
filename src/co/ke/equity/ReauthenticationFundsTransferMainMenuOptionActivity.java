
package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for re-authentication before one navigates to funds transfer section
 *
 */

public class ReauthenticationFundsTransferMainMenuOptionActivity extends ParentActivity 
{
	//Variable declaration
	Button mLogoutButton = null;
	Button mCancelButton = null;
	Button mReauthenticateButton = null;
	
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
    setContentView (R.layout.activity_reauthentication_funds_transfer_main_menu_option);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    mCancelButton = (Button)findViewById(R.id.btn_Cancel_funds_transfer);
    mLogoutButton = (Button)findViewById(R.id.btn_logout_funds_transfer_main_menu_option);
    mReauthenticateButton = (Button)findViewById(R.id.btn_login_funds_transfer);
    
    //Add onClick() listener for mCancelButton
    mCancelButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Cancels Reauthentication
			
		}
	});//end of anonymous inner class
    
    //Add onClick() Listener for mLogoutButton
    mLogoutButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Logs out a user and takes you back to login screen
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
			
		}
	});//end of anonymous inner class
    
    //Add onClick() Listener for mReauth button
    mReauthenticateButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Reauths a user's session before navigating to funds transfer section
			
			
			//on successful re authentication
			startActivity (new Intent(getApplicationContext(), FundsTransferMainMenuOptionActivity.class));
		}
	});//end of anonymous inner class
    
}//end of onClick()
    
} // end class
