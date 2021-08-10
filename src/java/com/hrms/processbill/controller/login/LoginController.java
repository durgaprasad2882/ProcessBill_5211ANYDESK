/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.processbill.controller.login;

import com.hrms.processbill.dao.login.LoginDAO;


import com.hrms.processbill.model.login.AdminUsers;
import com.hrms.processbill.model.login.LoginUserBean;
import com.hrms.processbill.model.login.UserDetails;
import com.hrms.processbill.model.login.UserExpertise;
import com.hrms.processbill.model.login.Users;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Surendra
 */
@Controller
@SessionAttributes({"LoginUserBean", "Privileges"})
public class LoginController implements ServletContextAware {

    @Autowired
    public LoginDAO loginDao;

    private ServletContext context;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.context = servletContext;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String viewLogin(Map<String, Object> model) {
        Users user = new Users();
        model.put("loginForm", user);
        return "index";
    }

    @RequestMapping(value = "redirectToLoginPage", method = RequestMethod.GET)
    public void viewLoginSessionOut(Map<String, Object> model, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<script language=\"JavaScript\" type=\"text/javascript\">");
            out.println("document.location = \"http://google.com\"");
            out.println("</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    
     @RequestMapping(value = {"/", "/home"})
     public String getUserDefault() {
     return "home";
     }
    
    
     @RequestMapping("/login")
     public String getLoginForm(WebRequest webRequest, @ModelAttribute("login") Users login, Errors errors, ModelMap model,
     @RequestParam(value = "error", required = false) String error,
     @RequestParam(value = "logout", required = false) String logout) {
     Object lastException = webRequest.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, WebRequest.SCOPE_SESSION);
     System.out.println("lastException " + lastException);
     if (lastException != null && !lastException.equals("")) {
     System.out.println("lastException error name == " + lastException.toString());
     }
     String message = "";
     if (error != null) {
     model.put("error", "true");
     message = "Incorrect username or password !";
     } else if (logout != null) {
     message = "Logout successful !";
     }
     return "login";
     }
    
