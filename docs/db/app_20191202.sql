/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : app

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 02/12/2019 13:17:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for family
-- ----------------------------
DROP TABLE IF EXISTS `family`;
CREATE TABLE `family` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `area` decimal(19,2) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of family
-- ----------------------------
BEGIN;
INSERT INTO `family` VALUES (1, '北京市朝阳区', 200.00, NULL, '1', '蔡长江');
INSERT INTO `family` VALUES (2, '北京市顺义区', NULL, NULL, '1', '郝红梅');
COMMIT;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `departmentCode` varchar(255) DEFAULT NULL,
  `departmentName` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `parentDepartmentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl0v8hqm5buvobhkcghes4wbns` (`parentDepartmentId`),
  CONSTRAINT `FKl0v8hqm5buvobhkcghes4wbns` FOREIGN KEY (`parentDepartmentId`) REFERENCES `sys_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
BEGIN;
INSERT INTO `sys_department` VALUES (1, '110', '总公司', 1, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `parentDictId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmon7qipr0ifmtugwr70c7jxyn` (`parentDictId`),
  CONSTRAINT `FKmon7qipr0ifmtugwr70c7jxyn` FOREIGN KEY (`parentDictId`) REFERENCES `sys_dict` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (1, NULL, '性别', NULL, 1, '性别', NULL);
INSERT INTO `sys_dict` VALUES (2, '', '男', 1, 1, '1', 1);
INSERT INTO `sys_dict` VALUES (3, '', '女', 2, 1, '2', 1);
INSERT INTO `sys_dict` VALUES (4, NULL, '城市', NULL, 1, '城市', NULL);
INSERT INTO `sys_dict` VALUES (5, '', '北京', 1, 1, '1', 4);
INSERT INTO `sys_dict` VALUES (6, '', '上海', 2, 1, '2', 4);
INSERT INTO `sys_dict` VALUES (7, '', '广州', 3, 1, '3', 4);
COMMIT;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `browser` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `loginTime` datetime DEFAULT NULL,
  `system` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_login_log` VALUES (1, 'Chrome 78', '127.0.0.1', '2019-12-02 13:13:04', 'Mac OS X 10_14_6', 'ccj');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) DEFAULT NULL,
  `menuName` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `parentMenuId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6dkoxo1qoftydqg5o39831fis` (`parentMenuId`),
  CONSTRAINT `FK6dkoxo1qoftydqg5o39831fis` FOREIGN KEY (`parentMenuId`) REFERENCES `sys_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 'layui-icon-setting', '系统管理', '', 10, 1, '', 1, NULL);
INSERT INTO `sys_menu` VALUES (2, '', '字典管理', '', 1, 1, '/system/dict/list', 1, 1);
INSERT INTO `sys_menu` VALUES (3, 'layui-icon-earth', '家庭管理', '', 1, 1, '', 1, NULL);
INSERT INTO `sys_menu` VALUES (4, '', '菜单管理', '', 2, 1, '/system/menu/menu', NULL, 1);
INSERT INTO `sys_menu` VALUES (5, '', '家庭管理', '', 1, 1, '/family/list', NULL, 3);
INSERT INTO `sys_menu` VALUES (6, '', '部门管理', '', 3, 1, '/system/department/index', NULL, 1);
INSERT INTO `sys_menu` VALUES (7, '', '角色管理', '', 4, 1, '/system/role/list', NULL, 1);
INSERT INTO `sys_menu` VALUES (8, '', '用户管理', '', 5, 1, '/system/user/list', NULL, 1);
INSERT INTO `sys_menu` VALUES (9, '', '列表', 'family:list', 1, 2, '', NULL, 5);
INSERT INTO `sys_menu` VALUES (10, '', '编辑', 'family:edit', 1, 1, '', NULL, 5);
INSERT INTO `sys_menu` VALUES (11, '', '删除', 'family:del', 1, 2, '', NULL, 5);
INSERT INTO `sys_menu` VALUES (12, 'layui-icon-cloud', '系统监控', '', 99, 1, '', 1, NULL);
INSERT INTO `sys_menu` VALUES (13, '', '在线用户', '', 1, 1, '/system/monitor/online', NULL, 12);
INSERT INTO `sys_menu` VALUES (14, '', '登录日志', '', 20, 1, '/system/monitor/loginLog', NULL, 12);
INSERT INTO `sys_menu` VALUES (15, '', '系统日志', '', 30, 1, '/system/monitor/systemLog', NULL, 12);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleDescription` varchar(255) DEFAULT NULL,
  `roleName` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '', '系统管理员', 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2lagreeeq77rb54mx3lwlhux4` (`menuId`),
  KEY `FK8g1xowau8dd5gqrdjhkgell1u` (`roleId`),
  CONSTRAINT `FK2lagreeeq77rb54mx3lwlhux4` FOREIGN KEY (`menuId`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `FK8g1xowau8dd5gqrdjhkgell1u` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (116, 3, 1);
INSERT INTO `sys_role_menu` VALUES (117, 5, 1);
INSERT INTO `sys_role_menu` VALUES (118, 9, 1);
INSERT INTO `sys_role_menu` VALUES (119, 10, 1);
INSERT INTO `sys_role_menu` VALUES (120, 11, 1);
INSERT INTO `sys_role_menu` VALUES (121, 1, 1);
INSERT INTO `sys_role_menu` VALUES (122, 2, 1);
INSERT INTO `sys_role_menu` VALUES (123, 4, 1);
INSERT INTO `sys_role_menu` VALUES (124, 6, 1);
INSERT INTO `sys_role_menu` VALUES (125, 7, 1);
INSERT INTO `sys_role_menu` VALUES (126, 8, 1);
INSERT INTO `sys_role_menu` VALUES (127, 12, 1);
INSERT INTO `sys_role_menu` VALUES (128, 13, 1);
INSERT INTO `sys_role_menu` VALUES (129, 14, 1);
INSERT INTO `sys_role_menu` VALUES (130, 15, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `realName` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `departmentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh4vmoyhnds14clx00p4xfsvgj` (`departmentId`),
  CONSTRAINT `FKh4vmoyhnds14clx00p4xfsvgj` FOREIGN KEY (`departmentId`) REFERENCES `sys_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, NULL, NULL, 'ok', '蔡长江', 'ccj', 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjh0u12g42rt4md78ptxxnlpmu` (`roleId`),
  KEY `FK8rsekkvn91inc4bkn6jxsayv4` (`userId`),
  CONSTRAINT `FK8rsekkvn91inc4bkn6jxsayv4` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKjh0u12g42rt4md78ptxxnlpmu` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of system_log
