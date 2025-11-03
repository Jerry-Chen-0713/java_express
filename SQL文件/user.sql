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

 Date: 03/11/2025 23:35:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `user_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号码',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `is_user` tinyint NULL DEFAULT 1 COMMENT '是否为普通用户：0-管理员，1-普通用户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name` ASC) USING BTREE,
  UNIQUE INDEX `user_phone`(`user_phone` ASC) USING BTREE,
  INDEX `idx_user_name`(`user_name` ASC) USING BTREE,
  INDEX `idx_user_phone`(`user_phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '13800138000', 'admin123', '2025-10-22 23:55:58', NULL, 0);
INSERT INTO `user` VALUES (2, '陈江越', '17301788057', '197199', '2025-10-23 00:00:59', '2025-11-03 19:01:03', 1);
INSERT INTO `user` VALUES (3, '用户_7816', '13764687816', '725229', '2025-11-03 19:07:05', '2025-11-03 19:07:05', 1);

SET FOREIGN_KEY_CHECKS = 1;
