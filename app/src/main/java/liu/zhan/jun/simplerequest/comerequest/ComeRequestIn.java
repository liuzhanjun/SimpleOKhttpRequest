package liu.zhan.jun.simplerequest.comerequest;

import android.support.v4.util.ArrayMap;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 刘展俊 on 2017/4/22.
 */

public interface ComeRequestIn{

    public  void requestGet(String url,RequestModel model,RequestCallBack callBack);

    public  void requestPost(String url,RequestModel model,RequestCallBack callback);
    public  ComeRequestIn prepareUpload();
    public  ComeRequestIn addFile(String name,File file);
    public  ComeRequestIn addFiles(String name,ArrayList<File> files);
    public  void upLoadFile(String url,RequestModel model,RequestCallBack callback);
    public  void downLoadFile(String url,RequestModel model,String filepath,RequestCallBack callback);

    public  void requestJson(String json);

    public interface RequestModel{

    }


}
