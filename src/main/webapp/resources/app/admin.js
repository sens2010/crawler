function changeContent(index)
{
	
	$(".ui a.item").removeClass("active");
	var content="";
	content += "<div class=\"ui container\">";
	
	if(index=="status")
	{
		content += "<h4 class=\"ui horizontal divider header\"><i class=\"bar chart icon\" /> 采集状态 </h4>";
		$("#status").addClass("active");
		
	}
	else if(index == "job")
	{
		content += "<h4 class=\"ui horizontal divider header\"><i class=\"bar chart icon\" /> 任务管理 </h4>";
		$("#job").addClass("active");
		
	}
	else if(index == "analysor")
	{
		content += "<h4 class=\"ui horizontal divider header\"><i class=\"bar chart icon\"></i> 解析器管理 </h4>";
		$("#analysor").addClass("active");
	}
	else if(index == "display")
	{
		content += "<h4 class=\"ui horizontal divider header\"><i class=\"bar chart icon\" /> 采集信息展示 </h4>";
		$("#display").addClass("active");
	}
	else if(index == "system")	
	{
		content += "<h4 class=\"ui horizontal divider header\"><i class=\"bar chart icon\" /> 系统管理 </h4>";
		$("#system").addClass("active");
	}
	
	content += "<table class=\"ui celled table\" id=\"content_table\"></table>";
	content += "</div>";
	
	$("#mainer").html(content);
	getContent(index);
};
function getContent(index)
{
	if(index=="status")
	{
		getStatusContent();
	}
	else if(index == "job")
	{
		getJobContent();
	}
	else if(index == "analysor")
	{
		getAnalysorContent();
	}
	else if(index == "display")
	{
		getDisplayContent();
	}
	else if(index == "system")	
	{
		getSystemContent();
	}
}


function getStatusContent()
{
	$.get("admin/status/list",function(result){
		var table = "<thead><tr>";
		table += "<th>任务名称</th><th>任务类型</th><th>时间</th><th>采集结果</th><th>采集总数</th><th>成功数量</th><th>失败数量</th><th>重复数量</th>";
		table += "</tr></thead>";
		table += "<tbody>";
		for(var i=0;i<result.length;i++)
		{
			table += "<tr>";
			table += "<td>"+result[i]["name"]+"</td><td>"+result[i]["type"]+"</td><td>"+(typeof(result[i]["endtime"])=="undefined"?"-":result[i]["endtime"])+"</td><td>"+(result[i]["result"]=="正常结束"? result[i]["result"]:"<div class=\"ui red ribbon label\">"+result[i]["result"]+"<div>")+"</td><td>"+result[i]["sum"]+"</td><td>"+result[i]["success"]+"</td><td>"+result[i]["fail"]+"</td><td>"+result[i]["same"]+"</td>";
			table += "</tr>";
		}
		table += "</tbody>";
		table += "<tfoot>";
		table += "<th><span class=\"ui left floated\"><button class=\"ui primary button\">下一页</button></span></th>";
		table += "</tfoot>";
		$("#content_table").html(table);
	});
}

function getJobContent()
{
	$.get("admin/job/list",function(result){
		var table = "<thead><tr>";
		table += "<th>任务名称</th><th>执行计划</th><th>任务分类</th><th>创建时间</th><th>最后修改时间</th><th>任务状态</th><th>任务操作</th>";
		table += "</tr></thead>";
		table += "<tbody>";
		for(var i=0;i<result.length;i++)
		{
			table += "<tr>";
			var statuslist = "<td><span class=\"ui small basic icon buttons\"><button class=\"ui active button\"><i class=\"play icon\" /></button><button class=\"ui button\"><i class=\"pause icon\" /></button><button class=\"ui button\"><i class=\"undo icon\" /></button></span></td>";
			var oplist = "<td > <span class=\"ui small basic icon buttons\"><button class=\"ui button\" onclick=\"changeJob();\"><i class=\"write icon\" /></button><button class=\"ui button\" onclick=\"infoJob();\"><i class=\"info Circle icon\" /></button><button class=\"ui button\"><i class=\"remove icon\" /></button></span></td>";
			table += "<td>"+result[i]["name"]+"</td><td>"+result[i]["plan"]+"</td><td>"+(typeof(result[i]["category"])=="undefined"?"-":result[i]["category"])+"</td><td>"+result[i]["createtime"]+"</td><td>"+(typeof(result[i]["lastmodifytime"])=="undefined"?"-":result[i]["lastmodifytime"])+"</td>"+statuslist+""+oplist+"";
			table += "</tr>";
		}
		table += "</tbody>";
		table += "<tfoot>";
		table += "<th><span class=\"ui left floated\"><button class=\"ui primary button\" onclick=\"addJob();\">添加任务</button></span></th>";
		table += "<th colSpan=\"6\"><span class=\"ui right floated pagination menu\"><a class=\"icon item\"><i class=\"left chevron icon\"></i></a>";
		table += "<a class=\"item\">1</a>";
		table += "<a class=\"icon item\"><i class=\"right chevron icon\"></i></a></span></th>";
		table += "</tfoot>";
		$("#content_table").html(table);
	});
}

