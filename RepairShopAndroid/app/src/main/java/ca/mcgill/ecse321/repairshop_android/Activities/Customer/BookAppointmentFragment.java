package ca.mcgill.ecse321.repairshop_android.Activities.Customer;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Model.User;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;
/*
books an appointment
*/
public class BookAppointmentFragment extends Fragment {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private LinearLayout parentLayout;
    private Button btnBookAppointment;
    private HashMap<Integer, String> services;
    private List<String> selectedServices;
    private static User user;

    public BookAppointmentFragment() {
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
        return inflater.inflate(R.layout.fragment_book_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViews(view);
        queryServices();
        displayAvailableServices();
        setBookAppointmentHandler();
    }

    /**
     * sets the view of the layout
     * @param view
     */
    private void setViews(View view){
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        parentLayout = view.findViewById(R.id.parentLayout);
        btnBookAppointment = view.findViewById(R.id.btnBookAppointment);
        services = new HashMap<>();
        selectedServices = new ArrayList<>();
    }

    /**
     * fetches all the services from the database
     */
    private void queryServices(){
        List<String> allServices = CustomerMainActivity.getAllServices();
        if(allServices == null){
            return;
        }
        for(int i = 0; i < allServices.size(); ++i) {
            services.put(i, allServices.get(i));
        }
    }

    /**
     * populates the view will all the available services
     */
    private void displayAvailableServices(){
        for(Map.Entry<Integer, String> entry : services.entrySet()){
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(entry.getValue());
            checkBox.setId(entry.getKey());
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setOnClickListener(getOnSelected(checkBox));
            checkBox.setSelected(false);
            parentLayout.addView(checkBox);
        }
    }

    /**
     * handles the book appointment button
     */
    private void setBookAppointmentHandler(){
        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                bookAppointment();
            }
        });
    }

    /**
     * sends the request to book the appointment
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void bookAppointment(){
        user = (User) Parcels.unwrap(getActivity().getIntent().getParcelableExtra("user"));
        String email = user.getEmail();
        String date = datePicker.getYear() + "-" + (Integer.valueOf(datePicker.getMonth()) + 1) + "-" + datePicker.getDayOfMonth();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String time = hour + ":" + minute + "";


        if(date == null || time == null){
            Toast.makeText(getContext(), "Date and time need to be selected", Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedServices == null || selectedServices.size() == 0){
            Toast.makeText(getContext(), "A service needs to be selected", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("customerEmail", email);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestParams.put("startTime", LocalTime.of(hour, minute));
        }else{
            requestParams.put("startTime", time);
        }
        requestParams.put("date", date);
        requestParams.put("serviceNames", selectedServices);

        System.out.println(email);
        System.out.println(time);
        System.out.println(date);
        System.out.println(selectedServices);

        HttpUtils.post("/appointment/app", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(getContext(), "Successfully booked appointment", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println(errorResponse.toString());
                Toast.makeText(getContext(), errorResponse.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(statusCode);
            }


        });
    }

    /**
     * sets the services booked in the appointment
     * @param view
     * @return
     */
    private List<String> selectedServices(View view) {
        for (Map.Entry<Integer, String> entry : services.entrySet()) {
            int checkboxId = entry.getKey();
        }
        return null;
    }

    View.OnClickListener getOnSelected(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("and text***" + button.getText().toString());
                selectedServices.add(button.getText().toString());
            }
        };
    }

}
