
package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for feature 3 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class UserRegistrationNormalActivity extends ParentActivity 
{
	//Variable declaration
	Button linkToLoginScreen = null;

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
    setContentView (R.layout.activity_normal_user_registration);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    linkToLoginScreen = (Button)findViewById(R.id.btnLinkToLoginScreen);
    
    //Add click listener
    linkToLoginScreen.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Take user to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
		}
	});//end of method
}
    
} // end class
