<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Weekop - zmiana hasła</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" type="text/css" rel="stylesheet">
</head>

<body>

<jsp:include page="fragment/navbar.jspf" />

<div class="container">
    <div class="col-sm-6 col-md-4 col-md-offset-4">
        <form class="form-signin" method="post" action="changePassword">
            <h2 class="form-signin-heading">Zarejestruj się</h2>
            <input name="inputOldPassword" type="password" class="form-control" placeholder="Stare hasło" required autofocus />
            <input name="inputNewPassword" type="password" name="inputUsername" class="form-control" placeholder="Nowe hasło" required autofocus />
            <button class="btn btn-lg btn-primary btn-block" type="submit" >Zmień hasło</button>
        </form>
    </div>
</div>

<jsp:include page="fragment/footer.jspf" />

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="resources/js/bootstrap.js"></script>
</body>
</html>