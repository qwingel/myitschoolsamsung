package com.example.myitschoolsamsung;

import android.view.View;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RequestToServe {

    public static final String SQurl = "http://192.168.43.40";
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

    public interface UserService {
        @POST("/registration")
        Call<ResponseRegistrationMessage> registration(@Body RegistrationRequest registrationRequest);
        @POST("/login")
        Call<ResponseLoginMessage> login(@Body LoginRequest loginRequest);
        @POST("/code")
        Call<ResponseCodeMessage> code(@Body CodeRequest codeRequest);
        @POST("/tickets")
        Call<ResponseTicketsMessage> tickets(@Body TicketsRequest ticketsRequest);
        @GET("/logout")
        Call<ResponseMessage> logout();
    }
}
