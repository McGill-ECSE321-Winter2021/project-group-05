package ca.mcgill.ecse321.repairshop_android.Activities.Customer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

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
}
