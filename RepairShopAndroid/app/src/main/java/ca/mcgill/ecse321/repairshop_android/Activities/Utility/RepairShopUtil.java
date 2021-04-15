package ca.mcgill.ecse321.repairshop_android.Activities.Utility;


public class RepairShopUtil {

    public static String loginUserName="";
    public static String loginUserEmail="";
    public static String userType="";


    public static void setCurrentUser(String username, String email, String loginUserType) {
        loginUserName = username;
        loginUserEmail = email;
        userType = loginUserType;
    }
}
