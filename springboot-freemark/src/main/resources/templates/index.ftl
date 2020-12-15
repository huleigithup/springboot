<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>配合freemark使用必须使用对应的FTL语法</title>
</head>
<body>
<h1>Hello boy,</h1><br><br>
<h2>配合freemark使用必须使用对应的FTL语法</h2><br>
<a href="https://www.cnblogs.com/-wanglei/p/11149263.html">FTL指令常用标签及语法</a><br>

<p>姓名：${username}</p><br>
<p>密码：${password}</p><br>
<p>测试：${test}</p><br><br>
<p>当前时间：${.now?string("yyyy-MM-dd HH:mm:ss.sss")}</p>

</body>
</html>