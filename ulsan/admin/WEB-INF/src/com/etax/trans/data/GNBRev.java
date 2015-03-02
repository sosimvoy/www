/*****************************************************
* 전문(수신전문 해쉬맵에 담기) 
* @author	   			 (주)미르이즈
* @version         1.0
* @since           1.0
******************************************************/

package com.etax.trans.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.etax.entity.CommonEntity;
import com.etax.util.StringUtil;

public class GNBRev {
	static Logger logger = Logger.getLogger("GNBRev");
	private static int sp = 100;
	static CommonEntity data = new CommonEntity();
	static List<CommonEntity> arrayList = new ArrayList<CommonEntity>();
  
	/****************************************************************************
    * 전문을 byte로 잘라서 CommonEntity에 담는다
    * @param msg           	수신전문
    * @return                CommonEntity에 담은 전문
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public static CommonEntity getData(String msg, String flag) throws Exception {
        CommonEntity rowData =  new CommonEntity();

        byte[] bytemsg = msg.getBytes();
        rowData.setValue("common01", new String(bytemsg,  0,  4));
        rowData.setValue("common02", new String(bytemsg,  4, 12));
        rowData.setValue("common03", new String(bytemsg, 16,  2));
        rowData.setValue("common04", new String(bytemsg, 18,  4));
        rowData.setValue("common05", new String(bytemsg, 22,  3));
        rowData.setValue("common06", new String(bytemsg, 25,  1));
        rowData.setValue("common07", new String(bytemsg, 26,  6));
        rowData.setValue("common08", new String(bytemsg, 32,  8));
        rowData.setValue("common09", new String(bytemsg, 40,  6));
        rowData.setValue("common10", new String(bytemsg, 46,  4));
        rowData.setValue("common11", new String(bytemsg, 50, 24));
        rowData.setValue("common12", new String(bytemsg, 74,  3));
        rowData.setValue("common13", new String(bytemsg, 77,  3));
        rowData.setValue("common14", new String(bytemsg, 80,  1));
        rowData.setValue("common15", new String(bytemsg, 81, 19));
        
        if ("ACCT".equals(flag)) {
			//수취인조회
			rowData.setValue("detail01", new String(bytemsg, 100,  15));  //계좌번호 
            rowData.setValue("detail02", new String(bytemsg, 115,  13));  //주민사업자번호
            rowData.setValue("detail03", new String(bytemsg, 128,  12));  //예금주명
            rowData.setValue("detail04", new String(bytemsg, 140,   3));  //은행코드
            rowData.setValue("detail05", new String(bytemsg, 143,  20));  //예금주명세부내역
            rowData.setValue("detail06", new String(bytemsg, 163,  12));  //금액
            rowData.setValue("detail07", new String(bytemsg, 175,   2));  //압류방지
            rowData.setValue("detail08", new String(bytemsg, 177, 123));  //예비
        } else if ("COMM".equals(flag)) {
			//통신망업무
		    rowData.setValue("detail01", new String(bytemsg, 100, 1));  //구분 
		    rowData.setValue("detail02", new String(bytemsg, 101, 199));  //예비
        } else if ("JAN".equals(flag)) {
			//잔액조회
		    rowData.setValue("detail01", new String(bytemsg, 100, 8));  //기준일자
		    rowData.setValue("detail02", new String(bytemsg, 108, 2));  //배열건수
        } else if ("DEAL".equals(flag)) {
			//거래명세
		    rowData.setValue("detail01", new String(bytemsg, 100, 15));  //계좌번호
		    rowData.setValue("detail02", new String(bytemsg, 115, 8));  //시작일
			rowData.setValue("detail03", new String(bytemsg, 123, 8));  //종료일
		    rowData.setValue("detail04", new String(bytemsg, 131, 5));  //거래일련번호
			rowData.setValue("detail05", new String(bytemsg, 136, 8));  //처리중일자
			rowData.setValue("detail06", new String(bytemsg, 144, 2));  //배열건수
        } 		
		return rowData;
		
	}
	
	/****************************************************************************
    * 전문을 byte로 잘라서 CommonEntity에 담는다
    * @param msg           	수신전문
	* @param num           	배열 건수
    * @return                List에 담은 전문
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public static List<CommonEntity> getArray(String msg, int num, String work_type) throws Exception{
	    byte[] bytemsg = msg.getBytes();
		List<CommonEntity> rowList = new ArrayList<CommonEntity>();
		if ("JAN".equals(work_type)) {
			sp = 110; //기준일자(8)+배열건수(2)
			for (int i=0; i<num; i++) {
			  CommonEntity rowData = new CommonEntity();
			  rowData.setValue("detail03", new String(bytemsg, sp, 15));  //계좌번호 
              rowData.setValue("detail04", new String(bytemsg, nextPoint(sp, 15), 13));  //계좌잔액
              rowData.setValue("detail05", new String(bytemsg, nextPoint(sp, 13), 4));  //처리결과코드
              rowData.setValue("detail06", new String(bytemsg, nextPoint(sp, 4), 24));  //처리결과메시지
			  /*
			  rowData.setValue("detail03", StringUtil.substringByte(msg,sp,nextPoint(sp, 15)));  //계좌번호 
			  rowData.setValue("detail04", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //계좌잔액
			  rowData.setValue("detail05", StringUtil.substringByte(msg,sp,nextPoint(sp,  4)));  //처리결과코드
			  rowData.setValue("detail06", StringUtil.substringByte(msg,sp,nextPoint(sp, 24)));  //처리결과메시지
			  */			
			  rowList.add(rowData);
		  }
		} else if ("DEAL".equals(work_type)) {
			sp = 146;
			for (int i=0; i<num; i++) {
			    CommonEntity rowData = new CommonEntity();
			    rowData.setValue("detail07", StringUtil.substringByte(msg,sp,nextPoint(sp,  8)));  //거래일자 
			    rowData.setValue("detail08", StringUtil.substringByte(msg,sp,nextPoint(sp,  2)));  //거래구분
			    rowData.setValue("detail09", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //거래금액
			    rowData.setValue("detail10", StringUtil.substringByte(msg,sp,nextPoint(sp, 13)));  //거래후잔액
				rowData.setValue("detail11", StringUtil.substringByte(msg,sp,nextPoint(sp, 20)));  //적요
			
				rowList.add(rowData);
			}
		}
		

		return rowList;
	}
	
	/****************************************************************************
    * byte로 자른 전문의 다음 인덱스
    * @param startPoint      시작인덱스
	* @param term           	잘린 byte 크기
    * @return                다음인덱스
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	private static int nextPoint(int startPoint, int term) throws Exception {
	    sp = startPoint + term;
	    return sp;
	}
	
	
	/****************************************************************************
    * 잔액조회 및 거래명세 리스트 구하기
    * @param msg      				수신전문
    * @return                일계와 누트가 담긴 리스트
    * @exception             Exception
    * @since                 1.0
    ***************************************************************************/
	public static List<CommonEntity>  getList(String msg, String flag) throws Exception {
		data = getData(msg, flag);
		int num = 0;
		if ("JAN".equals(flag))	{
			num = data.getInt("detail02");
		} else if ("DEAL".equals(flag))	{
			num = data.getInt("detail06");
		}
		arrayList = getArray(msg, num, flag);
		return arrayList;
	}
}
