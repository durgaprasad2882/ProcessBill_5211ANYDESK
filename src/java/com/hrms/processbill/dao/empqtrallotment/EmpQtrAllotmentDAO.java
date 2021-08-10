/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.processbill.dao.empqtrallotment;

import com.hrms.processbill.model.employee.QuaterAllotment;

/**
 *
 * @author Manas
 */
public interface EmpQtrAllotmentDAO {
    public QuaterAllotment[] getQuaterAllotmentDetail(String empId);
}