-- ----------------------------
BEGIN;
INSERT INTO `system_log` VALUES (1, '2019-12-02 13:13:08', '127.0.0.1', 'com.app.app.web.FamilyController.list()', 'family:list', '', 12, 'ccj');
INSERT INTO `system_log` VALUES (2, '2019-12-02 13:13:08', '127.0.0.1', 'com.app.app.web.FamilyController.listData()', 'family:list', ' pageInfo: PageInfo{pageNo=1, pageSize=20, orderField=\'null\', orderType=\'desc\', orderBy=\'id\', order=\'desc\', autoCount=true, res', 0, 'ccj');
INSERT INTO `system_log` VALUES (3, '2019-12-02 13:13:28', '127.0.0.1', 'com.app.app.web.FamilyController.list()', 'family:list', '', 0, 'ccj');
INSERT INTO `system_log` VALUES (4, '2019-12-02 13:13:28', '127.0.0.1', 'com.app.app.web.FamilyController.listData()', 'family:list', ' pageInfo: PageInfo{pageNo=1, pageSize=20, orderField=\'null\', orderType=\'desc\', orderBy=\'id\', order=\'desc\', autoCount=true, res', 0, 'ccj');
INSERT INTO `system_log` VALUES (5, '2019-12-02 13:15:08', '127.0.0.1', 'com.app.app.web.FamilyController.list()', 'family:list', '', 0, 'ccj');
INSERT INTO `system_log` VALUES (6, '2019-12-02 13:15:08', '127.0.0.1', 'com.app.app.web.FamilyController.listData()', 'family:list', ' pageInfo: PageInfo{pageNo=1, pageSize=20, orderField=\'null\', orderType=\'desc\', orderBy=\'id\', order=\'desc\', autoCount=true, res', 1, 'ccj');
INSERT INTO `system_log` VALUES (7, '2019-12-02 13:15:36', '127.0.0.1', 'com.app.app.web.FamilyController.list()', 'family:list', '', 1, 'ccj');
INSERT INTO `system_log` VALUES (8, '2019-12-02 13:15:36', '127.0.0.1', 'com.app.app.web.FamilyController.listData()', 'family:list', ' pageInfo: PageInfo{pageNo=1, pageSize=20, orderField=\'null\', orderType=\'desc\', orderBy=\'id\', order=\'desc\', autoCount=true, res', 0, 'ccj');
INSERT INTO `system_log` VALUES (9, '2019-12-02 13:15:38', '127.0.0.1', 'com.app.app.web.FamilyController.form()', 'family:edit', ' family: com.app.app.model.Family@610fb2e5', 0, 'ccj');
INSERT INTO `system_log` VALUES (10, '2019-12-02 13:16:06', '127.0.0.1', 'com.app.app.web.FamilyController.list()', 'family:list', '', 1, 'ccj');
INSERT INTO `system_log` VALUES (11, '2019-12-02 13:16:06', '127.0.0.1', 'com.app.app.web.FamilyController.listData()', 'family:list', ' pageInfo: PageInfo{pageNo=1, pageSize=20, orderField=\'null\', orderType=\'desc\', orderBy=\'id\', order=\'desc\', autoCount=true, res', 0, 'ccj');
INSERT INTO `system_log` VALUES (12, '2019-12-02 13:16:18', '127.0.0.1', 'com.app.app.web.FamilyController.list()', 'family:list', '', 1, 'ccj');
INSERT INTO `system_log` VALUES (13, '2019-12-02 13:16:18', '127.0.0.1', 'com.app.app.web.FamilyController.listData()', 'family:list', ' pageInfo: PageInfo{pageNo=1, pageSize=20, orderField=\'null\', orderType=\'desc\', orderBy=\'id\', order=\'desc\', autoCount=true, res', 1, 'ccj');
INSERT INTO `system_log` VALUES (14, '2019-12-02 13:16:18', '127.0.0.1', 'com.app.app.web.FamilyController.form()', 'family:edit', ' family: com.app.app.model.Family@6d65f21', 0, 'ccj');
INSERT INTO `system_log` VALUES (15, '2019-12-02 13:16:39', '127.0.0.1', 'com.app.app.web.FamilyController.save()', 'family:edit', '', 1, 'ccj');
INSERT INTO `system_log` VALUES (16, '2019-12-02 13:16:39', '127.0.0.1', 'com.app.app.web.FamilyController.listData()', 'family:list', ' pageInfo: PageInfo{pageNo=1, pageSize=20, orderField=\'null\', orderType=\'desc\', orderBy=\'id\', order=\'desc\', autoCount=true, res', 1, 'ccj');
INSERT INTO `system_log` VALUES (17, '2019-12-02 13:16:43', '127.0.0.1', 'com.app.app.web.FamilyController.form()', 'family:edit', ' family: com.app.app.model.Family@7660bf4d', 0, 'ccj');
INSERT INTO `system_log` VALUES (18, '2019-12-02 13:16:53', '127.0.0.1', 'com.app.app.web.FamilyController.save()', 'family:edit', '', 0, 'ccj');
INSERT INTO `system_log` VALUES (19, '2019-12-02 13:16:53', '127.0.0.1', 'com.app.app.web.FamilyController.listData()', 'family:list', ' pageInfo: PageInfo{pageNo=1, pageSize=20, orderField=\'null\', orderType=\'desc\', orderBy=\'id\', order=\'desc\', autoCount=true, res', 1, 'ccj');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
