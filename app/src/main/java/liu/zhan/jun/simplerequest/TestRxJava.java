package liu.zhan.jun.simplerequest;

import android.os.SystemClock;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import liu.zhan.jun.simplerequest.comerequest.ComeRequest;

import static liu.zhan.jun.simplerequest.MainActivity.TAG;

/**
 * Created by 刘展俊 on 2017/4/22.
 */

public class TestRxJava {


    public int rxjava(){
        Flowable.just("a","b","c")
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.i(TAG, "accept: ============"+s);
                System.out.print("c=========="+s);

            }
        });

        return 0;
    }

    public int rxjavaFrow(){

       CompositeDisposable disposables = new CompositeDisposable();
        disposables.add(sampleObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override public void onComplete() {
                        Log.i(TAG, "onComplete()");
                    }

                    @Override public void onError(Throwable e) {
                        Log.i(TAG, "onError()", e);
                    }

                    @Override public void onNext(String string) {
                        Log.i(TAG, "onNext(" + string + ")");
                    }
                }));

                return 0;
    }
    static Observable<String> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation
                SystemClock.sleep(5000);
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    
    public String getString(){
        Log.i(TAG, "getString: =============================");
        return "bb";
    }



    public int request(){
        ComeRequest cRequest = ComeRequest.request.getInstance();
//        cRequest.requestGet("https://github.com/jeasonlzy/okhttp-OkGo");
        return 0;
    }

}