function getAnalysorContent()
{
	$.get("admin/analysor/list",function(result){
		result = eval(result);
		var table = "<thead><tr>";
		table += "<th>名称</th><th>状态</th><th>解析网站个数</th><th>创建时间</th><th>最后修改时间</th><th>相关操作</th>";
		table += "</tr></thead>";
		table += "<tbody>";
		for(var i=0;i<result.length;i++)
		{
			table += "<tr>";
			var oplist = "<td > <span class=\"ui small basic icon buttons\"><button class=\"ui button\" onclick=\"changeAnalysor();\"><i class=\"write icon\" /></button><button class=\"ui button\" onclick=\"infoAnalysor();\"><i class=\"info Circle icon\" /></button><button class=\"ui button\" onclick=\"testAnalysor();\"><i class=\"browser icon\" /></button><button class=\"ui button\"><i class=\"remove icon\" /></button></span></td>";
			table += "<td>"+result[i]["id"]+"</td><td>正常</td><td>1</td><td>2016-07-25 16:41:43</td><td>-</td>"+oplist+"";
			table += "</tr>";
		}
		table += "</tbody>";
		table += "<tfoot>";
		table += "<th><span class=\"ui left floated\"><button class=\"ui primary button\" onclick=\"addAnalysor();\">添加解析器</button></span></th>";
		table += "<th colSpan=\"6\"><span class=\"ui right floated pagination menu\"><a class=\"icon item\"><i class=\"left chevron icon\"></i></a>";
		table += "<a class=\"item active\">1</a>";
		table += "<a class=\"item\">2</a>";
		table += "<a class=\"item\">3</a>";
		table += "<a class=\"icon item\"><i class=\"right chevron icon\"></i></a></span></th>";
		table += "</tfoot>";
		$("#content_table").html(table);
	});
}

function getDisplayContent()
{
	
}

function getSystemContent()
{
	
}
function addJob()
{
	$("#content_modal_header").html("添加任务");
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	var form = "<form class=\"ui form\">"
			  +"<div class=\"field\">"
			  +"<label>任务名称</label>"
			  +"<input type=\"text\" name=\"first-name\" placeholder=\"任务名称\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>执行计划</label>"
			  +"<input type=\"text\" name=\"last-name\" placeholder=\"intr|cron|time\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>任务分类</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"执行计划\">"
			  +"<option value=\"1\">财经门户</option>"
			  +"<option value=\"1\">政府官方网站</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>任务描述</label>"
			  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"任务描述\"></textarea>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\">提交</div>";
	actions += "<div class=\"ui button\">取消</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
}

function changeJob()
{
	$("#content_modal_header").html("修改任务");
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	var oplist = "<td > <span class=\"ui small basic icon buttons\"><button class=\"ui button\"><i class=\"write icon\" /></button><button class=\"ui button\"><i class=\"info Circle icon\" /></button><button class=\"ui button\"><i class=\"remove icon\" /></button></span></td>";
	
	var form = "<form class=\"ui form\">"
			  +"<div class=\"field\">"
			  +"<label>任务名称</label>"
			  +"<input type=\"text\" name=\"first-name\" value=\"搜狐财经\" placeholder=\"\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>执行计划</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"intr:2h\" placeholder=\"intr|cron|time\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>任务分类</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"执行计划\">"
			  +"<option value=\"1\">财经门户</option>"
			  +"<option value=\"1\">政府官方网站</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>任务描述</label>"
			  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"任务描述\"></textarea>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>相关子任务</label>"
			  +"<table class=\"ui celled table\" id=\"job_content_table\">"
			  +"<thead>"
			  +"<tr>"
			  +"<th>子任务名称</th><th>网站地址</th><th>解析器</th><th>类别</th><th>状态</th><th>创建时间</th><th>操作</th>"
			  +"</tr>"
			  +"</thead>"
			  +"<tbody>"
			  +"<tr>"
			  +"<td>宏观经济</td><td>http://business.sohu.com/hgjj/</td><td>16</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"+oplist
			  +"</tr>"
			  +"<tr>"
			  +"<td>金融监管</td><td>http://business.sohu.com/jrjg/</td><td>17</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"+oplist
			  +"</tr>"
			  +"<tr>"
			  +"<td>金融改革</td><td>http://business.sohu.com/jqgg/</td><td>18</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"+oplist
			  +"</tr>"
			  +"<tr>"
			  +"<td>金融机构</td><td>http://business.sohu.com/c241863626/</td><td>19</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"+oplist
			  +"</tr>"
			  +"<tr>"
			  +"<td>金融动态</td><td>http://business.sohu.com/jrqj/</td><td>20</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"+oplist
			  +"</tr>"
			  +"<tr>"
			  +"<td>证券要闻</td><td>http://stock.sohu.com/news/</td><td>21</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"+oplist
			  +"</tr>"
			  +"</tbody>"
			  +"</table>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\">修改</div>";
	actions += "<div class=\"ui button\">取消</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
}

