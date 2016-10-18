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
    <script src="resources/semanticui/semantic.min.js"></script>
    
    <link rel="stylesheet" type="text/css" href="resources/semanticui/semantic.min.css">
  </head>
  <body >

	<div id="header" >
		
	
	
	</div>
	
	<div id="main">
		<div id="content"></div>
			<div class="ui bottom example attached segment pushable">
  <div class="ui example visible inverted left vertical sidebar menu">
    <a class="item"><i class="home icon"></i> Home </a>
    <a class="item"><i class="block layout icon"></i> Topics </a>
    <a class="item"><i class="smile icon"></i> Friends </a>
    <a class="item"><i class="calendar icon"></i> History </a>
  </div>
  <div class="pusher">
    <div class="ui basic segment">
      <h3 class="ui header">Application Content</h3>
      <p></p>
      <p></p>
      <p></p>
      <p></p>
    </div>
  </div>
</div>
		
		<div class="ui primary button" id="test">测试</div>

	</div>
    
    <div id="footer">
    </div>
    
    
    <script type="text/babel" src="resources/app/example.js"></script>
    <script type="text/babel" src="resources/app/home.js"></script>
    <script type="text/babel" src="resources/app/common.js"></script>
    <script type="text/javascript" src="resources/app/bundle.js"></script>
    <script type="text/babel">
     
    </script>
    
    <script type="text/javascript">
    	console.log(1);
    	$("#test").click(function(){
    		console.log(2);	
    		$('.ui.sidebar')
    		  .sidebar({
    		    context: '.bottom.segment'
    		  })
    		  .sidebar('hide');
    		console.log(3);	
    	});
    	$('.visible.ui.sidebar').click(function(){
    		console.log(4);
    	});
    
    </script>
    
  </body>
</html>