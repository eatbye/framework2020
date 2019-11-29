package com.app.system.authentication;

import com.app.sqds.util.StringUtils;
import com.app.system.model.Menu;
import com.app.system.model.Role;
import com.app.system.model.User;
import com.app.system.service.MenuService;
import com.app.system.service.RoleService;
import com.app.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author ccj
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired private RoleService roleService;
    @Autowired private UserService userService;
    @Autowired private MenuService menuService;
    /**
     * 授权模块，获取用户角色和权限
     *
     * @param principal principal
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();

//        logger.debug("---------##-----------------");
//        logger.debug("userId = {}", userId);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        List<Role> roleList = roleService.findUserRole(userId);

        Set<String> roleSet = new HashSet<>();
        for(Role role : roleList){
            roleSet.add(role.getRoleName());
        }

        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Menu> permissionList = this.menuService.findUserPermissions(userId);

        Set<String> permissionSet = new HashSet<>();
        for(Menu menu : permissionList){
            if(StringUtils.isNotEmpty(menu.getPermission())){
                permissionSet.add(menu.getPermission());
            }
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param token AuthenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        // 通过用户名到数据库查询用户信息
        User user = userService.findByName(userName);
        if (user == null) {
            throw new UnknownAccountException("账号未注册！");
        }
        if(!password.equals(user.getPassword())){
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if(user.getValid().intValue()==2){
            throw new LockedAccountException("账号已被锁定！");
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 清除当前用户权限缓存
     * 使用方法：在需要清除用户权限的地方注入 ShiroRealm,
     * 然后调用其 clearCache方法。
     */
    public void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
