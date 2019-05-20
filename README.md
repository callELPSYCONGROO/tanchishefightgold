# 使用Python写一个代理IP访问链接的脚本

> 一般用于APP等上的点击链接功能

命令行参数格式：```-type [arg]```

* 获取代理信息： ```-proxy [arg]```

    * arg 为 list 时，获取代理IP列表
    * arg 为 url 时，获取代理IP资源文件URL
    
    默认为url。


* 执行点击操作：```-check [arg]```    
    
    arg 为key=value格式，key、value均不允许为空：

    * url：value为目标URL，默认为DEFAULT_TARGET_URL
    * time：value为点击次数，小于0则为使所有代理请求一次目标URL，默认为-1
    * repeat：value为重复点击次数，只允许为正整数，默认为1
    * callback：value为回调URL，使用GET请求，参数为：status-点击请求目标URL的HTTP状态码，如：https://myapp.com/demo?status=200，默认为空
