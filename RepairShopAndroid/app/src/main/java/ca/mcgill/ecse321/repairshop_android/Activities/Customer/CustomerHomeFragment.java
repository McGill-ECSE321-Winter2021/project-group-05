package ca.mcgill.ecse321.repairshop_android.Activities.Customer;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.model.Event;

import ca.mcgill.ecse321.repairshop_android.R;

public class CustomerHomeFragment extends Fragment {
    private CalenderEvent calendarEvent;

    public CustomerHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViews(view);
    }

    private void setViews(View views){
        calendarEvent = views.findViewById(R.id.calender_event);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setEvent(){
        Calendar calendar = Calendar.getInstance();
        Event event = new Event(calendar.getTimeInMillis(), "Test");
// to set desire day time milli second in first parameter
//or you set color for each event
        Event event2 = new Event(calendar.getTimeInMillis(), "Test", Color.RED);
        calendarEvent.addEvent(event2);
    }

}