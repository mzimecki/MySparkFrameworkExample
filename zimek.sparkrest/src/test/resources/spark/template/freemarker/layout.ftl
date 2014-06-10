<html>
<head>
	<title>Freemarker</title>
	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="http://getbootstrap.com/examples/starter-template/starter-template.css">
</head>
<body>
	<h1>${blogTitle}</h1>
	
	<#list myList as u>
		<h4>${u.getName()}: ${u.getEmail()}</h4>
	</#list>

	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
</body>
<html>