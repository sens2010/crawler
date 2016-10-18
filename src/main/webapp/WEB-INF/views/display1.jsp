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
    <script src="resources/echarts/echarts.min.js"></script>
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
				<img height="60" src="resources/images/index_lmtop.gif" width="200" style="display:block">
			</div>
			<div style="display:table">
			<div style="font-size:0;display:table-cell">
				<img height="120" src="resources/images/index_lmcenter.gif" width="40" >
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
			
			<div style="background-image: url(resources/images/index_centerbg.gif); height: 9px;width:100%;margin:0;top:-110px;position:relative">
                                   
            </div>
            
            <div style="margin-left:40px;margin-top:40px">      
            	<table id="ctl00_DataList1" cellspacing="10" border="0" style="background-color:White;">
	<tbody><tr>
		<td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl00_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl00$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=10", false, true))' style="font-weight:bold;">[中国经济增长]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl01_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl01$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=11", false, true))' style="font-weight:bold;">[中国通货膨胀]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl02_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl02$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=12", false, true))' style="font-weight:bold;">[中国外贸监测]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl03_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl03$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=31", false, true))' style="font-weight:bold;">[有色金属行业]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl04_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl04$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=14", false, true))' style="font-weight:bold;">[美国经济增长]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl05_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl05$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=15", false, true))' style="font-weight:bold;">[全球通货膨胀]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl06_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl06$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=16", false, true))' style="font-weight:bold;">[全球经济增长]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl07_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl07$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=17", false, true))' style="font-weight:bold;">[美国通货膨胀]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl08_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl08$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=19", false, true))' style="font-weight:bold;">[OECD先行指数]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl09_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl09$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=25", false, true))' style="font-weight:bold;">[俄罗斯经济增长]</a>
                                                    </td>
	</tr><tr>
		<td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl10_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl10$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=26", false, true))' style="font-weight:bold;">[澳大利亚经济增长]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl11_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl11$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=27", false, true))' style="font-weight:bold;">[印度经济增长]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl12_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl12$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=28", false, true))' style="font-weight:bold;">[加拿大经济增长]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl13_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl13$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=29", false, true))' style="font-weight:bold;">[欧债各国CPI]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl14_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl14$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=24", false, true))' style="font-weight:bold;">[全球工业生产]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl15_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl15$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=36", false, true))' style="font-weight:bold;">[煤炭行业]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl16_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl16$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=35", false, true))' style="font-weight:bold;">[家电行业]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl17_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl17$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=41", false, true))' style="font-weight:bold;">[房地产行业]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl18_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl18$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=37", false, true))' style="font-weight:bold;">[钢铁行业]</a>
                                                    </td><td style="white-space:nowrap;">
                                                        <a id="ctl00_DataList1_ctl19_linkMenu" href='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("ctl00$DataList1$ctl19$linkMenu", "", false, "", "Default.aspx?EcoMonitorScenarioID=39", false, true))' style="font-weight:bold;">[物流行业]</a>
                                                    </td>
	</tr>
</tbody></table>
            	   	
    			<!-- <div id="display_contents" style="margin-top:60px;width: 700px;height:400px;display:inline-block;float:left;">
    			</div> -->
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
    
     <script type="text/javascript">
     var myChart = echarts.init(document.getElementById('display_content'));
     
     var option = {
    		    title: {
    		        text: '最近解析数据'
    		    },
    		    tooltip : {
    		        trigger: 'axis'
    		    },
    		    legend: {
    		        data:['成功解析','失败解析','重复解析','单次解析','历史解析'],
    		     
    		    },
    		    toolbox: {
    		        feature: {
    		            saveAsImage: {}
    		        }
    		    },
    		    grid: {
    		        left: '3%',
    		        right: '4%',
    		        bottom: '3%',
    		        containLabel: true
    		    },
    		    xAxis : [
    		        {
    		            type : 'category',
    		            boundaryGap : false,
    		            data : ['0801','0802','0803','0804','0805','0806','0807']
    		        }
    		    ],
    		    backgroundColor:'#fff',
    		    yAxis : [
    		        {
    		            type : 'value'
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'成功解析',
    		            type:'line',
    		            stack: '总量',
    		            areaStyle: {normal: {}},
    		            data:[120, 132, 101, 134, 90, 230, 210]
    		        },
    		        {
    		            name:'失败解析',
    		            type:'line',
    		            stack: '总量',
    		            areaStyle: {normal: {}},
    		            data:[220, 182, 191, 234, 290, 330, 310]
    		        },
    		        {
    		            name:'重复解析',
    		            type:'line',
    		            stack: '总量',
    		            areaStyle: {normal: {}},
    		            data:[150, 232, 201, 154, 190, 330, 410]
    		        },
    		        {
    		            name:'单次解析',
    		            type:'line',
    		            stack: '总量',
    		            areaStyle: {normal: {}},
    		            data:[320, 332, 301, 334, 390, 330, 320]
    		        },
    		        {
    		            name:'历史解析',
    		            type:'line',
    		            stack: '总量',
    		            label: {
    		                normal: {
    		                    show: true,
    		                    position: 'top'
    		                }
    		            },
    		            areaStyle: {normal: {}},
    		            data:[820, 932, 901, 934, 1290, 1330, 1320]
    		        }
    		    ]
    		};

     
     
     myChart.setOption(option);
     </script>
    
  </body>
</html>