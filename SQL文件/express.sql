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

 Date: 03/11/2025 23:35:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for express
-- ----------------------------
DROP TABLE IF EXISTS `express`;
CREATE TABLE `express`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '快递ID（自增）',
  `number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '快递单号',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收件人姓名',
  `userPhone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收件人手机号',
  `company` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '快递公司',
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '取件码',
  `in_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间',
  `out_time` timestamp NULL DEFAULT NULL COMMENT '出库时间',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（0-待取件，1-已取件）',
  `sysPhone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '录入人手机号',
  `locker_id` int NULL DEFAULT NULL COMMENT '快递柜ID',
  `send_district` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发货区县（仅限上海各区）',
  `receive_district` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货区县（仅限上海各区）',
  `route_path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '最短路径经过的区',
  `total_distance` decimal(10, 2) NULL DEFAULT NULL COMMENT '总距离（公里）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `number`(`number` ASC) USING BTREE,
  INDEX `idx_number`(`number` ASC) USING BTREE,
  INDEX `idx_userPhone`(`userPhone` ASC) USING BTREE,
  INDEX `idx_sysPhone`(`sysPhone` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_in_time`(`in_time` ASC) USING BTREE,
  INDEX `idx_locker_id`(`locker_id` ASC) USING BTREE,
  INDEX `idx_send_district`(`send_district` ASC) USING BTREE,
  INDEX `idx_receive_district`(`receive_district` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 164 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '快递表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of express
-- ----------------------------
INSERT INTO `express` VALUES (18, '113001', '张三', '13843838438', '申通快递', '4440001', '2025-01-15 09:23:45', NULL, 0, '16666666666', 27, '青浦区', '徐汇区', NULL, NULL);
INSERT INTO `express` VALUES (19, '113002', '张三', '13843838438', '申通快递', '4440002', '2025-02-08 14:37:22', NULL, 0, '16666666666', 115, '长宁区', '闵行区', NULL, NULL);
INSERT INTO `express` VALUES (20, '113003', '张三', '13843838438', '申通快递', '4440003', '2025-03-12 11:51:10', NULL, 0, '16666666666', 66, '普陀区', '普陀区', NULL, NULL);
INSERT INTO `express` VALUES (21, '113004', '张三', '13843838438', '申通快递', '4440004', '2025-04-05 16:45:33', NULL, 0, '16666666666', 12, '杨浦区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (22, '113005', '张三', '13843838438', '申通快递', '4440005', '2025-05-20 08:12:57', NULL, 0, '16666666666', 192, '徐汇区', '松江区', NULL, NULL);
INSERT INTO `express` VALUES (23, '113006', '张三', '13843838438', '申通快递', '4440006', '2025-06-18 13:28:44', NULL, 0, '16666666666', 113, '宝山区', '闵行区', NULL, NULL);
INSERT INTO `express` VALUES (24, '113007', '张三', '13843838438', '申通快递', '4440007', '2025-07-22 10:15:39', NULL, 0, '16666666666', 188, '闵行区', '松江区', NULL, NULL);
INSERT INTO `express` VALUES (25, '113008', '张三', '13843838438', '申通快递', '4440008', '2025-08-30 17:42:11', NULL, 0, '16666666666', 238, '嘉定区', '崇明区', NULL, NULL);
INSERT INTO `express` VALUES (26, '113009', '张三', '13843838438', '申通快递', '4440009', '2025-09-14 12:08:26', NULL, 0, '16666666666', 139, '黄浦区', '嘉定区', NULL, NULL);
INSERT INTO `express` VALUES (27, '1130010', '张三', '13843838438', '申通快递', '44400010', '2025-10-25 15:33:48', NULL, 0, '16666666666', 6, '松江区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (28, '1130011', '张三', '13843838438', '申通快递', '44400011', '2025-11-03 07:59:12', NULL, 0, '16666666666', 45, '静安区', '长宁区', NULL, NULL);
INSERT INTO `express` VALUES (29, '1130012', '张三', '13843838438', '申通快递', '44400012', '2025-12-19 18:24:35', NULL, 0, '16666666666', 7, '长宁区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (30, '1130013', '张三', '13843838438', '申通快递', '44400013', '2025-01-28 11:47:53', NULL, 0, '16666666666', 129, '长宁区', '宝山区', NULL, NULL);
INSERT INTO `express` VALUES (31, '1130014', '张三', '13843838438', '申通快递', '44400014', '2025-02-14 13:22:41', NULL, 0, '16666666666', 189, '长宁区', '松江区', NULL, NULL);
INSERT INTO `express` VALUES (32, '1130015', '张三', '13843838438', '申通快递', '44400015', '2025-03-07 16:08:29', NULL, 0, '16666666666', 57, '嘉定区', '静安区', NULL, NULL);
INSERT INTO `express` VALUES (33, '1130016', '张三', '13843838438', '申通快递', '44400016', '2025-04-23 09:35:17', NULL, 0, '16666666666', 226, '嘉定区', '崇明区', NULL, NULL);
INSERT INTO `express` VALUES (34, '1130017', '张三', '13843838438', '申通快递', '44400017', '2025-05-11 14:51:44', NULL, 0, '16666666666', 100, '闵行区', '杨浦区', NULL, NULL);
INSERT INTO `express` VALUES (35, '1130018', '张三', '13843838438', '申通快递', '44400018', '2025-06-29 10:27:36', NULL, 0, '16666666666', 235, '长宁区', '崇明区', NULL, NULL);
INSERT INTO `express` VALUES (36, '1130019', '张三', '13843838438', '申通快递', '44400019', '2025-07-17 08:44:52', NULL, 0, '16666666666', 146, '崇明区', '嘉定区', NULL, NULL);
INSERT INTO `express` VALUES (37, '1130020', '张三', '13843838438', '申通快递', '44400020', '2025-08-04 15:19:28', NULL, 0, '16666666666', 33, '金山区', '长宁区', NULL, NULL);
INSERT INTO `express` VALUES (38, '1130021', '张三', '13843838438', '申通快递', '44400021', '2025-09-26 12:33:14', NULL, 0, '16666666666', 126, '虹口区', '宝山区', NULL, NULL);
INSERT INTO `express` VALUES (39, '1130022', '张三', '13843838438', '申通快递', '44400022', '2025-10-08 17:58:47', NULL, 0, '16666666666', 174, '普陀区', '金山区', NULL, NULL);
INSERT INTO `express` VALUES (40, '1130023', '张三', '13843838438', '申通快递', '44400023', '2025-11-21 11:12:39', NULL, 0, '16666666666', 140, '松江区', '嘉定区', NULL, NULL);
INSERT INTO `express` VALUES (41, '1130024', '张三', '13843838438', '申通快递', '44400024', '2025-12-10 13:47:25', NULL, 0, '16666666666', 153, '徐汇区', '浦东新区', NULL, NULL);
INSERT INTO `express` VALUES (42, '1130025', '张三', '13843838438', '申通快递', '44400025', '2025-01-05 16:22:18', NULL, 0, '16666666666', 90, '浦东新区', '虹口区', NULL, NULL);
INSERT INTO `express` VALUES (43, '1130026', '张三', '13843838438', '申通快递', '44400026', '2025-02-27 09:48:32', NULL, 0, '16666666666', 170, '杨浦区', '金山区', NULL, NULL);
INSERT INTO `express` VALUES (44, '1130027', '张三', '13843838438', '申通快递', '44400027', '2025-03-19 14:15:54', NULL, 0, '16666666666', 219, '长宁区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (45, '1130028', '张三', '13843838438', '申通快递', '44400028', '2025-04-14 10:39:41', NULL, 0, '16666666666', 206, '长宁区', '青浦区', NULL, NULL);
INSERT INTO `express` VALUES (46, '1130029', '张三', '13843838438', '申通快递', '44400029', '2025-05-07 18:04:23', NULL, 0, '16666666666', 5, '宝山区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (47, '1130030', '张三', '13843838438', '申通快递', '44400030', '2025-06-25 12:28:16', NULL, 0, '16666666666', 132, '长宁区', '宝山区', NULL, NULL);
INSERT INTO `express` VALUES (48, '1130031', '张三', '13843838438', '申通快递', '44400031', '2025-07-13 15:53:49', NULL, 0, '16666666666', 120, '金山区', '闵行区', NULL, NULL);
INSERT INTO `express` VALUES (49, '1130032', '张三', '13843838438', '申通快递', '44400032', '2025-08-21 08:17:35', NULL, 0, '16666666666', 42, '闵行区', '长宁区', NULL, NULL);
INSERT INTO `express` VALUES (50, '1130033', '张三', '13843838438', '申通快递', '44400033', '2025-09-02 11:42:27', NULL, 0, '16666666666', 199, '长宁区', '青浦区', NULL, NULL);
INSERT INTO `express` VALUES (51, '1130034', '张三', '13843838438', '申通快递', '44400034', '2025-10-19 17:06:14', NULL, 0, '16666666666', 218, '虹口区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (52, '1130035', '张三', '13843838438', '申通快递', '44400035', '2025-11-16 13:31:58', NULL, 0, '16666666666', 240, '闵行区', '崇明区', NULL, NULL);
INSERT INTO `express` VALUES (53, '1130036', '张三', '13843838438', '申通快递', '44400036', '2025-12-28 09:55:42', NULL, 0, '16666666666', 180, '青浦区', '金山区', NULL, NULL);
INSERT INTO `express` VALUES (54, '1130037', '张三', '13843838438', '申通快递', '44400037', '2025-01-17 14:20:31', NULL, 0, '16666666666', 145, '奉贤区', '嘉定区', NULL, NULL);
INSERT INTO `express` VALUES (55, '1130038', '张三', '13843838438', '申通快递', '44400038', '2025-02-09 10:44:19', NULL, 0, '16666666666', 127, '普陀区', '宝山区', NULL, NULL);
INSERT INTO `express` VALUES (56, '1130039', '张三', '13843838438', '申通快递', '44400039', '2025-03-24 16:09:07', NULL, 0, '16666666666', 196, '杨浦区', '青浦区', NULL, NULL);
INSERT INTO `express` VALUES (57, '1130040', '张三', '13843838438', '申通快递', '44400040', '2025-04-30 12:34:52', NULL, 0, '16666666666', 225, '静安区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (58, '1130041', '张三', '13843838438', '申通快递', '44400041', '2025-05-18 08:58:46', NULL, 0, '16666666666', 161, '静安区', '浦东新区', NULL, NULL);
INSERT INTO `express` VALUES (59, '1130042', '张三', '13843838438', '申通快递', '44400042', '2025-06-07 15:23:33', NULL, 0, '16666666666', 95, '崇明区', '杨浦区', NULL, NULL);
INSERT INTO `express` VALUES (60, '1130043', '张三', '13843838438', '申通快递', '44400043', '2025-07-26 11:47:21', NULL, 0, '16666666666', 18, '浦东新区', '徐汇区', NULL, NULL);
INSERT INTO `express` VALUES (61, '1130044', '张三', '13843838438', '申通快递', '44400044', '2025-08-14 17:12:09', NULL, 0, '16666666666', 8, '普陀区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (62, '1130045', '张三', '13843838438', '申通快递', '44400045', '2025-09-09 13:36:54', NULL, 0, '16666666666', 130, '普陀区', '宝山区', NULL, NULL);
INSERT INTO `express` VALUES (63, '1130046', '张三', '13843838438', '申通快递', '44400046', '2025-10-27 09:01:38', NULL, 0, '16666666666', 229, '徐汇区', '崇明区', NULL, NULL);
INSERT INTO `express` VALUES (64, '1130047', '张三', '13843838438', '申通快递', '44400047', '2025-11-13 14:26:25', NULL, 0, '16666666666', 35, '黄浦区', '长宁区', NULL, NULL);
INSERT INTO `express` VALUES (65, '1130048', '张三', '13843838438', '申通快递', '44400048', '2025-12-05 10:50:17', NULL, 0, '16666666666', 118, '闵行区', '闵行区', NULL, NULL);
INSERT INTO `express` VALUES (66, '1130049', '张三', '13843838438', '申通快递', '44400049', '2025-01-22 16:15:03', NULL, 0, '16666666666', 155, '闵行区', '浦东新区', NULL, NULL);
INSERT INTO `express` VALUES (67, '1130050', '张三', '13843838438', '申通快递', '44400050', '2025-02-18 12:39:48', NULL, 0, '16666666666', 211, '静安区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (68, '1130051', '张三', '13843838438', '申通快递', '44400051', '2025-03-11 08:04:36', NULL, 0, '16666666666', 178, '黄浦区', '金山区', NULL, NULL);
INSERT INTO `express` VALUES (69, '1130052', '张三', '13843838438', '申通快递', '44400052', '2025-04-28 14:29:24', NULL, 0, '16666666666', 208, '静安区', '青浦区', NULL, NULL);
INSERT INTO `express` VALUES (70, '1130053', '张三', '13843838438', '申通快递', '44400053', '2025-05-16 10:53:12', NULL, 0, '16666666666', 20, '青浦区', '徐汇区', NULL, NULL);
INSERT INTO `express` VALUES (71, '1130054', '张三', '13843838438', '申通快递', '44400054', '2025-06-03 17:18:05', NULL, 0, '16666666666', 54, '金山区', '静安区', NULL, NULL);
INSERT INTO `express` VALUES (72, '1130055', '张三', '13843838438', '申通快递', '44400055', '2025-07-21 13:42:53', NULL, 0, '16666666666', 83, '崇明区', '虹口区', NULL, NULL);
INSERT INTO `express` VALUES (73, '1130056', '张三', '13843838438', '申通快递', '44400056', '2025-08-09 09:07:41', NULL, 0, '16666666666', 75, '普陀区', '普陀区', NULL, NULL);
INSERT INTO `express` VALUES (74, '1130057', '张三', '13843838438', '申通快递', '44400057', '2025-09-30 15:32:29', NULL, 0, '16666666666', 224, '松江区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (75, '1130058', '张三', '13843838438', '申通快递', '44400058', '2025-10-12 11:56:17', NULL, 0, '16666666666', 59, '浦东新区', '静安区', NULL, NULL);
INSERT INTO `express` VALUES (76, '1130059', '张三', '13843838438', '申通快递', '44400059', '2025-11-25 18:21:04', NULL, 0, '16666666666', 3, '虹口区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (77, '1130060', '张三', '13843838438', '申通快递', '44400060', '2025-12-17 14:45:52', NULL, 0, '16666666666', 198, '徐汇区', '青浦区', NULL, NULL);
INSERT INTO `express` VALUES (78, '1130061', '张三', '13843838438', '申通快递', '44400061', '2025-01-09 10:10:38', NULL, 0, '16666666666', 17, '金山区', '徐汇区', NULL, NULL);
INSERT INTO `express` VALUES (79, '1130062', '张三', '13843838438', '申通快递', '44400062', '2025-02-26 16:35:26', NULL, 0, '16666666666', 79, '黄浦区', '虹口区', NULL, NULL);
INSERT INTO `express` VALUES (80, '1130063', '张三', '13843838438', '申通快递', '44400063', '2025-03-15 12:59:14', NULL, 0, '16666666666', 121, '徐汇区', '宝山区', NULL, NULL);
INSERT INTO `express` VALUES (81, '1130064', '张三', '13843838438', '申通快递', '44400064', '2025-04-07 09:24:02', NULL, 0, '16666666666', 194, '杨浦区', '松江区', NULL, NULL);
INSERT INTO `express` VALUES (82, '1130065', '张三', '13843838438', '申通快递', '44400065', '2025-05-29 14:48:49', NULL, 0, '16666666666', 149, '静安区', '嘉定区', NULL, NULL);
INSERT INTO `express` VALUES (83, '1130066', '张三', '13843838438', '申通快递', '44400066', '2025-06-16 11:13:37', NULL, 0, '16666666666', 203, '金山区', '青浦区', NULL, NULL);
INSERT INTO `express` VALUES (84, '1130067', '张三', '13843838438', '申通快递', '44400067', '2025-07-04 17:38:25', NULL, 0, '16666666666', 114, '静安区', '闵行区', NULL, NULL);
INSERT INTO `express` VALUES (85, '1130068', '张三', '13843838438', '申通快递', '44400068', '2025-08-27 13:02:13', NULL, 0, '16666666666', 69, '虹口区', '普陀区', NULL, NULL);
INSERT INTO `express` VALUES (86, '1130069', '张三', '13843838438', '申通快递', '44400069', '2025-09-19 09:27:01', NULL, 0, '16666666666', 236, '静安区', '崇明区', NULL, NULL);
INSERT INTO `express` VALUES (87, '1130070', '张三', '13843838438', '申通快递', '44400070', '2025-10-31 15:51:48', NULL, 0, '16666666666', 88, '徐汇区', '虹口区', NULL, NULL);
INSERT INTO `express` VALUES (88, '1130071', '张三', '13843838438', '申通快递', '44400071', '2025-11-08 12:16:36', NULL, 0, '16666666666', 137, '长宁区', '嘉定区', NULL, NULL);
INSERT INTO `express` VALUES (89, '1130072', '张三', '13843838438', '申通快递', '44400072', '2025-12-24 18:41:24', NULL, 0, '16666666666', 217, '金山区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (90, '1130073', '张三', '13843838438', '申通快递', '44400073', '2025-01-13 14:06:12', NULL, 0, '16666666666', 101, '长宁区', '杨浦区', NULL, NULL);
INSERT INTO `express` VALUES (91, '1130074', '张三', '13843838438', '申通快递', '44400074', '2025-02-05 10:30:59', NULL, 0, '16666666666', 228, '浦东新区', '崇明区', NULL, NULL);
INSERT INTO `express` VALUES (92, '1130075', '张三', '13843838438', '申通快递', '44400075', '2025-03-28 16:55:47', NULL, 0, '16666666666', 190, '杨浦区', '松江区', NULL, NULL);
INSERT INTO `express` VALUES (93, '1130076', '张三', '13843838438', '申通快递', '44400076', '2025-04-17 13:20:35', NULL, 0, '16666666666', 48, '虹口区', '静安区', NULL, NULL);
INSERT INTO `express` VALUES (94, '1130077', '张三', '13843838438', '申通快递', '44400077', '2025-05-25 09:45:23', NULL, 0, '16666666666', 105, '黄浦区', '杨浦区', NULL, NULL);
INSERT INTO `express` VALUES (95, '1130078', '张三', '13843838438', '申通快递', '44400078', '2025-06-13 16:10:11', NULL, 0, '16666666666', 173, '黄浦区', '金山区', NULL, NULL);
INSERT INTO `express` VALUES (96, '1130079', '张三', '13843838438', '申通快递', '44400079', '2025-07-02 12:34:59', NULL, 0, '16666666666', 58, '静安区', '静安区', NULL, NULL);
INSERT INTO `express` VALUES (97, '1130080', '张三', '13843838438', '申通快递', '44400080', '2025-08-19 08:59:47', NULL, 0, '16666666666', 37, '浦东新区', '长宁区', NULL, NULL);
INSERT INTO `express` VALUES (98, '1130081', '张三', '13843838438', '申通快递', '44400081', '2025-09-11 15:24:35', NULL, 0, '16666666666', 86, '徐汇区', '虹口区', NULL, NULL);
INSERT INTO `express` VALUES (99, '1130082', '张三', '13843838438', '申通快递', '44400082', '2025-10-23 11:49:23', NULL, 0, '16666666666', 181, '松江区', '松江区', NULL, NULL);
INSERT INTO `express` VALUES (100, '1130083', '张三', '13843838438', '申通快递', '44400083', '2025-11-30 18:14:11', NULL, 0, '16666666666', 13, '静安区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (101, '1130084', '张三', '13843838438', '申通快递', '44400084', '2025-12-08 14:38:59', NULL, 0, '16666666666', 138, '崇明区', '嘉定区', NULL, NULL);
INSERT INTO `express` VALUES (102, '1130085', '张三', '13843838438', '申通快递', '44400085', '2025-01-26 10:03:47', NULL, 0, '16666666666', 168, '嘉定区', '金山区', NULL, NULL);
INSERT INTO `express` VALUES (103, '1130086', '张三', '13843838438', '申通快递', '44400086', '2025-02-13 16:28:35', NULL, 0, '16666666666', 25, '杨浦区', '徐汇区', NULL, NULL);
INSERT INTO `express` VALUES (104, '1130087', '张三', '13843838438', '申通快递', '44400087', '2025-03-06 12:53:23', NULL, 0, '16666666666', 11, '普陀区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (105, '1130088', '张三', '13843838438', '申通快递', '44400088', '2025-04-21 09:18:11', NULL, 0, '16666666666', 141, '松江区', '嘉定区', NULL, NULL);
INSERT INTO `express` VALUES (106, '1130089', '张三', '13843838438', '申通快递', NULL, '2025-05-14 14:42:59', '2025-11-03 18:48:05', 1, '16666666666', 109, '浦东新区', '闵行区', NULL, NULL);
INSERT INTO `express` VALUES (107, '1130090', '张三', '13843838438', '申通快递', '44400090', '2025-06-01 11:07:47', NULL, 0, '16666666666', 214, '普陀区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (108, '1130091', '张三', '13843838438', '申通快递', '44400091', '2025-07-18 17:32:35', NULL, 0, '16666666666', 39, '青浦区', '长宁区', NULL, NULL);
INSERT INTO `express` VALUES (109, '1130092', '张三', '13843838438', '申通快递', '44400092', '2025-08-07 13:57:23', NULL, 0, '16666666666', 2, '长宁区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (110, '1130093', '张三', '13843838438', '申通快递', '44400093', '2025-09-25 10:22:11', NULL, 0, '16666666666', 154, '崇明区', '浦东新区', NULL, NULL);
INSERT INTO `express` VALUES (112, '1130095', '张三', '13843838438', '申通快递', '44400095', '2025-10-16 15:46:59', NULL, 0, '16666666666', 77, '嘉定区', '虹口区', NULL, NULL);
INSERT INTO `express` VALUES (113, '1130096', '张三', '13843838438', '申通快递', '44400096', '2025-11-27 12:11:47', NULL, 0, '16666666666', 107, '杨浦区', '闵行区', NULL, NULL);
INSERT INTO `express` VALUES (114, '1130097', '张三', '13843838438', '申通快递', '44400097', '2025-12-15 18:36:35', NULL, 0, '16666666666', 157, '崇明区', '浦东新区', NULL, NULL);
INSERT INTO `express` VALUES (115, '1130098', '张三', '13843838438', '申通快递', '44400098', '2025-01-07 15:01:23', NULL, 0, '16666666666', 222, '长宁区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (116, '1130099', '张三', '13843838438', '申通快递', '44400099', '2025-02-22 11:26:11', NULL, 0, '16666666666', 68, '普陀区', '普陀区', NULL, NULL);
INSERT INTO `express` VALUES (141, '888888', '李四', '10000', '阿联酋邮政', '771231', '2025-03-14 13:43:15', NULL, 0, '18888888888', 31, '宝山区', '长宁区', NULL, NULL);
INSERT INTO `express` VALUES (142, '124', '王五', '17636602606', '京东快递', NULL, '2025-04-09 00:59:32', '2025-04-24 15:27:46', 1, '17636602606', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `express` VALUES (149, '124123456', '王五', '17636602606', '京东快递', NULL, '2025-05-13 01:01:54', '2025-05-24 16:56:06', 1, '17636602606', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `express` VALUES (151, '000111213', '王五', '17636602606', '京东快递', '384909', '2025-06-18 01:02:48', NULL, 0, '17636602606', 142, '宝山区', '嘉定区', NULL, NULL);
INSERT INTO `express` VALUES (153, '1250011', '王五', '17636602606', '京东快递', '273167', '2025-07-22 01:12:21', NULL, 0, '17636602606', 40, '青浦区', '长宁区', NULL, NULL);
INSERT INTO `express` VALUES (154, '128123', '王五', '17636602606', '京东快递', '641080', '2025-08-15 01:21:58', NULL, 0, '17636602606', 223, '徐汇区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (155, '127000', '王五', '17636602606', '京东快递', '999315', '2025-09-19 01:25:29', NULL, 0, '17636602606', 201, '松江区', '青浦区', NULL, NULL);
INSERT INTO `express` VALUES (156, '12000', '王五', '17636602606', '京东快递', '654638', '2025-10-11 01:29:05', NULL, 0, '17636602606', 185, '闵行区', '松江区', NULL, NULL);
INSERT INTO `express` VALUES (157, '12600', '王五', '17636602606', '京东快递', '813715', '2025-11-23 01:49:48', NULL, 0, '17636602606', 14, '普陀区', '黄浦区', NULL, NULL);
INSERT INTO `express` VALUES (160, '12911', '王五', '17636602606', '京东快递', NULL, '2025-12-17 02:18:28', '2025-12-24 16:04:37', 1, '17636602606', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `express` VALUES (161, '12333', '赵六', '18888888888', '顺丰快递', NULL, '2025-01-29 00:38:37', '2025-01-24 15:08:56', 1, '18888888888', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `express` VALUES (162, '9876888', 'cjy', '17301788057', '顺丰速运', NULL, '2025-11-03 18:35:39', '2025-11-03 18:41:30', 1, '18888888888', 212, '虹口区', '奉贤区', NULL, NULL);
INSERT INTO `express` VALUES (163, '486486', '陈江越', '17301788057', '顺丰速运', NULL, '2025-11-03 19:02:55', '2025-11-03 19:04:44', 1, '18888888888', 216, '嘉定区', '奉贤区', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
