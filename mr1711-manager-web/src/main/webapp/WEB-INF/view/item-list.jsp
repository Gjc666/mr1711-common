<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/15
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>商品管理</title>
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
<body>
    <br>
    <table id="dg"></table>
<script>

    loadList();

    function loadList(){
        $('#dg').datagrid({
            url:'<%=path%>/item/list',//路径
            fitColumns:true,
            columns:[[    //列
                {field:'id',title:'商品编号',width:100},
                {field:'title',title:'商品标题',width:100},
                {field:'sellPoint',title:'商品卖点',width:100},
                {field:'price',title:'商品价格',width:100},
                {field:'num',title:'库存数量',width:100},
                {field:'cid',title:'所属类目',width:100},
                {field:'status',title:'商品状态',width:100},
                {field:'created',title:'创建时间',width:100},
                {field:'updated',title:'修改时间',width:100},
            ]],
            pagination :true, //分页
            pageSize:5,//每页条数
            pageList:[5,10,15,20,25,30],//初始化页面大小选择列表。
        });
    }

</script>
</body>
</html>
