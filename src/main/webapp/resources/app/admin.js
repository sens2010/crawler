var chooselabels = [];
var choosenews =[];
var timerangetype = 0;
var datalist=[];
var timelabels=[];
var timeindexes={};

$.postJSON = function(url, data, callback) {
    return jQuery.ajax({
    'type': 'POST',
    'url': url,
    'contentType': 'application/json;charset=UTF8',
    'data': JSON.stringify(data),
    'dataType': 'json',
    'success': callback
    });
};

$.putJSON = function(url, data, callback) {
    return jQuery.ajax({
    'type': 'PUT',
    'url': url,
    'contentType': 'application/json;charset=UTF8',
    'data': JSON.stringify(data),
    'dataType': 'json',
    'success': callback
    });
};

$.deleteJSON = function(url, callback) {
    return jQuery.ajax({
    'type': 'DELETE',
    'url': url,
    'contentType': 'application/json;charset=UTF8',
    'dataType': 'json',
    'success': callback
    });
};

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
	else if(index == "newslist")
	{
		content += "<h4 class=\"ui horizontal divider header\"><i class=\"bar chart icon\"></i> 新闻标注 </h4>";
		$("#newslist").addClass("active");
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
	content += "<div id=\"pretable_content\" class=\"ui container\"></div>";
	content += "<table class=\"ui celled table\" id=\"content_table\"></table>";
	content += "<div id=\"latertable_content\"></div>";
	content += "</div>";
	
	$("#mainer").html(content);
	getContent(index);
};
function getContent(index)
{
	if(index=="status")
	{
		getStatusContent(0);
	}
	else if(index == "job")
	{
		getJobContent(0);
	}
	else if(index == "analysor")
	{
		getAnalysorContent(0);
	}
	else if(index == "newslist")
	{
		getNewsListContent(0);
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


function getStatusContent(index)
{
	$.get("admin/status/list/"+index,function(raw_result){
		
		var result = raw_result.data;
		var count = raw_result.count;
		var pre = "<button class=\"ui primary button\" onclick=\"getStatusContent("+eval(index-1)+")\">上一页</button>";
		var next = "<button class=\"ui primary button\" onclick=\"getStatusContent("+eval(index+1)+")\">下一页</button>";
		var pagelist="";
		if(index>0)
		{
			pagelist+=pre;
		}
		if(index<count-1)
		{
			pagelist+=next;
		}
		var table = "<thead><tr>";
		table += "<th>任务名称</th><th>任务类型</th><th>起始时间</th><th>终止时间</th><th>采集结果</th><th>采集总数</th><th>成功数量</th><th>失败数量</th><th>重复数量</th>";
		table += "</tr></thead>";
		table += "<tbody>";
		for(var i=0;i<result.length;i++)
		{
			table += "<tr>";
			table += "<td>"+result[i]["name"]+"</td><td>"+result[i]["type"]+"</td><td>"+(typeof(result[i]["starttime"])=="undefined"?"-":result[i]["starttime"])+"</td><td>"+(typeof(result[i]["endtime"])=="undefined"?"-":result[i]["endtime"])+"</td><td>"+(result[i]["result"]=="正常结束"? result[i]["result"]:"<div class=\"ui red ribbon label\">"+result[i]["result"]+"<div>")+"</td><td>"+result[i]["sum"]+"</td><td>"+result[i]["success"]+"</td><td>"+result[i]["fail"]+"</td><td>"+result[i]["same"]+"</td>";
			table += "</tr>";
		}
		table += "</tbody>";
		table += "<tfoot>";
		table += "<th><span class=\"ui left floated\">"+pagelist+"</span></th>";
		table += "<th colSpan=\"8\"><span class=\"ui right floated pagination \">";
		table +="</span></th>";
		table += "</tfoot>";
		$("#content_table").html(table);
	});
}

function getJobContent(index)
{
	$.get("admin/job/list/"+index,function(raw_result){
		var result = raw_result.data;
		var count = raw_result.count;
		var pre = 0;
		var next = index ;
		if(index>0)pre=index-1;
		if(index<count-1)next=index+1;
		var pagelist = "<a class=\"icon item\" onclick=\"getJobContent("+pre+")\"><i class=\"left chevron icon\"></i></a>";
		for(var c=0;c<count;c++)
		{
			if(index!=c)
			{
				pagelist += "<a class=\"item\" onclick=\"getJobContent("+c+");\">"+eval(c+1)+"</a>";
			}
			else
			{
				pagelist += "<a class=\"active item\" onclick=\"getJobContent("+c+");\">"+eval(c+1)+"</a>";
			}
		}
		pagelist += "<a class=\"icon item\" onclick=\"getJobContent("+next+");\"><i class=\"right chevron icon\"></i></a>";
		console.log(pagelist);
		var table = "<thead><tr>";
		table += "<th>任务名称</th><th>执行计划</th><th>任务分类</th><th>创建时间</th><th>最后修改时间</th><th>任务状态</th><th>任务操作</th>";
		table += "</tr></thead>";
		table += "<tbody>";
		console.log(result);
		for(var i=0;i<result.length;i++)
		{
			table += "<tr>";
			var statuslist = "";
			
			if(result[i]["status"]=="R")
			{
				statuslist = "<td><span class=\"ui small basic icon buttons\"><button class=\"ui active button\" onclick=\"startJob("+result[i]["id"]+");\"><i class=\"play icon\" /></button><button class=\"ui button\" onclick=\"stopJob("+result[i]["id"]+");\"><i class=\"pause icon\" /></button><button class=\"ui button\" onclick=\"restartJob("+result[i]["id"]+");\"><i class=\"undo icon\" /></button></span></td>";
			}
			else
			{
				statuslist = "<td><span class=\"ui small basic icon buttons\"><button class=\"ui  button\" onclick=\"startJob("+result[i]["id"]+");\"><i class=\"play icon\" /></button><button class=\"ui active button\" onclick=\"stopJob("+result[i]["id"]+");\"><i class=\"pause icon\" /></button><button class=\"ui button\" onclick=\"restartJob("+result[i]["id"]+");\"><i class=\"undo icon\" /></button></span></td>";
			}
			var oplist = "<td > <span class=\"ui small basic icon buttons\"><button class=\"ui button\" onclick=\"changeJob("+result[i]["id"]+");\"><i class=\"write icon\" /></button><button class=\"ui button\" onclick=\"infoJob("+result[i]["id"]+");\"><i class=\"info Circle icon\" /></button><button class=\"ui button\" onclick=\"deleteJob("+result[i]["id"]+");\"><i class=\"remove icon\" /></button></span></td>";
			table += "<td>"+result[i]["name"]+"</td><td>"+result[i]["plan"]+"</td><td>"+(typeof(result[i]["category"])=="undefined"?"-":result[i]["category"])+"</td><td>"+result[i]["createtime"]+"</td><td>"+(typeof(result[i]["lastmodifytime"])=="undefined"?"-":result[i]["lastmodifytime"])+"</td>"+statuslist+""+oplist+"";
			table += "</tr>";
		}
		table += "</tbody>";
		table += "<tfoot>";
		table += "<th><span class=\"ui left floated\"><button class=\"ui primary button\" onclick=\"addJob();\">添加任务</button></span></th>";
		table += "<th colSpan=\"6\"><span class=\"ui right floated pagination menu\">";
		table += pagelist;
		table +="</span></th>";
		table += "</tfoot>";
		$("#content_table").html(table);
	});
}

function display_page(index,count,size)
{
	console.log("index:"+index+",count:"+count+",size:"+size);
	var top_down = {};
	if(index<size/2)
	{
		if(count<size)
		{
			top_down["top"]=0;
			top_down["down"]=count-1;
		}
		else
		{
			top_down["top"]=0;
			top_down["down"]=size-1;
		}
	}
	else
	{
		if(count<(size/2)+index)
		{
			top_down["top"]=count-size;
			top_down["down"]=size-1;
		}
		else
		{
			top_down["top"]=index-Math.floor(size/2);
			top_down["down"]=index+Math.floor(size/2);
		}
	}
	return top_down;
}


function getNewsListContent(index)
{
	var pagesize = 5;
	$.get("admin/news/list/"+index,function(raw_result){
		var result = raw_result.data;
		var count = raw_result.count;
		var pre = 0;
		var next = index ;
		if(index>0)pre=index-1;
		if(index<count-1)next=index+1;
		var pagelist = "<a class=\"icon item\" onclick=\"getNewsListContent("+pre+")\"><i class=\"left chevron icon\"></i></a>";
		
		var top_down = display_page(index,count,pagesize);
		
		for(var c=top_down["top"];c<=top_down["down"];c++)
		{
			if(index!=c)
			{
				pagelist += "<a class=\"item\" onclick=\"getNewsListContent("+c+");\">"+eval(c+1)+"</a>";
			}
			else
			{
				pagelist += "<a class=\"active item\" onclick=\"getNewsListContent("+c+");\">"+eval(c+1)+"</a>";
			}
		}
		pagelist += "<a class=\"icon item\" onclick=\"getNewsListContent("+next+");\"><i class=\"right chevron icon\"></i></a>";
		console.log(pagelist);
		var table = "<thead><tr>";
		table += "<th></th><th>新闻ID</th><th>新闻标题</th><th>发布时间</th><th>标签</th><th>操作</th>";
		table += "</tr></thead>";
		table += "<tbody>";
		console.log(result);
		for(var i=0;i<result.length;i++)
		{
			var element = result[i];
			var labels = [];
			for(var j=0;j<element.labels.length;j++)
			{
				labels.push(element.labels[j].name);
			}
			
			var oplist = "<td > <span class=\"ui small basic icon buttons\"><button class=\"ui button\" onclick=\"getNewsLabels("+index+","+result[i]["id"]+");\"><i class=\"add icon\" /></button><button class=\"ui button\" onclick=\"changeNews("+result[i]["id"]+");\"><i class=\"write icon\" /></button><button class=\"ui button\" onclick=\"infoNews("+result[i]["id"]+");\"><i class=\"info Circle icon\" /></button><button class=\"ui button\" onclick=\"deleteNews("+result[i]["id"]+","+index+");\"><i class=\"remove icon\" /></button></span></td>";
			
			table += "<tr>";
			table += "<td><input type=\"checkbox\" class=\"newscheck\" id=\"news_"+element.id+"\"></td>";
			table += "<td>"+element.id+"</td>"+"<td>"+(typeof(element.title)=="undefined"?"-":element.title)+"</td>"+"<td>"+(typeof(element.pubtime)=="undefined"?"-":element.pubtime)+"</td>"+"<td>"+labels.join("，")+"</td>";
			table += oplist;
			table += "</tr>";
		}
		table += "</tbody>";
		table += "<tfoot>";
		table += "<th colSpan=\"6\"><span class=\"ui right floated pagination menu\">";
		table += pagelist;
		table +="</span></th>";
		table += "</tfoot>";
		$("#content_table").html(table);
		//var precontent = "<span class=\"ui left floated\"><button class=\"ui primary button\" onclick=\"addJob();\">全选</button><button class=\"ui primary button\" onclick=\"addJob();\">添加标签</button></span>";
		var selector="";
		selector+="<select class=\"ui search dropdown\" id=\"newssearchselector\" onchange=\"changeNewsContent();\">";
		selector+="<option value=\"default\">请选择...</option>";
		selector+="<option value=\"id\">ID</option>";
		selector+="<option value=\"name\">标题</option>";
		selector+="<option value=\"resource\">发布机构</option>";
		selector+="<option value=\"text\">正文</option>";
		selector+="</select>";
		
		
		
		var precontent = "<div class=\"ui container\"><button class=\"ui primary button left floated\" onclick=\"selectAllNews();\"><i class=\"checkmark icon\"></i>全选</button><button class=\"ui primary button left floated\" onclick=\"reverseSelectAllNews();\"><i class=\"remove icon\"></i>反选</button><button class=\"ui primary button left floated\" onclick=\"getNewsLabels("+index+");\"><i class=\"plus icon\"></i>标注</button><div class=\"ui search buttons right floated\"><div class=\"ui icon input \">"+selector+"<input class=\"prompt\" type=\"text\" id=\"newssearchcontent\" placeholder=\"查询内容\" onchange=\"changeNewsContent();\"><i class=\"search icon\"></i></div><p></p></div></div>";
		
		$("#pretable_content").html(precontent);
		$("#aftertable_content").html("");
		
	});

}

function changeNewsContent(index)
{
	var pagesize = 5;
	if(typeof(index)=="undefined")index=0;
	var searchstr = $("#newssearchcontent").val();
	var selector = $("#newssearchselector").val();
	if(selector!="default"&&searchstr!="")
	{
		var data = {};
		data["name"]=selector;
		data["value"]=searchstr;
		console.log(data);
		$.postJSON("admin/news/list/"+index, data, function(raw_result){
			var result = raw_result.data;
			var count = raw_result.count;
			var pre = 0;
			var next = index ;
			if(index>0)pre=index-1;
			if(index<count-1)next=index+1;
			var pagelist = "<a class=\"icon item\" onclick=\"changeNewsContent("+pre+")\"><i class=\"left chevron icon\"></i></a>";
			
			var top_down = display_page(index,count,pagesize);
			
			for(var c=top_down["top"];c<=top_down["down"];c++)
			{
				if(index!=c)
				{
					pagelist += "<a class=\"item\" onclick=\"changeNewsContent("+c+");\">"+eval(c+1)+"</a>";
				}
				else
				{
					pagelist += "<a class=\"active item\" onclick=\"changeNewsContent("+c+");\">"+eval(c+1)+"</a>";
				}
			}
			pagelist += "<a class=\"icon item\" onclick=\"changeNewsContent("+next+");\"><i class=\"right chevron icon\"></i></a>";
			console.log(pagelist);
			var table = "<thead><tr>";
			table += "<th></th><th>新闻ID</th><th>新闻标题</th><th>发布时间</th><th>标签</th><th>操作</th>";
			table += "</tr></thead>";
			table += "<tbody>";
			console.log(result);
			for(var i=0;i<result.length;i++)
			{
				var element = result[i];
				var labels = [];
				for(var j=0;j<element.labels.length;j++)
				{
					labels.push(element.labels[j].name);
				}
				
				var oplist = "<td > <span class=\"ui small basic icon buttons\"><button class=\"ui button\" onclick=\"getNewsLabels("+index+","+result[i]["id"]+");\"><i class=\"add icon\" /></button><button class=\"ui button\" onclick=\"changeNews("+result[i]["id"]+");\"><i class=\"write icon\" /></button><button class=\"ui button\" onclick=\"infoNews("+result[i]["id"]+");\"><i class=\"info Circle icon\" /></button><button class=\"ui button\" onclick=\"deleteNews("+result[i]["id"]+","+index+");\"><i class=\"remove icon\" /></button></span></td>";
				
				table += "<tr>";
				table += "<td><input type=\"checkbox\" class=\"newscheck\" id=\"news_"+element.id+"\"></td>";
				table += "<td>"+element.id+"</td>"+"<td>"+(typeof(element.title)=="undefined"?"-":element.title)+"</td>"+"<td>"+(typeof(element.pubtime)=="undefined"?"-":element.pubtime)+"</td>"+"<td>"+labels.join("，")+"</td>";
				table += oplist;
				table += "</tr>";
			}
			table += "</tbody>";
			table += "<tfoot>";
			table += "<th colSpan=\"6\"><span class=\"ui right floated pagination menu\">";
			table += pagelist;
			table +="</span></th>";
			table += "</tfoot>";
			$("#content_table").html(table);
		});
	}
	else
	{
		return ;
	}
}



function removeNewsLabels(index,id)
{
	$.get("admin/news/"+id,function(raw_result){
		var labels = result.labels;
		for(var i=0;i<labels.length;i++)
		{
			labellist += "<a class=\"ui blue tag label\">"+labels[i].name+"</a>";
		}
		var form = "<form class=\"ui form\">"
			+"<div class=\"field disabled\">"
			+"<label>标签 </label>"
			+"<div class=\"ui container\" id=\"labellist\">"+labellist+"</div>"
			+"</div>"
			+"</form>";
		$("#content_modal_description").html(form);
		var actions = "";
		actions += "<div class=\"ui primary button\" onclick=\"modalfade();\">确定</div>";
		$("#content_modal_actions").html(actions);
		$("#content_modal").modal("show");
		$("#content_modal_header").html("标签删除");
		
	});


}
function infoNews(id)
{
	$.get("admin/news/"+id,function(raw_result){
		var result = raw_result.data;
		var labels = result.labels;
		
		var labellist = "";
		for(var i=0;i<labels.length;i++)
		{
			labellist += "<a class=\"ui blue tag label\">"+labels[i].name+"</a>";
		}
		
		var form = "<form class=\"ui form\">"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field disabled\">"
		      +"<label>ID</label>"
			  +"<input type=\"text\" id=\"info_news_id\" value=\""+result.id+"\" placeholder=\"ID\">"
			  +"</div>"
			  +"<div class=\"field disabled\">"
		      +"<label>编号</label>"
			  +"<input type=\"text\" id=\"info_news_sno\" value=\""+result.newsid+"\" placeholder=\"编号\">"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field disabled\">"
		      +"<label>URL</label>"
			  +"<input type=\"text\" id=\"info_news_url\" value=\""+result.url+"\" placeholder=\"URL\">"
			  +"</div>"
			  +"<div class=\"field disabled\">"
		      +"<label>标题</label>"
			  +"<input type=\"text\" id=\"info_news_title\" value=\""+result.title+"\" placeholder=\"标题\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field disabled\">"
			  +"<label>来源</label>"
			  +"<input type=\"text\" id=\"info_news_source\" value=\""+result.resource+"\" placeholder=\"来源\">"
			  +"</div>"
			  +"<div class=\"field disabled\">"
		      +"<label>发布时间</label>"
			  +"<input type=\"text\" id=\"info_news_pubtime\" value=\""+result.pubtime+"\" placeholder=\"发布时间\">"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field disabled\">"
		      +"<label>采集时间</label>"
			  +"<input type=\"text\" id=\"info_news_collecttime\" value=\""+result.createtime+"\" placeholder=\"采集时间\">"
			  +"</div>"
			  +"<div class=\"field disabled\">"
		      +"<label>最后修改时间</label>"
			  +"<input type=\"text\" id=\"info_news_lastmodifytime\" value=\""+result.lastmodifytime+"\" placeholder=\"最后修改时间\">"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field disabled\">"
		      +"<label>标签列表</label>"
			  +labellist
			  +"</div>"
			  +"<div class=\"field\">"
		 	  +"<label>描述</label>"
		 	  +"<div class=\"ui text container\" id=\"info_news_description\">"+"<p>"+(result.text.replace(/\r\n/g,"</p><p>"))+"</p>"+"</div>"
		 	  +"</div>"
		 	  +"</form>";
		console.log((result.text.replace(/\r\n/,"</p><p>")));
		$("#content_modal_description").html(form);
		var actions = "";
		actions += "<div class=\"ui primary button\" onclick=\"modalfade();\">确定</div>";
		$("#content_modal_actions").html(actions);
		$("#content_modal").modal("show");
		$("#content_modal_header").html("新闻详情");
		
	});

}

function changeNews(id)
{
	$.get("admin/news/"+id,function(raw_result){
		var result = raw_result.data;
		var labels = result.labels;
		
		var labellist = "";
		for(var i=0;i<labels.length;i++)
		{
			labellist += "<a class=\"ui blue tag label\">"+labels[i].name+"</a>";
		}
		
		var form = "<form class=\"ui form\">"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field disabled\">"
		      +"<label>ID</label>"
			  +"<input type=\"text\" id=\"change_news_id\" value=\""+result.id+"\" placeholder=\"ID\">"
			  +"</div>"
			  +"<div class=\"field disabled\">"
		      +"<label>编号</label>"
			  +"<input type=\"text\" id=\"change_news_sno\" value=\""+result.newsid+"\" placeholder=\"编号\">"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field\">"
		      +"<label>URL</label>"
			  +"<input type=\"text\" id=\"change_news_url\" value=\""+result.url+"\" placeholder=\"URL\">"
			  +"</div>"
			  +"<div class=\"field\">"
		      +"<label>标题</label>"
			  +"<input type=\"text\" id=\"change_news_title\" value=\""+result.title+"\" placeholder=\"标题\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field\">"
			  +"<label>来源</label>"
			  +"<input type=\"text\" id=\"change_news_source\" value=\""+result.resource+"\" placeholder=\"来源\">"
			  +"</div>"
			  +"<div class=\"field \">"
		      +"<label>发布时间</label>"
			  +"<input type=\"text\" id=\"change_news_pubtime\" value=\""+result.pubtime+"\" placeholder=\"YYYY-MM-DD hh:mm:ss\">"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field disabled\">"
		      +"<label>采集时间</label>"
			  +"<input type=\"text\" id=\"change_news_collecttime\" value=\""+result.createtime+"\" placeholder=\"采集时间\">"
			  +"</div>"
			  +"<div class=\"field disabled\">"
		      +"<label>最后修改时间</label>"
			  +"<input type=\"text\" id=\"change_news_lastmodifytime\" value=\""+result.lastmodifytime+"\" placeholder=\"最后修改时间\">"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field disabled\">"
		      +"<label>标签列表</label>"
			  +labellist
			  +"</div>"
			  +"<div class=\"field\">"
		 	  +"<label>正文</label>"
		 	  +"<textarea class=\"ui text container\" id=\"change_news_text\">"+(result.text)+"</textarea>"
		 	  +"</div>"
		 	  +"</form>";
		$("#content_modal_description").html(form);
		var actions = "<div class=\"ui primary button\" onclick=\"submitModifyNews("+id+");\">修改</div>";
		actions += "<div class=\"ui red button\" onclick=\"modalfade();\">取消</div>";
		$("#content_modal_actions").html(actions);
		$("#content_modal").modal("show");
		$("#content_modal_header").html("新闻详情");
		
	});

}

function submitModifyNews(id)
{
	var url = $("#change_news_url").val();
	var resource = $("#change_news_source").val();
	var title = $("#change_news_title").val();
	var pubtime = $("#change_news_pubtime").val();
	var text = $("#change_news_text").val();
	
	var data={};
	data["url"]=url;
	data["resource"]=resource;
	data["title"]=title;
	data["pubtime"]=pubtime;
	data["text"]=text;
	console.log(data);
	$.putJSON("admin/news/"+id, data, function(raw_result){
		alert("修改成功！");
		modalfade();
	});

}


function deleteNews(id,index)
{
	$.deleteJSON("admin/news/"+id,function(raw_result){
		var result = raw_result;
		if(result.code==200)
		{
			alert("删除成功!");
			
		}
		else
		{
			alert("删除失败!");
		}
		getNewsListContent(index);
		
	});
	
	
}


function selectAllNews()
{
	//$(".newscheck").attr("checked",true);
	var checklist = $(".newscheck").get();
	for(var i=0;i<checklist.length;i++)
	{
		checklist[i].checked=true;
	}
}

function reverseSelectAllNews()
{
	var checklist = $(".newscheck").get();
	for(var i=0;i<checklist.length;i++)
	{
		if(checklist[i].checked)
		{
			checklist[i].checked=false;	
		}
		else
		{
			checklist[i].checked=true;
		}
	}
}

function getNewsLabels(index,id)
{
	var checklist = $(".newscheck").get();
	choosenews=[];
	
	console.log(choosenews);
	var color_size = 4;
	var colors=["blue","orange","teal","red"];
	$.get("admin/labels/list",function(raw_result){
		console.log(raw_result);
		var result = raw_result.data;
		var level =0;
		
		
		var form = "<form class=\"ui form\">";
		form += "<p><h4>选择标签</h4></p>";
		chooselabels=[];
		form += "<div id=\"chooseNewsLables\"></div>";
		
		form += "<h4 class=\" divider header\"><i class=\"bar chart icon\" /> 标签列表 </h4>";
		form +="<p></p><p>";
		form+=" <div id=\"label_tree\"></div>";
		
		form +="</p>";
		form += "</form>";
		$("#content_modal_description").html(form);
		
		
		
		
		initLabelTree();
		
		if(typeof(id)=="undefined")
		{
			for(var i=0;i<checklist.length;i++)
			{
				if(checklist[i].checked)
				{
					choosenews.push(checklist[i].id.substr(5));	
				}
			}
			var actions = "<div class=\"ui primary button close\" onclick=\"submitNewsLabels("+index+");\">提交</div>";
			actions += "<div class=\"ui button\" onclick=\"modalfade();\">取消</div>";
			$("#content_modal_actions").html(actions);
			$("#content_modal_header").html("添加标签");
			$("#content_modal").modal("show");
		}
		else
		{
			choosenews.push(id);
			$.get("admin/news/"+id,function(raw_result){
				var result = raw_result.data;
				var labels = result.labels;
				
				var labellist = "";
				
				$("#chooseNewsLables").html(labellist);
				var actions = "<div class=\"ui primary button close\" onclick=\"submitNewsLabels("+index+","+id+");\">提交</div>";
				actions += "<div class=\"ui button\" onclick=\"modalfade();\">取消</div>";
				$("#content_modal_actions").html(actions);
				$("#content_modal_header").html("添加标签");
				for(var i=0;i<labels.length;i++)
				{
					chooseNewsLabels(labels[i].id,labels[i].name);
				}
				
				
				
				$("#content_modal").modal("show");
			});
			
		}
		
	});
}

function initLabelTree()
{
	$('#label_tree').jstree({
		'core':{
			'data':{
				"url":"admin/system/statcategory/tree",
				"dataType":"json"
			}
		}
	}); 
	
		$('#label_tree').on('changed.jstree',function(e,data){
			var i, j;
			//chooselabels = [];
		    for(i = 0, j = data.selected.length; i < j; i++) {
		    	//r.push({"id":data.instance.get_node(data.selected[i]).id,"text":data.instance.get_node(data.selected[i]).text});
		    	chooseNewsLabels(data.instance.get_node(data.selected[i]).id,data.instance.get_node(data.selected[i]).text);
		    }
		    //console.log('Selected: ' + r.join(', '));
		    //displayChooseLabels();
		});

}


function chooseNewsLabels(id,text)
{
	for(var i=0;i<chooselabels.length;i++)
	{
		if(chooselabels[i].id==id)
		{
			return;
		}
	}
	var element = {};
	element["id"]=id;
	element["text"]=text;
	chooselabels.push(element);
	displayChooseLabels();
}

function displayChooseLabels()
{
	var display = "<p>";
	for(var i=0;i<chooselabels.length;i++)
	{
		display +="<a class=\"ui blue tag label\" class=\"chooseLabels\" onclick=\"removeChooseLabels("+chooselabels[i].id+");\" value=\""+chooselabels[i].id+"\">"+chooselabels[i].text+"</a>";
	}
	display += "</p>";
	$("#chooseNewsLables").html(display);
}

function removeChooseLabels(id)
{
	var tempchoose = [];
	for(var i=0;i<chooselabels.length;i++)
	{
		if(chooselabels[i].id!=id)
		{
			tempchoose.push(chooselabels[i]);
		}
	}
	chooselabels = tempchoose;
	displayChooseLabels();
}

function submitNewsLabels(index,id)
{
	var labels = [];
	for(var i=0;i<chooselabels.length;i++)
	{
		labels.push(chooselabels[i].id);
	}
	var data = {};
	data["news"]=choosenews;
	data["labels"]=labels;
	console.log(data);
	chooselabels
	
	if(typeof(id)=="undefined")
	{
		$.postJSON("admin/news/labels",data,function(result){
			console.log(result);
			if(typeof(result["createtime"])!=undefined)
			{
				alert("添加标签成功");
				$("#content_modal").modal("hide");
				getNewsListContent(index);
			}
		});
	}
	else
	{
		$.putJSON("admin/news/labels/"+id, data, function(raw_result){
			alert("修改标签成功");
			$("#content_modal").modal("hide");
			getNewsListContent(index);
		});
	}
}


function getAnalysorContent(index)
{
	$.get("admin/analysor/list/"+index,function(raw_result){
	
		var result = raw_result.data;
		console.log(raw_result);
		console.log(raw_result.count);
		var count = raw_result.count;
		var pre = 0;
		var next = index ;
		if(index>0)pre=index-1;
		if(index<count-1)next=index+1;
		var pagelist = "<a class=\"icon item\" onclick=\"getAnalysorContent("+pre+")\"><i class=\"left chevron icon\"></i></a>";
		for(var c=0;c<count;c++)
		{
			if(index!=c)
			{
				pagelist += "<a class=\"item\" onclick=\"getAnalysorContent("+c+");\">"+eval(c+1)+"</a>";
			}
			else
			{
				pagelist += "<a class=\"active item\" onclick=\"getAnalysorContent("+c+");\">"+eval(c+1)+"</a>";
			}
		}
		pagelist += "<a class=\"icon item\" onclick=\"getAnalysorContent("+next+");\"><i class=\"right chevron icon\"></i></a>";
		console.log(pagelist);
		
		var table = "<thead><tr>";
		table += "<th>名称</th><th>状态</th><th>解析网站个数</th><th>创建时间</th><th>最后修改时间</th><th>最后检查时间</th><th>相关操作</th>";
		table += "</tr></thead>";
		table += "<tbody>";
		console.log("++++++++++");
		console.log(result);
		console.log("++++++++++");
		for(var i=0;i<result.length;i++)
		{
			table += "<tr>";
			var oplist = "<td > <span class=\"ui small basic icon buttons\"><button class=\"ui button\" onclick=\"changeAnalysor("+result[i]["id"]+");\"><i class=\"write icon\" /></button><button class=\"ui button\" onclick=\"infoAnalysor("+result[i]["id"]+");\"><i class=\"info Circle icon\" /></button><button class=\"ui button\" onclick=\"testAnalysor("+result[i]["id"]+",'"+result[i]["name"]+"');\"><i class=\"browser icon\" /></button><button class=\"ui button\" onclick=\"deleteAnalysor("+result[i]["id"]+");\"><i class=\"remove icon\" /></button></span></td>";
			table += "<td>"+result[i]["name"]+"</td><td>"+(result[i]["status"]==1?"正常":"异常")+"</td><td>"+result[i]["jcount"]+"</td><td>"+result[i]["createtime"]+"</td><td>"+(typeof(result[i]["lastmoditytime"])=="undefined"?"-":result[i]["lastmoditytime"])+"</td><td>"+(typeof(result[i]["lastchecktime"])=="undefined"?"-":result[i]["lastchecktime"])+"</td>"+oplist+"";
			table += "</tr>";
		}
		table += "</tbody>";
		table += "<tfoot>";
		table += "<th><span class=\"ui left floated\"><button class=\"ui primary button\" onclick=\"addAnalysor();\">添加解析器</button></span></th>";
		table += "<th colSpan=\"6\"><span class=\"ui right floated pagination menu\">";
		table += pagelist;
		table += "</span></th>";
		table += "</tfoot>";
		$("#content_table").html(table);
	});
}

function getDisplayContent()
{
	$.get("admin/system/statcategory/tree",function(raw_result){
		
		var result = raw_result;
		var content ="<tbody><tr><td><div id=\"label_tree\" style=\"height:600px;width:400px;\"></div></td><td style=\"vertical-align:top;padding:0\"><p><h4>选择标签</h4></p><div id=\"chooseNewsLables\"></div><div id=\"chooseTimeRange\"></div><div id=\"display_area\" style=\"height:400px;width:600px;margn:0;padding:0\"></div></td></tr></tbody>";
		console.log(content);
		$("#content_table").html(content);
		$('#label_tree').jstree({
			"animation" : 0,
		    "check_callback" : true,
		    "themes" : { "stripes" : true },
			'core':{
				'data':result
			},
			"plugins" : [
			             "contextmenu", "dnd", "search",
			             "state", "types", "wholerow"
			           ]
		}); 
		
			$('#label_tree').on('changed.jstree',function(e,data){
				var i, j;
				//chooselabels = [];
			    for(i = 0, j = data.selected.length; i < j; i++) {
			    	//r.push({"id":data.instance.get_node(data.selected[i]).id,"text":data.instance.get_node(data.selected[i]).text});
			    	chooseNewsLabels(data.instance.get_node(data.selected[i]).id,data.instance.get_node(data.selected[i]).text);
			    	//getLabeDetail(data.instance.get_node(data.selected[i]).id);
			    }
			    //console.log('Selected: ' + r.join(', '));
			    //displayChooseLabels();
			});
			
			
			
			var timeserial = "<button class=\"ui button right floated green\" onclick=\"generateGraph();\">生成图表</button><div class=\"blue ui right floated buttons\"><button class=\"ui button time\" onclick=\"setTimeRange('tyear');\"  id=\"tyear\">年</button><button class=\"ui button time\" onclick=\"setTimeRange('tseason');\" id=\"tseason\">季</button><button class=\"ui button time\" onclick=\"setTimeRange('tmonth');\" id=\"tmonth\">月</button><button class=\"ui button time\" onclick=\"setTimeRange('tweek');\" id=\"tweek\">周</button><button class=\"ui button time\" onclick=\"setTimeRange('tday');\" id=\"tday\">日</button></div><button class=\"ui button right floated white\">时间区间</button>";
			//$("#chooseTimeRange").html(timeserial);
			var graphserial = "<div class=\"blue ui left floated buttons\"><button class=\"ui button graph\" onclick=\"setGraph('gline');\"  id=\"gline\">折线图</button><button class=\"ui button graph\" onclick=\"setGraph('gbarline');\" id=\"gbarline\">折线柱状图</button><button class=\"ui button graph\" onclick=\"setGraph('gstack');\" id=\"gstack\">堆积图</button><button class=\"ui button graph\" onclick=\"setGraph('glines');\" id=\"glines\">多折线图</button><button class=\"ui button graph\" onclick=\"setGraph('gcloud');\" id=\"gcloud\">词云</button></div>";
			
			var precontent = "<div class=\"ui container\"><div class=\"ui teal buttons left floated\"><div class=\"ui primary button \" onclick=\"addLabel();\"><i class=\"checkmark icon\"></i>增加</div><div class=\"ui floating dropdown icon button\"><i class=\"dropdown icon\"></i><div class=\"menu\"><div class=\"item\" onclick=\"addLabel(true);\">增加根节点</div></div></div></div><button class=\"ui red button left floated\" onclick=\"deleteNewLabel();\"><i class=\"remove icon\"></i>删除</button><button class=\"ui green button left floated\" onclick=\"modifyLabel();\"><i class=\"plus icon\"></i>修改</button><p></p></div>";
			
			$("#pretable_content").html(timeserial+graphserial);
			
			$('.ui.dropdown')
			  .dropdown()
			;
			$('#label_tree').jstree(true)
			  .select_node('1');
			console.log($('#label_tree').jstree(true));
			getLabeDetail("1");
			
			var myChart = echarts.init(document.getElementById('display_area')); 
            var option = {
                    tooltip: {
                        show: true
                    },
                    legend: {
                        data:['数据量1','数据量2']
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data : ["1月","2月","3月","4月","5月","6月"]
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            "name":"数据量1",
                            "type":"bar",
                            "data":[5, 20, 40, 10, 10, 20]
                        },
                        {
                            "name":"数据量2",
                            "type":"bar",
                            "data":[20, 40, 80, 20, 20, 30]
                        }
                    ]
                };
            
            	myChart.setOption(option); 
			
            	var rangepicker = "<label class=\"ui label\">时间周期</label><select class=\"ui dropdown\" id=\"rpicker\">"+
          	  	"<option value=\"\">时间周期</option>"+
          	  	"<option value=\"tyear\">年</option>"+
          	  	"<option value=\"tseason\">季</option>"+
          	  	"<option value=\"tmonth\">月</option>"+
          	  	"<option value=\"tweek\">周</option>"+
          	  	"<option value=\"tday\">日</option>"+
          	  	"</select>";
            	
            	var graphpicker = "<label class=\"ui label\">图表类型</label><select class=\"ui dropdown\" id=\"gpicker\">"+
          	  	"<option value=\"\">图表类型</option>"+
          	  	"<option value=\"gline\">折线图</option>"+
          	  	"<option value=\"gbar\">柱状图</option>"+
          	  	"<option value=\"gstack\">堆积图</option>"+
          	  	"<option value=\"gcloud\">词云</option>"+
          	  	"</select>";
            	
            	var picker = "<div>"+rangepicker+graphpicker+"</div>";
            	
            	//var start_end ="<div class=\"blue ui buttons\"><button class=\"ui button time\" onclick=\"setTimeRange('tyear');\"  id=\"tyear\">年</button><button class=\"ui button time\" onclick=\"setTimeRange('tseason');\" id=\"tseason\">季</button><button class=\"ui button time\" onclick=\"setTimeRange('tmonth');\" id=\"tmonth\">月</button><button class=\"ui button time\" onclick=\"setTimeRange('tweek');\" id=\"tweek\">周</button><button class=\"ui button time\" onclick=\"setTimeRange('tday');\" id=\"tday\">日</button></div>"
                
            	var generate_button = "<div><button id=\"generate\" class=\"ui button green\" onclick=\"dataGenerate()\">生成</button><button id=\"generate\" class=\"ui button red\" onclick=\"dataClear()\">清除全部</button></div>";
            	
            	var latertable_content =  "<div class=\"ui input\"><label class=\"ui label\">开始时间</label>" +
            			"<input type=\"text\" id=\"starttime\" placeholder=\"开始时间\">" +
            			"<label class=\"ui label\">结束时间</label><input type=\"text\" id=\"endtime\" placeholder=\"结束时间\"></div>" ;
            			
            	var tablelist="<table class=\"ui celled table\" id=\"generate_graph_table\"></table>";
            	var graphgenerator="<div id=\"graph_area\" style=\"height:400px;width:600px;margn:0;padding:0\"></div>";
            	latertable_content += picker;
            	//latertable_content += start_end;
            	
            	latertable_content += generate_button;
        
            	
            	latertable_content += tablelist;
            	latertable_content += graphgenerator;
            	console.log(latertable_content);
            	$("#latertable_content").html(latertable_content);
			
            	
            	$("#starttime").prop("readonly", true).datepicker({
                    changeMonth: true,
                    dateFormat: "yy-mm-dd",
                    onClose: function(selectedDate) {
                        $("#date_end").datepicker("option", "minDate", selectedDate);
                    }
                });
            	
            	$("#endtime").prop("readonly", true).datepicker({
                    changeMonth: true,
                    dateFormat: "yy-mm-dd",
                    onClose: function(selectedDate) {
                        $("#date_end").datepicker("option", "minDate", selectedDate);
                    }
                });
            	
            	
            	
			
	});
}

function timepartition(start,end,range)
{
	if(range=="tyear")
	{
		
	}
	else if(range=="tmonth")
	{
		
	}
	else if(range=="tseason")
	{
		
	}
	else if(range =="tweek")
	{
		
	}
	else if(range =="tday")
	{
		
	}

}




function datacomposer(params)
{
	$.postJSON("admin/display/dataserial",params,function(raw_data){
		
		console.log("?????");
		console.log(timeindexes);
		
		for(var i=0;i<chooselabels.length;i++)
		{
			
			
			console.log(raw_data);
			var text = chooselabels[i].text;
			var id = chooselabels[i].id;
			var serialdata = raw_data.data[""+id+""]
			
			var dataelement = {};
			
			var dataserial = [];
			for(j=0;j<timelabels.length;j++)
			{
				dataserial.push(0);
			}
			
			if(params["rangepicker"]=="tyear")
			{
				for(var j=0;j<serialdata.length;j++)
				{
					var datapair = serialdata[j];
					dataserial[timeindexes[datapair["YEAR"]]]=datapair["count"];
				}
			}
			else if(params["rangepicker"]=="tseason")
			{
				for(var j=0;j<serialdata.length;j++)
				{
					var datapair = serialdata[j];
					dataserial[timeindexes[datapair["YEAR"]+"_"+datapair["SEASON"]]]=datapair["count"];
				}
			}
			else if(params["rangepicker"]=="tmonth")
			{
				
				for(var j=0;j<serialdata.length;j++)
				{
					var datapair = serialdata[j];
					console.log("*****"+datapair["YEAR"]+"_"+datapair["MONTH"]);
					console.log(timeindexes[datapair["YEAR"]+"_"+datapair["MONTH"]]);
					dataserial[timeindexes[datapair["YEAR"]+"_"+datapair["MONTH"]]]=datapair["count"];
				}
			}
			else if(params["rangepicker"]=="tweek")
			{
				for(var j=0;j<serialdata.length;j++)
				{
					var datapair = serialdata[j];
					dataserial[timeindexes[datapair["YEAR"]+"_"+datapair["WEEK"]]]=datapair["count"];
				}
			}
			else if(params["rangepicker"]=="tday")
			{
				for(var j=0;j<serialdata.length;j++)
				{
					var datapair = serialdata[j];
					dataserial[timeindexes[datapair["YEAR"]+"_"+datapair["MONTH"]+"_"+datapair["DAY"]]]=datapair["count"];
				}
			}
			dataelement["name"]=text;
			dataelement["id"]=id;
			dataelement["data"]=dataserial;
			dataelement["range"]=params["rangepicker"];
			dataelement["graph"]=params["graphpicker"];
			dataelement["start"]=params["starttime"];
			dataelement["end"]=params["endtime"];
			console.log("******");
			console.log(params);
			console.log(dataelement);
			var list_id = "datalist_"+id;
			datalist.push(dataelement);
		}
		
		$("#rpicker").removeClass("disabled");
		$("#rpicker").addClass("disabled");
		
/*		$("#gpicker").removeClass("disabled");
		$("#gpicker").addClass("disabled");*/
		
		$("#starttime").datepicker("disable");
		$("#endtime").datepicker("disable");
		//$("#starttime").addClass("disabled");
		
		
		generateDataTable();
		generateNewGraph();
	});


}

function removeGenerateDataTable(id)
{
	var index = -1;
	var templist=[];
	for(var i=0;i<datalist.length;i++)
	{
		if(datalist[i].id!=id)
		templist.push(datalist[i]);
	}
	datalist={};
	datalist=templist;
	generateDataTable();
	generateNewGraph();
}


function generateNewGraph()
{
	
	
	var gdata =[];
	var gseries =[];
	for(var i=0;i<datalist.length;i++)
	{
		var n = datalist[i].name;
		
		var t = "";
		if(datalist[i].graph=="gline")
		{
			t="line";
		}
		else if(datalist[i].graph=="gbar")
		{
			t="bar";
		}
		else if(datalist[i].graph=="gstack")
		{
			t="stack";
		}
		else if(datalist[i].graph=="gcloud")
		{
			t="wordCloud";
		}
		
		
		var d = datalist[i].data;
		gdata.push(n);
		var e = {};
		e["name"]=n;
		e["type"]=t;
		e["data"]=d;
		console.log("^^^^^^");
		console.log(d);
		gseries.push(e);
	}
	
	console.log("&&&&&&&&");
	console.log(timelabels);
	console.log(gdata);
	console.log(gseries);
	
	var myChart = echarts.init(document.getElementById('graph_area')); 
	option = {
		    tooltip : {
		        trigger: 'axis'
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    legend: {
		        data:gdata
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : timelabels
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : '指数',
		            axisLabel : {
		                formatter: '{value}'
		            }
		        },
		        {
		            type : 'value',
		            name : '经济增长',
		            axisLabel : {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series : gseries
		};
    
    	myChart.setOption(option); 

}



function generateDataTable()
{
	var graph_table = "<thead><tr><th>名称</th><th>起始时间</th><th>终止时间</th><th>周期</th><th>类型</th><th>操作</th></tr></thead>";
	graph_table+="<tbody>";
	
	var graphtype={};
	graphtype["gline"]="折线图";
	graphtype["gbar"]="柱状图";
	graphtype["gstack"]="堆积图";
	graphtype["gcloud"]="词云";
	
	var timerange={};
	timerange["tyear"]="年";
	timerange["tseason"]="季";
	timerange["tmonth"]="月";
	timerange["tweek"]="周";
	timerange["tday"]="日";
	
	
	for(var i=0;i<datalist.length;i++)
	{
		console.log(datalist[i]);
		
		graph_table+="<tr><td>"+datalist[i].name+"</td><td>"+datalist[i].start+"</td><td>"+datalist[i].end+"</td><td>"+timerange[datalist[i].range]+"</td><td>"+graphtype[datalist[i].graph]+"</td><td><button class=\"ui red button\" onclick=\"removeGenerateDataTable("+datalist[i].id+");\"><i class=\"remove icon\"></i>删除</button></td></tr>";
	}
	graph_table+="</tbody>";
	$("#generate_graph_table").html(graph_table);

}


function dataGenerate()
{
	var pstart = $("#starttime").val();
	var pend = $("#endtime").val();
	var rangepicker = $("#rpicker").val();
	var garphpicker = $("#gpicker").val();
	if(pstart==""||pend=="")
	{
		alert("起始日期和终止日期不得为空！");
		return;
	}
	else if(rangepicker=="")
	{
		alert("时间周期不得为空！");
		return;
	}
	else if(garphpicker=="")
	{
		alert("图表类型不得为空！");
		return;
	}
	else if(pstart>pend)
	{
		alert("起始日期大于终止日期！");
		return;
	}
	else
	{
		var params ={};
		
		params["rangepicker"]=rangepicker;
		params["graphpicker"]=garphpicker;
		params["starttime"]=pstart;
		params["endtime"]=pend;
		
		console.log(params);
		
		var picklabels = [];
		
		
		for(var i=0;i<chooselabels.length;i++)
		{
			picklabels.push(chooselabels[i].id);
		}
		
		params["picklabels"]=picklabels;
		
		if(timelabels.length==0)
		{
			$.postJSON("admin/display/timeserial",params,function(time_data){
				timelabels=time_data["labels"];
				timeindexes = time_data["indexes"];
				console.log(time_data);
				datacomposer(params);
			});
		}
		else
		{
			datacomposer(params);
		}
		
		

		
		

	}
	
}

function dataClear()
{
	$("#rpicker").removeClass("disabled");
	$("#starttime").datepicker("enable");
	$("#endtime").datepicker("enable");
	datalist=[];
	timelabels=[];
	timeindexes={};
	generateDataTable();
	generateNewGraph();
}

function createRandomItemStyle() {
    return {
        normal: {
            color: 'rgb(' + [
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160)
            ].join(',') + ')'
        }
    };
}

function addGraphSerial()
{
	var parameter = {};
	var prange = "";
	var ptype = "";
	var pstart = $("#starttime").val();
	var pend = $("#endtime").val();
	
	console.log(pstart);
	console.log(pend);
	
	$.postJSON("",parameter,function(raw_data){
		
		var elementlist = {};
		
		var data = raw_data.data;
		
	
		
		
		
	});

}


function generateAutoGraph()
{ 

}



function generateGraph()
{
	
	var activebutton = $(".button.time.active").get(0);
	var graphactivebutton = $(".button.graph.active").get(0);

	if(typeof(activebutton)!="undefined")
	{
		if(chooselabels.length==0)
		{
			alert("请选择标签！");
		}
		else
		{
			console.log(chooselabels);
			var chooseid = new Array();
			for(var i=0;i<chooselabels.length;i++)
			{
				chooseid.push(chooselabels[i].id);	
				console.log(i);
			}
			console.log("choose:"+chooseid);
			var data={};
			data["ids"]=chooseid;
			data["time"]=activebutton.id;
			console.log("data:"+data);
			$.postJSON("admin/display/getdata",data,function(raw_data){
				var data = raw_data.data;
				var code = raw_data.code;
				var msg = raw_data.msg;
				if(code==200)
				{
					console.log(data);
				}
				else if(code == 400)
				{
					console.log(raw_data);
				}
				else if(code == 500)
				{
					console.log(raw_data);
				}
				else
				{
					
				}
				
			});
			
			
		}
		console.log("gab:"+graphactivebutton.id);
		
		if(graphactivebutton.id=="gline")
		{
			var myChart = echarts.init(document.getElementById('display_area')); 
			option = {
				    title : {
				        text: '投资和消费',
				        subtext: '模拟数据'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['投资','消费']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:'投资',
				            type:'bar',
				            data:[2, 4, 7, 23, 25, 76, 135, 162, 32, 20, 6, 3],
				            markPoint : {
				                data : [
				                    {type : 'max', name: '最大值'},
				                    {type : 'min', name: '最小值'}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name: '平均值'}
				                ]
				            }
				        },
				        {
				            name:'消费',
				            type:'bar',
				            data:[2, 5, 9, 26, 28, 70, 175, 182, 48, 18, 6, 2],
				            markPoint : {
				                data : [
				                    {name : '年最高', value : 182, xAxis: 7, yAxis: 183, symbolSize:18},
				                    {name : '年最低', value : 2, xAxis: 11, yAxis: 3}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name : '平均值'}
				                ]
				            }
				        }
				    ]
				};
            
            	myChart.setOption(option); 
		}
		else if(graphactivebutton.id=="gbarline")
		{
			var myChart = echarts.init(document.getElementById('display_area')); 
			option = {
				    tooltip : {
				        trigger: 'axis'
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    legend: {
				        data:['经济增长','GDP','消费']
				    },
				    xAxis : [
				        {
				            type : 'category',
				            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name : '指数',
				            axisLabel : {
				                formatter: '{value}'
				            }
				        },
				        {
				            type : 'value',
				            name : '经济增长',
				            axisLabel : {
				                formatter: '{value}'
				            }
				        }
				    ],
				    series : [

				        {
				            name:'GDP',
				            type:'bar',
				            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
				        },
				        {
				            name:'消费',
				            type:'bar',
				            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
				        },
				        {
				            name:'经济增长',
				            type:'line',
				            yAxisIndex: 1,
				            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
				        }
				    ]
				};
            
            	myChart.setOption(option); 
		}
		
		else if(graphactivebutton.id=="gstack")
		{
			var myChart = echarts.init(document.getElementById('display_area')); 
			option = {
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['GDP','消费','投资']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : ['6月','7月','8月','9月','10月','11月','12月']
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:'GDP',
				            type:'bar',
				            stack: '总量',
				            itemStyle: {normal: {areaStyle: {type: 'default'}}},
				            data:[120, 132, 101, 134, 90, 230, 210]
				        },
				        {
				            name:'消费',
				            type:'bar',
				            stack: '总量',
				            itemStyle: {normal: {areaStyle: {type: 'default'}}},
				            data:[220, 182, 191, 234, 290, 330, 310]
				        },
				       
				        {
				            name:'投资',
				            type:'line',
				            stack: '总量2',
				            itemStyle: {normal: {areaStyle: {type: 'default'}}},
				            data:[320, 332, 301, 334, 390, 330, 320]
				        }
				        ,
					       
				        {
				            name:'投资2',
				            type:'line',
				            stack: '总量2',
				            itemStyle: {normal: {areaStyle: {type: 'default'}}},
				            data:[100, 150, 110, 160, 120, 130, 120]
				        }
				    ]
				};
            
            	myChart.setOption(option); 
		}
		
		else if(graphactivebutton.id=="glines")
		{
			var myChart = echarts.init(document.getElementById('display_area')); 
			option = {
				    title : {
				        text: '经济相关指标',
				        subtext: '虚拟数据'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['经济增长','通货膨胀']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : ['6月','7月','8月','9月','10月','11月','12月']
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value}'
				            }
				        }
				    ],
				    series : [
				        {
				            name:'经济增长',
				            type:'line',
				            data:[11, 11, 15, 13, 12, 13, 10],
				            markPoint : {
				                data : [
				                    {type : 'max', name: '最大值'},
				                    {type : 'min', name: '最小值'}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name: '平均值'}
				                ]
				            }
				        },
				        {
				            name:'通货膨胀',
				            type:'line',
				            data:[1, 12, 30, 50, 10, 2, 0],
				            markPoint : {
				                data : [
				                    {name : '最低值', value : 4, xAxis: 1, yAxis: 2}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name : '平均值'}
				                ]
				            }
				        }
				    ]
				};
			myChart.setOption(option); 
		}
		
		
		else if(graphactivebutton.id=="gcloud")
		{
			var myChart = echarts.init(document.getElementById('display_area')); 
			option = {
				    title: {
				        text: '金融大数据平台统计',
				        link: 'www.cnic.cn'
				    },
				    tooltip: {
				        show: true
				    },
				    series: [{
				        name: '金融大数据平台统计',
				        type: 'wordCloud',
				        size: ['80%', '80%'],
				        textRotation : [0, 45, 90, -45],
				        textPadding: 0,
				        autoSize: {
				            enable: true,
				            minSize: 14
				        },
				        data: [
				            {
				                name: "经济增长",
				                value: 10000,
				                itemStyle: {
				                    normal: {
				                        color: 'black'
				                    }
				                }
				            },
				            {
				                name: "GDP",
				                value: 6181,
				                itemStyle: createRandomItemStyle()
				            },
				            {
				                name: "消费",
				                value: 4386,
				                itemStyle: createRandomItemStyle()
				            },
				            {
				                name: "投资",
				                value: 4055,
				                itemStyle: createRandomItemStyle()
				            },
				            {
				                name: "通货膨胀",
				                value: 2467,
				                itemStyle: createRandomItemStyle()
				            },
				            {
				                name: "物价",
				                value: 2244,
				                itemStyle: createRandomItemStyle()
				            },
				            {
				                name: "量化宽松",
				                value: 1898,
				                itemStyle: createRandomItemStyle()
				            },
				            {
				                name: "CPI",
				                value: 1484,
				                itemStyle: createRandomItemStyle()
				            }
				        ]
				    }]
				};
			myChart.setOption(option); 
		}
	}
	else
	{
		alert("请选择区间！");
	}
}


