package ca.mcgill.ecse321.repairshop_android.Activities.Admin;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.sql.SQLOutput;
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
    private List<String> spinnerArray = new ArrayList<>();
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



        //System.out.println(spinnerArray.toString() + " ===================================================");
    }


    private void getServices(final View view) {
        RequestParams requestParams = new RequestParams();
        HttpUtils.get("/bookableServices", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for(int i = 0; i < response.length(); i++) {
                    try {
                        spinnerArray = JsonHelper.toList(response);
//                        for(String service : JsonHelper.toList(response)) {
//                            spinnerArray.add(service);
//                        }
                    }catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }
                }
                spinnerArray.add(0, "Select a service");
                System.out.println("ONSUCCESS ============== " + spinnerArray);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray) {
                    @Override
                    public boolean isEnabled(int position){
                        if(position == 0)
                        {
                            // Disable the first item from Spinner
                            // First item will be use for hint
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
                        else {
                            tv.setTextColor(Color.BLACK);
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
                                    (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
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
                    System.out.println("inside try : "+error);
                } catch (JSONException e) {
                    error += e.getMessage();
                    System.out.println("catch : "+error);
                }

            }

        });
    }




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
                    System.out.println("inside try : "+error);
                } catch (JSONException e) {
                    error += e.getMessage();
                    System.out.println("catch : "+error);
                }

            }

        });
    }


    public void setView(View view) {
        deleteServiceButton = view.findViewById(R.id.delete);
        updateServiceButton = view.findViewById(R.id.update);
        System.out.println("inside setVIEW  ==================================");
        spinner = (Spinner) view.findViewById(R.id.spinner);

        text_edit_service_name = (EditText) view.findViewById(R.id.newServiceName);
        text_edit_service_cost = (EditText) view.findViewById(R.id.newServiceCost);
        text_edit_service_duration = (EditText) view.findViewById(R.id.newServiceDuration);

        spinnerArray.add("Select a service");
        getServices(view);
        deleteButtonHandler();
        updateButtonHandler();

    }

    public void deleteButtonHandler(){
        System.out.println("inside HANDLER7  ==================================");
        deleteServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService();
            }
        });
    }

    public void updateButtonHandler(){
        System.out.println("inside HANDLER7  ==================================");
        updateServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateService();
            }
        });
    }

    public void deleteService() {
        System.out.println("TRYING TO DELETE =====================");
        HttpUtils.delete("/bookableService/" + selectedItemText, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                Toast.makeText
                        (getActivity(), "Service successfully deleted", Toast.LENGTH_SHORT)
                        .show();

                //getServices(view);

            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("#################################");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    System.out.println("-----------------------------------------------");
                    error += errorResponse.get("message").toString();
                    System.out.println("inside try : "+error);
                } catch (JSONException e) {
                    error += e.getMessage();
                    System.out.println("catch : "+error);
                }

            }
        });
    }


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

                //getServices(view);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    System.out.println("-----------------------------------------------");
                    error += errorResponse.get("message").toString();
                    System.out.println("inside try : "+error);
                } catch (JSONException e) {
                    error += e.getMessage();
                    System.out.println("catch : "+error);
                }

            }
            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("#################################");
            }
        });
    }

}