/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.processbill.dao.login;


import com.hrms.processbill.model.login.AdminUsers;
import com.hrms.processbill.model.login.UserDetails;
import com.hrms.processbill.model.login.UserExpertise;
import com.hrms.processbill.model.login.Users;

import java.util.Map;

/**
 *
 * @author Surendra
 */
public interface LoginDAO {

    Users findByUserName(String username);

    public Users getEmployeeProfileInfo(String hrmsid);

 

    public UserDetails checkLogin(String userid, String pwd);


    public AdminUsers getAdminUsersProfileInfo(String userid);

    public int requestPassword(Map<String, String> params);

    public Users getInstituteInfo(String hrmsId);

    public void updateCadreStatus(String empId, String cadreStat, String subCadreStat);

    public AdminUsers getDeptUsersProfileInfo(String userid);

    public UserExpertise getUserInfo(String hrmsid);

    public boolean countExpertise(String empid);



    public boolean getEligibility(String postCode);

    public String getCurrentTrainingProgram();

}
