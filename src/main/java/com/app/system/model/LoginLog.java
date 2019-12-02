package com.app.system.model;

import com.app.sqds.util.IPUtil;
import com.app.sqds.util.ParamUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登录日志
 */
@Entity
@Table(name="sys_login_log")
public class LoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username; //登录用户
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
    private String ip;
    private String system;
    private String browser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setSystemBrowserInfo() {
        try {
            HttpServletRequest request = ParamUtils.getRequest();

            StringBuilder userAgent = new StringBuilder("[");
            userAgent.append(request.getHeader("User-Agent"));
            userAgent.append("]");
            int indexOfMac = userAgent.indexOf("Mac OS X");
            int indexOfWindows = userAgent.indexOf("Windows NT");
            int indexOfIE = userAgent.indexOf("MSIE");
            int indexOfIE11 = userAgent.indexOf("rv:");
            int indexOfFF = userAgent.indexOf("Firefox");
            int indexOfSogou = userAgent.indexOf("MetaSr");
            int indexOfChrome = userAgent.indexOf("Chrome");
            int indexOfSafari = userAgent.indexOf("Safari");
            boolean isMac = indexOfMac > 0;
            boolean isWindows = indexOfWindows > 0;
            boolean isLinux = userAgent.indexOf("Linux") > 0;
            boolean containIE = indexOfIE > 0 || (isWindows && (indexOfIE11 > 0));
            boolean containFF = indexOfFF > 0;
            boolean containSogou = indexOfSogou > 0;
            boolean containChrome = indexOfChrome > 0;
            boolean containSafari = indexOfSafari > 0;
            String browser = "";
            if (containSogou) {
                if (containIE) {
                    browser = "搜狗" + userAgent.substring(indexOfIE, indexOfIE + "IE x.x".length());
                } else if (containChrome) {
                    browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
                }
            } else if (containChrome) {
                browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
            } else if (containSafari) {
                int indexOfSafariVersion = userAgent.indexOf("Version");
                browser = "Safari "
                        + userAgent.substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x.x".length());
            } else if (containFF) {
                browser = userAgent.substring(indexOfFF, indexOfFF + "Firefox/xx".length());
            } else if (containIE) {
                if (indexOfIE11 > 0) {
                    browser = "IE 11";
                } else {
                    browser = userAgent.substring(indexOfIE, indexOfIE + "IE x.x".length());
                }
            }
            String os = "";
            if (isMac) {
                os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxxxxxx".length());
            } else if (isLinux) {
                os = "Linux";
            } else if (isWindows) {
                os = "Windows ";
                String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
                        + "Windows NTx.x".length());
                switch (version.trim()) {
                    case "5.0":
                        os += "2000";
                        break;
                    case "5.1":
                        os += "XP";
                        break;
                    case "5.2":
                        os += "2003";
                        break;
                    case "6.0":
                        os += "Vista";
                        break;
                    case "6.1":
                        os += "7";
                        break;
                    case "6.2":
                        os += "8";
                        break;
                    case "6.3":
                        os += "8.1";
                        break;
                    case "10":
                        os += "10";
                        break;
                }
            }
            this.system = os;
            this.browser = StringUtils.replace(browser, "/", " ");
            this.loginTime = new Date();

            this.ip = IPUtil.getIpAddress(request);

        } catch (Exception e) {
//            log.error("获取登录信息失败：{}", e.getMessage());
            this.system = "";
            this.browser = "";
        }

    }
}
