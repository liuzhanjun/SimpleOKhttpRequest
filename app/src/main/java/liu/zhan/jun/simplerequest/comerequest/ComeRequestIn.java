package liu.zhan.jun.simplerequest.comerequest;

/**
 * Created by 刘展俊 on 2017/4/22.
 */

public interface ComeRequestIn{

    public  void requestGet(String url,RequestModel model,RequestCallBack callBack);

    public  void requestPost(String url,RequestModel model,RequestCallBack callback);

    public  void requestJson(String json);

    public interface RequestModel<T>{

    }

}