function infoJob()
{
	$("#content_modal_header").html("查看任务");
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	
	var form = "<form class=\"ui form\">"
			  +"<div class=\"disabled field\">"
			  +"<label>任务名称</label>"
			  +"<input type=\"text\" name=\"first-name\" value=\"搜狐财经\" placeholder=\"\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>执行计划</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"intr:2h\" placeholder=\"intr|cron|time\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>任务分类</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"执行计划\">"
			  +"<option value=\"1\">财经门户</option>"
			  +"<option value=\"1\">政府官方网站</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>任务描述</label>"
			  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"任务描述\"></textarea>"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>相关子任务</label>"
			  +"<table class=\"ui celled table\" id=\"job_content_table\">"
			  +"<thead>"
			  +"<tr>"
			  +"<th>子任务名称</th><th>网站地址</th><th>解析器</th><th>类别</th><th>状态</th><th>创建时间</th>"
			  +"</tr>"
			  +"</thead>"
			  +"<tbody>"
			  +"<tr>"
			  +"<td>宏观经济</td><td>http://business.sohu.com/hgjj/</td><td>16</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"
			  +"</tr>"
			  +"<tr>"
			  +"<td>金融监管</td><td>http://business.sohu.com/jrjg/</td><td>17</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"
			  +"</tr>"
			  +"<tr>"
			  +"<td>金融改革</td><td>http://business.sohu.com/jqgg/</td><td>18</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"
			  +"</tr>"
			  +"<tr>"
			  +"<td>金融机构</td><td>http://business.sohu.com/c241863626/</td><td>19</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"
			  +"</tr>"
			  +"<tr>"
			  +"<td>金融动态</td><td>http://business.sohu.com/jrqj/</td><td>20</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"
			  +"</tr>"
			  +"<tr>"
			  +"<td>证券要闻</td><td>http://stock.sohu.com/news/</td><td>21</td><td>暂无分类</td><td>正常</td><td>2016-07-25 16:41:43</td>"
			  +"</tr>"
			  +"</tbody>"
			  +"</table>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\">确定</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
}


function addAnalysor()
{
	$("#content_modal_header").html("添加解析器");
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	var form = "<form class=\"ui form\">"
			  +"<div class=\"field\">"
			  +"<label>列表解析规则</label>"
			  +"<input type=\"text\" name=\"first-name\" placeholder=\"列表解析规则\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field\">"
			  +"<label>列表是否支持CSS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>列表是否支持JS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>下一页解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" placeholder=\"下一页解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>标题解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" placeholder=\"标题解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>时间解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" placeholder=\"时间解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>时间转换规则</label>"
			  +"<input type=\"text\" name=\"last-name\" placeholder=\"时间转换规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>来源解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" placeholder=\"来源解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>正文解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" placeholder=\"正文解析规则\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field\">"
			  +"<label>正文是否支持CSS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>正文是否支持JS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>是否是相对路径</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\">提交</div>";
	actions += "<div class=\"ui button\">取消</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
}

function changeAnalysor()
{
	$("#content_modal_header").html("修改解析器");
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	var form = "<form class=\"ui form\">"
			  +"<div class=\"disabled field\">"
			  +"<label>解析器编号</label>"
			  +"<input type=\"text\" name=\"first-name\" value=\"16\" placeholder=\"列表解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>创建时间</label>"
			  +"<input type=\"text\" name=\"first-name\" value=\"2016-07-25 16:41:43\" placeholder=\"列表解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>列表解析规则</label>"
			  +"<input type=\"text\" name=\"first-name\" value=\"[@class='f14list']/ul/li/a\" placeholder=\"列表解析规则\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field\">"
			  +"<label>列表是否支持CSS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>列表是否支持JS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>下一页解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@class='pages']/p/table/tbody/tr/td/a[3]\" placeholder=\"下一页解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>标题解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1\" placeholder=\"标题解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>时间解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']\" placeholder=\"时间解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>时间转换规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"yyyy-MM-dd hh:mm:ss\" placeholder=\"时间转换规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>来源解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span\" placeholder=\"来源解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>正文解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@itemprop='articleBody']\" placeholder=\"正文解析规则\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field\">"
			  +"<label>正文是否支持CSS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>正文是否支持JS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>是否是相对路径</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\">提交</div>";
	actions += "<div class=\"ui button\">取消</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
}

