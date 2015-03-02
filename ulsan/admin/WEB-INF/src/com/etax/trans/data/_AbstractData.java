package com.etax.trans.data;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import com.etax.util.StringUtil;

abstract public class _AbstractData {

    private String msgTitle;
    private int sp = 0;
    private int msgLength = 0;
    private LinkedHashMap<String, String[]> fieldset = new LinkedHashMap<String, String[]>();
    private LinkedHashMap<String, String> msgData = new LinkedHashMap<String, String>();

    protected _AbstractData() {
    }

    protected int nextPoint(int startPoint, int term) {
        sp = startPoint + term;

        return sp;
    }

    public String getSendMsg() {
        formatMsgData();

        String sendMsg = "";

        Iterator<String> iterator = msgData.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String val = (String) msgData.get(key);
            sendMsg += val;
        }

        return sendMsg;
    }

    public void setFieldset(LinkedHashMap<String, String[]> fieldset) {
        this.fieldset = fieldset;
    }

    public LinkedHashMap<String, String[]> getFieldset() {
        return this.fieldset;
    }

    public String getMsgTitle() {
        return this.msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public int getMsgLength() {
        return this.msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }

    public int getStartPos() {
        return this.sp;
    }

    public void setStartPos(int sp) {
        this.sp = sp;
    }

    public String getMsgField(String fieldKey) {

        return (String) msgData.get(fieldKey);
    }

    public String getMsgString(String fieldKey) {

        return (String) msgData.get(fieldKey).replaceAll("[ ]+$", "");
    }

    public int getMsgInt(String fieldKey) {

        String fldStr = msgData.get(fieldKey).replaceAll("^[0]+", "");

        if ("".equals(fldStr) || Pattern.compile("[^0-9]").matcher(fldStr).find()) {
            fldStr = "0";
        }

        return Integer.parseInt(fldStr);
    }

    public long getMsgLong(String fieldKey) {

        String fldStr = msgData.get(fieldKey).replaceAll("^[0]+", "");

        if ("".equals(fldStr) || Pattern.compile("[^0-9]").matcher(fldStr).find()) {
            fldStr = "0";
        }

        return Long.parseLong(fldStr);
    }

    public void setMsgData(LinkedHashMap<String, String> msgData) {
        this.msgData = msgData;
    }

    public void setMsgValue(String msgKey, String msgValue) {
        msgData.put(msgKey, msgValue);
    }

    public void setMsgValue(String msgKey, int msgValue) {
        msgData.put(msgKey, String.valueOf(msgValue));
    }

    public void initMsg(String msgTitle, int startPos, int msgLength, LinkedHashMap<String, String[]> fieldset) {

        setMsgTitle(msgTitle);
        setStartPos(startPos);
        setMsgLength(msgLength);
        setFieldset(fieldset);

        Iterator<String> iterator = fieldset.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] val = fieldset.get(key);

            String str = ("N".equals(val[2]) || "9".equals(val[2])) ? "0" : " ";

            msgData.put(key, StringUtil.repeat(str, Integer.parseInt(val[1])));
        }
    }

    public void formatMsgData() {

        Iterator<String> iterator = fieldset.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] val = fieldset.get(key);
            int length = Integer.parseInt(val[1]);

            String msgStr = null;

            if ("N".equals(val[2]) || "9".equals(val[2])) {
                msgStr = StringUtil.zeroFill(StringUtil.nullToEmpty(msgData.get(key)), length);
            }
            else {
                msgStr = StringUtil.blankFill(StringUtil.nullToEmpty(msgData.get(key)), length);
            }

            msgData.put(key, StringUtil.substringByte(msgStr, 0, length));
        }
    }

    public LinkedHashMap<String, String> getMsgDataValues() {

        Iterator<String> iterator = fieldset.keySet().iterator();
        LinkedHashMap<String, String> msgDataValues = msgData;

        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] val = fieldset.get(key);

            if ("N".equals(val[2]) || "9".equals(val[2])) {
                String fldStr = msgDataValues.get(key).replaceAll("^[0]+", "");
                msgDataValues.put(key, "".equals(fldStr) ? "0" : fldStr);
            }
            else {
                msgDataValues.put(key, msgDataValues.get(key).replaceAll("[ ]+$", ""));
            }
        }
        return msgDataValues;
    }

    public void setDataLine(String msg) {

        Iterator<String> iterator = fieldset.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] val = fieldset.get(key);
            int length = Integer.parseInt(val[1]);

            this.msgData.put(key, StringUtil.substringByte(msg, sp, nextPoint(sp, length)));
        }
    }
    
    
    /****************************************************************************
     * 전문 초기화
     * 
     * @param msg
     * @return
     * @since 1.0
     ***************************************************************************/
    public void parseMsg(String msg) {
        Iterator<String> iterator = fieldset.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] val = fieldset.get(key);
            int length = Integer.parseInt(val[1]);
            msgData.put(key, new String(msg.getBytes(), sp, length));
            nextPoint(sp, length); //다음 스타트포인트 리턴
        }
    }
    

    public void setOcrBand(String msg) {

        Iterator<String> iterator = fieldset.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] val = fieldset.get(key);
            int length = Integer.parseInt(val[1]);

            this.msgData.put(key, StringUtil.substringByte(msg, sp, nextPoint(sp, length)));
        }
    }

    public void setOcrBand(byte[] msgBytes) {

        Iterator<String> iterator = fieldset.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String[] val = (String[]) fieldset.get(key);
            int len = Integer.parseInt(val[1]);

            int offset = nextPoint(sp, len) - len;
            int curIdx = 0;

            if (offset + len <= msgBytes.length) {

                for (curIdx = offset; curIdx < (offset + len); curIdx++) {
                    int hex = msgBytes[curIdx] & 0xff;

                    if (!(0x00 <= hex && hex <= 0x7F))
                        curIdx++;
                }

                if (curIdx - offset > len)
                    len--;

                byte[] _val = new byte[len];
                System.arraycopy(msgBytes, offset, _val, 0, len);

                msgData.put(key, new String(_val));
            }
            else {
                msgData.put(key, "");
            }
        }
    }
}