/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50716
 Source Host           : localhost
 Source Database       : fetch

 Target Server Version : 50716
 File Encoding         : utf-8

 Date: 08/02/2017 10:00:45 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `f_content`
-- ----------------------------
DROP TABLE IF EXISTS `f_content`;
CREATE TABLE `f_content` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `title` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '标题',
  `posting_date` date DEFAULT NULL COMMENT '发文日期',
  `reference_num` varchar(60) CHARACTER SET utf8 DEFAULT NULL COMMENT '文号',
  `tax_kind` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '税种',
  `content` mediumtext CHARACTER SET utf8 COMMENT '文章内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
