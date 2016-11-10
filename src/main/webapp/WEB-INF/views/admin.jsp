<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<!-- Site Properties -->
<title>基于多任务的海量数据采集平台</title>


<link rel="stylesheet" type="text/css"
	href="resources/semanticui/semantic.min.css">

<script src="resources/jquery/jquery.min.js"></script>
<script src="resources/semanticui/semantic.min.js"></script>
<script src="resources/app/admin.js"></script>

</head>
<body>
	<div class="ui container basic segment">
		<h2 class="ui center aligned header">基于多任务的海量数据采集平台</h2>
		<div id="mained" class="ui center aligned basic segment">
			<div class="ui stackable container menu">
				  	<a class="item active" id="status" onclick="changeContent('status');"><i class="alarm outline icon"></i>采集状态</a>
				  	<a class="item" id="job" onclick="changeContent('job');"><i class="checked calendar icon"></i>任务管理</a>
				  	<a class="item" id="analysor" onclick="changeContent('analysor');"><i class="sitemap icon"></i>解析器管理</a>
				  	<a class="item" id="display" onclick="changeContent('display');"><i class="alarm outline icon"></i>采集信息展示</a>
				  	<a class="item" id="system" onclick="changeContent('system');"><i class="setting icon"></i>系统管理</a>
			</div>
			<div id="mainer">
				  
			
			</div>
		</div>
		<p></p>
		<div id="footer">
			<div>
        <p class="ui center aligned header">最新修改日期: 2016年10月27日</p>
        <p class="ui center aligned header">中国科学院管理、决策与信息系统重点实验室 版权所有 Copyright &copy;2016</p>
        <p class="ui center aligned header"> 电话:010-62565817 电子邮件:master@mdis.amss.ac.cn</p>
      </div>
		
		</div>

	</div>
<script type="text/javascript">
	changeContent("status");
	$("#content_modal").modal({allowMultiple: true});
	$("#second_content_modal").modal({allowMultiple: true});
</script>



<div class="ui modal" id="second_content_modal">
<i class="close icon"></i>
  <div class="header" id="second_content_modal_header">
    	标题
  </div>
  <div class="image content" id="second_content_modal_content">
    
    <div class="description" id="second_content_modal_description">
		内容
    </div>
  </div>
  <div class="actions" id="second_content_modal_actions">
  </div>
</div>

<div class="ui modal large" id="content_modal">
<i class="close icon"></i>
  <div class="header" id="content_modal_header">
    	标题
  </div>
  <div class="image content" id="content_modal_content">
    
    <div class="description" id="content_modal_description">
		内容
    </div>
  </div>
  <div class="actions" id="content_modal_actions">
  </div>
</div>



</body>

</html>