function setTimeRange(type)
{
	$(".time").removeClass("active");
	$("#"+type).addClass("active");
	console.log(type);
}

function setGraph(type)
{
	$(".graph").removeClass("active");
	$("#"+type).addClass("active");
	console.log(type);
}


function getSystemContent()
{
	$.get("admin/system/statcategory/tree",function(raw_result){
		
		var result = raw_result;
		var content ="<tbody><tr><td><div id=\"label_tree\" style=\"height:600px;width:400px;\"></div></td><td><div id=\"label_detail\"></div></td></tr></tbody>";
		$("#content_table").html(content);
		$('#label_tree').jstree({
			"animation" : 0,
		    "check_callback" : true,
		    "themes" : { "stripes" : true },
			'core':{
				'data':result
			},
			"plugins" : [
			             "contextmenu", "dnd", "search",
			             "state", "types", "wholerow"
			           ]
		}); 
		
			$('#label_tree').on('changed.jstree',function(e,data){
				var i, j;
				//chooselabels = [];
			    for(i = 0, j = data.selected.length; i < j; i++) {
			    	//r.push({"id":data.instance.get_node(data.selected[i]).id,"text":data.instance.get_node(data.selected[i]).text});
			    	getLabeDetail(data.instance.get_node(data.selected[i]).id);
			    }
			    //console.log('Selected: ' + r.join(', '));
			    //displayChooseLabels();
			});
			
			var precontent = "<div class=\"ui container\"><div class=\"ui teal buttons left floated\"><div class=\"ui primary button \" onclick=\"addLabel();\"><i class=\"checkmark icon\"></i>增加</div><div class=\"ui floating dropdown icon button\"><i class=\"dropdown icon\"></i><div class=\"menu\"><div class=\"item\" onclick=\"addLabel(true);\">增加根节点</div></div></div></div><button class=\"ui red button left floated\" onclick=\"deleteNewLabel();\"><i class=\"remove icon\"></i>删除</button><button class=\"ui green button left floated\" onclick=\"modifyLabel();\"><i class=\"plus icon\"></i>修改</button><p></p></div>";
			
			$("#pretable_content").html(precontent);
			
			$('.ui.dropdown')
			  .dropdown()
			;
			$('#label_tree').jstree(true)
			  .select_node('1');
			console.log($('#label_tree').jstree(true));
			getLabeDetail("1");
	});
}

