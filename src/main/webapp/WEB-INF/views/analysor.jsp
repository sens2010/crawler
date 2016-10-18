<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8"%>
<!-- index.html -->
<!DOCTYPE html>
<html>
  <head>
   	<meta charset="utf-8" />
    <title>金融大数据平台</title>
    <script src="resources/react/react.js"></script>
    <script src="resources/react/react-dom.js"></script>
    <script src="resources/react/browser.min.js"></script>
    <script src="resources/jquery/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/all.css">
	<link rel="stylesheet" type="text/css" href="resources/semanticui/semantic.min.css">
    
  </head>
  <body style="margin:8px">

	<div id="header" >
		<div style="height: 165; background-color: #007091">

			<div
				style="width: 100%; border: 0; margin: 0; padding =0; background-image: url(resources/images/index_topbg.gif); float: right; display: inline-block">
				<div style="width: 100%; background-color: #007091; font-size: 0">
					<img src="resources/images/index_top1.gif" width="700"
						style="border-width: 0; margin: 0; padding: 0;display:block">
				</div>
				<div>
					<div style="display:inline-block;margin-top:40px;margin-left:30px">
					<p>
						     <a href='###' style="color:Black;">登录系统</a>
                                                            &nbsp; &nbsp;
                             <a href='###' style="color:Black;">操作层</a>
					</p>
					<span id="ctl00_TopTitleLabel" style="margin-left:20px;display:inline-block;color:#990000;font-size:XX-Large;height:40px;line-height: 40px; font-family: 华文新魏; color: #990000; vertical-align: middle;
                                                                font-weight: bold; word-wrap: normal;position:absolute">金融大数据平台—采集系统</span>
					</div>
					<div
						style="background-image: url(resources/images/01.gif); float: right; display: inline-block; font-size: 0">
						<img height="138" src="resources/images/index_toppic.gif"
							width="318"
							style="background-image: url(resources/images/01.gif); border: 0px; margin: 0px;;display:block">
					</div>
					<div
						style="height: 138px; width: 460px; border: 0; margin: 0; padding =0; background-image: url(resources/images/index_top2.gif); display: inline-block; background-repeat: no-repeat; float: right;">
					</div>
				</div>
			</div>
		</div>
		<div style="width: 100%; border: 0; margin: 0; padding =0; background-image: url(resources/images/index_topbottombg.gif); float: right; display: inline-block">
		
                                            <div valign="top" style="height: 12px;font-size: 0">
                                                <img height="12px" src="resources/images/index_top3.gif" width="200">
                                            </div>
                                            
                                           <!--  <div valign="top" style="height: 12px">
                                                <div align="right" style="font-size: 0">
                                                    <img height="100%" src="resources/images/index_top4.gif" width="318"></div>
                                            </div> -->
                                      
		</div>
	</div>
	<div id="main" style="display:table;width:100%">
		<div id="main_left" style="width:200px;background-image: url(resources/images/index_lmbg.gif);display:table-cell;">
			<div style="font-size:0">
				<img height="60" src="resources/images/index_lmtop.gif" width="200">
			</div>
			<div style="display:table">
			<div style="font-size:0;display:table-cell">
				<img height="120" src="resources/images/index_lmcenter.gif" width="40" style="display:block">
			</div>
			
			<div style="display:table-cell">

				<div style="position:relative;margin-left:3px;top:-90px;background-image: url(resources/images/index_lm.gif);border:0;width:140px;height:20px;text-align:center">
					<b><a href="status"><font color="#000000">采集状态</font></a></b>
				</div>
				
				<div style="position:relative;margin-left:3px;margin-top:15px;top:-90px;background-image: url(resources/images/index_lm.gif);border:0;width:140px;height:20px;text-align:center">
					<b><a href="job"><font color="#000000">任务管理</font></a></b>
				</div>
				
				<div style="position:relative;margin-left:3px;margin-top:15px;top:-90px;background-image: url(resources/images/index_lm.gif);border:0;width:140px;height:20px;text-align:center">
					<b><a href="analysor"><font color="#000000">解析器</font></a></b>
				</div>
			
				<div style="position:relative;margin-left:3px;margin-top:15px;top:-90px;background-image: url(resources/images/index_lm.gif);border:0;width:140px;height:20px;text-align:center">
					<b><a href="display"><font color="#000000">可视化展示</font></a></b>
				</div>
				
				<div style="position:relative;margin-left:3px;margin-top:15px;top:-90px;background-image: url(resources/images/index_lm.gif);border:0;width:140px;height:20px;text-align:center">
					<b><a href="system"><font color="#000000">系统管理</font></a></b>
				</div>
			
			</div>
		</div>
		</div>
		
        
		<div id="main_center" style="display:table-cell;cellpadding:0;cellspacing:0;background-color:#289cbf;width:100%">
			
			<div style="background-image: url(resources/images/index_centerbg.gif); height: 9px;width:100%;margin:0;top:-16px;position:relative">
                                   
            </div>
            
            <div style="margin-left:40px">
            	<table class="ui celled table" >
  <thead>
   <tr><th colspan="8"><h3>解析器管理</h3> </th>
    				<tr>
    				<th>名称</th>
    				<th>解析器状态</th>
    					<th>解析网站个数</th>
            			<th>创建时间</th>
            			<th>最后修改时间</th>
            			<th>相关操作</th>
  </tr></thead>
  <tbody>
    <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
            		
            		 <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
            		
            		 <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
            		
            		 <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
            		
            		 <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
            		
            		 <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
            		
            		 <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
            		
            		 <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
            		
            		 <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
            		
            		 <tr>
            			<td>搜狐财经-原油市场</td>
            			<td>正常</td>
            			<td>1</td>
            			<td>2016-08-24 15:00:00</td>
            			<td>无</td>
            			<td><div class="ui small basic icon buttons">
  <button class="ui button"><i class="write icon"></i></button>
  <button class="ui button"><i class="info Circle icon"></i></button>
  <button class="ui button"><i class="remove icon"></i></button>
