package liu.zhan.jun.simplerequest;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import liu.zhan.jun.simplerequest.comerequest.ComeRequest;
import liu.zhan.jun.simplerequest.comerequest.RequestCallBack;
import liu.zhan.jun.simplerequest.parameter.GetOrderParameter;
import liu.zhan.jun.simplerequest.respons.GetOrderResponse;
import liu.zhan.jun.simplerequest.utils.StoreSp;
import liu.zhan.jun.simplerequest.view.LoadProgressDialog;

public class RefundActivity extends AppCompatActivity {

    private static final String TAG ="RefundActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);

        GetOrderParameter parameter=new GetOrderParameter();
        Log.i(TAG, "onCreate: ==="+StoreSp.getMacAdress(getApplicationContext()));
        parameter.setDevice_cdoe(StoreSp.getMacAdress(getApplicationContext()));
        final Dialog dialog = LoadProgressDialog.createDialog(this, "正在查询");
        ComeRequest.request.setTag(MyConfig.get_order).requestPost(MyConfig.BASE_URL + MyConfig.get_order, parameter, new RequestCallBack<GetOrderResponse>() {
            @Override
            public void before() {
                Log.i(TAG, "before: ");
                LoadProgressDialog.show(dialog);
            }

            @Override
            public void success(GetOrderResponse result) {
                if (result!=null) {
                    Log.i(TAG, "success: "+result.toString());
                }
            }

            @Override
            public void failure(ComeRequest.NetThrowable error) {
                Log.i(TAG, "failure: "+error.getMessage());
//                LoadProgressDialog.close();
            }

            @Override
            public void finish() {
//                LoadProgressDialog.close();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        ComeRequest.request.cancel(MyConfig.get_order);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
