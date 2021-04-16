package ca.mcgill.ecse321.repairshop_android.Activities.Customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse321.repairshop_android.R;

public class UpdateAppointmentDialogFragment extends DialogFragment {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button btnUpdateAppointment;
    private LinearLayout parentLayout;
    static HashMap<Integer, String> allServices = new HashMap<>();
    public UpdateAppointmentDialogFragment() {
        // Required empty public constructor
    }

    public static UpdateAppointmentDialogFragment newInstance(String title){
        UpdateAppointmentDialogFragment frag = new UpdateAppointmentDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public static UpdateAppointmentDialogFragment newInstance(String title, HashMap<Integer, String> services){
        UpdateAppointmentDialogFragment frag = new UpdateAppointmentDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        for(Map.Entry<Integer, String> entry : services.entrySet()){
            args.putString(entry.getKey().toString(), entry.getValue());
        }
        allServices = services;
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViews(view);
        setTitleOfDialog();
        inflateServiceList();
        inflateServiceList();
        inflateServiceList();
        inflateServiceList();
        inflateServiceList();

    }

    private void setTitleOfDialog(){
        String title = getArguments().getString("title");
        getDialog().setTitle(title);
    }

    private void setViews(View view){
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        btnUpdateAppointment = view.findViewById(R.id.btnUpdateAppointment);
        parentLayout = view.findViewById(R.id.parentLayout);
    }

    private void inflateServiceList(){
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setText("wash");
        checkBox.setGravity(Gravity.CENTER);
        parentLayout.addView(checkBox);

    }
}