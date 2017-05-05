package cn.binux.sso.service.hystrix;

import cn.binux.pojo.TbUser;
import cn.binux.pojo.XbinResult;
import cn.binux.sso.service.UserService;
import org.springframework.stereotype.Component;

/**
 * 用户登录 熔断处理
 *
 * @author xubin.
 * @create 2017-05-05
 */

@Component
public class UserServiceHystrix implements UserService {

    /**
     * 请求格式 POST
     * 用户登录
     *
     * @param user Tbuser POJO Json
     * @return {
     * status: 200 //200 成功 400 登录失败 500 系统异常
     * msg: "OK" //错误 用户名或密码错误,请检查后重试.
     * data: "fe5cb546aeb3ce1bf37abcb08a40493e" //登录成功，返回token
     * }
     */
    @Override
    public XbinResult login(TbUser user) {
        return null;
    }

    /**
     * 请求格式 GET
     * 根据token值获取用户信息
     *
     * @param token    token值
     * @param callback 可选参数 有参表示jsonp调用
     * @return {
     * status: 200 //200 成功 400 没有此token 500 系统异常
     * msg: "OK" //错误 没有此token.
     * data: {"username":"xbin"} //返回用户名
     * }
     */
    @Override
    public XbinResult token(String token, String callback) {
        return null;
    }

    /**
     * 请求格式 GET
     * 根据token值 退出登录
     *
     * @param token    token值
     * @param callback 可选参数 有参表示jsonp调用
     * @return {
     * status: 200 //200 成功 400 没有此token 500 系统异常
     * msg: "OK" //错误 没有此token.
     * data: null
     * }
     */
    @Override
    public XbinResult logout(String token, String callback) {
        return null;
    }

    /**
     * 请求格式 POST
     * 注册检查是否可用
     *
     * @param isEngaged 需要检查是否使用的名称
     * @param regName
     * @param email
     * @param phone     @return {
     *                  "success": 0 可用 1 不可用
     *                  "morePin":["sssss740","sssss5601","sssss76676"] //isEngaged = isPinEngaged时返回推荐
     *                  }
     */
    @Override
    public String validateUser(String isEngaged, String regName, String email, String phone) {
        return null;
    }

    /**
     * 请求格式 POST
     * 验证验证码
     *
     * @param authCode 输入的验证码
     * @param uuid     Redis验证码uuid
     * @return {
     * "success": 0 可用 1 不可用
     * }
     */
    @Override
    public String validateAuthCode(String authCode, String uuid) {
        return null;
    }

    /**
     * 请求格式 POST
     * 注册
     *
     * @param regName    注册名
     * @param pwd        第一次密码
     * @param pwdRepeat  第二次密码
     * @param phone      电话
     * @param mobileCode 手机验证码
     * @param uuid       Redis验证码uuid
     * @param authCode   输入的验证码
     * @param s
     * @return
     */
    @Override
    public String register(String regName, String pwd, String pwdRepeat, String phone, String mobileCode, String uuid, String authCode, String s) {
        return null;
    }
}
