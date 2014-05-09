/*
Navicat MySQL Data Transfer

Source Server         : hbm
Source Server Version : 50523
Source Host           : localhost:3306
Source Database       : wfcsu

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2012-12-22 15:43:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `wf_attendance`
-- ----------------------------
DROP TABLE IF EXISTS `wf_attendance`;
CREATE TABLE `wf_attendance` (
  `ATTENDANCE_NO` int(10) NOT NULL AUTO_INCREMENT,
  `CLIENT_NAME` char(10) DEFAULT NULL,
  `CLIENT_ADDRESS_A` char(10) DEFAULT NULL,
  `CLIENT_ADDRESS_B` char(10) DEFAULT NULL,
  `CLIENT_ADDRESS_C` char(10) DEFAULT NULL,
  `REGISTER_TIME` datetime DEFAULT NULL,
  `PROBLEM_DESCRIBE` text,
  `COMPUTER_TYPE` char(10) DEFAULT NULL,
  `SYSTEM_TYPE` char(10) DEFAULT NULL,
  `DUTY_MEMBER` char(5) DEFAULT NULL,
  `ATTEN_MEMBER1` char(5) DEFAULT NULL,
  `ATTEN_MEMBER2` char(5) DEFAULT NULL,
  `SOLUTION` text,
  `BACK_TIME` datetime DEFAULT NULL,
  `BACK_DUTY_MEMBER` char(5) DEFAULT NULL,
  `ATTEN_STATE` char(10) DEFAULT NULL,
  `ATTEN_TYPE` char(10) DEFAULT NULL,
  `ATTEN_REMARK` text,
  `CLIENT_PHONE` char(20) DEFAULT NULL,
  `GET_MEMBER` char(5) DEFAULT NULL,
  `GET_TIME` datetime DEFAULT NULL,
  `MARK` int(11) DEFAULT NULL,
  PRIMARY KEY (`ATTENDANCE_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `wf_duty`
-- ----------------------------
DROP TABLE IF EXISTS `wf_duty`;
CREATE TABLE `wf_duty` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DUTY_DATE` date DEFAULT NULL,
  `DUTY_ORDER` char(2) DEFAULT NULL,
  `DUTY_MEMBER` char(5) DEFAULT NULL,
  `SIGNIN_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `wf_extrawork`
-- ----------------------------
DROP TABLE IF EXISTS `wf_extrawork`;
CREATE TABLE `wf_extrawork` (
  `EXTRA_ID` int(5) NOT NULL AUTO_INCREMENT,
  `EXTRA_ADD_MEMBER` char(5) DEFAULT NULL,
  `EXTRA_NO` char(5) NOT NULL DEFAULT '',
  `EXTRA_DATE1` datetime DEFAULT NULL,
  `EXTRA_DATE2` datetime DEFAULT NULL,
  `EXTRA_HOURS` float(4,1) DEFAULT NULL,
  `EXTRA_CONTENT` text,
  `EXTRA_STATE` char(10) DEFAULT NULL,
  `VERIFY_NO` char(5) DEFAULT NULL,
  `QUERY_ID` char(50) DEFAULT NULL,
  `EXTRA_MEMBERS_NAME` char(100) DEFAULT NULL,
  PRIMARY KEY (`EXTRA_ID`,`EXTRA_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=326 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `wf_member`
-- ----------------------------
DROP TABLE IF EXISTS `wf_member`;
CREATE TABLE `wf_member` (
  `MEMBER_NO` char(10) NOT NULL DEFAULT '',
  `MEMBER_NAME` char(20) DEFAULT NULL,
  `MEMBER_PSW` char(10) DEFAULT 'wfcsu',
  `MEMBER_CLASS` char(20) DEFAULT NULL,
  `MEMBER_PHONE` char(15) DEFAULT NULL,
  `MEMBER_EMAIL` char(50) DEFAULT NULL,
  `MEMBER_QQ` char(50) DEFAULT NULL,
  `MEMBER_BIRTH` char(20) DEFAULT NULL,
  `MEMBER_ROOM` char(20) DEFAULT NULL,
  `MEMBER_HOME` char(50) DEFAULT NULL,
  `MEMBER_PERMISSION` char(1) DEFAULT NULL,
  `MEMBER_STATE` char(10) DEFAULT NULL,
  PRIMARY KEY (`MEMBER_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `wf_option`
-- ----------------------------
DROP TABLE IF EXISTS `wf_option`;
CREATE TABLE `wf_option` (
  `ID` int(5) NOT NULL AUTO_INCREMENT,
  `OPTION_NAME` char(20) DEFAULT NULL,
  `OPTION_VALUE` char(20) DEFAULT NULL,
  `OPTION_VALUE2` char(20) DEFAULT NULL,
  `OPTION_VALUE3` char(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for `wf_salary`
-- ----------------------------
DROP TABLE IF EXISTS `wf_salary`;
CREATE TABLE `wf_salary` (
  `SALARY_ID` int(10) NOT NULL DEFAULT '0',
  `DUTY_SALARY` float(2,2) DEFAULT NULL,
  `EXTRA _SALARY` float(2,2) DEFAULT NULL,
  `ATTEN_SALARY` float(2,2) DEFAULT NULL,
  `AWARD_SALARY` float(2,2) DEFAULT NULL,
  `TOTAL_SALARY` float(2,2) DEFAULT NULL,
  PRIMARY KEY (`SALARY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wf_salary
-- ----------------------------
