/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80025 (8.0.25)
 Source Host           : localhost:3306
 Source Schema         : mybatisplus

 Target Server Type    : MySQL
 Target Server Version : 80025 (8.0.25)
 File Encoding         : 65001

 Date: 10/03/2024 23:28:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_book
-- ----------------------------
DROP TABLE IF EXISTS `sys_book`;
CREATE TABLE `sys_book`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '书名',
  `price` int NULL DEFAULT NULL COMMENT '价格',
  `is_deleted` int NULL DEFAULT 0,
  `user_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_book
-- ----------------------------
INSERT INTO `sys_book` VALUES (1, '西游记', 88, 0, 6);
INSERT INTO `sys_book` VALUES (2, '三国演义', 66, 0, 6);
INSERT INTO `sys_book` VALUES (3, '红楼梦', 50, 0, 3);
INSERT INTO `sys_book` VALUES (4, '水浒传', 30, 0, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `is_deleted` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'Jone', 18, 'test1@baomidou.com', 0);
INSERT INTO `sys_user` VALUES (2, 'Jack', 20, 'test2@baomidou.com', 0);
INSERT INTO `sys_user` VALUES (3, 'Tom', 28, 'test3@baomidou.com', 0);
INSERT INTO `sys_user` VALUES (4, 'Sandy', 21, 'test4@baomidou.com', 0);
INSERT INTO `sys_user` VALUES (5, 'Billie', 24, 'test5@baomidou.com', 0);
INSERT INTO `sys_user` VALUES (6, 'fgq', 23, 'lxy@163.com', 0);

SET FOREIGN_KEY_CHECKS = 1;