function addLabel(isroot)
{
	var form = "<form class=\"ui form\">"
		  +"<div class=\"field\">"
	      +"<label>名称</label>"
		  +"<input type=\"text\" id=\"add_label_name\" name=\"nameparser\" value=\"\" placeholder=\"名称\">"
		  +"</div>"
	 	  +"<div class=\"field\">"
	 	  +"<label>描述</label>"
	 	  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"标签描述\" id=\"add_label_description\"></textarea>"
	 	  +"</div>"
	 	  +"</form>";
	
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\" onclick=\"submitNewLabel("+isroot+");\">提交</div>";
	actions += "<div class=\"ui button\" onclick=\"modalfade();\">取消</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
	$("#content_modal_header").html("添加标签");
	/*var ref = $('#label_tree').jstree(true), sel = ref.get_selected();
	if (!sel.length) {
		return false;
	}
	sel = sel[0];
	sel = ref.create_node(sel);
	if (sel) {
		ref.edit(sel);
	}*/
}


function modifyLabel()
{
	var ref = $('#label_tree').jstree(true), sel = ref.get_selected();
	if (!sel.length) {
		return false;
	}
	sel = sel[0];
	
	$.get("admin/system/statcategory/"+sel,function(raw_result){
		var result = raw_result.data;
		var code = raw_result.code;
		if(code==200)
		{
			var form = "<form class=\"ui form\">"
				  +"<div class=\"field\">"
			      +"<label>名称</label>"
				  +"<input type=\"text\" id=\"update_label_name\" name=\"nameparser\" value=\""+result.name+"\" placeholder=\"名称\">"
				  +"</div>"
			 	  +"<div class=\"field\">"
			 	  +"<label>描述</label>"
			 	  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"标签描述\" id=\"update_label_description\">"+result.name+"</textarea>"
			 	  +"</div>"
			 	  +"</form>";
			
			$("#content_modal_description").html(form);
			var actions = "<div class=\"ui primary button close\" onclick=\"updateLabel("+sel+");\">提交</div>";
			actions += "<div class=\"ui button\" onclick=\"modalfade();\">取消</div>";
			$("#content_modal_actions").html(actions);
			$("#content_modal").modal("show");
			$("#content_modal_header").html("修改标签");
		
		}
	});
	
	
	/*var ref = $('#label_tree').jstree(true), sel = ref.get_selected();
	if (!sel.length) {
		return false;
	}
	sel = sel[0];
	sel = ref.create_node(sel);
	if (sel) {
		ref.edit(sel);
	}*/
}

