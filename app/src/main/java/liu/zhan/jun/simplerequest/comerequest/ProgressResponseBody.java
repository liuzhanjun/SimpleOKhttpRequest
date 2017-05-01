package liu.zhan.jun.simplerequest.comerequest;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by 刘展俊 on 2017/4/30.
 */

public class ProgressResponseBody extends ResponseBody {
    private final ProgressObserver observer;
    //实际的待包装响应体
    private ResponseBody responseBody;
    //进度回调接口
    private  RequestCallBack callBack;
    //包装完成的BufferedSource
    private BufferedSource bufferedSource;


    public ProgressResponseBody(ResponseBody responseBody, CompositeDisposable disposable,RequestCallBack progressListener) {
        this.responseBody = responseBody;
        this.callBack = progressListener;
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
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource==null){
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 读取，回调进度接口
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {

        return new ForwardingSource(source) {
            //当前读取字节数
            long totalBytesRead = 0L;
            @Override public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //回调，如果contentLength()不知道长度，会返回-1
                observer.update(totalBytesRead, responseBody.contentLength());

                return bytesRead;
            }
        };
    }
}
