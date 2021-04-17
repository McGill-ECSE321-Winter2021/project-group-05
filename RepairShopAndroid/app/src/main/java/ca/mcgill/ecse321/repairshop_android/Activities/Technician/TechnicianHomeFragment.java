package ca.mcgill.ecse321.repairshop_android.Activities.Technician;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnDayLongClickListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.mcgill.ecse321.repairshop_android.Activities.Customer.CustomerMainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.Model.Appointment;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class TechnicianHomeFragment extends Fragment {

    private CalendarView calendarView;
    private TextView tvSelectedDate;
    private List<EventDay> events;
    private static List<Appointment> allAppointments;


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

                tvSelectedDate.setText("Appointment Information:\n"+
                        findAppointmentOfTheDay(formatLongToDate(clickedDayCalendar.getTimeInMillis())));

            }
        });
    }

    private String findAppointmentOfTheDay(String date){
        String appointmentInfo="";
        String year = date.substring(6);
        String month = date.substring(0,2);
        String day = date.substring(3,5);

        // find all Appointments on the same day
        for (Appointment app : allAppointments){
            if (String.valueOf(app.DAY).equals(day) &&
                    String.valueOf(app.MONTH).equals(month) &&
                    String.valueOf(app.YEAR).equals(year) ){
                String nameServices = "";
                for (String name: app.getServices()){
                    nameServices+=name+", ";
                }

                appointmentInfo = appointmentInfo + month+"/"+day+"/"+year+":\ncustomer: "+app.getCustomer()+"\nservices: "+
                        nameServices+"\n";
            }
        }
        return appointmentInfo;
    }

    private void onLongClickHandler(){
        calendarView.setOnDayLongClickListener(new OnDayLongClickListener() {
            @Override
            public void onDayLongClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();


            }
        });
    }



    public static List<Appointment> getAllAppointments(){
        return allAppointments;
    }

    private void queryAppointments(){
        RequestParams requestParams = new RequestParams();
        requestParams.add("email", RepairShopUtil.getLoginUserEmail() );

        HttpUtils.get("appointmentOfTechnician", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    allAppointments = fromJsonArray(response);

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
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable) {

                Toast.makeText(getActivity(),"failed to retrieve any appointment for you",Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    /**
     * populates the event calendar with appointments
     */
    private void populateViewWithAppointment(){
        if(allAppointments == null){
            return;
        }
        for(Appointment appointment : allAppointments){
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.MONTH, appointment.MONTH - 1);
            calendar.set(Calendar.DAY_OF_MONTH, appointment.DAY + 1);
            calendar.set(Calendar.YEAR, appointment.YEAR);

            events.add(new EventDay(calendar, R.drawable.event));
            calendarView.setEvents(events);
        }
    }


    /**
     * parses a json object to a date type
     * @param appointment
     * @return
     * @throws JSONException
     */
    private static Date parseDate(JSONObject appointment) throws JSONException {
        JSONObject timeSlot = appointment.getJSONObject("timeSlot");
        String dateStringFormat = timeSlot.getString("date");
        return CustomerMainActivity.convertToDate(dateStringFormat);
    }

    /**
     * converts a date to a string
     * @param date
     * @return
     */
    private String convertDateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        String dateTime = dateFormat.format(date);
        System.out.println("Current Date Time : " + dateTime);
        return dateTime;
    }

    /**
     * splits the date into its 3 respective components: MONTH, DAY, YEAR
     * @param date
     * @return
     */
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

    /**
     * converts a long time to a date in the given format
     * @param date
     * @return
     */
    private String formatLongToDate(long date){
        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(date));
        return dateString;
    }

    public static List<Appointment> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Appointment> appointments = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            List<String> services = new ArrayList<>();

            Appointment appointment = new Appointment();

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // ADD THE CUSTOMER NAME
            appointment.setCustomer(jsonObject.getJSONObject("customer").getString("email"));

            JSONArray servicesArray = jsonObject.getJSONArray("services");

            // ADD ALL SERVICES
            for (int j=0; j< servicesArray.length(); j++){

                services.add(servicesArray.getJSONObject(i).getString("name"));

            }
            appointment.setServices(services);

            Date date = parseDate(jsonObject);

            appointment.setDate(date);


            appointments.add(appointment);
        }
        return appointments;
    }

}