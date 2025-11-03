/*
 Navicat Premium Data Transfer

 Source Server         : mysqltest
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : express

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 03/11/2025 23:35:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for district_distance
-- ----------------------------
DROP TABLE IF EXISTS `district_distance`;
CREATE TABLE `district_distance`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `district1` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `district2` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `distance` decimal(10, 2) NOT NULL COMMENT '距离（公里）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_districts`(`district1` ASC, `district2` ASC) USING BTREE,
  INDEX `idx_district1`(`district1` ASC) USING BTREE,
  INDEX `idx_district2`(`district2` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '区与区之间的距离表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of district_distance
-- ----------------------------
INSERT INTO `district_distance` VALUES (1, '黄浦区', '徐汇区', 8.50);
INSERT INTO `district_distance` VALUES (2, '黄浦区', '长宁区', 7.20);
INSERT INTO `district_distance` VALUES (3, '黄浦区', '静安区', 3.50);
INSERT INTO `district_distance` VALUES (4, '黄浦区', '普陀区', 9.10);
INSERT INTO `district_distance` VALUES (5, '黄浦区', '虹口区', 4.20);
INSERT INTO `district_distance` VALUES (6, '黄浦区', '杨浦区', 8.70);
INSERT INTO `district_distance` VALUES (7, '黄浦区', '闵行区', 15.30);
INSERT INTO `district_distance` VALUES (8, '黄浦区', '宝山区', 16.80);
INSERT INTO `district_distance` VALUES (9, '黄浦区', '嘉定区', 22.50);
INSERT INTO `district_distance` VALUES (10, '徐汇区', '长宁区', 5.30);
INSERT INTO `district_distance` VALUES (11, '徐汇区', '静安区', 7.80);
INSERT INTO `district_distance` VALUES (12, '徐汇区', '普陀区', 11.20);
INSERT INTO `district_distance` VALUES (13, '徐汇区', '虹口区', 12.10);
INSERT INTO `district_distance` VALUES (14, '徐汇区', '杨浦区', 15.60);
INSERT INTO `district_distance` VALUES (15, '徐汇区', '闵行区', 8.70);
INSERT INTO `district_distance` VALUES (16, '徐汇区', '宝山区', 20.30);
INSERT INTO `district_distance` VALUES (17, '徐汇区', '嘉定区', 25.80);
INSERT INTO `district_distance` VALUES (18, '长宁区', '静安区', 6.50);
INSERT INTO `district_distance` VALUES (19, '长宁区', '普陀区', 8.90);
INSERT INTO `district_distance` VALUES (20, '长宁区', '虹口区', 11.30);
INSERT INTO `district_distance` VALUES (21, '长宁区', '杨浦区', 14.80);
INSERT INTO `district_distance` VALUES (22, '长宁区', '闵行区', 10.20);
INSERT INTO `district_distance` VALUES (23, '长宁区', '宝山区', 18.70);
INSERT INTO `district_distance` VALUES (24, '长宁区', '嘉定区', 23.40);
INSERT INTO `district_distance` VALUES (25, '静安区', '普陀区', 6.20);
INSERT INTO `district_distance` VALUES (26, '静安区', '虹口区', 7.80);
INSERT INTO `district_distance` VALUES (27, '静安区', '杨浦区', 11.30);
INSERT INTO `district_distance` VALUES (28, '静安区', '闵行区', 13.50);
INSERT INTO `district_distance` VALUES (29, '静安区', '宝山区', 14.20);
INSERT INTO `district_distance` VALUES (30, '静安区', '嘉定区', 19.80);
INSERT INTO `district_distance` VALUES (31, '普陀区', '虹口区', 12.50);
INSERT INTO `district_distance` VALUES (32, '普陀区', '杨浦区', 16.10);
INSERT INTO `district_distance` VALUES (33, '普陀区', '闵行区', 15.80);
INSERT INTO `district_distance` VALUES (34, '普陀区', '宝山区', 11.30);
INSERT INTO `district_distance` VALUES (35, '普陀区', '嘉定区', 16.70);
INSERT INTO `district_distance` VALUES (36, '虹口区', '杨浦区', 4.50);
INSERT INTO `district_distance` VALUES (37, '虹口区', '闵行区', 18.20);
INSERT INTO `district_distance` VALUES (38, '虹口区', '宝山区', 13.80);
INSERT INTO `district_distance` VALUES (39, '虹口区', '嘉定区', 24.30);
INSERT INTO `district_distance` VALUES (40, '杨浦区', '闵行区', 21.50);
INSERT INTO `district_distance` VALUES (41, '杨浦区', '宝山区', 9.70);
INSERT INTO `district_distance` VALUES (42, '杨浦区', '嘉定区', 26.80);
INSERT INTO `district_distance` VALUES (43, '闵行区', '宝山区', 24.30);
INSERT INTO `district_distance` VALUES (44, '闵行区', '嘉定区', 28.70);
INSERT INTO `district_distance` VALUES (45, '宝山区', '嘉定区', 18.20);

SET FOREIGN_KEY_CHECKS = 1;
