package liu.zhan.jun.simplerequest.comerequest;

import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

import static liu.zhan.jun.simplerequest.MainActivity.TAG;

/**
 * Created by 刘展俊 on 2017/4/30.
 */

public class ProgressRequestBody extends RequestBody {
    //实际的待包装请求体
    private  RequestBody requestBody;
    private  CompositeDisposable disposable;
    private RequestCallBack callBack;
    //包装完成的BufferedSink
    private BufferedSink bufferedSink;

    private  ProgressObserver observer;

    public ProgressRequestBody(RequestBody requestBody, CompositeDisposable disposable, final RequestCallBack callBack) {
        this.requestBody = requestBody;
        this.disposable = disposable;
        this.callBack=callBack;
        observer=new ProgressObserver();
        disposable.add(Observable.create(observer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>(){

                    @Override
                    public void onNext(String o) {
                        String[] sp = o.split(",");
                        long currentByte=Long.parseLong(sp[0]);
                        long countByte=Long.parseLong(sp[1]);
                        callBack.progress(currentByte,countByte);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                observer.update(bytesWritten, contentLength);
            }
        };
    }


    public static class ProgressObserver implements ObservableOnSubscribe<String>,Progress{
        private ObservableEmitter<String> e;
        @Override
        public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
            this.e=e;

        }

        @Override
        public void update(long cByte, long countByte) {
                e.onNext(String.valueOf(cByte+","+countByte));
        }
    }

    public interface Progress{
        public void update(long cByte,long countByte);
    }
}
