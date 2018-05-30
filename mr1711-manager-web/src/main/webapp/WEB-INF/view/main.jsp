<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/14
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>main</title>
    <!-- 引入jQuery -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery/jquery-1.11.3.min.js"></script>
    <!-- 引入easyui -->
    <script  src="<%=request.getContextPath()%>/js/jquery-easyui/jquery.easyui.min.js" ></script>
    <!-- 引入easyui  css样式  只需引入easyui.css   其中就包含了其他的内容-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery-easyui/themes/default/easyui.css">
    <!-- 引入小图标 -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery-easyui/themes/icon.css">
    <!-- 样式转化为中文 -->
    <script  src="<%=request.getContextPath()%>/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',title:'North Title',split:true" style="height:100px;" align="center">
        <h1>后台管理系统</h1>
    </div>
    <div data-options="region:'west',title:'West',split:true" style="width:200px;">
        <%--tree--%>
            <ul id="myTree" class="easyui-tree">
                <li>
                    <span>Folder</span>
                    <ul>
                        <li>
                            <span><a href="javascript:addTabs('商品管理','<%=path%>/page/toItemListPage')">商品管理</a></span>
                        </li>
                        <li>
                            <span><a href="javascript:addTabs('商品类型管理','')">商品类型管理</a></span>
                        </li>
                    </ul>
                </li>
            </ul>
        <%--tree--%>
    </div>
    <div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
        <%--tabs--%>
        <div id="myTabs" class="easyui-tabs" fit="true">
            <div title="Tab1" style="padding:20px;display:none;">
                后台管理介绍
            </div>
        </div>
        <%--tabs--%>
    </div>

<script >
    function addTabs(tit , url){

        var exi = $('#myTabs').tabs('exists',tit);
        if(exi){
            $('#myTabs').tabs('select',tit);
        }else{
            $('#myTabs').tabs('add',{
                    title: tit,
                    content:'<iframe style="width:100%;height:100%;position:relative;" src="'+url+'" frameborder="0" scrolling="true" ></iframe>',
                    selected: true,
                    closable: true,
                    tools:[{    //添加工具
                        iconCls:'icon-mini-refresh',
                        handler: function(){
                            //获取当前的选项卡
                            var tab = $('#myTabs').tabs("getSelected");
                            //刷新选项卡
                            $('#myTabs').tabs('update', {
                                tab: tab,
                                options: {}
                            });
                        }
                    }]
                });
             }
    }

</script>
</body>
</html>
