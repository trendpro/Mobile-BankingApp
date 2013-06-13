
package co.ke.equity;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the base class for activities in the Equity application.
 * It implements methods that are useful to all top level activities.
 * That includes: (1) stub methods for all the activity lifecycle methods;
 * (2) onClick methods for clicks on home, search, feature 1, feature 2, etc.
 * (3) a method for displaying a message to the screen via the Toast class.
 *
 */

public abstract class ParentActivity extends Activity 
{
	//Instance variable
	QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
	Timer mAdTimer = new Timer(true);
	String[] currentAdArray ={"Your Listening caring Partner","no_url"};
	Timer timer = new Timer();

/**
 * onCreate - called when the activity is first created.
 *
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
    //setContentView(R.layout.activity_default);
    //mFooterTV = (TextView)findViewById(R.id.equity_app_footer);
    //mFooterTV.setText("Your Listening Caring Partner");
}
    
/**
 * onDestroy
 * The final call you receive before your activity is destroyed. 
 * This can happen either because the activity is finishing (someone called finish() on it, 
 * or because the system is temporarily destroying this instance of the activity to save space. 
 * You can distinguish between these two scenarios with the isFinishing() method.
 *
 */

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

protected void onStop ()
{
    super.onStop ();
}

/**
 */
// Click Methods

/**
 * Handle the click on the home button.
 * 
 * @param v View
 * @return void
 */

public void onClickHome (View v)
{
    goHome (this);
}


/**
 * Handle the click on the About button.
 * 
 * @param v View
 * @return void
 */

public void onClickAbout (View v)
{
    startActivity (new Intent(getApplicationContext(), AboutActivity.class));
}

/**
 * Handle the click of a Feature button.
 * 
 * @param v View
 * @return void
 */

public void onClickFeature (View v)
{
    int id = v.getId ();
    switch (id) {
      case R.id.home_btn_feature1 :
           startActivity (new Intent(getApplicationContext(), GetNearestATMActivity.class));
           break;
      case R.id.home_btn_feature2 :
    	  Uri uri = Uri.parse("https://m.equitybank.co.ke/wap/");
  		  Intent intent = new Intent(Intent.ACTION_VIEW, uri);
  		  startActivity(intent);
    	  
           break;
      case R.id.home_btn_feature3 :
           startActivity (new Intent(getApplicationContext(), RatesAndChargesActivity.class));
           break;
      case R.id.home_btn_feature4 :
           startActivity (new Intent(getApplicationContext(), NseLiveActivity.class));
           break;
      case R.id.home_btn_feature5 :
           startActivity (new Intent(getApplicationContext(), EquityNewsActivity.class));
           break;
      //case R.id.home_btn_feature6 :
          // startActivity (new Intent(getApplicationContext(), F6Activity.class));
           //break;
      default: 
    	   break;
    }
}

/**
 */
// More Methods

/**
 * Go back to the home activity.
 * 
 * @param context Context
 * @return void
 */

public void goHome(Context context) 
{
    final Intent intent = new Intent(context, HomeActivity.class);
    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity (intent);
}

/**
 * Use the activity label to set the text in the activity's title text view.
 * The argument gives the name of the view.
 *
 * <p> This method is needed because we have a custom title bar rather than the default Android title bar.
 * See the theme definitons in styles.xml.
 * 
 * @param textViewId int
 * @return void
 */

public void setTitleFromActivityLabel (int textViewId)
{
    TextView tv = (TextView) findViewById (textViewId);
    if (tv != null) tv.setText (getTitle ());
} // end setTitleText

/**
 * Show a string on the screen via Toast.
 * 
 * @param msg String
 * @return void
 */

public void toast (String msg)
{
    Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_LONG).show ();
} // end toast

/**
 * Send a message to the debug log and display it using Toast.
 */
public void trace (String msg) 
{
    Log.d("EquityApp", msg);
    toast (msg);
}

/*
 * Displays Adverts at the applications footer after a finite amount of time
 */

@SuppressLint("ParserError")
public void displayAdverts(final String uname,final TextView tv)
{
	//final TextView tv = (TextView)findViewById(R.id.equity_app_footer);
    timer.schedule(new TimerTask() {
		
		@Override
		public void run()
		{
			//TODO Auto-generated method stub
			//fetch ads preriodically from the QuestAccess Server using a timer object.
			String ads[] = qAccessClient.fetchRandomAdvert(uname);
			currentAdArray = ads;
			tv.setText(ads[0]);
		}
	},3000, 10000);
}//end of timer 

/*
 *  Open webKit Browser with the adverts url appended,if it exists
 */
public void onClickAd (View v)
{
    //startActivity (new Intent(getApplicationContext(), SearchActivity.class));
	if(currentAdArray[1].equals("no_url"))
	{
		//do nothing since the ad does not have a url
	}
	else
	{
		//Launch webkit browser with the ads url appended.This is genious
		Uri uri = Uri.parse(currentAdArray[1]);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
    
}

}//end class
