/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.processbill.dao.payroll.billbrowser;

import com.hrms.processbill.SelectOption;
import com.hrms.processbill.common.CommonFunctions;
import com.hrms.processbill.common.DataBaseFunctions;
import com.hrms.processbill.model.payroll.billbrowser.BillGroup;
import com.hrms.processbill.model.payroll.grouppayfixation.GroupPayFixation;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author Manas Jena
 */
public class BillGroupDAOImpl implements BillGroupDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ArrayList getBillGroupList(String offcode) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList billGroupList = new ArrayList();
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM BILL_GROUP_MASTER WHERE OFF_CODE=?");
            pstmt.setString(1, offcode);
            rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                i++;
                BillGroup billGroup = new BillGroup();
                billGroup.setSlno(i);
                billGroup.setBillgroupid(rs.getBigDecimal("bill_group_id"));
                billGroup.setBillgroupdesc(rs.getString("description"));
                billGroup.setDemandNo(rs.getString("demand_no"));
                billGroup.setMajorHead(rs.getString("major_head"));
                billGroup.setMajorHeadDesc(rs.getString("major_head_desc"));
                billGroup.setSubMajorHead(rs.getString("sub_major_head"));
                billGroup.setSubMajorHeadDesc(rs.getString("sub_major_head_desc"));
                billGroup.setMinorHead(rs.getString("minor_head"));
                billGroup.setMinorHeadDesc(rs.getString("minor_head_desc"));
                billGroup.setSubMinorHead1(rs.getString("sub_minor_head1"));
                billGroup.setSubMinorHeadDesc1(rs.getString("sub_minor_head1_desc"));
                billGroup.setSubMinorHead2(rs.getString("sub_minor_head2"));
                billGroup.setSubMinorHeadDesc2(rs.getString("sub_minor_head2_desc"));
                billGroup.setSubMinorHead3(rs.getString("sub_minor_head3"));
                billGroupList.add(billGroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return billGroupList;
    }
    @Override
    public ArrayList getActiveBillGroupList(String offcode){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList billGroupList = new ArrayList();
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM BILL_GROUP_MASTER WHERE OFF_CODE=? AND (IS_DELETED IS  NULL OR IS_DELETED='N') ORDER BY DESCRIPTION");
            pstmt.setString(1, offcode);
            rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                i++;
                BillGroup billGroup = new BillGroup();
                billGroup.setSlno(i);
                billGroup.setBillgroupid(rs.getBigDecimal("bill_group_id"));
                billGroup.setBillgroupdesc(rs.getString("description"));
                billGroup.setDemandNo(rs.getString("demand_no"));
                billGroup.setMajorHead(rs.getString("major_head"));
                billGroup.setMajorHeadDesc(rs.getString("major_head_desc"));
                billGroup.setSubMajorHead(rs.getString("sub_major_head"));
                billGroup.setSubMajorHeadDesc(rs.getString("sub_major_head_desc"));
                billGroup.setMinorHead(rs.getString("minor_head"));
                billGroup.setMinorHeadDesc(rs.getString("minor_head_desc"));
                billGroup.setSubMinorHead1(rs.getString("sub_minor_head1"));
                billGroup.setSubMinorHeadDesc1(rs.getString("sub_minor_head1_desc"));
                billGroup.setSubMinorHead2(rs.getString("sub_minor_head2"));
                billGroup.setSubMinorHeadDesc2(rs.getString("sub_minor_head2_desc"));
                billGroup.setSubMinorHead3(rs.getString("sub_minor_head3"));
                billGroupList.add(billGroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return billGroupList;
    }
    
    

    @Override
    public BillGroup getBillGroupDetails(BigDecimal billGroupId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BillGroup billGroup = new BillGroup();
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM BILL_GROUP_MASTER WHERE BILL_GROUP_ID=?");
            pstmt.setBigDecimal(1, billGroupId);
            rs = pstmt.executeQuery();
            int i = 0;
            if (rs.next()) {
                billGroup.setOffcode(rs.getString("OFF_CODE"));
                billGroup.setPlan(rs.getString("PLAN"));
                billGroup.setSector(rs.getString("SECTOR"));
                billGroup.setBillgroupid(rs.getBigDecimal("bill_group_id"));
                billGroup.setBillgroupdesc(rs.getString("description"));
                billGroup.setDemandNo(rs.getString("demand_no"));
                billGroup.setMajorHead(rs.getString("major_head"));
                billGroup.setMajorHeadDesc(rs.getString("major_head_desc"));
                billGroup.setSubMajorHead(rs.getString("sub_major_head"));
                billGroup.setSubMajorHeadDesc(rs.getString("sub_major_head_desc"));
                billGroup.setMinorHead(rs.getString("minor_head"));
                billGroup.setMinorHeadDesc(rs.getString("minor_head_desc"));
                billGroup.setSubMinorHead1(rs.getString("sub_minor_head1"));
                billGroup.setSubMinorHeadDesc1(rs.getString("sub_minor_head1_desc"));
                billGroup.setSubMinorHead2(rs.getString("sub_minor_head2"));
                billGroup.setSubMinorHeadDesc2(rs.getString("sub_minor_head2_desc"));
                billGroup.setSubMinorHead3(rs.getString("sub_minor_head3"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }

        return billGroup;
    }

    @Override
    public ArrayList getPlanStatusList() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList planStatusList = new ArrayList();
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM g_plan_status");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("description"));
                so.setLabel(rs.getString("plan_descpn"));
                planStatusList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return planStatusList;
    }

    @Override
    public ArrayList getSectorList() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList billSectorList = new ArrayList();

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM g_sector");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("sector_code"));
                so.setLabel(rs.getString("sector_desc"));
                billSectorList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return billSectorList;
    }

    @Override
    public ArrayList getPostClassList() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList billPostClassList = new ArrayList();

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM g_post_class");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("post_class"));
                so.setLabel(rs.getString("post_class"));
                billPostClassList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return billPostClassList;
    }

    @Override
    public ArrayList getBillTypeList() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList billTypeList = new ArrayList();

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM g_bill_type");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("bill_type"));
                so.setLabel(rs.getString("bill_desc"));
                billTypeList.add(so);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return billTypeList;
    }

    @Override
    public ArrayList getDemandList() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList billDemandList = new ArrayList();
        String desc = null;
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT DEMAND_NUMBER,DESCRIPTION FROM (SELECT DISTINCT DEMAND_NUMBER FROM G_CHART_OF_ACCOUNT ORDER BY DEMAND_NUMBER)T1 INNER JOIN G_DEMAND_NO ON  T1.DEMAND_NUMBER=G_DEMAND_NO.DEMAND_NO");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("DEMAND_NUMBER"));
                desc = rs.getString("DESCRIPTION") + "(" + rs.getString("DEMAND_NUMBER") + ")";
                so.setLabel(desc);
                billDemandList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return billDemandList;
    }

    @Override
    public ArrayList getMajorHeadList(String demandno) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList majorHeadList = new ArrayList();

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT DISTINCT MAJOR_HEAD FROM G_CHART_OF_ACCOUNT WHERE DEMAND_NUMBER=?");
            pstmt.setString(1, demandno);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("MAJOR_HEAD"));
                so.setLabel(rs.getString("MAJOR_HEAD"));
                majorHeadList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return majorHeadList;
    }

    @Override
    public ArrayList getSubMajorHeadList(String demandNo, String majorhead) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList subMajorHeadList = new ArrayList();

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT DISTINCT SUBMAJOR_HEAD FROM G_CHART_OF_ACCOUNT WHERE DEMAND_NUMBER=? AND MAJOR_HEAD=?");
            pstmt.setString(1, demandNo);
            pstmt.setString(2, majorhead);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("SUBMAJOR_HEAD"));
                so.setLabel(rs.getString("SUBMAJOR_HEAD"));
                subMajorHeadList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return subMajorHeadList;
    }

    @Override
    public ArrayList getMinorHeadList(String majorHead, String submajorhead) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList minorHeadList = new ArrayList();

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT DISTINCT MINOR_HEAD FROM G_CHART_OF_ACCOUNT WHERE SUBMAJOR_HEAD=? AND MAJOR_HEAD=?");
            pstmt.setString(1, submajorhead);
            pstmt.setString(2, majorHead);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("MINOR_HEAD"));
                so.setLabel(rs.getString("MINOR_HEAD"));
                minorHeadList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return minorHeadList;
    }

    @Override
    public ArrayList getSubMinorHeadList(String subMajorHead, String minorhead) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList subMinorHeadList = new ArrayList();

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT DISTINCT SUB_HEAD FROM G_CHART_OF_ACCOUNT WHERE MINOR_HEAD=? AND SUBMAJOR_HEAD=?");
            pstmt.setString(1, minorhead);
            pstmt.setString(2, subMajorHead);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("SUB_HEAD"));
                so.setLabel(rs.getString("SUB_HEAD"));
                subMinorHeadList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return subMinorHeadList;
    }

    @Override
    public ArrayList getDetailHeadList(String minorhead, String subhead) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList detailHeadList = new ArrayList();

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT DISTINCT DETAIL_HEAD FROM G_CHART_OF_ACCOUNT WHERE SUB_HEAD=? AND MINOR_HEAD=?");
            pstmt.setString(1, subhead);
            pstmt.setString(2, minorhead);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("DETAIL_HEAD"));
                so.setLabel(rs.getString("DETAIL_HEAD"));
                detailHeadList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return detailHeadList;
    }

    @Override
    public ArrayList getChargedVotedList(String detailhead, String subminorhead) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList chargedVotedList = new ArrayList();

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT DISTINCT CHARGED_VOTED FROM G_CHART_OF_ACCOUNT WHERE DETAIL_HEAD=? AND SUB_HEAD=?");
            // System.out.println("SELECT DISTINCT CHARGED_VOTED FROM G_CHART_OF_ACCOUNT WHERE DETAIL_HEAD='"+subminorhead+"' AND SUB_HEAD='"+detailhead+"'");
            pstmt.setString(1, detailhead);
            pstmt.setString(2, subminorhead);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                // System.out.println("subminorhead"+subminorhead+"detailhead"+detailhead);
                SelectOption so = new SelectOption();
                so.setValue(rs.getString("CHARGED_VOTED"));
                so.setLabel(rs.getString("CHARGED_VOTED"));
                chargedVotedList.add(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return chargedVotedList;
    }

    @Override
    public ArrayList getSectionList(String billGroupId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList sectionList = new ArrayList();
        GroupPayFixation groupPayFix = null;
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT SECTION_ID FROM BILL_SECTION_MAPPING WHERE BILL_GROUP_ID=?");
            pstmt.setDouble(1, Double.parseDouble(billGroupId));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                groupPayFix = new GroupPayFixation();
                groupPayFix.setSectionId(rs.getString("SECTION_ID"));
                sectionList.add(groupPayFix);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return sectionList;
    }

    @Override
    public String getConfigurationLvl(BigDecimal billGroupId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String configLvl = null;
        try {
            con = dataSource.getConnection();            
            pstmt = con.prepareStatement("SELECT CONFIGURED_LVL FROM BILL_GROUP_MASTER WHERE BILL_GROUP_ID=?");
            pstmt.setBigDecimal(1, billGroupId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                configLvl = rs.getString("CONFIGURED_LVL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return configLvl;
    }

    
}
