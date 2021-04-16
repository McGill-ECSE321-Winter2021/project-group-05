package ca.mcgill.ecse321.repairshop_android.Activities.Customer;


import android.app.AlertDialog;
import android.app.Dialog;
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

import ca.mcgill.ecse321.repairshop_android.Activities.Admin.AdminMainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.MainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class ProfileFragment extends Fragment {

    private String error = null;
    private Button updateButton;
    private Button deleteButton;

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

    }

    public void setView(View view){
        updateButton = view.findViewById(R.id.updateButton);
        deleteButton = view.findViewById(R.id.deleteButton);
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
                updateAccount(view);
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


    public void updateAccount(View view){

        error = "";

        final TextView tvUserName = (TextView) view.findViewById(R.id.editTextUserName);

        final TextView tvEmail = (TextView) view.findViewById(R.id.editTextEmailAddress);

        final TextView tvPassword = (TextView) view.findViewById(R.id.editTextPassword);

        final TextView tvPasswordConfirm = (TextView) view.findViewById(R.id.editTextPasswordConfirm);

        /**
         * throw error if the password != confirm password is the same
         */
        if (!tvPassword.getText().toString().equals(tvPasswordConfirm.getText().toString())){

            Toast.makeText
                    (getActivity(), "Error : Confirm password doesn't match with the password", Toast.LENGTH_SHORT)
                    .show();
        }
        else {

            RequestParams requestParams = new RequestParams();
            requestParams.add("username",tvUserName.getText().toString());
            requestParams.add("email",tvEmail.getText().toString());
            requestParams.add("password",tvPassword.getText().toString());
            /**
             * update customer account
             */
            if (RepairShopUtil.getUserType().equals("customer")){

                HttpUtils.put("person/customer/"+ RepairShopUtil.getLoginUserEmail(),requestParams, new JsonHttpResponseHandler() {
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
                        //refreshErrorMessage();
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
                        //refreshErrorMessage();
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
                        //refreshErrorMessage();
                    }

                });

                break;


            // DELETE ADMIN ACCOUNT
            case "admin":

                HttpUtils.delete("person/administrator/" + RepairShopUtil.getLoginUserEmail(), new RequestParams(), new JsonHttpResponseHandler() {
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
                        //refreshErrorMessage();
                    }

                });


                break;
        }

    }


    protected Dialog onCreateDialog(){
                Log.e("tag1","create new dialog");
                return new AlertDialog.Builder(this.getContext())

                        .setTitle("Warning")
                        .setMessage("Delete account is irreversible, are you sure to proceed?")
                        .setPositiveButton("DELETE",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteAccount();
                                    }
                                }
                        )
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // do nothing
                                    }
                                }
                        ).create();

    }


}

