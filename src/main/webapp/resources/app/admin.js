var chooselabels = [];
var choosenews =[];

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
	
}

function getSystemContent()
{
	$.get("admin/system/statcategory/tree",function(raw_result){
		
		var result = raw_result;
		var content ="<tbody><tr><td><div id=\"label_tree\"></div></td><td><div id=\"label_detail\"></div></td></tr></tbody>";
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
