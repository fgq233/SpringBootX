package com.shiro.demo.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 角色or判断过滤器
 */
public class RolesOrFilter extends RolesAuthorizationFilter {

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        for (String role : roles) {
            boolean flag = subject.hasRole(role);
            if (flag) {
                return flag;
            }
        }
        return false;
    }

}
