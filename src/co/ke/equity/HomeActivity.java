
package co.ke.equity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * This is a simple activity that demonstrates the dashboard user interface pattern.
 *
 */

public class HomeActivity extends ParentActivity 
{
	//Instance variables
	private  String username = MainAppLoginActivity.USERNAME;

/**
 * onCreate - called when the activity is first created.
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 */

protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    TextView tv = (TextView)findViewById(R.id.equity_app_footer);
    displayAdverts(username,tv);
}
    
/**
 * onDestroy
 * The final call you receive before your activity is destroyed. 
 * This can happen either because the activity is finishing (someone called finish() on it, 
 * or because the system is temporarily destroying this instance of the activity to save space. 
 * You can distinguish between these two scenarios with the isFinishing() method.
 *
 */

@Override
protected void onDestroy ()
{
   super.onDestroy ();
}

/**
 * onPause
 * Called when the system is about to start resuming a previous activity. 
 * This is typically used to commit unsaved changes to persistent data, stop animations 
 * and other things that may be consuming CPU, etc. 
 * Implementations of this method must be very quick because the next activity will not be resumed 
 * until this method returns.
 * Followed by either onResume() if the activity returns back to the front, 
 * or onStop() if it becomes invisible to the user.
 *
 */

@Override
protected void onPause ()
{
   super.onPause ();
}

/**
 * onRestart
 * Called after your activity has been stopped, prior to it being started again.
 * Always followed by onStart().
 *
 */

@Override
protected void onRestart ()
{
   super.onRestart ();
}

/**
 * onResume
 * Called when the activity will start interacting with the user. 
 * At this point your activity is at the top of the activity stack, with user input going to it.
 * Always followed by onPause().
 *
 */

@Override
protected void onResume ()
{
   super.onResume ();
}

/**
 * onStart
 * Called when the activity is becoming visible to the user.
 * Followed by onResume() if the activity comes to the foreground, or onStop() if it becomes hidden.
 *
 */

@Override
protected void onStart ()
{
   super.onStart ();
}

/**
 * onStop
 * Called when the activity is no longer visible to the user
 * because another activity has been resumed and is covering this one. 
 * This may happen either because a new activity is being started, an existing one 
 * is being brought in front of this one, or this one is being destroyed.
 *
 * Followed by either onRestart() if this activity is coming back to interact with the user, 
 * or onDestroy() if this activity is going away.
 */

@Override
protected void onStop ()
{
   super.onStop ();
}

/* (non-Javadoc)
 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
 */
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// TODO Auto-generated method stub
	new MenuInflater(this).inflate(R.menu.homescreen_menu, menu);
	return super.onCreateOptionsMenu(menu);
}

/* (non-Javadoc)
 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
 */
@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	if(item.getItemId()==R.id.call_customer_care_homescreen_menu)
	{
		//code for starting call intent
		//change this nuber ot equity customer care and later add end call button
		startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:+245727802152")));
	}
	return super.onOptionsItemSelected(item);
}


} // end class
