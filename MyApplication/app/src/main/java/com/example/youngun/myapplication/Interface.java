package com.example.youngun.myapplication;

/**
 * Created by youngun on 2017-10-26.
 */
public class Interface {
    //Server의 onPostExcecute내 정보를 빼서 HomeActivity에 넣기위한 interface
    public interface AsyncResponse {
        void processFinish(String output);
    }
}
