
package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for settings menu option from Eazzy 24/7 main menu
 *
 */

public class SettingsMainMenuOptionsActivity extends ParentActivity 
{
	//Variables declaration
	Button mChangeLanguageButton = null;
	Button mChangePasswordButton = null;
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
    setContentView (R.layout.activity_settings_main_menu_option);
    setTitleFromActivityLabel (R.id.title_text);
    
    //find views by id
    mChangeLanguageButton = (Button)findViewById(R.id.btn_change_language_settings_main_menu);
    mChangePasswordButton = (Button)findViewById(R.id.btn_change_password_settings_main_menu);
    mLogoutButton = (Button)findViewById(R.id.btn_logout_settings_main_menu_option);
    
    //Add onClick() Listener for change language button
    mChangeLanguageButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Takes you to change language preferences screen
			startActivity (new Intent(getApplicationContext(), ChangeLanguagePreferenceActivity.class));
		}
	});//end of anonymous inner class
    
    //add onClick() Listener for change password button
    mChangePasswordButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Takes you to change password screen
			startActivity (new Intent(getApplicationContext(), ChangePasswordSettingsMenuActivity.class));
		}
	});//end of anonymous inner class
    
    //Add onClick() Listener for mLogout button
    mLogoutButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Logs out a user and takes you to login screen
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));

			
		}
	});//end of anonymous inner class
}
    
} // end class