     */

    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView doLogin(@Valid @ModelAttribute("loginForm") Users loginForm, BindingResult result, ModelMap model, HttpServletRequest request) throws UnknownHostException {
        ModelAndView mav = new ModelAndView();
        String path = "index";
        LoginUserBean lub = new LoginUserBean();
        String empid = "";
        UserDetails ud = null;
        HttpSession session = request.getSession(true);
        InetAddress IP = InetAddress.getLocalHost();
        String curIP = IP.getHostAddress();

        try {
            String captcha = (String) session.getAttribute("CAPTCHA");
            if (captcha != null && captcha.equalsIgnoreCase(loginForm.getCaptcha())) {

                ud = loginDao.checkLogin(loginForm.getUserName(), loginForm.getUserPassword());

                if (ud != null && ud.getLinkid() != null && !ud.getLinkid().equals("")) {
                    if (ud.getAccountnonlocked() == 0) {
                        model.addAttribute("errorMsg", "Your Account has been locked");
                        path = "index";
                    } else if (ud.getAccountnonexpired() == 0) {
                        model.addAttribute("errorMsg", "Your Account has been expired");
                        path = "index";
                    } else if (ud.getUsertype().equalsIgnoreCase("G")) {
                        Users emp = loginDao.getEmployeeProfileInfo(ud.getLinkid());
                        UserExpertise ue = loginDao.getUserInfo(ud.getLinkid());

                        lub.setEmpid(emp.getEmpId());
                        lub.setLoginname(emp.getFullName());
                        lub.setMobile(emp.getMobile());
                        lub.setGpfno(emp.getGpfno());
                        emp.setUsertype("G");
                        lub.setDeptcode(emp.getDeptcode());
                        lub.setOffcode(emp.getOffcode());
                        lub.setOffname(emp.getOffname());
                        lub.setSpc(emp.getCurspc());
                        lub.setSpn(emp.getSpn());
                        lub.setGpc(emp.getGpc());
                        lub.setUserid(loginForm.getUserName());
                        lub.setUsername(emp.getFullName());
                        lub.setHasofficePriv(emp.getHasPrivilages());
                        mav.addObject("users", emp);
                        mav.addObject("LoginUserBean", lub);
                        boolean hasFilled = loginDao.countExpertise(ud.getLinkid());
                        String postCode = emp.getPostCode();
                        boolean isEligible = loginDao.getEligibility(postCode);
                        mav.addObject("isEligible", isEligible);
                        mav.addObject("userinfo", ue);
                        mav.addObject("hasFilled", hasFilled);
                        mav.addObject("curip", curIP);
                        path = "/tab/mypage";
                    } else if (ud.getUsertype().equalsIgnoreCase("I")) {
                        Users lam = loginDao.getInstituteInfo(ud.getLinkid());
                        lub.setEmpid(lam.getEmpId());
                        lub.setOffname(lam.getFullName());

                        mav.addObject("users", lam);
                        mav.addObject("LoginUserBean", lub);
                        path = "/trainingadmin/InstitutionDashboard";
                    } else if (ud.getUsertype().equalsIgnoreCase("P")) {//Deptment mis data entry
                        String commUserName = loginForm.getUserName();
                        if (commUserName.equals("opsc.gad")) {
                            AdminUsers adm = loginDao.getDeptUsersProfileInfo(ud.getLinkid());
                            mav.addObject("users", adm);
                            lub.setEmpid(ud.getLinkid());
                            lub.setOffname("Odisha Public Service Commission");
                            lub.setLoginname("Odisha Public Service Commission");
                            lub.setUserid("2468592");
                            mav.addObject("LoginUserBean", lub);

                            path = "/tab/commissionadmin";
                        } else if (commUserName.equals("ossc.gad")) {
                            AdminUsers adm = loginDao.getDeptUsersProfileInfo(ud.getLinkid());
                            mav.addObject("users", adm);
                            lub.setEmpid(ud.getLinkid());
                            lub.setOffname("Odisha Staff Selection Commission");
                            lub.setLoginname("Odisha Staff Selection Commission");
                            lub.setUserid("2468591");
                            mav.addObject("LoginUserBean", lub);

                            path = "/tab/commissionadmin";
                        } else if (commUserName.equals("osssc.gad")) {
                            AdminUsers adm = loginDao.getDeptUsersProfileInfo(ud.getLinkid());
                            mav.addObject("users", adm);
                            lub.setEmpid(ud.getLinkid());
                            lub.setOffname("Odisha Sub-ordinate Staff Selection Commission");
                            lub.setLoginname("Odisha Sub-ordinate Staff Selection Commission");
                            lub.setUserid("2468593");
                            mav.addObject("LoginUserBean", lub);

                            path = "/tab/commissionadmin";
                        } else {
                            AdminUsers adm = loginDao.getDeptUsersProfileInfo(ud.getLinkid());
                            mav.addObject("users", adm);
                            lub.setEmpid(ud.getLinkid());
                            lub.setOffname(adm.getOffName());
                            lub.setLoginname(adm.getFullName());
							lub.setUsertype("Dept");
                            //  System.out.println("()()()" + adm.getEmpId());
                            mav.addObject("LoginUserBean", lub);

                            if (ud.getLinkid().equals("00")) {
                                path = "/tab/deptmis";
                            } else {
                                path = "/tab/deptadmin";
                            }
                        }
                    } else {
                        path = "index";
                    }

                } else {
                    model.addAttribute("errorMsg", "Invalid User or Password");
                    path = "index";
                }
            } else {
                loginForm.setCaptcha("");

                model.addAttribute("errorMsg", "Security Code does not match");
                path = "index";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        mav.setViewName(path);
        return mav;
    }
    /*
    
     @RequestMapping("/admin**")
     public String getAdminProfile() {
     return "admin";
     }

     @RequestMapping("/user**")
     public String getUserProfile() {
     return "user";
     }

     @RequestMapping("/403")
     public ModelAndView getAccessDenied() {
     Authentication auth = SecurityContextHolder.getContext()
     .getAuthentication();
     String username = "";
     if (!(auth instanceof AnonymousAuthenticationToken)) {
     if (auth!= null) {
     UserDetails userDetail = (UserDetails) auth.getPrincipal();
     username = userDetail.getUsername();
     }else{
     System.out.println("auth is null handle=====");
     }
     }

     return new ModelAndView("403", "username", username);
     }
    
    
     */

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    /*
     * Download a file from
     *   - inside project, located in resources folder.
     *   - outside project, located in File system somewhere.
     */
    @RequestMapping(value = "displayprofilephoto", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @RequestParam("empid") String empid) throws IOException {
        int BUFFER_LENGTH = 4096;
        String filePath = context.getInitParameter("PhotoPath");
        String filename = empid + ".jpg";
        File file = new File(filePath + filename);
        response.setContentType("image/gif");
        if (file.exists()) {
            response.setContentLength((int) file.length());
            FileInputStream is = new FileInputStream(file);
            ServletOutputStream os = response.getOutputStream();
            byte[] bytes = new byte[BUFFER_LENGTH];
            int read = 0;
            while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
                os.write(bytes, 0, read);
            }
            os.flush();
            is.close();
            os.close();
        }
    }

    @ResponseBody
    @RequestMapping(value = "ForgotPassword", method = RequestMethod.POST)
    public void ForgotPassword(HttpServletResponse response, @RequestParam Map<String, String> requestParams) {

        response.setContentType("text/html");
        int status = 0;
        PrintWriter out = null;
        try {

            status = loginDao.requestPassword(requestParams);
            //System.out.println("Status is: "+status);
            out = response.getWriter();
            out.write(status + "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }

}
