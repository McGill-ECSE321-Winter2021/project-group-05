package ca.mcgill.ecse321.repairshop_android.Activities.Utility;

import android.view.View;
import android.widget.TextView;


public class RepairShopUtil {

    public static String loginUserName="";
    public static String loginUserEmail="";


    public static void refreshErrorMessage(View view, int id, String error) {
        // set the error message
        TextView tvError = (TextView) view.findViewById(id);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    public static void setCurrentUser(String username, String email) {
        loginUserName = username;
        loginUserEmail = email;
    }
}
