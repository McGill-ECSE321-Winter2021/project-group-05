package ca.mcgill.ecse321.repairshop_android.Activities.Customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnDayLongClickListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Model.Appointment;
import ca.mcgill.ecse321.repairshop_android.Model.User;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class CustomerHomeFragment extends Fragment {
    private CalendarView calendarView;
    private TextView tvSelectedDate;
    private List<EventDay> events;
    private static User user;
    private List<Appointment> allAppointments;

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
        queryAppointments();
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
                Log.d("Tag", clickedDayCalendar.getTime().toString());
                tvSelectedDate.setText(formatLongToDate(clickedDayCalendar.getTimeInMillis()));
                showUpdateDialog(1);
            }
        });
    }

    private void onLongClickHandler(){
        calendarView.setOnDayLongClickListener(new OnDayLongClickListener() {
            @Override
            public void onDayLongClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                showCancelDialog(1);
            }
        });
    }

    private void showCancelDialog(int appointmentId){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        CancelAppointmentDialogFragment alertDialog = CancelAppointmentDialogFragment.newInstance("Cancel Appointment", appointmentId); //TODO: FIX ID
        alertDialog.show(fm, "fragment_alert");
    }

    private void showUpdateDialog(int appointmentId){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        UpdateAppointmentDialogFragment dialogFragment = UpdateAppointmentDialogFragment.newInstance("Update Appointment");
        dialogFragment.show(fm, "fragment_update");
    }
    /**
     * Fetches all the appointment of the customer
     */
    private void queryAppointments() {
        user = (User) Parcels.unwrap(getActivity().getIntent().getParcelableExtra("user"));
        String customerEmail = user.getEmail();
        String url = "appointment/person/" + customerEmail;
        HttpUtils.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    allAppointments = getAllAppointments(response);
                    for(Appointment appointment : allAppointments){
                        appointment.MONTH = formatDate(convertDateToString(appointment.getDate()))[1];
                        appointment.DAY = formatDate(convertDateToString(appointment.getDate()))[2];
                        appointment.YEAR = formatDate(convertDateToString(appointment.getDate()))[0];
                    }
                    populateViewWithAppointment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void populateViewWithAppointment(){
        if(allAppointments == null){
            return;
        }
        System.out.println("size = a" + allAppointments.size());
        for(Appointment appointment : allAppointments){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 13);
            calendar.set(Calendar.HOUR, 7);
            calendar.set(Calendar.MONTH, appointment.MONTH - 1);
            calendar.set(Calendar.DAY_OF_MONTH, appointment.DAY + 1);
            calendar.set(Calendar.YEAR, appointment.YEAR);

            events.add(new EventDay(calendar, R.drawable.event));
            calendarView.setEvents(events);
        }
    }


    private static List<Appointment> getAllAppointments(JSONArray jsonArray) throws JSONException {
        List<Appointment> appointments = new ArrayList<Appointment>();
        for(int i = 0; i  < jsonArray.length(); ++i){
            Appointment appointment = new Appointment();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            List<String> serviceNames = CustomerMainActivity.getServiceNames(jsonObject.getJSONArray("services"));
            Date date = parseDate(jsonObject);
            String id = jsonObject.getString("id");
            appointment.setDate(date);
            appointment.setServices(serviceNames);
            appointments.add(appointment);
            appointment.setId(id);

        }
        return appointments;
    }

    private static Date parseDate(JSONObject appointment) throws JSONException {
        JSONObject timeSlot = appointment.getJSONObject("timeSlot");
        String dateStringFormat = timeSlot.getString("date");
        return CustomerMainActivity.convertToDate(dateStringFormat);
    }


    private String convertDateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        String dateTime = dateFormat.format(date);
        System.out.println("Current Date Time : " + dateTime);
        return dateTime;
    }

    private int [] formatDate(String date){
        String [] arr = date.split("-");
        int [] ans = new int[3];
        int idx = 0;
        String s = "";
        for(int i =0; i <date.length(); ++i){
            if(date.charAt(i) == '-'){
                ans[idx++] = Integer.parseInt(s);
                s = "";
                continue;
            }
            s += date.charAt(i);
        }
        s = s.substring(0, 2);
        ans[idx] = Integer.parseInt(s);
        return ans;
    }

    private String formatLongToDate(long date){
        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(date));
        return dateString;
    }
}