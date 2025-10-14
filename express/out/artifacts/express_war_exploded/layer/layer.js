/*!
 * layer v3.1.1 Web 弹层组件 - 无图片版本
 * MIT License
 */

;!function(win){
    "use strict";

    var Layer = function(){
        this.v = '3.1.1';
        this.ie = (function(){
            var v = 3, div = document.createElement('div'), all = div.getElementsByTagName('i');
            do {
                div.innerHTML = '<!--[if gt IE ' + (++v) + ']><i></i><![endif]-->';
            } while (all[0]);
            return v > 4 ? v : win.ActiveXObject ? 6 : 0;
        }());
        this.index = 0;
        this.path = '';

        this.config = {
            type: 0,
            title: '信息',
            content: '',
            skin: '',
            area: 'auto',
            offset: 'auto',
            icon: -1,
            btn: '确定',
            btnAlign: 'r',
            closeBtn: 1,
            shade: true,
            shadeClose: true,
            time: 0,
            id: '',
            zIndex: 19891014,
            maxWidth: 360,
            resize: true,
            scrollbar: true,
            maxmin: false,
            fixed: true,
            move: true,
            moveType: 1,
            moveOut: false,
            tips: 2,
            success: null,
            yes: null,
            cancel: null,
            end: null
        };
    };

    Layer.prototype = {
        // 创建层
        create: function(options){
            var that = this, config = that.config = $.extend({}, that.config, options);

            // 生成索引
            that.index = ++layer.index;

            // 设置zIndex
            config.zIndex = config.zIndex + that.index;

            // 创建HTML结构
            var html = that.html();

            // 添加到body
            $('body').append(html);

            // 获取DOM对象
            that.layero = $('#'+ that.elem(that.index));
            that.shadeo = $('#'+ that.elem(that.index, 'shade'));

            // 自适应
            that.auto(that.index);

            // 定位
            that.offset(that.index);

            // 拖拽
            if(config.move){
                that.move(that.index);
            }

            // 回调
            that.callback(that.index);

            return that.index;
        },

        // 生成HTML结构
        html: function(){
            var that = this, config = that.config;

            var titleHTML = config.title ? '<div class="layui-layer-title">'+ config.title +'</div>' : '';

            var closeHTML = config.closeBtn ? '<a class="layui-layer-close layui-layer-close'+ (config.title ? '1' : '2') +'" href="javascript:;"></a>' : '';

            var btnHTML = '';
            if(config.btn){
                var btns = typeof config.btn === 'string' ? [config.btn] : config.btn;
                btnHTML = '<div class="layui-layer-btn layui-layer-btn-'+ config.btnAlign +'">';
                $.each(btns, function(i, btn){
                    btnHTML += '<a class="layui-layer-btn'+ i +'">'+ btn +'</a>';
                });
                btnHTML += '</div>';
            }

            var contentHTML = '<div class="layui-layer-content">'+ (config.icon !== -1 ? '<i class="layui-layer-ico layui-layer-ico'+ config.icon +'"></i>' : '') + config.content +'</div>';

            var mainHTML = '<div class="layui-layer '+ (config.skin ? config.skin : '') +'" id="'+ that.elem(that.index) +'" type="'+ config.type +'" times="'+ that.index +'" showtime="'+ config.time +'" style="z-index: '+ config.zIndex +';">' + titleHTML + contentHTML + '<span class="layui-layer-setwin">'+ closeHTML +'</span>' + btnHTML + '</div>';

            var shadeHTML = config.shade ? '<div class="layui-layer-shade" id="'+ that.elem(that.index, 'shade') +'" times="'+ that.index +'" style="z-index: '+ (config.zIndex - 1) +';"></div>' : '';

            return shadeHTML + mainHTML;
        },

        // 获取元素ID
        elem: function(index, type){
            return 'layui-layer-' + (type || '') + index;
        },

        // 自适应
        auto: function(index){
            var that = this, config = that.config, layero = that.layero;

            if(config.area === 'auto'){
                var width = layero.width(), height = layero.height();
                var maxWidth = $(window).width() - 30;

                if(width > maxWidth){
                    layero.width(maxWidth);
                }
            } else if(typeof config.area === 'string'){
                layero.width(config.area);
            } else if($.isArray(config.area)){
                layero.width(config.area[0]).height(config.area[1]);
            }
        },

        // 定位
        offset: function(index){
            var that = this, config = that.config, layero = that.layero;
            var width = layero.outerWidth(), height = layero.outerHeight();
            var winWidth = $(window).width(), winHeight = $(window).height();
            var scrollTop = $(window).scrollTop(), scrollLeft = $(window).scrollLeft();

            var top = (winHeight - height) / 2 + scrollTop;
            var left = (winWidth - width) / 2 + scrollLeft;

            if(config.offset !== 'auto'){
                if(typeof config.offset === 'string'){
                    switch(config.offset){
                        case 't': top = scrollTop; break;
                        case 'r': left = winWidth - width + scrollLeft; break;
                        case 'b': top = winHeight - height + scrollTop; break;
                        case 'l': left = scrollLeft; break;
                        case 'lt': top = scrollTop; left = scrollLeft; break;
                        case 'lb': top = winHeight - height + scrollTop; left = scrollLeft; break;
                        case 'rt': top = scrollTop; left = winWidth - width + scrollLeft; break;
                        case 'rb': top = winHeight - height + scrollTop; left = winWidth - width + scrollLeft; break;
                    }
                } else if($.isArray(config.offset)){
                    top = config.offset[0] + (config.fixed ? 0 : scrollTop);
                    left = config.offset[1] + (config.fixed ? 0 : scrollLeft);
                }
            }

            layero.css({top: top, left: left});
        },

        // 拖拽
        move: function(index){
            var that = this, config = that.config, layero = that.layero;
            var moveElem = config.move ? (typeof config.move === 'string' ? layero.find(config.move) : layero.find('.layui-layer-title')) : null;

            if(moveElem){
                var move = false, moveX = 0, moveY = 0;

                moveElem.on('mousedown', function(e){
                    e.preventDefault();
                    move = true;
                    moveX = e.clientX - parseInt(layero.css('left'));
                    moveY = e.clientY - parseInt(layero.css('top'));

                    $('body').on('mousemove', function(e){
                        if(move){
                            var x = e.clientX - moveX, y = e.clientY - moveY;

                            if(!config.moveOut){
                                var maxX = $(window).width() - layero.outerWidth();
                                var maxY = $(window).height() - layero.outerHeight();

                                x = x < 0 ? 0 : x > maxX ? maxX : x;
                                y = y < 0 ? 0 : y > maxY ? maxY : y;
                            }

                            layero.css({left: x, top: y});
                        }
                    });
                });

                $(document).on('mouseup', function(){
                    move = false;
                    $('body').off('mousemove');
                });
            }
        },

        // 回调
        callback: function(index){
            var that = this, config = that.config, layero = that.layero;

            // 关闭按钮
            layero.find('.layui-layer-close').on('click', function(){
                var close = config.cancel && config.cancel(index, layero);
                if(close !== false){
                    layer.close(index);
                }
            });

            // 遮罩关闭
            if(config.shadeClose){
                that.shadeo.on('click', function(){
                    layer.close(index);
                });
            }

            // 按钮回调
            layero.find('.layui-layer-btn a').on('click', function(){
                var i = $(this).index();
                if(i === 0){
                    config.yes && config.yes(index, layero);
                } else {
                    var btn = config['btn' + (i+1)] && config['btn' + (i+1)](index, layero);
                    if(btn !== false){
                        layer.close(index);
                    }
                }
            });

            // 成功回调
            config.success && config.success(layero, index);

            // 自动关闭
            if(config.time > 0){
                setTimeout(function(){
                    layer.close(index);
                }, config.time);
            }
        }
    };

    // 全局方法
    var layer = {
        index: 1000,
        zIndex: 19891014,
        path: '',

        // 配置
        config: function(options){
            options = options || {};
            layer.path = options.path || layer.path;
            return this;
        },

        // 就绪
        ready: function(callback){
            if(typeof callback === 'function'){
                callback();
            }
            return this;
        },

        // 打开层
        open: function(options){
            var inst = new Layer();
            return inst.create(options);
        },

        // 提示框
        alert: function(content, options, yes){
            if(typeof options === 'function'){
                yes = options;
                options = {};
            }
            return layer.open($.extend({
                content: content,
                yes: yes,
                btn: '确定'
            }, options));
        },

        // 确认框
        confirm: function(content, options, yes, cancel){
            if(typeof options === 'function'){
                cancel = yes;
                yes = options;
                options = {};
            }
            return layer.open($.extend({
                content: content,
                btn: ['确定', '取消'],
                yes: yes,
                btn2: cancel
            }, options));
        },

        // 消息框
        msg: function(content, options, end){
            if(typeof options === 'function'){
                end = options;
                options = {};
            }
            options = $.extend({
                content: content,
                time: 3000,
                shade: false,
                title: false,
                closeBtn: false,
                btn: false,
                resize: false,
                end: end
            }, options);

            if(options.icon === -1 || !options.skin){
                options.skin = 'layui-layer-msg layui-layer-hui';
            }

            return layer.open(options);
        },

        // 加载层
        load: function(icon, options){
            return layer.open($.extend({
                type: 3,
                icon: icon || 0,
                resize: false,
                shade: 0.01
            }, options));
        },

        // 提示层
        tips: function(content, follow, options){
            return layer.open($.extend({
                type: 4,
                content: [content, follow],
                closeBtn: false,
                time: 3000,
                shade: false,
                resize: false,
                fixed: false,
                maxWidth: 260
            }, options));
        },

        // 关闭层
        close: function(index){
            var layero = $('#layui-layer-' + index), type = layero.attr('type');
            var shade = $('#layui-layer-shade' + index);

            // 移除元素
            layero.remove();
            shade.remove();

            // 回调
            var config = layero.data('config');
            config && config.end && config.end();
        },

        // 关闭所有层
        closeAll: function(type){
            $('[times]').each(function(){
                var layero = $(this), thisType = layero.attr('type');
                if(!type || thisType === type){
                    layer.close(layero.attr('times'));
                }
            });
        }
    };

    // 兼容旧版本
    win.layer = layer;

    // AMD 支持
    if(typeof define === 'function' && define.amd){
        define(['jquery'], function($){
            win.jQuery = win.$ = $;
            return layer;
        });
    }

    // 通用模块
    if(typeof exports === 'object'){
        module.exports = layer;
    }

}(window);