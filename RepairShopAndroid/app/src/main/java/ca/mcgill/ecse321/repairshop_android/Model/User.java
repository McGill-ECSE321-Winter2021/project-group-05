package ca.mcgill.ecse321.repairshop_android.Model;

import org.parceler.Parcel;

@Parcel
public class User {
    // fields must be package private
    String email;
    String password;

    // empty constructor needed by the Parceler library
    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }
}
