package liu.zhan.jun.simplerequest.comerequest;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by 刘展俊 on 2017/5/1.
 */

public class ProgressObserver implements ObservableOnSubscribe<String>,Progress{
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
