/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.6.49 : Database - cube-user
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cube_user` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `cube_user`;

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `level_id` bigint(20) DEFAULT NULL COMMENT '会员等级id',
  `username` char(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `nickname` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `header` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `birth` date DEFAULT NULL COMMENT '生日',
  `city` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '所在城市',
  `job` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '职业',
  `sign` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '个性签名',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '用户来源',
  `integration` int(11) DEFAULT NULL COMMENT '积分',
  `status` tinyint(4) DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员';

/*Data for the table `tb_user` */

/*Table structure for table `tb_user_collect_spu` */

DROP TABLE IF EXISTS `tb_user_collect_spu`;

CREATE TABLE `tb_user_collect_spu` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'spu_name',
  `spu_img` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'spu_img',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员收藏的商品';

/*Data for the table `tb_user_collect_spu` */

/*Table structure for table `tb_user_collect_subject` */

DROP TABLE IF EXISTS `tb_user_collect_subject`;

CREATE TABLE `tb_user_collect_subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `subject_id` bigint(20) DEFAULT NULL COMMENT 'subject_id',
  `subject_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'subject_name',
  `subject_img` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'subject_img',
  `subject_url` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '活动url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员收藏的专题活动';

/*Data for the table `tb_user_collect_subject` */

/*Table structure for table `tb_user_level` */

DROP TABLE IF EXISTS `tb_user_level`;

CREATE TABLE `tb_user_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '等级名称',
  `growth_point` int(11) DEFAULT NULL COMMENT '等级需要的成长值',
  `default_status` tinyint(4) DEFAULT NULL COMMENT '是否为默认等级[0->不是；1->是]',
  `free_freight_point` decimal(18,4) DEFAULT NULL COMMENT '免运费标准',
  `comment_growth_point` int(11) DEFAULT NULL COMMENT '每次评价获取的成长值',
  `priviledge_free_freight` tinyint(4) DEFAULT NULL COMMENT '是否有免邮特权',
  `priviledge_user_price` tinyint(4) DEFAULT NULL COMMENT '是否有会员价格特权',
  `priviledge_birthday` tinyint(4) DEFAULT NULL COMMENT '是否有生日特权',
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员等级';

/*Data for the table `tb_user_level` */

/*Table structure for table `tb_user_login_log` */

DROP TABLE IF EXISTS `tb_user_login_log`;

CREATE TABLE `tb_user_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'user_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'ip',
  `city` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'city',
  `login_type` tinyint(1) DEFAULT NULL COMMENT '登录类型[1-web，2-app]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员登录记录';

/*Data for the table `tb_user_login_log` */

/*Table structure for table `tb_user_receive_address` */

DROP TABLE IF EXISTS `tb_user_receive_address`;

CREATE TABLE `tb_user_receive_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'user_id',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `post_code` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '邮政编码',
  `province` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '省份/直辖市',
  `city` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '城市',
  `region` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '区',
  `detail_address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '详细地址(街道)',
  `areacode` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '省市区代码',
  `default_status` tinyint(1) DEFAULT NULL COMMENT '是否默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员收货地址';

/*Data for the table `tb_user_receive_address` */

/*Table structure for table `tb_user_statistics_info` */

DROP TABLE IF EXISTS `tb_user_statistics_info`;

CREATE TABLE `tb_user_statistics_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `consume_amount` decimal(18,4) DEFAULT NULL COMMENT '累计消费金额',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '累计优惠金额',
  `order_count` int(11) DEFAULT NULL COMMENT '订单数量',
  `coupon_count` int(11) DEFAULT NULL COMMENT '优惠券数量',
  `comment_count` int(11) DEFAULT NULL COMMENT '评价数',
  `return_order_count` int(11) DEFAULT NULL COMMENT '退货数量',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `attend_count` int(11) DEFAULT NULL COMMENT '关注数量',
  `fans_count` int(11) DEFAULT NULL COMMENT '粉丝数量',
  `collect_product_count` int(11) DEFAULT NULL COMMENT '收藏的商品数量',
  `collect_subject_count` int(11) DEFAULT NULL COMMENT '收藏的专题活动数量',
  `collect_comment_count` int(11) DEFAULT NULL COMMENT '收藏的评论数量',
  `invite_friend_count` int(11) DEFAULT NULL COMMENT '邀请的朋友数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员统计信息';

/*Data for the table `tb_user_statistics_info` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
