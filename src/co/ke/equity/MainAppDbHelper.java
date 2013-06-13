package co.ke.equity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainAppDbHelper extends SQLiteOpenHelper{
	
	//Variables declaration
	private static final String DATABASE_NAME = "equity_app.db";
	private static final int SCHEMA_VERSION = 1;
	private static final String CREATE_ATM_LOCATIONS_TABLE = "CREATE TABLE atm_location_coords (name TEXT PRIMARY KEY,longitude INTEGER,latitude INTEGER, location_address_snippet TEXT);";
	static final int LATITUDE_COLUMN = 1;
	static final int LONGITUDE_COLUMN = 2;
	static final int ATM_NAME_COLUMN = 0;
	static final int ATM_LOCATION_ADDRESS_SNIPPET_COLUMN = 3;

	public MainAppDbHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_ATM_LOCATIONS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//No operation as it will not be called until the next schema version is available
		
	}
	
	/*
	 * Adds data to atm_location_coords table
	 */
	public void insertLocation(String name, double lat, double lon,String locationAddressSnippet)
	{
		ContentValues cv = new ContentValues();
		cv.put("name", name);
		cv.put("latitude", lat);
		cv.put("longitude", lon);
		cv.put("location_address_snippet",locationAddressSnippet);
		
		getWritableDatabase().insert("atm_location_coords", null, cv);
		
	}
	
	/*
	 * Returns a Cursor with all location data
	 */
	public Cursor getAll()
	{
		return(getReadableDatabase().rawQuery("SELECT name, latitude, longitude, location_address_snippet FROM atm_location_coords ORDER BY name",
		null));
	}

	public String getName(Cursor c)
	{
		return c.getString(ATM_NAME_COLUMN);
	}
	
	public int getLatitude(Cursor c)
	{
		return c.getInt(LATITUDE_COLUMN);
	}
	
	public int getLongitude(Cursor c)
	{
		return c.getInt(LONGITUDE_COLUMN);
	}
	
	public String getATMLocationAddressSnippet(Cursor c)
	{
		return c.getString(ATM_LOCATION_ADDRESS_SNIPPET_COLUMN);
	}

}//end of class
