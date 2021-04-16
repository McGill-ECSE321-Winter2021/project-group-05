package ca.mcgill.ecse321.repairshop_android.Activities.Technician;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnDayLongClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse321.repairshop_android.Activities.Customer.CancelAppointmentDialogFragment;
import ca.mcgill.ecse321.repairshop_android.Activities.Customer.UpdateAppointmentDialogFragment;
import ca.mcgill.ecse321.repairshop_android.R;

public class TechnicianHomeFragment extends Fragment {

    private CalendarView calendarView;
    private TextView tvSelectedDate;
    private List<EventDay> events;


    public TechnicianHomeFragment() {
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
        return inflater.inflate(R.layout.fragment_technician_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViews(view);

        Calendar calendar = Calendar.getInstance();


        events.add(new EventDay(calendar, R.drawable.ic_round_event_24));
        calendarView.setEvents(events);

        onDayClickHandler();
        onLongClickHandler();
    }

    private void setViews(View views){
        calendarView = views.findViewById(R.id.calendarView);
        tvSelectedDate = views.findViewById(R.id.tvSelectedDate);
        events = new ArrayList<>();
    }

    private void onDayClickHandler(){
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                tvSelectedDate.setText(clickedDayCalendar.getTime().toString());

            }
        });
    }

    private void onLongClickHandler(){
        calendarView.setOnDayLongClickListener(new OnDayLongClickListener() {
            @Override
            public void onDayLongClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();


            }
        });
    }





}