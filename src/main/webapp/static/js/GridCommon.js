/**
 * 定义公共的
 */
define(['require', 'bootstrapDialog', 'jqueryForm', 'popover', 'exports', 'easyUi', 'select2'],
    function (require, bootstrapDialog, jqueryForm, popover, exports, easyUi) {

        // 从requirejs 中获得jQuery对象
        var $ = require('jquery');
        //dataGrid的ID,全局
        var Global_Grid_Class = 'gridContainer';
        // 全局的添加按钮的class 名称
        var Global_Add_Class = 'add-tag';
        // 全局的编辑按钮的class 名称
        var Global_Edit_Class = 'edit-tag';
        // 全局的详情按钮的class 名称
        var Global_Detail_Class = 'detail-tag';
        // 全局的删除按钮的class 名称
        var Global_Del_Class = 'del-tag';
        // 全局的删除按钮的class 名称
        var Global_Export_Class = 'export-tag';
        // 全局的返回按钮的class 名称
        var Global_goBack_Class = 'goBack-tag';
        // 全局的短信按钮的class 名称
        var Global_Balance_Class = 'balance-tag';
        // 全局对话框 样式
        var Global_Dialog_Type = bootstrapDialog.TYPE_DEFAULT;
        // 对话框提交按钮样式
        var Global_Dialog_Submit_Css = "btn-default";
        // 刷新页面的数组
        var flushPageArray = [];
        exports.flushPageArray = flushPageArray;
        // 高级搜索所在popover的索引
        var advSearchIndex = {};
        // 普通搜索或高级搜索(默认普通)
        var searchTypeJson = {};

        // 全局宽度
        var width;
        var height;
        var pageSize;
        var subHeight;
        var subPageSize;
        $(function () {
            // 初始化相关数据
            height = $(window).height() - 150; //页面高度减去
            width = $(".tab-content").width();//重新获得datagrid宽度
            pageSize = parseInt((height - 50) / 32); // 获得与浏览器相匹配的页数

            subHeight = height - 40;
            subPageSize = parseInt((height - 40) / 32);
            exports._height = height;
            exports._pageSize = pageSize;
            exports._contentWidth = width;
            // 返回二级标签页grid的高度
            exports.subHeight = subHeight;
            // 返回二级标签页grid的pageSize
            exports.subPageSize = subPageSize;
        });

        exports._width = initGridSize;

        function initGridSize() {
            var width2;
            // 重置height和width
            if ($(currentContent()).find(".zTreeContent").hasClass("zTreeContent")) {
                // 有zTree
                if ($(currentContent()).find(".zTreeContent:visible").hasClass("zTreeContent")) {
                    // zTree显示
                    // 折叠状态
                    if ($("body [data-toggle='offcanvas']").hasClass("sidebar-toggle2")) {
                        width2 = width - 255 + 115;
                    } else {
                        // 非折叠状态
                        width2 = width - 255;
                    }
                } else {
                    // zTree隐藏
                    // 折叠状态
                    if ($("body [data-toggle='offcanvas']").hasClass("sidebar-toggle2")) {
                        width2 = width + 115 - 30;
                    } else {
                        // 非折叠状态
                        width2 = width - 30;
                    }
                }

            } else {
                // 无zTree
                // 折叠状态
                if ($("body [data-toggle='offcanvas']").hasClass("sidebar-toggle2")) {
                    // 折叠状态
                    width2 = width + 115;
                } else {
                    // 非折叠状态
                    width2 = width;
                }
            }
            return width2;
        }

        // 全局登录超时, 返回登录页面
        $(document).ajaxError(function (event, request, settings) {
            if (request.status == 401 || request.status == 301 || request.status == 302) {
                window.location.href = ctx + "/user/login";
            }
        });

        //建立筛选列右键菜单
        function createColumnMenu(e, domainUrl) {
            e.preventDefault();
            // 先删除，重新创建
            $('#tmenu').remove();
            var tableId = currentGrid();
            var tmenu = $('<div id="tmenu" style="width:200px;"></div>').appendTo('body');
            // 获得listUrl
            var url = $(tableId).datagrid("options").url;
            var listUrl = url.replace(ctx, "");

            // 去除参数
            var index = listUrl.indexOf("?");
            if (index != -1) {
                listUrl = listUrl.substr(0, index);
            }

            var fields = $(tableId).datagrid('getColumnFields');
            //遍历需要显示或隐藏的列
            for (var i = 0; i < fields.length; i++) {
                var fieldOption = $(tableId).datagrid('getColumnOption', fields[i]);
                if (fieldOption) {
                    if (!fieldOption.checkbox) {
                        var title = fieldOption.title.replace("<br/>", "").replace("<br>", "");
                        if (fieldOption.hidden) {
                            $('<div id="_field_' + fields[i] + '" iconCls="icon-empty"/>').text(title).appendTo(tmenu);
                        } else {
                            $('<div id="_field_' + fields[i] + '" iconCls="glyphicon glyphicon-ok"/>').text(title).appendTo(tmenu);
                        }
                    }
                }
            }

            $('<div id="_clearGridCustom_" iconCls="fa fa-fw fa-history"/>').text("恢复默认列设置").appendTo(tmenu);

            //根据遍历内容创建菜单
            tmenu.menu({
                onClick: function (item) {
                    // 清除列配置
                    if (item.target.id === '_clearGridCustom_') {
                        jQuery.post(ctx + "/columnCustom/del", {domainUrl: listUrl}, function (data) {
                            if (data) {
                                if (!data.status) {
                                    customAlert("提示", data.msg);
                                }
                                // 标签页，第二个页面后使用
                                var subSecondLi = $("#" + Addtabs.activeTabId() + " .subTabs-tag > ul > li.active");
                                if (subSecondLi.prev('li').is("li")) {
                                    var href = subSecondLi.find("a").attr("href").substr(1);
                                    $("#" + Addtabs.activeTabId() + " .tab-content > div[id='" + href + "']").load(url);
                                } else {
                                    // 重新加载当前页
                                    $("#" + Addtabs.activeTabId()).load(url);
                                }
                            }
                        });
                        return;
                    }

                    // 显示或隐藏列
                    var fieldId = item.target.id.replace(/_field_/, "");
                    if (item.iconCls == 'glyphicon glyphicon-ok') {
                        if ($("#tmenu > .menu-item > .glyphicon").length > 1) {
                            $(tableId).datagrid('hideColumn', fieldId);
                            tmenu.menu('setIcon', {
                                target: item.target,
                                iconCls: 'icon-empty'
                            });
                        }
                    } else {
                        $(tableId).datagrid('showColumn', fieldId);
                        tmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'glyphicon glyphicon-ok'
                        });
                    }

                    // 保存列显示或隐藏到服务器
                    var json = "{";
                    var index = 0;
                    for (var i = 0; i < fields.length; i++) {
                        var fieldOption = $(tableId).datagrid('getColumnOption', fields[i]);
                        if (fieldOption) {
                            // 非checkbox才上传
                            if (!fieldOption.checkbox) {
                                // 第一个不加逗号
                                if (index == 0) {
                                    json += "\"" + fieldOption.field + "\":";
                                }
                                // 后面的加逗号
                                else {
                                    json += ",\"" + fieldOption.field + "\":";
                                }

                                // 后面加上是否隐藏
                                fieldOption.hidden ? json += false : json += true;

                                index++;
                            }
                        }
                    }
                    json += "}";
                    // 保存列配置到服务器中
                    $.post(ctx + "/columnCustom/addOrUpdate", {
                        "domainUrl": listUrl,
                        "columnConf": json
                    }, function (data) {
                        console.log(data.status + ":" + data.msg);
                        if (!data.status) {
                            alert(data.msg);
                        }
                    });

                }
            });

            tmenu.menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }

        exports.createColumnMenu = createColumnMenu;

        // 获得自定义的列（排序、宽度、是否显示）
        function makeCustomColumn(options) {
            var columnNew = [];
            jQuery.post(ctx + "/columnCustom/findCurrCustomColumn", {domainUrl: options.requestUrl}, function (data) {
                if (data) {
                    var successNum = 0;
                    var columnOrder;
                    var columnConf;
                    var column0 = options.grid.column0;
                    var column0Len = options.grid.column0.length;
                    if (data.columnConf) {
                        columnConf = JSON.parse(data.columnConf);
                    }
                    if (data.columnOrder) {
                        columnOrder = JSON.parse(data.columnOrder);
                        for (var order in columnOrder) {
                            for (var i = 0; i < column0Len; i++) {
                                if (column0[i].field === order) {
                                    columnNew[successNum] = column0[i]; // 为定义的顺序

                                    if (columnOrder[order] != "checkbox") {
                                        columnNew[successNum].width = columnOrder[order]; // 自定义宽度
                                        if (columnConf) {
                                            columnNew[successNum].hidden = !columnConf[order]; // 是否显示
                                        }
                                    }
                                    successNum++;
                                    break;
                                }
                            }
                        }

                        // 如果两边不一致，有可能是修改了grid列的名称或增加删除了列
                        if (successNum != column0Len) {
                            // 从服务器删除
                            customAlert("提示", "本模块列表改动, 将恢复该模块自定义列为初始化值!");
                            $.post(ctx + "/columnCustom/del", {domainUrl: options.requestUrl}, function (data) {
                                if (data) {
                                    if (!data.status) {
                                        customAlert("提示", data.msg);
                                    }
                                    console.log(data.msg);
                                }
                            });
                            // columnNew设为空。 使用原始数据
                            columnNew = [];
                        }

                    } else if (columnConf) {
                        // 列是否显示不为空。处理是否显示
                        for (var k = 0; k < column0Len; k++) {
                            // 非checkbox,才判断
                            if (!column0[k].checkbox) {
                                column0[k].hidden = !columnConf[column0[k].field]; // 是否显示
                            }
                        }
                    }

                }
                // 为空，默认原始数据
                if (columnNew.length == 0) {
                    columnNew = options.grid.column0;
                }
                var defaultsSub = {};
                if (Addtabs.activeTabContent().indexOf(" .subTabs-tag") != -1) {
                    defaultsSub = {
                        height: subHeight,                // 设置表格高度
                        pageSize: subPageSize,             // 默认显示多少页
                        pageList: [subPageSize, 30, 50, 100] // pageList
                    }
                }

                var defaults = {
                    width: initGridSize(),                // 设置表格宽度
                    height: height,                // 设置表格高度
                    pageSize: pageSize,             // 默认显示多少页
                    url: ctx + options.requestUrl,         // 查询的action地址
                    idField: 'guid',                 // 指定ID列
                    striped: true,                  // 每行间隔显示
                    pageList: [pageSize, 30, 50, 100], // pageList
                    rownumbers: true,                //显示行号
                    pagination: true,                //是否分页
                    onHeaderContextMenu: function (e) {  // 右键菜单（列筛选功能,不需要可删除）
                        createColumnMenu(e);
                    },
                    onResizeColumn: function () {
                        // 序列化到服务器
                        var currGridOptions = $(currentGrid()).datagrid("options");
                        var domainUrl = options.requestUrl;
                        // 取第一个
                        var columns = currGridOptions.columns[0];
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
                            if (column.width) {
                                json += column.width;
                            } else if (column.checkbox) {
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
                    },
                    //定义表格列
                    columns: [columnNew, options.grid.column1, options.grid.column2]
                };

                // 继承默认
                var gridOptions = $.extend({}, defaults, options.grid);
                // 如有子标签，继承子标签
                gridOptions = $.extend(gridOptions, defaultsSub, options.grid);
                var towSub = options.grid.towSub;
                $(currentGrid(towSub)).datagrid(gridOptions).datagrid("columnMoving");
            });

        }

        exports.makeCustomColumn = makeCustomColumn;

        // 加载成功执行获得列的功能
        function getServerColumnMenu(domainUrl) {
            $.post(ctx + "/columnCustom/findCurrCustomColumn", {domainUrl: domainUrl}, function (data) {
                if (data) {
                    var columnConf = data.columnConf;
                    if (columnConf) {
                        var json = JSON.parse(columnConf);
                        var tableId = currentGrid();

                        for (var o in json) {
                            if (json[o]) {
                                $(tableId).datagrid('showColumn', o);
                            } else {
                                $(tableId).datagrid('hideColumn', o);
                            }
                        }
                    }
                }
            });
        }

        exports.getServerColumnMenu = getServerColumnMenu;

        // 自动加上3个点
        function autoAdd3dot(value) {
            return "<div style='overflow:hidden;white-space:nowrap;text-overflow:ellipsis;' title='" + value + "'>" + value + "</div>";
        }

        exports.autoAdd3dot = autoAdd3dot;


        // 执行产品流量统计搜索函数
        function timeSearch(event) {
        	var curr = event.data.curr;
            $(currentGrid()).datagrid('reload', normalSearch(curr));
        }
        // 普通搜索返回json
        function normalSearch(curr) {
            var $startTime = $(curr + " #startTime");
            var startTime = $startTime.val();
            var $endTime = $(curr + " #endTime");
            var endTime = $endTime.val();
            // 组合普通搜索的JSON数据
            var jsonStr = "{\"map\":{\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\"}}";
            return JSON.parse(jsonStr);
        }

        // 执行普通搜索函数
        function doNormalSearch() {
            var currentContentString = currentContent();
            searchTypeJson[currentContentString] = 0;
            $(currentGrid()).datagrid('reload', getNormalSearchJson(currentContentString));
        }

        // 普通搜索返回json
        function getNormalSearchJson(currentContentString) {
            var $normalSearch = $(currentContentString + " #normalSearch");
            var normalName = $normalSearch.attr("name");
            var normalVal = $normalSearch.val().trim();
            // 组合普通搜索的JSON数据
            var jsonStr = "{\"map\":{\"" + normalName + "\":\"" + normalVal + "\"}}";
            return JSON.parse(jsonStr);
        }

        // 执行高级搜索函数
        function doAdvSearch(currentContentString) {
            searchTypeJson[currentContentString] = 1;
            $(currentGrid()).datagrid('reload', getAdvSearchJson(currentContentString));
        }

        // 普通搜索返回json
        function getAdvSearchJson(currentContentString) {
            // 获得当前内容所在的popover的index
            var popoverId = advSearchIndex[currentContentString];

            // 搜索高级搜索(实际值已经放在webui-popover-content)下面的所有input标签
            var $inputs = $("body > #" + popoverId + " :input");
            // 组合搜索条件
            var jsonStr = "{\"map\":{";
            var flag = true;
            $inputs.each(function () {
                var keyName = $(this).attr("name");
                var keyVal = $(this).val();
                // 过滤条件为空的, 不提交到服务器
                if (keyVal && flag) {  // 第一次了, 前面不加,
                    // 组合json字符
                    jsonStr += "\"" + keyName + "\":"; // "key":
                    jsonStr += "\"" + keyVal + "\"";  // "value"
                    flag = false;
                } else if (keyVal) {  // 不是第一次了, 前面加,
                    jsonStr += ",";
                    jsonStr += "\"" + keyName + "\":"; // "key":
                    jsonStr += "\"" + keyVal + "\"";  // "value"
                }
            });
            jsonStr += "}}";
            // 转化为json, 并返回
            return JSON.parse(jsonStr);
        }

        // 导出公用
        function exportWithSearch(options) {
            // 序列化配置文件到服务器
            var dataGridOptions = $(currentGrid()).datagrid("options");
            var url = dataGridOptions.url;
            var domainUrl = url.replace(ctx, "");

            // 去除？后的字符
            var lastNum = domainUrl.indexOf('?');
            if (lastNum) {
                domainUrl = domainUrl.substr(0, lastNum);
            }

            // 取第一个
            var columns = dataGridOptions.columns[0];
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
                if (column.width) {
                    json += column.width;
                } else if (column.checkbox) {
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

            // 请求
            var config = $.extend(true, {method: 'post'}, options);
            var $iframe = $('<iframe id="down-file-iframe" />');
            var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
            $form.attr('action', config.url);

            // 获得order 和sort
            var sort = dataGridOptions.sortName;
            var order = dataGridOptions.sortOrder;
            if (sort && order) {
                $form.append('<input type="hidden" name="sort" value="' + sort + '" >');
                $form.append('<input type="hidden" name="order" value="' + order + '" >');
            }

            // 获得搜索条件
            var map;
            var currentContentString = currentContent();
            var searchType = searchTypeJson[currentContentString];
            if (searchType == 0) {
                map = getNormalSearchJson(currentContentString).map;
            } else if (searchType == 1) {
                map = getAdvSearchJson(currentContentString).map;
            } else if(searchType == 2){
            	map = normalSearch(currentContentString).map;
            }
            for (var key in map) {
                $form.append('<input type="hidden" name=map[' + key + ']" value="' + map[key] + '" >');
            }
            $iframe.append($form);
            $(document.body).append($iframe);
            $form[0].submit();
            $iframe.remove();
        }

        exports.exportWithSearch = exportWithSearch;
        // 设置为普通搜索
        exports.setNormalSearch = function () {
            var currentContentString = currentContent();
            searchTypeJson[currentContentString] = 0;
        };

        // 定时刷新Grid
        function flushCurrentGridTimer(time, towSub) {
            var currentGridId = currentGrid(towSub);
            // 判断是否已经存在定时器。存在就不在次刷新
            var isExistTimer = false;
            for (var i = 0; i < flushPageArray.length; i++) {
                if (flushPageArray[i] === currentGridId) {
                    isExistTimer = true;
                }
            }

            if (!isExistTimer) {
                flushPageArray.push(currentGridId);
                if (towSub) {//包含子标签加子标签
                    setInterval("flushGridByTowSub('" + currentGridId + "','" + towSub + "')", time);
                } else {
                    setInterval("flushGrid('" + currentGridId + "')", time);
                }
            }

        }

        exports.flushCurrentGridTimer = flushCurrentGridTimer;

        /**
         * 刷新dataGrid
         */
        function flushDataGrid(towSub, dataGridClass) {
            var currDataGridClass;
            if (null == dataGridClass || "" == dataGridClass)
                currDataGridClass = Global_Grid_Class;
            $dataGrid = $(currentGrid(towSub));
            $dataGrid.datagrid('uncheckAll');//刷新前，取消选择
            $dataGrid.datagrid('reload');
            //setTimeout( '$(\"' + currentGrid() +'\").bootstrapTable(\'resetView\')' ,400);
        }

        /**
         * 取消选择Grid
         */
        function unCheckAllDataGrid(dataGridClass) {
            var currDataGridClass;
            if (null == dataGridClass || "" == dataGridClass)
                currDataGridClass = Global_Grid_Class;
            $dataGrid = $(currentGrid());
            $dataGrid.datagrid('uncheckAll');//刷新前，取消选择
        }

        // 自动过滤input text 前后空格
        function autoTrim() {
            $("form:last :input:not(:file)").each(function () {
                var val = $(this).val();
                $(this).val($.trim(val));
            });
        }

        // 转化为只读
        function turnToRead() {
            try {

                $("form:last input").each(function (index, obj) {
                    if (obj.type != "hidden" && obj.type != "submit" && obj.type != "checkbox" && obj.type != "radio" && obj.type != "button") {
                        if (obj.type == "file") {
                            if (obj.value != null) {
                                $(obj).after("<input class='form-control _add_hide' readonly value='" + obj.value + "'/>");
                            } else {
                                $(obj).after("<input class='form-control _add_hide' readonly value='" + "未选择文件" + "'/>");
                            }
                        } else if ($(this).attr("name") == "lon" || $(this).attr("name") == "lat") {
                            $(obj).after("<nobr><label class='form-control _add_hide' style='width: 49.5%;float:left;'>" + obj.value + "</label></nobr>");
                        } else if ($(this).attr("name") == "provinceStr") {
                            $(obj).after("<input class='form-control _add_hide' style='width: 32.5%;float:left;margin-right:6px;' readonly value='" + $(obj).val() + "'/>");
                            $(obj).hide();
                        } else if ($(this).attr("name") == "cityStr") {
                            $(obj).after("<input class='form-control _add_hide' style='width: 32.5%;float:left;margin-right:6px;' readonly value='" + $(obj).val() + "'/>");
                            $(obj).hide();
                        } else if ($(this).attr("name") == "areaIdStr") {
                            $(obj).after("<input class='form-control _add_hide' style='width: 32.5%;' readonly value='" + $(obj).val() + "'/>");
                            $(obj).hide();
                        } else if ($(this).attr("name") == "str") {

                            $(obj).after("<input class='form-control _add_hide' style='width: 100%;' readonly value='" + $('#shenghui').val() + $(obj).val() + "'/>");
                            $(obj).hide();
                        } else if ($(this).attr("id") == "address") {
                            $(obj).hide();
                        } else if ($(this).attr("id") == "fileName") {
                            $("#clearl").hide();
                            var newFile = $("#myFile").val();
                            if (newFile == '') {
                                var fileName = $("#fileName").val();
                                $(obj).after("<input class='form-control _add_hide' readonly value='" + fileName + "'/>");
                            } else {
                                $(obj).after("<input class='form-control _add_hide' readonly value='" + newFile + "'/>");
                            }

                            $("#upFile").hide();
                        } else {
                            $(obj).after("<input class='form-control _add_hide' readonly value='" + obj.value + "'/>");
                        }
                        $(obj).hide();
                    } else if ($(obj).attr("id") == "mapClick") {
                        $(obj).val("1");
                    }
                    else if (obj.type == "checkbox") {
                        $(obj).parent().parent().hide();
                        $(obj).hide().next().hide();
                        if (obj.checked) {
                            $(obj).parent().parent().after("<input class='form-control _add_hide' readonly value='" + $(obj).next().html() + "'/>");
                        }
                    }
                    else if (obj.type == "radio") {
                        $(obj).parent().parent().hide();
                        $(obj).hide().next().hide();
                        if (obj.checked) {
                            $(obj).parent().parent().after("<input class='form-control _add_hide' readonly value='" + $(obj).next("span").html() + "'/>");
                        }
                    }
                });
                // 隐藏select2
                $("form:last .select2-container").addClass("hidden");

                $("form:last textarea").each(function (index, obj) {
                    $(this).attr("readonly", "readonly");
                });
                $("form:last #search").each(function (index, obj) {
                    $(obj).hide();
                });
                $("form:last #download").each(function (index, obj) {
                    $(obj).hide();
                });
                $("form:last #nowFile").each(function (index, obj) {
                    $(obj).hide();
                });
                $("form:last #clearl").each(function (index, obj) {
                    $(obj).hide();
                });
                $("form:last select").each(function (index, obj) {
                    if ($(this).attr("name") == "province") {
                        $(obj).after("<input class='form-control _add_hide' style='width: 32.5%;float:left;margin-right:6px;' readonly value='" + $("option:selected", obj).text() + "'/>");
                        $(obj).hide();
                    } else if ($(this).attr("name") == "city") {
                        $(obj).after("<input class='form-control _add_hide' style='width: 32.5%;float:left;margin-right:6px;' readonly value='" + $("option:selected", obj).text() + "'/>");
                        $(obj).hide();
                    } else if ($(this).attr("name") == "areaId") {
                        $(obj).after("<input class='form-control _add_hide' style='width: 32.5%;' readonly value='" + $("option:selected", obj).text() + "'/>");
                        $(obj).hide();
                    } else if ($(this).attr("name") == "shenghui") {
                        /*$(obj).after("<input class='form-control _add_hide' style='width: 10%;' readonly value='" + $("option:selected", obj).text() + "'/>");*/
                        $(obj).hide();
                    } else {
                        $(obj).after("<input class='form-control _add_hide' readonly value='" + $("option:selected", obj).text() + "'/>");
                        $(obj).hide();
                    }
                });

                $("form:last td img").each(function (index, obj) {
                    $(obj).hide();
                });

            } catch (e) {
            }
        }

        //转为可编辑
        function turnToEdit() {
            try {

                $("._add_hide").remove(); // 删除只读的状态
                $("._modal_left_msg").remove(); // 删除模态框左下角的提示信息

                var obj = $("form:last").get(0); // 获得dom
                if (obj) {
                    obj.reset(); //重置form
                }
                $("form:last input").each(function (index, obj) {
                    if (obj.type != "hidden" && obj.type != "submit" && obj.type != "checkbox" && obj.type != "radio" && obj.type != "button") {
                        $(obj).show();
                    }
                    else if ($(obj).attr("id") == "mapClick") {
                        $(obj).val("0");
                    }
                    else if (obj.type == "checkbox") {
                        $(obj).parent().parent().show();
                        $(obj).show().next().show();
                    }
                    else if (obj.type == "radio") {
                        $(obj).parent().parent().show();
                        $(obj).show().next().show();
                    }
                });

                $("form:last textarea").each(function (index, obj) {
                    $(obj).removeAttr("readonly");
                });
                $("form:last select").each(function (index, obj) {
                    if ($(this).attr("name") == "areaid" || $(this).attr("name") == "city") {
                        $(this).html(" <option value='' >--请选择--</option>");
                    }
                    $(obj).show();
                });
                $("form:last #search").each(function (index, obj) {
                    $(obj).show();
                });
                $("form:last #clearl").each(function (index, obj) {
                    $(obj).show();
                });
                $("form:last td img").each(function (index, obj) {
                    $(obj).show();
                });

                // 显示select2. 并初始化
                $("form:last .select2-container").each(function (index, obj) {
                    $(obj).removeClass("hidden");
                    $(obj).prev("select").find("option:selected").removeAttr("selected");
                    $(obj).prev("select").select2();
                });
            } catch (e) {
            }
        }

        //继续编辑（下一条）
        function editNext(domainUrl) {
            var obj = $("form:last").get(0);
            if (obj) {
                obj.reset();
            }
            turnToEdit();
            if (g_ids_Index >= 0 && g_ids_Index < g_ids.length) {
                g_ids_Index = g_ids_Index + 1;
                loadData2Edit(g_ids[g_ids_Index], domainUrl);
            }
            return false;
        }

        //继续编辑（上一条）
        function editBefore(url) {
            var obj = jQuery("form:last").get(0);
            if (obj) {
                obj.reset();
            }
            turnToEdit();
            if (g_ids_Index >= 0 && g_ids_Index < g_ids.length) {
                g_ids_Index = g_ids_Index - 1;
                if (g_ids_Index < 0) {
                    g_ids_Index = 0;
                }
                loadData2Edit(g_ids[g_ids_Index], url);
            }
            return false;
        }

        // 加载编辑的内容
        function loadData2Edit(guid, url) {
            url = url + "?guid=" + guid;
            $(".bootstrap-dialog-message").load(url);
        }

        // 查看详情（下一条）
        function detailNext(domainUrl) {
            var obj = $("form:last").get(0);
            if (obj) {
                obj.reset();
            }
            //turnToEdit();
            if (g_ids_Index >= 0 && g_ids_Index < g_ids.length) {
                g_ids_Index = g_ids_Index + 1;
                loadData2Detail(g_ids[g_ids_Index], domainUrl);
            }
            return false;
        }

        // 查看详情（上一条）
        function detailBefore(domainUrl) {
            var obj = jQuery("form:last").get(0);
            if (obj) {
                obj.reset();
            }
            //turnToEdit();
            if (g_ids_Index >= 0 && g_ids_Index < g_ids.length) {
                g_ids_Index = g_ids_Index - 1;
                if (g_ids_Index < 0) {
                    g_ids_Index = 0;
                }
                loadData2Detail(g_ids[g_ids_Index], domainUrl);
            }
            return false;
        }

        // 加载详情的内容
        function loadData2Detail(guid, domainUrl) {
            var url = ctx + domainUrl + '/detail?guid=' + guid;
            $(".bootstrap-dialog-message").load(url);
        }

        /**
         * 更新方法
         */
        function updateAjaxSubmitFun(dialog, $form, guids, domainUrl, fileId) {
            $form.unbind("valid.form");//unbind验证,防止重复提交
            $form.bind("valid.form", function (e, form) {//bind验证规则
                if (!fileId) {
                    var options = {
                        beforeSubmit: showRequest,  //提交前处理
                        success: showResponse,  //处理完成
                        dataType: 'json'
                    };

                    $form.ajaxSubmit(options);//ajax提交
                } else {
                    var boo = getFileSize(fileId);
                    if (boo) {
                        var options = {
                            beforeSubmit: showRequest,  //提交前处理
                            success: showResponse,  //处理完成
                            dataType: 'json'
                        };

                        $form.ajaxSubmit(options);//ajax提交
                    }
                }


                /**
                 * 提交前的操作
                 */
                function showRequest(formData, jqForm, options) {
                    dialog.enableButtons(false);
                    return true;
                }

                /**
                 * 提交成功后的操作
                 */
                function showResponse(responseText, statusText, xhr, $form) {
                    dialog.enableButtons(true);
                    $("._modal_left_msg").remove();  // 移除以前显示的信息
                    if (!responseText.status) {
                        //提交失败
                        dialog.getModalFooter().prepend('<div class="pull-left text-danger _modal_left_msg">' + responseText.msg + '</div>');
                    } else {
                        //提交成功
                        // 显示信息
                        dialog.getModalFooter().prepend('<div class="pull-left text-success _modal_left_msg"> 修改成功!</div>');

                        var buttons = $(".bootstrap-dialog-footer-buttons > button");
                        if (g_ids[g_ids_Index - 1] != undefined) {
                            $(buttons[0]).show(); // 显示编辑上一条
                        } else {
                            $(buttons[0]).hide(); // 隐藏编辑上一条
                        }
                        if (g_ids[g_ids_Index + 1] != undefined) {
                            $(buttons[1]).show(); // 显示编辑下一条
                        } else {
                            $(buttons[1]).hide(); // 隐藏编辑下一条
                        }
                        $(buttons[3]).hide(); // 隐藏提交按钮

                        turnToRead();              // 转化为只读
                    }
                }
            });
            //触发验证
            $form.trigger("validate");

        }

        /**
         * 获得所有的选择的行，返回row[]
         */
        function getSelections(dataGridCssName) {
            if (!dataGridCssName) {
                return $(currentGrid()).datagrid('getSelections');
            } else {
                return $('#' + Addtabs.activeTabId() + " ." + dataGridCssName).datagrid('getSelections');
            }
        }

        // 判断上传文件的大小
        // fileId 上传文件组件的ID
        function getFileSize(fileId) {
            var maxsize = 30 * 1024 * 1024;//30M
            var errMsg = "上传的附件大小不能超过30M！";
            var tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过30M，建议使用FireFox、Chrome、360浏览器。";
            var browserCfg = {};
            var ua = window.navigator.userAgent;
            if (ua.indexOf("Firefox") >= 1) {
                browserCfg.firefox = true;
            } else if (ua.indexOf("Chrome") >= 1) {
                browserCfg.chrome = true;
            }
            try {
                var obj_file = document.getElementById(fileId);
                if (obj_file.value == "") {
                    return true;
                }
                var filesize = 0;
                if (browserCfg.firefox || browserCfg.chrome) {
                    filesize = obj_file.files[0].size;
                } else {
                    exports.customAlert("提示", tipMsg);
                    return true;
                }
                if (filesize == -1) {
                    exports.customAlert("提示", tipMsg);
                    return false;
                } else if (filesize > maxsize) {
                    exports.customAlert("提示", errMsg);
                    return false;
                } else {
                    return true;
                }
            } catch (e) {
                alert(e);
            }

        }

        /**
         * 销售量数据管理添加
         * @param data json
         */
        function addSalesDate(data) {
            if (!data.dialogType) {
                data.dialogType = Global_Dialog_Type;
            }
            if (!data.size) {
                data.size = bootstrapDialog.SIZE_NORMAL;
            }
            if (!data.title) {
                data.title = "添加";
            }
            if (!data.url) {
                alert("url不能为空!");
            }
            bootstrapDialog.show({
                title: data.title,
                type: data.dialogType,
                size: data.size,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);
                    return $message;
                },
                data: {
                    'pageToLoad': ctx + data.url
                },
                buttons: [
                    {
                        label: '关闭',
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            dialog.close();
                            flushDataGrid(); // 重新刷新当前dataGrid
                        }
                    },
                    {
                        label: '提交',
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            var customType = $("#customType").val();
                            var salesMonth = $("#salesMonth").val();
                            var startCount = $("#startCount").val();
                            var endCount = $("#endCount").val();
                            if (customType == '') {
                                //并给出错误提示
                                bootstrapDialog.show({
                                    title: "提示",
                                    type: 'type-default',
                                    size: 'size-normal',
                                    draggable: true,
                                    closable: false,
                                    message: '请选择配送方式！',
                                    buttons: [{
                                        label: '关闭',
                                        action: function (dialogs) {
                                            dialogs.close()
                                        }
                                    }]
                                });
                                return;
                            }
                            if (salesMonth == '') {
                                //并给出错误提示
                                bootstrapDialog.show({
                                    title: "提示",
                                    type: 'type-default',
                                    size: 'size-normal',
                                    draggable: true,
                                    closable: false,
                                    message: '请选择月份！',
                                    buttons: [{
                                        label: '关闭',
                                        action: function (dialogs) {
                                            dialogs.close()
                                        }
                                    }]
                                });
                                return;
                            }
                            if (startCount == '') {
                                //并给出错误提示
                                bootstrapDialog.show({
                                    title: "提示",
                                    type: 'type-default',
                                    size: 'size-normal',
                                    draggable: true,
                                    closable: false,
                                    message: '请选择统计周期起始日期！',
                                    buttons: [{
                                        label: '关闭',
                                        action: function (dialogs) {
                                            dialogs.close()
                                        }
                                    }]
                                });
                                return;
                            }
                            if (endCount == '') {
                                //并给出错误提示
                                bootstrapDialog.show({
                                    title: "提示",
                                    type: 'type-default',
                                    size: 'size-normal',
                                    draggable: true,
                                    closable: false,
                                    message: '请选择统计周期结束日期！',
                                    buttons: [{
                                        label: '关闭',
                                        action: function (dialogs) {
                                            dialogs.close()
                                        }
                                    }]
                                });
                                return;
                            }
                            var jsonStr = serializeJson($("#salesForm"));
                            $form = $("form:last");

                            autoTrim();  // 自动trim

                            $form.unbind("valid.form");//unbind验证,防止重复提交
                            $form.bind("valid.form", function (e, form) {//bind验证规则
                                $.ajax({
                                    url: ctx + '/salesDate/addSalesData?customType=' + customType + "&salesMonth=" + salesMonth + "&startDate=" + startCount + "&endDate=" + endCount,
                                    type: "POST",
                                    contentType: 'application/json;charset=utf-8', //设置请求头信息
                                    dataType: "json",
                                    data: jsonStr,
                                    success: function (data) {
                                        if (data.status) {
                                            // 获得按钮组
                                            var buttons = jQuery(dialog.getModalFooter()).find(" .bootstrap-dialog-footer-buttons > button");
                                            // 显示信息
                                            jQuery("._modal_left_msg").remove();  // 移除以前显示的信息
                                            dialog.getModalFooter().prepend('<div class="pull-left text-success _modal_left_msg"> 添加成功!</div>');
                                            jQuery(buttons[1]).hide(); // 隐藏提交
                                            //转为只读
                                            $("#customType").attr("disabled", "disabled");
                                            $("#salesMonth").attr("disabled", "disabled");
                                            $("#startCount").attr("disabled", "disabled");
                                            $("#endCount").attr("disabled", "disabled");
                                            $("#adds").hide();
                                            $("input[name='startDate']").attr("disabled", "disabled");
                                            $("input[name='endDate']").attr("disabled", "disabled");
                                            $("input[name='sellingPrice']").attr("disabled", "disabled");
                                            $("input[name='salesVolume']").attr("disabled", "disabled");
                                            $("input[name='amoun']").attr("disabled", "disabled");
                                            $(".tdWidth7").hide();
                                        } else {
                                            bootstrapDialog.show({
                                                title: "提示",
                                                type: 'type-default',
                                                size: 'size-normal',
                                                draggable: true,
                                                closable: false,
                                                message: data.msg,
                                                buttons: [{
                                                    label: '关闭',
                                                    action: function (dialogs) {
                                                        dialogs.close()
                                                    }
                                                }]
                                            });
                                        }

                                    },
                                    error: function (res) {
                                        //提交失败
                                        jQuery("._modal_left_msg").remove();  // 移除以前显示的信息
                                        dialog.getModalFooter().prepend('<div class="pull-left text-danger _modal_left_msg">系统错误，请联系管理员！</div>');
                                    }
                                });
                            });
                            //触发验证
                            $form.trigger("validate");

                        }
                    }
                ],
                onshown: function () {
                    var buttons = $(".bootstrap-dialog-footer-buttons > button");
                    $(".modal-body").scrollTop(0);
                }
            })
        }

        exports.addSalesDate = addSalesDate;
        /**
         * 自定义添加
         * @param data json
         */
        function addCommonCustom(data) {
            if (!data.dialogType) {
                data.dialogType = Global_Dialog_Type;
            }
            if (!data.size) {
                data.size = bootstrapDialog.SIZE_NORMAL;
            }
            if (!data.title) {
                data.title = "添加";
            }
            if (!data.url) {
                alert("url不能为空!");
            }
            bootstrapDialog.show({
                title: data.title,
                type: data.dialogType,
                size: data.size,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);
                    return $message;
                },
                data: {
                    'pageToLoad': ctx + data.url
                },
                buttons: [
                    {
                        label: '继续添加',
                        action: function (dialog) {
                            var buttons = $(".bootstrap-dialog-footer-buttons > button");
                            $(buttons[0]).hide(); // 隐藏继续添加
                            $(buttons[2]).show(); // 显示提交
                            turnToEdit();              // 转化为可编辑
                        }
                    },
                    {
                        label: '关闭',
                        action: function (dialog) {
                            if (typeof(data.closeBtnFun) != 'function') {
                                dialog.close();  // 关闭模态框
                                flushDataGrid(); // 重新刷新当前dataGrid
                            } else {
                                // 执行关闭按钮事件
                                if (data.closeBtnFun()) {
                                    dialog.close();  // 关闭模态框
                                    flushDataGrid(); // 重新刷新当前dataGrid
                                }
                            }
                        }
                    },
                    {
                        label: '提交',
                        //            hotkey: 13, // 快捷键,回车
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            $form = $("form:last");

                            autoTrim();  // 自动trim

                            $form.unbind("valid.form");//unbind验证,防止重复提交
                            $form.bind("valid.form", function (e, form) {//bind验证规则
                                if (!data.fileId) {
                                    var options = {
                                        beforeSubmit: showRequest,  //提交前处理
                                        success: showResponse,  //处理完成
                                        dataType: 'json'
                                    };

                                    $form.ajaxSubmit(options);//ajax提交
                                } else {
                                    var boo = getFileSize(data.fileId);
                                    if (boo) {
                                        var options = {
                                            beforeSubmit: showRequest,  //提交前处理
                                            success: showResponse,  //处理完成
                                            dataType: 'json'
                                        };

                                        $form.ajaxSubmit(options);//ajax提交
                                    }
                                }
                                /**
                                 * 提交前的操作
                                 */
                                function showRequest(formData, jqForm, options) {
                                    dialog.enableButtons(false);
                                    if (typeof(data.beforeSubmit) != 'function') {
                                        return true;
                                    } else {
                                        return data.beforeSubmit(formData, jqForm, options);
                                    }
                                }

                                /**
                                 * 提交成功后的操作
                                 */
                                function showResponse(responseText, statusText, xhr, $form) {
                                    dialog.enableButtons(true);
                                    jQuery("._modal_left_msg").remove();  // 移除以前显示的信息
                                    if (!responseText.status) {
                                        //提交失败
                                        dialog.getModalFooter().prepend('<div class="pull-left text-danger _modal_left_msg">' + responseText.msg + '</div>');
                                    } else {
                                        //提交成功
                                        // 显示信息
                                        dialog.getModalFooter().prepend('<div class="pull-left text-success _modal_left_msg"> 添加成功!</div>');
                                        // 转化form 为只读
                                        turnToRead();
                                        // 获得关闭按钮, 转化为继续添加
                                        var buttons = jQuery(dialog.getModalFooter()).find(" .bootstrap-dialog-footer-buttons > button");
                                        jQuery(buttons[0]).show(); // 显示继续添加
                                        jQuery(buttons[2]).hide(); // 隐藏提交

                                    }
                                }
                            });
                            //触发验证
                            $form.trigger("validate");
                        }
                    }
                ],
                onshown: function () {
                    var buttons = $(".bootstrap-dialog-footer-buttons > button");
                    $(buttons[0]).hide();
                    $(".modal-body").scrollTop(0);
                }
            })
        }

        exports.addCommonCustom = addCommonCustom;
        // 导出添加的公共方法
        exports.addCommon = function (title, domainUrl, fileId) {
            addCommonCustom(
                {
                    title: title,
                    url: domainUrl + "/add",
                    fileId: fileId
                }
            );
        };
        /**
         * 自定义编辑
         * @param data json
         */
        function editCommonCustom(data) {
            if (!data.dialogType) {
                data.dialogType = Global_Dialog_Type;
            }
            if (!data.size) {
                data.size = bootstrapDialog.SIZE_NORMAL;
            }
            if (!data.title) {
                data.title = "编辑";
            }
            if (!data.url) {
                alert("url不能为空!");
            }
            //id为空。从dataGrid获取第一个值
            if (!data.guids) {
                // 定义为数组
                var rows = getSelections();
                if (rows.length == 0) {
                    bootstrapDialog.show({
                        title: "提示",
                        type: bootstrapDialog.TYPE_DEFAULT,
                        message: "请选择需要操作的记录",
                        buttons: [{
                            label: '关闭',
                            action: function (dialog) {
                                dialog.close()
                            }
                        }]
                    });
                    return;
                }
                g_ids = [];      // 清空
                g_ids_Index = 0; // 重置为0

                // 添加到数组中
                for (var i = 0; i < rows.length; i++) {
                    g_ids[i] = rows[i].guid;
                }
            }

            //加载第一个值
            bootstrapDialog.show({
                title: data.title,
                type: data.dialogType,
                size: data.size,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);
                    return $message;
                },
                data: {
                    'pageToLoad': ctx + data.url + '?guid=' + g_ids[g_ids_Index]
                },
                buttons: [
                    {
                        label: '编辑上一条',
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            var buttons = $(".bootstrap-dialog-footer-buttons > button");
                            $(buttons[0]).hide(); // 编辑上一条
                            $(buttons[1]).hide(); // 编辑下一条
                            $(buttons[2]).show(); // 关闭
                            $(buttons[3]).show(); // 提交
                            editBefore(ctx + data.url);
                        }
                    },
                    {
                        label: '编辑下一条',
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            var buttons = $(".bootstrap-dialog-footer-buttons > button");
                            $(buttons[0]).hide(); // 编辑上一条
                            $(buttons[1]).hide(); // 编辑下一条
                            $(buttons[2]).show(); // 关闭
                            $(buttons[3]).show(); // 提交
                            editNext(ctx + data.url);
                        }
                    },
                    {
                        label: '关闭',
                        action: function (dialog) {
                            if (typeof(data.closeBtnFun) != 'function') {
                                dialog.close();  // 关闭模态框
                                flushDataGrid(); // 重新刷新当前dataGrid
                            } else {
                                // 执行关闭按钮事件
                                if (data.closeBtnFun()) {
                                    dialog.close();  // 关闭模态框
                                    flushDataGrid(); // 重新刷新当前dataGrid
                                }
                            }
                        }
                    },
                    {
                        label: '提交',
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            $form = $("form:last");
                            autoTrim(); // 自动trim
                            updateAjaxSubmitFun(dialog, $form, data.guids, "", data.fileId);//处理提交
                        }
                    }
                ],
                onshown: function () {
                    var buttons = $(".bootstrap-dialog-footer-buttons > button");
                    $(buttons[0]).hide();
                    $(buttons[1]).hide();
                    $(".modal-body").scrollTop(0);
                }
            });
        }

        exports.editCommonCustom = editCommonCustom;
        // 编辑公共
        exports.editCommon = function (title, domainUrl, fileId) {
            editCommonCustom(
                {
                    title: title,
                    url: domainUrl + "/update",
                    fileId: fileId
                }
            );
        };

        // 详情自定义方法
        function detailCommonCustom(data) {
            if (!data.dialogType) {
                data.dialogType = Global_Dialog_Type;
            }
            if (!data.size) {
                data.size = bootstrapDialog.SIZE_NORMAL;
            }
            if (!data.title) {
                data.title = "详情";
            }
            if (!data.domainUrl) {
                alert("domainUrl不能为空!");
            }
            //guids为空。从dataGrid获取
            if (!data.guids) {
                // 定义为数组
                var rows = getSelections();
                if (rows.length == 0) {
                    bootstrapDialog.show({
                        title: "提示",
                        type: bootstrapDialog.TYPE_DEFAULT,
                        message: "请选择需要操作的记录",
                        buttons: [{
                            label: '关闭',
                            action: function (dialog) {
                                dialog.close()
                            }
                        }]
                    });
                    return;
                }
                g_ids = [];      // 清空
                g_ids_Index = 0; // 重置为0

                // 添加到数组中
                for (var i = 0; i < rows.length; i++) {
                    g_ids[i] = rows[i].guid;
                }
            }
            //id已经有值了,提交
            bootstrapDialog.show({
                title: data.title,
                type: data.dialogType,
                size: data.size,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);
                    return $message;
                },
                data: {
                    'pageToLoad': ctx + data.domainUrl + '/detail?guid=' + g_ids[g_ids_Index]
                },
                buttons: [
                    {
                        label: '查看上一条',
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            detailBefore(data.domainUrl);
                            var buttons = $(".bootstrap-dialog-footer-buttons > button");
                            if (g_ids[g_ids_Index - 1] != undefined) {
                                $(buttons[0]).show(); // 显示编辑上一条
                            } else {
                                $(buttons[0]).hide(); // 隐藏编辑上一条
                            }
                            if (g_ids[g_ids_Index + 1] != undefined) {
                                $(buttons[1]).show(); // 显示编辑下一条
                            } else {
                                $(buttons[1]).hide(); // 隐藏编辑下一条
                            }
                            $(buttons[2]).show(); // 关闭
                        }
                    },
                    {
                        label: '查看下一条',
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            detailNext(data.domainUrl);
                            var buttons = $(".bootstrap-dialog-footer-buttons > button");
                            if (g_ids[g_ids_Index - 1] != undefined) {
                                $(buttons[0]).show(); // 显示编辑上一条
                            } else {
                                $(buttons[0]).hide(); // 隐藏编辑上一条
                            }
                            if (g_ids[g_ids_Index + 1] != undefined) {
                                $(buttons[1]).show(); // 显示编辑下一条
                            } else {
                                $(buttons[1]).hide(); // 隐藏编辑下一条
                            }
                            $(buttons[2]).show(); // 关闭
                        }
                    },
                    {
                        label: '关闭',
                        action: function (dialog) {
                            dialog.close();  // 关闭模态框
                        }
                    }, data.exportFun
                ]
                , onshown: function () { // 显示时隐藏前两个
                    var buttons = $(".bootstrap-dialog-footer-buttons > button");
                    if (g_ids[g_ids_Index - 1] != undefined) {
                        $(buttons[0]).show(); // 显示编辑上一条
                    } else {
                        $(buttons[0]).hide(); // 隐藏编辑上一条
                    }
                    if (g_ids[g_ids_Index + 1] != undefined) {
                        $(buttons[1]).show(); // 显示编辑下一条
                    } else {
                        $(buttons[1]).hide(); // 隐藏编辑下一条
                    }
                    $(".modal-body").scrollTop(0);
                }
            });
        }

        exports.detailCommonCustom = detailCommonCustom;
        // 详情公共
        exports.detailCommon = function (domainUrl, guids) {
            detailCommonCustom({
                domainUrl: domainUrl,
                guids: guids
            });

        };
        //向所有预存款不足的客户发送通知
        function sendAllMessage() {
            bootstrapDialog.show({
                title: "发送催款通知",
                message: '确定向所有预存款不足的客户发送短信通知及APP通知?',
                type: Global_Dialog_Type,
                draggable: true,
                closable: false,
                buttons: [{
                    label: '取消',
                    action: function (dialog) {
                        dialog.close();
                    }
                }, {
                    label: '确定',
                    hotkey: 13, // 快捷键,回车
                    action: function (dialog) {
                        $.ajax({
                            type: 'post',
                            traditional: true,//传统方式的序列化
                            url: ctx + "/customDeposit/sendAllMessage",
                            dataType: 'json',
                            success: function () {
                                dialog.close();//关闭对话框
                                //刷新列表
                                flushDataGrid();
                            }
                        });
                    }
                }]
            });
        }

        exports.sendAllMessage = sendAllMessage;
        //发送短信
        function sendMessage(customId) {
            var msgSend, appSend;
            if (jQuery("#sendMsgType" + customId).attr("class") == "fa fa-fw fa-check") {
                msgSend = false;
            } else {
                msgSend = true;
            }
            if (jQuery("#sendAppType" + customId).attr("class") == "fa fa-fw fa-check") {
                appSend = false;
            } else {
                appSend = true;
            }
            bootstrapDialog.show({
                title: "发送催款通知",
                message: '确定向客户发送短信通知和APP通知?',
                type: Global_Dialog_Type,
                draggable: true,
                closable: false,
                buttons: [{
                    label: '取消',
                    action: function (dialog) {
                        dialog.close();
                    }
                }, {
                    label: '确定',
                    hotkey: 13, // 快捷键,回车
                    action: function (dialog) {
                        $.ajax({
                            type: 'post',
                            traditional: true,//传统方式的序列化
                            url: ctx + "/customDeposit/sendMessage",
                            dataType: 'json',
                            data: {'customId': customId, "msgSend": msgSend, "appSend": appSend},
                            success: function () {
                                dialog.close();//关闭对话框
                                //刷新列表
                                flushDataGrid();

                            }
                        });
                    }
                }]
            });
        }

        exports.sendMessage = sendMessage;

        /**
         * 自定义删除
         * @param data json
         */
        function delCommonCustom(data) {
            if (!data.dialogType) {
                data.dialogType = bootstrapDialog.TYPE_DEFAULT;
            }
            if (!data.size) {
                data.size = bootstrapDialog.SIZE_NORMAL;
            }
            if (!data.title) {
                data.title = "提示";
            }
            if (!data.url) {
                alert("url不能为空!");
            }
            //未传值,从dataGrid获得
            if (!data.ids) {
                var rows = getSelections();
                //定义ids为数组
                data.ids = [];
                //for循环,取出所有的id,并加到数组中
                for (var i = 0; i < rows.length; i++) {
                    data.ids.push(rows[i].guid);
                }
            }
            //判断取出后的结果是否为空，为空，提示用户选择数据，不为空，删除数据
            if (data.ids.length > 0) {
                //不为空，删除数据
                bootstrapDialog.show({
                    title: data.title,
                    message: '确定删除这 <span style="color:red">' + data.ids.length + ' </span>条记录?',
                    type: data.dialogType,
                    size: data.size,
                    draggable: true,
                    closable: false,
                    buttons: [{
                        label: '取消',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }, {
                        label: '确定',
                        hotkey: 13, // 快捷键,回车
                        /*cssClass: 'btn-danger',*/
                        action: function (dialog) {
                            $.ajax({
                                type: 'post',
                                traditional: true,//传统方式的序列化
                                url: ctx + data.url,
                                dataType: 'json',
                                data: {'ids': data.ids},
                                success: function (result) {
                                    if (typeof(data.delSuccessAfter) != 'function') {
                                        flushDataGrid();//更新dataGrid
                                        dialog.close();//关闭对话框
                                        //状态为false,提示用户
                                        if (result.status == false)
                                            bootstrapDialog.show({
                                                title: '提示',
                                                message: result.msg,
                                                type: bootstrapDialog.TYPE_DEFAULT,
                                                buttons: [{
                                                    label: '关闭',
                                                    action: function (dialog) {
                                                        dialog.close();
                                                    }
                                                }]
                                            });
                                    } else {
                                        // 执行关闭按钮事件
                                        if (data.delSuccessAfter()) {
                                            flushDataGrid();//更新dataGrid
                                            dialog.close();//关闭对话框
                                            //状态为false,提示用户
                                            if (result.status == false)
                                                bootstrapDialog.show({
                                                    title: '提示',
                                                    message: result.msg,
                                                    type: bootstrapDialog.TYPE_DEFAULT,
                                                    buttons: [{
                                                        label: '关闭',
                                                        action: function (dialog) {
                                                            dialog.close();
                                                        }
                                                    }]
                                                });
                                        }
                                    }
                                }
                            });
                        }
                    }]
                });
            } else {
                //为空，提示用户选择数据
                bootstrapDialog.show({
                    title: '提示',
                    message: '请选择需要操作的记录',
                    type: bootstrapDialog.TYPE_DEFAULT,
                    buttons: [{
                        label: '关闭',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }]
                });
            }
        }

        exports.delCommonCustom = delCommonCustom;
        // 删除公共
        exports.delCommon = function (domainUrl, ids) {
            delCommonCustom({
                url: domainUrl + "/del",
                ids: ids
            })

        };

        /**
         * 销售量删除
         * @param data json
         */
        function delSalesCommonCustom(data) {
            if (!data.dialogType) {
                data.dialogType = bootstrapDialog.TYPE_DEFAULT;
            }
            if (!data.size) {
                data.size = bootstrapDialog.SIZE_NORMAL;
            }
            if (!data.title) {
                data.title = "提示";
            }
            if (!data.url) {
                alert("url不能为空!");
            }
            //未传值,从dataGrid获得
            if (!data.ids) {
                var rows = getSelections();
                //定义ids为数组
                data.ids = [];
                //for循环,取出所有的id,并加到数组中
                for (var i = 0; i < rows.length; i++) {
                    data.ids.push(rows[i].guid);
                }
            }
            //判断取出后的结果是否为空，为空，提示用户选择数据，不为空，删除数据
            if (data.ids.length > 0) {
                //不为空，删除数据
                bootstrapDialog.show({
                    title: data.title,
                    message: '已生成对应的销售量对账记录，若删除后，需手动调整\"预存款管理——预存款明细记录\"里的对应实际支出记录中的金额，并在备注中说明原因！，确定删除这 <span style="color:red">' + data.ids.length + ' </span>条记录?',
                    type: data.dialogType,
                    size: data.size,
                    draggable: true,
                    closable: false,
                    buttons: [{
                        label: '取消',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }, {
                        label: '确定',
                        hotkey: 13, // 快捷键,回车
                        /*cssClass: 'btn-danger',*/
                        action: function (dialog) {
                            $.ajax({
                                type: 'post',
                                traditional: true,//传统方式的序列化
                                url: ctx + data.url,
                                dataType: 'json',
                                data: {'ids': data.ids},
                                success: function (result) {
                                    if (typeof(data.delSuccessAfter) != 'function') {
                                        flushDataGrid();//更新dataGrid
                                        dialog.close();//关闭对话框
                                        //状态为false,提示用户
                                        if (result.status == false)
                                            bootstrapDialog.show({
                                                title: '提示',
                                                message: result.msg,
                                                type: bootstrapDialog.TYPE_DEFAULT,
                                                buttons: [{
                                                    label: '关闭',
                                                    action: function (dialog) {
                                                        dialog.close();
                                                    }
                                                }]
                                            });
                                    } else {
                                        // 执行关闭按钮事件
                                        if (data.delSuccessAfter()) {
                                            flushDataGrid();//更新dataGrid
                                            dialog.close();//关闭对话框
                                            //状态为false,提示用户
                                            if (result.status == false)
                                                bootstrapDialog.show({
                                                    title: '提示',
                                                    message: result.msg,
                                                    type: bootstrapDialog.TYPE_DEFAULT,
                                                    buttons: [{
                                                        label: '关闭',
                                                        action: function (dialog) {
                                                            dialog.close();
                                                        }
                                                    }]
                                                });
                                        }
                                    }
                                }
                            });
                        }
                    }]
                });
            } else {
                //为空，提示用户选择数据
                bootstrapDialog.show({
                    title: '提示',
                    message: '请选择需要操作的记录',
                    type: bootstrapDialog.TYPE_DEFAULT,
                    buttons: [{
                        label: '关闭',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }]
                });
            }
        }

        exports.delSalesCommonCustom = delSalesCommonCustom;
        // 销售量删除
        exports.delSalesCommon = function (domainUrl, ids) {
            delSalesCommonCustom({
                url: domainUrl + "/del",
                ids: ids
            })

        };

        /**
         * 自定义对话框
         * @param title
         * @param openUrl
         * @param buttons
         * @param dialogType[ 'type-default','type-info','type-primary', 'type-success','type-warning','type-danger']
         * @param size ['size-normal', 'size-small','size-wide','size-large']
         */
        exports.customDialog = function (title, openUrl, size, dialogType, buttons) {
            if (!buttons) {
                buttons = [{
                    label: '关闭',
                    action: function (dialog) {
                        dialog.close();
                        flushDataGrid(); // 重新刷新当前dataGrid
                    }
                }]
            }
            if (!dialogType) {
                dialogType = Global_Dialog_Type;
            }
            if (!size) {
                size = bootstrapDialog.SIZE_NORMAL;
            }
            bootstrapDialog.show({
                title: title,
                type: dialogType,
                size: size,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);
                    return $message;
                },
                data: {
                    'pageToLoad': openUrl
                },
                buttons: buttons
                , onshown: function () {  // 移动到顶部
                    $(".modal-body").scrollTop(0);
                }
            });
        };

        /**
         * 自定义的alert
         * @param title 标题
         * @param content 内容
         */
        exports.customAlert = customAlert;
        function customAlert(title, content) {
            bootstrapDialog.show({
                title: title,
                type: Global_Dialog_Type,
                draggable: true,
                closable: true,
                message: content,
                buttons: [{
                    label: '关闭',
                    action: function (dialog) {
                        dialog.close();
                    }
                }]
            });
        };

        /**
         * 自定义提交
         * @param data json对象的数据
         */
        exports.linkCustomSubmit = function (data) {
            if (!data.dialogType) {
                data.dialogType = Global_Dialog_Type;
            }
            if (!data.size) {
                data.size = bootstrapDialog.SIZE_NORMAL;
            }
            bootstrapDialog.show({
                title: data.title,
                type: data.dialogType,
                size: data.size,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);
                    return $message;
                },
                data: {
                    'pageToLoad': data.url
                },
                buttons: [
                    {
                        label: '关闭',
                        action: function (dialog) {
                            dialog.close();                   // 关闭模态框
                            flushDataGrid(); // 重新刷新当前dataGrid
                        }
                    }
                ]
                , onshown: function () {  // 移动到顶部
                    $(".modal-body").scrollTop(0);
                }
            });
        };

        /**
         * 外检完成提交
         * @param data json对象的数据
         * @zhoujl
         */
        exports.checkOutSubmit = function (data) {
            if (!data.dialogType) {
                data.dialogType = Global_Dialog_Type;
            }
            if (!data.size) {
                data.size = bootstrapDialog.SIZE_NORMAL;
            }
            var newDeposit = false;
            bootstrapDialog.show({
                title: data.title,
                type: data.dialogType,
                size: data.size,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);
                    return $message;
                },
                data: {
                    'pageToLoad': data.url
                },
                buttons: [
                    {
                        label: '关闭',
                        action: function (dialog) {
                            dialog.close();                   // 关闭模态框
                            flushDataGrid(); // 重新刷新当前dataGrid
                            if (newDeposit) {
                                newDeposit=false;
                                bootstrapDialog.show({
                                    title: "提示",
                                    type: 'type-default',
                                    size: 'size-normal',
                                    draggable: true,
                                    closable: false,
                                    message: '是否添加该产品下次外检计划?',
                                    buttons: [{
                                        label: '否',
                                        action: function (dialog) {
                                            dialog.close();
                                            flushDataGrid();
                                        }
                                    },
                                        {
                                            label: '是',
                                            cssClass: Global_Dialog_Submit_Css,
                                            action: function (dialogs) {
                                                dialogs.close();
                                                exports.customSubmit({
                                                    url: ctx + "/productCheckOut/add?oldProductId=" + data.oldProductId,
                                                    title: "添加产品外检计划"
                                                });
                                            }
                                        }
                                    ]
                                });
                            }
                        }
                    },
                    {
                        label: '提交',
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            $form = $("form:last");
                            $form.unbind("valid.form");//unbind验证,防止重复提交
                            $form.bind("valid.form", function (e, form) {//bind验证规则

                                if (!data.fileId) {
                                    var options = {
                                        beforeSubmit: showRequest,  //提交前处理
                                        success: showResponse,  //处理完成
                                        dataType: 'json'
                                    };

                                    $form.ajaxSubmit(options);//ajax提交
                                } else {
                                    var boo = getFileSize(data.fileId);
                                    if (boo) {
                                        var options = {
                                            beforeSubmit: showRequest,  //提交前处理
                                            success: showResponse,  //处理完成
                                            dataType: 'json'
                                        };

                                        $form.ajaxSubmit(options);//ajax提交
                                    }
                                }

                                /**
                                 * 提交前的操作
                                 */
                                function showRequest(formData, jqForm, options) {
                                    dialog.enableButtons(false);
                                    if (typeof(data.beforeSubmit) != 'function') {
                                        return true;
                                    } else {
                                        return data.beforeSubmit(formData, jqForm, options);
                                    }
                                }

                                /**
                                 * 提交成功后的操作
                                 */
                                function showResponse(responseText, statusText, xhr, $form) {
                                    dialog.enableButtons(true);
                                    if (typeof(data.afterSubmit) == 'function') {
                                        data.afterSubmit(responseText, statusText, xhr, $form);
                                    }
                                    jQuery("._modal_left_msg").remove();  // 移除以前显示的信息
                                    if (!responseText.status) {
                                        // 提交失败
                                        dialog.getModalFooter().prepend('<div class="pull-left text-danger _modal_left_msg">' + responseText.msg + '</div>');
                                    } else {
                                        // 提交成功,  显示信息
                                        dialog.getModalFooter().prepend('<div class="pull-left text-success _modal_left_msg">' + responseText.msg + '</div>');
                                        // 隐藏提交
                                        var buttons = jQuery(".bootstrap-dialog-footer-buttons > button");
                                        jQuery(buttons[1]).hide();
                                        newDeposit = true;
                                    }
                                }
                            });
                            //触发验证
                            $form.trigger("validate");
                        }
                    }
                ]
            });
        };
        /**
         * 自定义链接查看详情
         * @param data json对象的数据
         * @zy
         */
        exports.customSubmit = function (data) {
            if (!data.dialogType) {
                data.dialogType = Global_Dialog_Type;
            }
            if (!data.size) {
                data.size = bootstrapDialog.SIZE_NORMAL;
            }
            bootstrapDialog.show({
                title: data.title,
                type: data.dialogType,
                size: data.size,
                draggable: true,
                closable: false,
                message: function (dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);
                    return $message;
                },
                data: {
                    'pageToLoad': data.url
                },
                buttons: [
                    {
                        label: '关闭',
                        action: function (dialog) {
                            dialog.close();  // 关闭模态框
                            flushDataGrid(); // 重新刷新当前dataGrid
                        }
                    },
                    {
                        label: '提交',
                        cssClass: Global_Dialog_Submit_Css,
                        action: function (dialog) {
                            $form = $("form:last");
                            $form.unbind("valid.form");//unbind验证,防止重复提交
                            $form.bind("valid.form", function (e, form) {//bind验证规则
                                if (!data.fileId) {
                                    var options = {
                                        beforeSubmit: showRequest,  //提交前处理
                                        success: showResponse,  //处理完成
                                        dataType: 'json'
                                    };

                                    $form.ajaxSubmit(options);//ajax提交
                                } else {
                                    var boo = getFileSize(data.fileId);
                                    if (boo) {
                                        var options = {
                                            beforeSubmit: showRequest,  //提交前处理
                                            success: showResponse,  //处理完成
                                            dataType: 'json'
                                        };

                                        $form.ajaxSubmit(options);//ajax提交
                                    }
                                }

                                /**
                                 * 提交前的操作
                                 */
                                function showRequest(formData, jqForm, options) {
                                    dialog.enableButtons(false);
                                    if (typeof(data.beforeSubmit) != 'function') {
                                        return true;
                                    } else {
                                        return data.beforeSubmit(formData, jqForm, options);
                                    }
                                }

                                /**
                                 * 提交成功后的操作
                                 */
                                function showResponse(responseText, statusText, xhr, $form) {
                                    dialog.enableButtons(true);
                                    if (typeof(data.afterSubmit) == 'function') {
                                        data.afterSubmit(responseText, statusText, xhr, $form, dialog);
                                    }
                                    jQuery("._modal_left_msg").remove();  // 移除以前显示的信息
                                    if (!responseText.status) {
                                        // 提交失败
                                        dialog.getModalFooter().prepend('<div class="pull-left text-danger _modal_left_msg">' + responseText.msg + '</div>');
                                    } else {
                                        // 提交成功,  显示信息
                                        dialog.getModalFooter().prepend('<div class="pull-left text-success _modal_left_msg">' + responseText.msg + '</div>');
                                        /*turnToRead();*/
                                        // 隐藏提交
                                        var buttons = jQuery(dialog.getModalFooter()).find(" .bootstrap-dialog-footer-buttons > button");
                                        jQuery(buttons[1]).hide();
                                    }
                                }
                            });
                            //触发验证
                            $form.trigger("validate");

                        }
                    }
                ]
                , onshown: function () {  // 移动到顶部
                    $(".modal-body").scrollTop(0);
                }
            });
        };
        // 获得一行
        exports.getSelectedOne = function () {
            var rows = getSelections();
            if (rows.length == 0) {
                bootstrapDialog.show({
                    title: "提示",
                    type: bootstrapDialog.TYPE_DEFAULT,
                    message: "请选择需要操作的记录",
                    buttons: [{
                        label: '关闭',
                        action: function (dialog) {
                            dialog.close()
                        }
                    }]
                });
                return null;
            }
            else if (rows.length > 1) {
                bootstrapDialog.show({
                    title: "提示",
                    type: bootstrapDialog.TYPE_DEFAULT,
                    message: "请只选择一条记录",
                    buttons: [{
                        label: '关闭',
                        action: function (dialog) {
                            dialog.close()
                        }
                    }]
                });
                flushDataGrid();
                return null;
            } else {
                return rows[0];
            }
        };

        // 获得多行
        exports.getSelections = function () {
            var rows = getSelections();
            alert(rows.length);
            if (rows.length == 0) {
                bootstrapDialog.show({
                    title: "提示",
                    type: bootstrapDialog.TYPE_DEFAULT,
                    message: "请选择需要操作的记录",
                    buttons: [{
                        label: '关闭',
                        action: function (dialog) {
                            dialog.close()
                        }
                    }]
                });
                return null;
            } else {
                return rows[0];
            }
        };

        // 获得选择的行
        exports.getSelections = getSelections;

        // 返回当前激活的tab 下的dataGrid
        function currentGrid(towSub) {
            var activeTabId = Addtabs.activeTabId();
            // 地图模式列表 三级页标签
            if (towSub) {
                var href = $('#' + activeTabId + " .subTabs-tag > .tab-content ").find(".subTabs-tag > ul .active a").attr("href");
                return '#' + activeTabId + ' .subTabs-tag .tab-content .subTabs-tag  .tab-content ' + href + ' .' + Global_Grid_Class;
            }
            // 如包含了二级标签页，返回当前二级激活的grid
            if ($('#' + activeTabId + " > div").hasClass("subTabs-tag")) {
                var href = $('#' + activeTabId + " .subTabs-tag > ul .active a").attr("href");
                if (href != 'undefined') {
                    return '#' + activeTabId + ' .subTabs-tag .tab-content ' + href + ' .' + Global_Grid_Class;
                }
            } else {
                return '#' + activeTabId + " ." + Global_Grid_Class;
            }
        }

        exports.currentGrid = currentGrid;

        // 返回当前激活的tab 下的内容
        function currentContent(towSub) {
            return Addtabs.activeTabContent(towSub);
        }

        exports.currentContent = currentContent;

        // 导出全局Grid标识class
        exports.Global_Grid_Class = Global_Grid_Class;

        // 返回添加
        exports.currentAdd = function () {
            return currentContent() + " ." + Global_Add_Class;
        };

        // 返回编辑
        exports.currentEdit = function () {
            return currentContent() + " ." + Global_Edit_Class;
        };

        // 返回详情
        exports.currentDetail = function () {
            return currentContent() + " ." + Global_Detail_Class;
        };

        // 返回删除
        exports.currentDel = function () {
            return currentContent() + " ." + Global_Del_Class;
        };

        // 返回导出
        exports.currentExport = function () {
            return currentContent() + " ." + Global_Export_Class;
        };

        // 返回
        exports.currentGoBack = function () {
            return currentContent() + " ." + Global_goBack_Class;
        };
        // 返回短信
        exports.currentBalance = function () {
            return currentContent() + " ." + Global_Balance_Class;
        };

        // 返回一个重新刷新DataGrid，调用common.flushDataGrid()即可
        exports.flushDataGrid = flushDataGrid;

        // 取消选择Guid
        exports.unCheckAllDataGrid = unCheckAllDataGrid;

        // 返回一个搜索函数
        exports.commonSearch = function () {
            // 输入框变化时, 0.61秒后立即执行搜索
            var lastKeyWord; // 存放上次搜索的关键字
            var currentContentString = currentContent();
            if ($(currentContentString + " #normalSearch").is("select")) {
                // select用change事件
                $(currentContentString + " #normalSearch").change(function () {
                    setTimeout("", 600); // 0.6秒内值改变了才进行搜索
                    if ($(this).val() != lastKeyWord) {
                        setTimeout(doNormalSearch, 1);
                        // 存入关键字
                        lastKeyWord = $(this).val();
                    }
                });
            } else {
                // input
                $(currentContentString + " #normalSearch").keyup(function () {
                    setTimeout("", 600); // 0.6秒内值改变了才进行搜索
                    if ($(this).val() != lastKeyWord) {
                        setTimeout(doNormalSearch, 1);
                        // 存入关键字
                        lastKeyWord = $(this).val();
                    }
                });
            }
            // 点击搜索按妞
            $(currentContentString + " #normalSearchBtn").click(doNormalSearch);
        };

        // 返回一个产品时间搜索搜索函数
        exports.commonTimeSearch = function () {
            var currentContentString = currentContent();
            searchTypeJson[currentContentString] = 2;
            // 点击搜索按妞
            $(currentContentString + " #normalSearchBtn").click({curr:currentContentString},timeSearch);
        };

        // 返回高级搜索函数
        exports.advSearch = function (data) {
            var currentContentString = currentContent();
            var $adv = $(currentContentString + " #advSearchBtn");
            var $advContent = $(currentContentString + " #advancedSearchDialogContainer");
            $adv.webuiPopover('destroy').webuiPopover({
                title: '高级搜索',
                html: true,
                content: $advContent.html(),
                placement: 'bottom',
                animation: 'pop',
                width: 450,
                closeable: true,
                dismissible: false // if popover can be dismissed by  outside click or escape key
            }).on("shown.webui.popover", function (e) {
                if (data) {
                    if (typeof(data.onshown) == 'function') {
                        data.onshown();
                    }
                }

                // 移除包含在jsp里的html, 做到唯一性
                $advContent.remove();

                // 点击关闭
                var $closeAdv = $("body > .in #closeAdvBtn");
                $closeAdv.unbind("click");
                $closeAdv.bind("click", function () {
                    $adv.webuiPopover('hide')
                });

                // 获得当前内容所在的popover的id
                advSearchIndex[currentContentString] = $("body > .in").attr("id");

                // 点击搜索按妞
                var $doAdv = $("body > .in #doAdvBtn");
                $doAdv.unbind("click");
                $doAdv.bind("click", function () {
                    // 执行搜索
                    doAdvSearch(currentContentString);
                    // 隐藏弹出框
                    $adv.webuiPopover('hide');
                });
                // 使select2 显示
                jQuery(".webui-popover:visible .select2AdvTag").select2();
            });

        };
        // 重制grid宽度或高度
        function resizeGrid(_width, _height) {
            var sizeObj = null;
            if (_width && _width > 0) {
                if (sizeObj == null) {
                    sizeObj = {};
                }
                sizeObj.width = _width;
            }
            if (_height && _height > 0) {
                if (sizeObj == null) {
                    sizeObj = {};
                }
                sizeObj.height = _height;
            }
            if (sizeObj != null) {
                $(currentGrid()).datagrid('resize', sizeObj);
            }
        }

        // 重制grid宽度或高度
        exports.resizeGrid = resizeGrid;

        /**
         * 自定义搜索工具，传入搜索参数json或字符格式。
         * 如{"name":"chenjie"}
         * @param json
         */
        exports.customSearch = function (json) {
            $(currentGrid()).datagrid('reload', customSearch());
            // 普通搜索返回json
            function customSearch() {
                // 组合普通搜索的JSON数据
                var jsonStr;
                if ("object" == typeof(json)) {
                    jsonStr = "{\"map\":" + JSON.stringify(json) + "}";
                } else if ("string" == typeof(json)) {
                    jsonStr = "{\"map\":" + json + "}";
                } else {
                    alert("请传入json对象或字符");
                }
                return JSON.parse(jsonStr);
            }
        };

        //二级选项卡
        exports.subTabs = function (towSub, click2Reload) {
            // 有二级选项卡才添加事件
            var activeTabsId = Addtabs.activeTabId();
            if (activeTabsId) {
                var subTab = '#' + activeTabsId + ' .subTabs-tag';
                if (towSub) {
                    subTab += ' .subTabs-tag';
                }

                if ($(subTab)) {
                    $(subTab + " > ul a").click(function () {
                        var href = $(this).attr("href");
                        var $tabContent = $(subTab + " .tab-content");

                        // 强制加载或没有加载过才加载内容
                        if (click2Reload || !$tabContent.children("div").is(href)) {
                            // 请求url
                            var dataRequestUrl = $(this).attr("data-request-url");
                            if (dataRequestUrl) {
                                // 带有入口地址的
                                $(".tab-content > #" + href.substring(1)).remove();  // 删除已经存在的Content

                                $.get(dataRequestUrl, function (data) {
                                    var $div = $("<div>");
                                    $div.attr("id", href.substring(1)); // 去掉#
                                    $div.addClass("tab-pane");
                                    $div.addClass("active");
                                    $div.append(data);
                                    // 移除已经激活的tab
                                    $tabContent.children(".active").removeClass("active");
                                    // 添加到后面
                                    $tabContent.append($div);
                                });
                            } else {
                                // 主入口. 刷新Grid
                                flushDataGrid(towSub)
                            }
                        }
                    });
                }

            }
        };

        // 返回url中的内容。 默认当前激活的标签
        function goBack(url) {
            $('#' + Addtabs.activeTabId()).load(url);
        }

        exports.goBack = goBack


        exports.addTab = addTab;
        function addTab(data) {
            Addtabs.add(data)
        }


    });

