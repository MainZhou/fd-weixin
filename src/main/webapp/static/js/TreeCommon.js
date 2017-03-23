//树
define([ 'require', 'exports','common' ],
		function(require, exports, common) {
            var $ = require('jquery');
            var zTree;
			var rMenu;
			var url;
			var setting;
            // 查询是否获得父Id
			var queryWithParentId;
            function setSetting(url, checkBoxRadio) {
				setting = {
					async : {
						contentType : "application/json",
						enable : true,
						dataType : "json",
						type : "get",
						url : ctx + "/zTree/loadZtree?model="+ url,
						dataFilter : null
					},
					check : {
						enable : true,
						chkStyle : checkBoxRadio,
                        radioType:"all",
						chkboxType : {
							"Y" : "ps",
							"N" : "ps"
						}
					},
					callback : {
						onCheck : alertTestCheck, // 勾选事件
						onClick : alertClick, // 点击事件
						onRightClick : OnRightClick, // 右键事件
						beforeRename : beforeRename

					},
					data : {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "pid",
							rootPId : 0   // 根节点
						}
					}
				};
			}
			function ztreeCustom(data) {

                var setting = {
                    async : {
                        contentType : "application/json",
                        enable : true,
                        dataType : "json",
                        type : "get",
                        url : data.url,
                        dataFilter : null
                    },
                    check : {
                        enable : true,
                        chkStyle : data.chkStyle,
                        chkboxType : {
                            "Y" : "ps",
                            "N" : "ps"
                        }
                    },
                    callback : {
                        onCheck : data.onCheck, // 勾选事件
                        onClick : data.onClick,
                        onAsyncSuccess:data.onAsyncSuccess //成功加载完成事件
                    },
                    data : {
                        simpleData : {
                            enable : true,
                            idKey : data.id,
                            pIdKey : data.pid,
                            rootPId : 0   // 根节点
                        }
                    }
                };

                if (!$(common.currentContent() + " #" + data.domId).is("#" + data.domId)){
                    alert("zTree树请添加正常的id!" + data.domId);
                    return;
                }
                zTree = $.fn.zTree.init($(common.currentContent() + " #" + data.domId), setting);

                // 隐藏或显示列
                $(common.currentContent() + " .slider > div").click(function(){
                    var className = $(this).attr('class');
                    if(className === 'sliderLeft'){
                        $(common.currentContent() + " .zTreeContent").hide();
                        $(common.currentContent() + " .zTreeBackground").removeClass().addClass("zTreeBackground2");
                        $(common.currentContent() + " .sliderLeft").removeClass().addClass("sliderRight");
                        $(common.currentContent() + " .gridContainerWithTree").css("margin-left","0");
                        // 改变grid宽度
                        common.resizeGrid(common._width());
                    }else if (className === 'sliderRight'){
                        $(common.currentContent() + " .zTreeContent").show();
                        $(common.currentContent() + " .zTreeBackground2").removeClass().addClass("zTreeBackground");
                        $(common.currentContent() + " .sliderRight").removeClass().addClass("sliderLeft");
                        $(common.currentContent() + " .gridContainerWithTree").css("margin-left","10px");
                        // 改变grid宽度
                        common.resizeGrid(common._width());
                    }
                });
                return zTree;
			}
            exports.ztreeCustom = ztreeCustom;

            function ztree(domainUrl, domId, getParentId,checkBoxRadio) {
                // 判断是否查询传值传父ID
                getParentId==false?queryWithParentId=false:queryWithParentId=true;

                // 默认checkbox
                if (!checkBoxRadio){
                    checkBoxRadio = "checkbox";
                }

                url = domainUrl.replace("/", '');
                setSetting(url, checkBoxRadio);

                if (!$(common.currentContent() + " #" + domId).is("#" + domId)){
                    alert("zTree树请添加正常的id!" + domId);
                    return;
                }
                zTree = $.fn.zTree.init($(common.currentContent() + " #" + domId), setting);
				rMenu = $("#rMenu");
				$("#m_add").unbind("click").bind("click",function(){
                                                addTreeNode();
                                            });

                $("#m_up").unbind("click").bind("click",function(){
                                                updateTreeNode();
                                            });
                $("#m_del").unbind("click").bind("click",function(){
                                                removeTreeNode();
                                            });
                // 隐藏或显示列
                $(common.currentContent() + " .slider > div").click(function(){
                    var className = $(this).attr('class');
                    if(className === 'sliderLeft'){
                        $(common.currentContent() + " .zTreeContent").hide();
                        $(common.currentContent() + " .zTreeBackground").removeClass().addClass("zTreeBackground2");
                        $(common.currentContent() + " .sliderLeft").removeClass().addClass("sliderRight");
                        $(common.currentContent() + " .gridContainerWithTree").css("margin-left","0");
                        // 改变grid宽度
                        common.resizeGrid(common._width());
                    }else if (className === 'sliderRight'){
                        $(common.currentContent() + " .zTreeContent").show();
                        $(common.currentContent() + " .zTreeBackground2").removeClass().addClass("zTreeBackground");
                        $(common.currentContent() + " .sliderRight").removeClass().addClass("sliderLeft");
                        $(common.currentContent() + " .gridContainerWithTree").css("margin-left","10px");
                        // 改变grid宽度
                        common.resizeGrid(common._width());
                    }
                });
			}
			exports.ztree = ztree;
            // 获取所有被选中的节点, 返回string
            function getSelectedNode(treeId) {
                var selectedNode = "(";
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                var nodes = treeObj.getCheckedNodes(true);
                for (var i = 0; i < nodes.length; i++) {
                    // 查询父ID
                    if (queryWithParentId){
                        if(selectedNode == "("){
                            selectedNode += "\'";
                            selectedNode += nodes[i].id;
                        }else{
                            selectedNode += ",\'";
                            selectedNode += nodes[i].id;
                        }
                        selectedNode += "\'";
                    }else{
                        // 不查询父ID
                        if (!nodes[i].open && nodes[i].children == undefined) {
                            if(selectedNode == "("){
                                selectedNode += "\'";
                                selectedNode += nodes[i].id;
                            }else{
                                selectedNode += ",\'";
                                selectedNode += nodes[i].id;
                            }
                            selectedNode += "\'";
                        }
                    }
                }

                selectedNode += ")";

                return selectedNode;
            }
			exports.getSelectedNode = getSelectedNode;
            function doSearch(treeId, columnName){


                // 获得树
                var selectedNodes = getSelectedNode(treeId);
                // 为空
                var json = "{\"" + columnName + "\":\"";
                if (selectedNodes === '()'){
                    json += "(\'\')";
                }else{
                    json +=  selectedNodes;
                }
				if(treeId=="productFlowCountTree"){
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					json += "\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
				}else{
					json += "\"}";
				}
                common.customSearch(JSON.parse(json))

            }
			function alertTestCheck(e, treeId, treeNode) {
				//alert("该CheckBox 已经被勾选上了!\nID:" + treeNode.id + "\nNAME:"
				//		+ treeNode.name + "\nPID:" + treeNode.pid);
				//getNodeAll(treeId);
                var columnName = $(common.currentContent() + " #" + treeId).attr("data-search-column");
                if(!columnName){
                    alert("请为zTree指定data-search-column属性。此属性是查询的column名称");
                }else{
                    doSearch(treeId, columnName);
                }
			}
			function alertClick(e, treeId, treeNode, clickFlag) {
				zTree.checkNode(treeNode, !treeNode.checked, true);
                var columnName = $(common.currentContent() + " #" + treeId).attr("data-search-column");
                if(!columnName){
                    alert("请为zTree指定data-search-column属性。此属性是查询的column名称");
                }else{
                    doSearch(treeId, columnName);
                }
                //alert("点击了文字《" + treeNode.name + "》勾选上了!\nID:" + treeNode.id
				//		+ "\nNAME:" + treeNode.name + "\nPID:" + treeNode.pid);
				//getNodeAll(treeId);
			}
			var array;
			// 获取所有被选中的节点
			function getNodeAll(treeId) {
				array = [];
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				var nodes = treeObj.getCheckedNodes(true);
				for (var i = 0; i < nodes.length; i++) {
					if (!nodes[i].open && nodes[i].children == undefined) {
						array[array.length] = nodes[i].id;
					}
				}
                return array;
			}

			// 右键功能
			function OnRightClick(event, treeId, treeNode) {
				if (!treeNode && event.target.tagName.toLowerCase() != "button"
						&& $(event.target).parents("a").length == 0) {
					zTree.cancelSelectedNode();
					showRMenu("root", event.clientX, event.clientY);
				} else if (treeNode && !treeNode.noR) {
					zTree.selectNode(treeNode);
					showRMenu("node",parseInt(event.clientX)-220, parseInt(event.clientY)-60);
				}
				//zTree.checkNode(treeNode, !treeNode.checked, true);
			}

			function showRMenu(type, x, y) {
					
				if (type == "root") {
				} else {
					$("#rMenu ul").show();
					rMenu.css({
						"top" : y + "px",
						"left" : x + "px",
						"visibility" : "visible"
					});

				}
				$("body").bind("mousedown", onBodyMouseDown);
			}
			function hideRMenu() {
				if (rMenu)
					rMenu.css({
						"visibility" : "hidden"
					});
				$("body").unbind("mousedown", onBodyMouseDown);
			}
			function onBodyMouseDown(event) {
				if (!(event.target.id == "rMenu" || $(event.target).parents(
						"#rMenu").length > 0)) {
					rMenu.css({
						"visibility" : "hidden"
					});
				}
			}
			var addCount = 1;
			function addTreeNode() {
				hideRMenu();
				var newNode = returnNode();
				if (zTree.getSelectedNodes()[0]) {
					newNode.checked = zTree.getSelectedNodes()[0].checked;
					zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
				} else {
					zTree.addNodes(null, newNode);
				}
				$.ajax({
					type : 'get',
					contentType : 'appliction/json;charset=utf-8',
					url : ctx+'/zTree/checkName?model='+url,
					data : 'name=' + newNode.name,
					dataType : 'json',
					success:function(data) { 
						if(data){
							$.ajax({
								type : 'get',
								contentType : 'appliction/json;charset=utf-8',
								url : ctx+'/zTree/addZtree?model='+url,
								data : newNode,
								dataType : 'json',
                                // 返回正常，才重新加载zTree
                                statusCode: {200: function() {
                                    ztree("/"+url);
                                    // 刷新grid
                                    common.flushDataGrid();
                                    }
                                }
							});
						}else{
							ztree("/"+url);
                            // 刷新grid
                            common.flushDataGrid();
						}
					}
				});
			}
			function beforeRename(event, treeId, treeNode) {
				updateTreeName(treeId.id, treeNode);
			}
			function updateTreeNode(event, treeId, treeNode) {
				hideRMenu();
				var node = zTree.getSelectedNodes()[0];
				zTree.editName(node);

			}
			function updateTreeName(id, name) {
				var data = {
					id : id,
					name : name
				};
				$.ajax({
					type : 'get',
					contentType : 'appliction/json;charset=utf-8',
					url : ctx+'/zTree/updateZtree?model='+url,
					data : data,
					dataType : 'json',
                    // 返回正常，才重新加载zTree
                    statusCode: {200: function() {
                        ztree("/"+url);
                        // 刷新grid
                        common.flushDataGrid();
                        }
                    }
                });
			}
			function removeTreeNode() {
				hideRMenu();
				var nodes = zTree.getSelectedNodes();
				if (nodes && nodes.length > 0) {
					if (nodes[0].children && nodes[0].children.length > 0) {
						var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉！";
						if (confirm(msg) == true) {
							array = new Array();
							var childNodes = zTree.transformToArray(nodes[0]);
							for (var i = 0; i < childNodes.length; i++) {
								array[array.length] = childNodes[i].id
										.toString();
							}
							zTree.removeNode(nodes[0]);
						}
					} else {
						array = new Array();
						array[array.length] = nodes[0].id.toString();
						zTree.removeNode(nodes[0]);
					}
				}
				$.ajax({
					type : 'get',
					contentType : 'appliction/json;charset=utf-8',
					url : ctx+'/zTree/deleteZtree?model='+url,
					data : 'array=' + array,
					dataType : 'json',
                    // 返回正常，才重新加载zTree
                    statusCode: {200: function() {
                        ztree("/"+url);
                        // 刷新grid
                        common.flushDataGrid();
                        }
                    }
				});
			}
			function returnNode() {
				var node = zTree.getSelectedNodes()[0];
				var children = node.children;
				var id = "";
				var pid = node.id;
				var open = false;
				return {
					id : id,
					name : "新增 - " + addCount++,
					pid : pid
				};
			}

			function mousePosition(evt){
			    var xPos,yPos;         
			    evt=evt || window.event;
			    if(evt.pageX){         
			        xPos=evt.pageX;         
			        yPos=evt.pageY;     
			    } else {         
			        xPos=evt.clientX+document.body.scrollLeft -document.body.clientLeft;
			        yPos=evt.clientY+document.body.scrollTop-document.body.clientTop;     
			    }
			    return [xPos, yPos];
			}
		})