function testAnalysor()
{
	$("#content_modal_header").html("测试解析器-16");
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	var form = "<form class=\"ui form\">"
			/*+"<div class=\"two fields\">"*/
			  +"<div class=\"field\">"
			  +"<label>输入网页</label>"
			  +"<input type=\"text\" name=\"first-name\"  placeholder=\"http://crudeoil.hexun.com\">"
			  +"<div class=\"ui primary button\" onclick=\"testAnalysorGet();\">确定</div>"
			  +"</div>"
			  /*+"<div class=\"field\">"
			  +"<button class=\"ui button\">Button</button>"
			  +"</div>"*/
			  /*+"</div>"*/
			  +"<div class=\"disabled field\">"
			  +"<label>网页地址</label>"
			  +"<input type=\"text\" name=\"first-name\" id=\"analysor_address\" placeholder=\"网页地址\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>标题</label>"
			  +"<input type=\"text\" name=\"first-name\" id=\"analysor_title\" placeholder=\"标题\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>信息来源</label>"
			  +"<input type=\"text\" name=\"first-name\" id=\"analysor_source\" placeholder=\"信息来源\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>发布时间</label>"
			  +"<input type=\"text\" name=\"first-name\" id=\"analysor_pubtime\" placeholder=\"发布时间\">"
			  +"</div>"


			  +"<div class=\"+field\">"
			  +"<label>正文内容</label>"
			  +"<textarea type=\"text\" rows=\"4\"  id=\"analysor_text\" placeholder=\"正文内容\"></textarea>"
			  +"</div>"
			  
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\">提交</div>";
	actions += "<div class=\"ui button\">取消</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
}

function testAnalysorGet()
{
	console.log("1");
	$("#analysor_address").attr("value","http://crudeoil.hexun.com/2015-10-30/180250041.html");
	$("#analysor_source").attr("value","汇通网");
	$("#analysor_pubtime").attr("value","2015-10-30 18:08:00");
	$("#analysor_title").attr("value","彭博将大宗商品指数中布油权重提高至7.53%，首次高于美油");
	$("#analysor_text").html("汇通网10月30日讯――彭博周四(10月29日)提高了2016年彭博大宗商品指数中布伦特原油的权重，这是布伦特原油的权重首次高于美国原油的权重，彭博同时还调低了黄金在该指数中的权重。彭博将布伦特原油期货的权重从7.16%提高到7.53%，将美国原油的权重从7.84%降至7.47%，为2012年布伦特原油纳入该指数以来首次超过美国原油的权重。在2016年彭博大宗商品指数中，布伦特原油期货权重的提高幅度最大，下调幅度最大的是黄金权重，从11.90%下调至11.38%，但黄金依然是该指数中权重最高的商品。彭博称，追踪彭博大宗商品指数的投资基金总规模达到650亿美元，而追踪标普高盛商品价格指数的投资基金总规模为逾2,000亿美元，该指数追踪24种大宗商品。北京时间17:44，布伦特12月原油期货报48.96美元/桶；美国NYMEX 12月原油期货报45.94美元/桶。（责任编辑：HN666）");

}
function infoAnalysor()
{
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	
	$("#content_modal_header").html("查看解析器");
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	var form = "<form class=\"ui form\">"
			  +"<div class=\"disabled field\">"
			  +"<label>解析器编号</label>"
			  +"<input type=\"text\" name=\"first-name\" value=\"16\" placeholder=\"列表解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>创建时间</label>"
			  +"<input type=\"text\" name=\"first-name\" value=\"2016-07-25 16:41:43\" placeholder=\"列表解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>列表解析规则</label>"
			  +"<input type=\"text\" name=\"first-name\" value=\"[@class='f14list']/ul/li/a\" placeholder=\"列表解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled two fields\">"
			  +"<div class=\"field\">"
			  +"<label>列表是否支持CSS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>列表是否支持JS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>下一页解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@class='pages']/p/table/tbody/tr/td/a[3]\" placeholder=\"下一页解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>标题解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@id='container']/div[1]/div[@class='content-box clear']/h1\" placeholder=\"标题解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>时间解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='time']\" placeholder=\"时间解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>时间转换规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"yyyy-MM-dd hh:mm:ss\" placeholder=\"时间转换规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>来源解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@id='container']/div[1]/div[@class='content-box clear']/div[@class='time-fun clear']/*/div[@class='source']/span/span\" placeholder=\"来源解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>正文解析规则</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\"//*/div[@itemprop='articleBody']\" placeholder=\"正文解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled two fields\">"
			  +"<div class=\"field\">"
			  +"<label>正文是否支持CSS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>正文是否支持JS</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>是否是相对路径</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"1\">否</option>"
			  +"<option value=\"1\">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\">关闭</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
}