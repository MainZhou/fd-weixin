<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../common/header.jsp" %>
<div class="content-wrapper" id="tabs">
    <section class="content">
        <div class="row">
            <div class="col-xs-12">
                <div class="nav-tabs-custom tab-primary">
                    <%--导航条--%>
                    <ul class="nav nav-tabs">
                        <li class="active" role="presentation" style="margin-left: 5px;">
                            <a href="#home" aria-controls="home" role="tab" data-toggle="tab" >首页</a>
                        </li>
                    </ul>
                    <%--内容--%>
                    <div class="tab-content">
                        <div class="tab-pane active" id="home">
                            <%--快捷图标--%>
                            <div class="row">
                                <div class="col-lg-3 col-xs-6">
                                    <div class="small-box bg-aqua">
                                        <div class="inner">
                                            <h3 id="productInfoNum"></h3>
                                            <p>储罐</p>
                                        </div>
                                        <div class="icon">
                                            <i class="ion ion-bag"></i>
                                        </div>
                                        <a href="javascript://"
                                           class="small-box-footer"
                                           onclick='addTab("{\"id\":\"产品运行信息\",\"title\":\"产品运行信息\",\"ajax\":true,\"url\":\"" + ctx + "/productRunRecord/list\"}")'>
                                            更 多 <i class="fa fa-arrow-circle-right"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-xs-6">
                                    <div class="small-box bg-green">
                                        <div class="inner">
                                            <h3 id="carInfoNum"></h3>
                                            <p>槽车</p>
                                        </div>
                                        <div class="icon">
                                            <i class="ion ion-stats-bars"></i>
                                        </div>
                                        <a href="javascript://"
                                           class="small-box-footer"
                                           onclick='addTab("{\"id\":\"车辆管理\",\"title\":\"车辆管理\",\"ajax\":true,\"url\":\"" + ctx + "/carInfo/list\"}")'>
                                            更 多 <i class="fa fa-arrow-circle-right"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-xs-6">
                                    <!-- small box -->
                                    <div class="small-box bg-yellow">
                                        <div class="inner">
                                            <h3 id="customInfoNum"></h3>
                                            <p>客户</p>
                                        </div>
                                        <div class="icon">
                                            <i class="ion ion-person-add"></i>
                                        </div>
                                        <a href="javascript://"
                                           class="small-box-footer"
                                           onclick='addTab("{\"id\":\"客户信息\",\"title\":\"客户信息\",\"ajax\":true,\"url\":\"" + ctx + "/customInfo/list\"}")'>
                                            更 多 <i class="fa fa-arrow-circle-right"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-xs-6">
                                    <div class="small-box bg-red">
                                        <div class="inner">
                                            <h3 id="customFeedbackNum"></h3>
                                            <p>客户反馈</p>
                                        </div>
                                        <div class="icon">
                                            <i class="ion ion-pie-graph"></i>
                                        </div>
                                        <a href="javascript://"
                                           class="small-box-footer"
                                           onclick='addTab("{\"id\":\"反馈信息\",\"title\":\"反馈信息\",\"ajax\":true,\"url\":\"" + ctx + "/customFeedback/list\"}")'>
                                            更 多 <i class="fa fa-arrow-circle-right"></i>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <%--统计图表--%>
                            <div class="row" style="overflow: auto">
                                <%--流量统计图--%>
                                <div class="col-xs-12 col-md-9">
                                    <div class="box">
                                        <div class="box-header with-border">
                                            <h3 class="box-title">流量统计图</h3>
                                        </div>
                                        <div class="box-body" role="group">
                                            <div class="row">
                                                <div class="col-md-5 col-md-offset-5">
                                                    <div class="btn-group btn-group-sm" id="flowCountTypes" role="group" style="margin: 5px 0">
                                                        <button type="button" data-countType="week" class="btn btn-success">本周</button>
                                                        <button type="button" data-countType="month" class="btn btn-default">本月</button>
                                                        <button type="button" data-countType="quarter" class="btn btn-default">本季</button>
                                                        <button type="button" data-countType="year" class="btn btn-default">本年</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="box-footer">
                                            <div id="getFlowCountByAllArea" style="width: 100%;"></div>
                                        </div>
                                    </div>
                                </div>
                                <%--用量排行榜--%>
                                <div class="col-xs-12 col-md-3">
                                    <div class="box">
                                        <div class="box-header with-border">
                                            <h3 class="box-title">用量排行榜</h3>
                                        </div>
                                        <div class="box-body" role="group">
                                            <div class="row">
                                                <div class="col-md-8 col-md-offset-3">
                                                    <div class="btn-group btn-group-sm" id="flowRankingTypes" role="group" style="margin: 5px 0">
                                                        <button type="button" data-rankingType="yesterday" class="btn btn-success">昨日</button>
                                                        <button type="button" data-rankingType="lastWeek" class="btn btn-default">近一周</button>
                                                        <button type="button" data-rankingType="lastMonth" class="btn btn-default">近一月</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="box-footer">
                                            <div style="margin-top: 30px;"></div>
                                            <div class="row">
                                                <div class="col-md-8 autoAdd3dot" id="ranking0Name"></div>
                                                <div class="col-md-4 autoAdd3dot" id="ranking0Val"></div>
                                            </div>
                                            <div class="progress progress-sm" style="height:10px;">
                                                <div class="progress-bar progress-bar-success progress-bar-striped" id="ranking0bar"></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8 autoAdd3dot" id="ranking1Name"></div>
                                                <div class="col-md-4 autoAdd3dot" id="ranking1Val"></div>
                                            </div>
                                            <div class="progress progress-sm" style="height:10px;">
                                                <div class="progress-bar progress-bar-primary progress-bar-striped" id="ranking1bar"></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8 autoAdd3dot" id="ranking2Name"></div>
                                                <div class="col-md-4 autoAdd3dot" id="ranking2Val"></div>
                                            </div>
                                            <div class="progress progress-sm" style="height:10px;">
                                                <div class="progress-bar progress-bar-info progress-bar-striped" id="ranking2bar"></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8 autoAdd3dot" id="ranking3Name"></div>
                                                <div class="col-md-4 autoAdd3dot" id="ranking3Val"></div>
                                            </div>
                                            <div class="progress progress-sm" style="height:10px;">
                                                <div class="progress-bar progress-bar-info progress-bar-striped" id="ranking3bar"></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8 autoAdd3dot" id="ranking4Name"></div>
                                                <div class="col-md-4 autoAdd3dot" id="ranking4Val"></div>
                                            </div>
                                            <div class="progress progress-sm" style="height:10px;">
                                                <div class="progress-bar progress-bar-danger progress-bar-striped" id="ranking4bar"></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8 autoAdd3dot" id="ranking5Name"></div>
                                                <div class="col-md-4 autoAdd3dot" id="ranking5Val"></div>
                                            </div>
                                            <div class="progress progress-sm" style="height:10px;">
                                                <div class="progress-bar progress-bar-success progress-bar-striped" id="ranking5bar"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

 <!-- 底部 -->
<%@include file="../common/footer.jsp" %>