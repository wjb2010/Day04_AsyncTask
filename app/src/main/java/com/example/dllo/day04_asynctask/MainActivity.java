package com.example.dllo.day04_asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //进度条提示
    private ProgressDialog progressDialog;
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog=new ProgressDialog(this);

        pb = (ProgressBar) findViewById(R.id.pb);

        //在使用异步任务类的时候我们要调用的方法就是execute;
        //传入的值就是我们定义泛型时候的第一个参数:100,400,500对应Integer[]
        new MyAsyncTack().execute(100,400,500);




    }


    /**
     * params 这个泛型代表我们传入参数的类型
     * Progress 代表我们用于更新UI界面的单位参数
     *          如果不需要对UI进行操作,我们可以将此泛型设置为void
     * Result 代表最后的结果的类型
     *
     *
     */
    class MyAsyncTack extends AsyncTask<Integer,Integer,Boolean>{
        public   int i=1;
        /**
         * 这是一个准备工作的方法,
         * 我们一般进行某些组件或者对象的初始化操作
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //显示进度小圈圈

            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("更新");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.show();
        }


        /**
         * 此方法是在子线程中执行的一些耗时操作,也是我们异步任务的核心操作
         *
         * @param params 这个方法形参是我们定义的第一个泛型
         *               Integer...:Integer类型的数组,这个数组的长度是不确定的
         * @return 返回值类型 ,是我们定义的第三个参数
         * 当doInBackground 方法执行结束之后 返回的结果会传送到onPostExcute中
         * 如果说我们想对UI界面进行某些操作的话,必须调用publishProgress()方法
         * 如果调用publishProgress()方法后,那么我们就会执行onProgressUpdate方法
         * 执行多少次publishProgress方法
         * 那么我们的onProgressUpdate方法就会执行多少次
         */
        @Override
        protected Boolean doInBackground(Integer... params) {

            int loading=0;
            while (loading<params[0]){



                publishProgress(loading);
              //  publishProgress(loading,400);

                loading++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }


        /**
         * 由publishProgress方法触发
         * 这个方法运行在主线程中,所以可以进行UI操作
         * 这个方法的作用就是对我们的进度进行更新
         * @param values 这个形参就是我们定义的第二个泛型
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //progressDialog.setMessage("正在下载"+values[0]+"%");
            progressDialog.setProgress(i++);
            pb.incrementProgressBy(1);

        }


        /**
         * 这个方法就是对我们最后运行结果的回调
         * 当我们的异步任务完成了就会执行这个方法
         * @param aBoolean 这个形参是我们最后定义的第三个泛型
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                //progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
