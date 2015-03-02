/*****************************************************
* 전문 세밀부
* @author	   			 (주)미르이즈
* @version         1.0
* @since           1.0
******************************************************/

package com.etax.trans.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.etax.entity.*;
import com.etax.util.StringUtil;

public class GNBJanAk {
	Logger logger = Logger.getLogger("GNBJanAk");
	private int sp = 100;
	private HashMap<String, String> data = new HashMap<String, String>();
	private List<HashMap<String, String>> initList = new ArrayList<HashMap<String, String>>();
	
	/****************************************************************************
   * GNBJanAk 생성자
   * @return                
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public GNBJanAk() {
		data.put("detail01", StringUtil.zeroFill ("0",   8));  //기준일자
		data.put("detail02", StringUtil.zeroFill ("0",   2));  //배열건수
		data.put("detail03", StringUtil.blankFill(" ", 15));  //계좌번호
		data.put("detail04", StringUtil.zeroFill ("0", 13));  //계좌잔액
		data.put("detail05", StringUtil.blankFill(" ",  4));  //처리결과코드
		data.put("detail06", StringUtil.blankFill(" ",24));  //처리결과메시지
		data.put("detail07", StringUtil.blankFill(" ", 60));  //예비
	}
	
	/****************************************************************************
    * 전문 초기화 셋팅
    * @param msg             전문
    * @return                
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public void init(String msg) throws Exception{		 
		data.put("detail01", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //기준일자 
		data.put("detail02", StringUtil.substringByte(msg,sp,nextPoint(sp,  2)));  //배열건수
		
		List <HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();
		for (int i=0; i<Integer.parseInt(data.get("detail02")); i++) {
			HashMap<String, String> rowData = new HashMap<String, String>();
			rowData.put("detail03", StringUtil.substringByte(msg,sp,nextPoint(sp, 15)));  //계좌번호 
			rowData.put("detail04", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //계좌잔액
			rowData.put("detail05", StringUtil.substringByte(msg,sp,nextPoint(sp,  4)));  //처리결과코드
			rowData.put("detail06", StringUtil.substringByte(msg,sp,nextPoint(sp, 24)));  //처리결과메시지
			rowList.add(rowData);
			initList = rowList;
		}
		data.put("detail07", StringUtil.substringByte(msg,sp,nextPoint(sp, 60)));  //예비
	}
		
	/****************************************************************************
   * 해쉬맵에 담은 정보를 전문으로 만들기
   * @return                전문
   * @exception             
   * @since                 1.0
  ***************************************************************************/
	public String dataSend(List<CommonEntity> sendList) {	  
		String sendMsg = "";
		data.put("detail01", StringUtil.zeroFill (data.get("detail01"),  8));  //기준일자 
		data.put("detail02", StringUtil.zeroFill (sendList.size()+"",  2));  //배열건수
		for (int a=1; a<=2; a++) {
			sendMsg+=data.get("detail" + StringUtil.zeroFill(a, 2));
		}
		if (sendList.size() <= 30) {
      for (int x=0; x<sendList.size(); x++) {
			  CommonEntity rowData = (CommonEntity)sendList.get(x);
		    data.put("detail03", StringUtil.blankFill(rowData.getString("M300_ACCOUNTNO"), 15));  //계좌번호
		    data.put("detail04", StringUtil.zeroFill (data.get("detail04"), 13));  //계좌잔액
		    data.put("detail05", StringUtil.blankFill(data.get("detail05"),  4));  //처리결과코드
		    data.put("detail06", StringUtil.blankFill(data.get("detail06"), 24));  //처리결과메시지
		  
		    for (int b=3; b<=6; b++) {
			    sendMsg += data.get("detail" + StringUtil.zeroFill(b, 2));
		    }
		  }

      for (int y=0; y<30-sendList.size(); y++) {
		    data.put("detail03", StringUtil.blankFill(" ",  15));  //계좌번호
		    data.put("detail04", StringUtil.zeroFill ("0",  13));  //계좌잔액
		    data.put("detail05", StringUtil.blankFill(" ",   4));  //처리결과코드
		    data.put("detail06", StringUtil.blankFill (" ", 24));  //처리결과메시지
		  
		    for (int c=3; c<=6; c++) {
			    sendMsg += data.get("detail" + StringUtil.zeroFill(c, 2));
		    }
	 }

      
    } else {
      for (int x=0; x<30; x++) {
			  CommonEntity rowData = (CommonEntity)sendList.get(x);
		    data.put("detail03", StringUtil.blankFill(rowData.getString("M300_ACCOUNTNO"), 15));  //계좌번호
		    data.put("detail04", StringUtil.zeroFill (data.get("detail04"), 13));  //계좌잔액
		    data.put("detail05", StringUtil.blankFill(data.get("detail05"),  4));  //처리결과코드
		    data.put("detail06", StringUtil.blankFill(data.get("detail06"), 24));  //처리결과메시지
		  
		    for (int b=3; b<=6; b++) {
			    sendMsg += data.get("detail" + StringUtil.zeroFill(b, 2));
		    }
		  }

      for (int y=0; y<sendList.size() - 30; y++) {
		    data.put("detail03", StringUtil.blankFill(" ",  15));  //계좌번호
		    data.put("detail04", StringUtil.zeroFill ("0",  13));  //계좌잔액
		    data.put("detail05", StringUtil.blankFill(" ",   4));  //처리결과코드
		    data.put("detail06", StringUtil.blankFill (" ", 24));  //처리결과메시지
		  
		    for (int c=3; c<=6; c++) {
			    sendMsg += data.get("detail" + StringUtil.zeroFill(c, 2));
		    }
		  }

    }
		

		

		data.put("detail07", StringUtil.blankFill(data.get("detail07"), 60));  //문자4   

		sendMsg += data.get("detail07");
		return sendMsg;
	}
	
	/****************************************************************************
   * byte단위로 자르기 위해 다음 인덱스 구하기
   * @param startPoint      시작인덱스      
   * @param term            길이
   * @return                다음 인덱스
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/
	private int nextPoint(int startPoint, int term) throws Exception {
	  sp = startPoint + term;

	  return sp;
	}
	
	/****************************************************************************
   * 조회조건을 해쉽맵에 담아서 전문에 추가함
   * @param sendData        조회조건           
   * @return                보낼 전문
   * @exception             Exception
   * @since                 1.0
  ***************************************************************************/

	public String process(HashMap<String,String> sendData, List<CommonEntity> sendList) throws Exception {
	  String sendStr = "";

	  data.putAll(sendData);

	  sendStr = this.dataSend(sendList);

	  return sendStr;
	}
	
	public HashMap<String, String> getMsgData() {
	  return data;
	}
	
	public List<HashMap<String, String>> getDataList() {
		
	  return initList;
	}
}