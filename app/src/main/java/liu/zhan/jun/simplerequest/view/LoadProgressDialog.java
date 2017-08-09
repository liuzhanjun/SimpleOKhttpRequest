package liu.zhan.jun.simplerequest.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import liu.zhan.jun.simplerequest.R;

/**
 * Created by yunniu on 2017/8/9.
 */

public class LoadProgressDialog {


    public static Dialog createDialog(Context context, String msg){
        Dialog progressDialog = new Dialog(context, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        tv_msg.setText(msg);
        return progressDialog;
    }

    public static void updateShowMsg(Dialog progressDialog,String msg){
        if (progressDialog!=null){
            TextView tv_msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
            tv_msg.setText(msg);
        }

    }
    public static void show(Dialog progressDialog){
        if (progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    public static void close(Dialog progressDialog){
        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }


}
