package liu.zhan.jun.simplerequest.comerequest;

import android.support.v4.util.ArrayMap;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 刘展俊 on 2017/4/22.
 */

public interface ComeRequestIn{

    void requestGet(String url, RequestModel model, RequestCallBack callBack);

      void requestPost(String url,RequestModel model,RequestCallBack callback);
      ComeRequestIn prepareUpload();
      ComeRequestIn addFile(String name,File file);
      ComeRequestIn addFiles(String name,ArrayList<File> files);
      void upLoadFile(String url,RequestModel model,RequestCallBack callback);
      void downLoadFile(String url,RequestModel model,String filepath,RequestCallBack callback);

      void requestJson(String json);

     interface RequestModel{

    }


}
