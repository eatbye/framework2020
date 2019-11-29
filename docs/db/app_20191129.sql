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

 Date: 29/11/2019 09:50:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `departmentCode` varchar(255) DEFAULT NULL,
  `departmentName` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `parentDepartmentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsecudj74n2nxwwm0y8krfc574` (`parentDepartmentId`),
  CONSTRAINT `FKsecudj74n2nxwwm0y8krfc574` FOREIGN KEY (`parentDepartmentId`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of department
-- ----------------------------
BEGIN;
INSERT INTO `department` VALUES (1, '110', '总公司', 1, 1, NULL);
INSERT INTO `department` VALUES (2, '110001', '开发部', 1, 1, 1);
INSERT INTO `department` VALUES (3, '110002', '工程部', 2, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `parentDictId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk6kj2j8w5k1a2pdcmhxru9173` (`parentDictId`),
  CONSTRAINT `FKk6kj2j8w5k1a2pdcmhxru9173` FOREIGN KEY (`parentDictId`) REFERENCES `dict` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of dict
-- ----------------------------
BEGIN;
INSERT INTO `dict` VALUES (1, NULL, '性别', NULL, 1, '性别', NULL);
INSERT INTO `dict` VALUES (2, 'blue', '男', 1, 1, '1', 1);
INSERT INTO `dict` VALUES (3, 'red', '女', 2, 1, '2', 1);
INSERT INTO `dict` VALUES (7, '', '保密', 3, 1, '3', 1);
INSERT INTO `dict` VALUES (8, NULL, '城市', NULL, 1, '城市', NULL);
INSERT INTO `dict` VALUES (9, '', '北京', 1, 1, '1', 8);
INSERT INTO `dict` VALUES (10, '', '上海', 2, 1, '2', 8);
INSERT INTO `dict` VALUES (11, '', '广州', 3, 1, '3', 8);
COMMIT;

-- ----------------------------
-- Table structure for family
-- ----------------------------
DROP TABLE IF EXISTS `family`;
CREATE TABLE `family` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `area` decimal(19,2) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of family
-- ----------------------------
BEGIN;
INSERT INTO `family` VALUES (1, '北京市朝阳区北苑', 100.00, '郝红梅', NULL, NULL);
INSERT INTO `family` VALUES (2, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (3, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (4, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (5, '北京市顺义区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (6, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (7, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (8, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (9, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (10, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (11, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (12, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (13, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (14, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (15, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (16, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (17, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (18, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (19, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (20, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (21, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (22, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (23, '北京市朝阳区王静', 1000.00, '张梅', NULL, NULL);
INSERT INTO `family` VALUES (24, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (25, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (26, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (27, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (28, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (29, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (30, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (31, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (32, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (33, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (35, '北京市朝阳区', 100.00, '张艺谋', NULL, NULL);
INSERT INTO `family` VALUES (36, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (38, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (39, '北京市朝阳区', 100.00, '张三', NULL, NULL);
INSERT INTO `family` VALUES (41, '北京市朝阳区', 100.50, '张三', NULL, NULL);
INSERT INTO `family` VALUES (43, '北京市顺义区后沙峪', 1000.00, '郝红梅', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `browser` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `loginTime` datetime DEFAULT NULL,
  `system` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of login_log
-- ----------------------------
BEGIN;
INSERT INTO `login_log` VALUES (2, 'Chrome 78', '127.0.0.1', '2019-11-29 09:38:24', 'Mac OS X 10_15_0', 'ccj');
COMMIT;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
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
  KEY `FK5y7q1wgnx4vftcmj8444v42ts` (`parentMenuId`),
  CONSTRAINT `FK5y7q1wgnx4vftcmj8444v42ts` FOREIGN KEY (`parentMenuId`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES (1, 'layui-icon-setting', '系统管理', '', 10, 1, '', 1, NULL);
INSERT INTO `menu` VALUES (2, '', '字典管理', '', 1, 1, '/system/dict/list', 1, 1);
INSERT INTO `menu` VALUES (3, 'layui-icon-earth', '家庭管理', '', 1, 1, '', 1, NULL);
INSERT INTO `menu` VALUES (4, '', '菜单管理', '', 2, 1, '/system/menu/menu', NULL, 1);
INSERT INTO `menu` VALUES (5, '', '家庭管理', '', 1, 1, '/family/list', NULL, 3);
INSERT INTO `menu` VALUES (6, '', '部门管理', '', 3, 1, '/system/department/index', NULL, 1);
INSERT INTO `menu` VALUES (7, '', '角色管理', '', 4, 1, '/system/role/list', NULL, 1);
INSERT INTO `menu` VALUES (8, '', '用户管理', '', 5, 1, '/system/user/list', NULL, 1);
INSERT INTO `menu` VALUES (9, '', '列表', 'family:list', 1, 2, '', NULL, 5);
INSERT INTO `menu` VALUES (10, '', '编辑', 'family:edit', 1, 1, '', NULL, 5);
INSERT INTO `menu` VALUES (11, '', '删除', 'family:del', 1, 2, '', NULL, 5);
INSERT INTO `menu` VALUES (12, 'layui-icon-cloud', '系统监控', '', 99, 1, '', 1, NULL);
INSERT INTO `menu` VALUES (13, '', '在线用户', '', 1, 1, '/system/monitor/online', NULL, 12);
INSERT INTO `menu` VALUES (14, '', '登录日志', '', 20, 1, '/system/monitor/loginLog', NULL, 12);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleDescription` varchar(255) DEFAULT NULL,
  `roleName` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, '系统权限管理维护', '系统管理员', 1, NULL);
INSERT INTO `role` VALUES (3, '112200', '员工', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for roleMenu
-- ----------------------------
DROP TABLE IF EXISTS `roleMenu`;
CREATE TABLE `roleMenu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKifh73i1vb2jjhtp7yvc89doa3` (`menuId`),
  KEY `FKgyt5txv6lh7s84gneub1n0mwk` (`roleId`),
  CONSTRAINT `FKgyt5txv6lh7s84gneub1n0mwk` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`),
  CONSTRAINT `FKifh73i1vb2jjhtp7yvc89doa3` FOREIGN KEY (`menuId`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of roleMenu
-- ----------------------------
BEGIN;
INSERT INTO `roleMenu` VALUES (35, 3, 3);
INSERT INTO `roleMenu` VALUES (36, 1, 3);
INSERT INTO `roleMenu` VALUES (37, 2, 3);
INSERT INTO `roleMenu` VALUES (38, 4, 3);
INSERT INTO `roleMenu` VALUES (102, 3, 1);
INSERT INTO `roleMenu` VALUES (103, 5, 1);
INSERT INTO `roleMenu` VALUES (104, 9, 1);
INSERT INTO `roleMenu` VALUES (105, 10, 1);
INSERT INTO `roleMenu` VALUES (106, 11, 1);
INSERT INTO `roleMenu` VALUES (107, 1, 1);
INSERT INTO `roleMenu` VALUES (108, 2, 1);
INSERT INTO `roleMenu` VALUES (109, 4, 1);
INSERT INTO `roleMenu` VALUES (110, 6, 1);
INSERT INTO `roleMenu` VALUES (111, 7, 1);
INSERT INTO `roleMenu` VALUES (112, 8, 1);
INSERT INTO `roleMenu` VALUES (113, 12, 1);
INSERT INTO `roleMenu` VALUES (114, 13, 1);
INSERT INTO `roleMenu` VALUES (115, 14, 1);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `realName` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `departmentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKthtg3ev4sxtt924emdlkvish9` (`departmentId`),
  CONSTRAINT `FKthtg3ev4sxtt924emdlkvish9` FOREIGN KEY (`departmentId`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '1', NULL, 'ok', '蔡长江', 'ccj', 1, 3);
INSERT INTO `user` VALUES (4, NULL, NULL, 'ok', '测试', 'test', 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfjlagks6xvf2uas035crflu75` (`roleId`),
  KEY `FKj5g46wgmq1wmqfhv78g7cxaqe` (`userId`),
  CONSTRAINT `FKfjlagks6xvf2uas035crflu75` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`),
  CONSTRAINT `FKj5g46wgmq1wmqfhv78g7cxaqe` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (8, 1, 1);
INSERT INTO `user_role` VALUES (9, 3, 1);
INSERT INTO `user_role` VALUES (14, 3, 4);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
