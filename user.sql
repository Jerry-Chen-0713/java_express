-- 创建用户表
CREATE TABLE `user` (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    `user_name` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `user_phone` VARCHAR(11) NOT NULL UNIQUE COMMENT '手机号码',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `login_time` DATETIME COMMENT '登录时间',
    `is_user` TINYINT DEFAULT 1 COMMENT '是否为普通用户：0-管理员，1-普通用户',
    INDEX `idx_user_name` (`user_name`),
    INDEX `idx_user_phone` (`user_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入默认管理员账户
INSERT INTO `user` (`user_name`, `user_phone`, `password`, `is_user`) VALUES
('admin', '13800138000', 'admin123', 0);