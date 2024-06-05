package com.example.myitschoolsamsung;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    Button Logout, toTickets, telegram, editProfile, confirmPassForEdit, basket;
    EditText name, surname, document, et_Password;
    TextView profileNum;
    SharedPreferences sPref, tsPref;
    boolean isEditing = false;
    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().hide();

        sPref = getSharedPreferences("account", Context.MODE_PRIVATE);

        String userName = sPref.getString("name", null);
        String userSurname = sPref.getString("surname", null);
        String userPhone = sPref.getString("phone", null);
        String userPassword = sPref.getString("password", null);

        name = (EditText) findViewById(R.id.profileName); name.setText(userName);
        surname = (EditText) findViewById(R.id.profileSurname); surname.setText(userSurname);
        document = (EditText) findViewById(R.id.document);

        profileNum = (TextView) findViewById(R.id.profileNum);
        profileNum.setText("+" + userPhone);

        Logout = (Button) findViewById(R.id.logout);
        toTickets = (Button) findViewById(R.id.toTicketsActivity);
        telegram = (Button) findViewById(R.id.telegramBut); telegram.setText("");
        editProfile = (Button) findViewById(R.id.b_profileEdit);

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        final View customView = getLayoutInflater().inflate(R.layout.dialog_password, null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        confirmPassForEdit = (Button) customView.findViewById(R.id.confButForEdit);
        et_Password = (EditText) customView.findViewById(R.id.passconf);

        Logout.setOnClickListener(view -> {
            if (!isEditing) {
                this.getSharedPreferences("account", Context.MODE_PRIVATE).edit().clear().apply();
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                isEditing = false;
                name.setEnabled(false);
                surname.setEnabled(false);
                document.setEnabled(false);
                String[] passport = document.getText().toString().split(" ");
                String passS = passport[0];
                String passN = passport[1];
                String s_Name = name.getText().toString();
                String s_Surname = surname.getText().toString();
                SharedPreferences.Editor editor = sPref.edit();
                editor.putString("name", s_Name);
                editor.putString("surname", s_Surname);
                editor.putString("passport_series", passS);
                editor.putString("passport_number", passN);
                document.setText("**** ******");
                editor.apply();

                Logout.setText("Выйти из аккаунта");
                Logout.setTextColor(R.color.pr_red);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestToServe.SQurl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
                Call<RequestToServe.ResponseUpdateProfile> updateProfileRequestCall = userService.updateProfile(new RequestToServe.UpdateProfileRequest(userPhone, s_Name, s_Surname, passS, passN));
                updateProfileRequestCall.enqueue(new Callback<RequestToServe.ResponseUpdateProfile>() {
                    @Override
                    public void onResponse(Call<RequestToServe.ResponseUpdateProfile> call, Response<RequestToServe.ResponseUpdateProfile> response) {
                        if (response.body() != null){
                            if (response.body().status.equals("failed")){
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Серверу плохо",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestToServe.ResponseUpdateProfile> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        toTickets.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
        });

        telegram.setOnClickListener(view -> {
            Uri uriUrl = Uri.parse("https://t.me/girlsarethesame");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        });

        confirmPassForEdit.setOnClickListener(view -> {
            if(et_Password.getText().toString().equals(userPassword)){
                dialog.dismiss();
                isEditing = true;
                document.setText(sPref.getString("passport_series", null) + " " + sPref.getString("passport_number", null));
                name.setEnabled(true);
                surname.setEnabled(true);
                document.setEnabled(true);
                Logout.setText("Сохранить");
                Logout.setTextColor(R.color.pr_green);
            }
        });
        editProfile.setOnClickListener(view -> {
            if (customView.getParent() != null){
                ((ViewGroup)customView.getParent()).removeView(customView);
            }
            if(dialog.isShowing()) dialog.dismiss();
            dialog.show();
        });
    }
}
