package ca.mcgill.ecse321.repairshop_android.Activities.Customer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import ca.mcgill.ecse321.repairshop_android.Activities.Admin.AdminHomeFragment;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import cz.msebera.android.httpclient.Header;

public class CancelAppointmentDialogFragment extends DialogFragment {
    public CancelAppointmentDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static CancelAppointmentDialogFragment newInstance(String title, int appointmentId) {
        CancelAppointmentDialogFragment frag = new CancelAppointmentDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("appointmentId", appointmentId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        int appointmentId = getArguments().getInt("appointmentId");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Are you sure?");
        alertDialogBuilder.setPositiveButton("YES",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                Log.d("cancel", "cancel" + appointmentId);      //TODO: handle appointment cancellation
                cancelAppointment(appointmentId);
            }
        });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null ) {
                    dialog.dismiss();
                }
            }
        });
        return alertDialogBuilder.create();
    }

    private void cancelAppointment(int appointmentId){
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", appointmentId);
        HttpUtils.delete("/appointment", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getActivity(), "Your appointment was successfully cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if(statusCode == 200) {
                    // TODO toast makes problem here
                }
            }
        });
    }

}
