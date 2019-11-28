package com.app.system.service;

import com.app.sqds.util.DateUtils;
import com.app.sqds.util.StringUtils;
import com.app.system.model.ActiveUser;
import com.app.system.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 在线用户列表
     * @return
     */
    public List<ActiveUser> list() {
        String currentSessionId = (String) SecurityUtils.getSubject().getSession().getId();

        List<ActiveUser> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            ActiveUser activeUser = new ActiveUser();
            User user;
            SimplePrincipalCollection principalCollection;
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                user = (User) principalCollection.getPrimaryPrincipal();
                activeUser.setUsername(user.getUsername());
                activeUser.setUserId(user.getId().toString());
            }
            activeUser.setId((String) session.getId());
            activeUser.setHost(session.getHost());
            activeUser.setStartTimestamp(DateUtils.date2string("yyyy-MM-dd HH:mm:ss", session.getStartTimestamp()));
            activeUser.setLastAccessTime(DateUtils.date2string("yyyy-MM-dd HH:mm:ss", session.getLastAccessTime()));
            long timeout = session.getTimeout();
            activeUser.setStatus(timeout == 0L ? "0" : "1");
            String address = activeUser.getHost();
            activeUser.setLocation(address);
            activeUser.setTimeout(timeout);
            if(currentSessionId.equals(activeUser.getId())){
                activeUser.setCurrent(true);
            }
            list.add(activeUser);
        }
        return list;
    }

    /**
     * 将用户踢出
     * @param sessionId
     */
    public void forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        session.setTimeout(0);
        session.stop();
        sessionDAO.delete(session);
    }
}