function updateLabel(sel)
{
	
	var params = {};
	params["parent"]=sel;
	params["name"]=$("#update_label_name").val();
	params["description"]=$("#update_label_description").val();
	
	console.log(params);
	$.putJSON("admin/system/statcategory/"+sel,params,function(raw_data){
		alert("标签修改成功！");
		modalfade();
		getSystemContent();
	});
}



function submitNewLabel(isroot)
{
	var ref = $('#label_tree').jstree(true), sel = ref.get_selected();
	if(typeof(isroot)=="undefined")
	{
	
		if (!sel.length) {
			return false;
		}
		sel = sel[0];
	}
	else
	{
		sel=-1;
	}
	var params = {};
	params["parent"]=sel;
	params["name"]=$("#add_label_name").val();
	params["description"]=$("#add_label_description").val();
	
	console.log(params);
	$.postJSON("admin/system/statcategory",params,function(raw_data){
		alert("标签添加成功！");
		modalfade();
		getSystemContent();
	});
}

function deleteNewLabel()
{
	var ref = $('#label_tree').jstree(true), sel = ref.get_selected();
	if (!sel.length) {
		return false;
	}
	sel = sel[0];
	
	$.deleteJSON("admin/system/statcategory/"+sel,function(raw_data){
		alert("标签删除成功");
		getSystemContent();
	});

}



