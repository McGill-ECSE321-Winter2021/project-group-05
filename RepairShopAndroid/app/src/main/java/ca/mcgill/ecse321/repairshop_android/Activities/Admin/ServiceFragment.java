package ca.mcgill.ecse321.repairshop_android.Activities.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class ServiceFragment extends Fragment {

    private String error = null;

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
    }


    private void refreshErrorMessage(View view) {
        // set the error message
        TextView tvError = (TextView) view.findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }


    public void createService(final View v){

        error="";
        final TextView tv_service_name = (TextView) v.findViewById(R.id.newServiceName);
        final TextView tv_service_cost = (TextView) v.findViewById(R.id.newServiceCost);
        final TextView tv_service_duration = (TextView) v.findViewById(R.id.newServiceDuration);

        RequestParams requestParams = new RequestParams();
        requestParams.add("newServiceName", tv_service_name.getText().toString());
        requestParams.add("newServiceCost",tv_service_cost.getText().toString());
        requestParams.add("newServiceDuration",tv_service_duration.getText().toString());

        HttpUtils.post("/bookableService", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage(v);
                tv_service_name.setText("");
                tv_service_cost.setText("");
                tv_service_duration.setText("");
                // Notify with successful message
                Toast.makeText
                        (getActivity(), "Service successfully created", Toast.LENGTH_SHORT)
                        .show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage(v);
            }

        });
    }
}