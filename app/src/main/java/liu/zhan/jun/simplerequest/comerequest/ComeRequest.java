package liu.zhan.jun.simplerequest.comerequest;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;



/**
 * Created by 刘展俊 on 2017/4/22.
 */

public enum  ComeRequest implements ComeRequestIn {
        request {
            @Override
            public ComeRequest getInstance() {
                return this;
            }
        };

    private final CompositeDisposable disposables = new CompositeDisposable();
    private Gson mGson;
    private Subscribe subscribe;

    public abstract ComeRequest getInstance();
    public static final int DEFAULT_MILLISECONDS = 60000;       //默认的超时时间
    private static final String PROTOCOL_CHARSET = "UTF-8";
    private static final String PROTOCOL_CONTENT_TYPE_JSON = String.format(
            "application/json; charset=%s", new Object[]{PROTOCOL_CHARSET});

    private static final String PROTOCOL_CONTENT_TYPE_FORM = String.format(
            "application/x-www-form-urlencoded; charset=%s",
            new Object[]{PROTOCOL_CHARSET});

    public static final String TAG="ComeRequest";
    private OkHttpClient client;
    private OkHttpClient.Builder okClientBuilder;
    private HashMap<String,String> GlobalHeads;
    private HashMap<String,String> CountHeads;
    private ArrayMap<String,File> upFiles;
    private Object tag;
    private ComeRequest() {
        okClientBuilder=new OkHttpClient.Builder();
        mGson=new Gson();

    }
    public OkHttpClient getClient(){
        if (client==null) {
            client = okClientBuilder.build();
        }

        return client;
    }





    /*
            全局设置heads
         */
    public void setGlobalHeads(HashMap<String, String> heads) {
        this.GlobalHeads = heads;

    }

    /**
     * 清空全局heads
     */
    public void clearGlobalHeads(){
        if (this.GlobalHeads!=null)
            this.GlobalHeads.clear();
        this.GlobalHeads=null;
    }

    /**
     *
     * @param heads
     * @param hasGlobal 是否保留全局head true保留 false 不保留
     */
    public void addHeads(HashMap<String, String> heads,boolean hasGlobal){
        CountHeads=new HashMap<String, String>();
        if (hasGlobal){
            if (GlobalHeads!=null) {
                this.CountHeads.putAll(GlobalHeads);
            }

        }
        this.CountHeads.putAll(heads);

    }
    public ComeRequest setTag(Object tag){
            this.tag=tag;
            return this;
    }

    /**
     *添加https的.pfx证件验证
     * @param is .pfx的输入流
     * @param password 密码
     * @param hostName 请求域名，用于校验服务器
     *                 只需要添加一次即可
     * @return
     */
    public void addVert(InputStream is, String password, final String hostName){
        try {
            //通过服务器域名验证,提高安全性
            HostnameVerifier hostnameVerifier=new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    //验证服务器证书域名

                    Log.i(TAG, "verify: hostNmae="+hostname+"===sesseion="+session.toString());
                    if (hostname.equals(hostName)){
                        return true;
                    }
                    return false;
                }
            };
            char[] passwords = password.toCharArray(); // Any password will work.
            KeyStore keyStore = newEmptyKeyStore(passwords);
            keyStore.load(is,passwords);
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

