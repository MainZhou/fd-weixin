// easyui datagrid 列拖拽
$(function() {
    $.extend($.fn.datagrid.methods, {
        columnMoving: function (jq) {
            return jq.each(function () {
                var target = this;
                var cells = $(this).datagrid('getPanel').find('.datagrid-view2 div.datagrid-header td[field]'); //可拖拽列才可以拖
                cells.draggable({
                    revert: true,
                    cursor: 'pointer',
                    edge: 5,
                    proxy: function (source) {
                        var p = $('<div class="tree-node-proxy tree-dnd-no" style="position:absolute;border:1px solid #ff0000"/>').appendTo('body');
                        p.html($(source).text());
                        p.hide();
                        return p;
                    },
                    onBeforeDrag: function (e) {
                        e.data.startLeft = $(this).offset().left;
                        e.data.startTop = $(this).offset().top;
                    },
                    onStartDrag: function () {
                        $(this).draggable('proxy').css({
                            left: -10000,
                            top: -10000
                        });
                    },
                    onDrag: function (e) {
                        if (e.type != 'mouseup'){ // 非mouseup才显示。排序和右键都会触发mouseup
                            $(this).draggable('proxy').show().css({
                                left: e.pageX + 15,
                                top: e.pageY + 15
                            });
                        }
                        return false;
                    }
                }).droppable({
                    accept: '.datagrid-view2 td[field]',//可拖拽列才可以拖
                    onDragOver: function (e, source) {
                            $(source).draggable('proxy').removeClass('tree-dnd-no').addClass('tree-dnd-yes');
                            $(this).css('border-left', '5px solid green');
                    },
                    onDragLeave: function (e, source) {
                        $(source).draggable('proxy').removeClass('tree-dnd-yes').addClass('tree-dnd-no');
                        $(this).css('border-left', 0);
                    },
                    onDrop: function (e, source) {
                        $(this).css('border-left', 0);
                        var fromField = $(source).attr('field');
                        var toField = $(this).attr('field');

                        setTimeout(function () {
                            moveField(fromField, toField);

                            // 移动
                            $(target).datagrid();
                            $(target).datagrid('columnMoving');

                            // 序列化到服务器
                            var options = $(target).datagrid("options");
                            var url = options.url;
                            var domainUrl = url.replace(ctx, "");

                            // 去除？后的字符
                            var lastNum = domainUrl.indexOf('?');
                            if (lastNum){
                                domainUrl = domainUrl.substr(0, lastNum);
                            }

                            // 取第一个
                            var columns = options.columns[0];
                            // 保存列显示或隐藏到服务器
                            var json = "{";
                            var index = 0;
                            for (var i = 0; i < columns.length; i++) {
                                var column = columns[i];
                                // 第一个不加逗号
                                if (index == 0) {
                                    json += "\"" + column.field + "\":";
                                }
                                // 后面的加逗号
                                else {
                                    json += ",\"" + column.field + "\":";
                                }
                                // 宽度，如为checkbox传checkbox
                                if(column.width){
                                    json += column.width;
                                }else if (column.checkbox){
                                    json += "\"checkbox\"";
                                }

                                index++;

                            }
                            json += "}";
                            $.post(ctx + "/columnCustom/addOrUpdate", {
                                "domainUrl": domainUrl,
                                "columnOrder": json
                            }, function (data) {
                                console.log(data.status + ":" + data.msg);
                                if (!data.status) {
                                    alert(data.msg);
                                }
                            });

                        }, 0);
                    }
                });

                // move field to another location
                function moveField(from, to) {
                    var columns = $(target).datagrid('options').columns;
                    var cc = columns[0];
                    var c = _remove(from);
                    if (c) {
                        _insert(to, c);
                    }

                    function _remove(field) {
                        for (var i = 0; i < cc.length; i++) {
                            if (cc[i].field == field) {
                                var c = cc[i];
                                cc.splice(i, 1);
                                return c;
                            }
                        }
                        return null;
                    }

                    function _insert(field, c) {
                        var newcc = [];
                        for (var i = 0; i < cc.length; i++) {
                            if (cc[i].field == field) {
                                newcc.push(c);
                            }
                            newcc.push(cc[i]);
                        }
                        columns[0] = newcc;
                    }
                }
            });
        }
    });
});