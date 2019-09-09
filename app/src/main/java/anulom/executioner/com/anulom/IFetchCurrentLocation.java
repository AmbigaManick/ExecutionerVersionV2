package anulom.executioner.com.anulom;

import android.location.Location;


public interface IFetchCurrentLocation {
    void onSuccess(Location loc);
    void onFailure();
    void saveOldLocation(Location loc);
}
