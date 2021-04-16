package ca.mcgill.ecse321.repairshop_android.Activities.Admin;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class ServiceFragment extends Fragment {


    private Button createServiceButton;
    private EditText tv_service_name = null;
    private EditText tv_service_cost = null;
    private EditText tv_service_duration = null;
    private TextView tvError = null;

    public ServiceFragment() {
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
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view);
    }


    public void setView(View view) {
        createServiceButton = view.findViewById(R.id.CreateServiceButton);
        tv_service_name = (EditText) view.findViewById(R.id.newServiceName);
        tv_service_cost = (EditText) view.findViewById(R.id.newServiceCost);
        tv_service_duration = (EditText) view.findViewById(R.id.newServiceDuration);
        tvError = (TextView) view.findViewById(R.id.errorService);
        createServiceButtonHandler();
    }

    /**
     * sets create button on-click function
     */
    public void createServiceButtonHandler(){
        createServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createService(view);
            }
        });
    }


    /**
     * creates a new service when create button is pressed with all fields filled
     */
    public void createService(final View view){

        RequestParams requestParams = new RequestParams();
        requestParams.add("serviceName", tv_service_name.getText().toString());
        requestParams.add("serviceCost",tv_service_cost.getText().toString());
        requestParams.add("serviceDuration",tv_service_duration.getText().toString());

        HttpUtils.post("/bookableService/app", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                tv_service_name.setText("");
                tv_service_cost.setText("");
                tv_service_duration.setText("");
                Toast.makeText
                        (getActivity(), "Service successfully created", Toast.LENGTH_SHORT)
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
            }
        });
    }
}