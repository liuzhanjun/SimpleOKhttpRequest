package liu.zhan.jun.simplerequest.utils;

import java.nio.charset.Charset;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yunniu on 2017/8/11.
 */

public class PrintUtils {
    private Vector<Byte> allCMD;//所有命令
    private PrinterCMD cmd;
    public PrintUtils() {
        this.allCMD = new Vector<>();
        cmd=new PrinterCMD();
    }

    public byte[] getAllCMD() {
        return ByteTo_byte(allCMD);
    }

    /**
     * 清空所有
     */
    public PrintUtils clearAllCMD(){
        allCMD.clear();
        return this;
    }

    public void remove(StringBuffer collect,String regex){
        Pattern pattern=Pattern.compile(regex);
        Matcher m = pattern.matcher(collect.toString());
        if (m.find()){
            String str= m.replaceAll("");
            collect.delete(0,collect.length());
            collect.append(str);
        }
    }



    public PrintUtils p(){
        return this;
    }

    public PrintUtils PrintMusic() {

        addCmd(cmd.CMC_Music());

        return this;
    }

    private void addCmd(String key) {
        byte bytes[]=key.getBytes(Charset.forName("ASCII"));
        for (int i = 0; i < bytes.length; i++) {
            allCMD.add(bytes[i]);
        }
    }
    private void addCmdGB2312(String key) {
        byte bytes[]=key.getBytes(Charset.forName("GB2312"));
        for (int i = 0; i < bytes.length; i++) {
            allCMD.add(bytes[i]);
        }
    }
    /// <summary>
    /// 网络打印机换行
    /// </summary>
    /// <param name="lines"></param>
    public PrintUtils PrintNewLines(int lines){
        for (int i = 0; i < lines; i++) {
            PrintEnter();
        } return this;
    }

    /// <summary>
    /// 网络打印机 切割
    /// </summary>
    /// <param name="pagenum">切割时，走纸行数</param>
    public PrintUtils CutPage(int pagenum)
    {
        addCmd(cmd.CMD_PageGO(pagenum));
        addCmd(cmd.CMD_Enter());
        addCmd(cmd.CMD_CutPage());
        addCmd(cmd.CMD_Enter());
        return this;
    }
    public PrintUtils PrintEnter() {
        addCmd(cmd.CMD_Enter());
        return this;
    }
    /// <summary>
    /// 网络打印机 打印的文本
    /// </summary>
    /// <param name="pszString"></param>
    /// <param name="nFontAlign">0:居左 1:居中 2:居右</param>
    /// <param name="nfontsize">字体大小0:正常大小 1:两倍高 2:两倍宽 3:两倍大小 4:三倍高 5:三倍宽 6:三倍大小 7:四倍高 8:四倍宽 9:四倍大小 10:五倍高 11:五倍宽 12:五倍大小</param>
    /// <param name="ifzhenda">0:非针打  1:针打</param>
    public PrintUtils PrintText(String pszString, int nFontAlign, int nFontSize,int ifzhenda){
        addCmd(cmd.CMD_TextAlign(nFontAlign));
        if (ifzhenda == 1)
        {
            addCmd(cmd.CMD_FontSize_BTP_M280(nFontSize));
            addCmd(cmd.CMD_FontSize_BTP_M2801(nFontSize));
        }else{
            addCmd(cmd.CMD_FontSize(nFontSize));
        }
        addCmdGB2312(pszString);
        return this;
    }

    /**
     * 打印一行
     * /// <summary>
     /// 网络打印机 打印的文本
     /// </summary>
     /// <param name="pszString"></param>
     /// <param name="nFontAlign">0:居左 1:居中 2:居右</param>
     /// <param name="nfontsize">字体大小0:正常大小 1:两倍高 2:两倍宽 3:两倍大小 4:三倍高 5:三倍宽 6:三倍大小 7:四倍高 8:四倍宽 9:四倍大小 10:五倍高 11:五倍宽 12:五倍大小</param>
     /// <param name="ifzhenda">0:非针打  1:针打</param>
     * @param pszString
     * @param nFontAlign
     * @param nFontSize
     * @param ifzhenda
     * @return
     */
    public PrintUtils PrintTextLine(String pszString, int nFontAlign, int nFontSize,int ifzhenda){
        PrintText(pszString, nFontAlign, nFontSize, ifzhenda);
        PrintEnter();
        return this;
    }

    public PrintUtils PrintTextLine(String pszString){
        PrintText(pszString, 0, 0, 0);
        PrintEnter();
        return this;
    }

    public byte[] ByteTo_byte(Vector<Byte> vector) {
        int len = vector.size();
        byte[] data = new byte[len];

        for(int i = 0; i < len; ++i) {
            data[i] = ((Byte)vector.get(i)).byteValue();
        }

        return data;
    }

}
