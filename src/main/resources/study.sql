/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : study

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 19/10/2018 11:28:04
*/
CREATE DATABASE `study` DEFAULT CHARACTER SET utf8;

USE `study`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  `parentId` int(11) NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT NULL,
  `creationUid` int(11) NULL DEFAULT NULL,
  `logo` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  `type` enum('总公司','终端','组机','经销商') CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  `creationTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `deptSite` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `creationUid`(`creationUid`) USING BTREE,
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`creationUid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '重庆铭贝科技有限公司', -1, 1, NULL, NULL, '总公司', '2018-10-17 13:49:09', NULL);
INSERT INTO `department` VALUES (3, '经销商单位', 1, 1, 2, '/E:/IntelliJ/study/target/classes/static/images/logo/e9cdb5d9-4475-4044-aaf9-21133bb45ef2-1537350443434.bmp', '经销商', '2018-10-17 13:49:09', NULL);
INSERT INTO `department` VALUES (9, '组机厂单位', 1, 1, 2, NULL, '组机', '2018-10-17 13:49:09', NULL);
INSERT INTO `department` VALUES (10, '终端用户单位', 1, 1, 2, NULL, '终端', '2018-10-17 13:49:09', NULL);
INSERT INTO `department` VALUES (16, '终端用户单位2', 10, 1, 2, NULL, '终端', '2018-10-17 13:49:09', NULL);

-- ----------------------------
-- Table structure for device_detail
-- ----------------------------
DROP TABLE IF EXISTS `device_detail`;
CREATE TABLE `device_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `DTUId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'DTU编号',
  `DTUModel` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'DTU型号',
  `siteName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '站点名称',
  `controllerModel` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控制器型号',
  `communicationNumber` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '通信号码',
  `registrationDate` datetime(0) NOT NULL COMMENT '注册日期',
  `licenseExpirationDate` datetime(0) NOT NULL COMMENT '许可到期日',
  `uploadPhoto` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片上传',
  `controllerBrand` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控制器品牌',
  `controllerType` enum('控制器类型1','控制器类型2') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '控制器类型',
  `userId` int(11) NOT NULL COMMENT '操作员/工程师',
  `licensePeriod` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '许可周期',
  `deviceStatus` enum('运行','待机','离线','报警') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备状态',
  `other` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '其他',
  `unitModel` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机组型号',
  `engineModel` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发动机型号',
  `speedControlType` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调速类型',
  `speedControlBoardModel` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调速板型号',
  `pressureRegulatorType` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调压器类型',
  `manufacturer` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产厂家',
  `electricGeneratorModel` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发电机型号',
  `actuatorType` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行器类型',
  `fuelTankCapacity` float NOT NULL COMMENT '油箱容量',
  `reference` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `accessory` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件',
  `electronicFenceStatus` tinyint(1) NOT NULL DEFAULT 0 COMMENT '电子围栏状态',
  `electronicFenceScope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '围栏范围设置',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  `depId` int(11) NOT NULL COMMENT '单位id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `DTUId`(`DTUId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(64) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  `parentId` int(11) NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parentId`(`parentId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '/', '所有', NULL, 1);
