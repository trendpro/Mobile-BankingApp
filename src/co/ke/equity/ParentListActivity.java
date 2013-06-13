package co.ke.equity;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ParentListActivity extends ListActivity{
	//Variable declaration
	QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
	Timer mAdTimer = new Timer(true);
	String[] currentAdArray ={"Your Listening caring Partner","no_url"};
	Timer timer = new Timer();
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
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
	
	public void setTitleFromActivityLabel (int textViewId)
	{
	    TextView tv = (TextView) findViewById (textViewId);
	    if (tv != null) tv.setText (getTitle ());
	} // end setTitleText

}//end of class
