SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for geom_info
-- ----------------------------
DROP TABLE IF EXISTS `geom_info`;
CREATE TABLE `geom_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `area_formula` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `area_params` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `area_params_desc` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `isdeleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of geom_info
-- ----------------------------
INSERT INTO `geom_info` VALUES (1, 'Triangle', 'h*b', 'h,b', 'height,base', 0);
INSERT INTO `geom_info` VALUES (2, 'Square', 's^2', 's', 'side', 0);
INSERT INTO `geom_info` VALUES (3, 'Rectangle','l*w', 'l,w', 'length,width', 0);
INSERT INTO `geom_info` VALUES (4, 'Parallelogram', 'h*b', 'h,b', 'height,base', 0);
INSERT INTO `geom_info` VALUES (5, 'Rhombus', 'p*q/2', 'p,q', 'diagonal p,diagonal q', 0);
INSERT INTO `geom_info` VALUES (6, 'Kite', 'p*q/2', 'p,q', 'diagonal p,diagonal q', 0);
INSERT INTO `geom_info` VALUES (7, 'Trapezium', '((a+b)/2)*h', 'a,b,h', 'parallel side a,parallel side b,height', 0);
INSERT INTO `geom_info` VALUES (8, 'Circle', 'pi*(r^2)', 'r', 'radius', 0);
INSERT INTO `geom_info` VALUES (9, 'Ellipse', 'pi*r*s', 'r,s', 'radius r,radius s', 0);

-- ----------------------------
-- Table structure for geom_property
-- ----------------------------
DROP TABLE IF EXISTS `geom_property`;
CREATE TABLE `geom_property`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `property` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `type` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'NUMBER / CHOICE',
  `response` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `isdeleted` tinyint(1) NOT NULL DEFAULT 0,
  `entrytime` datetime(0) NULL DEFAULT NULL,
  `deletedtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of geom_property
-- ----------------------------
INSERT INTO `geom_property` VALUES (1, 'How Many Side ?', 'NUMBER', '0 - 99', 0, '2019-10-18 23:43:05', NULL);
INSERT INTO `geom_property` VALUES (2, 'Maximum Number of Right Angle ?', 'NUMBER', '0 - 99', 0, '2019-10-18 23:44:07', NULL);
INSERT INTO `geom_property` VALUES (3, 'Have Right Angle ?', 'CHOICE', 'Yes,Maybe,No', 0, '2019-10-18 23:45:38', NULL);
INSERT INTO `geom_property` VALUES (4, 'All Side Have Equal Length ?', 'CHOICE', 'Yes,Maybe,No', 0, '2019-10-18 23:46:28', NULL);
INSERT INTO `geom_property` VALUES (5, 'Have Parallel Side ?', 'CHOICE', 'Yes,Maybe,No', 0, '2019-10-18 23:46:56', NULL);
INSERT INTO `geom_property` VALUES (6, 'Parallel Side Have Equal Length ?', 'CHOICE', 'Yes,Maybe,No', 0, '2019-10-18 23:47:36', NULL);
INSERT INTO `geom_property` VALUES (7, 'Have Curve Side ?', 'CHOICE', 'None,Some,All', 0, '2019-10-18 23:48:52', NULL);
INSERT INTO `geom_property` VALUES (8, 'Have Equal Radius ?', 'CHOICE', 'Yes,Maybe,No', 0, '2019-10-18 23:49:17', NULL);
INSERT INTO `geom_property` VALUES (9, 'Have Perpendicular Diagonal ?', 'CHOICE', 'Yes,Maybe,No', 0, '2019-10-18 23:50:22', NULL);
INSERT INTO `geom_property` VALUES (10, 'Have Equal Opposite Angle ?', 'CHOICE', 'Yes,Maybe,No', 0, '2019-10-18 23:51:32', NULL);

-- ----------------------------
-- Table structure for geom_property_mapping
-- ----------------------------
DROP TABLE IF EXISTS `geom_property_mapping`;
CREATE TABLE `geom_property_mapping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `geom_id` int(11) NOT NULL,
  `property_id` int(11) NOT NULL,
  `property_response` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `isdeleted` tinyint(1) NOT NULL DEFAULT 0,
  `entrytime` datetime(0) NULL DEFAULT NULL,
  `deletedtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `geom_id`(`geom_id`) USING BTREE,
  INDEX `property_id`(`property_id`) USING BTREE,
  CONSTRAINT `geom_property_mapping_ibfk_1` FOREIGN KEY (`geom_id`) REFERENCES `geom_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `geom_property_mapping_ibfk_2` FOREIGN KEY (`property_id`) REFERENCES `geom_property` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of geom_property_mapping