// 刷新guid
function flushGrid(gridId) {
    var content = Addtabs.activeTabContent();
    // 当前内容等于刷新的grid才刷新
    if (content === gridId.substr(0, content.length)) {
        $(gridId).datagrid('reload').datagrid('uncheckAll');
    }
}

// 刷新guid,包含二级
function flushGridByTowSub(gridId, towSub) {
    var activeTabId = Addtabs.activeTabId();
    var href = $('#' + activeTabId + " .subTabs-tag > .tab-content ").find(".subTabs-tag > ul .active a").attr("href");
    if (gridId === '#' + activeTabId + ' .subTabs-tag .tab-content .subTabs-tag  .tab-content ' + href + ' .gridContainer') {
        $(gridId).datagrid('reload').datagrid('uncheckAll');
    }

}

// 改变当前内容
function changeContent(url, towSub) {
    if (towSub) {
        $(Addtabs.activeSubTabContent()).load(url);
    } else {
        $(Addtabs.activeTabContent()).load(url);
    }
}
function serializeJson(form) {
    var jsonData1 = {};
    var serializeArray = $(form).serializeArray();
    // 先转换成{"id": ["12","14"], "name": ["aaa","bbb"], "pwd":["pwd1","pwd2"]}这种形式
    $(serializeArray).each(function () {
        if (jsonData1[this.name]!=null) {
            if ($.isArray(jsonData1[this.name])) {
                jsonData1[this.name].push(this.value);
            } else {
                jsonData1[this.name] = [jsonData1[this.name], this.value];
            }
        } else {
            jsonData1[this.name] = this.value;
        }
    });
    // 再转成[{"id": "12", "name": "aaa", "pwd":"pwd1"},{"id": "14", "name": "bb", "pwd":"pwd2"}]的形式
    var vCount = 0;
    // 计算json内部的数组最大长度
    for (var item in jsonData1) {
        var tmp = $.isArray(jsonData1[item]) ? jsonData1[item].length : 1;
        vCount = (tmp > vCount) ? tmp : vCount;
    }

    if (vCount > 1) {
        var jsonData2 = new Array();
        for (var i = 0; i < vCount; i++) {
            var jsonObj = {};
            for (var item in jsonData1) {
                jsonObj[item] = jsonData1[item][i];
            }
            jsonData2.push(jsonObj);
        }
        return JSON.stringify(jsonData2);
    } else {
        return "[" + JSON.stringify(jsonData1) + "]";
    }
}
