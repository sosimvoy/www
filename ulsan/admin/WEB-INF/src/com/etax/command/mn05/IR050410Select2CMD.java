/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050410Select2CMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금(재)배정통지서조회-자금배정내역
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050410;
import com.etax.entity.CommonEntity;


public class IR050410Select2CMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050410Select2CMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("doc_no",           request.getParameter("doc_no"));
		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
        paramInfo.setValue("acc_date",         request.getParameter("acc_date"));
        paramInfo.setValue("doc_code",         request.getParameter("doc_code"));

        if ("ED05".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",     "1");
        } else if ("ED06".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",     "2");
        } else {
            paramInfo.setValue("report_gubun",     "1");
        }

        logger.info("paramInfo : " + paramInfo);
        
        List<CommonEntity> boyuReportList1 = new ArrayList<CommonEntity>();

          /* 현금보유현황- 현금보유잔액(전체) */
        List<CommonEntity> dataList1 = IR050410.getBoyuJan(conn, paramInfo);
        long boyu_amt = 0L; 
        long gwan_boyu = 0L;  //관서합계(배정현금보유액)
        long gwan_re_boyu = 0L;  //관서합계(재배정현금보유액)

        for (int i=0; i<dataList1.size(); i++) {
            CommonEntity rowData = (CommonEntity)dataList1.get(i);
            rowData.setValue("M360_ACCCODE",                rowData.getString("M360_ACCCODE"));
            rowData.setValue("M360_ACCNAME",                rowData.getString("M360_ACCNAME"));
            rowData.setValue("M170_OFFICIALDEPOSITJANAMT",  rowData.getString("M170_OFFICIALDEPOSITJANAMT")); //현금보유잔액
            if (!"".equals(rowData.getString("M360_ACCCODE")) ) {
                paramInfo.setValue("M360_ACCCODE",       rowData.getString("M360_ACCCODE"));
                CommonEntity allotAmt = IR050410.getBoyuAllotAmt(conn, paramInfo);
                rowData.setValue("M100_ALLOTAMT",      allotAmt.getString("M100_ALLOTAMT"));  //금회배정액
            } else {
                rowData.setValue("M100_ALLOTAMT",      "0");
            }
            boyu_amt = rowData.getLong("M170_OFFICIALDEPOSITJANAMT") + rowData.getLong("M100_ALLOTAMT");
            rowData.setValue("M_BOYU",       boyu_amt+"");  //현금보유액
            boyuReportList1.add(rowData);

            if ("1".equals(paramInfo.getString("report_gubun")) && "21".equals(rowData.getString("M360_ACCCODE")) ) {
                CommonEntity gwanData = IR050410.getGwanBoyuJan(conn, paramInfo);
                gwanData.setValue("M360_ACCCODE",                gwanData.getString("M360_ACCCODE"));
                gwanData.setValue("M360_ACCNAME",                gwanData.getString("M360_ACCNAME"));
                gwanData.setValue("M170_OFFICIALDEPOSITJANAMT",  gwanData.getString("M170_OFFICIALDEPOSITJANAMT"));
                if (!"".equals(gwanData.getString("M360_ACCCODE")) ) {
                    paramInfo.setValue("M360_ACCCODE",      gwanData.getString("M360_ACCCODE"));
                    CommonEntity allotAmt1 = IR050410.getBoyuAllotAmt(conn, paramInfo);
                    gwanData.setValue("M100_ALLOTAMT",      allotAmt1.getString("M100_ALLOTAMT"));  //금회배정액
                } else {
                    gwanData.setValue("M100_ALLOTAMT",      "0");
                }
                gwan_boyu = gwanData.getLong("M170_OFFICIALDEPOSITJANAMT") + gwanData.getLong("M100_ALLOTAMT");
                gwanData.setValue("M_BOYU",       gwan_boyu+"");  //현금보유액
                boyuReportList1.add(gwanData);
            }

            if ("2".equals(paramInfo.getString("report_gubun")) && "00".equals(rowData.getString("M360_ACCCODE")) ) {
                CommonEntity reBon =new CommonEntity();
                //본청 자료 0으로 셋팅
                reBon.setValue("M360_ACCCODE",                "21");
                reBon.setValue("M360_ACCNAME",                "본 청");
                reBon.setValue("M170_OFFICIALDEPOSITJANAMT",  "0"); //현금보유잔액
                reBon.setValue("M100_ALLOTAMT",               "0"); //금회배정액
                reBon.setValue("M_BOYU",                      "0"); //현금보유액

                boyuReportList1.add(reBon);
            
                //재배정 관서합계
                CommonEntity gwanData1 = IR050410.getGwanBoyuJan(conn, paramInfo);
                gwanData1.setValue("M360_ACCCODE",                gwanData1.getString("M360_ACCCODE"));
                gwanData1.setValue("M360_ACCNAME",                gwanData1.getString("M360_ACCNAME").trim());
                gwanData1.setValue("M170_OFFICIALDEPOSITJANAMT",  gwanData1.getString("M170_OFFICIALDEPOSITJANAMT"));
                if (!"".equals(gwanData1.getString("M360_ACCCODE")) ) {
                    paramInfo.setValue("M360_ACCCODE",       gwanData1.getString("M360_ACCCODE"));
                    CommonEntity allotAmt2 = IR050410.getBoyuAllotAmt(conn, paramInfo);
                    gwanData1.setValue("M100_ALLOTAMT",      allotAmt2.getString("M100_ALLOTAMT"));  //금회배정액
                } else {
                    gwanData1.setValue("M100_ALLOTAMT",      "0");
                }
                gwan_re_boyu = gwanData1.getLong("M170_OFFICIALDEPOSITJANAMT") + gwanData1.getLong("M100_ALLOTAMT");
                gwanData1.setValue("M_BOYU",       gwan_re_boyu+"");  //현금보유액
                boyuReportList1.add(gwanData1);
            }
        }

        request.setAttribute("page.mn05.boyuReportList1", boyuReportList1); //보유현황(관서합계 제외)


        if ("ED00".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",     "2");
            List<CommonEntity> boyuReportList2 = new ArrayList<CommonEntity>();

            /* 현금보유현황- 현금보유잔액(전체) */
            List<CommonEntity> dataList2 = IR050410.getBoyuJan(conn, paramInfo);
            boyu_amt = 0L; 
            gwan_boyu = 0L;  //관서합계(배정현금보유액)
            gwan_re_boyu = 0L;  //관서합계(재배정현금보유액)

            for (int i=0; i<dataList2.size(); i++) {
                CommonEntity rowData = (CommonEntity)dataList2.get(i);
                rowData.setValue("M360_ACCCODE",                rowData.getString("M360_ACCCODE"));
                rowData.setValue("M360_ACCNAME",                rowData.getString("M360_ACCNAME"));
                rowData.setValue("M170_OFFICIALDEPOSITJANAMT",  rowData.getString("M170_OFFICIALDEPOSITJANAMT")); //현금보유잔액
                if (!"".equals(rowData.getString("M360_ACCCODE")) ) {
                    paramInfo.setValue("M360_ACCCODE",       rowData.getString("M360_ACCCODE"));
                    CommonEntity allotAmt = IR050410.getBoyuAllotAmt(conn, paramInfo);
                    rowData.setValue("M100_ALLOTAMT",      allotAmt.getString("M100_ALLOTAMT"));  //금회배정액
                } else {
                    rowData.setValue("M100_ALLOTAMT",      "0");
                }
                boyu_amt = rowData.getLong("M170_OFFICIALDEPOSITJANAMT") + rowData.getLong("M100_ALLOTAMT");
                rowData.setValue("M_BOYU",       boyu_amt+"");  //현금보유액
                boyuReportList2.add(rowData);

                if ("2".equals(paramInfo.getString("report_gubun")) && "00".equals(rowData.getString("M360_ACCCODE")) ) {
                    CommonEntity reBon =new CommonEntity();
                    //본청 자료 0으로 셋팅
                    reBon.setValue("M360_ACCCODE",                "21");
                    reBon.setValue("M360_ACCNAME",                "본 청");
                    reBon.setValue("M170_OFFICIALDEPOSITJANAMT",  "0"); //현금보유잔액
                    reBon.setValue("M100_ALLOTAMT",               "0"); //금회배정액
                    reBon.setValue("M_BOYU",                      "0"); //현금보유액

                    boyuReportList2.add(reBon);
                
                    //재배정 관서합계
                    CommonEntity gwanData1 = IR050410.getGwanBoyuJan(conn, paramInfo);
                    gwanData1.setValue("M360_ACCCODE",                gwanData1.getString("M360_ACCCODE"));
                    gwanData1.setValue("M360_ACCNAME",                gwanData1.getString("M360_ACCNAME").trim());
                    gwanData1.setValue("M170_OFFICIALDEPOSITJANAMT",  gwanData1.getString("M170_OFFICIALDEPOSITJANAMT"));
                    if (!"".equals(gwanData1.getString("M360_ACCCODE")) ) {
                        paramInfo.setValue("M360_ACCCODE",       gwanData1.getString("M360_ACCCODE"));
                        CommonEntity allotAmt2 = IR050410.getBoyuAllotAmt(conn, paramInfo);
                        gwanData1.setValue("M100_ALLOTAMT",      allotAmt2.getString("M100_ALLOTAMT"));  //금회배정액
                    } else {
                        gwanData1.setValue("M100_ALLOTAMT",      "0");
                    }
                    gwan_re_boyu = gwanData1.getLong("M170_OFFICIALDEPOSITJANAMT") + gwanData1.getLong("M100_ALLOTAMT");
                    gwanData1.setValue("M_BOYU",       gwan_re_boyu+"");  //현금보유액
                    boyuReportList2.add(gwanData1);
                }
            }

            request.setAttribute("page.mn05.boyuReportList2", boyuReportList2); //보유현황(관서합계 제외)
        }

    }
}