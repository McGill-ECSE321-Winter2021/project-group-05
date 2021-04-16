package ca.mcgill.ecse321.repairshop_android.Activities.Utility;


public class RepairShopUtil {

    private static String loginUserName="";
    private static String loginUserEmail="";
    private static String userType="";


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