function getLabeDetail(id)
{
	console.log($('#label_tree').jstree(true).get_selected());
	$.get("admin/system/statcategory/"+id,function(raw_result){
		var result = raw_result.data;
		var code = raw_result.code;
		if(code==200)
		{
			var form = "<form class=\"ui form\">"
			      +"<div class=\"field disabled\">"
			      +"<label>ID</label>"
			      +"<input type=\"text\" id=\"label_id\" name=\"nameparser\" value=\""+result.id+"\" placeholder=\"ID\">"
			  	  +"</div>"
			  	  +"<div class=\"field disabled\">"
			      +"<label>编码</label>"
				  +"<input type=\"text\" id=\"label_code\" name=\"nameparser\" value=\""+result.code+"\" placeholder=\"编码\">"
				  +"</div>"
				  +"<div class=\"field disabled\">"
			      +"<label>名称</label>"
				  +"<input type=\"text\" id=\"label_name\" name=\"nameparser\" value=\""+result.name+"\" placeholder=\"名称\">"
				  +"</div>"
				  +"<div class=\"field disabled\">"
			      +"<label>创建时间</label>"
				  +"<input type=\"text\" id=\"label_name\" name=\"nameparser\" value=\""+result.createtime+"\" placeholder=\"创建时间\">"
				  +"</div>"
				  +"<div class=\"field disabled\">"
				  +"<label>最后修改时间</label>"
				  +"<input type=\"text\" id=\"label_name\" name=\"nameparser\" value=\""+(typeof(result.lastmodifytime)=="undefined"?"":result.lastmodifytime)+"\" placeholder=\"最后修改时间\">"
				  +"</div>"
			 	  +"<div class=\"field disabled\">"
			 	  +"<label>描述</label>"
			 	  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"任务描述\" id=\"job_description\">"+result.description+"</textarea>"
			 	  +"</div>";
			 	  
			 	  $("#label_detail").html(form);
		
		}
	});
}


