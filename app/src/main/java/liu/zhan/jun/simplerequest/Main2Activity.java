package liu.zhan.jun.simplerequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import liu.zhan.jun.simplerequest.comerequest.ComeRequest;
import liu.zhan.jun.simplerequest.comerequest.RequestCallBack;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {

    private  final String TAG = "LOGI";
    private  final String CLIENT_KET_PASSWORD = "654321@@m";
    private InputStream ins;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView2= (TextView) findViewById(R.id.textView2);
        try {
            ins=getAssets().open("server.pfx");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //添加证书，全局添加，在application中添加
        ComeRequest.request.addVert(ins,"654321@@m","192.168.1.100");
    }


    public void httpsRequest(View view)  {
        ComeRequest.request
                .requestGet("https://192.168.1.100:7474/", null, new RequestCallBack<String>() {
                    @Override
                    public void before() {

                        textView2.setText("正在发送请求");Log.i(TAG, "before: ");
                    }

                    @Override
                    public void success(String result) {
                        textView2.setText(result);
                        Log.i(TAG, "success: result="+result);
                    }

                    @Override
                    public void failure(ComeRequest.NetThrowable error) {
                        Log.i(TAG, "failure: "+error.getMessage());
                    }

                    @Override
                    public void finish() {
                        Log.i(TAG, "finish:");
                    }
                });

    }

    public void httpsPost(View view){
        ComeRequest.request
                .requestPost("https://192.168.1.100:7474/", null, new RequestCallBack<String>() {
                    @Override
                    public void before()
                    {
                        textView2.setText("正在发送请求");
                        Log.i(TAG, "before: ");
                    }

                    @Override
                    public void success(String result) {
                        textView2.setText(result);
                        Log.i(TAG, "success: result="+result);
                    }

                    @Override
                    public void failure(ComeRequest.NetThrowable error) {
                        Log.i(TAG, "failure: "+error.getMessage());
                    }

                    @Override
                    public void finish() {
                        Log.i(TAG, "finish:");
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (ins!=null)
            ins.close();
            ins=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pfxRequest(View view){

        try {

            HostnameVerifier hostnameVerifier=new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    //验证服务器证书域名

                    Log.i(TAG, "verify: hostNmae="+hostname+"===sesseion="+session.toString());
                    if (hostname.equals("192.168.1.100")){
                        return true;
                    }
                    return false;
                }
            };
            InputStream ins=getAssets().open("server.pfx");

            char[] passwords = CLIENT_KET_PASSWORD.toCharArray(); // Any password will work.
            KeyStore keyStore = newEmptyKeyStore(passwords);
            keyStore.load(ins,passwords);

            // Use it to build an X509 trust manager.
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, passwords);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }

            SSLContext ssContext = SSLContext.getInstance("TLS");
            ssContext.init(keyManagerFactory.getKeyManagers(),trustManagers,null);

            OkHttpClient.Builder clientbuilder=new OkHttpClient.Builder();
            OkHttpClient client=clientbuilder.hostnameVerifier(hostnameVerifier)
                    .sslSocketFactory(ssContext.getSocketFactory(), (X509TrustManager) trustManagers[0])
                    .build();
            String url="https://192.168.1.100:7474/";
            final Request request = new Request.Builder().url(url).get().build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG, "onFailure: 失败"+e.getMessage()+"====");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i(TAG, "onResponse: "+response.body().string());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            // By convention, 'null' creates an empty key store.
            keyStore.load(null, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

}
