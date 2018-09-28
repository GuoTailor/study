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

 Date: 27/09/2018 20:11:01
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
  `isParent` tinyint(1) NULL DEFAULT NULL,
  `creationUid` int(11) NULL DEFAULT NULL,
  `logoPath` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '重庆铭贝科技有限公司', -1, 1, 1, NULL, NULL);
INSERT INTO `department` VALUES (3, '经销商单位', 1, 1, 0, NULL, '/E:/IntelliJ/study/target/classes/static/images/logo/e9cdb5d9-4475-4044-aaf9-21133bb45ef2-1537350443434.bmp');

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '/', '所有', NULL, 1);
INSERT INTO `menu` VALUES (2, '/users/**', '用户管理', 1, 1);
INSERT INTO `menu` VALUES (3, '/test/**', '测试', 1, 0);
INSERT INTO `menu` VALUES (4, '/system/dep/**', '系统管理', 1, 1);
INSERT INTO `menu` VALUES (5, '/system/role/**', '权限管理', 1, 1);
INSERT INTO `menu` VALUES (6, '/system/device/**', '设备管理', 1, 1);

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
  INDEX `mid`(`mid`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES (1, 4, 2, NULL);
INSERT INTO `menu_role` VALUES (2, 3, 2, NULL);
INSERT INTO `menu_role` VALUES (3, 2, 3, NULL);

-- ----------------------------
-- Table structure for request_method
-- ----------------------------
DROP TABLE IF EXISTS `request_method`;
CREATE TABLE `request_method`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `method` enum('GET','POST','PUT','DELETE','ALL') CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL,
  `roleId` int(11) NULL DEFAULT NULL,
  `menuId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `roleId`(`roleId`) USING BTREE,
  INDEX `menuId`(`menuId`) USING BTREE,
  CONSTRAINT `request_method_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `request_method_ibfk_2` FOREIGN KEY (`menuId`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  `nameZh` varchar(64) CHARACTER SET utf16 COLLATE utf16_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_HIGH_GRADE_ADMIN', '高级管理员');
INSERT INTO `role` VALUES (2, 'ROLE_SURVEY', '观察员');
INSERT INTO `role` VALUES (3, 'ROLE_SUPER_ADMIN', '超级管理员');
INSERT INTO `role` VALUES (4, 'ROLE_DEALER_ADMIN', '经销商管理员');
INSERT INTO `role` VALUES (5, 'ROLE_ASSEMBLY_MACHINE_ADMIN', '组机厂管理员');

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `depid`(`depId`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`depId`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (21, 'test', '$2a$10$ph0QH3sRyHBHtizuWOUgI.iAfF0GOOQ3hyh3Q/uSttgBUvXa5kRIa', 'cq', '男', NULL, '2018-09-04 16:46:41', '2018-09-16 16:38:22', 1);
INSERT INTO `user` VALUES (22, 'pc', '$2a$10$ph0QH3sRyHBHtizuWOUgI.iAfF0GOOQ3hyh3Q/uSttgBUvXa5kRIa', 'cp', '男', NULL, '2018-09-04 16:49:52', '2018-09-20 14:08:15', 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `roleid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  INDEX `roleid`(`roleid`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf16 COLLATE = utf16_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (8, 21, 5);
INSERT INTO `user_role` VALUES (9, 22, 2);
INSERT INTO `user_role` VALUES (10, 21, 3);
INSERT INTO `user_role` VALUES (11, 22, 1);

SET FOREIGN_KEY_CHECKS = 1;