INSERT INTO `menu` VALUES (2, '/user/**', '用户管理', 1, 1);
INSERT INTO `menu` VALUES (3, '/test/**', '测试', 1, 0);
INSERT INTO `menu` VALUES (4, '/system/dep/**', '单位管理', 1, 1);
INSERT INTO `menu` VALUES (5, '/system/role/**', '权限管理', 1, 1);
INSERT INTO `menu` VALUES (6, '/system/device/**', '设备管理', 1, 1);
INSERT INTO `menu` VALUES (7, '/system/role/assignment/**', '权限分配', 4, 1);
INSERT INTO `menu` VALUES (9, '/user/role/**', '角色分配', 2, 1);

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NULL DEFAULT NULL,
  `rid` int(11) NULL DEFAULT NULL,
  `depid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `menu_role_ibfk_1`(`mid`) USING BTREE,
  INDEX `menu_role_ibfk_2`(`rid`) USING BTREE,
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES (2, 2, 2, NULL);
INSERT INTO `menu_role` VALUES (4, 2, 3, NULL);
INSERT INTO `menu_role` VALUES (5, 2, 4, NULL);
INSERT INTO `menu_role` VALUES (6, 2, 5, NULL);
INSERT INTO `menu_role` VALUES (7, 2, 6, NULL);
INSERT INTO `menu_role` VALUES (8, 2, 7, NULL);
INSERT INTO `menu_role` VALUES (10, 4, 2, NULL);
INSERT INTO `menu_role` VALUES (11, 4, 3, NULL);
INSERT INTO `menu_role` VALUES (12, 4, 4, NULL);
INSERT INTO `menu_role` VALUES (13, 4, 5, NULL);
INSERT INTO `menu_role` VALUES (14, 5, 2, NULL);
INSERT INTO `menu_role` VALUES (15, 5, 3, NULL);
INSERT INTO `menu_role` VALUES (16, 5, 4, NULL);
INSERT INTO `menu_role` VALUES (17, 5, 5, NULL);
INSERT INTO `menu_role` VALUES (18, 5, 6, NULL);
INSERT INTO `menu_role` VALUES (19, 5, 7, NULL);
INSERT INTO `menu_role` VALUES (20, 5, 8, NULL);
INSERT INTO `menu_role` VALUES (21, 6, 2, NULL);
INSERT INTO `menu_role` VALUES (24, 6, 3, NULL);
INSERT INTO `menu_role` VALUES (25, 6, 4, NULL);
INSERT INTO `menu_role` VALUES (26, 6, 5, NULL);
INSERT INTO `menu_role` VALUES (27, 6, 6, NULL);
INSERT INTO `menu_role` VALUES (28, 6, 7, NULL);
INSERT INTO `menu_role` VALUES (29, 6, 8, NULL);
INSERT INTO `menu_role` VALUES (30, 9, 2, NULL);
INSERT INTO `menu_role` VALUES (31, 9, 3, NULL);
INSERT INTO `menu_role` VALUES (32, 9, 4, NULL);
INSERT INTO `menu_role` VALUES (33, 9, 5, NULL);

-- ----------------------------
-- Table structure for request_method
-- ----------------------------
DROP TABLE IF EXISTS `request_method`;
CREATE TABLE `request_method`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `method` enum('GET','POST','PUT','DELETE') CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  `roleId` int(11) NULL DEFAULT NULL,
  `menuId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `request_method_ibfk_1`(`roleId`) USING BTREE,
  INDEX `request_method_ibfk_2`(`menuId`) USING BTREE,
  CONSTRAINT `request_method_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `request_method_ibfk_2` FOREIGN KEY (`menuId`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of request_method
-- ----------------------------
INSERT INTO `request_method` VALUES (1, 'GET', 2, 2);
INSERT INTO `request_method` VALUES (2, 'POST', 2, 2);
INSERT INTO `request_method` VALUES (3, 'PUT', 2, 2);
INSERT INTO `request_method` VALUES (4, 'DELETE', 2, 2);
INSERT INTO `request_method` VALUES (5, 'GET', 3, 2);
INSERT INTO `request_method` VALUES (6, 'POST', 3, 2);
INSERT INTO `request_method` VALUES (7, 'PUT', 3, 2);
INSERT INTO `request_method` VALUES (8, 'DELETE', 3, 2);
INSERT INTO `request_method` VALUES (9, 'GET', 4, 2);
INSERT INTO `request_method` VALUES (10, 'POST', 4, 2);
INSERT INTO `request_method` VALUES (11, 'PUT', 4, 2);
INSERT INTO `request_method` VALUES (12, 'DELETE', 4, 2);
INSERT INTO `request_method` VALUES (13, 'GET', 5, 2);
INSERT INTO `request_method` VALUES (14, 'POST', 5, 2);
INSERT INTO `request_method` VALUES (15, 'PUT', 5, 2);
INSERT INTO `request_method` VALUES (16, 'DELETE', 5, 2);
INSERT INTO `request_method` VALUES (17, 'GET', 6, 2);
INSERT INTO `request_method` VALUES (18, 'PUT', 6, 2);
INSERT INTO `request_method` VALUES (19, 'GET', 7, 2);
INSERT INTO `request_method` VALUES (20, 'PUT', 7, 2);
INSERT INTO `request_method` VALUES (23, 'GET', 2, 4);
INSERT INTO `request_method` VALUES (24, 'POST', 2, 4);
INSERT INTO `request_method` VALUES (25, 'PUT', 2, 4);
INSERT INTO `request_method` VALUES (26, 'DELETE', 2, 4);
INSERT INTO `request_method` VALUES (27, 'GET', 3, 4);
INSERT INTO `request_method` VALUES (28, 'POST', 3, 4);
INSERT INTO `request_method` VALUES (29, 'PUT', 3, 4);
INSERT INTO `request_method` VALUES (30, 'DELETE', 3, 4);
INSERT INTO `request_method` VALUES (31, 'GET', 4, 4);
INSERT INTO `request_method` VALUES (32, 'POST', 4, 4);
INSERT INTO `request_method` VALUES (33, 'PUT', 4, 4);
INSERT INTO `request_method` VALUES (34, 'DELETE', 4, 4);
INSERT INTO `request_method` VALUES (35, 'GET', 5, 4);
INSERT INTO `request_method` VALUES (36, 'POST', 5, 4);
INSERT INTO `request_method` VALUES (37, 'PUT', 5, 4);
INSERT INTO `request_method` VALUES (38, 'DELETE', 5, 4);
INSERT INTO `request_method` VALUES (39, 'GET', 2, 5);
INSERT INTO `request_method` VALUES (40, 'GET', 3, 5);
INSERT INTO `request_method` VALUES (41, 'GET', 4, 5);
INSERT INTO `request_method` VALUES (42, 'GET', 5, 5);
INSERT INTO `request_method` VALUES (43, 'GET', 6, 5);
INSERT INTO `request_method` VALUES (44, 'GET', 7, 5);
INSERT INTO `request_method` VALUES (45, 'GET', 8, 5);
INSERT INTO `request_method` VALUES (46, 'GET', 2, 6);
INSERT INTO `request_method` VALUES (47, 'POST', 2, 6);
INSERT INTO `request_method` VALUES (48, 'PUT', 2, 6);
INSERT INTO `request_method` VALUES (49, 'DELETE', 2, 6);
INSERT INTO `request_method` VALUES (50, 'GET', 3, 6);
INSERT INTO `request_method` VALUES (51, 'POST', 3, 6);
INSERT INTO `request_method` VALUES (52, 'PUT', 3, 6);
INSERT INTO `request_method` VALUES (53, 'DELETE', 3, 6);
INSERT INTO `request_method` VALUES (54, 'GET', 4, 6);
INSERT INTO `request_method` VALUES (55, 'POST', 4, 6);
INSERT INTO `request_method` VALUES (56, 'PUT', 4, 6);
INSERT INTO `request_method` VALUES (57, 'DELETE', 4, 6);
INSERT INTO `request_method` VALUES (58, 'GET', 5, 6);
INSERT INTO `request_method` VALUES (59, 'POST', 5, 6);
INSERT INTO `request_method` VALUES (60, 'PUT', 5, 6);
INSERT INTO `request_method` VALUES (61, 'DELETE', 5, 6);
INSERT INTO `request_method` VALUES (62, 'GET', 6, 6);
INSERT INTO `request_method` VALUES (63, 'POST', 6, 6);
INSERT INTO `request_method` VALUES (64, 'PUT', 6, 6);
INSERT INTO `request_method` VALUES (65, 'DELETE', 6, 6);
INSERT INTO `request_method` VALUES (66, 'GET', 7, 6);
INSERT INTO `request_method` VALUES (68, 'PUT', 7, 6);
INSERT INTO `request_method` VALUES (69, 'DELETE', 7, 6);
INSERT INTO `request_method` VALUES (70, 'GET', 8, 6);
INSERT INTO `request_method` VALUES (71, 'GET', 2, 9);
INSERT INTO `request_method` VALUES (72, 'POST', 2, 9);
INSERT INTO `request_method` VALUES (73, 'PUT', 2, 9);
INSERT INTO `request_method` VALUES (74, 'DELETE', 2, 9);
INSERT INTO `request_method` VALUES (75, 'POST', 3, 9);
INSERT INTO `request_method` VALUES (76, 'DELETE', 3, 9);
INSERT INTO `request_method` VALUES (77, 'POST', 4, 9);
INSERT INTO `request_method` VALUES (78, 'DELETE', 4, 9);
INSERT INTO `request_method` VALUES (79, 'POST', 5, 9);
INSERT INTO `request_method` VALUES (80, 'DELETE', 5, 9);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  `nameZh` varchar(64) CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  `pid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid`(`pid`) USING BTREE,
  CONSTRAINT `role_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_SUPER_ADMIN', '超级管理员', NULL);
