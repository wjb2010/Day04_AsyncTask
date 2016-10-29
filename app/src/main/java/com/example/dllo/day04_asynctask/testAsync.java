package com.example.dllo.day04_asynctask;

import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by dllo on 16/10/29.
 */

public class testAsync extends AppCompatActivity{


    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        progressDialog=new ProgressDialog(this);
        new MyAsync().execute(100);
    }












    class MyAsync extends AsyncTask<Integer,Integer,Boolean>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            int i=0;
            while (i<params[0]){

                publishProgress(i);


                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                i++;
            }




            return true;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressDialog.setMessage("正在下载:"+values[0]+"%");


        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            Toast.makeText(testAsync.this, "下载完成", Toast.LENGTH_SHORT).show();
        }
    }


    class MyAdapter extends AnimatorListenerAdapter{





    }

}



