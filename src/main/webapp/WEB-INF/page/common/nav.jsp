<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="wrapper">
    <!-- 导航条 -->
    <header class="main-header">
        <a href="${ctx}/user/admin" class="logo">
            <span class="logo-mini">
                <img src="${ctx}/static/img/logo-min.png">
            </span>
            <span class="logo-lg" style="float:left;">
                <img src="${ctx}/static/img/logo-white.png">
            </span>
        </a>
        <%--顶部导航条--%>
        <nav class="navbar navbar-static-top bannerWhite" role="navigation">
            <%-- 隐藏或显示左侧菜单 --%>
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <%--断线--%>
                    <li class="messages-menu">
                        <a aria-expanded="true"
                           href="javascript:"
                           title="产品实时断线信息"
                           <%--onclick='addTab("{\"id\":\"productBreakTabId\",\"title\":\"产品实时断线信息\",\"ajax\":true,\"url\":\"" + ctx + "/productBreak/list\"}")'>--%>
                            <i class="fa fa-fw fa-ban"></i>
                            <span class="label" style="background-color: #B2B1B1" id="productBreakNum"></span>
                        </a>
                    </li>
                    <%--报警--%>
                    <li class="messages-menu">
                        <a aria-expanded="true"
                           href="javascript:"
                           title="产品实时报警信息"
                           <%--onclick='addTab("{\"id\":\"productAlarmTabId\",\"title\":\"产品实时报警信息\",\"ajax\":true,\"url\":\"" + ctx + "/productAlarmDetail/alarmList?alarmOrPreAlarm=alarm\"}")'>--%>
                            <i class="fa fa-fw fa-warning"></i>
                            <span class="label label-danger" id="productAlarmNum"></span>
                        </a>
                    </li>
                    <%--预警--%>
                    <li class="messages-menu">
                        <a aria-expanded="true"
                           href="javascript:"
                           title="产品实时预警信息"
                           <%--onclick='addTab("{\"id\":\"productPreAlarmTabId\",\"title\":\"产品实时预警信息\",\"ajax\":true,\"url\":\"" + ctx + "/productAlarmDetail/alarmList?alarmOrPreAlarm=preAlarm\"}")'>--%>
                            <i class="fa fa-fw fa-info"></i>
                            <span class="label label-warning" id="productPreAlarmNum"></span>
                        </a>
                    </li>
                    <%--通知--%>
                    <li class="dropdown notifications-menu">
                        <a aria-expanded="false" href="#" class="dropdown-toggle" data-toggle="dropdown"
                           title="待办事项">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-primary" id="countMatter"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">待办事项</li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <div style="position: relative; overflow: hidden; width: auto; height: 200px;"
                                     class="slimScrollDiv">
                                    <ul style="overflow: hidden; width: 100%; height: 200px;" class="menu"
                                        id="matters">
                                        <%--<li>
                                            <a href="#">
                                                <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="fa fa-warning text-yellow"></i> Very long description here that may not fit into the
                                                page and may cause design problems
                                            </a>
                                        </li>--%>
                                        <%--<c:if test="${count!=0}">--%>
                                            <%--<li>--%>
                                                <%--<a href="javascript:"--%>
                                                   <%--onclick='addTab("{\"id\":\"fc04e1fd-6037-4a19-b3d1-45707e436186\",\"title\":\"预存款管理\",\"ajax\":true,\"url\":\"" + ctx + "/customBalance/list\"}")'>--%>
                                                    <%--<i class="fa fa-users text-red"></i> ${count}位客户账户预存款不足--%>
                                                <%--</a>--%>
                                            <%--</li>--%>
                                        <%--</c:if>--%>
                                        <%--<c:if test="${checkOutCount!=0}">--%>
                                            <%--<li>--%>
                                                <%--<a href="javascript:"--%>
                                                   <%--onclick='addTab("{\"id\":\"62135af1-2494-4ca7-9d0e-8cb9b300c7e8\",\"title\":\"产品检测管理\",\"ajax\":true,\"url\":\"" + ctx + "/productCheckOut/list\"}")'>--%>
                                                    <%--<i class="fa fa-users text-red"></i> ${checkOutCount}个产品预计外检日期即将到期--%>
                                                <%--</a>--%>
                                            <%--</li>--%>
                                        <%--</c:if>--%>
                                        <%--<li>
                                            <a href="#">
                                                <i class="fa fa-shopping-cart text-green"></i> 25 sales made
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="fa fa-user text-red"></i> You changed your username
                                            </a>
                                        </li>--%>
                                    </ul>
                                    <div style="background: rgb(0, 0, 0) none repeat scroll 0% 0%; width: 3px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 195.122px;"
                                         class="slimScrollBar"></div>
                                    <div style="width: 3px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51) none repeat scroll 0% 0%; opacity: 0.2; z-index: 90; right: 1px;"
                                         class="slimScrollRail"></div>
                                </div>
                            </li>
                            <li class="footer" style="display: none;"><a href="#">查看全部</a></li>
                        </ul>
                    </li>
                    <%--头像--%>
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <c:if test="${sessionScope['LOGIN_SUCCESS'].sex == 0}">
                                <img src="${ctx}/static/adminlte/img/avatar.png" class="user-image"/>
                            </c:if>
                            <c:if test="${sessionScope['LOGIN_SUCCESS'].sex == 1}">
                                <img src="${ctx}/static/img/women2.png" class="user-image"/>
                            </c:if>
                            <span class="hidden-xs">${sessionScope['LOGIN_SUCCESS'].realName}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <sec:authorize access="hasAuthority('清除服务器缓存')">
                                <li class="user-footer">
                                    <a id="clearRedisCache" class="btn btn-default btn-flat">清除服务器缓存</a>
                                </li>
                            </sec:authorize>
                            <li class="user-footer">
                                <a id="changePassword" data-user-id="${sessionScope.LOGIN_SUCCESS.guid}"
                                   class="btn btn-default btn-flat">
                                    <spring:message code="User.func.change.password"/>
                                </a>
                            </li>
                            <li class="user-footer">
                                <a href="${ctx}/user/logout" class="btn btn-default btn-flat">退出登录</a>
                            </li>
                        </ul>
                    </li>
                    <%--设置--%>
                    <%--<li class="dropdown user user-menu" id="setting">--%>
                        <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i--%>
                                <%--class="fa fa-gears"></i></a>--%>
                        <%--<ul class="dropdown-menu">--%>
                            <%--<li style="float:left; width: 33.33333%; padding: 15px 20px;">--%>
                                <%--<a href="javascript:void(0);" id="style1" data-skin="skin-black-light"--%>
                                   <%--style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"--%>
                                   <%--class="clearfix full-opacity-hover">--%>
                                    <%--<div style="box-shadow: 0 0 2px rgba(0,0,0,0.1)" class="clearfix">--%>
                                        <%--<span style="display:block; width: 20%; float: left; height: 7px; background: #fefefe;"></span>--%>
                                        <%--<span style="display:block; width: 80%; float: left; height: 7px; background: #fefefe;"></span>--%>
                                    <%--</div>--%>
                                    <%--<div><span--%>
                                            <%--style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span>--%>
                                        <%--<span style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>--%>
                                    <%--</div>--%>
                                <%--</a>--%>
                                <%--<p class="text-center no-margin" style="font-size: 12px">风格一</p>--%>
                            <%--</li>--%>
                            <%--<li style="float:left; width: 33.33333%; padding: 15px 20px;">--%>
                                <%--<a href="javascript:void(0);" id="style2" data-skin="skin-green"--%>
                                   <%--style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.5)"--%>
                                   <%--class="clearfix full-opacity-hover">--%>
                                    <%--<div><span style="display:block; width: 20%; float: left; height: 7px;"--%>
                                               <%--class="bg-green-active"></span>--%>
                                                <%--<span class="bg-green"--%>
                                                      <%--style="display:block; width: 80%; float: left; height: 7px;"></span>--%>
                                    <%--</div>--%>
                                    <%--<div><span--%>
                                            <%--style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span>--%>
                                        <%--<span style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>--%>
                                    <%--</div>--%>
                                <%--</a>--%>
                                <%--<p class="text-center no-margin">风格二</p>--%>
                            <%--</li>--%>

                            <%--<li style="float:left; width: 33.33333%; padding: 15px 20px;">--%>
                                <%--<a href="javascript:void(0);" id="style3" data-skin="skin-green-light"--%>
                                   <%--style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.5)"--%>
                                   <%--class="clearfix full-opacity-hover">--%>
                                    <%--<div>--%>
                                                <%--<span style="display:block; width: 20%; float: left; height: 7px;"--%>
                                                      <%--class="bg-green-active"></span>--%>
                                                <%--<span class="bg-green"--%>
                                                      <%--style="display:block; width: 80%; float: left; height: 7px;"></span>--%>
                                    <%--</div>--%>
                                    <%--<div><span--%>
                                            <%--style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span>--%>
                                        <%--<span style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>--%>
                                    <%--</div>--%>
                                <%--</a>--%>
                                <%--<p class="text-center no-margin" style="font-size: 12px">风格三</p>--%>
                            <%--</li>--%>

                        <%--</ul>--%>
                    <%--</li>--%>
                </ul>
            </div>
        </nav>
    </header>

    <%--左侧菜单--%>
    <aside class="main-sidebar">
        <section class="sidebar">
            <ul class="sidebar-menu">
                <c:forEach items="${sessionScope['USER_MENU']}" var="item" varStatus="ind">
                    <c:choose>
                        <c:when test="${fn:length(item.subMenus) == 0}">
                            <c:choose>
                                <c:when test="${item.entryUrl=='/creator/step1'}">
                                    <li>
                                        <a href="${ctx}${item.entryUrl}" target="_blank"><i
                                                class="${item.icon.cssName}"></i><span>${item.menuName}</span>
                                                <%--<c:if test="${ind.index  ==0}">--%>
                                                <%--<small class="label pull-right bg-green">new</small>--%>
                                                <%--</c:if>--%>
                                                <%--<c:if test="${ind.index == 5}">--%>
                                                <%--<span class="label pull-right bg-yellow">12</span>--%>
                                                <%--</c:if>--%>
                                                <%--<c:if test="${ind.index == 3}">--%>
                                                <%--<span class="label pull-right bg-red">3</span>--%>
                                                <%--</c:if>--%>
                                        </a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a href="javascript:" url="${ctx}${item.entryUrl}"
                                           data-addtab="${item.menuName}"
                                           ajax='true'><i class="${item.icon.cssName}"></i><span>${item.menuName}</span>
                                                <%--<c:if test="${ind.index  ==0}">--%>
                                                <%--<small class="label pull-right bg-green">new</small>--%>
                                                <%--</c:if>--%>
                                                <%--<c:if test="${ind.index == 5}">--%>
                                                <%--<span class="label pull-right bg-yellow">12</span>--%>
                                                <%--</c:if>--%>
                                                <%--<c:if test="${ind.index == 3}">--%>
                                                <%--<span class="label pull-right bg-red">3</span>--%>
                                                <%--</c:if>--%>
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <li class="treeview">
                                <a href="#">
                                    <i class="${item.icon.cssName}"></i> <span>${item.menuName}</span> <i
                                        class="fa fa-angle-left pull-right"></i>
                                </a>
                                <ul class="treeview-menu">
                                    <c:forEach items="${item.subMenus}" var="subItem">
                                        <li>
                                            <c:choose>
                                                <c:when test="${subItem.entryUrl=='/creator/step1'}">
                                                    <a href="${ctx}${subItem.entryUrl}"><i
                                                            class="${subItem.icon.cssName}"></i>${subItem.menuName}</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript:" data-addtab="${subItem.menuName}"
                                                       url="${ctx}${subItem.entryUrl}" ajax='true'><i
                                                            class="${subItem.icon.cssName}"></i>${subItem.menuName}</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </section>
    </aside>
    <script>
        //自定义进入标签页

        function addTab(data) {
            Addtabs.add(JSON.parse(data))
        }
    </script>