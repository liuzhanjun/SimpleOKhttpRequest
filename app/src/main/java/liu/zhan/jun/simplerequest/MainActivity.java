package liu.zhan.jun.simplerequest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import liu.zhan.jun.simplerequest.comerequest.ComeRequest;
import liu.zhan.jun.simplerequest.comerequest.RequestCallBack;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.ConnectInterceptor;

public class MainActivity extends AppCompatActivity {
    public static String TAG="LOGI";

    private TextView textView;
    private Context mContext;
//    private ProgressDialog dialog;
    private Button button;
    private Button button_get;
    private View button2;
    private Button btn_downLoad;
    private Button cancel_button;
    private Button cancel_button_get;
    private View cancel_button2;
    private Button cancel_btn_downLoad;
    String Request1="request1";
    String Request_get="request1_get";
    String Request_Upload="request1_Upload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        long id=Thread.currentThread().getId();
        Log.i(TAG, "onCreate: currentThreadId="+id);

        textView= (TextView) findViewById(R.id.textView);
        button= (Button) findViewById(R.id.btn_request);
        button_get= (Button) findViewById(R.id.btn_request_get);
        button2=findViewById(R.id.btn_upload);
        btn_downLoad= (Button) findViewById(R.id.cancel_btn_download);
        cancel_button= (Button) findViewById(R.id.cancel_btn_request);
        cancel_button_get= (Button) findViewById(R.id.cancel_btn_request_get);
        cancel_button2=findViewById(R.id.cancel_btn_upload);
        cancel_btn_downLoad= (Button) findViewById(R.id.btn_download);
        //post请求
        RxView.clicks(button).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                request();
            }
        });
        /**
         * gen请求
         */
        RxView.clicks(button_get).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                request_get();
            }
        });
        RxView.clicks(button2).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object s) throws Exception {
                uploadFile();
            }
        });
        RxView.clicks(btn_downLoad).subscribe(new Consumer<Object>() {

            @Override
            public void accept(@NonNull Object s) throws Exception {
                downLoad();
            }
        });
        RxView.clicks(cancel_button).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                ComeRequest.request.cancel(Request1);
            }
        });
        RxView.clicks(cancel_button_get).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                ComeRequest.request.cancel(Request_get);
            }
        });
        RxView.clicks(cancel_button2).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                ComeRequest.request.cancel(Request_Upload);

            }
        });

    }

    public void next(View view){

        Intent intent=new Intent(this,Main2Activity.class);
        startActivity(intent);
    }

    private void request_get() {
        Student student=new Student();
        student.setID(1000);
        student.setAge(39);
        student.setName("赵云");
        ComeRequest r = ComeRequest.request.getInstance();
        HashMap<String,String> globHead=new HashMap<>();
        globHead.put("ggggggggg","sssss");
        //添加每次请求都会有的head
        r.setGlobalHeads(globHead);


        HashMap head=new HashMap<String,String>();
        r.setTag(Request_get);
        r.requestGet("http://www.eclipse.org/ajdt/downloads/#43zips",null, new RequestCallBack<String>() {
            @Override
            public void success(String result) {
                Log.i(TAG, "success: ============================");
                textView.setText(result);
            }

            @Override
            public void failure(ComeRequest.NetThrowable error) {
                Log.i(TAG, "failure: ========"+error.getRequestString()+"===status="+error.getStatus()+"|====message"+error.getMessage());
//                dialog.cancel();
            }

            @Override
            public void finish() {
                //dialog.cancel();
            }

            @Override
            public void before() {
//                Log.i(TAG, "before: ======================================");
//                dialog=new ProgressDialog(mContext);
//                dialog.setMessage("正在加载中...");
//                long id=Thread.currentThread().getId();
//                Log.i(TAG, "before: TID="+id);
//                dialog.show();
            }
        });
    }

    /**
     * 下载
     */
    private void downLoad() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        ComeRequest.request.unsubcribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ComeRequest.request.unsubcribe();
    }

    private void uploadFile(){
        Student student=new Student();
        student.setID(1000);
        student.setAge(39);
        student.setName("赵云");
        File file=new File(Environment.getExternalStorageDirectory()+"/Download");
        Log.i(TAG, "uploadFile: file===="+file.exists());
        ComeRequest.request
                .setTag(Request_Upload)
                .prepareUpload()
                .addFile("mimg1",new File(file.getAbsolutePath()+"/22.jpg"))
                .addFile("mimg2",new File(file.getAbsolutePath()+"/33.jpg"))
                .addFile("mov",new File(Environment.getExternalStorageDirectory()+"/Movies/yuv_cuc_ieschool.yuv"))
                .upLoadFile("http://192.168.1.101:8080/DemoServlet/HelloWorldServlet", student, new RequestCallBack<String>() {
                    @Override
                    public void before() {
                        Log.i(TAG, "before: 开始上传");
                    }

                    @Override
                    public void success(String result) {
                        Log.i(TAG, "success: ========"+result);
                    }

                    @Override
                    public void failure(ComeRequest.NetThrowable error) {
                        Log.i(TAG, "failure: ==========="+error.getMessage());
                    }

                    @Override
                    public void finish() {
                        Log.i(TAG, "finish: 请求完成");
                    }

                    @Override
                    public void progress(long currentByte, long countByte) {
                        long id=Thread.currentThread().getId();
                        Log.i(TAG, "progress: currentByte="+currentByte+"===progress="+countByte+"threadID="+id);
                        DecimalFormat format=new DecimalFormat("0.00");
                        double bfb=(1.0*currentByte/countByte)*100;
                        Log.i(TAG, "progress: bfb===="+bfb);
                        String sult=format.format(bfb);
                        textView.setText(sult+"%");
                    }
                });
    }






    private void request() {
        Student student=new Student();
        student.setID(1000);
        student.setAge(39);
        student.setName("赵云");
        ComeRequest r = ComeRequest.request.getInstance();
        HashMap<String,String> globHead=new HashMap<>();
        globHead.put("ggggggggg","sssss");
        //添加每次请求都会有的head
        r.setGlobalHeads(globHead);


        HashMap head=new HashMap<String,String>();
        head.put("feel","yes");
        r.setTag(Request1);
        r.addHeads(head,true);
        r.requestPost("http://192.168.1.101:8080/DemoServlet/RequestFriendServlet",student, new RequestCallBack<GirlFriend>() {
            @Override
            public void success(GirlFriend result) {
                Log.i(TAG, "success: ============================");
                textView.setText(result.getName()+" age="+result.getAge());
            }

            @Override
            public void failure(ComeRequest.NetThrowable error) {
                Log.i(TAG, "failure: ========"+error.getRequestString()+"===status="+error.getStatus()+"|====message"+error.getMessage());
//                dialog.cancel();
            }

            @Override
            public void finish() {
//                dialog.cancel();
            }

            @Override
            public void before() {
//                Log.i(TAG, "before: ======================================");
//                dialog=new ProgressDialog(mContext);
//                dialog.setMessage("正在加载中...");
//                long id=Thread.currentThread().getId();
//                Log.i(TAG, "before: TID="+id);
//                dialog.show();
            }
        });
    }


}
