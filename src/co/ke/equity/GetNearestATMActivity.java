
package co.ke.equity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * This is the activity for get nearest ATM feature in the equity app
 * It displays your current location on a google map and nearest Equity Bank AutoBranches
 *
 */

public class GetNearestATMActivity extends MapActivity 
{
	//Instance variables
	MapController mMapController = null;
	private MapView mMapView = null;
	Cursor mMyAtmLocationsCursor = null;
	MainAppDbHelper mDBHelper = null;
	private static final GeoPoint DEFAULT_MY_CURRENT_LOCATION = new GeoPoint(29647929, -82352486);
	public static  GeoPoint mCurrentLocation = DEFAULT_MY_CURRENT_LOCATION;//doesnt sound ok
	
	private  String username = MainAppLoginActivity.USERNAME;
	QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
	String[] currentAdArray ={"Your Listening caring Partner","no_url"};
	Timer timer = new Timer();

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
    setContentView (R.layout.activity_get_nearest_atm);
    setTitleFromActivityLabel (R.id.title_text);
    TextView tv = (TextView)findViewById(R.id.equity_app_footer);
    displayAdverts(username,tv);
    
    //initializations
    mDBHelper = new MainAppDbHelper(this);
    mMyAtmLocationsCursor = mDBHelper.getAll();
    
    
    //find views by id
    mMapView = (MapView)findViewById(R.id.map);
    mMapController = mMapView.getController();
    
    mMapController.setZoom(14);
    mMapView.setBuiltInZoomControls(true);
    
    List<Overlay> overlays = mMapView.getOverlays();
    overlays.add(getAllATMOverlays(this));
    overlays.add(showMyCurrentLocationOverlay(this));
    
	mMapView.getController().setCenter(mCurrentLocation);
    
}//end of onCreate()



/* (non-Javadoc)
 * @see com.google.android.maps.MapActivity#onPause()
 */
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
}



/* (non-Javadoc)
 * @see com.google.android.maps.MapActivity#onResume()
 */
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
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



@Override
protected void onDestroy()
{
	// TODO Auto-generated method stub
	super.onDestroy();
	
	//Close SQLite database
	mDBHelper.close();
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

@Override
protected boolean isRouteDisplayed() {
	// TODO Auto-generated method stub
	return false;
}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// TODO Auto-generated method stub
	new MenuInflater(this).inflate(R.menu.get_nearest_atm_menu, menu);
	return super.onCreateOptionsMenu(menu);
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	if(item.getItemId()==R.id.street_view_menu_get_nearest_atm)
	{
		mMapView.setStreetView(true);
		mMapView.postInvalidate();
	}
	else if(item.getItemId()==R.id.traffic_view_menu_get_nearest_atm)
	{
		mMapView.setStreetView(false);//incompatible with traffic view
		mMapView.setTraffic(true);
		mMapView.postInvalidate();
	}
	else if(item.getItemId()==R.id.satelite_view_menu_get_nearest_atm)
	{
		mMapView.setSatellite(true);
		mMapView.postInvalidate();
	}
	else if(item.getItemId()==R.id.map_view_menu_get_nearest_atm)
	{
		mMapView.setSatellite(false);
	    mMapView.setTraffic(false);
	    mMapView.setStreetView(false);
	    mMapView.postInvalidate();
	}//end of if
	
	return super.onOptionsItemSelected(item);
}


/*
 * Populates atm_locations_table with GeoPoint-coordinates in Microdegrees when the application 
 * runs for the first time
 */
