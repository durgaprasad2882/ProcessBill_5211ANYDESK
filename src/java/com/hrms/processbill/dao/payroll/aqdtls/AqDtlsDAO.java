package com.hrms.processbill.dao.payroll.aqdtls;

import com.hrms.processbill.model.payroll.aqdtls.AqDtlsModel;
import com.hrms.processbill.model.payroll.billbrowser.AqDtlsDedBean;
import java.util.List;

public interface AqDtlsDAO {

    public int saveAqdtlsDataBKP(AqDtlsModel[] dtls, boolean stopSubscription);

    public int saveAqdtlsData(AqDtlsModel[] dtls, boolean stopSubscription, String aqdtlsTableName);

    public AqDtlsModel getAqdtlsData(String aqslno);

    public List getAqdtlsList(String aqslno);

    public int deleteAqdtls(String aqslno);

    public void updateAqDtlsData(AqDtlsDedBean aqDtlsDedBean);

    public AqDtlsDedBean getAqDetailsDed(String aqslNo, String dedType, String nowdedn);

    public AqDtlsDedBean getAqDetailsAllowance(String aqslNo, String dedType);

}
