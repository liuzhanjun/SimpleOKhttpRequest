package liu.zhan.jun.simplerequest.comerequest;

import java.io.File;

/**
 * Created by 刘展俊 on 2017/5/1.
 */

public class FileItem {
    private File file;
    private long size;
    private String Type;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
