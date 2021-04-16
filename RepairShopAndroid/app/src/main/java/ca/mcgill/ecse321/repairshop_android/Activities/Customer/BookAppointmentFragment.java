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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.R;

public class BookAppointmentFragment extends Fragment {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private LinearLayout parentLayout;
    private Button btnBookAppointment;
    private HashMap<Integer, String> services;
    private List<String> selectedServices;

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

    private void setViews(View view){
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        parentLayout = view.findViewById(R.id.parentLayout);
        btnBookAppointment = view.findViewById(R.id.btnBookAppointment);
        services = new HashMap<>();
        selectedServices = new ArrayList<>();
    }

    private void queryServices(){
        List<String> allServices = CustomerMainActivity.getAllServices();
        if(allServices == null){
            return;
        }
        for(int i = 0; i < allServices.size(); ++i) {
            services.put(i, allServices.get(i));
        }
    }

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

    private void setBookAppointmentHandler(){
        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: call controller to book appointment
            }
        });
    }

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