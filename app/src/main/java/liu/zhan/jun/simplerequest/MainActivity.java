package liu.zhan.jun.simplerequest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private ProgressDialog dialog;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        long id=Thread.currentThread().getId();
        Log.i(TAG, "onCreate: currentThreadId="+id);

        textView= (TextView) findViewById(R.id.textView);
        button= (Button) findViewById(R.id.btn_request);
        RxView.clicks(button).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                request();
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ComeRequest.request.unsubcribe();
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
        r.addHeads(head,true);
        r.requestPost("http://192.168.1.101:8080/DemoServlet/HelloWorldServlet",student, new RequestCallBack<GirlFriend>() {
            @Override
            public void success(GirlFriend result) {
                Log.i(TAG, "success: ============================");
                textView.setText(result.getName()+" age="+result.getAge());
            }

            @Override
            public void failure(ComeRequest.NetThrowable error) {
                Log.i(TAG, "failure: ========"+error.getRequestString()+"===status="+error.getStatus()+"|====message"+error.getMessage());
                dialog.cancel();
            }

            @Override
            public void finish() {
                dialog.cancel();
            }

            @Override
            public void before() {
                Log.i(TAG, "before: ======================================");
                dialog=new ProgressDialog(mContext);
                dialog.setMessage("正在加载中...");
                long id=Thread.currentThread().getId();
                Log.i(TAG, "before: TID="+id);
                dialog.show();
            }
        });
    }


}
