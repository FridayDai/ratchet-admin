<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title><g:layoutTitle default="Grails"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
	<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
	<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
	<asset:stylesheet src="css/rest.css"/>
	%{--<link rel="stylesheet" href="/assets/bower_components/DataTables/media/css/jquery.dataTables.css">--}%
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<g:if test="${cssPath}">
		<asset:stylesheet src="css/pages/${cssPath}"/>
	</g:if>
	<g:layoutHead/>
	<g:render template="/shared/pingdomRUM" />
</head>

<body>
<g:render template="/shared/header"></g:render>
<g:render template="/shared/nav"></g:render>

<div id="main" class="main container">
	<g:layoutBody/>
</div>

<g:if test="${commonScriptPath}">
	<asset:javascript src="${commonScriptPath}"/>
</g:if>
<g:if test="${scriptPath}">
	<asset:javascript src="${scriptPath}"/>
</g:if>
<g:else>
	<asset:javascript src="bundles/defaultBundle"/>
</g:else>
</body>
</html>
