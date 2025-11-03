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

 Date: 03/11/2025 23:34:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for courier
-- ----------------------------
DROP TABLE IF EXISTS `courier`;
CREATE TABLE `courier`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '快递员编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号码',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `delivery_count` int NULL DEFAULT 0 COMMENT '派件数',
  `register_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '上次登陆时间',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone` ASC) USING BTREE,
  UNIQUE INDEX `id_card`(`id_card` ASC) USING BTREE,
  INDEX `idx_phone`(`phone` ASC) USING BTREE,
  INDEX `idx_id_card`(`id_card` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1012 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '快递员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courier
-- ----------------------------
INSERT INTO `courier` VALUES (1001, '张三', '13843838438', '110101199001011234', 'wsygdsb', 100000, '2025-10-23 00:26:40', '2025-10-23 00:26:40', 1);
INSERT INTO `courier` VALUES (1002, '李四', '13843838439', '110101199001011235', 'wsygdsb', 98000, '2025-10-23 00:26:40', '2025-10-23 00:26:40', 1);
INSERT INTO `courier` VALUES (1003, '王五', '13843838440', '110101199001011236', 'wsygdsb', 120000, '2025-10-23 00:26:40', '2025-10-23 00:26:40', 1);
INSERT INTO `courier` VALUES (1004, '云落', '17301788057', '110101199001011237', '197199', 85000, '2025-10-23 00:26:40', '2025-10-23 00:26:40', 1);

SET FOREIGN_KEY_CHECKS = 1;
