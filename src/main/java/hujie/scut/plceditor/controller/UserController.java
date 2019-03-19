package hujie.scut.plceditor.controller;

import hujie.scut.plceditor.service.UserService;
import hujie.scut.plceditor.model.User;
import hujie.scut.plceditor.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;

import java.io.File;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录验证
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {

        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());//使用md5加密
        User user = new User();
        user.setUsername(username);
        user.setPassword(md5Password);

        User result = userService.findUser(user);
        if(result == null){
            return "failed";
        }else{
            // 登录成功，创建用户工作目录，如果该目录已经存在，则不会被创建
            File file = new File(ConstantUtils.PROPATH + username);
            file.mkdir();
            System.out.println(ConstantUtils.PROPATH + username + "创建成功!");
            return "successful";
        }
    }
}