INSERT INTO `role` VALUES (2, 'ROLE_HIGH_GRADE_ADMIN', '高级管理员', 1);
INSERT INTO `role` VALUES (3, 'ROLE_USER_ADMIN', '终端用户管理员', 5);
INSERT INTO `role` VALUES (4, 'ROLE_DEALER_ADMIN', '经销商管理员', 2);
INSERT INTO `role` VALUES (5, 'ROLE_ASSEMBLY_MACHINE_ADMIN', '组机厂管理员', 4);
INSERT INTO `role` VALUES (6, 'ROLE_ENGINEER', '工程师', 3);
INSERT INTO `role` VALUES (7, 'ROLE_OPERATOR', '操作员', 3);
INSERT INTO `role` VALUES (8, 'ROLE_SURVEY', '观察员', 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  `password` text CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  `sex` enum('男','女') CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT '男',
  `phone` varchar(11) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  `creationTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updateTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `depId` int(11) NULL DEFAULT NULL,
  `logo` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `user_ibfk_1`(`depId`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`depId`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test', '$2a$10$vHlFh92LPD.514uAqAHuUOOxJHyf2hXSMpdPPvGz7k5xBOvDVT./6', 'cq', '男', NULL, '2018-09-04 16:46:41', '2018-10-09 15:22:24', 1, NULL);
INSERT INTO `user` VALUES (2, '高级管理员', '$2a$10$ph0QH3sRyHBHtizuWOUgI.iAfF0GOOQ3hyh3Q/uSttgBUvXa5kRIa', 'cp', '男', NULL, '2018-09-04 16:49:52', '2018-10-09 15:30:11', 1, NULL);
INSERT INTO `user` VALUES (3, '经销商管理员', '$2a$10$.70JwfyvWeRahB6jYKIfVeEXWjX/Bkw7k0bs.Tf/nWPl6y75ul1WW', '张三', NULL, NULL, '2018-10-09 15:33:17', '2018-10-09 15:33:36', NULL, NULL);
INSERT INTO `user` VALUES (4, '机组厂管理员', '$2a$10$h3HsrjFNvueJz/AS2MnhIOz.h9Re.S/Bp3fzrm3/QuG.RtPF4T/ZO', '张三', NULL, NULL, '2018-10-09 15:34:06', '2018-10-09 15:38:02', NULL, NULL);
INSERT INTO `user` VALUES (5, '终端用户管理员', '$2a$10$Xc2hVpefXR8c0IIUBrSL.u7RPMpqIQF3/bs2muw2a9szzFRCPnOau', '张三', '男', '11011211912', '2018-10-09 15:46:50', '2018-10-09 15:47:56', 1, NULL);
INSERT INTO `user` VALUES (6, '工程师', '$2a$10$8DJvD6mOOvUm7k86P.RR7OFZY1pkGJqAc5206ZgLiNasVa6vhd4Qm', '张三', '女', '11011211912', '2018-10-09 15:47:26', '2018-10-17 17:30:26', 1, NULL);
INSERT INTO `user` VALUES (7, '操作员', '$2a$10$UY/gi5.OXFa6KP7rWikduuycGPgZOoJTKxenWrulsfM0iHr4/YiMa', '张三', '男', '11011211912', '2018-10-09 15:48:20', '2018-10-09 15:48:34', 1, NULL);
INSERT INTO `user` VALUES (8, '观察员', '$2a$10$oupPoCeP9LMtwmb0f1w4lOeB5kdnyN5Ry8uEl7YJ4iIp914K2IAO2', '张三', '女', '11011211912', '2018-10-09 15:47:44', '2018-10-09 15:48:30', 1, NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `roleid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_role_ibfk_1`(`userid`) USING BTREE,
  INDEX `user_role_ibfk_2`(`roleid`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);
INSERT INTO `user_role` VALUES (3, 3, 4);
INSERT INTO `user_role` VALUES (4, 4, 5);
INSERT INTO `user_role` VALUES (5, 5, 3);
INSERT INTO `user_role` VALUES (6, 6, 6);
INSERT INTO `user_role` VALUES (7, 7, 7);
INSERT INTO `user_role` VALUES (8, 8, 8);

SET FOREIGN_KEY_CHECKS = 1;
