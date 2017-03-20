package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.recap.model.usermanagement.UserForm;
import org.recap.security.UserManagementService;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;

import org.recap.RecapConstants;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by hemalathas on 1/2/17.
 */
public class LoginControllerUT extends BaseControllerUT{

    @Autowired
    LoginController loginController;

    @Mock
    HttpSession session;

    @Mock
    javax.servlet.http.HttpServletRequest request;

    @Mock
    private UserAuthUtil userAuthUtil;

    @Mock
    Model model;

    @Mock
    BindingResult error;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void loginScreenTest(){
        String response = loginController.loginScreen(request,model,new UserForm());
        assertNotNull(response);
        assertEquals(response,"login");
    }

    @Test
    public void logOutTest() throws Exception {
        when(request.getSession()).thenReturn(session);
        usersSessionAttributes();
        String response = loginController.logoutUser(request);
        assertNotNull(response);
    }

    @Test
    public void createSessionTest(){
        UserForm userForm = new UserForm();
        String response = loginController.createSession(userForm,request,model,error);
        assertNotNull(response);
        assertEquals(response,"login");
    }


    private void usersSessionAttributes() throws Exception {
        when(request.getSession()).thenReturn(session);
        UserForm userForm = new UserForm();
        userForm.setUsername("SuperAdmin");
        userForm.setInstitution("1");
        userForm.setPassword("12345");
        UsernamePasswordToken token=new UsernamePasswordToken(userForm.getUsername()+ RecapConstants.TOKEN_SPLITER +userForm.getInstitution(),userForm.getPassword(),true);
        userAuthUtil.doAuthentication(token);
        when(session.getAttribute(RecapConstants.USER_TOKEN)).thenReturn(token);
        when(session.getAttribute(RecapConstants.USER_ID)).thenReturn(3);
        when(session.getAttribute(RecapConstants.SUPER_ADMIN_USER)).thenReturn(false);
        when(session.getAttribute(RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(false);
        when(session.getAttribute(RecapConstants.REQUEST_ITEM_PRIVILEGE)).thenReturn(false);
        when(session.getAttribute(RecapConstants.USER_INSTITUTION)).thenReturn(1);
        when(session.getAttribute(RecapConstants.REQUEST_ALL_PRIVILEGE)).thenReturn(false);
        userAuthUtil.getUserDetails(session,RecapConstants.BARCODE_RESTRICTED_PRIVILEGE);
    }

}