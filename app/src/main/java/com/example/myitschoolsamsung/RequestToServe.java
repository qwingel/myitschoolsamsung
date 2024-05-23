package com.example.myitschoolsamsung;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RequestToServe {

    public static final String SQurl = "http://antarktida.pythonanywhere.com";
    public static List<String> ids = new ArrayList<String>();
    public static String[] getIds() {
        String[] s1 = new String[ids.size()];
        for(int i = 0; i < ids.size(); i++){
            s1[i] = ids.get(i);
        }
        return s1;
    }
    public static void addId(String id){ ids.add(id); }
    public static class ResponseMessage{
        public String status, message;

        @Override
        public String toString() {
            return "ResponseMessage{" +
                    "status='" + status + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
    public static class ResponseLoginMessage{
        public String status;
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' + '}';
        }
    }

    public static class ResponseCodeMessage{
        public String status, message, user_data;
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' +
                    ", message='" + message + '\'' +
                    ", user_data='" + user_data + '\'' +
                    '}';
        }
    }
    public static class ResponseRegistrationMessage{
        public String status, message;
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    public static class ResponseTicketsMessage{
        public String status, message;
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' +
                    ", message='" + message+ '\'' +
                    '}';
        }
    }

    public static class ResponseDelayedTickets{
        public String status, message;
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    public static class ResponseUpdateProfile{
        public String status, message;
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status'"+ status +'\'' +
                    ", message='" + message +'\'' +
                    '}';
        }
    }

    public static class RegistrationRequest{
        public RegistrationRequest(String phone, String password){
            this.phone = phone;
            this.password = password;
        }
        String phone, password;
    }

    public static class LoginRequest{
        public LoginRequest(String phone){
            this.phone = phone;
        }
        String phone;
    }

    public static class CodeRequest{
        public CodeRequest(String phone, String code) { this.phone = phone; this.code = code;}
        String phone, code;
    }

    public static class TicketsRequest{
        public TicketsRequest(String fromWhere, String toWhere, String date, String filters) {
            this.fromWhere = fromWhere;
            this.toWhere = toWhere;
            this.date = date;
            this.filters = filters;
        }
            String fromWhere, toWhere, date, filters;
    }

    public static class DelayedTicketsRequest{
        public DelayedTicketsRequest(String id){
            this.id = id;
        }
        String id;
    }

    public static class UpdateProfileRequest{
        public UpdateProfileRequest(String phone, String name, String surname, String passS, String passN){
            this.name = name;
            this.surname = surname;
            this.passS = passS;
            this.passN = passN;
            this.phone = phone;
        }
        String phone, name, surname, passS, passN;
    }

    public interface UserService {
        @POST("/registration")
        Call<ResponseRegistrationMessage> registration(@Body RegistrationRequest registrationRequest);
        @POST("/login")
        Call<ResponseLoginMessage> login(@Body LoginRequest loginRequest);
        @POST("/code")
        Call<ResponseCodeMessage> code(@Body CodeRequest codeRequest);
        @POST("/tickets")
        Call<ResponseTicketsMessage> tickets(@Body TicketsRequest ticketsRequest);
        @POST("/dtickets")
        Call<ResponseDelayedTickets> dtickets(@Body DelayedTicketsRequest delayedTicketsRequest);
        @POST("/update")
        Call<ResponseUpdateProfile> updateProfile(@Body UpdateProfileRequest updateProfileRequest);
        @GET("/logout")
        Call<ResponseMessage> logout();
    }
}