function modalfade()
{
	$("#content_modal").modal("hide");
}

function startJob(jobid)
{
	var params = {};
	params["operation"]="start";
	$.putJSON("admin/job/jstat/"+jobid,params,function(result){
		if(result.code==200)
		{
			getJobContent(0);
		}
		alert(result.message);
	});
}

function stopJob(jobid)
{
	var params = {};
	params["operation"]="stop";
	$.putJSON("admin/job/jstat/"+jobid,params,function(result){
		if(result.code==200)
		{
			getJobContent(0);
		}
		alert(result.message);
	});
}

function restartJob(jobid)
{
	var params = {};
	params["operation"]="restart";
	$.putJSON("admin/job/jstat/"+jobid,params,function(result){
		if(result.code==200)
		{
			getJobContent(0);
		}
		alert(result.message);
	});
}

function startSubJob(subjobid,jobid)
{
	var params = {};
	params["operation"]="start";
	$.putJSON("admin/job/sjstat/"+subjobid,params,function(result){
		if(result.code==200)
		{
			modalfade();
			changeJob(jobid);
		}
		else
		{
			alert(result.message);
		}
	});
}

function stopSubJob(subjobid,jobid)
{
	var params = {};
	params["operation"]="stop";
	$.putJSON("admin/job/sjstat/"+subjobid,params,function(result){
		if(result.code==200)
		{
			modalfade();
			changeJob(jobid);
		}
		else
		{
			alert(result.message);
		}
	});
}

function restartSubJob(subjobid,jobid)
{
	var params = {};
	params["operation"]="restart";
	$.putJSON("admin/job/sjstat/"+subjobid,params,function(result){
		if(result.code==200)
		{
			modalfade();
			changeJob(jobid);
		}
		else
		{
			alert(result.message);
		}
	});
}

function addJob()
{
	$("#content_modal_header").html("添加任务");
	//$("#content_modal_content").html("");
	
	$.get("admin/job/metadata",function(result){
		result = eval(result);
		var categories = result["category"];
		var categoryhtml = "";
		for(var i = 0;i<categories.length;i++)
		{
			categoryhtml += "<option value=\""+categories[i]["innerid"]+"\">"+categories[i]["name"]+"</option>";
		}
		var form = "<form class=\"ui form\">"
			  +"<div class=\"field\">"
			  +"<label>任务名称</label>"
			  +"<input type=\"text\" name=\"name\" placeholder=\"任务名称\" id=\"job_name\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>执行计划</label>"
			  +"<input type=\"text\" name=\"plan\" placeholder=\"intr|cron|time\" id=\"job_plan\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>任务分类</label>"
			  +"<select type=\"text\" name=\"category\" placeholder=\"任务分类\" id=\"job_category\">"
			  +categoryhtml
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>任务描述</label>"
			  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"任务描述\" id=\"job_description\"></textarea>"
			  +"</div>"
			  +"</form>";
		$("#content_modal_description").html(form);
		var actions = "<div class=\"ui primary button close\" onclick=\"submitjob();\">提交</div>";
		actions += "<div class=\"ui button\" onclick=\"modalfade();\">取消</div>";
		$("#content_modal_actions").html(actions);
		$("#content_modal").modal("show");
	});
	
	//$("#content_modal_actions").html("");
	
}


function submitjob()
{
	var job={};
	job["name"] = $("#job_name").val();
	job["plan"] = $("#job_plan").val();
	job["category"] = $("#job_category").val();
	job["description"] = $("#job_description").val();
	
	$.postJSON("/admin/job",job,function(result){
		console.log(result);
		if(typeof(result["createtime"])!=undefined)
		{
			alert("添加任务成功");
			$("#content_modal").modal("hide");
			getJobContent(0);
		}
	});
}


function changeJob(jobid)
{
/*	$("#content_modal_header").html("修改任务");
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
	$("#content_modal").modal("show");*/
	
	
	$("#content_modal_header").html("修改任务");
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	

	$.get("admin/job/f/"+jobid,function(result){
		
		var subjobs = result.subjobs;
		var metadata = result.metadata;
		var parsers = result.parsers;
		var job = result.job;
		
		console.log(metadata);
		var subjoblist = "";
		
		for(var i=0;i<subjobs.length;i++)
		{
			var statuslist = "";
			if(subjobs[i]["sstatus"]=="R")
			{
				statuslist = "<td><span class=\"ui small basic icon buttons\"><div class=\"ui active button\" onclick=\"startSubJob("+subjobs[i]["id"]+","+jobid+");\"><i class=\"play icon\" /></div><div class=\"ui button\" onclick=\"stopSubJob("+subjobs[i]["id"]+","+jobid+");\"><i class=\"pause icon\" /></div><div class=\"ui button\" onclick=\"restartSubJob("+subjobs[i]["id"]+","+jobid+");\"><i class=\"undo icon\" /></div></span></td>";
			}
			else
			{
				statuslist = "<td><span class=\"ui small basic icon buttons\"><div class=\"ui  button\" onclick=\"startSubJob("+subjobs[i]["id"]+","+jobid+");\"><i class=\"play icon\" /></div><div class=\"ui active button\" onclick=\"stopSubJob("+subjobs[i]["id"]+","+jobid+");\"><i class=\"pause icon\" /></div><div class=\"ui button\" onclick=\"restartSubJob("+subjobs[i]["id"]+","+jobid+");\"><i class=\"undo icon\" /></div></span></td>";
			}
			
			var subjob = subjobs[i];
			subjoblist += "<tr>";
			subjoblist += "<td>"+subjob.name+"</td><td>"+subjob.url+"</td><td>"+parsers[subjob.parserid+""].name+"</td><td>"+(subjob.category==0?"暂无分类":subjob.category)+"</td><td>"+subjob.createtime+"</td>"+statuslist+"";
			subjoblist += "<td> <span class=\"ui small basic icon buttons\"><div class=\"ui button\" onclick=\"changeSubJob("+subjob.id+")\"><i class=\"write icon\" /></div><div class=\"ui button\" onclick=\"infoSubJob("+subjob.id+")\"><i class=\"info Circle icon\" /></div><div class=\"ui button\" onclick=\"deleteSubJob("+subjob.id+","+jobid+")\"><i class=\"remove icon\" /></div></span></td>";
			subjoblist += "</tr>";
		}
		
		var categorylist = "";
		console.log(metadata["category"]);
		for(var i=0;i<metadata["category"].length;i++)
		{
			var category = metadata["category"][i];
			console.log("******************");
			console.log(category);
			console.log("******************");
			if(category["innerid"]==job.category)
			{
				categorylist += "<option value=\""+category["innerid"]+"\" selected=\"selected\">"+category.name+"</option>";
			}
			else
			{
				categorylist += "<option value=\""+category["innerid"]+"\">"+category.name+"</option>";	
			}
		}
		
		var form = "<form class=\"ui form\">"
			  +"<div class=\"field\">"
			  +"<label>任务名称</label>"
			  +"<input type=\"text\" id =\"job_name\" name=\"name\" value=\""+job.name+"\" placeholder=\"\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>执行计划</label>"
			  +"<input type=\"text\" id =\"job_plan\" name=\"plan\" value=\""+job.plan+"\" placeholder=\"intr|cron|time\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>任务分类</label>"
			  +"<select type=\"text\" id =\"job_category\" name=\"category\" placeholder=\"执行计划\">"
			  + categorylist
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>任务描述</label>"
			  +"<textarea type=\"text\" id =\"job_description\" rows=\"3\"  placeholder=\"任务描述\">"+job.description+"</textarea>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>相关子任务</label>"
			  +"<table class=\"ui celled table\" id=\"job_content_table\">"
			  +"<thead>"
			  +"<tr>"
			  +"<th>子任务名称</th><th>网站地址</th><th>解析器</th><th>类别</th><th>创建时间</th><th>状态</th><th>操作</th>"
			  +"</tr>"
			  +"</thead>"
			  +"<tbody>"
			  +subjoblist
			  +"</tbody>"
			  +"</table>"
			  +"<div class=\"ui primary button\" onclick=\"addSubJob("+jobid+");\">添加子任务</div>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\" onclick=\"modifyJob("+jobid+")\">修改</div>";
	actions += "<div class=\"ui button\" onclick=\"modalfade();\">取消</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
		
	});
}

function modifyJob(jobid)
{
	var job = {};
	job["name"] = $("#job_name").val();
	job["plan"] = $("#job_plan").val();
	job["category"] = $("#job_category").val();
	job["description"] = $("#job_description").val();
	$.putJSON("/admin/job/"+jobid, job, function(result){
		$("#content_modal").modal("hide");
		getJobContent(0);
	});
}


function addSubJob(jobid)
{
	$("#second_content_modal_header").html("添加子任务");
	//$("#content_modal_content").html("");
	
	$.get("admin/job/submetadata",function(result){
		result = eval(result);
		var categories = result["subcategory"];
		var parsers = result["parser"];
		var parserhtml = "";
		for(var i = 0; i<parsers.length;i++)
		{
			parserhtml += "<option value=\""+parsers[i]["id"]+"\">"+parsers[i]["name"]+"</option>";
		}
		var categoryhtml = "";
		for(var i = 0;i<categories.length;i++)
		{
			categoryhtml += "<option value=\""+categories[i]["innerid"]+"\">"+categories[i]["name"]+"</option>";
		}
		var form = "<form class=\"ui form\">"
			  +"<div class=\"field\">"
			  +"<label>子任务名称</label>"
			  +"<input type=\"text\" name=\"name\" placeholder=\"子任务名称\" id=\"subjob_name\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>URL</label>"
			  +"<input type=\"text\" name=\"url\" placeholder=\"url\" id=\"subjob_url\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>解析器</label>"
			  +"<select type=\"text\" name=\"解析器\" placeholder=\"url\" id=\"subjob_parser\">"
			  +parserhtml
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>子任务分类</label>"
			  +"<select type=\"text\" name=\"category\" placeholder=\"子任务分类\" id=\"subjob_category\">"
			  +categoryhtml
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>子任务描述</label>"
			  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"子任务描述\" id=\"subjob_description\"></textarea>"
			  +"</div>"
			  +"</form>";
		$("#second_content_modal_description").html(form);
		var actions = "<div class=\"ui primary button close\" onclick=\"submitsubjob("+jobid+");\">提交</div>";
		actions += "<div class=\"ui button\" onclick=\"modalfades();\">取消</div>";
		$("#second_content_modal_actions").html(actions);
		$("#second_content_modal").modal("show");
	});
	
	//$("#content_modal_actions").html("");
}


function modalfades()
{
	$("#second_content_modal").modal("hide");
}

function submitsubjob(jobid)
{
	var subjob = {};
	subjob["jobid"]=jobid;
	subjob["name"]=$("#subjob_name").val();
	subjob["url"]=$("#subjob_url").val();
	subjob["parserid"]=$("#subjob_parser").val();
	subjob["category"]=$("#subjob_category").val();
	subjob["description"]=$("#subjob_description").val();
	
	console.log(subjob);
	$.postJSON("admin/job/subjob",subjob,function(result){
		console.log(result);
		alert("子任务添加成功！");
		modalfades();
	});
}

