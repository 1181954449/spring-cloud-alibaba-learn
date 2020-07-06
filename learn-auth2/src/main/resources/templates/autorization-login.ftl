<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>LEARN-AUTH2 授权登录页面</title>
</head>


<style>
    .login-container {
        margin: 50px;
        width: 100%;
    }

    .form-container {
        margin: 0px auto;
        width: 50%;
        text-align: center;
        box-shadow: 1px 1px 10px #888888;
        height: 300px;
        padding: 5px;
    }

    input {
        margin-top: 10px;
        width: 350px;
        height: 30px;
        border-radius: 3px;
        border: 1px #E9686B solid;
        padding-left: 2px;

    }


    .btn {
        width: 350px;
        height: 35px;
        line-height: 35px;
        cursor: pointer;
        margin-top: 20px;
        border-radius: 3px;
        background-color: #E9686B;
        color: white;
        border: none;
        font-size: 15px;
    }

    .title{
        margin-top: 5px;
        font-size: 18px;
        color: #E9686B;
    }
</style>

<body>
<div class="login-container">
    <div class="form-container">
        <p class="title">用户登录</p>
        <form name="loginForm" method="post" action="/login">
            <input type="text" name="username" placeholder="用户名"/>
            <br>
            <input type="password" name="password" placeholder="密码"/>
            <br>
            <button type="submit" class="btn">登 &nbsp;&nbsp; 录</button>
        </form>
    </div>
</div>
</body>
</html>