package hkbu.cs.ivanyu.fyp2014_2;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String itemDesc;
    private final double itemPrice;
    private final String isbn;
    
    public MyItem(double lat, double lng, String itemDesc, double itemPrice, String isbn) {
        mPosition = new LatLng(lat, lng);
        this.itemDesc = itemDesc;
        this.itemPrice = itemPrice;
        this.isbn = isbn;
    }

    
    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
        itemDesc = "";
        itemPrice = 0.0;
        isbn = "";
        //this.itemDesc = itemDesc;
    }
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

	public String getItemDesc() {
		return itemDesc;
	}


	public String getIsbn() {
		return isbn;
	}
    
}