function changeSubJob(subjobid)
{
$.get('admin/job/subjob/'+subjobid,function(result){
		
		$("#content_modal_header").html("查看子任务");	

			
			var subjob = result.subjob;
			var metadata = result.metadata;
			
			var categories = metadata["subcategory"];
			var parsers = metadata["parser"];
			var parserhtml = "";
			for(var i = 0; i<parsers.length;i++)
			{
				if(parsers[i]["id"]!=subjob.parserid)
				{
					parserhtml += "<option value=\""+parsers[i]["id"]+"\">"+parsers[i]["name"]+"</option>";
				}
				else
				{
					parserhtml += "<option value=\""+parsers[i]["id"]+"\" selected=\"selected\">"+parsers[i]["name"]+"</option>";	
				}
			}
			var categoryhtml = "";
			for(var i = 0;i<categories.length;i++)
			{
				if(categories[i]["innerid"]!=subjob.category)
				{
					categoryhtml += "<option value=\""+categories[i]["innerid"]+"\">"+categories[i]["name"]+"</option>";
				}
				else
				{
					categoryhtml += "<option value=\""+categories[i]["innerid"]+"\" selected=\"selected\">"+categories[i]["name"]+"</option>";	
				}
			}
			var form = "<form class=\"ui form\">"
				  +"<div class=\"field\">"
				  +"<label>子任务名称</label>"
				  +"<input type=\"text\" name=\"name\" value=\""+subjob.name+"\" placeholder=\"子任务名称\" id=\"subjob_name\">"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>URL</label>"
				  +"<input type=\"text\" name=\"url\" value=\""+subjob.url+"\" placeholder=\"url\" id=\"subjob_url\">"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>解析器</label>"
				  +"<select type=\"text\" name=\"解析器\" placeholder=\"url\" id=\"subjob_parser\">"
				  +parserhtml
				  +"</select>"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>子任务分类</label>"
				  +"<select type=\"text\" name=\"category\" placeholder=\"子任务分类\" id=\"subjob_category\">"
				  +categoryhtml
				  +"</select>"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>子任务描述</label>"
				  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"子任务描述\" id=\"subjob_description\">"+subjob.description+"</textarea>"
				  +"</div>"
				  +"</form>";
			$("#second_content_modal_description").html(form);
			var actions = "<div class=\"ui primary button close\" onclick=\"modifySubJob("+subjobid+");\">提交</div>";
			actions += "<div class=\"ui button\" onclick=\"modalfades();\">取消</div>";
			$("#second_content_modal_actions").html(actions);
			$("#second_content_modal").modal("show");

	});
	
}

function modifySubJob(subjobid)
{
	var subjob = {};
	subjob["name"]=$("#subjob_name").val();
	subjob["url"]=$("#subjob_url").val();
	subjob["parserid"]=$("#subjob_parser").val();
	subjob["category"]=$("#subjob_category").val();
	subjob["description"]=$("#subjob_description").val();
	
	console.log(subjob);
	$.putJSON("admin/job/subjob/"+subjobid,subjob,function(result){
		console.log(result);
		alert("子任务修改成功！");
		modalfades();
	});
	
	

}


function infoSubJob(subjobid)
{
	$.get('admin/job/subjob'+subjobid,function(result){
		
		$("#content_modal_header").html("查看子任务");	

			
			var subjob = result.subjob;
			var metadata = result.metadata;
			
			var categories = metadata["subcategory"];
			var parsers = metadata["parser"];
			var parserhtml = "";
			for(var i = 0; i<parsers.length;i++)
			{
				if(parsers[i]["id"]!=subjob.parserid)
				{
					parserhtml += "<option value=\""+parsers[i]["id"]+"\">"+parsers[i]["name"]+"</option>";
				}
				else
				{
					parserhtml += "<option value=\""+parsers[i]["id"]+"\" selected=\"selected\">"+parsers[i]["name"]+"</option>";	
				}
			}
			var categoryhtml = "";
			for(var i = 0;i<categories.length;i++)
			{
				if(categories[i]["innerid"]!=subjob.category)
				{
					categoryhtml += "<option value=\""+categories[i]["innerid"]+"\">"+categories[i]["name"]+"</option>";
				}
				else
				{
					categoryhtml += "<option value=\""+categories[i]["innerid"]+"\" selected=\"selected\">"+categories[i]["name"]+"</option>";	
				}
			}
			var form = "<form class=\"ui form\">"
				  +"<div class=\"field\">"
				  +"<label>子任务名称</label>"
				  +"<input type=\"text\" name=\"name\" value=\""+subjob.name+"\" placeholder=\"子任务名称\" id=\"subjob_name\">"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>URL</label>"
				  +"<input type=\"text\" name=\"url\" value=\""+subjob.url+"\" placeholder=\"url\" id=\"subjob_url\">"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>解析器</label>"
				  +"<select type=\"text\" name=\"解析器\" placeholder=\"url\" id=\"subjob_parser\">"
				  +parserhtml
				  +"</select>"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>子任务分类</label>"
				  +"<select type=\"text\" name=\"category\" placeholder=\"子任务分类\" id=\"subjob_category\">"
				  +categoryhtml
				  +"</select>"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>子任务描述</label>"
				  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"子任务描述\" id=\"subjob_description\">"+subjob.description+"</textarea>"
				  +"</div>"
				  +"</form>";
			$("#second_content_modal_description").html(form);
			var actions = "<div class=\"ui primary button close\" onclick=\"modalfades();\">提交</div>";
			$("#second_content_modal_actions").html(actions);
			$("#second_content_modal").modal("show");
		
	});
	
	
}

function deleteSubJob(subjobid,jobid)
{
	$.deleteJSON("admin/job/subjob/"+subjobid, function(result){
		alert("子任务删除成功！");
		console.log(result);
		modalfade();
		changeJob(jobid);
	});
}

function infoJob(jobid)
{
	$("#content_modal_header").html("查看任务");	

	$.get("admin/job/f/"+jobid,function(result){
		
		var subjobs = result.subjobs;
		var metadata = result.metadata;
		var parsers = result.parsers;
		var job = result.job;
		
		console.log(metadata);
		var subjoblist = "";
		for(var i=0;i<subjobs.length;i++)
		{
			var subjob = subjobs[i];
			subjoblist += "<tr>";
			subjoblist += "<td>"+subjob.name+"</td><td>"+subjob.url+"</td><td>"+parsers[subjob.parserid+""].name+"</td><td>"+(subjob.category==0?"暂无分类":subjob.category)+"</td><td>"+(subjob.status==1?"正常":"异常")+"</td><td>"+subjob.createtime+"</td>";
			subjoblist += "</tr>";
		}
		
		var categorylist = "";
		console.log(metadata["category"]);
		for(var i=0;i<metadata["category"].length;i++)
		{
			var category = metadata["category"][i];
			console.log("******************");
			console.log(category);
			console.log("******************");
			if(category["innerid"]==job.category)
			{
				categorylist += "<option value=\""+category["innerid"]+"\" selected=\"selected\">"+category.name+"</option>";
			}
			else
			{
				categorylist += "<option value=\""+category["innerid"]+"\">"+category.name+"</option>";	
			}
		}
		
		var form = "<form class=\"ui form\">"
			  +"<div class=\"disabled field\">"
			  +"<label>任务名称</label>"
			  +"<input type=\"text\" name=\"first-name\" value=\""+job.name+"\" placeholder=\"\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>执行计划</label>"
			  +"<input type=\"text\" name=\"last-name\" value=\""+job.plan+"\" placeholder=\"intr|cron|time\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>任务分类</label>"
			  +"<select type=\"text\" name=\"last-name\" placeholder=\"执行计划\">"
			  + categorylist
			  +"</select>"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>任务描述</label>"
			  +"<textarea type=\"text\" rows=\"3\"  placeholder=\"任务描述\">"+job.description+"</textarea>"
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
			  +subjoblist
			  +"</tbody>"
			  +"</table>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\" onclick=\"modalfade()\">确定</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
		
	});
	

}

function deleteJob(jobid)
{
	$.deleteJSON("admin/job/"+jobid, function(result){
		alert("任务删除成功！");
		console.log(result);
		getJobContent(0);
	});
}

function addAnalysor()
{
	$("#content_modal_header").html("添加解析器");
	//$("#content_modal_content").html("");
	
	$.get("admin/analysor/all",function(result){
		//$("#content_modal_actions").html("");
		var options = "<option value=\"-1\"></option>";
		for(var i =0; i<result.length;i++)
		{
			options +="<option value=\""+result[i].id+"\">"+result[i].name+"</option>";
		}
		
		
		var form = "<form class=\"ui form\">"
				  +"<div class=\"two fields\">"
			      +"<div class=\"field\">"
			      +"<label>名称</label>"
			      +"<input type=\"text\" id=\"parser_name\" name=\"nameparser\" placeholder=\"解析器名称\">"
			  	  +"</div>"
			  	  +"<div class=\"field\">"
			      +"<label>复制列表</label>"
			      +"<select id=\"copy_parser_list\" onchange=\"changecopyAnalysor();\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			      +options
			      +"</select>"
			      +"</div>"
			  	  +"</div>"
			  	  
				  +"<div class=\"field\">"
				  +"<label>列表解析规则</label>"
				  +"<input type=\"text\" id=\"parser_list_ruler\" name=\"listparser\" placeholder=\"列表解析规则\">"
				  +"</div>"
				  +"<div class=\"two fields\">"
				  +"<div class=\"field\">"
				  +"<label>列表是否支持CSS</label>"
				  +"<select id=\"parser_list_css\" type=\"text\" name=\"last-name\" placeholder=\"\">"
				  +"<option value=\"0\">否</option>"
				  +"<option value=\"1\">是</option>"
				  +"</select>"
				   /*+"<div class=\"ui fitted toggle checkbox\">"
				  +"<input  type=\"checkbox\">"
				  +"<label></label>"
				  +"</div>"*/
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>列表是否支持JS</label>"
				  /*+"<div class=\"ui fitted toggle checkbox\">"
				  +"<input id=\"parser_list_js\" type=\"checkbox\">"
				  +"<label></label>"
				  +"</div>"*/
				  +"<select id=\"parser_list_js\" type=\"text\" name=\"last-name\" placeholder=\"\">"
				  +"<option value=\"0\">否</option>"
				  +"<option value=\"1\">是</option>"
				  +"</select>"
				  +"</div>"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>下一页解析规则</label>"
				  +"<input id=\"parser_next_ruler\" type=\"text\" name=\"nextparser\" placeholder=\"下一页解析规则\">"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>标题解析规则</label>"
				  +"<input id=\"parser_title_ruler\" type=\"text\" name=\"titleparser\" placeholder=\"标题解析规则\">"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>时间解析规则</label>"
				  +"<input id=\"parser_time_ruler\" type=\"text\" name=\"timeparser\" placeholder=\"时间解析规则\">"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>时间转换规则</label>"
				  +"<input id=\"parser_time_transfer\" type=\"text\" name=\"timetransfer\" placeholder=\"时间转换规则\">"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>来源解析规则</label>"
				  +"<input id=\"parser_source_ruler\" type=\"text\" name=\"sourceparser\" placeholder=\"来源解析规则\">"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>正文解析规则</label>"
				  +"<input id=\"parser_article_ruler\" type=\"text\" name=\"articleparser\" placeholder=\"正文解析规则\">"
				  +"</div>"
				  +"<div class=\"two fields\">"
				  +"<div class=\"field\">"
				  +"<label>正文是否支持CSS</label>"
				  /*+"<div class=\"ui checkbox\">"
				  +"<input id=\"parser_article_css\" type=\"checkbox\">"
				  +"<label></label>"
				  +"</div>"*/
				  +"<select id=\"parser_article_css\" type=\"text\" name=\"last-name\" placeholder=\"\">"
				  +"<option value=\"0\">否</option>"
				  +"<option value=\"1\">是</option>"
				  +"</select>"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>正文是否支持JS</label>"
				  /*+"<div  class=\"ui checkbox\">"
				  +"<input id=\"parser_article_js\" type=\"checkbox\" checked=\"checked\">"
				  +"<label></label>"
				  +"</div>"*/
				  +"<select id=\"parser_article_js\" type=\"text\" name=\"last-name\" placeholder=\"\">"
				  +"<option value=\"0\">否</option>"
				  +"<option value=\"1\">是</option>"
				  +"</select>"
				  +"</div>"
				  +"</div>"
				  +"<div class=\"field\">"
				  +"<label>是否是相对路径</label>"
				  /*+"<div class=\"ui fitted toggle checkbox\">"
				  +"<input id=\"parser_relative\" type=\"checkbox\">"
				  +"<label></label>"
				  +"</div>"*/
				  +"<select id=\"parser_relative\" type=\"text\" name=\"last-name\" placeholder=\"\">"
				  +"<option value=\"0\">否</option>"
				  +"<option value=\"1\">是</option>"
				  +"</select>"
				  +"</div>"
				  +"</form>";
		$("#content_modal_description").html(form);
		var actions = "<div class=\"ui primary button close\" onclick=\"submitAnalysor();\">提交</div>";
		actions += "<div class=\"ui button\" onclick=\"modalfades();\">取消</div>";
		$("#content_modal_actions").html(actions);
		$("#content_modal").modal("show");
	});
	
}

