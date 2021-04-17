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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class UpdateAppointmentDialogFragment extends DialogFragment {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button btnUpdateAppointment;
    private LinearLayout parentLayout;
    static HashMap<Integer, String> allServices = new HashMap<>();
    public UpdateAppointmentDialogFragment() {
        // Required empty public constructor
    }

    public static UpdateAppointmentDialogFragment newInstance(String title, ArrayList<String> services){
        UpdateAppointmentDialogFragment frag = new UpdateAppointmentDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArrayList("services", services);
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

    }

    /**
     * sets the title of the dialog
     */
    private void setTitleOfDialog(){
        String title = getArguments().getString("title");
        getDialog().setTitle(title);
    }

    /**
     * sets the view of the dialog
     * @param view
     */
    private void setViews(View view){
        parentLayout = view.findViewById(R.id.parentLayout);
    }

    /**
     * populates the dialog with all the services in the system
     */
    private void inflateServiceList(){
        ArrayList<String> services = getArguments().getStringArrayList("services");

        for(int i = 0; i < services.size(); ++i) {
            allServices.put(i, CustomerMainActivity.getAllServices().get(i));
            TextView checkBox = new TextView(getContext());
            checkBox.setText(allServices.get(i));
            checkBox.setId(i);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setTextColor(R.color.day_item_background_state_color);
            parentLayout.addView(checkBox);
        }
    }

    /**
     * handles the update of an appointment
     */
    private void updateAppointmentHandler(){
        btnUpdateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAppointment();
            }
        });
    }

    private void updateAppointment(){
        //TODO: handle update
        getDialog().dismiss();

        RequestParams requestParams = new RequestParams();
        //requestParams.put("id", appointmentId);
        HttpUtils.delete("appointment", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(getActivity(), "Your appointment was successfully cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity(), "went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}