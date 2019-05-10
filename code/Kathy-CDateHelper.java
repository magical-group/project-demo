package mydate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import txt.CTxtHelp;

public class CDateHelper {
    public CDateHelper() {
    }

    private static class DateHelperHolder {
        private static final CDateHelper instance = new CDateHelper();
    }
    
    public static CDateHelper getInstance() {
        return DateHelperHolder.instance;
    }
    
    int[] arrTime = new int[6];
    // yyyyMMdd HH:mm:ss
    public int[] GetTime1(String strTime, String strFormat) {
        SimpleDateFormat formatdate = new SimpleDateFormat(strFormat);
        Date date = null;
        try { date = formatdate.parse(strTime); } catch (ParseException ex) { CTxtHelp.AppendLog("[Error] [CDateHelper]<GetTime1>:" + ex.getMessage()); }
        arrTime[0] = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        arrTime[1] = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        arrTime[2] = Integer.parseInt(new SimpleDateFormat("dd").format(date));
        arrTime[3] = Integer.parseInt(new SimpleDateFormat("HH").format(date));
        arrTime[4] = Integer.parseInt(new SimpleDateFormat("mm").format(date));
        arrTime[5] = Integer.parseInt(new SimpleDateFormat("ss").format(date));
        
        return arrTime;
    }
    
    public int[] GetTime2(Date date) {
        arrTime[0] = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        arrTime[1] = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        arrTime[2] = Integer.parseInt(new SimpleDateFormat("dd").format(date));
        arrTime[3] = Integer.parseInt(new SimpleDateFormat("HH").format(date));
        arrTime[4] = Integer.parseInt(new SimpleDateFormat("mm").format(date));
        arrTime[5] = Integer.parseInt(new SimpleDateFormat("ss").format(date));
        
        return arrTime;
    }
    
    // yyyyMMdd
    public int[] GetTime3(String strTime, String strFormat) {
        SimpleDateFormat formatdate = new SimpleDateFormat(strFormat);
        Date date = null;
        try { date = formatdate.parse(strTime); } catch (ParseException ex) { CTxtHelp.AppendLog("[Error] [CDateHelper]<GetTime3>:" + ex.getMessage()); }
        arrTime[0] = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        arrTime[1] = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        arrTime[2] = Integer.parseInt(new SimpleDateFormat("dd").format(date));
        
        return arrTime;
    }
    
    public String FormatToFormat(String strTime, String strFormat1, String strFormat2) {
        SimpleDateFormat formatdate = new SimpleDateFormat(strFormat1);
        Date date = null;
        try { date = formatdate.parse(strTime); } catch (ParseException ex) { CTxtHelp.AppendLog("[Error] [CDateHelper]<FormatToFormat>:" + ex.getMessage()); }
        
        return (new SimpleDateFormat(strFormat2)).format(date);
    }
    
    public String DateCalculate(int day, String format) {
        if (null == format || "".equals(format)) format = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(format);    
        return df.format(new Date(new Date().getTime() + day * 24 * 60 * 60 * 1000));  
    }
    
    public Date StringToDate(String strDate) {
        Date date = null;
        try { date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);} catch (ParseException ex) { CTxtHelp.AppendLog("[Error] [CDateHelper]<StringToDate>:" + ex.getMessage()); }
        
        return date;
    }
    
    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      
    public static String GetNowTime() {
        synchronized(sdf){
            return sdf.format(new Date());
        }  
    }
    
    public static TimeEntity GetNowTimeWithEntity() {
        TimeEntity ret = new TimeEntity();
        synchronized(sdf){
            Date now = new Date();
            ret.TimeStamp = now.getTime() / 1000;// 精确到秒
            ret.TimeValue = sdf.format(new Date());
        } 
        return ret;
    }
}
