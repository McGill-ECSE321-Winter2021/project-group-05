package ca.mcgill.ecse321.repairshop_android.Activities.Customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.HashMap;
import java.util.Map;

import ca.mcgill.ecse321.repairshop_android.R;

public class BookAppointmentFragment extends Fragment {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private LinearLayout parentLayout;
    private Button btnBookAppointment;
    private HashMap<Integer, String> services;

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
        displayAvailableServices();     //TODO  :: call after fetching, not here
        setBookAppointmentHandler();
    }

    private void setViews(View view){
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        parentLayout = view.findViewById(R.id.parentLayout);
        btnBookAppointment = view.findViewById(R.id.btnBookAppointment);
    }

    private void queryServices(){
        test();
        //TODO get a list of all services
    }

    private void displayAvailableServices(){
      for(Map.Entry<Integer, String> entry : services.entrySet()){
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(entry.getValue());
            checkBox.setId(entry.getKey());
            checkBox.setGravity(Gravity.CENTER);
            parentLayout.addView(checkBox);
        }
    }

    private void setBookAppointmentHandler(){
        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: call controller to book appointment
            }
        });
    }

    private void test(){
        this.services = new HashMap<>();
        this.services.put(1, "wash");
        this.services.put(2, "cleaning");
        this.services.put(3, "Dry");
        this.services.put(4, "cut");
    }

    public HashMap<Integer, String> getAllServices(){
        return services;
    }
}