</div></td>
            		</tr>
 
  </tbody>
  <tfoot>
    <tr><th colspan="8">
    <div class="ui left floated pagination menu">
        <button class="ui primary button">＋添加任务 </button>
      </div>
      <div class="ui right floated pagination menu">
        <a class="icon item">
          <i class="left chevron icon"></i>
        </a>
        <a class="item">1</a>
        <a class="item">2</a>
        <a class="item">3</a>
        <a class="item">4</a>
        <a class="icon item">
          <i class="right chevron icon"></i>
        </a>
      </div>
    </th>
  </tr></tfoot>
</table>
   			</div>
            
		</div>
		<div id="main_right" style="width:50px;font-size:0;display:table-cell;background-image: url(resources/images/index_tbbg.gif);">
        	<img height="50" src="resources/images/index_tbtop.gif" width="50" style="top:-10px;position:relative">
        </div>
	</div>
	




  
    
    
    
    
    
    <div id="footer">
            <table cellspacing="0" cellpadding="0" width="100%" style="background-image: url(resources/images/index_bottombg.gif)"
            border="0">
            <tr>
                <td width="230" valign="top">
                    <img src="resources/images/index_bottom1.gif" width="230">
                </td>
                <td>
                    <table class="englishfont" cellspacing="0" cellpadding="0" width="60%" align="center"
                        border="0">
                        <tr>
                            <td height="80px" valign="top">
                                <div align="center">
                                    <font color="#e7e7e7">最新修改日期: 2016年8月23日<br />
                                        中国科学院管理、决策与信息系统重点实验室&nbsp;&nbsp;版权所有 Copyright &copy;2016
                                        <br />
                                        <!--Telephone-->
                                        电话:10-62565817&nbsp;&nbsp;&nbsp;&nbsp;<!--EMail-->
                                        电子邮件:master@mdis.amss.ac.cn</font></div>
                                <p>
                                </p>
                                <div align="center">
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
                <td width="50" valign="top">
                    <div align="right">
                        <img src="resources/images/index_bottom2.gif" width="50" height="100" /></div>
                </td>
            </tr>
        </table>
    
    
    
    </div>
    
    
    <script type="text/babel" src="resources/app/example.js"></script>
    
    <script type="text/babel">
     
    </script>
  </body>
</html>