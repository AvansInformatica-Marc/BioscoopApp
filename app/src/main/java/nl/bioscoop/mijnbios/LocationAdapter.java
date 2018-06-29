package nl.bioscoop.mijnbios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import nl.bioscoop.biosapi.model.location.Location;
import java.util.ArrayList;
import static nl.bioscoop.mijnbios.utils.Views.inflateLayout;

public class LocationAdapter extends ArrayAdapter<Location> {
    public LocationAdapter(@NonNull Context context, @NonNull ArrayList<Location> list){
        super(context, 0, list);
    }

    @Override @NonNull public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @NonNull View view = convertView != null ? convertView : inflateLayout(R.layout.location_item, parent);
        @Nullable Location location = getItem(position);

        if(location == null) {
            view.setVisibility(View.GONE);
        } else {
            TextView locationName = view.findViewById(R.id.locationNameTextView);
            locationName.setText(location.getLocationName());
            TextView locationAddress = view.findViewById(R.id.locationTextView);
            locationAddress.setText(location.getLocationAddress());
            TextView contactInformation = view.findViewById(R.id.contactInformationTextView);
            contactInformation.setText(location.getLocationContactInfo());
        }

        return view;
    }
}
