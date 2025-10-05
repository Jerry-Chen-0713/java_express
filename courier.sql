-- 创建快递员表
CREATE TABLE `courier` (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '快递员编号',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `phone` VARCHAR(11) NOT NULL UNIQUE COMMENT '手机号码',
    `id_card` VARCHAR(18) NOT NULL UNIQUE COMMENT '身份证号',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `delivery_count` INT DEFAULT 0 COMMENT '派件数',
    `register_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    `last_login_time` DATETIME COMMENT '上次登陆时间',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    INDEX `idx_phone` (`phone`),
    INDEX `idx_id_card` (`id_card`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='快递员表';

-- 插入示例数据
INSERT INTO `courier` (`id`, `name`, `phone`, `id_card`, `password`, `delivery_count`, `register_time`, `last_login_time`) VALUES
(1001, '张三', '13843838438', '110101199001011234', 'wsygdsb', 100000, NOW(), NOW()),
(1002, '李四', '13843838439', '110101199001011235', 'wsygdsb', 98000, NOW(), NOW()),
(1003, '王五', '13843838440', '110101199001011236', 'wsygdsb', 120000, NOW(), NOW()),
(1004, '赵六', '13843838441', '110101199001011237', 'wsygdsb', 85000, NOW(), NOW());