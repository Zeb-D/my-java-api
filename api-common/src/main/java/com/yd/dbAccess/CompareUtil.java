package com.yd.dbAccess;

import java.util.*;

/**
 * 比较器
 *
 * @author Yd on  2017-11-30
 * @Description：
 **/
public class CompareUtil {
    public final static String baseColunms = "creater,createdate,changer,changedate";
    public final static Map<String, String> baseColunmsMap = new HashMap<>();

    static {
        baseColunmsMap.put("creater", "createdName");
        baseColunmsMap.put("createdate", "createdTime");
        baseColunmsMap.put("changer", "lastModifiedName");
        baseColunmsMap.put("changedate", "lastModifiedTime");
    }

    //大小写都有可能
    public static Map<String, String> containCollection(List<String> oracleColunms, List<String> mysqlColunms) {
        // 获取 oracle 中真正的 有效的字段
        List<String> result = new ArrayList<>();

        Set<String> valiadColunms = new HashSet<String>();
        for (int i = 0; i < oracleColunms.size(); i++) {
            for (int j = 0; j < mysqlColunms.size(); j++) {
                if (oracleColunms.get(i).equalsIgnoreCase(mysqlColunms.get(j))) {
                    baseColunmsMap.put(oracleColunms.get(i), mysqlColunms.get(j));
                    result.add(oracleColunms.get(i));
//                    valiadColunms.add(oracleColunms.get(i));
                    break;//查到就外循环
                }
            }

            if (oracleColunms.get(i).toLowerCase().contains("item")) {// item-goods
//                valiadColunms.add(oracleColunms.get(i));
                String temp = oracleColunms.get(i).toLowerCase().replace("item", "goods");
                for (int j = 0; j < mysqlColunms.size(); j++) {
                    if (temp.equalsIgnoreCase(mysqlColunms.get(j))) {
                        baseColunmsMap.put(oracleColunms.get(i), mysqlColunms.get(j));
                        result.add(oracleColunms.get(i));
//                    valiadColunms.add(oracleColunms.get(i));
                        break;//查到就外循环
                    }
                }
//                baseColunmsMap.put(oracleColunms.get(i), oracleColunms.get(i).toLowerCase().replace("item", "goods"));
//                oracleColunms.set(i, oracleColunms.get(i).toLowerCase().replace("item", "goods"));
            } else if (oracleColunms.get(i).toLowerCase().contains("quality")) {//quality - qty
                String temp = oracleColunms.get(i).toLowerCase().replace("quality", "qty");
                for (int j = 0; j < mysqlColunms.size(); j++) {
                    if (temp.equalsIgnoreCase(mysqlColunms.get(j))) {
                        baseColunmsMap.put(oracleColunms.get(i), mysqlColunms.get(j));
                        result.add(oracleColunms.get(i));
//                    valiadColunms.add(oracleColunms.get(i));
                        break;//查到就外循环
                    }
                }
//                valiadColunms.add(oracleColunms.get(i));
//                baseColunmsMap.put(oracleColunms.get(i), oracleColunms.get(i).toLowerCase().replace("quality", "qty"));
//                oracleColunms.set(i, oracleColunms.get(i).toLowerCase().replace("quality", "qty"));
            }

        }
        System.out.println(baseColunmsMap);
//        String s=baseColunmsMap.values();
        StringBuffer sb = new StringBuffer();
//        System.out.println(baseColunmsMap.values().toString());
//        for (String colunm : set) {
//            System.out.println(colunm);
//        }
        return baseColunmsMap;
    }

    public static List<String> getColunms(List<TableInfo> tableInfos) {
        List<String> result = new ArrayList<>();
        for (TableInfo tableInfo : tableInfos) result.add(tableInfo.getColumnName().toLowerCase());
        return result;
    }