-- ----------------------------
INSERT INTO `geom_property_mapping` VALUES (1, 1, 1, '3', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (2, 1, 2, '1', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (3, 1, 3, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (4, 1, 4, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (5, 1, 5, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (6, 1, 6, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (7, 1, 7, 'NONE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (8, 1, 8, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (9, 1, 9, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (10, 1, 10, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (11, 2, 1, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (12, 2, 2, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (13, 2, 3, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (14, 2, 4, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (15, 2, 5, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (16, 2, 6, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (17, 2, 7, 'NONE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (18, 2, 8, 'NO ', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (19, 2, 9, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (20, 2, 10, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (21, 3, 1, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (22, 3, 2, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (23, 3, 3, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (24, 3, 4, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (25, 3, 5, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (26, 3, 6, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (27, 3, 7, 'NONE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (28, 3, 8, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (29, 3, 9, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (30, 3, 10, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (31, 4, 1, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (32, 4, 2, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (33, 4, 3, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (34, 4, 4, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (35, 4, 5, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (36, 4, 6, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (37, 4, 7, 'NONE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (38, 4, 8, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (39, 4, 9, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (40, 4, 10, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (41, 5, 1, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (42, 5, 2, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (43, 5, 3, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (44, 5, 4, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (45, 5, 5, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (46, 5, 6, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (47, 5, 7, 'NONE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (48, 5, 8, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (49, 5, 9, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (50, 5, 10, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (51, 6, 1, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (52, 6, 2, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (53, 6, 3, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (54, 6, 4, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (55, 6, 5, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (56, 6, 6, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (57, 6, 7, 'NONE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (58, 6, 8, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (59, 6, 9, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (60, 6, 10, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (61, 7, 1, '4', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (62, 7, 2, '2', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (63, 7, 3, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (64, 7, 4, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (65, 7, 5, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (66, 7, 6, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (67, 7, 7, 'NONE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (68, 7, 8, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (69, 7, 9, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (70, 7, 10, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (71, 8, 1, '0', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (72, 8, 2, '0', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (73, 8, 3, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (74, 8, 4, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (75, 8, 5, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (76, 8, 6, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (77, 8, 7, 'ALL', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (78, 8, 8, 'YES', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (79, 8, 9, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (80, 8, 10, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (81, 9, 1, '0', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (82, 9, 2, '0', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (83, 9, 3, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (84, 9, 4, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (85, 9, 5, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (86, 9, 6, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (87, 9, 7, 'ALL', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (88, 9, 8, 'MAYBE', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (89, 9, 9, 'NO', 0, '2019-10-19 00:40:28', NULL);
INSERT INTO `geom_property_mapping` VALUES (90, 9, 10, 'NO', 0, '2019-10-19 00:40:28', NULL);

-- ----------------------------
-- Table structure for geom_relation
-- ----------------------------
DROP TABLE IF EXISTS `geom_relation`;
CREATE TABLE `geom_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_main_geom` int(11) NOT NULL,
  `id_related_geom` int(11) NOT NULL,
  `isrelated` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_main_geom`(`id_main_geom`) USING BTREE,
  INDEX `id_related_geom`(`id_related_geom`) USING BTREE,
  CONSTRAINT `geom_relation_ibfk_1` FOREIGN KEY (`id_main_geom`) REFERENCES `geom_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `geom_relation_ibfk_2` FOREIGN KEY (`id_related_geom`) REFERENCES `geom_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for geom_trail
-- ----------------------------
DROP TABLE IF EXISTS `geom_trail`;
CREATE TABLE `geom_trail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `geom_id` int(11) NOT NULL,
  `geom_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `id_modifier` int(11) NULL DEFAULT NULL,
  `status` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'CREATED / UPDATED / DELETED',
  `entrytime` datetime(0) DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of geom_trail
-- ----------------------------
INSERT INTO `geom_trail` VALUES (1, 1, 'Triangle', '1', 'CREATED', '2019-10-19 00:16:18');
INSERT INTO `geom_trail` VALUES (2, 2, 'Square', '1', 'CREATED', '2019-10-19 00:16:18');
INSERT INTO `geom_trail` VALUES (3, 3, 'Rectangle', '1', 'CREATED', '2019-10-19 00:16:18');
INSERT INTO `geom_trail` VALUES (4, 4, 'Parallelogram', '1', 'CREATED', '2019-10-19 00:16:18');
INSERT INTO `geom_trail` VALUES (5, 5, 'Rhombus', '1', 'CREATED', '2019-10-19 00:16:18');
INSERT INTO `geom_trail` VALUES (6, 6, 'Kite', '1', 'CREATED', '2019-10-19 00:16:18');
INSERT INTO `geom_trail` VALUES (7, 7, 'Trapezium', '1', 'CREATED', '2019-10-19 00:16:18');
INSERT INTO `geom_trail` VALUES (8, 8, 'Circle', '1', 'CREATED', '2019-10-19 00:16:18');
INSERT INTO `geom_trail` VALUES (9, 9, 'Ellipse', '1', 'CREATED', '2019-10-19 00:16:18');

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_info
-- ----------------------------
INSERT INTO `role_info` VALUES (1, 'ADMIN', 'System Administrator');
INSERT INTO `role_info` VALUES (2, 'USER', 'General User');

-- ----------------------------
-- Table structure for user_geom
-- ----------------------------
DROP TABLE IF EXISTS `user_geom`;
CREATE TABLE `user_geom`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) NOT NULL,
  `user_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `geom_id` int(11) NOT NULL,
  `geom_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `geom_params` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `geom_vars` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `geom_area` decimal(10, 2) NULL DEFAULT NULL,
  `isdeleted` tinyint(1) NOT NULL DEFAULT 0,
  `entrytime` datetime(0) NULL DEFAULT NULL,
  `deletedtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `geom_id`(`geom_id`) USING BTREE,
  CONSTRAINT `user_geom_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_geom_ibfk_2` FOREIGN KEY (`geom_id`) REFERENCES `geom_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `isdeleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'admin@admin.com', '$2a$04$HAVWAPGUI.SODrnhnUNpAOTxs/AblFibI/KzgDi2lIZk6W4zyyddS', 'admin', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  `isdeleted` tinyint(1) NOT NULL DEFAULT 0,
  `entrytime` datetime(0) DEFAULT CURRENT_TIMESTAMP(0),
  `deletedtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, 0, '2019-10-18 23:20:49', NULL);

-- ----------------------------
-- Table structure for user_trail
-- ----------------------------
DROP TABLE IF EXISTS `user_trail`;
CREATE TABLE `user_trail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `modifier_id` int(11) NULL DEFAULT NULL,
  `status` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'CREATED / UPDATED / DELETED ',
  `entrytime` datetime(0) DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_trail
-- ----------------------------
INSERT INTO `user_trail` VALUES (1, 1, 1, NULL, 'CREATED', '2019-10-18 23:20:49');

SET FOREIGN_KEY_CHECKS = 1;