            okClientBuilder.hostnameVerifier(hostnameVerifier)
                    .sslSocketFactory(ssContext.getSocketFactory(), (X509TrustManager) trustManagers[0]);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
//            PKCS12
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private <T> void request(requestMode mode, String url, RequestModel model, final RequestCallBack<T> callback){
        Request.Builder builder= new Request.Builder();
        if (null!=tag){
            builder.tag(tag);
        }
        builder.url(url);
        switch (mode){
            case GET:
                get(builder);
                break;
            case POST:
                post(builder,model);
                break;
            case Upload:
                upload(builder,model,callback);
                break;
        }
        //添加header
        if (CountHeads!=null){
            Set<String> kes = CountHeads.keySet();
            Iterator<String> itera = kes.iterator();
            while (itera.hasNext()){
                String name=itera.next();
                builder.addHeader(name,CountHeads.get(name));
            }
        }
        Request request=builder.build();
        subscribe=new Subscribe();
        subscribe.setRequest(request);
        disposables.add(Observable.create(subscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        //这个方法在线程开始前执行，可以作为Progress显示
                        callback.before();
                    }
                })
                .map(new Function<String, T>() {
                    @Override
                    public T apply(@NonNull String s) throws Exception {
                        if (null==s){
                            return null;
                        }
                        if (String.class.toString().equals(
                                callback.getmType().toString())){
                            return (T) s;
                        }
                        T result=mGson.fromJson(s,callback.getmType());
                        return result;
                    }
                })
                //上面的在主线程
                .subscribeWith(new DisposableObserver<T>(){

                    @Override
                    public void onNext(T s) {
                        callback.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        error(e, callback);

                    }

                    @Override
                    public void onComplete() {
                        callback.finish();
                    }
                }));




    }

    private <T> void error(Throwable e, RequestCallBack<T> callback) {
        NetThrowable throwable;
        if (e instanceof NetThrowable){
            throwable= (NetThrowable) e;
        }else{
            throwable=new NetThrowable(e.getMessage());
            throwable.setStackTrace(e.getStackTrace());

        }
        callback.failure(throwable);
    }


    /**
     * 取消订阅在activity中onPause 或者onDestory调用即可，不能在cancle之前调用
     */
    public void unsubcribe(){
        disposables.clear();
    }

    public void cancel(Object tag){
        if (subscribe==null){
            return;
        }

        Call call=subscribe.getCall();
        if (null!=call){
            Object rTag=call.request().tag();
            if (tag.equals(rTag)){
                call.cancel();
            }
        }
    }
    /**
     *
     * @param builder
     */

    private void get(Request.Builder builder) {
        builder.get();

    }
    private void post(Request.Builder builder,RequestModel model) {
        MediaType type=MediaType.parse(PROTOCOL_CONTENT_TYPE_FORM);
        byte[] bodybyte = getBody(model);
        RequestBody body=null;
        if (bodybyte==null){
            body=RequestBody.create(type,"");
        }else {
             body = RequestBody.create(type,bodybyte);

        }
        builder.post(body);

    }

    private void upload(Request.Builder builder,RequestModel model,RequestCallBack callBack) {
        MultipartBody.Builder multbuild=new MultipartBody.Builder().setType(MultipartBody.FORM);
        ///                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + fileName + "\""),
//                        RequestBody.create(MEDIA_TYPE_PNG, file))
//                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"imagetype\""),
//                        RequestBody.create(null, imageType))
//                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"userphone\""),
//                        RequestBody.create(null, userPhone))
        Set keys=upFiles.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()){
            String key= (String) it.next();
            File file=upFiles.get(key);
            multbuild.addFormDataPart(key,file.getName(),RequestBody.create(MultipartBody.FORM,file));
        }
        HashMap<String, String> modelmap = jsonToMap(model);
        if (null!=modelmap) {
            Set<String> mkeys = modelmap.keySet();
            Iterator<String> mit = mkeys.iterator();
            while (mit.hasNext()) {
                String mkey = mit.next();
                String value = modelmap.get(mkey);
                multbuild.addFormDataPart(mkey, value);
            }
        }
        MultipartBody mbody = multbuild.build();
        //包装进度body
        ProgressRequestBody body= null;
        try {
            body = new ProgressRequestBody(mbody,disposables,callBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.post(body);
    }
    /**
     * post请求
     * @param url
     * @param model
     */
    @Override
    public void requestPost(String url, RequestModel model,RequestCallBack callback) {
        request(requestMode.POST,url,model,callback);
    }

    /**
     *
     * @return
     * @see
     */
    @Override
    public ComeRequestIn prepareUpload() {
        if (null!=upFiles){
            upFiles.clear();
            upFiles=null;
        }
        upFiles=new ArrayMap<String,File>();
        return this;
    }

    /**
     *
     * @param name 表单对应的name
     * @param file
     * @see ComeRequest#prepareUpload() 使用前必须调用此方法
     * @return
     */
    @Override
    public ComeRequestIn addFile(String name, File file) {
        upFiles.put(name,file);
        return this;
    }

    /**
     *
     * @param name 表单对应的name
     * @param files
     * @see ComeRequest#prepareUpload() 使用前必须调用此方法
     * @return
     */
    @Override
    public ComeRequestIn addFiles(String name, ArrayList<File> files) {
        for (File file: files) {
            upFiles.put(name,file);
        }
        return this;
    }

    /**
     *上传文件
     * @param url
     * @param callback
     * @see ComeRequest#addFile(java.lang.String, java.io.File) or liu.zhan.jun.simplerequest.comerequest.ComeRequest#addFiles(java.lang.String, java.util.ArrayList)
     * 使用前必须调用上面任意一个方法
     */
    @Override
    public void upLoadFile(String url,RequestModel model, RequestCallBack callback) {
        request(requestMode.Upload,url,model,callback);
    }

    @Override
    public void downLoadFile(String url, RequestModel model, final String filepath, final RequestCallBack callback) {
        if (null!=model){
            url=RecodeURL(url,model);
        }
        Request.Builder builder= new Request.Builder();
        if (null!=tag){
            builder.tag(tag);
        }
        builder.url(url).get();

        DownLoadSubscribe downsub=new DownLoadSubscribe();
        downsub.setRequest(builder.build());
        okClientBuilder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response rspons=chain.proceed(chain.request());
                return rspons.newBuilder().body(new ProgressResponseBody(rspons.body(),disposables,callback)).build();
            }
        });
        disposables.add(Observable.create(downsub)
                .map(new Function<Response, FileItem>() {
                    @Override
                    public FileItem apply(@NonNull Response response) throws Exception {
                        long id=Thread.currentThread().getId();
                        Log.i(TAG, "apply: id="+id);
                        File file=new File(filepath);
                        FileOutputStream fos=new FileOutputStream(file);
                        byte[] bytes=new byte[1024];
                        int readCount=0;
                        ResponseBody body = response.body();
                        //获得输入流
                        InputStream inputStream = body.byteStream();
                        //获得文件大小
                        long length = body.contentLength();
                        //获得文件类型
                        MediaType type=body.contentType();
                        Log.i(TAG, "apply: type==="+type.subtype());
                        while ((readCount=inputStream.read(bytes))!=-1){
                            fos.write(bytes,0,readCount);
                            fos.flush();
                        }
                        fos.close();
                        inputStream.close();
                        FileItem item=new FileItem();
                        item.setFile(file);
                        item.setSize(length);
                        item.setType(type.subtype());
                        return item;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        //这个方法在线程开始前执行，可以作为Progress显示
                        callback.before();
                    }
                })
                .subscribeWith(new DisposableObserver<FileItem>() {
                    @Override
                    public void onNext(FileItem file) {
                        callback.success(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        error(e, callback);
                    }

                    @Override
                    public void onComplete() {
                        callback.finish();
                    }
                }));
    }


    /**
     * json请求
     * @param json
     */
    @Override
    public void requestJson(String json) {

    }

    /**
     * get请求
     * @param url
     */
    @Override
    public void requestGet(String url,RequestModel model,RequestCallBack callback) {
        if (null!=model){
            url=RecodeURL(url,model);
            Log.i(TAG, "requestGet: url==="+url);
        }
        request(requestMode.GET,url,null,callback);
    }

    private byte[] getBody(Object obj) {
        if (obj != null) {
            Map<String, String> params = jsonToMap(obj);
            if (params != null && params.size() > 0) {
                return encodeParameters(params, PROTOCOL_CHARSET);
            }
        }
        return null;
    }

    /**
     * 拼接参数
     *
     * @param url
     * @param obj
     * @return
     */
    private String RecodeURL(String url, Object obj) {
        if (obj != null) {
            //判断obj的类型
            if (obj instanceof String) {
                obj=mGson.fromJson(obj.toString(),obj.getClass());
            }
            HashMap<String, String> map = jsonToMap(obj);
            String s = StringParameters(map, PROTOCOL_CHARSET);
            StringBuilder builder = new StringBuilder();
            builder.append(url + "?");
            builder.append(s);
            return builder.toString();

        } else {
            return url;
        }
    }

    private byte[] encodeParameters(Map<String, String> params,
                                    String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(),
                        paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(),
                        paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: "
                    + paramsEncoding, uee);
        }
    }

    /**
     * 将对象解析成符合URL的字符串
     *
     * @param params
     * @param paramsEncoding
     * @return
     */
    private String StringParameters(Map<String, String> params,
                                    String paramsEncoding) {
        StringBuffer encodedParams = new StringBuffer();
        try {
            int len = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (len > 0) {
                    encodedParams.append('&');
                }
                encodedParams.append(URLEncoder.encode(entry.getKey(),
                        paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(),
                        paramsEncoding));
                len++;
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: "
                    + paramsEncoding, uee);
        }
    }

    /**
     * 将json to map
     * @param obj
     * @return
     */
    public HashMap<String, String> jsonToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        HashMap<String, String> map = null;
        JsonElement jsonTree = mGson.toJsonTree(obj);
        JsonObject asJsonObject = jsonTree.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = asJsonObject.entrySet();
        if (entrySet.size() > 0) {
            map = new HashMap<String, String>();
        }

        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            map.put(key, value);
        }
        return map;
    }


    /** 全局连接超时时间 */
    public ComeRequest setConnectTimeout(long connectTimeout) {
        okClientBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        return this;
    }

    /** 全局读取超时时间 */
    public ComeRequest setReadTimeOut(long readTimeOut) {
        okClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
        return this;
    }

    /** 全局写入超时时间 */
    public ComeRequest setWriteTimeOut(long writeTimeout) {
        okClientBuilder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        return this;
    }


    public  enum requestMode{
        GET,POST,Upload,DOWN_LOAD
    }

    public static class DownLoadSubscribe implements ObservableOnSubscribe<Response>{

        private Request request;
        private Call call;
        public void setRequest(Request request) {
            this.request = request;

        }


        @Override
        public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
            long id=Thread.currentThread().getId();
            Log.i(TAG, "onResponse: currentThreadId================="+id);
            OkHttpClient client=ComeRequest.request.getClient();
            call=client.newCall(request);
            try {
                Response response=response = call.execute();
                String requestString=response.request().toString();
                Log.i(TAG, "subscribe: requestString==="+requestString);
                int code = response.code();
                if (code>=200&&code<300){
                    e.onNext(response);
                }else{
                    NetThrowable throwable=new NetThrowable("request Fail code="+code);
                    throwable.setStatus(code);
                    throwable.setRequestString(requestString);
                    e.onError(throwable);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                NetThrowable throwable=new NetThrowable(e1.getMessage());
                throwable.setStatus(-1);
                throwable.setRequestString(e1.getMessage());
                e.onError(throwable);
            }
            e.onComplete();
        }
    }


    public static class Subscribe implements ObservableOnSubscribe<String>{
        private Request request;
        private Call call;

        public void setRequest(Request request) {
            this.request = request;

        }
        public Call getCall(){
            return call;
        }
        @Override
        public void subscribe(@NonNull ObservableEmitter<String> e){
            long id=Thread.currentThread().getId();
            Log.i(TAG, "onResponse: currentThreadId================="+id);
            OkHttpClient client=ComeRequest.request.getClient();
            call=client.newCall(request);
            try {
                Response response=response = call.execute();
                String result= response.body().string();
                Log.i(TAG, "subscribe: response"+response.protocol().name());
                Log.i(TAG, "subscribe: result="+result);
                String requestString=response.request().toString();
                Log.i(TAG, "subscribe: requestString==="+requestString);
                int code = response.code();
                if (code>=200&&code<300){
                    e.onNext(result);
                }else{
                    NetThrowable throwable=new NetThrowable("request Fail code="+code);
                    throwable.setStatus(code);
                    throwable.setRequestString(requestString);
                    e.onError(throwable);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                NetThrowable throwable=new NetThrowable(e1.getMessage());
                throwable.setStatus(-1);
                throwable.setRequestString(e1.getMessage());
                e.onError(throwable);
            }


            e.onComplete();
        }
    }

    public static class NetThrowable extends Throwable{
        private int status;
        private String RequestString;
        public int getStatus() {
            return status;
        }

        public NetThrowable(String message) {
            super(message);
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRequestString() {
            return RequestString;
        }

        public void setRequestString(String requestString) {
            RequestString = requestString;
        }
    }
}