private void populateDBWithLocations()
{
	//put this in a try catch clause
	mDBHelper.insertLocation("Kahawa west AutoBranch",29656582, -82411151,"6419 W Newberry Rd, Gainesville, FL 32605");
	mDBHelper.insertLocation("Equity Nyali",29649831 ,-82376347,"3501 Southwest 2nd Avenue, Gainesville, FL");
	mDBHelper.insertLocation("Kigali AutoBranch",29674146 ,-8238905,"NW 43rd St & NW 16th Blvd. Gainesville, FL");
	mDBHelper.insertLocation("Makolongo ATMs", 29675078,-82322617,"Gainesville, FL");
	mDBHelper.insertLocation("Cashville Mall", 29677017,-82339761,"2624 Northwest 13th Street Gainesville, FL 32609-2834");
	mDBHelper.insertLocation("Wayneville Shopping Center",29663835 ,-82325599,"1344 N Main St Gainesville, Florida 32601");
	
}//end of method


/*
 * Creates overlay items from Coordinates stored in database.
 */
private ATMItemizedOverlay getAllATMOverlays(Context ctx)
{	
	//Variable declaration
	Cursor myLocalCursor = mMyAtmLocationsCursor;//dalvik vm optimization
	
	Drawable marker=getResources().getDrawable(R.drawable.atm_marker);
    marker.setBounds(0, 0, marker.getIntrinsicWidth(),marker.getIntrinsicHeight());
    
	ATMItemizedOverlay atmsPos = new ATMItemizedOverlay(marker,ctx);
	
	GeoPoint curPoint;
	
		//if cursor has data populate overlays otherwise insert data into table
		if(myLocalCursor.moveToFirst())
		{
			do
			{
				curPoint = new GeoPoint(myLocalCursor.getInt(MainAppDbHelper.LATITUDE_COLUMN), myLocalCursor.getInt(MainAppDbHelper.LONGITUDE_COLUMN));
				OverlayItem overlayItem = new OverlayItem(curPoint, myLocalCursor.getString(MainAppDbHelper.ATM_NAME_COLUMN), myLocalCursor.getString(MainAppDbHelper.ATM_LOCATION_ADDRESS_SNIPPET_COLUMN));
				atmsPos.addOverlay(overlayItem);
				
			}while(myLocalCursor.moveToNext());
		}
		else
		{
			populateDBWithLocations();
			myLocalCursor = mDBHelper.getAll();
			
			if(myLocalCursor.moveToFirst())
			{
				do
				{
					curPoint = new GeoPoint(myLocalCursor.getInt(MainAppDbHelper.LATITUDE_COLUMN), myLocalCursor.getInt(MainAppDbHelper.LONGITUDE_COLUMN));
					OverlayItem overlayItem = new OverlayItem(curPoint, myLocalCursor.getString(MainAppDbHelper.ATM_NAME_COLUMN), myLocalCursor.getString(MainAppDbHelper.ATM_LOCATION_ADDRESS_SNIPPET_COLUMN));
					atmsPos.addOverlay(overlayItem);
					
				}while(myLocalCursor.moveToNext());
			}
		}
	
	return atmsPos;
	
}//end of mock()

/*
 * Creates an overlay Item indicating your current location
 */
private ATMItemizedOverlay showMyCurrentLocationOverlay(Context ctx)
{	
	//Variable declaration
	GeoPoint curLoc;
	Drawable cur_location_marker=getResources().getDrawable(R.drawable.cur_location_marker);
	cur_location_marker.setBounds(0, 0, cur_location_marker.getIntrinsicWidth(),cur_location_marker.getIntrinsicHeight());
    
	ATMItemizedOverlay currentPos = new ATMItemizedOverlay(cur_location_marker,ctx);
	
	MyLocationOverlay myLocationOverlay = new MyLocationOverlay(ctx, mMapView);
	
	curLoc =myLocationOverlay.getMyLocation();
		
		//Check if curLoc is null.If null set it to default
		if(curLoc ==  null)
		{
			curLoc = DEFAULT_MY_CURRENT_LOCATION;
		}
	 //Reassign current location
	mCurrentLocation = curLoc;
	 
	OverlayItem overlayItem = new OverlayItem(curLoc, "My Current Location", "Where Am I");
	currentPos.addOverlay(overlayItem);
	
	return currentPos;
	
}//end of show my current location

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