function changecopyAnalysor()
{
	var parserid = $("#copy_parser_list").val();
	if(parserid!=-1)
	{
		$.get("admin/analysor/"+parserid,function(result){
			var parser = result;
			$("#parser_name").val(parser["name"]);
			$("#parser_list_ruler").val(parser["listparser"]);
			$("#parser_list_css").val(parser["listcss"]?1:0);
			$("#parser_list_js").val(parser["listjs"]?1:0);
			$("#parser_next_ruler").val(parser["nextparser"]);
			parser["urlrelativer"]=($("#parser_relative").val()==1?true:false);
			$("#parser_title_ruler").val(parser["titleparser"]);
			$("#parser_time_ruler").val(parser["timeparser"]);
			$("#parser_source_ruler").val(parser["sourceparser"]);
			$("#parser_article_ruler").val(parser["textparser"]);
			$("#parser_time_transfer").val(parser["timetransfer"]);
			$("#parser_article_css").val(parser["artcss"]?1:0);
			$("#parser_article_js").val(parser["artjs"]?1:0);
		});
	}

}

function submitAnalysor()
{
	var parser = {};
	parser["name"]=$("#parser_name").val();
	parser["listparser"]=$("#parser_list_ruler").val();
	parser["listcss"]=($("#parser_list_css").val()==1?true:false);
	parser["listjs"]=($("#parser_list_js").val()==1?true:false);
	parser["nextparser"]=$("#parser_next_ruler").val();
	parser["urlrelativer"]=($("#parser_relative").val()==1?true:false);
	parser["titleparser"]=$("#parser_title_ruler").val();
	parser["timeparser"]=$("#parser_time_ruler").val();
	parser["sourceparser"]=$("#parser_source_ruler").val();
	parser["textparser"]=$("#parser_article_ruler").val();
	parser["timetransfer"]=$("#parser_time_transfer").val();
	parser["artcss"]=($("#parser_article_css").val()==1?true:false);
	parser["artjs"]=($("#parser_article_js").val()==1?true:false);
	
	console.log(parser);
	
	$.postJSON("admin/analysor", parser, function(result){
		console.log(result);
		$("#content_modal").modal("hide");
	});
}


function changeAnalysor(analysorid)
{
	$.get("admin/analysor/"+analysorid,function(result){
		$("#content_modal_header").html("修改解析器");

		var form = "<form class=\"ui form\">"
			  +"<div class=\"disabled field\">"
		      +"<label>编号</label>"
		      +"<input type=\"text\" id=\"parser_id\" value=\""+result["id"]+"\" name=\"nameparser\" placeholder=\"解析器名称\">"
		  	  +"</div>"
		      +"<div class=\"field\">"
		      +"<label>名称</label>"
		      +"<input type=\"text\" id=\"parser_name\" value=\""+result["name"]+"\" name=\"nameparser\" placeholder=\"解析器名称\">"
		  	  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>列表解析规则</label>"
			  +"<input type=\"text\" id=\"parser_list_ruler\" value=\""+result["listparser"]+"\" name=\"listparser\" placeholder=\"列表解析规则\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field\">"
			  +"<label>列表是否支持CSS</label>"
			  +"<select id=\"parser_list_css\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["listcss"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["listcss"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>列表是否支持JS</label>"
			  +"<select id=\"parser_list_js\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["listjs"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["listjs"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>下一页解析规则</label>"
			  +"<input id=\"parser_next_ruler\" type=\"text\" value=\""+result["nextparser"]+"\" name=\"nextparser\" placeholder=\"下一页解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>标题解析规则</label>"
			  +"<input id=\"parser_title_ruler\" type=\"text\" value=\""+result["titleparser"]+"\" name=\"titleparser\" placeholder=\"标题解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>时间解析规则</label>"
			  +"<input id=\"parser_time_ruler\" type=\"text\" value=\""+result["timeparser"]+"\"  name=\"timeparser\" placeholder=\"时间解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>时间转换规则</label>"
			  +"<input id=\"parser_time_transfer\" type=\"text\" value=\""+result["timetransfer"]+"\"  name=\"timetransfer\" placeholder=\"时间转换规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>来源解析规则</label>"
			  +"<input id=\"parser_source_ruler\" type=\"text\" value=\""+result["sourceparser"]+"\"  name=\"sourceparser\" placeholder=\"来源解析规则\">"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>正文解析规则</label>"
			  +"<input id=\"parser_article_ruler\" type=\"text\" value=\""+result["textparser"]+"\"  name=\"articleparser\" placeholder=\"正文解析规则\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"field\">"
			  +"<label>正文是否支持CSS</label>"
			  +"<select id=\"parser_article_css\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["artcss"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["artcss"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>正文是否支持JS</label>"
			  +"<select id=\"parser_article_js\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["artjs"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["artjs"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"field\">"
			  +"<label>是否是相对路径</label>"
			  +"<select id=\"parser_relative\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["urlrelativer"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["urlrelativer"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui primary button close\" onclick=\"modifyAnalysor("+result["id"]+")\">提交</div>";
		actions += "<div class=\"ui button\" onclick=\"modalfade();\">取消</div>";
	
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
		
	});
	
}

function modifyAnalysor(analysorid)
{
	var parser = {};
	parser["name"]=$("#parser_name").val();
	parser["listparser"]=$("#parser_list_ruler").val();
	parser["listcss"]=($("#parser_list_css").val()==1?true:false);
	parser["listjs"]=($("#parser_list_js").val()==1?true:false);
	parser["nextparser"]=$("#parser_next_ruler").val();
	parser["urlrelativer"]=($("#parser_relative").val()==1?true:false);
	parser["titleparser"]=$("#parser_title_ruler").val();
	parser["timeparser"]=$("#parser_time_ruler").val();
	parser["sourceparser"]=$("#parser_source_ruler").val();
	parser["textparser"]=$("#parser_article_ruler").val();
	parser["timetransfer"]=$("#parser_time_transfer").val();
	parser["artcss"]=($("#parser_article_css").val()==1?true:false);
	parser["artjs"]=($("#parser_article_js").val()==1?true:false);
	$.putJSON("admin/analysor/"+analysorid, parser, function(result){
		console.log(result);
		modalfade();
	});
}



function testAnalysor(analysorid,analysorname)
{	
	$("#content_modal_header").html("测试解析器-"+analysorname);
	//$("#content_modal_content").html("");
	
	//$("#content_modal_actions").html("");
	var form = "<form class=\"ui form\">"
			/*+"<div class=\"two fields\">"*/
			  +"<div class=\"field\">"
			  +"<label>输入网页</label>"
			  +"<input type=\"text\" id=\"analysor_test_address\" name=\"site\"  placeholder=\"网页地址\">"
			  +"<div class=\"ui primary button\" onclick=\"testAnalysorGet("+analysorid+");\">确定</div>"
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
	var actions = "<div class=\"ui primary button close\" onclick=\"checkAnalysor("+analysorid+");\">检验成功</div>";
		actions += "<div class=\"ui button\" onclick=\"modalfade();\">取消</div>";
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
}


function checkAnalysor(analysorid)
{
	var data = {};
	data["lastchecktime"]=true;
	$.putJSON("admin/analysor/"+analysorid, data, function(result){
		console.log(result);
		$("#content_modal").modal("hide");
		getAnalysorContent(0);
	});
}

function testAnalysorGet(analysorid)
{
	var params = {};
	params["url"]=$("#analysor_test_address").val();
	$.postJSON("admin/analysor/test/"+analysorid, params, function(result){
		var data = result.data;
		var code = result.code;
		var message = result.message;
		if(code!=200)
		{
			alert(message);
		}
		else
		{
			console.log(result);
			$("#analysor_address").attr("value",data.url);
			$("#analysor_source").attr("value",data.source);
			$("#analysor_pubtime").attr("value",data.time);
			$("#analysor_title").attr("value",data.title);
			$("#analysor_text").html(data.text);
		}
	});
	
}
function infoAnalysor(analysorid)
{
	
	$.get("admin/analysor/"+analysorid,function(result){
		$("#content_modal_header").html("查看解析器");

		var form = "<form class=\"ui form\">"
			  +"<div class=\"disabled field\">"
		      +"<label>编号</label>"
		      +"<input type=\"text\" id=\"parser_name\" value=\""+result["id"]+"\" name=\"nameparser\" placeholder=\"解析器名称\">"
		  	  +"</div>"
		      +"<div class=\"disabled field\">"
		      +"<label>名称</label>"
		      +"<input type=\"text\" id=\"parser_name\" value=\""+result["name"]+"\" name=\"nameparser\" placeholder=\"解析器名称\">"
		  	  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>列表解析规则</label>"
			  +"<input type=\"text\" id=\"parser_list_ruler\" value=\""+result["listparser"]+"\" name=\"listparser\" placeholder=\"列表解析规则\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"disabled field\">"
			  +"<label>列表是否支持CSS</label>"
			  +"<select id=\"parser_list_css\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["listcss"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["listcss"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>列表是否支持JS</label>"
			  +"<select id=\"parser_list_js\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["listjs"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["listjs"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>下一页解析规则</label>"
			  +"<input id=\"parser_next_ruler\" type=\"text\" value=\""+result["nextparser"]+"\" name=\"nextparser\" placeholder=\"下一页解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>标题解析规则</label>"
			  +"<input id=\"parser_title_ruler\" type=\"text\" value=\""+result["titleparser"]+"\" name=\"titleparser\" placeholder=\"标题解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>时间解析规则</label>"
			  +"<input id=\"parser_time_ruler\" type=\"text\" value=\""+result["timeparser"]+"\"  name=\"timeparser\" placeholder=\"时间解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>时间转换规则</label>"
			  +"<input id=\"parser_time_transfer\" type=\"text\" value=\""+result["timetransfer"]+"\"  name=\"timetransfer\" placeholder=\"时间转换规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>来源解析规则</label>"
			  +"<input id=\"disabled parser_source_ruler\" type=\"text\" value=\""+result["sourceparser"]+"\"  name=\"sourceparser\" placeholder=\"来源解析规则\">"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>正文解析规则</label>"
			  +"<input id=\"parser_article_ruler\" type=\"text\" value=\""+result["textparser"]+"\"  name=\"articleparser\" placeholder=\"正文解析规则\">"
			  +"</div>"
			  +"<div class=\"two fields\">"
			  +"<div class=\"disabled field\">"
			  +"<label>正文是否支持CSS</label>"
			  +"<select id=\"parser_article_css\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["artcss"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["artcss"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>正文是否支持JS</label>"
			  +"<select id=\"parser_article_js\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["artjs"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["artjs"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</div>"
			  +"<div class=\"disabled field\">"
			  +"<label>是否是相对路径</label>"
			  +"<select id=\"parser_relative\" type=\"text\" name=\"last-name\" placeholder=\"\">"
			  +"<option value=\"0\" "+(result["urlrelativer"]?"":"selected=\"selected\"")+">否</option>"
			  +"<option value=\"1\" "+(result["urlrelativer"]?"selected=\"selected\"":"")+">是</option>"
			  +"</select>"
			  +"</div>"
			  +"</form>";
	$("#content_modal_description").html(form);
	var actions = "<div class=\"ui button\" onclick=\"modalfade();\">取消</div>";
	
	$("#content_modal_actions").html(actions);
	$("#content_modal").modal("show");
	});
}

function deleteAnalysor(analysorid)
{
	$.deleteJSON("admin/analysor/"+analysorid, function(result){
		console.log(result);
		getAnalysorContent(0);
	});
}

function infoAnalysorBack()
{
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
		  +"<option value=\"0\">否</option>"
		  +"<option value=\"1\">是</option>"
		  +"</select>"
		  +"</div>"
		  +"<div class=\"field\">"
		  +"<label>列表是否支持JS</label>"
		  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
		  +"<option value=\"0\">否</option>"
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
		  +"<option value=\"0\">否</option>"
		  +"<option value=\"1\">是</option>"
		  +"</select>"
		  +"</div>"
		  +"<div class=\"field\">"
		  +"<label>正文是否支持JS</label>"
		  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
		  +"<option value=\"0\">否</option>"
		  +"<option value=\"1\">是</option>"
		  +"</select>"
		  +"</div>"
		  +"</div>"
		  +"<div class=\"disabled field\">"
		  +"<label>是否是相对路径</label>"
		  +"<select type=\"text\" name=\"last-name\" placeholder=\"\">"
		  +"<option value=\"0\">否</option>"
		  +"<option value=\"1\">是</option>"
		  +"</select>"
		  +"</div>"
		  +"</form>";
$("#content_modal_description").html(form);
var actions = "<div class=\"ui primary button close\">关闭</div>";
$("#content_modal_actions").html(actions);
$("#content_modal").modal("show");	

}
