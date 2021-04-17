package ca.mcgill.ecse321.repairshop_android.Activities.Admin;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.JsonHelper;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class AdminHomeFragment extends Fragment {

    private String error = "";
    private Button deleteServiceButton;
    private Button updateServiceButton;
    private List<String> serviceList = new ArrayList<>();
    private EditText text_edit_service_name = null;
    private EditText text_edit_service_cost = null;
    private EditText text_edit_service_duration = null;
    String selectedItemText = null;
    Spinner spinner = null;
    public AdminHomeFragment() {
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
        return inflater.inflate(R.layout.fragment_admin_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view);
    }

    public void setView(View view) {
        deleteServiceButton = view.findViewById(R.id.delete);
        updateServiceButton = view.findViewById(R.id.update);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        text_edit_service_name = (EditText) view.findViewById(R.id.newServiceName);
        text_edit_service_cost = (EditText) view.findViewById(R.id.newServiceCost);
        text_edit_service_duration = (EditText) view.findViewById(R.id.newServiceDuration);

        getServices(view);
        deleteButtonHandler();
        updateButtonHandler();

    }

    /**
     * Gets all the available services and adds to the spinner
     * @param view
     */

    private void getServices(final View view) {
        RequestParams requestParams = new RequestParams();
        HttpUtils.get("/bookableServices", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for(int i = 0; i < response.length(); i++) {
                    try {
                        serviceList = JsonHelper.toList(response);
                    }catch (JSONException e) {
                        Toast.makeText
                                (getActivity(), e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                serviceList.add(0, "Select a service");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, serviceList) {
                    @Override
                    public boolean isEnabled(int position){
                        if(position == 0)
                        {
                            // Disable the first item from Spinner as first item will be use for hint
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if(position == 0){
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        }
                        return view;
                    }
                };

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner sItems = (Spinner) view.findViewById(R.id.spinner);
                sItems.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedItemText = (String) parent.getItemAtPosition(position);
                        // If user change the default selection
                        // First item is disable and it is used for hint
                        if(position > 0){
                            // Notify the selected item text
                            Toast.makeText
                                    (getActivity(), "Selected service: " + selectedItemText, Toast.LENGTH_SHORT)
                                    .show();
                            getServiceByName(selectedItemText, view);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                    Toast.makeText
                            (getActivity(), error, Toast.LENGTH_SHORT)
                            .show();
                } catch (JSONException e) {
                    error += e.getMessage();
                    Toast.makeText
                            (getActivity(), error, Toast.LENGTH_SHORT)
                            .show();
                }

            }

        });
    }


    /**
     * Displays details of the select service from the spinner
     * @param serviceName
     * @param view
     */
    public void getServiceByName(String serviceName, final View view) {
        HttpUtils.get("/bookableService/"+serviceName, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    text_edit_service_name.setText(((JSONObject) response).get("name").toString());
                    text_edit_service_cost.setText(((JSONObject) response).get("cost").toString());
                    text_edit_service_duration.setText(((JSONObject) response).get("duration").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                    Toast.makeText
                            (getActivity(), error, Toast.LENGTH_SHORT)
                            .show();
                } catch (JSONException e) {
                    error += e.getMessage();
                    Toast.makeText
                            (getActivity(), error, Toast.LENGTH_SHORT)
                            .show();
                }

            }

        });
    }


    /**
     * sets delete button on-click function
     */
    public void deleteButtonHandler(){
        deleteServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService();
            }
        });
    }

    /**
     * sets update button on-click function
     */
    public void updateButtonHandler(){
        updateServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateService();
            }
        });
    }

    /**
     * deletes a service when delete button is pressed for a service
     */
    public void deleteService() {
        String serviceToDelete = selectedItemText;
        RequestParams requestParams = new RequestParams();
        requestParams.add("name", serviceToDelete);
        HttpUtils.delete("/bookableService/app", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                text_edit_service_name.setText("");
                text_edit_service_cost.setText("");
                text_edit_service_duration.setText("");
                spinner.setSelection(0, true);
                refresh();
                Toast.makeText
                        (getActivity(), "Service deleted successfully", Toast.LENGTH_SHORT)
                        .show();


            }
            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable){
                Toast.makeText
                        (getActivity(), responseString, Toast.LENGTH_SHORT)
                        .show();
                if(statusCode == 200) {
                    text_edit_service_name.setText("");
                    text_edit_service_cost.setText("");
                    text_edit_service_duration.setText("");
                    spinner.setSelection(0, true);
                    refresh();
                }
            }

        });
    }


    /**
     * updates a service when update button is pressed for a service
     */
    public void updateService() {
        RequestParams requestParams = new RequestParams();
        requestParams.add("oldName", selectedItemText);
        requestParams.add("newName", text_edit_service_name.getText().toString());
        requestParams.add("newCost", text_edit_service_cost.getText().toString());
        requestParams.add("newDuration", text_edit_service_duration.getText().toString());

        HttpUtils.put("/bookableService/app/", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText
                        (getActivity(), "Service updated successfully", Toast.LENGTH_SHORT)
                        .show();
                text_edit_service_name.setText("");
                text_edit_service_cost.setText("");
                text_edit_service_duration.setText("");
                spinner.setSelection(0, true);
                refresh();

            }
            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable){
                Toast.makeText
                        (getActivity(), responseString, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * this method refreshes the view of the fragment
     */
    public void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(AdminHomeFragment.this).attach(AdminHomeFragment.this).commit();
    }

}