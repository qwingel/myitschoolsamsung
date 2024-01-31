package com.example.myitschoolsamsung;

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
    public static class RegistrationRequest{
        public RegistrationRequest(String phone, String data){
            this.phone = phone;
            this.data = data;
        }
        String phone, data;
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
    public interface UserService {
        @POST("/registration")
        Call<ResponseMessage> registration(@Body RegistrationRequest registrationRequest);
        @POST("/login")
        Call<ResponseLoginMessage> login(@Body LoginRequest loginRequest);
        @POST("/code")
        Call<ResponseCodeMessage> code(@Body CodeRequest codeRequest);
        @GET("/logout")
        Call<ResponseMessage> logout();
    }
}
