/*
 Navicat Premium Data Transfer

 Source Server         : 81.71.142.76_3306
 Source Server Type    : MySQL
 Source Server Version : 50713
 Source Host           : 81.71.142.76:3306
 Source Schema         : questionnaire

 Target Server Type    : MySQL
 Target Server Version : 50713
 File Encoding         : 65001

 Date: 03/07/2022 13:53:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for project_info
-- ----------------------------
DROP TABLE IF EXISTS `project_info`;
CREATE TABLE `project_info`  (
  `id` bigint(32) NOT NULL COMMENT '雪花id',
  `project_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `project_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '项目说明',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '项目表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of project_info
-- ----------------------------
INSERT INTO `project_info` VALUES (1541989299846914050, 'aaa2', 'aa', 'admin', '2022-06-29 11:37:53', 'admin', '2022-06-29 14:02:19');
INSERT INTO `project_info` VALUES (1541990191149096961, 'ss', 's', 'admin', '2022-06-29 11:41:25', NULL, NULL);
INSERT INTO `project_info` VALUES (1542419265780719618, '123', '123', 'admin', '2022-06-30 16:06:24', NULL, NULL);
INSERT INTO `project_info` VALUES (1542420662093881345, '1111', '123', 'admin', '2022-06-30 16:11:57', NULL, NULL);
INSERT INTO `project_info` VALUES (1542430051613810690, '11111', '1', 'admin', '2022-06-30 16:49:16', NULL, NULL);
INSERT INTO `project_info` VALUES (1542432046353489921, '1', '1', 'admin', '2022-06-30 16:57:12', NULL, NULL);
INSERT INTO `project_info` VALUES (1543208801968332802, '测试项目', '测试项目', 'admin', '2022-07-02 20:23:44', NULL, NULL);
INSERT INTO `project_info` VALUES (1543452955344379906, 'wcy', '1', 'admin', '2022-07-03 12:33:55', NULL, NULL);
INSERT INTO `project_info` VALUES (1543453513383944194, '111111', '111', 'admin', '2022-07-03 12:36:08', NULL, NULL);
INSERT INTO `project_info` VALUES (1543453853651050497, '', '1', 'admin', '2022-07-03 12:37:29', NULL, NULL);
INSERT INTO `project_info` VALUES (1543456718419402754, 'wcy1', '1', 'admin', '2022-07-03 12:48:52', NULL, NULL);
INSERT INTO `project_info` VALUES (1543459912843272193, '测试项目aaa', '测试项目aaa', 'admin', '2022-07-03 13:01:34', NULL, NULL);
INSERT INTO `project_info` VALUES (1543463806675722242, 'test22', 'test22', 'admin', '2022-07-03 13:17:02', NULL, NULL);
INSERT INTO `project_info` VALUES (1543464657188298753, 'aafafaaaaaa', 'aafafaaaaaa', 'admin', '2022-07-03 13:20:25', NULL, NULL);
INSERT INTO `project_info` VALUES (1543468614199549953, '测试啊啊啊啊', '测试啊啊啊啊', 'admin', '2022-07-03 13:36:09', NULL, NULL);
INSERT INTO `project_info` VALUES (1543469150932049922, '安爱飞', '啊啊啊啊', 'admin', '2022-07-03 13:38:17', NULL, NULL);

-- ----------------------------
-- Table structure for project_question
-- ----------------------------
DROP TABLE IF EXISTS `project_question`;
CREATE TABLE `project_question`  (
  `project_id` bigint(32) NOT NULL DEFAULT 0 COMMENT '项目id',
  `questionnaire_id` bigint(32) NOT NULL COMMENT '问卷id',
  `link` bigint(1) NULL DEFAULT NULL COMMENT '链接'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目问卷关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project_question
-- ----------------------------
INSERT INTO `project_question` VALUES (1541989299846914050, 1536246945479401473, 0);
INSERT INTO `project_question` VALUES (1542432046353489921, 1543132061950550018, 0);
INSERT INTO `project_question` VALUES (1542432046353489921, 1543176617932890114, 0);
INSERT INTO `project_question` VALUES (1543208801968332802, 1536246945479401473, 0);
INSERT INTO `project_question` VALUES (1543459912843272193, 1543132061950550018, 0);
INSERT INTO `project_question` VALUES (1543463806675722242, 1543132061950550018, 0);
INSERT INTO `project_question` VALUES (1543464657188298753, 1543132061950550018, 1);
INSERT INTO `project_question` VALUES (1543468614199549953, 1543132061950550018, 0);
INSERT INTO `project_question` VALUES (1543469150932049922, 1543132061950550018, 1);

-- ----------------------------
-- Table structure for questionnaire_info
-- ----------------------------
DROP TABLE IF EXISTS `questionnaire_info`;
CREATE TABLE `questionnaire_info`  (
  `id` bigint(32) NOT NULL COMMENT '雪花id',
  `type` int(1) NULL DEFAULT NULL COMMENT '问卷类型(1-测试、2-生产、3-本地 ...这种格式 自己定义)',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '问卷名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '问卷描述',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '问卷标题',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `status` int(11) NULL DEFAULT 1 COMMENT '问卷状态(1-启用、2-停用)',
  `examination` int(11) NULL DEFAULT 2 COMMENT '是否考试中(1-是、2-否)',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问卷表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of questionnaire_info
-- ----------------------------
INSERT INTO `questionnaire_info` VALUES (1536246945479401473, 1, '测试问卷', '问卷描述', '标题', '2022-06-13 09:00:00', '2022-07-02 15:11:39', 1, 2, 'admin', '2022-06-13 15:19:48', NULL, NULL);
INSERT INTO `questionnaire_info` VALUES (1543132061950550018, 1, '调查名称333', '调查名称333', NULL, NULL, '2022-07-04 15:47:00', 1, 2, 'admin', '2022-07-02 15:18:48', 'admin', '2022-07-02 15:47:16');
INSERT INTO `questionnaire_info` VALUES (1543176617932890114, 1, 'aaaaaaaaaaaaaaaaa', 'aaaaaaaaaaaaaaaaa', NULL, NULL, '2022-07-09 18:42:00', 1, 2, 'admin', '2022-07-02 18:15:51', NULL, NULL);

-- ----------------------------
-- Table structure for questions_info
-- ----------------------------
DROP TABLE IF EXISTS `questions_info`;
CREATE TABLE `questions_info`  (
  `id` bigint(32) NOT NULL COMMENT '雪花id',
  `questionnaire_id` bigint(20) NULL DEFAULT NULL COMMENT '问卷id',
  `num` int(4) NULL DEFAULT NULL COMMENT '试题编号',
  `type` int(1) NULL DEFAULT NULL COMMENT '题目类型(1-单选、2-多选、3-填空)',
  `required` int(1) NULL DEFAULT 1 COMMENT '是否必答(1-必答、2-选答)',
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '题目名称',
  `options` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '选项-以@分割',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(32) NOT NULL COMMENT '用户表主键-雪花id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `status` int(1) NULL DEFAULT NULL COMMENT '是否启用（1启用，0不启用）',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (123143241231, 'admin', '123456', 1, 'admin', '2022-06-12 14:04:46', 'admin', '2022-06-29 17:51:33');
INSERT INTO `user_info` VALUES (1543424759248986113, '6', '6', 1, 'admin', '2022-07-03 10:41:53', NULL, NULL);
INSERT INTO `user_info` VALUES (1543424780686073857, '8', '0', 0, 'admin', '2022-07-03 10:41:58', NULL, NULL);
INSERT INTO `user_info` VALUES (1543424976836894721, '888', '123456', 1, 'admin', '2022-07-03 10:42:45', NULL, NULL);
INSERT INTO `user_info` VALUES (1543425013071486978, '000', '000', 0, 'admin', '2022-07-03 10:42:53', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
