package co.ke.equity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class ATMItemizedOverlay extends ItemizedOverlay{
	
	//Variables
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	public ATMItemizedOverlay(Drawable defaultMarker,Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;

		// TODO Auto-generated constructor stub
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		 return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	 
	 GeoPoint curPos = GetNearestATMActivity.mCurrentLocation;
	 double sourceLat = Double.parseDouble(curPos.getLatitudeE6()+"");
	 double sourceLon = Double.parseDouble(curPos.getLongitudeE6()+"");
	 
	 double desLat = Double.parseDouble(item.getPoint().getLatitudeE6()+"");
	 double desLon = Double.parseDouble(item.getPoint().getLongitudeE6()+"");
	 
	 String uri = "http://maps.google.com/maps?saddr=" +sourceLat/1000000.0+","+sourceLon/1000000.0+"&daddr="+desLat/1000000+","+desLon/1000000;
	 Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
	 
	 ((Activity)mContext).startActivity(intent);

	  return true;
	}
	
}//end of class
