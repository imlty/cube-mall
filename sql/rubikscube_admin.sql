/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : rubikscube_admin

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 18/04/2021 22:11:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', NULL, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000176FFB639487874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('RenrenScheduler', 'XiaodongLiu1618753327733', 1618755086756, 15000);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', NULL, 1618756200000, 1618754400000, 5, 'WAITING', 'CRON', 1610608935000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000176FFB639487874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
COMMIT;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
BEGIN;
INSERT INTO `schedule_job` VALUES (1, 'testTask', 'renren', '0 0/30 * * * ?', 0, '参数测试', '2021-01-14 15:05:01');
COMMIT;

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
BEGIN;
INSERT INTO `schedule_job_log` VALUES (1, 1, 'testTask', 'renren', 0, NULL, 0, '2021-01-14 15:30:07');
INSERT INTO `schedule_job_log` VALUES (2, 1, 'testTask', 'renren', 0, NULL, 2, '2021-01-14 16:00:00');
INSERT INTO `schedule_job_log` VALUES (3, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-15 15:00:00');
INSERT INTO `schedule_job_log` VALUES (4, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-17 11:00:00');
INSERT INTO `schedule_job_log` VALUES (5, 1, 'testTask', 'renren', 0, NULL, 3, '2021-03-17 11:30:00');
INSERT INTO `schedule_job_log` VALUES (6, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-17 14:30:00');
INSERT INTO `schedule_job_log` VALUES (7, 1, 'testTask', 'renren', 0, NULL, 4, '2021-03-17 15:00:00');
INSERT INTO `schedule_job_log` VALUES (8, 1, 'testTask', 'renren', 0, NULL, 10, '2021-03-17 15:30:00');
INSERT INTO `schedule_job_log` VALUES (9, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-17 16:00:00');
INSERT INTO `schedule_job_log` VALUES (10, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-17 16:30:00');
INSERT INTO `schedule_job_log` VALUES (11, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-17 17:00:00');
INSERT INTO `schedule_job_log` VALUES (12, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-17 17:30:00');
INSERT INTO `schedule_job_log` VALUES (13, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-17 18:00:00');
INSERT INTO `schedule_job_log` VALUES (14, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-17 18:30:00');
INSERT INTO `schedule_job_log` VALUES (15, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-17 19:00:00');
INSERT INTO `schedule_job_log` VALUES (16, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-17 19:30:00');
INSERT INTO `schedule_job_log` VALUES (17, 1, 'testTask', 'renren', 0, NULL, 4, '2021-03-18 09:30:00');
INSERT INTO `schedule_job_log` VALUES (18, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-18 10:00:00');
INSERT INTO `schedule_job_log` VALUES (19, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 10:30:00');
INSERT INTO `schedule_job_log` VALUES (20, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 11:00:00');
INSERT INTO `schedule_job_log` VALUES (21, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-18 12:00:00');
INSERT INTO `schedule_job_log` VALUES (22, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 13:00:00');
INSERT INTO `schedule_job_log` VALUES (23, 1, 'testTask', 'renren', 0, NULL, 4, '2021-03-18 13:30:00');
INSERT INTO `schedule_job_log` VALUES (24, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-18 14:00:00');
INSERT INTO `schedule_job_log` VALUES (25, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 14:30:00');
INSERT INTO `schedule_job_log` VALUES (26, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 15:00:00');
INSERT INTO `schedule_job_log` VALUES (27, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 15:30:00');
INSERT INTO `schedule_job_log` VALUES (28, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-18 16:00:00');
INSERT INTO `schedule_job_log` VALUES (29, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 16:30:00');
INSERT INTO `schedule_job_log` VALUES (30, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 17:00:00');
INSERT INTO `schedule_job_log` VALUES (31, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-18 17:30:00');
INSERT INTO `schedule_job_log` VALUES (32, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-18 18:00:00');
INSERT INTO `schedule_job_log` VALUES (33, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-18 18:30:00');
INSERT INTO `schedule_job_log` VALUES (34, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 19:00:00');
INSERT INTO `schedule_job_log` VALUES (35, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-18 19:59:00');
INSERT INTO `schedule_job_log` VALUES (36, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-18 22:00:00');
INSERT INTO `schedule_job_log` VALUES (37, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-19 10:00:00');
INSERT INTO `schedule_job_log` VALUES (38, 1, 'testTask', 'renren', 0, NULL, 4, '2021-03-19 15:00:00');
INSERT INTO `schedule_job_log` VALUES (39, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-19 15:30:00');
INSERT INTO `schedule_job_log` VALUES (40, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-19 16:00:00');
INSERT INTO `schedule_job_log` VALUES (41, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-19 16:30:00');
INSERT INTO `schedule_job_log` VALUES (42, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-19 17:00:00');
INSERT INTO `schedule_job_log` VALUES (43, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-19 17:30:00');
INSERT INTO `schedule_job_log` VALUES (44, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-19 18:00:00');
INSERT INTO `schedule_job_log` VALUES (45, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-19 18:30:00');
INSERT INTO `schedule_job_log` VALUES (46, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-19 19:00:00');
INSERT INTO `schedule_job_log` VALUES (47, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-19 23:39:17');
INSERT INTO `schedule_job_log` VALUES (48, 1, 'testTask', 'renren', 0, NULL, 4, '2021-03-20 00:39:19');
INSERT INTO `schedule_job_log` VALUES (49, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-20 01:00:00');
INSERT INTO `schedule_job_log` VALUES (50, 1, 'testTask', 'renren', 0, NULL, 26, '2021-03-20 20:00:00');
INSERT INTO `schedule_job_log` VALUES (51, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-20 22:00:00');
INSERT INTO `schedule_job_log` VALUES (52, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-20 23:00:00');
INSERT INTO `schedule_job_log` VALUES (53, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-20 23:30:00');
INSERT INTO `schedule_job_log` VALUES (54, 1, 'testTask', 'renren', 0, NULL, 5, '2021-03-21 00:00:00');
INSERT INTO `schedule_job_log` VALUES (55, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-21 00:30:00');
INSERT INTO `schedule_job_log` VALUES (56, 1, 'testTask', 'renren', 0, NULL, 4, '2021-03-21 15:30:00');
INSERT INTO `schedule_job_log` VALUES (57, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-21 16:00:00');
INSERT INTO `schedule_job_log` VALUES (58, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-22 12:00:00');
INSERT INTO `schedule_job_log` VALUES (59, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-22 12:30:00');
INSERT INTO `schedule_job_log` VALUES (60, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-22 13:00:00');
INSERT INTO `schedule_job_log` VALUES (61, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-22 13:30:00');
INSERT INTO `schedule_job_log` VALUES (62, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-22 14:00:00');
INSERT INTO `schedule_job_log` VALUES (63, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-22 14:30:00');
INSERT INTO `schedule_job_log` VALUES (64, 1, 'testTask', 'renren', 0, NULL, 6, '2021-03-22 15:00:00');
INSERT INTO `schedule_job_log` VALUES (65, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-22 15:30:00');
INSERT INTO `schedule_job_log` VALUES (66, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-22 16:00:00');
INSERT INTO `schedule_job_log` VALUES (67, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-22 16:30:00');
INSERT INTO `schedule_job_log` VALUES (68, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-22 17:30:00');
INSERT INTO `schedule_job_log` VALUES (69, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-22 18:00:00');
INSERT INTO `schedule_job_log` VALUES (70, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-22 18:30:00');
INSERT INTO `schedule_job_log` VALUES (71, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-22 19:00:00');
INSERT INTO `schedule_job_log` VALUES (72, 1, 'testTask', 'renren', 0, NULL, 5, '2021-03-22 19:30:00');
INSERT INTO `schedule_job_log` VALUES (73, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-22 20:00:00');
INSERT INTO `schedule_job_log` VALUES (74, 1, 'testTask', 'renren', 0, NULL, 4, '2021-03-23 10:00:00');
INSERT INTO `schedule_job_log` VALUES (75, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 11:30:00');
INSERT INTO `schedule_job_log` VALUES (76, 1, 'testTask', 'renren', 0, NULL, 4, '2021-03-23 12:00:00');
INSERT INTO `schedule_job_log` VALUES (77, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 12:30:00');
INSERT INTO `schedule_job_log` VALUES (78, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 13:00:00');
INSERT INTO `schedule_job_log` VALUES (79, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 13:30:00');
INSERT INTO `schedule_job_log` VALUES (80, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 14:00:00');
INSERT INTO `schedule_job_log` VALUES (81, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 14:30:00');
INSERT INTO `schedule_job_log` VALUES (82, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-23 15:00:00');
INSERT INTO `schedule_job_log` VALUES (83, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-23 15:30:00');
INSERT INTO `schedule_job_log` VALUES (84, 1, 'testTask', 'renren', 0, NULL, 3, '2021-03-23 16:00:00');
INSERT INTO `schedule_job_log` VALUES (85, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 16:30:00');
INSERT INTO `schedule_job_log` VALUES (86, 1, 'testTask', 'renren', 0, NULL, 3, '2021-03-23 17:00:00');
INSERT INTO `schedule_job_log` VALUES (87, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-23 17:30:00');
INSERT INTO `schedule_job_log` VALUES (88, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 18:00:00');
INSERT INTO `schedule_job_log` VALUES (89, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 18:30:00');
INSERT INTO `schedule_job_log` VALUES (90, 1, 'testTask', 'renren', 0, NULL, 7, '2021-03-23 19:00:24');
INSERT INTO `schedule_job_log` VALUES (91, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-23 22:00:00');
INSERT INTO `schedule_job_log` VALUES (92, 1, 'testTask', 'renren', 0, NULL, 3, '2021-03-24 10:00:00');
INSERT INTO `schedule_job_log` VALUES (93, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-24 10:30:00');
INSERT INTO `schedule_job_log` VALUES (94, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-24 11:00:00');
INSERT INTO `schedule_job_log` VALUES (95, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-24 11:30:00');
INSERT INTO `schedule_job_log` VALUES (96, 1, 'testTask', 'renren', 0, NULL, 4, '2021-03-24 12:00:00');
INSERT INTO `schedule_job_log` VALUES (97, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-24 12:30:00');
INSERT INTO `schedule_job_log` VALUES (98, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-24 13:00:00');
INSERT INTO `schedule_job_log` VALUES (99, 1, 'testTask', 'renren', 0, NULL, 0, '2021-03-24 13:30:00');
INSERT INTO `schedule_job_log` VALUES (100, 1, 'testTask', 'renren', 0, NULL, 2, '2021-03-24 14:00:00');
INSERT INTO `schedule_job_log` VALUES (101, 1, 'testTask', 'renren', 0, NULL, 1, '2021-03-25 10:30:00');
INSERT INTO `schedule_job_log` VALUES (102, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-02 11:30:00');
INSERT INTO `schedule_job_log` VALUES (103, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-02 12:00:00');
INSERT INTO `schedule_job_log` VALUES (104, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-02 12:30:00');
INSERT INTO `schedule_job_log` VALUES (105, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-02 13:00:00');
INSERT INTO `schedule_job_log` VALUES (106, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-02 13:30:00');
INSERT INTO `schedule_job_log` VALUES (107, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-02 14:00:00');
INSERT INTO `schedule_job_log` VALUES (108, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-02 14:30:00');
INSERT INTO `schedule_job_log` VALUES (109, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-02 15:00:00');
INSERT INTO `schedule_job_log` VALUES (110, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-02 15:30:00');
INSERT INTO `schedule_job_log` VALUES (111, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-02 16:00:00');
INSERT INTO `schedule_job_log` VALUES (112, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-02 16:30:00');
INSERT INTO `schedule_job_log` VALUES (113, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-02 17:00:00');
INSERT INTO `schedule_job_log` VALUES (114, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-06 12:00:00');
INSERT INTO `schedule_job_log` VALUES (115, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-06 12:30:00');
INSERT INTO `schedule_job_log` VALUES (116, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-06 13:00:00');
INSERT INTO `schedule_job_log` VALUES (117, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-06 13:30:00');
INSERT INTO `schedule_job_log` VALUES (118, 1, 'testTask', 'renren', 0, NULL, 5, '2021-04-06 14:00:00');
INSERT INTO `schedule_job_log` VALUES (119, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-06 15:00:00');
INSERT INTO `schedule_job_log` VALUES (120, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-06 16:30:00');
INSERT INTO `schedule_job_log` VALUES (121, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-06 17:30:00');
INSERT INTO `schedule_job_log` VALUES (122, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-06 18:00:00');
INSERT INTO `schedule_job_log` VALUES (123, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-07 12:00:00');
INSERT INTO `schedule_job_log` VALUES (124, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-07 12:30:00');
INSERT INTO `schedule_job_log` VALUES (125, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-07 13:00:00');
INSERT INTO `schedule_job_log` VALUES (126, 1, 'testTask', 'renren', 0, NULL, 4, '2021-04-07 13:30:00');
INSERT INTO `schedule_job_log` VALUES (127, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-07 14:00:00');
INSERT INTO `schedule_job_log` VALUES (128, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-07 14:30:00');
INSERT INTO `schedule_job_log` VALUES (129, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-08 14:30:00');
INSERT INTO `schedule_job_log` VALUES (130, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-08 15:00:00');
INSERT INTO `schedule_job_log` VALUES (131, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-08 15:30:00');
INSERT INTO `schedule_job_log` VALUES (132, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 10:30:00');
INSERT INTO `schedule_job_log` VALUES (133, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-09 11:00:00');
INSERT INTO `schedule_job_log` VALUES (134, 1, 'testTask', 'renren', 0, NULL, 9, '2021-04-09 11:30:00');
INSERT INTO `schedule_job_log` VALUES (135, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 12:00:00');
INSERT INTO `schedule_job_log` VALUES (136, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 12:30:00');
INSERT INTO `schedule_job_log` VALUES (137, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-09 13:00:00');
INSERT INTO `schedule_job_log` VALUES (138, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-09 13:30:00');
INSERT INTO `schedule_job_log` VALUES (139, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-09 14:00:00');
INSERT INTO `schedule_job_log` VALUES (140, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-09 14:30:00');
INSERT INTO `schedule_job_log` VALUES (141, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 15:00:00');
INSERT INTO `schedule_job_log` VALUES (142, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 15:30:00');
INSERT INTO `schedule_job_log` VALUES (143, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 16:00:00');
INSERT INTO `schedule_job_log` VALUES (144, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-09 16:30:00');
INSERT INTO `schedule_job_log` VALUES (145, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 17:00:00');
INSERT INTO `schedule_job_log` VALUES (146, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 17:30:00');
INSERT INTO `schedule_job_log` VALUES (147, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-09 18:00:00');
INSERT INTO `schedule_job_log` VALUES (148, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 18:30:00');
INSERT INTO `schedule_job_log` VALUES (149, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-09 19:00:00');
INSERT INTO `schedule_job_log` VALUES (150, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-09 22:30:00');
INSERT INTO `schedule_job_log` VALUES (151, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-09 23:00:00');
INSERT INTO `schedule_job_log` VALUES (152, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-09 23:30:00');
INSERT INTO `schedule_job_log` VALUES (153, 1, 'testTask', 'renren', 0, NULL, 5, '2021-04-10 00:00:00');
INSERT INTO `schedule_job_log` VALUES (154, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-10 00:30:00');
INSERT INTO `schedule_job_log` VALUES (155, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-10 01:00:00');
INSERT INTO `schedule_job_log` VALUES (156, 1, 'testTask', 'renren', 0, NULL, 4, '2021-04-10 10:00:00');
INSERT INTO `schedule_job_log` VALUES (157, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-10 10:30:00');
INSERT INTO `schedule_job_log` VALUES (158, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-10 11:00:00');
INSERT INTO `schedule_job_log` VALUES (159, 1, 'testTask', 'renren', 0, NULL, 5, '2021-04-10 11:30:00');
INSERT INTO `schedule_job_log` VALUES (160, 1, 'testTask', 'renren', 0, NULL, 4, '2021-04-13 12:30:00');
INSERT INTO `schedule_job_log` VALUES (161, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-13 13:00:00');
INSERT INTO `schedule_job_log` VALUES (162, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-13 13:30:00');
INSERT INTO `schedule_job_log` VALUES (163, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-13 14:00:00');
INSERT INTO `schedule_job_log` VALUES (164, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-13 14:30:00');
INSERT INTO `schedule_job_log` VALUES (165, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-13 15:00:00');
INSERT INTO `schedule_job_log` VALUES (166, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-13 15:30:00');
INSERT INTO `schedule_job_log` VALUES (167, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-13 16:00:00');
INSERT INTO `schedule_job_log` VALUES (168, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-13 16:30:00');
INSERT INTO `schedule_job_log` VALUES (169, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-13 17:00:00');
INSERT INTO `schedule_job_log` VALUES (170, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-13 17:30:00');
INSERT INTO `schedule_job_log` VALUES (171, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-13 20:00:00');
INSERT INTO `schedule_job_log` VALUES (172, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-13 20:30:05');
INSERT INTO `schedule_job_log` VALUES (173, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-14 03:00:00');
INSERT INTO `schedule_job_log` VALUES (174, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-14 10:00:00');
INSERT INTO `schedule_job_log` VALUES (175, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-14 10:30:00');
INSERT INTO `schedule_job_log` VALUES (176, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-14 11:00:00');
INSERT INTO `schedule_job_log` VALUES (177, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-14 12:30:00');
INSERT INTO `schedule_job_log` VALUES (178, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-14 13:00:00');
INSERT INTO `schedule_job_log` VALUES (179, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-14 13:30:00');
INSERT INTO `schedule_job_log` VALUES (180, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-14 14:00:00');
INSERT INTO `schedule_job_log` VALUES (181, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-14 14:30:00');
INSERT INTO `schedule_job_log` VALUES (182, 1, 'testTask', 'renren', 0, NULL, 7, '2021-04-14 15:00:00');
INSERT INTO `schedule_job_log` VALUES (183, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-14 15:30:00');
INSERT INTO `schedule_job_log` VALUES (184, 1, 'testTask', 'renren', 0, NULL, 4, '2021-04-14 16:00:00');
INSERT INTO `schedule_job_log` VALUES (185, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-14 16:30:00');
INSERT INTO `schedule_job_log` VALUES (186, 1, 'testTask', 'renren', 0, NULL, 6, '2021-04-14 17:00:00');
INSERT INTO `schedule_job_log` VALUES (187, 1, 'testTask', 'renren', 0, NULL, 4, '2021-04-14 17:30:00');
INSERT INTO `schedule_job_log` VALUES (188, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-14 18:00:00');
INSERT INTO `schedule_job_log` VALUES (189, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-14 18:30:00');
INSERT INTO `schedule_job_log` VALUES (190, 1, 'testTask', 'renren', 0, NULL, 4, '2021-04-15 08:30:00');
INSERT INTO `schedule_job_log` VALUES (191, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-15 09:00:00');
INSERT INTO `schedule_job_log` VALUES (192, 1, 'testTask', 'renren', 0, NULL, 4, '2021-04-15 10:30:00');
INSERT INTO `schedule_job_log` VALUES (193, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-15 11:00:00');
INSERT INTO `schedule_job_log` VALUES (194, 1, 'testTask', 'renren', 0, NULL, 11, '2021-04-15 11:30:00');
INSERT INTO `schedule_job_log` VALUES (195, 1, 'testTask', 'renren', 0, NULL, 4, '2021-04-15 12:00:00');
INSERT INTO `schedule_job_log` VALUES (196, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-15 12:30:00');
INSERT INTO `schedule_job_log` VALUES (197, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-15 13:00:00');
INSERT INTO `schedule_job_log` VALUES (198, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-15 13:30:00');
INSERT INTO `schedule_job_log` VALUES (199, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-15 14:00:00');
INSERT INTO `schedule_job_log` VALUES (200, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-15 14:30:00');
INSERT INTO `schedule_job_log` VALUES (201, 1, 'testTask', 'renren', 0, NULL, 4, '2021-04-15 15:00:00');
INSERT INTO `schedule_job_log` VALUES (202, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-15 15:30:00');
INSERT INTO `schedule_job_log` VALUES (203, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-15 16:00:00');
INSERT INTO `schedule_job_log` VALUES (204, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-18 18:00:00');
INSERT INTO `schedule_job_log` VALUES (205, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-18 18:30:00');
INSERT INTO `schedule_job_log` VALUES (206, 1, 'testTask', 'renren', 0, NULL, 1, '2021-04-18 19:00:00');
INSERT INTO `schedule_job_log` VALUES (207, 1, 'testTask', 'renren', 0, NULL, 3, '2021-04-18 19:30:00');
INSERT INTO `schedule_job_log` VALUES (208, 1, 'testTask', 'renren', 0, NULL, 2, '2021-04-18 20:00:00');
INSERT INTO `schedule_job_log` VALUES (209, 1, 'testTask', 'renren', 0, NULL, 0, '2021-04-18 22:00:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统验证码';

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------
BEGIN;
INSERT INTO `sys_captcha` VALUES ('01437a3f-5ea3-403e-822d-8cd1525538e4', 'fcx65', '2021-04-06 11:56:46');
INSERT INTO `sys_captcha` VALUES ('0210551c-3db5-4017-8931-89ddb1b11f49', 'yapmy', '2021-03-17 15:34:43');
INSERT INTO `sys_captcha` VALUES ('08569f4a-4831-48c8-8aa8-b2dd2d4de59c', 'pfc43', '2021-03-17 15:12:48');
INSERT INTO `sys_captcha` VALUES ('0f2cb80d-1319-4f39-8e3e-956365fe64b5', 'gngde', '2021-04-06 17:54:14');
INSERT INTO `sys_captcha` VALUES ('0fd87e03-6ad5-4f5b-87f5-c3a2d0eae0fb', 'pe3cg', '2021-03-23 14:51:40');
INSERT INTO `sys_captcha` VALUES ('110aba47-b07d-410e-8250-905158e9ede8', 'c8e2m', '2021-01-14 15:58:25');
INSERT INTO `sys_captcha` VALUES ('126db5a0-3f28-4068-8159-49a5655bbec5', '66nmc', '2021-04-06 11:59:10');
INSERT INTO `sys_captcha` VALUES ('1291b403-dc02-43d9-8429-9fcf1f06e54f', 'aec24', '2021-04-06 12:07:41');
INSERT INTO `sys_captcha` VALUES ('17454055-6ec5-471a-8486-f54bbc405ddb', 'ymd3x', '2021-03-17 14:58:54');
INSERT INTO `sys_captcha` VALUES ('1849cf4e-69c7-4f3a-80dd-3443105eda8f', 'd2ny6', '2021-03-18 10:17:19');
INSERT INTO `sys_captcha` VALUES ('190d85e1-b9df-4832-8800-7beac7df8551', 'ad4f2', '2021-04-06 17:55:05');
INSERT INTO `sys_captcha` VALUES ('19b6bca3-4c44-42fb-8b04-d4c6e7cdfd60', 'xfpw6', '2021-03-18 17:51:24');
INSERT INTO `sys_captcha` VALUES ('21787ced-14c2-45a0-8608-0f87e3f69e38', 'fgdna', '2021-03-17 14:58:58');
INSERT INTO `sys_captcha` VALUES ('294ae13a-cb34-4fd7-8709-822cbbd5b823', '3pba6', '2021-03-17 11:16:16');
INSERT INTO `sys_captcha` VALUES ('2b9cb979-2f55-4451-873f-c1bb4585c123', 'mwxp6', '2021-04-06 18:06:22');
INSERT INTO `sys_captcha` VALUES ('2ea30eb1-fe21-4fac-8055-8a511fa7a1ab', 'pg38e', '2021-03-22 11:45:01');
INSERT INTO `sys_captcha` VALUES ('2fa4ddec-d7cd-4456-87b0-5b4041e4937a', 'dny3y', '2021-01-14 15:46:10');
INSERT INTO `sys_captcha` VALUES ('30707bdf-eefb-463d-8fd1-ec5067f05852', 'ey74b', '2021-03-17 16:25:25');
INSERT INTO `sys_captcha` VALUES ('34b62c6e-abf2-4e85-8d09-61333239ec60', '82aec', '2021-04-13 19:57:50');
INSERT INTO `sys_captcha` VALUES ('36b5e656-f1d0-475e-8a52-fe650c227cb5', 'b7586', '2021-04-06 17:53:27');
INSERT INTO `sys_captcha` VALUES ('3a839980-20a6-495a-8fb8-949f5e4ff4bf', 'fca8g', '2021-04-06 17:51:29');
INSERT INTO `sys_captcha` VALUES ('3acb0f34-8f72-4dae-805c-e1fbe858500a', 'nnand', '2021-04-06 18:06:22');
INSERT INTO `sys_captcha` VALUES ('3dc1efc3-80e6-4167-845f-e1e72fde9dbf', 'wm3mm', '2021-03-17 16:25:24');
INSERT INTO `sys_captcha` VALUES ('3ee414ae-bdd6-413d-884c-ec7f2ce6c82b', 'pnmbe', '2021-04-06 17:51:53');
INSERT INTO `sys_captcha` VALUES ('43cb7e75-535b-431b-80b6-c54aafbb6aff', 'bxybw', '2021-04-06 17:55:06');
INSERT INTO `sys_captcha` VALUES ('44a26c79-bc48-4106-8382-0fec7b382214', '3gbdn', '2021-04-06 16:52:18');
INSERT INTO `sys_captcha` VALUES ('4554001d-310f-439e-8aee-ed08ec70a683', 'cmb83', '2021-04-06 17:55:06');
INSERT INTO `sys_captcha` VALUES ('48b6f800-9b41-407e-890b-29f99b2d20b9', 'bcnen', '2021-04-13 11:46:10');
INSERT INTO `sys_captcha` VALUES ('497494f6-7ebf-4aad-873c-72e50d3656cf', 'fad4e', '2021-04-08 14:26:34');
INSERT INTO `sys_captcha` VALUES ('4b19c137-2d91-41c1-8d19-a3936870ed7e', 'y6n46', '2021-03-17 14:57:04');
INSERT INTO `sys_captcha` VALUES ('4f9d56db-442a-4558-8438-014d10ac692c', '38xwc', '2021-03-17 14:50:00');
INSERT INTO `sys_captcha` VALUES ('5146ee52-9544-43da-8bd0-85a63009f5a5', 'cn8ab', '2021-03-17 11:46:30');
INSERT INTO `sys_captcha` VALUES ('52485de6-84bc-4918-84c6-a79ac2a69266', 'en7e5', '2021-03-17 15:00:35');
INSERT INTO `sys_captcha` VALUES ('5310839e-cefb-4651-8410-5d7a7164a13c', 'ydyac', '2021-03-18 11:52:23');
INSERT INTO `sys_captcha` VALUES ('544a47e6-1442-4b05-8175-e4b62bbd8645', '6g38n', '2021-04-06 12:04:00');
INSERT INTO `sys_captcha` VALUES ('582d36a9-40ed-45ac-8450-88a5f88fe46f', 'y22dg', '2021-03-17 14:31:03');
INSERT INTO `sys_captcha` VALUES ('5862e826-d5fd-4427-83b2-0ced0a0b80d0', 'n7nem', '2021-03-17 15:00:38');
INSERT INTO `sys_captcha` VALUES ('599e0cd5-5eda-4b8e-8dc9-8d1ca659ac9d', '87gnd', '2021-04-06 17:53:31');
INSERT INTO `sys_captcha` VALUES ('5ad52765-6823-4556-8a10-e3deb8d087ad', 'wfb46', '2021-04-06 17:55:06');
INSERT INTO `sys_captcha` VALUES ('5b6fd804-c2d7-4e2f-8ddf-4db25bc4f7c4', 'bc23n', '2021-03-19 14:55:12');
INSERT INTO `sys_captcha` VALUES ('606942c0-e93c-4fc4-841c-ac24323edbad', 'bdppa', '2021-01-15 19:15:47');
INSERT INTO `sys_captcha` VALUES ('623ebd0e-2061-4b73-8fbd-caffbe681c50', 'emebm', '2021-04-06 18:06:22');
INSERT INTO `sys_captcha` VALUES ('6333c28c-2144-45fc-8a18-1275e3ab5572', 'xg5n5', '2021-03-18 14:47:33');
INSERT INTO `sys_captcha` VALUES ('639f4621-aaa9-4811-867b-77a308bfa82d', 'wbaxd', '2021-04-06 18:06:22');
INSERT INTO `sys_captcha` VALUES ('67156ae9-f79a-4a7b-872d-de68b558013b', '7a5bd', '2021-03-17 13:01:04');
INSERT INTO `sys_captcha` VALUES ('69168794-d6ce-48ee-84b4-1257c20b620d', 'x37gn', '2021-03-17 11:47:37');
INSERT INTO `sys_captcha` VALUES ('6b0147a7-709c-45b2-86ee-cba34d6a0326', '43cce', '2021-01-14 15:44:58');
INSERT INTO `sys_captcha` VALUES ('6c649501-bacb-496f-8d36-5f7e0380d8e6', '2ac5c', '2021-03-17 14:30:57');
INSERT INTO `sys_captcha` VALUES ('6d3a9ecc-597d-4f52-82f7-b2ef4ae9539c', '35ga8', '2021-04-06 14:51:32');
INSERT INTO `sys_captcha` VALUES ('7122f260-6ad0-4c3e-81ad-a8b670d9cce1', 'b72y7', '2021-04-06 12:02:51');
INSERT INTO `sys_captcha` VALUES ('732bebcc-eda8-41e3-8b61-d10ed9b1e92e', 'f7648', '2021-03-17 13:00:53');
INSERT INTO `sys_captcha` VALUES ('787f1e23-f6f3-4f4c-8136-4414a0485c5e', 'pnead', '2021-04-06 18:06:22');
INSERT INTO `sys_captcha` VALUES ('7a885ca9-8e8b-4207-8fbf-f66fe6bcd004', '64x3m', '2021-03-17 16:25:21');
INSERT INTO `sys_captcha` VALUES ('7e520f0a-0eae-469c-8b32-8df1e1ac8f23', '3nax3', '2021-04-06 16:52:18');
INSERT INTO `sys_captcha` VALUES ('7f358470-ffd6-4766-852b-30688db6ad8b', 'bedgc', '2021-03-17 13:00:54');
INSERT INTO `sys_captcha` VALUES ('84e78ba6-a42b-4f51-86de-484faa10d490', '3dynp', '2021-03-17 14:49:55');
INSERT INTO `sys_captcha` VALUES ('876fc54a-5524-4072-8980-052cbd3ab7e2', 'n48c4', '2021-04-06 17:53:31');
INSERT INTO `sys_captcha` VALUES ('8fa0fb5b-5039-499e-8e62-9cbe4e9cfb62', 'pmg43', '2021-04-06 12:02:51');
INSERT INTO `sys_captcha` VALUES ('939c2a1c-b3e6-4156-86d9-a97fa75f9280', 'pnn5c', '2021-03-15 14:59:51');
INSERT INTO `sys_captcha` VALUES ('955b3198-eda0-41be-84c4-ee252e3063db', '7f2xw', '2021-04-06 12:03:14');
INSERT INTO `sys_captcha` VALUES ('960c8976-ffc9-4067-8816-789f736c47b8', '6en87', '2021-03-17 11:47:16');
INSERT INTO `sys_captcha` VALUES ('9aaac373-24b5-48bb-8070-b42846d8422c', '76aw5', '2021-04-02 11:59:41');
INSERT INTO `sys_captcha` VALUES ('9da41751-7d23-4e7a-8e74-989ce9f9ad18', 'xbgb6', '2021-03-17 14:57:07');
INSERT INTO `sys_captcha` VALUES ('9e59e76a-2b6b-47f4-87c0-f99621779861', '8y383', '2021-03-18 17:51:24');
INSERT INTO `sys_captcha` VALUES ('9f002940-b311-46d6-8f70-4cb3bf2711b0', 'w7xb4', '2021-03-18 14:47:33');
INSERT INTO `sys_captcha` VALUES ('a1a7ab74-f945-488e-8606-b91bc9924824', '75be6', '2021-03-17 14:59:08');
INSERT INTO `sys_captcha` VALUES ('a474889f-88b3-4180-8145-db1e59e097e5', 'dxn3n', '2021-03-17 16:25:21');
INSERT INTO `sys_captcha` VALUES ('a599c321-7eea-4bec-8fcd-8a0c3bd57bef', '8w8ng', '2021-04-06 17:54:13');
INSERT INTO `sys_captcha` VALUES ('aa89b45c-d22a-4686-8b54-483af4d86185', 'wxm2y', '2021-04-06 17:55:06');
INSERT INTO `sys_captcha` VALUES ('ac766bbf-25cd-4332-8f23-2ae4adff307d', '432fm', '2021-04-06 11:58:03');
INSERT INTO `sys_captcha` VALUES ('add5a9fb-769c-44aa-8112-bdbd331b47c0', 'awwa3', '2021-03-18 17:28:42');
INSERT INTO `sys_captcha` VALUES ('aefedabf-8b06-46ec-8590-ff248e85bb3d', 'wxa6n', '2021-03-19 15:55:23');
INSERT INTO `sys_captcha` VALUES ('afd1e4c1-3743-4d01-86d2-33cb02f6ca55', 'xa34w', '2021-03-17 16:25:23');
INSERT INTO `sys_captcha` VALUES ('b7f51da7-681c-466a-80ca-a102504ca752', 'mnacy', '2021-03-17 11:35:03');
INSERT INTO `sys_captcha` VALUES ('bb7fa00d-da53-4002-8fe6-1f889f8c6aee', 'gd8m6', '2021-03-23 15:20:12');
INSERT INTO `sys_captcha` VALUES ('c07a29d9-4180-486b-8c7e-9e397903f362', 'n6p4n', '2021-04-06 11:55:05');
INSERT INTO `sys_captcha` VALUES ('c5982ae0-9d2b-4d98-8f82-6d9080bffeae', 'n6dny', '2021-03-17 14:58:13');
INSERT INTO `sys_captcha` VALUES ('c7e99428-45d1-4488-822f-7eea8a2541b2', 'ac85p', '2021-03-17 14:44:46');
INSERT INTO `sys_captcha` VALUES ('c8a93315-a798-475e-8b20-9fbb9189cf1d', '24p48', '2021-04-14 12:36:17');
INSERT INTO `sys_captcha` VALUES ('c9f3f09b-def9-48c3-8a00-3317a0fec299', 'g6pgw', '2021-03-17 11:16:11');
INSERT INTO `sys_captcha` VALUES ('cc5b9906-f09e-4b38-8c50-670ec0b3fd18', '5d877', '2021-04-02 11:59:08');
INSERT INTO `sys_captcha` VALUES ('cc748c9e-7397-4ea1-84c3-21b011eb5b79', 'pydmp', '2021-03-18 14:43:33');
INSERT INTO `sys_captcha` VALUES ('cd0332d3-40d8-4f85-8798-7a7c1a730336', '483m8', '2021-03-17 11:47:20');
INSERT INTO `sys_captcha` VALUES ('cd7ebd6d-0a9a-4cc4-87dd-e4038f9e48c7', 'na22f', '2021-01-14 15:58:24');
INSERT INTO `sys_captcha` VALUES ('ce585682-fc88-42f0-8f5f-d43c2447188d', 'cmpdn', '2021-03-17 11:41:26');
INSERT INTO `sys_captcha` VALUES ('d2f18a55-94c3-4035-80c9-dc57e958a092', 'nf6ad', '2021-03-17 14:44:53');
INSERT INTO `sys_captcha` VALUES ('d2f1ff6d-be47-49aa-8ddb-50cf6bef0361', 'xaf4w', '2021-03-17 16:25:26');
INSERT INTO `sys_captcha` VALUES ('d33bd5f2-86af-4d05-83f6-f7f12ce2169b', '3x8pw', '2021-04-06 17:30:21');
INSERT INTO `sys_captcha` VALUES ('daf4f79d-7b3b-4b72-8993-79ce5ecaa359', '63e3m', '2021-01-14 15:45:06');
INSERT INTO `sys_captcha` VALUES ('dbe1d1bb-04be-4af1-8799-30dfce43f29b', 'c722d', '2021-04-06 17:54:14');
INSERT INTO `sys_captcha` VALUES ('deb7a89e-4acc-4a7b-8ff8-f883cd83491d', 'nbync', '2021-04-18 21:49:27');
INSERT INTO `sys_captcha` VALUES ('e5f55efb-c731-48b9-8e9a-0e90612051d5', 'e6cyf', '2021-03-17 14:44:28');
INSERT INTO `sys_captcha` VALUES ('e9d51639-a32b-4521-8a2e-52d709eed9fd', 'mmx6f', '2021-03-22 16:20:05');
INSERT INTO `sys_captcha` VALUES ('eb18c261-ea09-4099-8369-7f076493f54a', 'b72nd', '2021-03-18 16:36:04');
INSERT INTO `sys_captcha` VALUES ('ebb2d373-10e9-4fe7-8230-27b6c655ea6a', 'ydx7p', '2021-04-14 10:09:03');
INSERT INTO `sys_captcha` VALUES ('ecedcc4a-25db-40ce-86dd-3972622579b4', 'x8g25', '2021-04-14 12:36:17');
INSERT INTO `sys_captcha` VALUES ('f094934f-1c11-40ee-81f1-76f94e393462', '472fy', '2021-04-18 17:58:27');
INSERT INTO `sys_captcha` VALUES ('f0c10387-e3ac-4940-8a17-3a75a402921d', 'gcfcc', '2021-03-19 15:52:47');
INSERT INTO `sys_captcha` VALUES ('f11e4610-5d49-4af4-8803-0d16a4b64fb3', '2f4na', '2021-04-06 14:57:32');
INSERT INTO `sys_captcha` VALUES ('f2073567-0be6-47f7-8dc4-e89a1f96ddb3', '35add', '2021-04-18 21:49:15');
INSERT INTO `sys_captcha` VALUES ('f6263796-62d2-42c3-8359-562b825e0ad4', 'yga73', '2021-04-06 17:54:14');
INSERT INTO `sys_captcha` VALUES ('f7d30ffc-5deb-4488-87a0-5f3d9737e8b0', '588b2', '2021-04-06 18:06:21');
INSERT INTO `sys_captcha` VALUES ('fa8d9856-cdf9-4af3-8739-4a2d049444b6', 'aem63', '2021-01-14 15:58:32');
INSERT INTO `sys_captcha` VALUES ('fbe5f4f6-7998-4658-8ff1-2e395b6a85b6', 'g5ff2', '2021-04-18 17:58:11');
INSERT INTO `sys_captcha` VALUES ('fedf2783-4089-4206-878d-4db1c655955e', 'cdwdn', '2021-03-17 10:59:03');
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', 0, '云存储配置信息');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES (1, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":31,\"parentId\":0,\"name\":\"商品系统\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"editor\",\"orderNum\":0}]', 18, '127.0.0.1', '2021-03-17 10:58:33');
INSERT INTO `sys_log` VALUES (2, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":32,\"parentId\":0,\"name\":\"分类维护\",\"url\":\"商品系统\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]', 15, '127.0.0.1', '2021-03-17 11:01:00');
INSERT INTO `sys_log` VALUES (3, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"pro\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]', 29, '127.0.0.1', '2021-03-17 11:02:58');
INSERT INTO `sys_log` VALUES (4, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]', 19, '127.0.0.1', '2021-03-17 11:03:13');
INSERT INTO `sys_log` VALUES (5, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product/category\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]', 21, '127.0.0.1', '2021-03-17 11:03:32');
INSERT INTO `sys_log` VALUES (6, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":33,\"parentId\":0,\"name\":\"品牌管理\",\"url\":\"product\",\"perms\":\"\",\"type\":1,\"icon\":\"\",\"orderNum\":0}]', 22, '0:0:0:0:0:0:0:1', '2021-03-19 14:50:53');
INSERT INTO `sys_log` VALUES (7, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":33,\"parentId\":31,\"name\":\"品牌管理\",\"url\":\"product\",\"perms\":\"\",\"type\":1,\"icon\":\"editor\",\"orderNum\":0}]', 22, '0:0:0:0:0:0:0:1', '2021-03-19 14:51:49');
INSERT INTO `sys_log` VALUES (8, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":33,\"parentId\":31,\"name\":\"品牌管理\",\"url\":\"product/brand\",\"perms\":\"\",\"type\":1,\"icon\":\"editor\",\"orderNum\":0}]', 18, '0:0:0:0:0:0:0:1', '2021-03-19 15:22:28');
INSERT INTO `sys_log` VALUES (9, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":33,\"parentId\":31,\"name\":\"品牌管理\",\"url\":\"product/brand\",\"perms\":\"\",\"type\":1,\"icon\":\"editor\",\"orderNum\":0}]', 16, '0:0:0:0:0:0:0:1', '2021-03-19 15:22:29');
INSERT INTO `sys_log` VALUES (10, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[46]', 5, '127.0.0.1', '2021-04-02 11:56:10');
INSERT INTO `sys_log` VALUES (11, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[46]', 4, '127.0.0.1', '2021-04-02 11:56:13');
INSERT INTO `sys_log` VALUES (12, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[46]', 4, '127.0.0.1', '2021-04-02 11:56:16');
INSERT INTO `sys_log` VALUES (13, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[44]', 52, '127.0.0.1', '2021-04-02 12:01:35');
INSERT INTO `sys_log` VALUES (14, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[45]', 54, '127.0.0.1', '2021-04-02 12:01:40');
INSERT INTO `sys_log` VALUES (15, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[46]', 37, '127.0.0.1', '2021-04-02 12:01:44');
INSERT INTO `sys_log` VALUES (16, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[43]', 35, '127.0.0.1', '2021-04-02 12:02:57');
INSERT INTO `sys_log` VALUES (17, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[42]', 30, '127.0.0.1', '2021-04-02 12:03:00');
INSERT INTO `sys_log` VALUES (18, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[31]', 0, '127.0.0.1', '2021-04-02 12:04:17');
INSERT INTO `sys_log` VALUES (19, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":76,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product/category\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]', 20, '127.0.0.1', '2021-04-06 11:45:04');
INSERT INTO `sys_log` VALUES (20, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":77,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product/category\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]', 27, '0:0:0:0:0:0:0:1', '2021-04-06 14:47:19');
INSERT INTO `sys_log` VALUES (21, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":78,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product/category\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]', 16, '0:0:0:0:0:0:0:1', '2021-04-06 14:53:37');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `name` varchar(150) DEFAULT NULL,
  `url` varchar(600) DEFAULT NULL,
  `perms` varchar(1500) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `icon` varchar(150) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO `sys_menu` VALUES (5, 1, 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', NULL, 1, 'sql', 4);
INSERT INTO `sys_menu` VALUES (6, 1, '定时任务', 'job/schedule', NULL, 1, 'job', 5);
INSERT INTO `sys_menu` VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO `sys_menu` VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);
INSERT INTO `sys_menu` VALUES (31, 0, '商品系统', '', '', 0, 'editor', 0);
INSERT INTO `sys_menu` VALUES (32, 31, '分类维护', 'product/category', '', 1, 'menu', 0);
INSERT INTO `sys_menu` VALUES (34, 31, '品牌管理', 'product/brand', '', 1, 'editor', 0);
INSERT INTO `sys_menu` VALUES (37, 31, '平台属性', '', '', 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (38, 37, '属性分组', 'product/attrgroup', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (39, 37, '基本属性', 'product/baseattr', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (40, 37, '销售属性', 'product/saleattr', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (41, 31, '商品维护', 'product/spu', '', 0, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (42, 0, '优惠营销', '', '', 0, 'mudedi', 0);
INSERT INTO `sys_menu` VALUES (43, 0, '库存系统', '', '', 0, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (44, 0, '订单系统', '', '', 0, 'config', 0);
INSERT INTO `sys_menu` VALUES (45, 0, '用户系统', '', '', 0, 'admin', 0);
INSERT INTO `sys_menu` VALUES (46, 0, '内容管理', '', '', 0, 'sousuo', 0);
INSERT INTO `sys_menu` VALUES (47, 42, '优惠券管理', 'coupon/coupon', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (48, 42, '发放记录', 'coupon/history', '', 1, 'sql', 0);
INSERT INTO `sys_menu` VALUES (49, 42, '专题活动', 'coupon/subject', '', 1, 'tixing', 0);
INSERT INTO `sys_menu` VALUES (50, 42, '秒杀活动', 'coupon/seckill', '', 1, 'daohang', 0);
INSERT INTO `sys_menu` VALUES (51, 42, '积分维护', 'coupon/bounds', '', 1, 'geren', 0);
INSERT INTO `sys_menu` VALUES (52, 42, '满减折扣', 'coupon/full', '', 1, 'shoucang', 0);
INSERT INTO `sys_menu` VALUES (53, 43, '仓库维护', 'inventory/inventory', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (54, 43, '库存工作单', 'inventory/inventoryordertask', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (55, 43, '商品库存', 'inventory/inventorysku', '', 1, 'jiesuo', 0);
INSERT INTO `sys_menu` VALUES (56, 44, '订单查询', 'order/order', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (57, 44, '退货单处理', 'order/return', '', 1, 'shanchu', 0);
INSERT INTO `sys_menu` VALUES (58, 44, '等级规则', 'order/settings', '', 1, 'system', 0);
INSERT INTO `sys_menu` VALUES (59, 44, '支付流水查询', 'order/payment', '', 1, 'job', 0);
INSERT INTO `sys_menu` VALUES (60, 44, '退款流水查询', 'order/refund', '', 1, 'mudedi', 0);
INSERT INTO `sys_menu` VALUES (61, 45, '会员列表', 'member/member', '', 1, 'geren', 0);
INSERT INTO `sys_menu` VALUES (62, 45, '会员等级', 'member/level', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (63, 45, '积分变化', 'member/growth', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` VALUES (64, 45, '统计信息', 'member/statistics', '', 1, 'sql', 0);
INSERT INTO `sys_menu` VALUES (65, 46, '首页推荐', 'content/index', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (66, 46, '分类热门', 'content/category', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (67, 46, '评论管理', 'content/comments', '', 1, 'pinglun', 0);
INSERT INTO `sys_menu` VALUES (68, 41, 'spu管理', 'product/spu', '', 1, 'config', 0);
INSERT INTO `sys_menu` VALUES (69, 41, '发布商品', 'product/spuadd', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` VALUES (70, 43, '采购单维护', '', '', 0, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (71, 70, '采购需求', 'inventory/purchasedetail', '', 1, 'editor', 0);
INSERT INTO `sys_menu` VALUES (72, 70, '采购单', 'inventory/purchase', '', 1, 'menu', 0);
INSERT INTO `sys_menu` VALUES (73, 41, '商品管理', 'product/manager', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (74, 42, '会员价格', 'coupon/memberprice', '', 1, 'admin', 0);
INSERT INTO `sys_menu` VALUES (75, 42, '每日秒杀', 'coupon/seckillsession', '', 1, 'job', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_token` VALUES (1, '5bf3c4c04c0e2973cac45409b29ee6e0', '2021-04-19 09:57:29', '2021-04-18 21:57:29');
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
