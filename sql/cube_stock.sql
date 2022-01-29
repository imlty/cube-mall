/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.5.68-MariaDB : Database - cube_stock
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cube_stock` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `cube_stock`;

/*Table structure for table `tb_purchase` */

DROP TABLE IF EXISTS `tb_purchase`;

CREATE TABLE `tb_purchase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '采购单id',
  `assignee_id` bigint(20) DEFAULT NULL COMMENT '采购人id',
  `assignee_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '采购人名',
  `phone` char(13) COLLATE utf8_bin DEFAULT NULL COMMENT '联系方式',
  `priority` int(4) DEFAULT NULL COMMENT '优先级',
  `status` int(4) DEFAULT NULL COMMENT '状态',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `amount` decimal(18,4) DEFAULT NULL COMMENT '总金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='采购信息';

/*Data for the table `tb_purchase` */

/*Table structure for table `tb_purchase_detail` */

DROP TABLE IF EXISTS `tb_purchase_detail`;

CREATE TABLE `tb_purchase_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint(20) DEFAULT NULL COMMENT '采购单id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT '采购商品id',
  `sku_num` int(11) DEFAULT NULL COMMENT '采购数量',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '采购金额',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `status` int(11) DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `tb_purchase_detail` */

/*Table structure for table `tb_stock_info` */

DROP TABLE IF EXISTS `tb_stock_info`;

CREATE TABLE `tb_stock_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='仓库信息';

/*Data for the table `tb_stock_info` */

/*Table structure for table `tb_stock_order_task` */

DROP TABLE IF EXISTS `tb_stock_order_task`;

CREATE TABLE `tb_stock_order_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
  `task_status` tinyint(2) DEFAULT NULL COMMENT '任务状态',
  `order_body` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) COLLATE utf8_bin DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='库存工作单';

/*Data for the table `tb_stock_order_task` */

/*Table structure for table `tb_stock_order_task_detail` */

DROP TABLE IF EXISTS `tb_stock_order_task_detail`;

CREATE TABLE `tb_stock_order_task_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int(11) DEFAULT NULL COMMENT '购买个数',
  `task_id` bigint(20) DEFAULT NULL COMMENT '工作单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='库存工作单';

/*Data for the table `tb_stock_order_task_detail` */

/*Table structure for table `tb_stock_sku` */

DROP TABLE IF EXISTS `tb_stock_sku`;

CREATE TABLE `tb_stock_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `stock` int(11) DEFAULT NULL COMMENT '库存数',
  `sku_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int(11) DEFAULT NULL COMMENT '锁定库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品库存';

/*Data for the table `tb_stock_sku` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
