
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

public class MiniStatementMainMenuOption extends ParentActivity 
{
	//Variable declaration
	Button mLoginButton = null;
	Button mGetMinistatementButton = null;	

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
    setContentView (R.layout.activity_mini_statement_main_menu_option);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    mLoginButton = (Button)findViewById(R.id.btn_logout_mini_statement_main_menu_option);
    mGetMinistatementButton = (Button)findViewById(R.id.btn_get_mini_statement_mm_option);
    
    
    //Add onClick() Listener for mLogout button
    mLoginButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Save any instance state and logout user
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
			
		}
	});//end of anonymous inner class
    
    //Add onClick() Listener for mGetMiniStatement button
    mGetMinistatementButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Code for generating and displaying mini bank statement
			
		}
	});
    
}//end of onCreate()
    
} // end class
