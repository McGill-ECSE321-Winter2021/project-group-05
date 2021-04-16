package ca.mcgill.ecse321.repairshop_android.Activities.Customer;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;



import ca.mcgill.ecse321.repairshop_android.Activities.MainActivity;

import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class ProfileFragment extends Fragment {

    private String error = null;
    private Button updateButton;
    private Button deleteButton;
    private Button logoutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        setTextField(view);
        setView(view);

        updateButtonHandler();
        deleteButtonHandler();
        logoutButtonHandler();

    }

    public void setView(View view){
        updateButton = view.findViewById(R.id.updateButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        logoutButton = view.findViewById(R.id.logoutButton);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setTextField(View view){

        TextView tvUserName = (TextView) view.findViewById(R.id.editTextUserName);

        TextView tvEmail = (TextView) view.findViewById(R.id.editTextEmailAddress);

        tvUserName.setText(RepairShopUtil.getLoginUserName());
        tvEmail.setText(RepairShopUtil.getLoginUserEmail());

    }

    public void updateButtonHandler(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccount();
            }
        });
    }

    public void logoutButtonHandler(){
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }




    public void deleteButtonHandler(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog();
            }
        });
    }


    public void logout(){
        goToLogin();
    }

    public void updateAccount(){

        error = "";

        TextView tvUserName = (TextView) this.getView().findViewById(R.id.editTextUserName);

         TextView tvEmail = (TextView) this.getView().findViewById(R.id.editTextEmailAddress);

         TextView tvPassword = (TextView) this.getView().findViewById(R.id.editTextPassword);

         TextView tvPasswordConfirm = (TextView) this.getView().findViewById(R.id.editTextPasswordConfirm);

        /**
         * throw error if the password != confirm password is the same
         */
        if (tvUserName.getText()=="" ){
            Toast.makeText
                    (getActivity(), "please enter the new/old username", Toast.LENGTH_SHORT)
                    .show();
        }
        else if (tvEmail.getText()==""){
            Toast.makeText
                    (getActivity(), "please enter the new/old email", Toast.LENGTH_SHORT)
                    .show();
        }
        else if (tvPassword.getText()=="" ){
            Toast.makeText
                    (getActivity(), "please enter the new/old password", Toast.LENGTH_SHORT)
                    .show();
        }
        else if(tvPasswordConfirm.getText()=="" ){
            Toast.makeText
                    (getActivity(), "please enter the new/old passwird", Toast.LENGTH_SHORT)
                    .show();
        }


        if (tvPassword==null || tvPasswordConfirm ==null ||
                tvUserName==null ||tvEmail ==null ){
            Toast.makeText
                    (getActivity(), "Error : Please fill in all fields", Toast.LENGTH_SHORT)
                    .show();
        }
        /**
         * throw error if the password != confirm password is the same
         */
        else if (!tvPassword.getText().toString().equals(tvPasswordConfirm.getText().toString())){

            Toast.makeText
                    (getActivity(), "Error : Confirm password doesn't match with the password", Toast.LENGTH_SHORT)
                    .show();
        }
        else {

            RequestParams requestParams = new RequestParams();
            requestParams.add("username",tvUserName.getText().toString());
            requestParams.add("oldEmail",RepairShopUtil.getLoginUserEmail());
            requestParams.add("newEmail",tvEmail.getText().toString());
            requestParams.add("password",tvPassword.getText().toString());
            /**
             * update customer account
             */
            if (RepairShopUtil.getUserType().equals("customer")){

                HttpUtils.put("person/customer/"+ RepairShopUtil.getLoginUserEmail(),requestParams, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                        try {


                            // set the current user with updated account info
                            RepairShopUtil.setCurrentUser(tvUserName.getText().toString(),tvEmail.getText().toString(), "customer");

                            // reset the password field
                            tvPassword.setText("");
                            tvPasswordConfirm.setText("");
                            Toast.makeText
                                    (getActivity(), "Account has been updated successfully", Toast.LENGTH_SHORT)
                                    .show();

                        } catch (Exception e) {
                            error += e.getMessage();
                            Toast.makeText
                                    (getActivity(), "Failed to set the current user because of:\n"+error, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers,  Throwable throwable, JSONObject errorResponse) {

                    try {
                        error += errorResponse.get("message").toString();
                        Toast.makeText
                                (getActivity(), "Error : couldn't update account because of:\n" + error, Toast.LENGTH_SHORT)
                                .show();
                        System.out.println("inside failure");
                    }
                    catch (JSONException e){
                        error += e.getMessage();
                        Toast.makeText
                                (getActivity(), "Error : couldn't update account because of:\n" + error, Toast.LENGTH_SHORT)
                                .show();
                    }
                    }

                });

            }
            /**
             * update admin account
             */
            else if (RepairShopUtil.getUserType().equals("admin")){



                HttpUtils.put("person/administrator/"+ RepairShopUtil.getLoginUserEmail(),requestParams, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //refreshErrorMessage();

                        try {
                            // reset the password field
                            tvPassword.setText("");
                            tvPasswordConfirm.setText("");

                            // set the current user with updated account info
                            RepairShopUtil.setCurrentUser(tvUserName.getText().toString(),tvEmail.getText().toString(), "customer");

                            Toast.makeText
                                    (getActivity(), "Success : Account has been updated successfully", Toast.LENGTH_SHORT)
                                    .show();

                        } catch (Exception e) {

                            error += e.getMessage();
                            Toast.makeText
                                    (getActivity(), "Error : fail to set the current user because of:\n"+error, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {

                            error += errorResponse.get("message").toString();
                        } catch (JSONException e) {
                            error += e.getMessage();
                            Toast.makeText
                                    (getActivity(), "Error : couldn't update account because of:\n" + error, Toast.LENGTH_SHORT)
                                    .show();
                        }
                        //refreshErrorMessage();
                    }

                });

            }

            /**
             * update technician account
             */
            else if (RepairShopUtil.getUserType().equals("technician")){



                HttpUtils.put("person/technicians/"+ RepairShopUtil.getLoginUserEmail(),requestParams, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //refreshErrorMessage();

                        try {
                            // reset the password field
                            tvPassword.setText("");
                            tvPasswordConfirm.setText("");

                            // set the current user with updated account info
                            RepairShopUtil.setCurrentUser(tvUserName.getText().toString(),tvEmail.getText().toString(), "customer");

                            Toast.makeText
                                    (getActivity(), "Success : Account has been updated successfully", Toast.LENGTH_SHORT)
                                    .show();

                        } catch (Exception e) {
                            error += e.getMessage();
                            Toast.makeText
                                    (getActivity(), "Error : fail to set the current user because of:\n"+error, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {

                            error += errorResponse.get("message").toString();
                        } catch (JSONException e) {
                            error += e.getMessage();
                            Toast.makeText
                                    (getActivity(), "Error : Couldn't update account because of:\n" + error, Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }

                });

            }

        }

    }

    public void deleteAccount(){
        Log.e("tag2","deleting the account");
        // DELETE CUSTOMER ACCOUNT
        switch (RepairShopUtil.getUserType()) {
            case "customer":

                HttpUtils.delete("person/customer/" + RepairShopUtil.getLoginUserEmail(), new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                        // GO OT THE LOGIN PAGE
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {

                            error += errorResponse.get("message").toString();
                        } catch (JSONException e) {
                            error += e.getMessage();
                            Toast.makeText
                                    (getActivity(), "Error : Couldn't update account because of:\n" + error, Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }

                });

                break;


            // DELETE TECHNICIAN ACCOUNT
            case "technician":

                HttpUtils.delete("person/technicians/" + RepairShopUtil.getLoginUserEmail(), new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //refreshErrorMessage();

                        // GO OT THE LOGIN PAGE
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {

                            error += errorResponse.get("message").toString();
                        } catch (JSONException e) {
                            error += e.getMessage();
                            Toast.makeText
                                    (getActivity(), "Error : Couldn't update account because of:\n" + error, Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }

                });

                break;


            // DELETE ADMIN ACCOUNT
            case "admin":

                HttpUtils.delete("person/administrator/" + RepairShopUtil.getLoginUserEmail(), new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                        // GO OT THE LOGIN PAGE
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {

                            error += errorResponse.get("message").toString();
                        } catch (JSONException e) {
                            error += e.getMessage();
                            Toast.makeText
                                    (getActivity(), "Error : Couldn't update account because of:\n" + error, Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }

                });


                break;
        }

    }

    // GO TO LOGIN PAGE
    private void goToLogin(){
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        startActivity(intent);

    }

    // WARNING DIALOG
    protected void onCreateDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setTitle("Warning: Account Deletion");
        alertDialogBuilder.setMessage("Delete account is irreversible. Are you sure to proceed?");

        alertDialogBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                deleteAccount();
                goToLogin();
            }
        });

        alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // DO NOTHING
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }





}

