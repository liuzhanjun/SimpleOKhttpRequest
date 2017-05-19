# SimpleOKhttpRequest
使用rxjava rxandroid 封装okhttp
使用方式
请求参数以对象的形式
比如http://192.168.1.101:8080/DemoServlet/RequestFriendServlet?ID=1&name=赵云&age=20
请求的参数表对应成一个对象
public class Student implements ComeRequestIn.RequestModel {
    private Integer ID;
    private String name;
    private Integer age;

  }
 返回的数据如果是json数据可以自动转换成对象
public class GirlFriend {
	private Integer id;
	private String name;
	private Integer age;
	}

然后就可以开始正式进行网络请求了
1.get请求 (注意回调对象的泛型必须为引用类型，必须写明类型，不能不写)
否则会抛出Missing type parameter. 请在RequestCallBack上添加泛型类型
 Student student=new Student();
        student.setID(1000);
        student.setAge(39);
        student.setName("赵云");
ComeRequest.request.getInstance()
.requestGet("http://192.168.1.101:8080/DemoServlet/RequestFriendServlet",student,
new RequestCallBack<GirlFriend>() {
            @Override
            public void success(GirlFriend result) {
                Log.i(TAG, "success: ============================");
                textView.setText(result.toString());
            }

            @Override
            public void failure(ComeRequest.NetThrowable error) {
                Log.i(TAG, "failure: ========"+error.getRequestString()+"===status="+error.getStatus()+"|====message"+error.getMessage());
                 dialog.cancel();
            }

            @Override
            public void finish() {
                dialog.cancel();
            }

            @Override
            public void before() {
                    Log.i(TAG, "before: ======================================");
                    dialog=new ProgressDialog(mContext);
                    dialog.setMessage("正在加载中...");
                    long id=Thread.currentThread().getId();
                    Log.i(TAG, "before: TID="+id);
                    dialog.show();
            });

 2.post请求 步骤和get方法一致
 ComeRequest.request.getInstance().requestPost(url,modle,requestCallback)

3.添加head
        1.添加全局head:ComeRequest.request.getInstance().setGlobalHeads(HashMap);

        2.添加局部head:ComeRequest.request.getInstance().addHeads(HashMap,boolean);
        后面boolean是表示当前请求是否使用全局的head

4 https请求添加证书 只实现了添加.pfx证书，如果是其他类型的证书可以使用openssl转换
        try {
            ins=getAssets().open("server.pfx");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //添加证书，全局添加，在application中添加
        ComeRequest.request.addVert(ins,"654321@@m","192.168.1.100");

  然后使用上面的get和post请求即可

  5.上传文件
        Student student=new Student();
        student.setID(1000);
        student.setAge(39);
        student.setName("赵云");
        File file=new File(Environment.getExternalStorageDirectory()+"/Download");
ComeRequest.request
                .setTag(Request_Upload)
                .prepareUpload()
                .addFile("mimg1",new File(file.getAbsolutePath()+"/22.jpg"))
                .addFile("mimg2",new File(file.getAbsolutePath()+"/33.jpg"))
                .addFile("mov",new File(Environment.getExternalStorageDirectory()+"/Movies/yuv_cuc_ieschool.yuv"))
                .upLoadFile("http://192.168.1.101:8080/DemoServlet/HelloWorldServlet", student, new RequestCallBack<String>() {
                    @Override
                    public void before() {
                        Log.i(TAG, "before: 开始上传");
                        dialog=new ProgressDialog(mContext);
                        dialog.setMessage("正在加载中...");
                        long id=Thread.currentThread().getId();
                        Log.i(TAG, "before: TID="+id);
                        dialog.setProgress(0);
                        dialog.setMax(100);
                        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        dialog.show();
                    }

                    @Override
                    public void success(String result) {
                        Log.i(TAG, "success: ========"+result);
                    }

                    @Override
                    public void failure(ComeRequest.NetThrowable error) {
                        Log.i(TAG, "failure: ==========="+error.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void finish() {
                        Log.i(TAG, "finish: 请求完成");
                        dialog.dismiss();
                    }

                    @Override
                    public void progress(long currentByte, long countByte) {
                        long id=Thread.currentThread().getId();
                        Log.i(TAG, "progress: currentByte="+currentByte+"===progress="+countByte+"threadID="+id);
                        DecimalFormat format=new DecimalFormat("0");
                        double bfb=(1.0*currentByte/countByte)*100;
                        Log.i(TAG, "progress: bfb===="+bfb);
                        String sult=format.format(bfb);
                        dialog.setProgress(Integer.valueOf(sult));
                    }
                });


  6.下载文件
  File file=new File(Environment.getExternalStorageDirectory()+"/Download/那些年，我们一起追的女孩.mp4");
ComeRequest.request.downLoadFile("http://192.168.1.101:8080/DemoServlet/upload/nxn.mp4", null,file.getAbsolutePath(), new RequestCallBack<FileItem>() {
            @Override
            public void before() {
                Log.i(TAG, "before: 开始下载");
                //                Log.i(TAG, "before: ======================================");
                dialog=new ProgressDialog(mContext);
                dialog.setMessage("正在加载中...");
                long id=Thread.currentThread().getId();
                Log.i(TAG, "before: TID="+id);
                dialog.setProgress(0);
                dialog.setMax(100);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
            }

            @Override
            public void success(FileItem result) {
                Log.i(TAG, "success: 下载成功 type="+result.getType());
                dialog.setProgress(100);
            }

            @Override
            public void failure(ComeRequest.NetThrowable error) {
                Log.i(TAG, "failure: 下载失败"+error.getMessage());
                dialog.dismiss();
            }

            @Override
            public void progress(long currentByte, long countByte) {
                long id=Thread.currentThread().getId();
                Log.i(TAG, "progress: currentByte="+currentByte+"===progress="+countByte+"threadID="+id);
                DecimalFormat format=new DecimalFormat("0");

                double bfb=(1.0*currentByte/countByte)*100;
                Log.i(TAG, "progress: bfb===="+bfb);
                String sult=format.format(bfb);
                dialog.setProgress(Integer.valueOf(sult));
            }

            @Override
            public void finish() {
                Log.i(TAG, "finish: ");
                dialog.dismiss();
            }
        });
