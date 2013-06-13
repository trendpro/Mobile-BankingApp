package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for changing a user's password in Eazzy 24/7 Settings menu.
 * It enables one to change eazzy 24/7  password and provides a way to get back to the home activity.
 *
 */

public class ChangePasswordSettingsMenuActivity extends ParentActivity 
{
	//Variables declaration
	Button mLogoutButton = null;
	Button mCancelButton = null;
	Button mChangePasswordButton = null;
	

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
    setContentView (R.layout.activity_change_password_settings_menu);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    mLogoutButton = (Button)findViewById(R.id.btn_logout_change_password_settings_menu);
    mCancelButton = (Button)findViewById(R.id.btn_cancel_change_password_settings_menu);
    mChangePasswordButton = (Button)findViewById(R.id.btn_change_password_change_password_settings_menu);
    
    //Add onClick listener to mLogout button
    mLogoutButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Logouts a user and navigates to login screen
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
			
		}
	});//end of anonymous inner class
    
    //add onClick() listener to mCancel button
    mCancelButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Resets all fields
			
		}
	});//end of anonymous inner
    
    //add onClick() listener to mChangePassword button
    mChangePasswordButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Changes a users password
			
		}
	});//end of anonymous inner class
    
}//end of onCreate()
    
}// end class