    public static void main(String[] args) {
        String mysql = "goodsCode,goodsName,orgCode,orgName,enterPriseCode,enterPriseName,wareHouseCode,wareHouseName,isEnable,MPType,goodsStatus,isFreeze,isSplit,dutyOrgCode,isForceOrderReconFirm,isForceOrderInv,ordBatchQty,ordBatchPolicyType,isOrd,ordDate,ordDateTIme,ordGoodsAllocationFlag,isWrappageNeeded,wrappageGoodsCode,wrappageQty,packQty,packweight,isReturn,returnFinalDate,returnTerm,returnWareHouse,returnRate,DCShelfLife,cycleCode,statusType,statusChangeDate,entryDate,testMarketDate,marketDate,washOutDate,washOutTime,inDate,outDate,isOrderAuto,costProfitCentorSeq,isOOS,isRetail,baseTargetQty,discountAmt,discountRate,maxPrice,minPrice,recommendRemark,saleWareHouse,saleLocation,saleBinCode,isRecommend,recommendBeginDate,recommendEndDate,recommendPrice,recommendRetailPrice,priRetailRate,NewStorePrice,LowerStorePrice,upperStorePrice,isEditSalPrice,salPrice,salePrice,preRequireDays,minRequireQty,leastStockQty,mostStockQty,minAvgInvQty,maxAvgInvQty,maxStockQty,shelfLife,storageRack,AvgDay,isAddPreCycle,isLabel,preCycleCode,needDate,DHBeginDate,DHEndDate,createdName,createdCode,createdTime,lastModifiedName,lastModifiedCode,lastModifiedTime";
        String oracle = "ITEMCODE,ORGCODE,ENTERPRISECODE,WAREHOUSECODE,ORDBATCHQTY,ORDBATCHPOLICYTYPE,STANDARDBOXCONVERSION,CANORDORDERURGENTCHANGE,MIXCODE,ORDITEMALLOCATIONFLAG,ISORD,ISENABLED,MPTYPE,ISRETURN,ISWASHOUT,RETURNWAREHOUSE,ISWRAPPAGENEEDED,WRAPPAGEITEMCODE,WRAPPERQTY,STATUSTYPE,STATUSCHANGEDATE,ITEMSTATUS,ENTRYDATE,TESTMARKETDATE,MARKETDATE,WASHOUTDATE,RECOMMENDRETAILPRICE,RECOMMENDREMARK,RETURNFINALDATE,RETURNTERM,SALEWAREHOUSE,SALELOCATION,SALEBINCODE,CREATER,CREATEDATE,CHANGER,CHANGEDATE,INCOMERATE,STDCHARGEAMT,ISRECOMMEND,RECOMMENDPRICE,PRISALERATE,PRIRETAILRATE,LASTMONAVGSALEQTY,LASTMONAVGSALEAMT,PURPRICE,VENDORCODE,NEWSTOREPRICE,LOWERSTOREPRICE,UPPERSTOREPRICE,LASTMONAVGPROFIT,INDATE,OUTDATE,MANUFACTUREORG,HOURSRATION,ISORDERAUTO,FORMULACODE,COSTPROFITCENTORSEQ,SALPRICE,RETURNRATE,RECOMMENDBEGINDATE,RECOMMENDENDDATE,RESTRICTBEGINDATE,RESTRICTENDDATE,SALEPRICE,ISREVERT,ISINVITEM,ISDISFEE,CANORDORDERURGENTRATE,MONSUMSALEQTY1,MONSUMSALEQTY2,MONSUMSALEQTY3,MONSUMSALEAMT1,MONSUMSALEAMT2,MONSUMSALEAMT3,ISBYPRODUCT,MINPROFITRATE,ISFORCEORDERCONFIRM,ISFORCEORDERINV,ISEDITSALPRICE,ISOOS,DEDUCTVALUE,DEDUCTTYPE,ITEMATTRIBUTE,STANDARDOUTPERCENT,ISORIGINALMILK,ISORIGINALMATERIAL,ISASSISTSMATERIAL,ISPARTMATERIAL,STANDARUSEMILKQTY,ISFRUITSPECE,FRUITSPECESTANDARD,FRUITSPECECONTAINWATER,STANDARDDRYGOOGS,STANDARDPROTEID,ISFLAVOURPARTMATERIAL,ISBACILLIFLAG,PREREQUIREDAYS,MINREQUIREQTY,LEASTSTOCKQTY,MOSTSTOCKQTY,SEVENAVGORDQTY,MINAVGINVQTY,MAXAVGINVQTY,SHELFLIFE,LASTUPDATEUSERCODE,LASTUPDATETIME,DCSHELFLIFE,CYCLECODE,ISINCLUDEINSPECTIONQTY,ORDDATETIME,AVGDAY,PRECYCLECODE,ISFREEZE,ISSPLIT,ROUNDRATE,MINROUND,MAXSTOCKQTY,WASHOUTTIME,ISADDPRECYCLE,ISRETAIL,BASETARGETQTY,DISCOUNTAMT,DISCOUNTRATE,PACKQUALITY,PACKWEIGHT,MAXPRICE,MINPRICE,STORAGERACK,ORDDATE,DUTYORGCODE,DHBEGINDATE,DHENDDATE,NEEDDATE,ISLABEL";
        CompareUtil.containCollection(Arrays.asList(oracle.toLowerCase().split(",")), Arrays.asList(mysql.toLowerCase().split(",")));
        System.out.println("ITEMCODE".toLowerCase().contains("item"));
    }
}
