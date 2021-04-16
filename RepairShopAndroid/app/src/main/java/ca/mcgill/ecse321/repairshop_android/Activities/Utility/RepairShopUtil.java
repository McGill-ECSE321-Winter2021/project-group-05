package ca.mcgill.ecse321.repairshop_android.Activities.Utility;

import android.view.View;
import android.widget.TextView;


public class RepairShopUtil {

    private static String loginUserName="";
    private static String loginUserEmail="";
    private static String userType="";


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

    public static void setCurrentUser(String username, String email, String loginUserType) {
        loginUserName = username;
        loginUserEmail = email;
        userType = loginUserType;
    }

    public static String getLoginUserName() {
        return loginUserName;
    }

    public static String getLoginUserEmail() {
        return loginUserEmail;
    }

    public static String getUserType() {
        return userType;
    }
}
