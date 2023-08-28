/*
 Navicat Premium Data Transfer

 Source Server         : 8.131.53.96
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : 8.131.53.96:3306
 Source Schema         : szh-dahc

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 10/02/2023 16:55:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for a_base
-- ----------------------------
DROP TABLE IF EXISTS `a_base`;
CREATE TABLE `a_base`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of a_base
-- ----------------------------

-- ----------------------------
-- Table structure for dahc_archive_type
-- ----------------------------
DROP TABLE IF EXISTS `dahc_archive_type`;
CREATE TABLE `dahc_archive_type`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `archive_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案类型',
  `table_level1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一级名',
  `table_level1_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一级表数据库表名',
  `table_level2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二级名',
  `table_level2_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二级表数据库表名',
  `archive_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_archive_type
-- ----------------------------
INSERT INTO `dahc_archive_type` VALUES ('1623228019478257665', '一个档案', '一个案卷表', NULL, '一个文件表', NULL, '一个档案', 8080, '2023-02-08 15:51:33', NULL, NULL);
INSERT INTO `dahc_archive_type` VALUES ('1623523372091703297', '文书档案测试3', '文书案卷表1', 'dahc_dt1_yJReLHFQhWGyvnTdATLH', '文书文件表1', 'dahc_dt2_XcLyZatEGFvGQHmpIfRL', '测试流程222', 8080, '2023-02-09 11:25:10', NULL, NULL);
INSERT INTO `dahc_archive_type` VALUES ('1623628517798563842', '<<文书档案>>', '案卷级表', 'dahc_dt1_vTqbQWJNRDVTDrCBzOJQ', '文件级表', 'dahc_dt2_OgCnftSzqBHKdwWImFjc', '2022年新版文书档案', 1, '2023-02-09 18:22:59', NULL, NULL);
INSERT INTO `dahc_archive_type` VALUES ('1623631868007227394', '文书档案测试1', '案卷', 'dahc_dt1_fZdKQMlfxsafcVPdNPAC', '文件', 'dahc_dt2_WuftjkxXsWdSxqOwwgIR', '测试测试', 1, '2023-02-09 18:36:18', NULL, NULL);

-- ----------------------------
-- Table structure for dahc_business_archive_metadata
-- ----------------------------
DROP TABLE IF EXISTS `dahc_business_archive_metadata`;
CREATE TABLE `dahc_business_archive_metadata`  (
  `archive_type_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `archive_type_level` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `attr_order` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `metadata_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_business_archive_metadata
-- ----------------------------
INSERT INTO `dahc_business_archive_metadata` VALUES ('1623228019478257665', '1', '0', '1622831907999223810');
INSERT INTO `dahc_business_archive_metadata` VALUES ('1623228019478257665', '1', '1', '1623230633989300226');
INSERT INTO `dahc_business_archive_metadata` VALUES ('1623228019478257665', '1', '2', '1623528559208542210');
INSERT INTO `dahc_business_archive_metadata` VALUES ('1623523372091703297', '1', '0', '1622831907999223810');
INSERT INTO `dahc_business_archive_metadata` VALUES ('1623523372091703297', '1', '1', '1623230633989300226');
INSERT INTO `dahc_business_archive_metadata` VALUES ('1623628517798563842', '1', '0', '1622831907999223810');
INSERT INTO `dahc_business_archive_metadata` VALUES ('1623631868007227394', '1', '0', '1623528559208542210');
INSERT INTO `dahc_business_archive_metadata` VALUES ('1623228019478257665', '2', '0', '1623230633989300226');

-- ----------------------------
-- Table structure for dahc_business_mapper
-- ----------------------------
DROP TABLE IF EXISTS `dahc_business_mapper`;
CREATE TABLE `dahc_business_mapper`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `mapper_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '映射名',
  `archive_type_id` bigint(0) NULL DEFAULT NULL COMMENT '档案类型id',
  `mapper_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `table_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一级表或二级表',
  `attr_ordinal` bigint(0) NULL DEFAULT NULL COMMENT '表中attr序号',
  `metadata_id` bigint(0) NULL DEFAULT NULL COMMENT '元数据id',
  `mapper_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_business_mapper
-- ----------------------------
INSERT INTO `dahc_business_mapper` VALUES ('1623227700136534017', '案卷号-attr1', 1623523372091703297, '文书常用映射关系', '1', 1, 1623230633989300226, '案卷号', 8080, '2023-02-08 15:50:16', NULL, NULL);
INSERT INTO `dahc_business_mapper` VALUES ('1623227743228813313', '案卷号-attr2', 1623523372091703297, '文书常用映射关系', '1', 2, 1623230633989300226, '案卷号', 8080, '2023-02-08 15:50:27', NULL, NULL);
INSERT INTO `dahc_business_mapper` VALUES ('1623227752930238466', '案卷号-attr3', 1623523372091703297, '文书常用映射关系', '1', 3, 1623230633989300226, '案卷号', 8080, '2023-02-08 15:50:29', NULL, NULL);
INSERT INTO `dahc_business_mapper` VALUES ('1623231026571960322', '案卷号-attr4', 1623523372091703297, '文书常用映射关系', '1', 4, 1623230633989300226, '案卷号', 8080, '2023-02-08 16:03:30', NULL, NULL);

-- ----------------------------
-- Table structure for dahc_business_metadata
-- ----------------------------
DROP TABLE IF EXISTS `dahc_business_metadata`;
CREATE TABLE `dahc_business_metadata`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `metadata_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '元数据名',
  `metadata_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '元数据类型(int|char)',
  `archive_type_id` bigint(0) NULL DEFAULT NULL COMMENT '档案类型id',
  `metadata_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `m_correlation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联字段(待定)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_business_metadata
-- ----------------------------
INSERT INTO `dahc_business_metadata` VALUES ('1622831907999223810', 8080, '2023-02-07 13:37:32', NULL, NULL, '案卷号', 'int', 1623628517798563842, '一个元数据', '');
INSERT INTO `dahc_business_metadata` VALUES ('1623230633989300226', 8080, '2023-02-08 16:01:56', NULL, NULL, '题名', 'char', NULL, '题名', NULL);
INSERT INTO `dahc_business_metadata` VALUES ('1623528559208542210', 8080, '2023-02-09 11:45:47', NULL, NULL, '创建人', 'varchar', NULL, '', '');
INSERT INTO `dahc_business_metadata` VALUES ('1623945381053341698', 8080, '2023-02-10 15:22:05', NULL, NULL, '件号', 'int', NULL, '一个元数据', '');

-- ----------------------------
-- Table structure for dahc_business_model
-- ----------------------------
DROP TABLE IF EXISTS `dahc_business_model`;
CREATE TABLE `dahc_business_model`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名',
  `archive_type_id` bigint(0) NULL DEFAULT NULL COMMENT '档案类型id',
  `model_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_business_model
-- ----------------------------
INSERT INTO `dahc_business_model` VALUES ('1623231707533991938', 8080, '2023-02-08 16:06:12', NULL, NULL, '文书模板', 1623523372091703297, '模板1');

-- ----------------------------
-- Table structure for dahc_business_model_mapper
-- ----------------------------
DROP TABLE IF EXISTS `dahc_business_model_mapper`;
CREATE TABLE `dahc_business_model_mapper`  (
  `model_id` bigint(0) NULL DEFAULT NULL COMMENT '模板id',
  `mapper_id` bigint(0) NULL DEFAULT NULL COMMENT '元数据id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_business_model_mapper
-- ----------------------------
INSERT INTO `dahc_business_model_mapper` VALUES (1623231707533991938, 1623227700136534017);
INSERT INTO `dahc_business_model_mapper` VALUES (1623231707533991938, 1623227743228813313);
INSERT INTO `dahc_business_model_mapper` VALUES (1623231707533991938, 1623227752930238466);
INSERT INTO `dahc_business_model_mapper` VALUES (1623231707533991938, 1623231026571960322);

-- ----------------------------
-- Table structure for dahc_dt1_fzdkqmlfxsafcvpdnpac
-- ----------------------------
DROP TABLE IF EXISTS `dahc_dt1_fzdkqmlfxsafcvpdnpac`;
CREATE TABLE `dahc_dt1_fzdkqmlfxsafcvpdnpac`  (
  `attr0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr0',
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr2',
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr3',
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr4',
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr5',
  `attr6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr6',
  `attr7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr7',
  `attr8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr8',
  `attr9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr9',
  `attr10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr10',
  `attr11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr11',
  `attr12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr12',
  `attr13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr13',
  `attr14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr14',
  `attr15` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr15',
  `attr16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr16',
  `attr17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr17',
  `attr18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr18',
  `attr19` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr19'
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_dt1_fzdkqmlfxsafcvpdnpac
-- ----------------------------

-- ----------------------------
-- Table structure for dahc_dt1_vtqbqwjnrdvtdrcbzojq
-- ----------------------------
DROP TABLE IF EXISTS `dahc_dt1_vtqbqwjnrdvtdrcbzojq`;
CREATE TABLE `dahc_dt1_vtqbqwjnrdvtdrcbzojq`  (
  `attr0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr0',
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr2',
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr3',
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr4',
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr5',
  `attr6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr6',
  `attr7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr7',
  `attr8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr8',
  `attr9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr9',
  `attr10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr10',
  `attr11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr11',
  `attr12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr12',
  `attr13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr13',
  `attr14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr14',
  `attr15` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr15',
  `attr16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr16',
  `attr17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr17',
  `attr18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr18',
  `attr19` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr19'
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_dt1_vtqbqwjnrdvtdrcbzojq
-- ----------------------------

-- ----------------------------
-- Table structure for dahc_dt1_yjrelhfqhwgyvntdatlh
-- ----------------------------
DROP TABLE IF EXISTS `dahc_dt1_yjrelhfqhwgyvntdatlh`;
CREATE TABLE `dahc_dt1_yjrelhfqhwgyvntdatlh`  (
  `attr0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr0',
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr2',
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr3',
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr4',
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr5',
  `attr6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr6',
  `attr7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr7',
  `attr8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr8',
  `attr9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr9',
  `attr10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr10',
  `attr11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr11',
  `attr12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr12',
  `attr13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr13',
  `attr14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr14',
  `attr15` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr15',
  `attr16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr16',
  `attr17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr17',
  `attr18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr18',
  `attr19` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr19'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_dt1_yjrelhfqhwgyvntdatlh
-- ----------------------------
INSERT INTO `dahc_dt1_yjrelhfqhwgyvntdatlh` VALUES ('1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for dahc_dt2_ogcnftszqbhkdwwimfjc
-- ----------------------------
DROP TABLE IF EXISTS `dahc_dt2_ogcnftszqbhkdwwimfjc`;
CREATE TABLE `dahc_dt2_ogcnftszqbhkdwwimfjc`  (
  `attr0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr0',
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr2',
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr3',
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr4',
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr5',
  `attr6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr6',
  `attr7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr7',
  `attr8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr8',
  `attr9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr9',
  `attr10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr10',
  `attr11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr11',
  `attr12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr12',
  `attr13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr13',
  `attr14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr14',
  `attr15` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr15',
  `attr16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr16',
  `attr17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr17',
  `attr18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr18',
  `attr19` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr19'
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_dt2_ogcnftszqbhkdwwimfjc
-- ----------------------------

-- ----------------------------
-- Table structure for dahc_dt2_wuftjkxxswdsxqowwgir
-- ----------------------------
DROP TABLE IF EXISTS `dahc_dt2_wuftjkxxswdsxqowwgir`;
CREATE TABLE `dahc_dt2_wuftjkxxswdsxqowwgir`  (
  `attr0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr0',
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr2',
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr3',
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr4',
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr5',
  `attr6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr6',
  `attr7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr7',
  `attr8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr8',
  `attr9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr9',
  `attr10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr10',
  `attr11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr11',
  `attr12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr12',
  `attr13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr13',
  `attr14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr14',
  `attr15` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr15',
  `attr16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr16',
  `attr17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr17',
  `attr18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr18',
  `attr19` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr19'
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_dt2_wuftjkxxswdsxqowwgir
-- ----------------------------

-- ----------------------------
-- Table structure for dahc_dt2_xclyzategfvgqhmpifrl
-- ----------------------------
DROP TABLE IF EXISTS `dahc_dt2_xclyzategfvgqhmpifrl`;
CREATE TABLE `dahc_dt2_xclyzategfvgqhmpifrl`  (
  `attr0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr0',
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr2',
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr3',
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr4',
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr5',
  `attr6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr6',
  `attr7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr7',
  `attr8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr8',
  `attr9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr9',
  `attr10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr10',
  `attr11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr11',
  `attr12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr12',
  `attr13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr13',
  `attr14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr14',
  `attr15` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr15',
  `attr16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr16',
  `attr17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr17',
  `attr18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr18',
  `attr19` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'attr19'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_dt2_xclyzategfvgqhmpifrl
-- ----------------------------
INSERT INTO `dahc_dt2_xclyzategfvgqhmpifrl` VALUES ('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dahc_dt2_xclyzategfvgqhmpifrl` VALUES ('ad', 'asds', 'dsa', 'dsa', 'sda', 'dsa', 'dsa', 'dsa', 'dsa', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dahc_dt2_xclyzategfvgqhmpifrl` VALUES ('adsa', 'dsa', 'sad', 'sda', 'sda', 'sda', 'dsa', 'dsa', 'dsad', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for dahc_hcx_trueing_management
-- ----------------------------
DROP TABLE IF EXISTS `dahc_hcx_trueing_management`;
CREATE TABLE `dahc_hcx_trueing_management`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `trueing_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '核查项名称',
  `table_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案类型',
  `trueing_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核查项详情',
  `trueing_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核查项备注',
  `trueing_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '判断展示那个页面',
  `show_piece` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否展示件号输入框(0-展示，1-不展示)',
  `show_record` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否展示著录框（0-展示，1-不展示）',
  `trueing_scope_stair` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核查项范围（treeId)',
  `show_page_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否展示页号框（0-展示，1-不展示）',
  `trueing_tree_rank` bigint(0) NULL DEFAULT NULL COMMENT '档案级别（0-一级 1-二级 2-三级）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_hcx_trueing_management
-- ----------------------------
INSERT INTO `dahc_hcx_trueing_management` VALUES ('2952a7851d9d463591e967af6eee3dfb', NULL, '2023-02-10 11:57:44', NULL, NULL, '备考表核查项', '', '', '', '', '0', '0', '12345895213', '1', 2);
INSERT INTO `dahc_hcx_trueing_management` VALUES ('339f871ecc3e4de2b4c8e244637bd05e', NULL, '2023-02-10 13:53:51', NULL, NULL, '备考表2 - 核查项1', '', '', '', '', '1', '0', '9f1f66e02c26434eb6412382915c3e00', '1', 2);
INSERT INTO `dahc_hcx_trueing_management` VALUES ('3516389dffa04927b78b8dbd27996f7a', NULL, '2023-02-09 16:58:02', NULL, NULL, '核查项测试1', '', '', '', '', '0', '0', '123456', '0', 2);
INSERT INTO `dahc_hcx_trueing_management` VALUES ('5de3dc3c6d50406a8f20a3bc009a62ba', NULL, '2023-02-10 16:38:18', NULL, '2023-02-10 16:38:35', '测试', '', '', '', '', '0', '1', '12345895', '0', 2);
INSERT INTO `dahc_hcx_trueing_management` VALUES ('e92eb78f9412442fbc01df9b5f3077a1', NULL, '2023-02-10 16:36:12', NULL, '2023-02-10 16:36:21', '核查项', '', '', '', '', '1', '1', '12345895', '1', 2);
INSERT INTO `dahc_hcx_trueing_management` VALUES ('ef7da8ee1dc94bf2bf691f077fdf7eb8', NULL, '2023-02-10 16:36:41', NULL, NULL, '核查项1', '', '', '', '', '1', '1', '12345895', '1', 2);

-- ----------------------------
-- Table structure for dahc_hcx_trueing_standard
-- ----------------------------
DROP TABLE IF EXISTS `dahc_hcx_trueing_standard`;
CREATE TABLE `dahc_hcx_trueing_standard`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `trueing_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联核查项主键id',
  `trueing_standard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '核查标准',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_hcx_trueing_standard
-- ----------------------------
INSERT INTO `dahc_hcx_trueing_standard` VALUES ('33c0d9f2af2b94a98bfc6188f3867e36', '5de3dc3c6d50406a8f20a3bc009a62ba', '测试');
INSERT INTO `dahc_hcx_trueing_standard` VALUES ('916a72706a6c105c52aaacde8cd65ea7', '5de3dc3c6d50406a8f20a3bc009a62ba', '测试2');
INSERT INTO `dahc_hcx_trueing_standard` VALUES ('c0941178ad3f0dc69ae8b6fd2e1ef69b', 'ef7da8ee1dc94bf2bf691f077fdf7eb8', '测试');
INSERT INTO `dahc_hcx_trueing_standard` VALUES ('c6c9e8b0ac7db037a26f1f9665d30769', 'e92eb78f9412442fbc01df9b5f3077a1', '测试');

-- ----------------------------
-- Table structure for dahc_project_procedure
-- ----------------------------
DROP TABLE IF EXISTS `dahc_project_procedure`;
CREATE TABLE `dahc_project_procedure`  (
  `id` bigint(0) NOT NULL COMMENT '主键id',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `procedure_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工序名',
  `procedure_type` bigint(0) NULL DEFAULT NULL COMMENT '工序类型',
  `trueing_type` bigint(0) NULL DEFAULT NULL COMMENT '核查项页面分类',
  `project_id` bigint(0) NOT NULL COMMENT '项目id',
  `procedure_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_project_procedure
-- ----------------------------
INSERT INTO `dahc_project_procedure` VALUES (1623929397940830210, 8080, '2023-02-10 14:18:34', NULL, NULL, '核查首页', 1, 1, 1, NULL);
INSERT INTO `dahc_project_procedure` VALUES (1623939187798646786, 8080, '2023-02-10 14:57:28', NULL, NULL, '工序1', 1, 1, 1, '工序1');
INSERT INTO `dahc_project_procedure` VALUES (1623954384198246402, 8080, '2023-02-10 15:57:51', NULL, NULL, '工序2', 1, 1, 2, '工序2');

-- ----------------------------
-- Table structure for dahc_project_procedure_trueing
-- ----------------------------
DROP TABLE IF EXISTS `dahc_project_procedure_trueing`;
CREATE TABLE `dahc_project_procedure_trueing`  (
  `procedure_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trueing_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_project_procedure_trueing
-- ----------------------------
INSERT INTO `dahc_project_procedure_trueing` VALUES ('1623929397940830210', '3516389dffa04927b78b8dbd27996f7a');
INSERT INTO `dahc_project_procedure_trueing` VALUES ('1623929397940830210', '2952a7851d9d463591e967af6eee3dfb');
INSERT INTO `dahc_project_procedure_trueing` VALUES ('1623929397940830210', '339f871ecc3e4de2b4c8e244637bd05e');
INSERT INTO `dahc_project_procedure_trueing` VALUES ('1623939187798646786', '1');
INSERT INTO `dahc_project_procedure_trueing` VALUES ('1623939187798646786', '2');
INSERT INTO `dahc_project_procedure_trueing` VALUES ('1623929397940830210', '1');

-- ----------------------------
-- Table structure for dahc_project_table
-- ----------------------------
DROP TABLE IF EXISTS `dahc_project_table`;
CREATE TABLE `dahc_project_table`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `project_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名',
  `project_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目描述',
  `archive_type_id` bigint(0) NULL DEFAULT NULL COMMENT '档案类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_project_table
-- ----------------------------
INSERT INTO `dahc_project_table` VALUES ('1623927316496465922', 8080, '2023-02-10 14:10:18', NULL, NULL, '项目1', '项目1', NULL);
INSERT INTO `dahc_project_table` VALUES ('1623927550458937346', 8080, '2023-02-10 14:11:14', NULL, NULL, '项目2', '项目2', NULL);
INSERT INTO `dahc_project_table` VALUES ('1623930874952400897', 8080, '2023-02-10 14:24:26', NULL, NULL, '文书项目', '文书项目', 1);

-- ----------------------------
-- Table structure for dahc_project_table_procedure
-- ----------------------------
DROP TABLE IF EXISTS `dahc_project_table_procedure`;
CREATE TABLE `dahc_project_table_procedure`  (
  `table_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `procedure_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_project_table_procedure
-- ----------------------------
INSERT INTO `dahc_project_table_procedure` VALUES ('1623927316496465922', '1623929397940830210');
INSERT INTO `dahc_project_table_procedure` VALUES ('1623927316496465922', '1623939187798646786');

-- ----------------------------
-- Table structure for dahc_sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `dahc_sys_dict_data`;
CREATE TABLE `dahc_sys_dict_data`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '代码类型（类型id）',
  `dict_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码',
  `eparent_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '父级代码',
  `full_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码全称',
  `nicker_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码简称',
  `pinyin` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文简称',
  `code_property` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '代码属性',
  `code_attr` bigint(0) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `type` bigint(0) NULL DEFAULT 0 COMMENT '级别0-父节点，1-一级子节点，2-二级子节点）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_sys_dict_data
-- ----------------------------
INSERT INTO `dahc_sys_dict_data` VALUES ('123456', '100', '', '554e69dd41f643aeac2470a4acc513a5', '三级节点-01', NULL, NULL, 'N', 0, '2023-02-09 11:22:34', '', NULL, NULL, 'admin', 2);
INSERT INTO `dahc_sys_dict_data` VALUES ('12345895', '100', '', 'c9649672bb3d4f03a20f99ab46920e5b', '档案盒编辑', NULL, NULL, '', 0, NULL, '', '2023-02-10 14:13:24', NULL, NULL, 2);
INSERT INTO `dahc_sys_dict_data` VALUES ('12345895213', '100', '', 'c9649672bb3d4f03a20f99ab46920e5b', '备考表', NULL, NULL, 'N', 0, NULL, '', NULL, NULL, NULL, 2);
INSERT INTO `dahc_sys_dict_data` VALUES ('40e7e4f0647f47d0a64b6d3a3c9756b1', '100', '', '-', '实体档案', NULL, NULL, '', 0, '2023-02-09 11:13:27', '', '2023-02-09 12:01:12', NULL, 'admin', 0);
INSERT INTO `dahc_sys_dict_data` VALUES ('4fc7358d541c4811a145c5f3d677f25d', '100', '', 'fde450ab2fbb405e9a1dcfb3385b0d0e', '卷内文件著录项', NULL, NULL, '', 0, '2023-02-09 12:02:28', '', NULL, NULL, 'admin', 1);
INSERT INTO `dahc_sys_dict_data` VALUES ('554e69dd41f643aeac2470a4acc513a5', '100', '', 'fde450ab2fbb405e9a1dcfb3385b0d0e', '案卷著录项', '', '', '', 0, '2023-02-08 14:52:26', '', '2023-02-09 12:02:06', '', 'admin', 1);
INSERT INTO `dahc_sys_dict_data` VALUES ('68b9ee906b784fe5859c9cc57958e3df', 'f5ac542edcc3436a8b96f146a9e8e800', '', '-', '测试', NULL, NULL, '', 0, '2023-02-09 10:06:27', '', NULL, NULL, 'admin', 0);
INSERT INTO `dahc_sys_dict_data` VALUES ('75c5655fcd7c419988cacdc9bf4537aa', '100', '', '-', '电子对照实体', NULL, NULL, '', 0, '2023-02-09 12:02:40', '', NULL, NULL, 'admin', 0);
INSERT INTO `dahc_sys_dict_data` VALUES ('98377fae2d5d4f4cb468a279449b6011', '100', '', '282ab6b2380d4c17bd6f679f143a7cbb', '删除三级', NULL, NULL, 'N', 0, '2023-02-10 15:41:51', '', NULL, NULL, 'admin', 2);
INSERT INTO `dahc_sys_dict_data` VALUES ('9f1f66e02c26434eb6412382915c3e00', '100', '', 'c9649672bb3d4f03a20f99ab46920e5b', '备考表2', NULL, NULL, '', 0, '2023-02-10 13:53:22', '', NULL, NULL, 'admin', 2);
INSERT INTO `dahc_sys_dict_data` VALUES ('be6b6926bef6444a906e6c9adce50e1d', '100', '', '5a38fc3f992643debba0ddf515ca66df', '删除三级', NULL, NULL, 'N', 0, '2023-02-10 15:44:48', '', NULL, NULL, 'admin', 2);
INSERT INTO `dahc_sys_dict_data` VALUES ('c9649672bb3d4f03a20f99ab46920e5b', '100', '', '40e7e4f0647f47d0a64b6d3a3c9756b1', '档案盒/备考表', NULL, NULL, '', 0, '2023-02-09 13:45:49', '', NULL, NULL, 'admin', 1);
INSERT INTO `dahc_sys_dict_data` VALUES ('d7405e2a2453411da14b85f57cc8abd3', '100', '', 'c9649672bb3d4f03a20f99ab46920e5b', '编辑测试', NULL, NULL, 'N', 0, '2023-02-10 14:16:47', '', '2023-02-10 14:17:05', NULL, 'admin', 2);
INSERT INTO `dahc_sys_dict_data` VALUES ('fde450ab2fbb405e9a1dcfb3385b0d0e', '100', '', '-', '电子档案', '', '', '', 0, '2023-02-08 14:51:50', '', '2023-02-09 12:01:51', '', 'admin', 0);

-- ----------------------------
-- Table structure for dahc_sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `dahc_sys_dict_type`;
CREATE TABLE `dahc_sys_dict_type`  (
  `dict_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dahc_sys_dict_type
-- ----------------------------
INSERT INTO `dahc_sys_dict_type` VALUES ('100', '核查项-档案类型', '', '0', '', '2023-02-08 14:26:51', 'admin', '2023-02-09 12:00:55', '备注');
INSERT INTO `dahc_sys_dict_type` VALUES ('f5ac542edcc3436a8b96f146a9e8e800', '测试2', '', '0', 'admin', '2023-02-08 19:02:33', '', NULL, NULL);

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (1, 'dahc_archive_type', '', NULL, NULL, 'DahcArchiveType', 'crud', 'com.fudian.system', 'system', 'type', NULL, 'fudian', '0', '/', NULL, 'admin', '2023-02-02 17:24:41', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (2, 'dahc_archive_type', '', NULL, NULL, 'DahcArchiveType', 'crud', 'com.fudian.system', 'system', 'type', NULL, 'fudian', '0', '/', NULL, 'admin', '2023-02-02 17:24:42', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (3, 'dahc_mapper', '', NULL, NULL, 'DahcMapper', 'crud', 'com.fudian.system', 'system', 'mapper', NULL, 'fudian', '0', '/', NULL, 'admin', '2023-02-02 17:25:51', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (4, 'dahc_metadata', '', NULL, NULL, 'DahcMetadata', 'crud', 'com.fudian.system', 'system', 'metadata', NULL, 'fudian', '0', '/', NULL, 'admin', '2023-02-02 17:42:58', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (5, 'dahc_hcx_trueing_manage', '', NULL, NULL, 'DahcHcxTrueingManage', 'crud', 'com.fudian.system', 'system', 'manage', NULL, 'fudian', '0', '/', NULL, 'admin', '2023-02-07 17:55:35', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (6, 'dahc_sys_dict_data', '', NULL, NULL, 'DahcSysDictData', 'crud', 'com.fudian.system', 'system', 'data', NULL, 'fudian', '0', '/', NULL, 'admin', '2023-02-08 12:18:37', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (7, 'dahc_sys_dict_type', '字典类型表', NULL, NULL, 'DahcSysDictType', 'crud', 'com.fudian.system', 'system', 'type', '字典类型', 'fudian', '0', '/', NULL, 'admin', '2023-02-08 12:18:38', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (8, 'dahc_hcx_trueing_management', '', NULL, NULL, 'DahcHcxTrueingManagement', 'crud', 'com.fudian.system', 'system', 'management', NULL, 'fudian', '0', '/', NULL, 'admin', '2023-02-09 12:19:09', '', NULL, NULL);
INSERT INTO `gen_table` VALUES (9, 'dahc_hcx_trueing_standard', '', NULL, NULL, 'DahcHcxTrueingStandard', 'crud', 'com.fudian.system', 'system', 'standard', NULL, 'fudian', '0', '/', NULL, 'admin', '2023-02-09 14:59:14', '', NULL, NULL);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (1, '1', 'id', NULL, 'bigint unsigned', 'String', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', NULL, '', 1, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (2, '1', 'create_by', '创建人', 'bigint', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 2, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (3, '1', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 3, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (4, '1', 'update_by', '修改人', 'bigint', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 4, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (5, '1', 'update_time', '修改时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 5, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (6, '1', 'archive_name', '档案类型', 'varchar(255)', 'String', 'archiveName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (7, '1', 'table_level1', '一级名', 'varchar(255)', 'String', 'tableLevel1', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (8, '1', 'table_level2', '二级名', 'varchar(255)', 'String', 'tableLevel2', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (9, '2', 'id', NULL, 'bigint unsigned', 'String', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', NULL, '', 1, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (10, '2', 'create_by', '创建人', 'bigint', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 2, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (11, '1', 'archive_desc', '描述', 'varchar(255)', 'String', 'archiveDesc', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-02-02 17:24:42', '', NULL);
INSERT INTO `gen_table_column` VALUES (12, '2', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 3, 'admin', '2023-02-02 17:24:43', '', NULL);
INSERT INTO `gen_table_column` VALUES (13, '2', 'update_by', '修改人', 'bigint', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 4, 'admin', '2023-02-02 17:24:43', '', NULL);
INSERT INTO `gen_table_column` VALUES (14, '2', 'update_time', '修改时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 5, 'admin', '2023-02-02 17:24:43', '', NULL);
INSERT INTO `gen_table_column` VALUES (15, '2', 'archive_name', '档案类型', 'varchar(255)', 'String', 'archiveName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2023-02-02 17:24:43', '', NULL);
INSERT INTO `gen_table_column` VALUES (16, '2', 'table_level1', '一级名', 'varchar(255)', 'String', 'tableLevel1', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-02-02 17:24:43', '', NULL);
INSERT INTO `gen_table_column` VALUES (17, '2', 'table_level2', '二级名', 'varchar(255)', 'String', 'tableLevel2', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-02-02 17:24:43', '', NULL);
INSERT INTO `gen_table_column` VALUES (18, '2', 'archive_desc', '描述', 'varchar(255)', 'String', 'archiveDesc', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-02-02 17:24:43', '', NULL);
INSERT INTO `gen_table_column` VALUES (19, '3', 'id', '主键id', 'bigint', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (20, '3', 'create_by', '创建人id', 'bigint', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 2, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (21, '3', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 3, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (22, '3', 'update_by', '修改人id', 'bigint', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 4, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (23, '3', 'update_time', '修改时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 5, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (24, '3', 'archive_type_id', '档案类型id', 'bigint', 'Long', 'archiveTypeId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (25, '3', 'table_level', '一级表或二级表', 'varchar(255)', 'String', 'tableLevel', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (26, '3', 'attr_ordinal', '表中attr序号', 'bigint', 'Long', 'attrOrdinal', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (27, '3', 'metadata_id', '元数据id', 'bigint', 'Long', 'metadataId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (28, '3', 'mapper_desc', '描述', 'varchar(255)', 'String', 'mapperDesc', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2023-02-02 17:25:51', '', NULL);
INSERT INTO `gen_table_column` VALUES (29, '4', 'id', '主键id', 'bigint', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (30, '4', 'create_by', '创建人id', 'bigint', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 2, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (31, '4', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 3, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (32, '4', 'update_by', '修改人id', 'bigint', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 4, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (33, '4', 'update_time', '修改时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 5, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (34, '4', 'metadata_name', '元数据名', 'varchar(255)', 'String', 'metadataName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (35, '4', 'metadata_type', '元数据类型(int|char)', 'varchar(255)', 'String', 'metadataType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 7, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (36, '4', 'archive_type_id', '档案类型id', 'bigint', 'Long', 'archiveTypeId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (37, '4', 'metadata_desc', '描述', 'varchar(255)', 'String', 'metadataDesc', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (38, '4', 'm_correlation', '关联字段(待定)', 'varchar(255)', 'String', 'mCorrelation', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2023-02-02 17:42:59', '', NULL);
INSERT INTO `gen_table_column` VALUES (39, '5', 'id', '主键id', 'bigint', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (40, '5', 'create_by', '创建人id', 'bigint', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 2, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (41, '5', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 3, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (42, '5', 'update_by', '修改人id', 'bigint', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 4, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (43, '5', 'update_time', '修改时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 5, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (44, '5', 'trueing_name', '核查项名称', 'varchar(255)', 'String', 'trueingName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (45, '5', 'table_type', '档案类型', 'varchar(255)', 'String', 'tableType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 7, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (46, '5', 'trueing_desc', '核查项详情', 'varchar(255)', 'String', 'trueingDesc', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (47, '5', 'trueing_remark', '核查项备注', 'varchar(255)', 'String', 'trueingRemark', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (48, '5', 'trueing_type', '判断展示那个页面', 'varchar(255)', 'String', 'trueingType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 10, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (49, '5', 'show_piece', '是否展示件号输入框(0-展示，1-不展示)', 'varchar(255)', 'String', 'showPiece', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (50, '5', 'show_record', '是否展示著录框（0-展示，1-不展示）', 'varchar(255)', 'String', 'showRecord', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (51, '5', 'trueing_scope_stair', '核查项范围（treeId)', 'varchar(255)', 'String', 'trueingScopeStair', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (52, '5', 'show_page_number', '是否展示页号框（0-展示，1-不展示）', 'varchar(255)', 'String', 'showPageNumber', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2023-02-07 17:55:35', '', '2023-02-09 12:05:45');
INSERT INTO `gen_table_column` VALUES (55, '6', 'id', '主键', 'varchar(100)', 'String', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (56, '6', 'dict_type', '代码类型', 'varchar(100)', 'String', 'dictType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 2, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (57, '6', 'dict_code', '代码', 'varchar(100)', 'String', 'dictCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (58, '6', 'eparent_code', '父级代码', 'varchar(100)', 'String', 'eparentCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (59, '6', 'full_name', '代码全称', 'varchar(100)', 'String', 'fullName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 5, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (60, '6', 'nicker_name', '代码简称', 'varchar(100)', 'String', 'nickerName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (61, '6', 'pinyin', '英文简称', 'varchar(100)', 'String', 'pinyin', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (62, '6', 'code_property', '代码属性', 'varchar(100)', 'String', 'codeProperty', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (63, '6', 'code_attr', '状态（0正常 1停用）', 'bigint', 'Long', 'codeAttr', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (64, '6', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 10, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (65, '6', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 11, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (66, '6', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 12, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (67, '6', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 13, 'admin', '2023-02-08 12:18:37', '', NULL);
INSERT INTO `gen_table_column` VALUES (68, '6', 'create_by', '创建人', 'varchar(40)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 14, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (69, '6', 'type', '级别M-父节点，C-一级子节点，B-二级子节点）', 'bigint', 'Long', 'type', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 15, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (70, '7', 'dict_id', '字典主键', 'bigint', 'Long', 'dictId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (71, '7', 'dict_name', '字典名称', 'varchar(100)', 'String', 'dictName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (72, '7', 'dict_type', '字典类型', 'varchar(100)', 'String', 'dictType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 3, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (73, '7', 'status', '状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 4, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (74, '7', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 5, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (75, '7', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 6, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (76, '7', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 7, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (77, '7', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 8, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (78, '7', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 9, 'admin', '2023-02-08 12:18:38', '', NULL);
INSERT INTO `gen_table_column` VALUES (79, '5', 'trueing_tree_rank', '档案级别（0-一级 1-二级 2-三级）', 'bigint', 'Long', 'trueingTreeRank', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 15, '', '2023-02-09 12:05:45', '', NULL);
INSERT INTO `gen_table_column` VALUES (80, '8', 'id', '主键id', 'bigint', 'Long', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (81, '8', 'create_by', '创建人id', 'bigint', 'Long', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 2, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (82, '8', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 3, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (83, '8', 'update_by', '修改人id', 'bigint', 'Long', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 4, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (84, '8', 'update_time', '修改时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 5, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (85, '8', 'trueing_name', '核查项名称', 'varchar(255)', 'String', 'trueingName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 6, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (86, '8', 'table_type', '档案类型', 'varchar(255)', 'String', 'tableType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 7, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (87, '8', 'trueing_desc', '核查项详情', 'varchar(255)', 'String', 'trueingDesc', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (88, '8', 'trueing_remark', '核查项备注', 'varchar(255)', 'String', 'trueingRemark', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (89, '8', 'trueing_type', '判断展示那个页面', 'varchar(255)', 'String', 'trueingType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 10, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (90, '8', 'show_piece', '是否展示件号输入框(0-展示，1-不展示)', 'varchar(255)', 'String', 'showPiece', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (91, '8', 'show_record', '是否展示著录框（0-展示，1-不展示）', 'varchar(255)', 'String', 'showRecord', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (92, '8', 'trueing_scope_stair', '核查项范围（treeId)', 'varchar(255)', 'String', 'trueingScopeStair', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (93, '8', 'show_page_number', '是否展示页号框（0-展示，1-不展示）', 'varchar(255)', 'String', 'showPageNumber', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (94, '8', 'trueing_tree_rank', '档案级别（0-一级 1-二级 2-三级）', 'bigint', 'Long', 'trueingTreeRank', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 15, 'admin', '2023-02-09 12:19:09', '', NULL);
INSERT INTO `gen_table_column` VALUES (95, '9', 'id', '主键', 'varchar(40)', 'String', 'id', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-02-09 14:59:14', '', NULL);
INSERT INTO `gen_table_column` VALUES (96, '9', 'trueing_id', '关联核查项主键id', 'varchar(40)', 'String', 'trueingId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2023-02-09 14:59:14', '', NULL);
INSERT INTO `gen_table_column` VALUES (97, '9', 'trueing_standard', '核查标准', 'varchar(255)', 'String', 'trueingStandard', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-02-09 14:59:14', '', NULL);

-- ----------------------------
-- Table structure for pm_afa
-- ----------------------------
DROP TABLE IF EXISTS `pm_afa`;
CREATE TABLE `pm_afa`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pm_afa
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob NULL COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Blob类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日历信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Cron类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(0) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(0) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(0) NOT NULL COMMENT '优先级',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '已触发的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储的悲观锁信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '暂停的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(0) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(0) NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度器状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(0) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(0) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(0) NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简单触发器的信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(0) NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(0) NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(0) NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(0) NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '同步机制的行锁表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(0) NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(0) NULL DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(0) NULL DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(0) NOT NULL COMMENT '开始时间',
  `end_time` bigint(0) NULL DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(0) NULL DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2023-01-10 10:09:07', '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2023-01-10 10:09:07', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2023-01-10 10:09:07', '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'false', 'Y', 'admin', '2023-01-10 10:09:07', 'admin', '2023-02-06 13:48:24', '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'true', 'Y', 'admin', '2023-01-10 10:09:07', 'admin', '2023-01-31 17:51:41', '是否开启注册用户功能（true开启，false关闭）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '若依科技', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-01-10 10:08:49', '', NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(0) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2023-01-10 10:09:05', '', NULL, '停用状态');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2023-01-10 10:09:03', '', NULL, '登录状态列表');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2023-01-10 10:09:09', '', NULL, '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2023-01-10 10:09:09', '', NULL, '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2023-01-10 10:09:09', '', NULL, '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 318 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (100, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-10 10:34:20');
INSERT INTO `sys_logininfor` VALUES (101, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-13 18:15:50');
INSERT INTO `sys_logininfor` VALUES (102, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-29 16:48:21');
INSERT INTO `sys_logininfor` VALUES (103, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-29 16:48:37');
INSERT INTO `sys_logininfor` VALUES (104, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-30 11:46:18');
INSERT INTO `sys_logininfor` VALUES (105, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-30 15:12:14');
INSERT INTO `sys_logininfor` VALUES (106, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-31 15:50:46');
INSERT INTO `sys_logininfor` VALUES (107, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-01-31 15:52:34');
INSERT INTO `sys_logininfor` VALUES (108, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-31 15:52:39');
INSERT INTO `sys_logininfor` VALUES (109, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-31 16:17:57');
INSERT INTO `sys_logininfor` VALUES (110, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-31 17:18:07');
INSERT INTO `sys_logininfor` VALUES (111, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-31 17:43:46');
INSERT INTO `sys_logininfor` VALUES (112, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-01-31 17:51:48');
INSERT INTO `sys_logininfor` VALUES (113, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-31 17:51:51');
INSERT INTO `sys_logininfor` VALUES (114, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-01-31 17:54:32');
INSERT INTO `sys_logininfor` VALUES (115, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-01 15:33:56');
INSERT INTO `sys_logininfor` VALUES (116, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-02-01 15:50:54');
INSERT INTO `sys_logininfor` VALUES (117, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-01 15:50:56');
INSERT INTO `sys_logininfor` VALUES (118, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-02-01 15:51:04');
INSERT INTO `sys_logininfor` VALUES (119, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-01 15:54:36');
INSERT INTO `sys_logininfor` VALUES (120, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-01 15:56:23');
INSERT INTO `sys_logininfor` VALUES (121, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-02-01 15:57:29');
INSERT INTO `sys_logininfor` VALUES (122, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '密码输入错误1次', '2023-02-01 15:57:34');
INSERT INTO `sys_logininfor` VALUES (123, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-01 15:57:34');
INSERT INTO `sys_logininfor` VALUES (124, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '密码输入错误2次', '2023-02-01 15:57:38');
INSERT INTO `sys_logininfor` VALUES (125, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-01 15:57:38');
INSERT INTO `sys_logininfor` VALUES (126, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-01 15:59:42');
INSERT INTO `sys_logininfor` VALUES (127, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-02-01 16:03:19');
INSERT INTO `sys_logininfor` VALUES (128, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-01 16:03:26');
INSERT INTO `sys_logininfor` VALUES (129, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-01 16:16:53');
INSERT INTO `sys_logininfor` VALUES (130, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-01 17:48:20');
INSERT INTO `sys_logininfor` VALUES (131, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-02 12:17:27');
INSERT INTO `sys_logininfor` VALUES (132, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-02 13:50:10');
INSERT INTO `sys_logininfor` VALUES (133, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-02-02 13:56:04');
INSERT INTO `sys_logininfor` VALUES (134, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-02 14:17:10');
INSERT INTO `sys_logininfor` VALUES (135, 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2023-02-02 16:36:51');
INSERT INTO `sys_logininfor` VALUES (136, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '密码输入错误1次', '2023-02-02 16:48:53');
INSERT INTO `sys_logininfor` VALUES (137, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-02 16:48:53');
INSERT INTO `sys_logininfor` VALUES (138, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-02 16:49:01');
INSERT INTO `sys_logininfor` VALUES (139, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2023-02-02 16:56:15');
INSERT INTO `sys_logininfor` VALUES (140, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '密码输入错误1次', '2023-02-02 16:56:26');
INSERT INTO `sys_logininfor` VALUES (141, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-02 16:56:26');
INSERT INTO `sys_logininfor` VALUES (142, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-02 16:56:37');
INSERT INTO `sys_logininfor` VALUES (143, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '密码输入错误2次', '2023-02-02 16:56:37');
INSERT INTO `sys_logininfor` VALUES (144, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-02 16:56:42');
INSERT INTO `sys_logininfor` VALUES (145, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-02 17:23:28');
INSERT INTO `sys_logininfor` VALUES (146, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 11:11:32');
INSERT INTO `sys_logininfor` VALUES (147, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 11:24:57');
INSERT INTO `sys_logininfor` VALUES (148, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 12:19:08');
INSERT INTO `sys_logininfor` VALUES (149, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 13:32:43');
INSERT INTO `sys_logininfor` VALUES (150, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-02-06 13:37:59');
INSERT INTO `sys_logininfor` VALUES (151, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 13:38:06');
INSERT INTO `sys_logininfor` VALUES (152, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 13:48:07');
INSERT INTO `sys_logininfor` VALUES (153, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 13:48:44');
INSERT INTO `sys_logininfor` VALUES (154, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 14:48:09');
INSERT INTO `sys_logininfor` VALUES (155, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 15:00:28');
INSERT INTO `sys_logininfor` VALUES (156, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 16:23:41');
INSERT INTO `sys_logininfor` VALUES (157, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 17:42:05');
INSERT INTO `sys_logininfor` VALUES (158, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 17:42:56');
INSERT INTO `sys_logininfor` VALUES (159, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-06 18:01:00');
INSERT INTO `sys_logininfor` VALUES (160, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-06 18:17:20');
INSERT INTO `sys_logininfor` VALUES (161, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-06 18:20:19');
INSERT INTO `sys_logininfor` VALUES (162, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 09:37:06');
INSERT INTO `sys_logininfor` VALUES (163, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 10:35:57');
INSERT INTO `sys_logininfor` VALUES (164, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 10:38:47');
INSERT INTO `sys_logininfor` VALUES (165, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 10:41:45');
INSERT INTO `sys_logininfor` VALUES (166, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 10:44:42');
INSERT INTO `sys_logininfor` VALUES (167, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 10:50:48');
INSERT INTO `sys_logininfor` VALUES (168, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 10:52:39');
INSERT INTO `sys_logininfor` VALUES (169, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 10:59:00');
INSERT INTO `sys_logininfor` VALUES (170, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:04:47');
INSERT INTO `sys_logininfor` VALUES (171, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', 'nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named \'dept\' in \'class com.fudian.common.core.domain.entity.SysUser\'', '2023-02-07 11:08:39');
INSERT INTO `sys_logininfor` VALUES (172, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', 'nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named \'dept\' in \'class com.fudian.common.core.domain.entity.SysUser\'', '2023-02-07 11:08:41');
INSERT INTO `sys_logininfor` VALUES (173, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', 'nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named \'dept\' in \'class com.fudian.common.core.domain.entity.SysUser\'', '2023-02-07 11:08:46');
INSERT INTO `sys_logininfor` VALUES (174, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:09:42');
INSERT INTO `sys_logininfor` VALUES (175, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:20:26');
INSERT INTO `sys_logininfor` VALUES (176, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:24:53');
INSERT INTO `sys_logininfor` VALUES (177, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:29:09');
INSERT INTO `sys_logininfor` VALUES (178, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:31:01');
INSERT INTO `sys_logininfor` VALUES (179, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 11:33:57');
INSERT INTO `sys_logininfor` VALUES (180, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:34:59');
INSERT INTO `sys_logininfor` VALUES (181, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:45:09');
INSERT INTO `sys_logininfor` VALUES (182, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:46:34');
INSERT INTO `sys_logininfor` VALUES (183, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Mac OS X', '0', '登录成功', '2023-02-07 11:46:48');
INSERT INTO `sys_logininfor` VALUES (184, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:49:14');
INSERT INTO `sys_logininfor` VALUES (185, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 11:50:40');
INSERT INTO `sys_logininfor` VALUES (186, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 12:04:17');
INSERT INTO `sys_logininfor` VALUES (187, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 12:15:24');
INSERT INTO `sys_logininfor` VALUES (188, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 12:17:39');
INSERT INTO `sys_logininfor` VALUES (189, 'admin', '192.168.0.25', '内网IP', 'Chrome 10', 'Mac OS X', '0', '登录成功', '2023-02-07 12:18:43');
INSERT INTO `sys_logininfor` VALUES (190, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 13:43:17');
INSERT INTO `sys_logininfor` VALUES (191, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 14:03:27');
INSERT INTO `sys_logininfor` VALUES (192, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 14:11:04');
INSERT INTO `sys_logininfor` VALUES (193, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 14:27:21');
INSERT INTO `sys_logininfor` VALUES (194, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 14:28:06');
INSERT INTO `sys_logininfor` VALUES (195, 'admin123', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '1', '登录用户：admin123 不存在', '2023-02-07 14:33:42');
INSERT INTO `sys_logininfor` VALUES (196, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-07 14:33:54');
INSERT INTO `sys_logininfor` VALUES (197, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '1', '密码输入错误1次', '2023-02-07 14:33:54');
INSERT INTO `sys_logininfor` VALUES (198, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 14:36:39');
INSERT INTO `sys_logininfor` VALUES (199, 'ry', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 15:01:34');
INSERT INTO `sys_logininfor` VALUES (200, 'ry', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-02-07 15:02:05');
INSERT INTO `sys_logininfor` VALUES (201, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 15:02:09');
INSERT INTO `sys_logininfor` VALUES (202, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 16:31:48');
INSERT INTO `sys_logininfor` VALUES (203, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 16:35:06');
INSERT INTO `sys_logininfor` VALUES (204, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 16:36:49');
INSERT INTO `sys_logininfor` VALUES (205, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 16:37:59');
INSERT INTO `sys_logininfor` VALUES (206, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2023-02-07 16:40:19');
INSERT INTO `sys_logininfor` VALUES (207, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 16:40:22');
INSERT INTO `sys_logininfor` VALUES (208, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2023-02-07 16:40:29');
INSERT INTO `sys_logininfor` VALUES (209, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-07 16:40:35');
INSERT INTO `sys_logininfor` VALUES (210, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '密码输入错误1次', '2023-02-07 16:40:35');
INSERT INTO `sys_logininfor` VALUES (211, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '密码输入错误2次', '2023-02-07 16:40:38');
INSERT INTO `sys_logininfor` VALUES (212, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-07 16:40:38');
INSERT INTO `sys_logininfor` VALUES (213, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '密码输入错误3次', '2023-02-07 16:40:42');
INSERT INTO `sys_logininfor` VALUES (214, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-07 16:40:42');
INSERT INTO `sys_logininfor` VALUES (215, 'mcy', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '登录用户：mcy 不存在', '2023-02-07 16:40:46');
INSERT INTO `sys_logininfor` VALUES (216, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-07 16:40:52');
INSERT INTO `sys_logininfor` VALUES (217, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '密码输入错误1次', '2023-02-07 16:40:52');
INSERT INTO `sys_logininfor` VALUES (218, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 16:40:54');
INSERT INTO `sys_logininfor` VALUES (219, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2023-02-07 16:41:23');
INSERT INTO `sys_logininfor` VALUES (220, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 16:41:28');
INSERT INTO `sys_logininfor` VALUES (221, 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2023-02-07 16:41:45');
INSERT INTO `sys_logininfor` VALUES (222, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:05:15');
INSERT INTO `sys_logininfor` VALUES (223, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:24:05');
INSERT INTO `sys_logininfor` VALUES (224, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:31:30');
INSERT INTO `sys_logininfor` VALUES (225, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:39:47');
INSERT INTO `sys_logininfor` VALUES (226, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:42:16');
INSERT INTO `sys_logininfor` VALUES (227, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:46:05');
INSERT INTO `sys_logininfor` VALUES (228, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:48:28');
INSERT INTO `sys_logininfor` VALUES (229, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 17:54:17');
INSERT INTO `sys_logininfor` VALUES (230, '马超宇', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2023-02-07 17:54:31');
INSERT INTO `sys_logininfor` VALUES (231, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '密码输入错误1次', '2023-02-07 17:54:36');
INSERT INTO `sys_logininfor` VALUES (232, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '1', '用户不存在/密码错误', '2023-02-07 17:54:36');
INSERT INTO `sys_logininfor` VALUES (233, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 17:54:40');
INSERT INTO `sys_logininfor` VALUES (234, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:55:44');
INSERT INTO `sys_logininfor` VALUES (235, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:55:55');
INSERT INTO `sys_logininfor` VALUES (236, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 17:58:22');
INSERT INTO `sys_logininfor` VALUES (237, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 18:12:51');
INSERT INTO `sys_logininfor` VALUES (238, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 18:13:01');
INSERT INTO `sys_logininfor` VALUES (239, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '退出成功', '2023-02-07 18:13:14');
INSERT INTO `sys_logininfor` VALUES (240, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-07 18:13:26');
INSERT INTO `sys_logininfor` VALUES (241, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 18:20:27');
INSERT INTO `sys_logininfor` VALUES (242, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2023-02-07 18:24:12');
INSERT INTO `sys_logininfor` VALUES (243, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 18:24:15');
INSERT INTO `sys_logininfor` VALUES (244, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-07 18:26:54');
INSERT INTO `sys_logininfor` VALUES (245, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 09:47:04');
INSERT INTO `sys_logininfor` VALUES (246, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 09:54:15');
INSERT INTO `sys_logininfor` VALUES (247, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 10:03:43');
INSERT INTO `sys_logininfor` VALUES (248, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 10:06:04');
INSERT INTO `sys_logininfor` VALUES (249, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 10:08:11');
INSERT INTO `sys_logininfor` VALUES (250, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-08 10:30:55');
INSERT INTO `sys_logininfor` VALUES (251, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 10:36:18');
INSERT INTO `sys_logininfor` VALUES (252, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-08 12:12:09');
INSERT INTO `sys_logininfor` VALUES (253, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 14:13:58');
INSERT INTO `sys_logininfor` VALUES (254, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2023-02-08 14:22:50');
INSERT INTO `sys_logininfor` VALUES (255, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 14:22:53');
INSERT INTO `sys_logininfor` VALUES (256, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 14:26:14');
INSERT INTO `sys_logininfor` VALUES (257, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2023-02-08 14:26:18');
INSERT INTO `sys_logininfor` VALUES (258, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 14:26:20');
INSERT INTO `sys_logininfor` VALUES (259, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 14:47:42');
INSERT INTO `sys_logininfor` VALUES (260, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 14:56:50');
INSERT INTO `sys_logininfor` VALUES (261, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 15:18:59');
INSERT INTO `sys_logininfor` VALUES (262, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-08 15:39:15');
INSERT INTO `sys_logininfor` VALUES (263, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 15:48:33');
INSERT INTO `sys_logininfor` VALUES (264, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 15:51:38');
INSERT INTO `sys_logininfor` VALUES (265, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 15:53:11');
INSERT INTO `sys_logininfor` VALUES (266, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-08 16:10:58');
INSERT INTO `sys_logininfor` VALUES (267, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-08 16:24:06');
INSERT INTO `sys_logininfor` VALUES (268, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 16:29:15');
INSERT INTO `sys_logininfor` VALUES (269, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 16:31:39');
INSERT INTO `sys_logininfor` VALUES (270, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 16:41:32');
INSERT INTO `sys_logininfor` VALUES (271, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 17:20:12');
INSERT INTO `sys_logininfor` VALUES (272, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 17:21:57');
INSERT INTO `sys_logininfor` VALUES (273, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 17:25:00');
INSERT INTO `sys_logininfor` VALUES (274, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 17:26:47');
INSERT INTO `sys_logininfor` VALUES (275, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Mac OS X', '0', '登录成功', '2023-02-08 18:14:08');
INSERT INTO `sys_logininfor` VALUES (276, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 18:30:49');
INSERT INTO `sys_logininfor` VALUES (277, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-08 18:57:28');
INSERT INTO `sys_logininfor` VALUES (278, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 09:51:12');
INSERT INTO `sys_logininfor` VALUES (279, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 10:01:42');
INSERT INTO `sys_logininfor` VALUES (280, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 10:06:05');
INSERT INTO `sys_logininfor` VALUES (281, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 10:24:03');
INSERT INTO `sys_logininfor` VALUES (282, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 10:25:14');
INSERT INTO `sys_logininfor` VALUES (283, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 11:09:01');
INSERT INTO `sys_logininfor` VALUES (284, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 11:17:34');
INSERT INTO `sys_logininfor` VALUES (285, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 11:24:21');
INSERT INTO `sys_logininfor` VALUES (286, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 11:25:23');
INSERT INTO `sys_logininfor` VALUES (287, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 12:00:23');
INSERT INTO `sys_logininfor` VALUES (288, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 14:13:52');
INSERT INTO `sys_logininfor` VALUES (289, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 14:49:41');
INSERT INTO `sys_logininfor` VALUES (290, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 14:51:59');
INSERT INTO `sys_logininfor` VALUES (291, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 15:07:29');
INSERT INTO `sys_logininfor` VALUES (292, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 15:08:57');
INSERT INTO `sys_logininfor` VALUES (293, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 15:29:31');
INSERT INTO `sys_logininfor` VALUES (294, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 15:38:51');
INSERT INTO `sys_logininfor` VALUES (295, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 15:44:03');
INSERT INTO `sys_logininfor` VALUES (296, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-09 16:13:24');
INSERT INTO `sys_logininfor` VALUES (297, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 16:52:25');
INSERT INTO `sys_logininfor` VALUES (298, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 16:53:29');
INSERT INTO `sys_logininfor` VALUES (299, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 16:54:20');
INSERT INTO `sys_logininfor` VALUES (300, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-09 17:41:00');
INSERT INTO `sys_logininfor` VALUES (301, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-09 17:58:57');
INSERT INTO `sys_logininfor` VALUES (302, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 18:03:47');
INSERT INTO `sys_logininfor` VALUES (303, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-09 18:29:27');
INSERT INTO `sys_logininfor` VALUES (304, 'admin', '127.0.0.1', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-09 18:34:20');
INSERT INTO `sys_logininfor` VALUES (305, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-09 18:45:20');
INSERT INTO `sys_logininfor` VALUES (306, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 09:36:38');
INSERT INTO `sys_logininfor` VALUES (307, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 09:59:59');
INSERT INTO `sys_logininfor` VALUES (308, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 10:01:38');
INSERT INTO `sys_logininfor` VALUES (309, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 10:03:40');
INSERT INTO `sys_logininfor` VALUES (310, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 10:04:10');
INSERT INTO `sys_logininfor` VALUES (311, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 10:05:07');
INSERT INTO `sys_logininfor` VALUES (312, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 10:08:06');
INSERT INTO `sys_logininfor` VALUES (313, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 10:20:45');
INSERT INTO `sys_logininfor` VALUES (314, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 10:31:12');
INSERT INTO `sys_logininfor` VALUES (315, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 11:02:45');
INSERT INTO `sys_logininfor` VALUES (316, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 11:46:03');
INSERT INTO `sys_logininfor` VALUES (317, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 11:51:18');
INSERT INTO `sys_logininfor` VALUES (318, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 11:56:19');
INSERT INTO `sys_logininfor` VALUES (319, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 14:10:36');
INSERT INTO `sys_logininfor` VALUES (320, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 14:56:28');
INSERT INTO `sys_logininfor` VALUES (321, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 15:05:25');
INSERT INTO `sys_logininfor` VALUES (322, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 15:29:15');
INSERT INTO `sys_logininfor` VALUES (323, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 15:34:33');
INSERT INTO `sys_logininfor` VALUES (324, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 15:39:47');
INSERT INTO `sys_logininfor` VALUES (325, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 16:08:28');
INSERT INTO `sys_logininfor` VALUES (326, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 16:26:10');
INSERT INTO `sys_logininfor` VALUES (327, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 16:32:20');
INSERT INTO `sys_logininfor` VALUES (328, 'admin', '192.168.0.18', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2023-02-10 16:33:37');
INSERT INTO `sys_logininfor` VALUES (329, 'admin', '192.168.0.10', '内网IP', 'Chrome 10', 'Windows 10', '0', '登录成功', '2023-02-10 16:51:51');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(0) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(0) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2015 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 4, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2023-01-11 16:53:30', 'admin', '2023-01-18 10:17:46', '系统管理目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2023-01-11 16:53:30', 'admin', '2023-01-19 17:45:57', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2023-01-11 16:53:30', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2023-01-11 16:53:30', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2023-01-11 16:53:30', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2023-01-11 16:53:30', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2023-01-11 16:53:30', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 1, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2023-02-02 17:08:52', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2023-01-11 16:53:30', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2023-01-11 16:53:30', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2023-01-11 16:53:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '首页', 0, 1, 'index', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'dashboard', 'admin', '2023-01-12 10:03:55', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2001, '业务管理', 0, 2, 'business', NULL, NULL, 1, 0, 'M', '0', '0', '', 'component', 'admin', '2023-01-12 17:43:43', 'admin', '2023-01-18 10:16:41', '');
INSERT INTO `sys_menu` VALUES (2002, '核查项管理', 2001, 1, 'examine', 'business/examine/index', NULL, 1, 0, 'C', '0', '0', '', 'documentation', 'admin', '2023-01-12 17:45:20', 'admin', '2023-01-12 17:45:59', '');
INSERT INTO `sys_menu` VALUES (2003, '档案类型管理', 2001, 2, 'archivesType', 'business/archivesType/index', NULL, 1, 0, 'C', '0', '0', '', 'education', 'admin', '2023-01-16 14:14:17', 'admin', '2023-01-16 14:14:40', '');
INSERT INTO `sys_menu` VALUES (2004, '元数据管理', 2001, 3, 'metaData', 'business/metaData/index', NULL, 1, 0, 'C', '0', '0', '', 'druid', 'admin', '2023-01-16 18:22:22', 'admin', '2023-01-17 10:07:15', '');
INSERT INTO `sys_menu` VALUES (2005, '映射管理', 2001, 4, 'mapper', 'business/mapper/index', NULL, 1, 0, 'C', '0', '0', '', 'dict', 'admin', '2023-01-16 18:26:26', 'admin', '2023-01-16 18:26:41', '');
INSERT INTO `sys_menu` VALUES (2006, '模版管理', 2001, 5, 'archivesTemplate', 'business/archivesTemplate/index', NULL, 1, 0, 'C', '0', '0', '', 'dict', 'admin', '2023-01-17 17:11:14', 'admin', '2023-01-17 17:11:25', '');
INSERT INTO `sys_menu` VALUES (2007, '项目管理', 0, 3, 'projectManage', NULL, NULL, 1, 0, 'M', '0', '0', '', 'documentation', 'admin', '2023-01-18 10:11:59', 'admin', '2023-01-18 10:17:50', '');
INSERT INTO `sys_menu` VALUES (2008, '项目初始化', 2007, 1, 'projectInitialize', 'projectManage/projectInitialize/index', NULL, 1, 0, 'C', '0', '0', '', 'example', 'admin', '2023-01-18 10:15:20', 'admin', '2023-01-18 10:16:14', '');
INSERT INTO `sys_menu` VALUES (2009, '定义工序', 2007, 2, 'definingProcess', 'projectManage/definingProcess/index', NULL, 1, 0, 'C', '0', '0', '', 'clipboard', 'admin', '2023-01-18 12:15:24', 'admin', '2023-01-18 12:15:51', '');
INSERT INTO `sys_menu` VALUES (2010, '原用户管理页面', 1, 11, 'user2', 'system/user/index2', NULL, 1, 0, 'C', '0', '0', '', 'example', 'admin', '2023-01-19 10:21:40', 'admin', '2023-01-29 10:03:19', '');
INSERT INTO `sys_menu` VALUES (2011, '原角色管理页面', 1, 12, 'role2', 'system/role/index2', NULL, 1, 0, 'C', '0', '0', '', 'example', 'admin', '2023-01-19 17:47:49', 'admin', '2023-01-29 11:48:38', '');
INSERT INTO `sys_menu` VALUES (2012, '档案核查', 0, 5, 'fileCheck', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'druid', 'admin', '2023-02-09 14:53:13', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2013, '实体核查', 2012, 0, 'physicalFile', 'fileCheck/physicalFile/index', NULL, 1, 0, 'C', '0', '0', '', 'chart', 'admin', '2023-02-09 14:56:16', 'admin', '2023-02-10 12:18:01', '');
INSERT INTO `sys_menu` VALUES (2014, '电子核查-模版1', 2012, 1, 'electronicTemplateFirst', 'fileCheck/electronicFile/templateFirst/index', NULL, 1, 0, 'C', '0', '0', '', 'code', 'admin', '2023-02-10 12:20:53', 'admin', '2023-02-10 12:22:41', '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2023-01-10 10:09:10', '', NULL, '管理员');
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2023-01-10 10:09:10', '', NULL, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(0) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(0) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(0) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 346 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (100, '参数管理', 9, 'com.fudian.web.controller.system.SysConfigController.refreshCache()', 'DELETE', 1, 'admin', NULL, '/system/config/refreshCache', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-01-31 17:51:08');
INSERT INTO `sys_oper_log` VALUES (101, '参数管理', 2, 'com.fudian.web.controller.system.SysConfigController.edit()', 'PUT', 1, 'admin', NULL, '/system/config', '127.0.0.1', '内网IP', '{\"configId\":4,\"configKey\":\"sys.account.captchaEnabled\",\"configName\":\"账号自助-验证码开关\",\"configType\":\"Y\",\"configValue\":\"false\",\"createBy\":\"admin\",\"createTime\":\"2023-01-10 10:09:07\",\"params\":{},\"remark\":\"是否开启验证码功能（true开启，false关闭）\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-01-31 17:51:31');
INSERT INTO `sys_oper_log` VALUES (102, '参数管理', 2, 'com.fudian.web.controller.system.SysConfigController.edit()', 'PUT', 1, 'admin', NULL, '/system/config', '127.0.0.1', '内网IP', '{\"configId\":5,\"configKey\":\"sys.account.registerUser\",\"configName\":\"账号自助-是否开启用户注册功能\",\"configType\":\"Y\",\"configValue\":\"true\",\"createBy\":\"admin\",\"createTime\":\"2023-01-10 10:09:07\",\"params\":{},\"remark\":\"是否开启注册用户功能（true开启，false关闭）\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-01-31 17:51:41');
INSERT INTO `sys_oper_log` VALUES (103, '字典类型', 9, 'com.fudian.web.controller.system.SysDictTypeController.refreshCache()', 'DELETE', 1, 'admin', NULL, '/system/dict/type/refreshCache', '127.0.0.1', '内网IP', '{}', NULL, 1, 'Error in execution; nested exception is io.lettuce.core.RedisCommandExecutionException: MISCONF Redis is configured to save RDB snapshots, but is currently not able to persist on disk. Commands that may modify the data set are disabled. Please check Redis logs for details about the error.', '2023-02-01 18:00:23');
INSERT INTO `sys_oper_log` VALUES (104, '个人信息', 2, 'com.fudian.web.controller.system.SysProfileController.updateProfile()', 'PUT', 1, 'admin', NULL, '/system/user/profile', '127.0.0.1', '内网IP', '{\"admin\":true,\"createBy\":\"admin\",\"createTime\":\"2023-01-10 10:08:51\",\"delFlag\":\"0\",\"dept\":{\"ancestors\":\"0,100,101\",\"children\":[],\"deptId\":103,\"deptName\":\"研发部门\",\"leader\":\"若依\",\"orderNum\":1,\"params\":{},\"parentId\":101,\"status\":\"0\"},\"email\":\"ry@163.com\",\"loginDate\":\"2023-02-02 14:17:10\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"若依111\",\"params\":{},\"phonenumber\":\"15888888888\",\"remark\":\"管理员\",\"roles\":[{\"admin\":true,\"dataScope\":\"1\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":1,\"roleKey\":\"admin\",\"roleName\":\"超级管理员\",\"roleSort\":1,\"status\":\"0\"}],\"sex\":\"1\",\"status\":\"0\",\"userId\":1,\"userName\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-02 16:47:27');
INSERT INTO `sys_oper_log` VALUES (105, '个人信息', 2, 'com.fudian.web.controller.system.SysProfileController.updateProfile()', 'PUT', 1, 'admin', NULL, '/system/user/profile', '127.0.0.1', '内网IP', '{\"admin\":true,\"createBy\":\"admin\",\"createTime\":\"2023-01-10 10:08:51\",\"delFlag\":\"0\",\"dept\":{\"ancestors\":\"0,100,101\",\"children\":[],\"deptId\":103,\"deptName\":\"研发部门\",\"leader\":\"若依\",\"orderNum\":1,\"params\":{},\"parentId\":101,\"status\":\"0\"},\"email\":\"ry@163.com\",\"loginDate\":\"2023-02-02 14:17:10\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"若依\",\"params\":{},\"phonenumber\":\"15888888888\",\"remark\":\"管理员\",\"roles\":[{\"admin\":true,\"dataScope\":\"1\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":1,\"roleKey\":\"admin\",\"roleName\":\"超级管理员\",\"roleSort\":1,\"status\":\"0\"}],\"sex\":\"1\",\"status\":\"0\",\"userId\":1,\"userName\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-02 16:47:41');
INSERT INTO `sys_oper_log` VALUES (106, '用户管理', 1, 'com.fudian.web.controller.system.SysUserController.add()', 'POST', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"nickName\":\"马超宇\",\"params\":{},\"sex\":\"0\",\"userId\":100,\"userName\":\"马超宇\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-02 16:56:08');
INSERT INTO `sys_oper_log` VALUES (107, '用户管理', 3, 'com.fudian.web.controller.system.SysUserController.remove()', 'DELETE', 1, 'admin', NULL, '/system/user/100', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-02 16:57:23');
INSERT INTO `sys_oper_log` VALUES (108, '代码生成', 6, 'com.fudian.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '\"dahc_archive_type\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-02 17:24:43');
INSERT INTO `sys_oper_log` VALUES (109, '代码生成', 6, 'com.fudian.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '\"dahc_archive_type\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-02 17:24:43');
INSERT INTO `sys_oper_log` VALUES (110, '代码生成', 8, 'com.fudian.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"dahc_archive_type\"}', NULL, 1, 'nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 2', '2023-02-02 17:24:46');
INSERT INTO `sys_oper_log` VALUES (111, '代码生成', 6, 'com.fudian.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '192.168.0.10', '内网IP', '\"dahc_mapper\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-02 17:25:51');
INSERT INTO `sys_oper_log` VALUES (112, '代码生成', 6, 'com.fudian.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '\"dahc_metadata\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-02 17:42:59');
INSERT INTO `sys_oper_log` VALUES (113, '参数管理', 2, 'com.fudian.web.controller.system.SysConfigController.edit()', 'PUT', 1, 'admin', NULL, '/system/config', '127.0.0.1', '内网IP', '{\"configId\":4,\"configKey\":\"sys.account.captchaEnabled\",\"configName\":\"账号自助-验证码开关\",\"configType\":\"Y\",\"configValue\":\"true\",\"createBy\":\"admin\",\"createTime\":\"2023-01-10 10:09:07\",\"params\":{},\"remark\":\"是否开启验证码功能（true开启，false关闭）\",\"updateBy\":\"admin\",\"updateTime\":\"2023-01-31 17:51:31\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-06 12:19:25');
INSERT INTO `sys_oper_log` VALUES (114, '参数管理', 2, 'com.fudian.web.controller.system.SysConfigController.edit()', 'PUT', 1, 'admin', NULL, '/system/config', '127.0.0.1', '内网IP', '{\"configId\":4,\"configKey\":\"sys.account.captchaEnabled\",\"configName\":\"账号自助-验证码开关\",\"configType\":\"Y\",\"configValue\":\"false\",\"createBy\":\"admin\",\"createTime\":\"2023-01-10 10:09:07\",\"params\":{},\"remark\":\"是否开启验证码功能（true开启，false关闭）\",\"updateBy\":\"admin\",\"updateTime\":\"2023-02-06 12:19:25\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-06 13:48:24');
INSERT INTO `sys_oper_log` VALUES (115, '参数管理', 9, 'com.fudian.web.controller.system.SysConfigController.refreshCache()', 'DELETE', 1, 'admin', NULL, '/system/config/refreshCache', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-06 13:48:26');
INSERT INTO `sys_oper_log` VALUES (116, '用户管理', 2, 'com.fudian.web.controller.system.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"avatar\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-02 16:56:08\",\"delFlag\":\"0\",\"dept\":{\"ancestors\":\"0,100,101\",\"children\":[],\"deptId\":103,\"deptName\":\"研发部门\",\"leader\":\"若依\",\"orderNum\":1,\"params\":{},\"parentId\":101,\"status\":\"0\"},\"deptId\":103,\"email\":\"\",\"loginIp\":\"\",\"nickName\":\"马超宇\",\"params\":{},\"phonenumber\":\"\",\"roleIds\":[2],\"roles\":[],\"sex\":\"0\",\"status\":\"0\",\"updateBy\":\"admin\",\"userId\":100,\"userName\":\"马超宇\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 11:58:20');
INSERT INTO `sys_oper_log` VALUES (117, '用户管理', 4, 'com.fudian.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '100 [2,3]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 14:09:23');
INSERT INTO `sys_oper_log` VALUES (118, '用户管理', 4, 'com.fudian.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '100 [2]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 14:20:04');
INSERT INTO `sys_oper_log` VALUES (119, '用户管理', 4, 'com.fudian.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '100 [2,3]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 14:20:10');
INSERT INTO `sys_oper_log` VALUES (120, '用户管理', 4, 'com.fudian.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '100 [3]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 14:20:55');
INSERT INTO `sys_oper_log` VALUES (121, '用户管理', 4, 'com.fudian.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '100 [3,2]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 14:22:56');
INSERT INTO `sys_oper_log` VALUES (122, '用户管理', 4, 'com.fudian.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '1 [1,2]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 14:23:15');
INSERT INTO `sys_oper_log` VALUES (123, '用户管理', 4, 'com.fudian.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '1 [1]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 14:23:18');
INSERT INTO `sys_oper_log` VALUES (124, '用户管理', 4, 'com.fudian.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '1 [1,3,2]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 16:38:09');
INSERT INTO `sys_oper_log` VALUES (125, '角色管理', 2, 'com.fudian.web.controller.system.SysRoleController.dataScope()', 'PUT', 1, 'admin', NULL, '/system/role/dataScope', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2023-01-10 10:08:54\",\"dataScope\":\"2\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"deptIds\":[100,101,103,104,105,106,107,102,108,109],\"flag\":false,\"menuCheckStrictly\":true,\"params\":{},\"remark\":\"普通角色\",\"roleId\":3,\"roleKey\":\"common\",\"roleName\":\"测试角色\",\"roleSort\":2,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 16:39:10');
INSERT INTO `sys_oper_log` VALUES (126, '角色管理', 2, 'com.fudian.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2023-01-10 10:08:54\",\"dataScope\":\"2\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,1,100,1000,1001,1002,1003,1004,1005,1006,101,1007,1008,1009,1010,1011,116,102,1012,1013,1014,1015,105,1025,1026,1027,1028,1029,106,1030,1031,1032,1033,1034,108,500,1039,1040,1041,501,1042,1043,1044,1045,2010,2011],\"params\":{},\"remark\":\"普通角色\",\"roleId\":3,\"roleKey\":\"common\",\"roleName\":\"测试角色\",\"roleSort\":2,\"status\":\"0\"}', '{\"msg\":\"修改角色\'测试角色\'失败，角色权限已存在\",\"code\":500}', 0, NULL, '2023-02-07 16:39:37');
INSERT INTO `sys_oper_log` VALUES (127, '角色管理', 2, 'com.fudian.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2023-01-10 10:08:54\",\"dataScope\":\"2\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[1,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,100,1000,1001,1002,1003,1004,1005,1006,101,1007,1008,1009,1010,1011,116,102,1012,1013,1014,1015,105,1025,1026,1027,1028,1029,106,1030,1031,1032,1033,1034,108,500,1039,1040,1041,501,1042,1043,1044,1045],\"params\":{},\"remark\":\"普通角色\",\"roleId\":2,\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"roleSort\":2,\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 16:39:44');
INSERT INTO `sys_oper_log` VALUES (128, '角色管理', 2, 'com.fudian.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2023-01-10 10:08:54\",\"dataScope\":\"2\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[2001,2002,2003,2004,2005,2006,2007,2008,2009],\"params\":{},\"remark\":\"普通角色\",\"roleId\":3,\"roleKey\":\"common\",\"roleName\":\"测试角色\",\"roleSort\":2,\"status\":\"0\"}', '{\"msg\":\"修改角色\'测试角色\'失败，角色权限已存在\",\"code\":500}', 0, NULL, '2023-02-07 16:39:51');
INSERT INTO `sys_oper_log` VALUES (129, '角色管理', 2, 'com.fudian.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":true,\"createTime\":\"2023-01-10 10:08:54\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[2001,2002,2003,2004,2005,2006,2007,2008,2009],\"params\":{},\"remark\":\"超级管理员\",\"roleId\":1,\"roleKey\":\"admin\",\"roleName\":\"超级管理员\",\"roleSort\":1,\"status\":\"0\"}', NULL, 1, '不允许操作超级管理员角色', '2023-02-07 16:39:57');
INSERT INTO `sys_oper_log` VALUES (130, '用户管理', 2, 'com.fudian.web.controller.system.SysUserController.resetPwd()', 'PUT', 1, 'admin', NULL, '/system/user/resetPwd', '127.0.0.1', '内网IP', '{\"admin\":false,\"params\":{},\"updateBy\":\"admin\",\"userId\":100}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 16:41:16');
INSERT INTO `sys_oper_log` VALUES (131, '用户管理', 4, 'com.fudian.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '1 [1,2]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 17:25:44');
INSERT INTO `sys_oper_log` VALUES (132, '用户管理', 5, 'com.fudian.web.controller.system.SysUserController.export()', 'POST', 1, 'admin', NULL, '/system/user/export', '127.0.0.1', '内网IP', '{\"admin\":false,\"params\":{\"dataScope\":\"\"}}', NULL, 0, NULL, '2023-02-07 17:31:45');
INSERT INTO `sys_oper_log` VALUES (133, '用户管理', 5, 'com.fudian.web.controller.system.SysUserController.export()', 'POST', 1, 'admin', NULL, '/system/user/export', '127.0.0.1', '内网IP', '{\"admin\":false,\"params\":{\"dataScope\":\"\"}}', NULL, 0, NULL, '2023-02-07 17:32:12');
INSERT INTO `sys_oper_log` VALUES (134, '角色管理', 5, 'com.fudian.web.controller.system.SysRoleController.export()', 'POST', 1, 'admin', NULL, '/system/role/export', '127.0.0.1', '内网IP', '{\"admin\":false,\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{\"dataScope\":\"\"}}', NULL, 0, NULL, '2023-02-07 17:34:53');
INSERT INTO `sys_oper_log` VALUES (135, '代码生成', 6, 'com.fudian.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '\"dahc_hcx_trueing_manage\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-07 17:55:35');
INSERT INTO `sys_oper_log` VALUES (136, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManageController.add()', 'POST', 1, 'admin', NULL, '/system/manage', '192.168.0.18', '内网IP', '{\"createBy\":\"\",\"createTime\":\"2023-02-07 18:29:47.427\",\"id\":0,\"params\":{},\"remark\":\"11\",\"showPageNumber\":\"11\",\"showPiece\":\"11\",\"showRecord\":\"11\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"\",\"trueingRemark\":\"\",\"trueingScopeSecond\":\"\",\"trueingScopeStair\":\"\",\"trueingScopeThreeLevel\":\"\",\"trueingType\":\"\",\"updateBy\":\"\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Incorrect integer value: \'\' for column \'create_by\' at row 1\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\trueingManage\\DahcHcxTrueingManageMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingManageMapper.insertDahcHcxTrueingManage-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_hcx_trueing_manage      ( id,       create_by,       create_time,       update_by,                     table_type,       trueing_desc,       trueing_remark,       trueing_type,       show_piece,       show_record,       trueing_scope_stair,       show_page_number,       trueing_scope_second,       trueing_scope_three_level )       values ( ?,       ?,       ?,       ?,                     ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ? )\r\n### Cause: java.sql.SQLException: Incorrect integer value: \'\' for column \'create_by\' at row 1\n; uncategorized SQLException; SQL state [HY000]; error code [1366]; Incorrect integer value: \'\' for column \'create_by\' at row 1; nested exception is java.sql.SQLException: Incorrect integer value: \'\' for column \'create_by\' at row 1', '2023-02-07 18:29:51');
INSERT INTO `sys_oper_log` VALUES (137, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleKey\":\"common\",\"roleName\":\"测试1\"}', '{\"msg\":\"新增角色\'测试1\'失败，角色权限已存在\",\"code\":500}', 0, NULL, '2023-02-08 09:54:29');
INSERT INTO `sys_oper_log` VALUES (138, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleKey\":\"common1\",\"roleName\":\"测试1\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'status\' doesn\'t have a default value\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-system\\target\\classes\\mapper\\system\\SysRoleMapper.xml]\r\n### The error may involve com.fudian.system.mapper.SysRoleMapper.insertRole-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into sys_role(                    role_name,           role_key,                               menu_check_strictly,           dept_check_strictly,                               create_by,          create_time         )values(                    ?,           ?,                               ?,           ?,                               ?,          sysdate()         )\r\n### Cause: java.sql.SQLException: Field \'status\' doesn\'t have a default value\n; Field \'status\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'status\' doesn\'t have a default value', '2023-02-08 09:54:37');
INSERT INTO `sys_oper_log` VALUES (139, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"deptCheckStrictly\":true,\"deptIds\":[],\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[],\"params\":{},\"roleKey\":\"common\",\"roleName\":\"测试1\",\"roleSort\":1,\"status\":\"0\"}', '{\"msg\":\"新增角色\'测试1\'失败，角色权限已存在\",\"code\":500}', 0, NULL, '2023-02-08 09:55:06');
INSERT INTO `sys_oper_log` VALUES (140, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"deptCheckStrictly\":true,\"deptIds\":[],\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[],\"params\":{},\"roleId\":100,\"roleKey\":\"common1\",\"roleName\":\"测试1\",\"roleSort\":1,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 09:55:10');
INSERT INTO `sys_oper_log` VALUES (141, '角色管理', 3, 'com.fudian.web.controller.system.SysRoleController.remove()', 'DELETE', 1, 'admin', NULL, '/system/role/100', '192.168.0.18', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 09:55:21');
INSERT INTO `sys_oper_log` VALUES (142, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":101,\"roleKey\":\"com\",\"roleName\":\"测试1\",\"status\":\"0\"}', NULL, 1, '', '2023-02-08 10:00:50');
INSERT INTO `sys_oper_log` VALUES (143, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":102,\"roleKey\":\"com\",\"roleName\":\"测试1\",\"status\":\"0\"}', NULL, 1, '', '2023-02-08 10:01:27');
INSERT INTO `sys_oper_log` VALUES (144, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":103,\"roleKey\":\"com\",\"roleName\":\"测试1\",\"status\":\"0\"}', NULL, 1, '', '2023-02-08 10:02:14');
INSERT INTO `sys_oper_log` VALUES (145, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":104,\"roleKey\":\"com\",\"roleName\":\"测试1\",\"status\":\"0\"}', NULL, 1, '', '2023-02-08 10:03:12');
INSERT INTO `sys_oper_log` VALUES (146, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":105,\"roleKey\":\"com\",\"roleName\":\"测试1\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 10:03:55');
INSERT INTO `sys_oper_log` VALUES (147, '角色管理', 3, 'com.fudian.web.controller.system.SysRoleController.remove()', 'DELETE', 1, 'admin', NULL, '/system/role/105', '192.168.0.18', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 10:04:04');
INSERT INTO `sys_oper_log` VALUES (148, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.add()', 'POST', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createBy\":\"admin\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":106,\"roleKey\":\"com\",\"roleName\":\"测试1\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 10:04:12');
INSERT INTO `sys_oper_log` VALUES (149, '角色管理', 2, 'com.fudian.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createTime\":\"2023-02-08 10:04:12\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":106,\"roleKey\":\"com11\",\"roleName\":\"测试1\",\"status\":\"0\",\"updateBy\":\"admin\"}', NULL, 1, '', '2023-02-08 10:04:17');
INSERT INTO `sys_oper_log` VALUES (150, '角色管理', 2, 'com.fudian.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createTime\":\"2023-02-08 10:04:12\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":106,\"roleKey\":\"com1\",\"roleName\":\"测试1\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"修改角色\'测试1\'失败，请联系管理员\",\"code\":500}', 0, NULL, '2023-02-08 10:06:11');
INSERT INTO `sys_oper_log` VALUES (151, '角色管理', 2, 'com.fudian.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createTime\":\"2023-02-08 10:04:12\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":106,\"roleKey\":\"com11\",\"roleName\":\"测试1\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 10:08:20');
INSERT INTO `sys_oper_log` VALUES (152, '角色管理', 2, 'com.fudian.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '192.168.0.18', '内网IP', '{\"admin\":false,\"createTime\":\"2023-02-08 10:04:12\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"params\":{},\"roleId\":106,\"roleKey\":\"com11\",\"roleName\":\"测试1\",\"status\":\"1\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 10:08:25');
INSERT INTO `sys_oper_log` VALUES (153, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.saveRoleMenu()', 'POST', 1, 'admin', NULL, '/system/role/saveRoleMenu', '192.168.0.18', '内网IP', '{\"admin\":false,\"createTime\":\"2023-02-08 10:04:12\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"menuIds\":[],\"params\":{},\"roleId\":106,\"roleKey\":\"com11\",\"roleName\":\"测试1\",\"status\":\"1\"}', '{\"msg\":\"修改成功\",\"code\":20000}', 0, NULL, '2023-02-08 10:40:32');
INSERT INTO `sys_oper_log` VALUES (154, '角色管理', 1, 'com.fudian.web.controller.system.SysRoleController.saveRoleMenu()', 'POST', 1, 'admin', NULL, '/system/role/saveRoleMenu', '192.168.0.18', '内网IP', '{\"admin\":false,\"createTime\":\"2023-02-08 10:04:12\",\"dataScope\":\"1\",\"delFlag\":\"0\",\"deptCheckStrictly\":false,\"flag\":false,\"menuCheckStrictly\":false,\"menuIds\":[2001,2002,2003,2004,2005,2006,2007,2008,2009],\"params\":{},\"roleId\":106,\"roleKey\":\"com11\",\"roleName\":\"测试1\",\"status\":\"1\"}', '{\"msg\":\"修改成功\",\"code\":20000}', 0, NULL, '2023-02-08 10:40:39');
INSERT INTO `sys_oper_log` VALUES (155, '代码生成', 6, 'com.fudian.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '192.168.0.18', '内网IP', '\"dahc_sys_dict_type,dahc_sys_dict_data\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 12:18:38');
INSERT INTO `sys_oper_log` VALUES (156, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/updata', '192.168.0.18', '内网IP', '{\"createBy\":\"\",\"createTime\":\"2023-02-08 14:26:51.183\",\"dictId\":100,\"dictName\":\"测试1\",\"dictType\":\"\",\"params\":{},\"remark\":\"\",\"status\":\"\",\"updateBy\":\"\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 14:26:51');
INSERT INTO `sys_oper_log` VALUES (157, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"children\":[{\"children\":[],\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"\",\"fullName\":\"父级节点--01\",\"id\":\"\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":0,\"updateBy\":\"\"}],\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 14:50:09.241\",\"dictCode\":\"\",\"dictType\":\"\",\"eparentCode\":\"\",\"fullName\":\"\",\"id\":\"d0c203c973474c8ea5f4efd32ab51277\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":0,\"updateBy\":\"\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 14:50:09');
INSERT INTO `sys_oper_log` VALUES (158, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"children\":[{\"children\":[],\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"\",\"fullName\":\"父级节点--01\",\"id\":\"\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":0,\"updateBy\":\"\"}],\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 14:50:49.365\",\"dictCode\":\"\",\"dictType\":\"\",\"eparentCode\":\"\",\"fullName\":\"\",\"id\":\"70745a9646684a5c8386b2a668f12d49\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":0,\"updateBy\":\"\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 14:50:49');
INSERT INTO `sys_oper_log` VALUES (159, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"children\":[{\"children\":[],\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"\",\"fullName\":\"父级节点--01\",\"id\":\"\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":0,\"updateBy\":\"\"}],\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 14:51:50.013\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"父级节点--01\",\"id\":\"fde450ab2fbb405e9a1dcfb3385b0d0e\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":0,\"updateBy\":\"\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 14:51:50');
INSERT INTO `sys_oper_log` VALUES (160, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"children\":[{\"children\":[],\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"\",\"fullName\":\"父级节点--01\",\"id\":\"\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":0,\"updateBy\":\"\"}],\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 14:52:25.823\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"fde450ab2fbb405e9a1dcfb3385b0d0e\",\"fullName\":\"子级节点--01\",\"id\":\"554e69dd41f643aeac2470a4acc513a5\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":0,\"updateBy\":\"\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 14:52:26');
INSERT INTO `sys_oper_log` VALUES (161, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createTime\":\"2023-02-08 15:46:08.408\",\"dictName\":\"测试2\",\"params\":{},\"remark\":\"11\",\"status\":\"0\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\sys\\DahcSysDictTypeMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.sys.DahcSysDictTypeMapper.insertDahcSysDictType-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_sys_dict_type      ( dict_name,              status,              create_time,                     remark )       values ( ?,              ?,              ?,                     ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'\n; Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'', '2023-02-08 15:46:08');
INSERT INTO `sys_oper_log` VALUES (162, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createTime\":\"2023-02-08 15:46:41.111\",\"dictName\":\"测试21\",\"params\":{},\"remark\":\"11\",\"status\":\"0\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\sys\\DahcSysDictTypeMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.sys.DahcSysDictTypeMapper.insertDahcSysDictType-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_sys_dict_type      ( dict_name,              status,              create_time,                     remark )       values ( ?,              ?,              ?,                     ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'\n; Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'', '2023-02-08 15:46:41');
INSERT INTO `sys_oper_log` VALUES (163, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 15:48:51.676\",\"dictName\":\"测试2\",\"params\":{},\"status\":\"0\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\sys\\DahcSysDictTypeMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.sys.DahcSysDictTypeMapper.insertDahcSysDictType-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_sys_dict_type      ( dict_name,              status,       create_by,       create_time )       values ( ?,              ?,       ?,       ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'\n; Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'', '2023-02-08 15:48:52');
INSERT INTO `sys_oper_log` VALUES (164, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 15:51:51.726\",\"dictId\":\"e595be0d9c4a42039f60663ee725ecd6\",\"dictName\":\"测试2\",\"params\":{},\"status\":\"0\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'dict_id\' doesn\'t have a default value\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\sys\\DahcSysDictTypeMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.sys.DahcSysDictTypeMapper.insertDahcSysDictType-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_sys_dict_type      ( dict_name,              status,       create_by,       create_time )       values ( ?,              ?,       ?,       ? )\r\n### Cause: java.sql.SQLException: Field \'dict_id\' doesn\'t have a default value\n; Field \'dict_id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'dict_id\' doesn\'t have a default value', '2023-02-08 15:51:52');
INSERT INTO `sys_oper_log` VALUES (165, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 15:52:08.906\",\"dictId\":\"df9e694fff45468eb0ec1947109df29c\",\"dictName\":\"测试2\",\"params\":{},\"status\":\"0\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'dict_id\' doesn\'t have a default value\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\sys\\DahcSysDictTypeMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.sys.DahcSysDictTypeMapper.insertDahcSysDictType-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_sys_dict_type      ( dict_name,              status,       create_by,       create_time )       values ( ?,              ?,       ?,       ? )\r\n### Cause: java.sql.SQLException: Field \'dict_id\' doesn\'t have a default value\n; Field \'dict_id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'dict_id\' doesn\'t have a default value', '2023-02-08 15:52:41');
INSERT INTO `sys_oper_log` VALUES (166, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 15:53:23.103\",\"dictId\":\"10a452a7163047f68f8c2a9f09d85052\",\"dictName\":\"测试22\",\"params\":{},\"status\":\"0\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\sys\\DahcSysDictTypeMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.sys.DahcSysDictTypeMapper.insertDahcSysDictType-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_sys_dict_type      ( dict_id,       dict_name,              status,       create_by,       create_time )       values ( ?,       ?,              ?,       ?,       ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'\n; Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'\' for key \'dahc_sys_dict_type.dict_type\'', '2023-02-08 15:53:26');
INSERT INTO `sys_oper_log` VALUES (167, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 15:58:18.014\",\"dictId\":\"af95518bbba74d7697b98e5af5ad0364\",\"dictName\":\"测试22\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 15:58:18');
INSERT INTO `sys_oper_log` VALUES (168, '字典类型', 2, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.edit()', 'POST', 1, 'admin', NULL, '/system/dictType/update', '127.0.0.1', '内网IP', '{\"dictId\":\"af95518bbba74d7697b98e5af5ad0364\",\"dictName\":\"测试22\",\"params\":{},\"remark\":\"备注\",\"status\":\"0\",\"updateTime\":\"2023-02-08 16:31:49.854\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 16:31:50');
INSERT INTO `sys_oper_log` VALUES (169, '字典类型', 2, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.edit()', 'POST', 1, 'admin', NULL, '/system/dictType/update', '127.0.0.1', '内网IP', '{\"dictId\":\"af95518bbba74d7697b98e5af5ad0364\",\"dictName\":\"测试221\",\"params\":{},\"remark\":\"备注\",\"status\":\"0\",\"updateTime\":\"2023-02-08 16:32:20.459\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 16:32:20');
INSERT INTO `sys_oper_log` VALUES (170, '字典类型', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.remove()', 'POST', 1, 'admin', NULL, '/system/dictType/undefined', '127.0.0.1', '内网IP', '\"undefined\"', '{\"msg\":\"操作失败\",\"code\":500}', 0, NULL, '2023-02-08 16:42:20');
INSERT INTO `sys_oper_log` VALUES (171, '字典类型', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.remove()', 'POST', 1, 'admin', NULL, '/system/dictType/af95518bbba74d7697b98e5af5ad0364', '127.0.0.1', '内网IP', '\"af95518bbba74d7697b98e5af5ad0364\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 16:42:49');
INSERT INTO `sys_oper_log` VALUES (172, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 16:43:16.047\",\"dictId\":\"9d8185f4ba8a459285647749ffb163c9\",\"dictName\":\"测试\",\"params\":{},\"remark\":\"哈哈\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 16:43:16');
INSERT INTO `sys_oper_log` VALUES (173, '字典类型', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.remove()', 'POST', 1, 'admin', NULL, '/system/dictType/9d8185f4ba8a459285647749ffb163c9', '127.0.0.1', '内网IP', '\"9d8185f4ba8a459285647749ffb163c9\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 16:43:23');
INSERT INTO `sys_oper_log` VALUES (174, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 16:44:32.416\",\"dictId\":\"33d8f1e97a3d411baafdf8d8be527bd4\",\"dictName\":\"1111\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 16:44:32');
INSERT INTO `sys_oper_log` VALUES (175, '字典类型', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.remove()', 'POST', 1, 'admin', NULL, '/system/dictType/33d8f1e97a3d411baafdf8d8be527bd4', '127.0.0.1', '内网IP', '\"33d8f1e97a3d411baafdf8d8be527bd4\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 16:44:34');
INSERT INTO `sys_oper_log` VALUES (176, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 16:45:41.889\",\"dictId\":\"498017d9ed8844a9b314f0f8942b5a2a\",\"dictName\":\"111\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 16:45:42');
INSERT INTO `sys_oper_log` VALUES (177, '字典类型', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.remove()', 'POST', 1, 'admin', NULL, '/system/dictType/498017d9ed8844a9b314f0f8942b5a2a', '127.0.0.1', '内网IP', '\"498017d9ed8844a9b314f0f8942b5a2a\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 16:45:45');
INSERT INTO `sys_oper_log` VALUES (178, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '127.0.0.1', '内网IP', '{\"codeProperty\":\"11\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 18:32:39.592\",\"dictType\":\"100\",\"fullName\":\"显示\",\"id\":\"b009b73d2e454b6fa5a861163cd90593\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 18:32:40');
INSERT INTO `sys_oper_log` VALUES (179, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '127.0.0.1', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 18:32:50.73\",\"dictType\":\"100\",\"fullName\":\"2222\",\"id\":\"40b3e98891264f4e9f97c19619032e44\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 18:32:51');
INSERT INTO `sys_oper_log` VALUES (180, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 18:44:35.046\",\"dictType\":\"100\",\"fullName\":\"子级\",\"id\":\"64dc7f4e857b41d5b953a3bdbe8cde5d\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 18:44:35');
INSERT INTO `sys_oper_log` VALUES (181, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 18:46:02.913\",\"dictType\":\"100\",\"fullName\":\"测试\",\"id\":\"9a87f56a1c244f50b14b0600aabdef58\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 18:46:03');
INSERT INTO `sys_oper_log` VALUES (182, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '127.0.0.1', '内网IP', '{\"codeProperty\":\"1·\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 18:48:02.35\",\"dictType\":\"100\",\"fullName\":\"测试1111\",\"id\":\"f7a1e00659434f7a9fa7115becbbd319\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 18:48:03');
INSERT INTO `sys_oper_log` VALUES (183, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '127.0.0.1', '内网IP', '{\"codeProperty\":\"啊\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 18:48:13.035\",\"dictType\":\"100\",\"fullName\":\"测试333\",\"id\":\"831a41594f704ad2a59dd1dbdf8a1828\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 18:48:13');
INSERT INTO `sys_oper_log` VALUES (184, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '127.0.0.1', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 18:50:23.153\",\"dictType\":\"100\",\"fullName\":\"测试1\",\"id\":\"b77ae0ac6ccd49e6bebf16e226babc05\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 18:50:23');
INSERT INTO `sys_oper_log` VALUES (185, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '127.0.0.1', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 18:51:45.497\",\"dictType\":\"100\",\"eparentCode\":\"fde450ab2fbb405e9a1dcfb3385b0d0e\",\"fullName\":\"测试123\",\"id\":\"3a4870e5597e4b8b80e873bf30351039\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 18:51:46');
INSERT INTO `sys_oper_log` VALUES (186, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.edit()', 'POST', 1, 'admin', NULL, '/system/dictData/upadte', '127.0.0.1', '内网IP', '{\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 18:51:45\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"fde450ab2fbb405e9a1dcfb3385b0d0e\",\"fullName\":\"测试12322\",\"id\":\"3a4870e5597e4b8b80e873bf30351039\",\"params\":{},\"type\":1,\"updateBy\":\"\",\"updateTime\":\"2023-02-08 19:02:03.185\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 19:02:04');
INSERT INTO `sys_oper_log` VALUES (187, '字典类型', 1, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dictType/add', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-08 19:02:33.126\",\"dictId\":\"f5ac542edcc3436a8b96f146a9e8e800\",\"dictName\":\"测试2\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 19:02:34');
INSERT INTO `sys_oper_log` VALUES (188, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '127.0.0.1', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 19:03:29.116\",\"dictType\":\"f5ac542edcc3436a8b96f146a9e8e800\",\"eparentCode\":\"-\",\"fullName\":\"测试1\",\"id\":\"f25c971ea75943489ddda65e1d24193c\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 19:03:29');
INSERT INTO `sys_oper_log` VALUES (189, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/3a4870e5597e4b8b80e873bf30351039', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-08 19:12:50');
INSERT INTO `sys_oper_log` VALUES (190, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 09:51:37.425\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"父级节点-02\",\"id\":\"94e72294ee934070a77f3f6f053b2440\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 09:51:39');
INSERT INTO `sys_oper_log` VALUES (191, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 09:51:59.521\",\"dictType\":\"100\",\"eparentCode\":\"94e72294ee934070a77f3f6f053b2440\",\"fullName\":\"子级节点-02-01\",\"id\":\"f1303954d0fe482094f35f081411a50c\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 09:52:01');
INSERT INTO `sys_oper_log` VALUES (192, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 09:52:48.871\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"子级节点-02-02\",\"id\":\"2eb8ecb737b847f6915053b17035bbdb\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 09:52:50');
INSERT INTO `sys_oper_log` VALUES (193, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/2eb8ecb737b847f6915053b17035bbdb', '192.168.0.18', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 09:53:01');
INSERT INTO `sys_oper_log` VALUES (194, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 09:53:16.434\",\"dictType\":\"100\",\"eparentCode\":\"94e72294ee934070a77f3f6f053b2440\",\"fullName\":\"子级节点-02-02\",\"id\":\"59de99b0eb694b72818e1c10b2b7953e\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 09:53:18');
INSERT INTO `sys_oper_log` VALUES (195, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/f1303954d0fe482094f35f081411a50c,59de99b0eb694b72818e1c10b2b7953e', '192.168.0.18', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 09:53:34');
INSERT INTO `sys_oper_log` VALUES (196, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 09:56:16.308\",\"dictType\":\"100\",\"eparentCode\":\"94e72294ee934070a77f3f6f053b2440\",\"fullName\":\"字典11\",\"id\":\"5fa151af6b43482b86fee4a64211341c\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 09:56:18');
INSERT INTO `sys_oper_log` VALUES (197, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 09:56:24.211\",\"dictType\":\"100\",\"eparentCode\":\"94e72294ee934070a77f3f6f053b2440\",\"fullName\":\"字典222\",\"id\":\"e18c9b3c05c6424db01b9b70b01151d9\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 09:56:26');
INSERT INTO `sys_oper_log` VALUES (198, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 10:01:55.458\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"父级1\",\"id\":\"fed25f49110e453e9a1aa362c1de65e6\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 10:01:57');
INSERT INTO `sys_oper_log` VALUES (199, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 10:02:02.209\",\"dictType\":\"100\",\"eparentCode\":\"fed25f49110e453e9a1aa362c1de65e6\",\"fullName\":\"子级2\",\"id\":\"8205f8ce0d2c427882912013df560ed1\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 10:02:04');
INSERT INTO `sys_oper_log` VALUES (200, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 10:02:08.759\",\"dictType\":\"100\",\"eparentCode\":\"fed25f49110e453e9a1aa362c1de65e6\",\"fullName\":\"子级3\",\"id\":\"e50abf2092814a328b241072a6cc1eac\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 10:02:10');
INSERT INTO `sys_oper_log` VALUES (201, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 10:02:22.343\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"父级3\",\"id\":\"d3c507dcacf44dca8b407b9c0872ddb8\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 10:02:24');
INSERT INTO `sys_oper_log` VALUES (202, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/fed25f49110e453e9a1aa362c1de65e6,d3c507dcacf44dca8b407b9c0872ddb8', '192.168.0.18', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 10:02:41');
INSERT INTO `sys_oper_log` VALUES (203, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 10:06:27.454\",\"dictType\":\"f5ac542edcc3436a8b96f146a9e8e800\",\"eparentCode\":\"-\",\"fullName\":\"测试\",\"id\":\"68b9ee906b784fe5859c9cc57958e3df\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 10:06:29');
INSERT INTO `sys_oper_log` VALUES (204, '字典类型', 2, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.edit()', 'POST', 1, 'admin', NULL, '/system/dictType/update', '192.168.0.18', '内网IP', '{\"dictId\":\"100\",\"dictName\":\"测试1\",\"params\":{},\"remark\":\"备注\",\"status\":\"0\",\"updateBy\":\"admin\",\"updateTime\":\"2023-02-09 10:06:52.516\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 10:06:54');
INSERT INTO `sys_oper_log` VALUES (205, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[]', 0, NULL, '2023-02-09 10:24:25');
INSERT INTO `sys_oper_log` VALUES (206, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 10:25:18');
INSERT INTO `sys_oper_log` VALUES (207, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 10:28:32');
INSERT INTO `sys_oper_log` VALUES (208, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 10:28:59');
INSERT INTO `sys_oper_log` VALUES (209, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 10:28:59');
INSERT INTO `sys_oper_log` VALUES (210, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 10:29:31');
INSERT INTO `sys_oper_log` VALUES (211, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 10:31:58');
INSERT INTO `sys_oper_log` VALUES (212, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 10:32:07');
INSERT INTO `sys_oper_log` VALUES (213, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 10:33:35');
INSERT INTO `sys_oper_log` VALUES (214, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 10:34:46');
INSERT INTO `sys_oper_log` VALUES (215, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 11:13:17');
INSERT INTO `sys_oper_log` VALUES (216, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 11:13:26.781\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"父级节点02\",\"id\":\"40e7e4f0647f47d0a64b6d3a3c9756b1\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 11:13:28');
INSERT INTO `sys_oper_log` VALUES (217, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 11:18:20');
INSERT INTO `sys_oper_log` VALUES (218, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 11:19:21');
INSERT INTO `sys_oper_log` VALUES (219, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 11:21:34');
INSERT INTO `sys_oper_log` VALUES (220, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 11:47:11');
INSERT INTO `sys_oper_log` VALUES (221, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 11:47:47');
INSERT INTO `sys_oper_log` VALUES (222, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 11:49:54');
INSERT INTO `sys_oper_log` VALUES (223, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"测试1\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 12:00:27');
INSERT INTO `sys_oper_log` VALUES (224, '字典类型', 2, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.edit()', 'POST', 1, 'admin', NULL, '/system/dictType/update', '192.168.0.18', '内网IP', '{\"dictId\":\"100\",\"dictName\":\"实体档案\",\"params\":{},\"remark\":\"备注\",\"status\":\"0\",\"updateBy\":\"admin\",\"updateTime\":\"2023-02-09 12:00:40.397\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 12:00:40');
INSERT INTO `sys_oper_log` VALUES (225, '字典类型', 2, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.edit()', 'POST', 1, 'admin', NULL, '/system/dictType/update', '192.168.0.18', '内网IP', '{\"dictId\":\"100\",\"dictName\":\"核查项-档案类型\",\"params\":{},\"remark\":\"备注\",\"status\":\"0\",\"updateBy\":\"admin\",\"updateTime\":\"2023-02-09 12:00:55.379\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 12:00:55');
INSERT INTO `sys_oper_log` VALUES (226, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.edit()', 'POST', 1, 'admin', NULL, '/system/dictData/upadte', '192.168.0.18', '内网IP', '{\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 11:13:27\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"实体档案\",\"id\":\"40e7e4f0647f47d0a64b6d3a3c9756b1\",\"params\":{},\"type\":0,\"updateBy\":\"\",\"updateTime\":\"2023-02-09 12:01:11.692\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 12:01:11');
INSERT INTO `sys_oper_log` VALUES (227, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 12:01:17');
INSERT INTO `sys_oper_log` VALUES (228, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.edit()', 'POST', 1, 'admin', NULL, '/system/dictData/upadte', '192.168.0.18', '内网IP', '{\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 14:51:50\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"电子档案\",\"id\":\"fde450ab2fbb405e9a1dcfb3385b0d0e\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":0,\"updateBy\":\"\",\"updateTime\":\"2023-02-09 12:01:50.782\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 12:01:50');
INSERT INTO `sys_oper_log` VALUES (229, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.edit()', 'POST', 1, 'admin', NULL, '/system/dictData/upadte', '192.168.0.18', '内网IP', '{\"codeAttr\":0,\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-08 14:52:26\",\"dictCode\":\"\",\"dictType\":\"100\",\"eparentCode\":\"fde450ab2fbb405e9a1dcfb3385b0d0e\",\"fullName\":\"案卷著录项\",\"id\":\"554e69dd41f643aeac2470a4acc513a5\",\"nickerName\":\"\",\"params\":{},\"pinyin\":\"\",\"remark\":\"\",\"type\":1,\"updateBy\":\"\",\"updateTime\":\"2023-02-09 12:02:05.663\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 12:02:05');
INSERT INTO `sys_oper_log` VALUES (230, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 12:02:28.058\",\"dictType\":\"100\",\"eparentCode\":\"fde450ab2fbb405e9a1dcfb3385b0d0e\",\"fullName\":\"卷内文件著录项\",\"id\":\"4fc7358d541c4811a145c5f3d677f25d\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 12:02:28');
INSERT INTO `sys_oper_log` VALUES (231, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 12:02:40.05\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"电子对照实体\",\"id\":\"75c5655fcd7c419988cacdc9bf4537aa\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 12:02:40');
INSERT INTO `sys_oper_log` VALUES (232, '代码生成', 2, 'com.fudian.generator.controller.GenController.synchDb()', 'GET', 1, 'admin', NULL, '/tool/gen/synchDb/dahc_hcx_trueing_manage', '192.168.0.18', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 12:05:45');
INSERT INTO `sys_oper_log` VALUES (233, '代码生成', 2, 'com.fudian.generator.controller.GenController.synchDb()', 'GET', 1, 'admin', NULL, '/tool/gen/synchDb/dahc_hcx_trueing_manage', '192.168.0.18', '内网IP', '{}', NULL, 1, '同步数据失败，原表结构不存在', '2023-02-09 12:18:58');
INSERT INTO `sys_oper_log` VALUES (234, '代码生成', 6, 'com.fudian.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '192.168.0.18', '内网IP', '\"dahc_hcx_trueing_management\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 12:19:09');
INSERT INTO `sys_oper_log` VALUES (235, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 13:45:27');
INSERT INTO `sys_oper_log` VALUES (236, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-09 13:45:48.552\",\"dictType\":\"100\",\"eparentCode\":\"40e7e4f0647f47d0a64b6d3a3c9756b1\",\"fullName\":\"档案盒/备考表\",\"id\":\"c9649672bb3d4f03a20f99ab46920e5b\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 13:45:48');
INSERT INTO `sys_oper_log` VALUES (237, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 14:49:49.532\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试1\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\trueingManage\\DahcHcxTrueingManagementMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingManagementMapper.insertDahcHcxTrueingManagement-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_hcx_trueing_management      ( create_time,                     trueing_name,       table_type,       trueing_desc,       trueing_remark,       trueing_type,       show_piece,       show_record,       trueing_scope_stair,       show_page_number,       trueing_tree_rank )       values ( ?,                     ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ? )\r\n### Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\n; Field \'id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'id\' doesn\'t have a default value', '2023-02-09 14:49:50');
INSERT INTO `sys_oper_log` VALUES (238, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 14:52:06.835\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\trueingManage\\DahcHcxTrueingManagementMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingManagementMapper.insertDahcHcxTrueingManagement-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_hcx_trueing_management      ( create_time,                     trueing_name,       table_type,       trueing_desc,       trueing_remark,       trueing_type,       show_piece,       show_record,       trueing_scope_stair,       show_page_number,       trueing_tree_rank )       values ( ?,                     ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ? )\r\n### Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\n; Field \'id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'id\' doesn\'t have a default value', '2023-02-09 14:52:07');
INSERT INTO `sys_oper_log` VALUES (239, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 14:52:18.907\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 14:52:19');
INSERT INTO `sys_oper_log` VALUES (240, '菜单管理', 1, 'com.fudian.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"druid\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"档案核查\",\"menuType\":\"M\",\"orderNum\":5,\"params\":{},\"parentId\":0,\"path\":\"fileCheck\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 14:53:13');
INSERT INTO `sys_oper_log` VALUES (241, '菜单管理', 1, 'com.fudian.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"chart\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"核查模版1\",\"menuType\":\"C\",\"orderNum\":0,\"params\":{},\"parentId\":2012,\"path\":\"checkTemplate1\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 14:56:16');
INSERT INTO `sys_oper_log` VALUES (242, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 14:59:04');
INSERT INTO `sys_oper_log` VALUES (243, '代码生成', 6, 'com.fudian.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '192.168.0.18', '内网IP', '\"dahc_hcx_trueing_standard\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 14:59:14');
INSERT INTO `sys_oper_log` VALUES (244, '菜单管理', 2, 'com.fudian.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"fileCheck/physicalFile/index\",\"createTime\":\"2023-02-09 14:56:16\",\"icon\":\"chart\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2013,\"menuName\":\"核查模版1\",\"menuType\":\"C\",\"orderNum\":0,\"params\":{},\"parentId\":2012,\"path\":\"checkTemplate1\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 15:01:19');
INSERT INTO `sys_oper_log` VALUES (245, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 15:05:23.726\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\trueingManage\\DahcHcxTrueingManagementMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingManagementMapper.insertDahcHcxTrueingManagement-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_hcx_trueing_management      ( create_time,                            table_type,       trueing_desc,       trueing_remark,       trueing_type,       show_piece,       show_record,       trueing_scope_stair,       show_page_number,       trueing_tree_rank )       values ( ?,                            ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ? )\r\n### Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\n; Field \'id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'id\' doesn\'t have a default value', '2023-02-09 15:05:24');
INSERT INTO `sys_oper_log` VALUES (246, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 15:05:55.974\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\trueingManage\\DahcHcxTrueingManagementMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingManagementMapper.insertDahcHcxTrueingManagement-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_hcx_trueing_management      ( create_time,                            table_type,       trueing_desc,       trueing_remark,       trueing_type,       show_piece,       show_record,       trueing_scope_stair,       show_page_number,       trueing_tree_rank )       values ( ?,                            ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ? )\r\n### Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\n; Field \'id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'id\' doesn\'t have a default value', '2023-02-09 15:05:56');
INSERT INTO `sys_oper_log` VALUES (247, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-09 15:09:18.904\",\"examineStas\":[{\"value\":\"11\"},{\"value\":\"22\",\"key\":\"1675926547130\"}],\"id\":\"27ee41ce1e364b7fb5987ec984ddf1bc\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"11\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Incorrect integer value: \'admin\' for column \'create_by\' at row 1\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\trueingManage\\DahcHcxTrueingManagementMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingManagementMapper.insertDahcHcxTrueingManagement-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_hcx_trueing_management      ( id,       create_by,       create_time,                     trueing_name,       table_type,       trueing_desc,       trueing_remark,       trueing_type,       show_piece,       show_record,       trueing_scope_stair,       show_page_number,       trueing_tree_rank )       values ( ?,       ?,       ?,                     ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ? )\r\n### Cause: java.sql.SQLException: Incorrect integer value: \'admin\' for column \'create_by\' at row 1\n; uncategorized SQLException; SQL state [HY000]; error code [1366]; Incorrect integer value: \'admin\' for column \'create_by\' at row 1; nested exception is java.sql.SQLException: Incorrect integer value: \'admin\' for column \'create_by\' at row 1', '2023-02-09 15:09:19');
INSERT INTO `sys_oper_log` VALUES (248, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-09 15:10:03.225\",\"examineStas\":[{\"value\":\"11\"},{\"value\":\"22\",\"key\":\"1675926547130\"}],\"id\":\"f98d52a2f21f49bb9bfe5f05f81d904a\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"11\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Incorrect integer value: \'admin\' for column \'create_by\' at row 1\r\n### The error may exist in file [D:\\Projects\\系统核查\\fudian-boot-dahc\\target\\classes\\mapper\\trueingManage\\DahcHcxTrueingManagementMapper.xml]\r\n### The error may involve com.fudian.dahc.mapper.trueingManage.DahcHcxTrueingManagementMapper.insertDahcHcxTrueingManagement-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into dahc_hcx_trueing_management      ( id,       create_by,       create_time,                     trueing_name,       table_type,       trueing_desc,       trueing_remark,       trueing_type,       show_piece,       show_record,       trueing_scope_stair,       show_page_number,       trueing_tree_rank )       values ( ?,       ?,       ?,                     ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ? )\r\n### Cause: java.sql.SQLException: Incorrect integer value: \'admin\' for column \'create_by\' at row 1\n; uncategorized SQLException; SQL state [HY000]; error code [1366]; Incorrect integer value: \'admin\' for column \'create_by\' at row 1; nested exception is java.sql.SQLException: Incorrect integer value: \'admin\' for column \'create_by\' at row 1', '2023-02-09 15:10:03');
INSERT INTO `sys_oper_log` VALUES (249, '菜单管理', 2, 'com.fudian.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"fileCheck/physicalFile/templateFirst/indexindex\",\"createTime\":\"2023-02-09 14:56:16\",\"icon\":\"chart\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2013,\"menuName\":\"核查模版1\",\"menuType\":\"C\",\"orderNum\":0,\"params\":{},\"parentId\":2012,\"path\":\"templateFirst\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 15:27:16');
INSERT INTO `sys_oper_log` VALUES (250, '菜单管理', 2, 'com.fudian.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"fileCheck/physicalFile/templateFirst/index\",\"createTime\":\"2023-02-09 14:56:16\",\"icon\":\"chart\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2013,\"menuName\":\"核查模版1\",\"menuType\":\"C\",\"orderNum\":0,\"params\":{},\"parentId\":2012,\"path\":\"templateFirst\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-09 15:27:53');
INSERT INTO `sys_oper_log` VALUES (251, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 15:29:52.64\",\"examineStas\":[{\"value\":\"1\"},{\"value\":\"2\",\"key\":\"1675927791089\"}],\"id\":\"a4690402a80e424c8622c79554812f24\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试22\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 15:29:53');
INSERT INTO `sys_oper_log` VALUES (252, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"examineStas\":[{\"value\":\"1\"}],\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":50000,\"data\":\"重复核查项名称\",\"msg\":\"服务器内部错误\"}', 0, NULL, '2023-02-09 15:39:05');
INSERT INTO `sys_oper_log` VALUES (253, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"examineStas\":[{\"value\":\"1\"}],\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":50000,\"msg\":\"重复核查项名称\"}', 0, NULL, '2023-02-09 15:44:14');
INSERT INTO `sys_oper_log` VALUES (254, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 15:44:24.609\",\"examineStas\":[{\"value\":\"1\"}],\"id\":\"314fb0dd2a404c198e723b2708075d62\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试2\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 15:44:25');
INSERT INTO `sys_oper_log` VALUES (255, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11.035\",\"examineStas\":[{\"value\":\"2\"}],\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 16:25:11');
INSERT INTO `sys_oper_log` VALUES (256, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:58:02.467\",\"examineStas\":[{\"value\":\"核查标准1\"},{\"value\":\"核查标准2\",\"key\":\"1675933077843\"}],\"id\":\"3516389dffa04927b78b8dbd27996f7a\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项测试1\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"123456\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 16:58:03');
INSERT INTO `sys_oper_log` VALUES (257, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-09 18:16:15');
INSERT INTO `sys_oper_log` VALUES (258, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"2\"}],\"examineStasString\":\"2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:45:45.079\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:45:46');
INSERT INTO `sys_oper_log` VALUES (259, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"2\"},{\"value\":\"233\",\"key\":\"1675939549273\"}],\"examineStasString\":\"2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:45:51.065\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:45:52');
INSERT INTO `sys_oper_log` VALUES (260, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:45:56.039\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:45:57');
INSERT INTO `sys_oper_log` VALUES (261, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:46:00.761\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:46:01');
INSERT INTO `sys_oper_log` VALUES (262, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:46:07.893\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:46:08');
INSERT INTO `sys_oper_log` VALUES (263, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:47:04.697\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:47:05');
INSERT INTO `sys_oper_log` VALUES (264, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"2\"},{\"value\":\"233\"}],\"examineStasString\":\"2/233/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:49:03.479\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:49:04');
INSERT INTO `sys_oper_log` VALUES (265, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"0\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:49:17.345\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:49:18');
INSERT INTO `sys_oper_log` VALUES (266, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:51:10.753\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:51:11');
INSERT INTO `sys_oper_log` VALUES (267, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"2\"},{\"value\":\"233\"}],\"examineStasString\":\"2/233/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:51:32.338\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:51:33');
INSERT INTO `sys_oper_log` VALUES (268, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:51:35.7\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:51:36');
INSERT INTO `sys_oper_log` VALUES (269, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"2\"},{\"value\":\"233\"}],\"examineStasString\":\"2/233/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:52:16.401\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:52:17');
INSERT INTO `sys_oper_log` VALUES (270, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"2\"},{\"value\":\"233\"}],\"examineStasString\":\"2/233/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:53:41.125\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:53:42');
INSERT INTO `sys_oper_log` VALUES (271, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:53:45.482\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:53:46');
INSERT INTO `sys_oper_log` VALUES (272, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:53:48.604\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:53:49');
INSERT INTO `sys_oper_log` VALUES (273, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"},{\"value\":\"2\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:53:51.186\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:53:52');
INSERT INTO `sys_oper_log` VALUES (274, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 16:25:11\",\"examineStas\":[{\"value\":\"233\"}],\"examineStasString\":\"233/2/\",\"id\":\"08e62b7faf914ad4bad75b8093a1945e\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项01121\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-09 18:53:54.806\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-09 18:53:55');
INSERT INTO `sys_oper_log` VALUES (275, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"examineStas\":[{\"value\":\"哈哈\"}],\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":50000,\"msg\":\"重复核查项名称\"}', 0, NULL, '2023-02-10 09:45:37');
INSERT INTO `sys_oper_log` VALUES (276, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 09:45:39.861\",\"examineStas\":[{\"value\":\"哈哈\"}],\"id\":\"224d46f882ad4c91b9f41f7b92d73a16\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试21\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 09:45:41');
INSERT INTO `sys_oper_log` VALUES (277, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.remove()', 'POST', 1, 'admin', NULL, '/trueing/management/remove/08e62b7faf914ad4bad75b8093a1945e', '192.168.0.18', '内网IP', '[\"08e62b7faf914ad4bad75b8093a1945e\"]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 10:20:50');
INSERT INTO `sys_oper_log` VALUES (278, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.remove()', 'POST', 1, 'admin', NULL, '/trueing/management/remove/314fb0dd2a404c198e723b2708075d62,224d46f882ad4c91b9f41f7b92d73a16', '192.168.0.18', '内网IP', '[\"314fb0dd2a404c198e723b2708075d62\",\"224d46f882ad4c91b9f41f7b92d73a16\"]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 10:20:58');
INSERT INTO `sys_oper_log` VALUES (279, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.remove()', 'POST', 1, 'admin', NULL, '/trueing/management/remove/a4690402a80e424c8622c79554812f24', '192.168.0.18', '内网IP', '[\"a4690402a80e424c8622c79554812f24\"]', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 10:31:21');
INSERT INTO `sys_oper_log` VALUES (280, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:02:47');
INSERT INTO `sys_oper_log` VALUES (281, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:02:47');
INSERT INTO `sys_oper_log` VALUES (282, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:02:47');
INSERT INTO `sys_oper_log` VALUES (283, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:03:35');
INSERT INTO `sys_oper_log` VALUES (284, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:03:35');
INSERT INTO `sys_oper_log` VALUES (285, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:03:35');
INSERT INTO `sys_oper_log` VALUES (286, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:03:43');
INSERT INTO `sys_oper_log` VALUES (287, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:03:43');
INSERT INTO `sys_oper_log` VALUES (288, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:03:43');
INSERT INTO `sys_oper_log` VALUES (289, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:04:25');
INSERT INTO `sys_oper_log` VALUES (290, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:04:25');
INSERT INTO `sys_oper_log` VALUES (291, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:04:25');
INSERT INTO `sys_oper_log` VALUES (292, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:04:42');
INSERT INTO `sys_oper_log` VALUES (293, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:04:42');
INSERT INTO `sys_oper_log` VALUES (294, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:04:42');
INSERT INTO `sys_oper_log` VALUES (295, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:04:55');
INSERT INTO `sys_oper_log` VALUES (296, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:04:55');
INSERT INTO `sys_oper_log` VALUES (297, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:04:55');
INSERT INTO `sys_oper_log` VALUES (298, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 11:05:42');
INSERT INTO `sys_oper_log` VALUES (299, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:06:34');
INSERT INTO `sys_oper_log` VALUES (300, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:06:34');
INSERT INTO `sys_oper_log` VALUES (301, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:06:34');
INSERT INTO `sys_oper_log` VALUES (302, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:06:58');
INSERT INTO `sys_oper_log` VALUES (303, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:06:58');
INSERT INTO `sys_oper_log` VALUES (304, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:06:58');
INSERT INTO `sys_oper_log` VALUES (305, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:07:27');
INSERT INTO `sys_oper_log` VALUES (306, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:07:27');
INSERT INTO `sys_oper_log` VALUES (307, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:07:27');
INSERT INTO `sys_oper_log` VALUES (308, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:08:04');
INSERT INTO `sys_oper_log` VALUES (309, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:08:04');
INSERT INTO `sys_oper_log` VALUES (310, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:08:04');
INSERT INTO `sys_oper_log` VALUES (311, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:10:14');
INSERT INTO `sys_oper_log` VALUES (312, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 11:10:45.424\",\"examineStas\":[{\"value\":\"整齐\"},{\"value\":\"没有破损\",\"key\":\"1675998632011\"}],\"id\":\"bbf091234f294352890f4021a0ad8276\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查目录\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 11:10:47');
INSERT INTO `sys_oper_log` VALUES (313, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:13:51');
INSERT INTO `sys_oper_log` VALUES (314, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:25:25');
INSERT INTO `sys_oper_log` VALUES (315, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:31:15');
INSERT INTO `sys_oper_log` VALUES (316, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:31:55');
INSERT INTO `sys_oper_log` VALUES (317, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:33:51');
INSERT INTO `sys_oper_log` VALUES (318, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:37:04');
INSERT INTO `sys_oper_log` VALUES (319, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:46:05');
INSERT INTO `sys_oper_log` VALUES (320, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:46:35');
INSERT INTO `sys_oper_log` VALUES (321, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:51:21');
INSERT INTO `sys_oper_log` VALUES (322, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:52:25');
INSERT INTO `sys_oper_log` VALUES (323, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"}]', 0, NULL, '2023-02-10 11:56:21');
INSERT INTO `sys_oper_log` VALUES (324, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 11:57:32');
INSERT INTO `sys_oper_log` VALUES (325, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 11:57:44.068\",\"examineStas\":[{\"value\":\"332\"}],\"id\":\"2952a7851d9d463591e967af6eee3dfb\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"0\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"备考表核查项\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895213\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 11:57:46');
INSERT INTO `sys_oper_log` VALUES (326, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 11:58:31');
INSERT INTO `sys_oper_log` VALUES (327, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 12:11:25');
INSERT INTO `sys_oper_log` VALUES (328, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 12:12:44');
INSERT INTO `sys_oper_log` VALUES (329, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 12:13:10');
INSERT INTO `sys_oper_log` VALUES (330, '菜单管理', 2, 'com.fudian.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"fileCheck/physicalFile/index\",\"createTime\":\"2023-02-09 14:56:16\",\"icon\":\"chart\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2013,\"menuName\":\"实体核查\",\"menuType\":\"C\",\"orderNum\":0,\"params\":{},\"parentId\":2012,\"path\":\"physicalFile\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 12:18:01');
INSERT INTO `sys_oper_log` VALUES (331, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 12:19:58');
INSERT INTO `sys_oper_log` VALUES (332, '菜单管理', 1, 'com.fudian.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"code\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"电子核查\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2012,\"path\":\"ele\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 12:20:53');
INSERT INTO `sys_oper_log` VALUES (333, '菜单管理', 2, 'com.fudian.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2023-02-10 12:20:53\",\"icon\":\"code\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2014,\"menuName\":\"电子核查\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2012,\"path\":\"electronicFile\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 12:21:25');
INSERT INTO `sys_oper_log` VALUES (334, '菜单管理', 2, 'com.fudian.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"fileCheck/electronicFile/templateFirst/index\",\"createTime\":\"2023-02-10 12:20:53\",\"icon\":\"code\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2014,\"menuName\":\"电子核查-模版1\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2012,\"path\":\"electronicTemplateFirst\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 12:22:41');
INSERT INTO `sys_oper_log` VALUES (335, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 12:24:37');
INSERT INTO `sys_oper_log` VALUES (336, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 12:26:21');
INSERT INTO `sys_oper_log` VALUES (337, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 12:27:41');
INSERT INTO `sys_oper_log` VALUES (338, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 12:29:41');
INSERT INTO `sys_oper_log` VALUES (339, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 13:44:07');
INSERT INTO `sys_oper_log` VALUES (340, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 13:51:13');
INSERT INTO `sys_oper_log` VALUES (341, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 13:51:23.678\",\"dictType\":\"100\",\"eparentCode\":\"c9649672bb3d4f03a20f99ab46920e5b\",\"fullName\":\"备考表1\",\"id\":\"f3b5e298557a43d6942ddfcd758d0771\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 13:51:26');
INSERT INTO `sys_oper_log` VALUES (342, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"}]', 0, NULL, '2023-02-10 13:51:29');
INSERT INTO `sys_oper_log` VALUES (343, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 13:52:16');
INSERT INTO `sys_oper_log` VALUES (344, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 13:53:15');
INSERT INTO `sys_oper_log` VALUES (345, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 13:53:22.035\",\"dictType\":\"100\",\"eparentCode\":\"c9649672bb3d4f03a20f99ab46920e5b\",\"fullName\":\"备考表2\",\"id\":\"9f1f66e02c26434eb6412382915c3e00\",\"params\":{},\"type\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 13:53:24');
INSERT INTO `sys_oper_log` VALUES (346, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 13:53:50.532\",\"examineStas\":[{\"value\":\"11\"}],\"id\":\"339f871ecc3e4de2b4c8e244637bd05e\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"备考表2 - 核查项1\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"9f1f66e02c26434eb6412382915c3e00\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 13:53:53');
INSERT INTO `sys_oper_log` VALUES (347, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 14:10:38');
INSERT INTO `sys_oper_log` VALUES (348, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 14:12:36');
INSERT INTO `sys_oper_log` VALUES (349, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 14:13:12');
INSERT INTO `sys_oper_log` VALUES (350, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.edit()', 'POST', 1, 'admin', NULL, '/system/dictData/upadte', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"eparentCode\":\"\",\"fullName\":\"档案盒编辑\",\"id\":\"12345895\",\"params\":{},\"updateTime\":\"2023-02-10 14:13:23.933\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 14:13:26');
INSERT INTO `sys_oper_log` VALUES (351, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 14:13:49');
INSERT INTO `sys_oper_log` VALUES (352, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.edit()', 'POST', 1, 'admin', NULL, '/system/dictData/upadte', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"eparentCode\":\"\",\"fullName\":\"备考表编辑\",\"params\":{},\"updateTime\":\"2023-02-10 14:13:53.095\"}', '{\"msg\":\"操作失败\",\"code\":500}', 0, NULL, '2023-02-10 14:13:55');
INSERT INTO `sys_oper_log` VALUES (353, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 14:16:16');
INSERT INTO `sys_oper_log` VALUES (354, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-10 14:16:46.931\",\"dictType\":\"100\",\"eparentCode\":\"c9649672bb3d4f03a20f99ab46920e5b\",\"fullName\":\"编辑测\",\"id\":\"d7405e2a2453411da14b85f57cc8abd3\",\"params\":{},\"type\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 14:16:49');
INSERT INTO `sys_oper_log` VALUES (355, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.edit()', 'POST', 1, 'admin', NULL, '/system/dictData/upadte', '192.168.0.18', '内网IP', '{\"dictType\":\"100\",\"eparentCode\":\"c9649672bb3d4f03a20f99ab46920e5b\",\"fullName\":\"编辑测试\",\"id\":\"d7405e2a2453411da14b85f57cc8abd3\",\"params\":{},\"type\":2,\"updateTime\":\"2023-02-10 14:17:04.932\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 14:17:07');
INSERT INTO `sys_oper_log` VALUES (356, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 14:17:45');
INSERT INTO `sys_oper_log` VALUES (357, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 14:22:13');
INSERT INTO `sys_oper_log` VALUES (358, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"},{\"label\":\"备考表1\",\"value\":\"f3b5e298557a43d6942ddfcd758d0771\"}]', 0, NULL, '2023-02-10 14:24:19');
INSERT INTO `sys_oper_log` VALUES (359, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/f3b5e298557a43d6942ddfcd758d0771', '192.168.0.18', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 14:24:35');
INSERT INTO `sys_oper_log` VALUES (360, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 14:24:42');
INSERT INTO `sys_oper_log` VALUES (361, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 14:39:19');
INSERT INTO `sys_oper_log` VALUES (362, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 14:56:30');
INSERT INTO `sys_oper_log` VALUES (363, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-10 14:56:34.18\",\"dictType\":\"100\",\"eparentCode\":\"c9649672bb3d4f03a20f99ab46920e5b\",\"fullName\":\"删除\",\"id\":\"afb1138776044d3182f2a68e96c94759\",\"params\":{},\"type\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 14:56:36');
INSERT INTO `sys_oper_log` VALUES (364, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 14:56:47.783\",\"examineStas\":[{\"value\":\"删除\"},{\"value\":\"删除\",\"key\":\"1676012204625\"}],\"id\":\"ddd1fdf49fd449ae92853eea362450af\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"删除\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"afb1138776044d3182f2a68e96c94759\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 14:56:50');
INSERT INTO `sys_oper_log` VALUES (365, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/afb1138776044d3182f2a68e96c94759', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 14:56:53');
INSERT INTO `sys_oper_log` VALUES (366, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 14:59:21');
INSERT INTO `sys_oper_log` VALUES (367, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-10 14:59:27.921\",\"dictType\":\"100\",\"eparentCode\":\"c9649672bb3d4f03a20f99ab46920e5b\",\"fullName\":\"删除\",\"id\":\"074a6c950c784066a2590f3954f81ce7\",\"params\":{},\"type\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 14:59:30');
INSERT INTO `sys_oper_log` VALUES (368, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 15:00:39.221\",\"examineStas\":[{\"value\":\"删除11\"},{\"value\":\"删除11\",\"key\":\"1676012436087\"}],\"id\":\"1359c030bb174564960248775ed69dee\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"删除\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"074a6c950c784066a2590f3954f81ce7\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:00:42');
INSERT INTO `sys_oper_log` VALUES (369, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/074a6c950c784066a2590f3954f81ce7', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:03:00');
INSERT INTO `sys_oper_log` VALUES (370, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 15:05:27');
INSERT INTO `sys_oper_log` VALUES (371, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:05:36.333\",\"dictType\":\"100\",\"eparentCode\":\"c9649672bb3d4f03a20f99ab46920e5b\",\"fullName\":\"删除\",\"id\":\"6c6b6f97f03f4f3ebc09e7e94eed71f3\",\"params\":{},\"type\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:05:39');
INSERT INTO `sys_oper_log` VALUES (372, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 15:05:54.677\",\"examineStas\":[{\"value\":\"删除44\"},{\"value\":\"删除44\",\"key\":\"1676012749407\"}],\"id\":\"5dab8441c7c9441091745cbab7d1f81e\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"删除22\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"6c6b6f97f03f4f3ebc09e7e94eed71f3\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:05:57');
INSERT INTO `sys_oper_log` VALUES (373, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/6c6b6f97f03f4f3ebc09e7e94eed71f3', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:06:10');
INSERT INTO `sys_oper_log` VALUES (374, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:29:21');
INSERT INTO `sys_oper_log` VALUES (375, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:29:24.891\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"删除\",\"id\":\"0f2855abf8ed4cce96b769d2ad6d7dd6\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:29:27');
INSERT INTO `sys_oper_log` VALUES (376, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:29:36.368\",\"dictType\":\"100\",\"eparentCode\":\"0f2855abf8ed4cce96b769d2ad6d7dd6\",\"fullName\":\"删除\",\"id\":\"d58ce5d95a5b4a188263a9acd35c9a7a\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:29:39');
INSERT INTO `sys_oper_log` VALUES (377, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 15:29:43');
INSERT INTO `sys_oper_log` VALUES (378, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 15:29:59');
INSERT INTO `sys_oper_log` VALUES (379, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 15:30:10');
INSERT INTO `sys_oper_log` VALUES (380, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 15:31:20');
INSERT INTO `sys_oper_log` VALUES (381, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 15:31:42');
INSERT INTO `sys_oper_log` VALUES (382, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:31:56.857\",\"dictType\":\"100\",\"eparentCode\":\"d58ce5d95a5b4a188263a9acd35c9a7a\",\"fullName\":\"删除哈哈\",\"id\":\"a2fec778f8d24e0e971f4300425ff4c0\",\"params\":{},\"type\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:31:59');
INSERT INTO `sys_oper_log` VALUES (383, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 15:32:13.625\",\"examineStas\":[{\"value\":\"删除哈哈\"},{\"value\":\"删除哈哈\",\"key\":\"1676014329776\"}],\"id\":\"f684c31be3dc44cf8850fb4c0f8b995e\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"删除哈哈\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"a2fec778f8d24e0e971f4300425ff4c0\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:32:16');
INSERT INTO `sys_oper_log` VALUES (384, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:32:42');
INSERT INTO `sys_oper_log` VALUES (385, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/0f2855abf8ed4cce96b769d2ad6d7dd6', '192.168.0.18', '内网IP', '{}', NULL, 1, 'nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named \'id\' in \'class java.lang.String\'', '2023-02-10 15:33:40');
INSERT INTO `sys_oper_log` VALUES (386, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:34:36');
INSERT INTO `sys_oper_log` VALUES (387, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/0f2855abf8ed4cce96b769d2ad6d7dd6', '192.168.0.18', '内网IP', '{}', NULL, 1, 'nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named \'id\' in \'class java.lang.String\'', '2023-02-10 15:34:49');
INSERT INTO `sys_oper_log` VALUES (388, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:39:54');
INSERT INTO `sys_oper_log` VALUES (389, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/0f2855abf8ed4cce96b769d2ad6d7dd6', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:40:00');
INSERT INTO `sys_oper_log` VALUES (390, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:40:08');
INSERT INTO `sys_oper_log` VALUES (391, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:41:24');
INSERT INTO `sys_oper_log` VALUES (392, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:41:32.328\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"删除父级\",\"id\":\"276a3c4f71434445b864986b13be6312\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:41:35');
INSERT INTO `sys_oper_log` VALUES (393, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:41:40.525\",\"dictType\":\"100\",\"eparentCode\":\"276a3c4f71434445b864986b13be6312\",\"fullName\":\"删除二级\",\"id\":\"282ab6b2380d4c17bd6f679f143a7cbb\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:41:43');
INSERT INTO `sys_oper_log` VALUES (394, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 15:41:46');
INSERT INTO `sys_oper_log` VALUES (395, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:41:51.106\",\"dictType\":\"100\",\"eparentCode\":\"282ab6b2380d4c17bd6f679f143a7cbb\",\"fullName\":\"删除三级\",\"id\":\"98377fae2d5d4f4cb468a279449b6011\",\"params\":{},\"type\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:41:53');
INSERT INTO `sys_oper_log` VALUES (396, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 15:42:10.843\",\"examineStas\":[{\"value\":\"删除核查标准1\"},{\"value\":\"删除核查标准2\",\"key\":\"1676014925694\"}],\"id\":\"c74a94993f48480789c6812f908dec2c\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"删除核查项\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"98377fae2d5d4f4cb468a279449b6011\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:42:13');
INSERT INTO `sys_oper_log` VALUES (397, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:42:32');
INSERT INTO `sys_oper_log` VALUES (398, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/276a3c4f71434445b864986b13be6312', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:42:35');
INSERT INTO `sys_oper_log` VALUES (399, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:43:59');
INSERT INTO `sys_oper_log` VALUES (400, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:44:06.954\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"删除父级\",\"id\":\"5c83f29f9959489c81a06f7f2add9768\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:44:09');
INSERT INTO `sys_oper_log` VALUES (401, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:44:15.22\",\"dictType\":\"100\",\"eparentCode\":\"5c83f29f9959489c81a06f7f2add9768\",\"fullName\":\"删除子级\",\"id\":\"5a38fc3f992643debba0ddf515ca66df\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:44:18');
INSERT INTO `sys_oper_log` VALUES (402, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"删除三级\",\"value\":\"98377fae2d5d4f4cb468a279449b6011\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 15:44:33');
INSERT INTO `sys_oper_log` VALUES (403, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"删除三级\",\"value\":\"98377fae2d5d4f4cb468a279449b6011\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 15:44:38');
INSERT INTO `sys_oper_log` VALUES (404, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-10 15:44:47.929\",\"dictType\":\"100\",\"eparentCode\":\"5a38fc3f992643debba0ddf515ca66df\",\"fullName\":\"删除三级\",\"id\":\"be6b6926bef6444a906e6c9adce50e1d\",\"params\":{},\"type\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 15:44:50');
INSERT INTO `sys_oper_log` VALUES (405, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 15:45:00.859\",\"examineStas\":[{\"value\":\"11\"}],\"id\":\"0210d1a7ed6849338392a4a81a9d9529\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"be6b6926bef6444a906e6c9adce50e1d\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:45:03');
INSERT INTO `sys_oper_log` VALUES (406, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:45:20');
INSERT INTO `sys_oper_log` VALUES (407, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/5c83f29f9959489c81a06f7f2add9768', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 15:46:41');
INSERT INTO `sys_oper_log` VALUES (408, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 15:46:47');
INSERT INTO `sys_oper_log` VALUES (409, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 16:08:33');
INSERT INTO `sys_oper_log` VALUES (410, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 16:08:37.437\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"删除父级\",\"id\":\"f12993b0026244d4afd23207eb86326d\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 16:08:40');
INSERT INTO `sys_oper_log` VALUES (411, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 16:08:47.092\",\"dictType\":\"100\",\"eparentCode\":\"f12993b0026244d4afd23207eb86326d\",\"fullName\":\"删除子级二级\",\"id\":\"8cc5f9e83fb14eb3a52053bd6bb36889\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 16:08:49');
INSERT INTO `sys_oper_log` VALUES (412, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"删除三级\",\"value\":\"98377fae2d5d4f4cb468a279449b6011\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"删除三级\",\"value\":\"be6b6926bef6444a906e6c9adce50e1d\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 16:08:53');
INSERT INTO `sys_oper_log` VALUES (413, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-02-10 16:09:02.884\",\"dictType\":\"100\",\"eparentCode\":\"8cc5f9e83fb14eb3a52053bd6bb36889\",\"fullName\":\"删除三级哈哈\",\"id\":\"216748dfafd8475d822cc5bc80d7b15a\",\"params\":{},\"type\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 16:09:05');
INSERT INTO `sys_oper_log` VALUES (414, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 16:09:25.746\",\"examineStas\":[{\"value\":\"删除哈哈哈\"}],\"id\":\"30e1629602714db397ba4a1c595bb4fc\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"删除核查项2222\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"216748dfafd8475d822cc5bc80d7b15a\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:09:28');
INSERT INTO `sys_oper_log` VALUES (415, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 16:09:36');
INSERT INTO `sys_oper_log` VALUES (416, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/8cc5f9e83fb14eb3a52053bd6bb36889', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:09:42');
INSERT INTO `sys_oper_log` VALUES (417, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 16:09:50');
INSERT INTO `sys_oper_log` VALUES (418, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 16:11:13');
INSERT INTO `sys_oper_log` VALUES (419, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/f12993b0026244d4afd23207eb86326d', '192.168.0.18', '内网IP', '{}', '{\"code\":50000,\"msg\":\"删除失败\"}', 0, NULL, '2023-02-10 16:11:16');
INSERT INTO `sys_oper_log` VALUES (420, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 16:11:19');
INSERT INTO `sys_oper_log` VALUES (421, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/f12993b0026244d4afd23207eb86326d', '192.168.0.18', '内网IP', '{}', '{\"code\":50000,\"msg\":\"删除失败\"}', 0, NULL, '2023-02-10 16:11:30');
INSERT INTO `sys_oper_log` VALUES (422, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/f12993b0026244d4afd23207eb86326d', '192.168.0.18', '内网IP', '{}', '{\"code\":50000,\"msg\":\"删除失败\"}', 0, NULL, '2023-02-10 16:12:29');
INSERT INTO `sys_oper_log` VALUES (423, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/f12993b0026244d4afd23207eb86326d', '192.168.0.18', '内网IP', '{}', '{\"code\":50000,\"msg\":\"删除失败\"}', 0, NULL, '2023-02-10 16:18:13');
INSERT INTO `sys_oper_log` VALUES (424, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 16:26:13');
INSERT INTO `sys_oper_log` VALUES (425, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/f12993b0026244d4afd23207eb86326d', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:26:17');
INSERT INTO `sys_oper_log` VALUES (426, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 16:26:32');
INSERT INTO `sys_oper_log` VALUES (427, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 16:26:36.582\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"11\",\"id\":\"c6d681384c424063ba3979a104d28070\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 16:26:39');
INSERT INTO `sys_oper_log` VALUES (428, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/c6d681384c424063ba3979a104d28070', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:26:41');
INSERT INTO `sys_oper_log` VALUES (429, '用户表格转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictTypeController.queryDictType()', 'GET', 1, 'admin', NULL, '/system/dictType/queryDictType', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"核查项-档案类型\",\"value\":\"100\"},{\"label\":\"测试2\",\"value\":\"f5ac542edcc3436a8b96f146a9e8e800\"}]', 0, NULL, '2023-02-10 16:27:24');
INSERT INTO `sys_oper_log` VALUES (430, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 16:27:24.094\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"111\",\"id\":\"33fbf78c46f34e47846b3d30ebe3ac00\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 16:27:27');
INSERT INTO `sys_oper_log` VALUES (431, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/33fbf78c46f34e47846b3d30ebe3ac00', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:27:29');
INSERT INTO `sys_oper_log` VALUES (432, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 16:27:34.66\",\"dictType\":\"100\",\"eparentCode\":\"-\",\"fullName\":\"123\",\"id\":\"59b1a9fecf624695b18485e89109e34e\",\"params\":{},\"type\":0}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 16:27:37');
INSERT INTO `sys_oper_log` VALUES (433, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dictData/add', '192.168.0.18', '内网IP', '{\"codeProperty\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-02-10 16:27:38.437\",\"dictType\":\"100\",\"eparentCode\":\"59b1a9fecf624695b18485e89109e34e\",\"fullName\":\"3213\",\"id\":\"2b9a7ab704c8467c8e0b93ae70ab3f90\",\"params\":{},\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-02-10 16:27:41');
INSERT INTO `sys_oper_log` VALUES (434, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dictData/59b1a9fecf624695b18485e89109e34e', '192.168.0.18', '内网IP', '{}', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:27:44');
INSERT INTO `sys_oper_log` VALUES (435, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"删除三级\",\"value\":\"98377fae2d5d4f4cb468a279449b6011\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"删除三级\",\"value\":\"be6b6926bef6444a906e6c9adce50e1d\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 16:32:22');
INSERT INTO `sys_oper_log` VALUES (436, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 14:52:19\",\"examineStas\":[{\"value\":\"测试\"}],\"examineStasString\":\"\",\"id\":\"1\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":50000,\"msg\":\"重复核查项名称\"}', 0, NULL, '2023-02-10 16:32:38');
INSERT INTO `sys_oper_log` VALUES (437, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 14:52:19\",\"examineStas\":[{\"value\":\"测试\"}],\"examineStasString\":\"\",\"id\":\"1\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":50000,\"msg\":\"重复核查项名称\"}', 0, NULL, '2023-02-10 16:32:41');
INSERT INTO `sys_oper_log` VALUES (438, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"删除三级\",\"value\":\"98377fae2d5d4f4cb468a279449b6011\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"删除三级\",\"value\":\"be6b6926bef6444a906e6c9adce50e1d\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 16:33:40');
INSERT INTO `sys_oper_log` VALUES (439, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 14:52:19\",\"examineStas\":[{\"value\":\"221\"}],\"examineStasString\":\"\",\"id\":\"1\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-10 16:33:49.033\"}', '{\"code\":50000,\"msg\":\"修改失败\"}', 0, NULL, '2023-02-10 16:33:52');
INSERT INTO `sys_oper_log` VALUES (440, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 14:52:19\",\"examineStas\":[{\"value\":\"221\"}],\"examineStasString\":\"\",\"id\":\"1\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-10 16:34:16.178\"}', '{\"code\":50000,\"msg\":\"修改失败\"}', 0, NULL, '2023-02-10 16:34:27');
INSERT INTO `sys_oper_log` VALUES (441, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 14:52:19\",\"examineStas\":[{\"value\":\"221\"}],\"examineStasString\":\"\",\"id\":\"1\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-10 16:34:33.305\"}', '{\"code\":50000,\"msg\":\"修改失败\"}', 0, NULL, '2023-02-10 16:34:59');
INSERT INTO `sys_oper_log` VALUES (442, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-09 14:52:19\",\"examineStas\":[{\"value\":\"221\"}],\"examineStasString\":\"\",\"id\":\"1\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"0\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-10 16:35:01.146\"}', '{\"code\":50000,\"msg\":\"修改失败\"}', 0, NULL, '2023-02-10 16:35:17');
INSERT INTO `sys_oper_log` VALUES (443, '【请填写功能名称】', 3, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.remove()', 'POST', 1, 'admin', NULL, '/trueing/management/remove/1,bbf091234f294352890f4021a0ad8276', '192.168.0.18', '内网IP', '[\"1\",\"bbf091234f294352890f4021a0ad8276\"]', '{\"code\":20000,\"data\":\"删除成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:36:03');
INSERT INTO `sys_oper_log` VALUES (444, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 16:36:11.717\",\"examineStas\":[{\"value\":\"测试\"}],\"id\":\"e92eb78f9412442fbc01df9b5f3077a1\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:36:14');
INSERT INTO `sys_oper_log` VALUES (445, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 16:36:12\",\"examineStas\":[{\"value\":\"测试\"}],\"examineStasString\":\"测试/\",\"id\":\"e92eb78f9412442fbc01df9b5f3077a1\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-10 16:36:20.7\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:36:23');
INSERT INTO `sys_oper_log` VALUES (446, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"examineStas\":[{\"value\":\"测试\"}],\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":50000,\"msg\":\"重复核查项名称\"}', 0, NULL, '2023-02-10 16:36:42');
INSERT INTO `sys_oper_log` VALUES (447, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 16:36:41.301\",\"examineStas\":[{\"value\":\"测试\"}],\"id\":\"ef7da8ee1dc94bf2bf691f077fdf7eb8\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项1\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:36:44');
INSERT INTO `sys_oper_log` VALUES (448, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 16:36:41\",\"examineStas\":[{\"value\":\"测试\"}],\"examineStasString\":\"测试/\",\"id\":\"ef7da8ee1dc94bf2bf691f077fdf7eb8\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"核查项\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":50000,\"msg\":\"重复核查项名称\"}', 0, NULL, '2023-02-10 16:37:09');
INSERT INTO `sys_oper_log` VALUES (449, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"删除三级\",\"value\":\"98377fae2d5d4f4cb468a279449b6011\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"删除三级\",\"value\":\"be6b6926bef6444a906e6c9adce50e1d\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 16:37:38');
INSERT INTO `sys_oper_log` VALUES (450, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"删除三级\",\"value\":\"98377fae2d5d4f4cb468a279449b6011\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"删除三级\",\"value\":\"be6b6926bef6444a906e6c9adce50e1d\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 16:38:04');
INSERT INTO `sys_oper_log` VALUES (451, '【请填写功能名称】', 1, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.add()', 'POST', 1, 'admin', NULL, '/trueing/management/add', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 16:38:18.164\",\"examineStas\":[{\"value\":\"测试\"}],\"id\":\"5de3dc3c6d50406a8f20a3bc009a62ba\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\"}', '{\"code\":20000,\"data\":\"新增成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:38:21');
INSERT INTO `sys_oper_log` VALUES (452, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 16:38:18\",\"examineStas\":[{\"value\":\"测试\"},{\"value\":\"测试2\",\"key\":\"1676018306754\"}],\"examineStasString\":\"测试/\",\"id\":\"5de3dc3c6d50406a8f20a3bc009a62ba\",\"params\":{},\"showPageNumber\":\"1\",\"showPiece\":\"1\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-10 16:38:29.638\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:38:32');
INSERT INTO `sys_oper_log` VALUES (453, '【请填写功能名称】', 2, 'com.fudian.dahc.controller.trueingManage.DahcHcxTrueingManagementController.edit()', 'POST', 1, 'admin', NULL, '/trueing/management/update', '192.168.0.18', '内网IP', '{\"createTime\":\"2023-02-10 16:38:18\",\"examineStas\":[{\"value\":\"测试2\"},{\"value\":\"测试\"}],\"examineStasString\":\"测试2/测试/\",\"id\":\"5de3dc3c6d50406a8f20a3bc009a62ba\",\"params\":{},\"showPageNumber\":\"0\",\"showPiece\":\"0\",\"showRecord\":\"1\",\"tableType\":\"\",\"trueingDesc\":\"\",\"trueingName\":\"测试\",\"trueingRemark\":\"\",\"trueingScopeStair\":\"12345895\",\"trueingTreeRank\":2,\"trueingType\":\"\",\"updateTime\":\"2023-02-10 16:38:35.334\"}', '{\"code\":20000,\"data\":\"修改成功\",\"msg\":\"操作成功\"}', 0, NULL, '2023-02-10 16:38:38');
INSERT INTO `sys_oper_log` VALUES (454, '表格字段字典转换', 3, 'com.fudian.dahc.controller.sys.DahcSysDictDataController.queryDictDataTransition()', 'GET', 1, 'admin', NULL, '/system/dictData/queryDictDataTransition', '192.168.0.18', '内网IP', '{}', '[{\"label\":\"三级节点-01\",\"value\":\"123456\"},{\"label\":\"档案盒编辑\",\"value\":\"12345895\"},{\"label\":\"备考表\",\"value\":\"12345895213\"},{\"label\":\"删除三级\",\"value\":\"98377fae2d5d4f4cb468a279449b6011\"},{\"label\":\"备考表2\",\"value\":\"9f1f66e02c26434eb6412382915c3e00\"},{\"label\":\"删除三级\",\"value\":\"be6b6926bef6444a906e6c9adce50e1d\"},{\"label\":\"编辑测试\",\"value\":\"d7405e2a2453411da14b85f57cc8abd3\"}]', 0, NULL, '2023-02-10 16:40:58');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(0) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2023-01-10 10:08:52', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2023-01-10 10:08:52', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2023-01-10 10:08:52', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2023-01-10 10:08:52', '', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(0) NULL DEFAULT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2023-01-10 10:08:54', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2023-01-10 10:08:54', 'admin', '2023-02-07 16:39:44', '普通角色');
INSERT INTO `sys_role` VALUES (3, '测试角色', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2023-01-10 10:08:54', '', '2023-02-07 16:39:10', '普通角色');
INSERT INTO `sys_role` VALUES (105, '测试1', 'com', NULL, '1', 0, 0, '0', '2', 'admin', '2023-02-08 10:03:55', '', NULL, NULL);
INSERT INTO `sys_role` VALUES (106, '测试1', 'com11', NULL, '1', 0, 0, '1', '0', 'admin', '2023-02-08 10:04:12', 'admin', '2023-02-08 10:08:25', NULL);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(0) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);
INSERT INTO `sys_role_dept` VALUES (3, 100);
INSERT INTO `sys_role_dept` VALUES (3, 101);
INSERT INTO `sys_role_dept` VALUES (3, 102);
INSERT INTO `sys_role_dept` VALUES (3, 103);
INSERT INTO `sys_role_dept` VALUES (3, 104);
INSERT INTO `sys_role_dept` VALUES (3, 105);
INSERT INTO `sys_role_dept` VALUES (3, 106);
INSERT INTO `sys_role_dept` VALUES (3, 107);
INSERT INTO `sys_role_dept` VALUES (3, 108);
INSERT INTO `sys_role_dept` VALUES (3, 109);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (2, 2001);
INSERT INTO `sys_role_menu` VALUES (2, 2002);
INSERT INTO `sys_role_menu` VALUES (2, 2003);
INSERT INTO `sys_role_menu` VALUES (2, 2004);
INSERT INTO `sys_role_menu` VALUES (2, 2005);
INSERT INTO `sys_role_menu` VALUES (2, 2006);
INSERT INTO `sys_role_menu` VALUES (2, 2007);
INSERT INTO `sys_role_menu` VALUES (2, 2008);
INSERT INTO `sys_role_menu` VALUES (2, 2009);
INSERT INTO `sys_role_menu` VALUES (106, 2001);
INSERT INTO `sys_role_menu` VALUES (106, 2002);
INSERT INTO `sys_role_menu` VALUES (106, 2003);
INSERT INTO `sys_role_menu` VALUES (106, 2004);
INSERT INTO `sys_role_menu` VALUES (106, 2005);
INSERT INTO `sys_role_menu` VALUES (106, 2006);
INSERT INTO `sys_role_menu` VALUES (106, 2007);
INSERT INTO `sys_role_menu` VALUES (106, 2008);
INSERT INTO `sys_role_menu` VALUES (106, 2009);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '192.168.0.10', '2023-02-10 16:51:49', 'admin', '2023-01-10 10:08:51', '', '2023-02-10 16:51:51', '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2023-02-07 15:01:35', 'admin', '2023-01-10 10:08:51', '', '2023-02-07 15:01:34', '测试员');
INSERT INTO `sys_user` VALUES (100, 103, '马超宇', '马超宇', '00', '', '', '0', '', '$2a$10$rJ6Ae7reMnI6Oqu/iLCwcO1PxvixhDOEX2vBO2wJ7dVkVuWdbfpI6', '0', '0', '127.0.0.1', '2023-02-07 17:54:14', 'admin', '2023-02-02 16:56:08', 'admin', '2023-02-07 17:54:17', NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `post_id` bigint(0) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_process
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_process`;
CREATE TABLE `sys_user_process`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `process_id` bigint(0) NOT NULL COMMENT '工序id\r\n',
  PRIMARY KEY (`user_id`, `process_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户关联工序' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_process
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (100, 2);
INSERT INTO `sys_user_role` VALUES (100, 3);

SET FOREIGN_KEY_CHECKS = 1;
