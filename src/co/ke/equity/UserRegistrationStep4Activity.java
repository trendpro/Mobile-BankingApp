package co.ke.equity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for feature 2 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class UserRegistrationStep4Activity extends ParentActivity 
{
	//Varables declaration
	String sucessfulRegustrationMsg=null;
	Button finishButton = null;
	

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
    setContentView (R.layout.activity_user_registration_step4);
    setTitleFromActivityLabel (R.id.title_text);
    
    sucessfulRegustrationMsg = (String)getString(R.string.finish_msg_user_reg);
    finishButton = (Button)findViewById(R.id.btn_finish_user_reg_step4);
    
    finishButton.setOnClickListener(new OnClickListener() 
    {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			toast(sucessfulRegustrationMsg);
		}
	});//end of onClickListener()
}
    
} // end class
