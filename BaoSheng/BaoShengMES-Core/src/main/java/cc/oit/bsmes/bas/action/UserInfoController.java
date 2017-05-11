package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by joker on 2014/8/15 0015.
 */
@Controller
@RequestMapping("/bas/userInfo")
public class UserInfoController {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private UserService userService;

    @RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "userInfo");
        return "bas.userInfo";
    }

    @ResponseBody
    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
    public JSONObject getUserInfo(){
        JSONObject userInfo = new JSONObject();
        User user = SessionUtils.getUser();

        Employee employee = employeeService.getByUserCode(user.getUserCode());

        userInfo.put("employeeId",employee.getId());
        userInfo.put("name",employee.getName());
        userInfo.put("email",employee.getEmail());
        userInfo.put("telephone",employee.getTelephone());
        userInfo.put("certificate",employee.getCertificate());

        userInfo.put("userId",user.getId());
        userInfo.put("password",user.getPassword());

        return userInfo;
    }

    @ResponseBody
    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    public JSON updateUserInfo(HttpServletRequest request){
        String employeeId = request.getParameter("employeeId");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        Employee employee = new Employee();
        employee.setId(employeeId);
        boolean isUpdate = false;
        if(StringUtils.isNotBlank(email)){
            employee.setEmail(email);
            isUpdate = true;
        }

        if(StringUtils.isNotBlank(telephone)){
            employee.setTelephone(telephone);
            isUpdate = true;
        }
        if(isUpdate){
            employeeService.update(employee);
        }

        isUpdate = false;
        User user = new User();
        user.setId(request.getParameter("userId"));
        String password = request.getParameter("newPassword");
        if(StringUtils.isNotBlank(password)){
            user.setPassword(password);
            user.setModifyUserCode(SessionUtils.getUser().getUserCode());
            isUpdate = true;
        }

        if(isUpdate){
            userService.update(user);
        }
        return JSONArrayUtils.ajaxJsonResponse(true,"");
    }
}
