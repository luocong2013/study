# OpenResty简介：OpenResty快速入门教程



**1、OpenResty简介**

**1.1 了解OpenResty**

OpenResty是一个基于 Nginx 与 Lua 的高性能 Web 平台，其内部集成了大量精良的 Lua 库、第三方模块以及大多数的依赖项。用于方便地搭建能够处理超高并发、扩展性极高的动态 Web 应用、Web 服务和动态网关。

简单地说OpenResty 的目标是让你的Web服务直接跑在 Nginx 服务内部，充分利用 Nginx 的非阻塞 I/O 模型，不仅仅对 HTTP 客户端请求,甚至于对远程后端诸如 MySQL、PostgreSQL、Memcached 以及 Redis 等都进行一致的高性能响应。

**1.2 OpenResty安装**

- 下载对应你系统的OpenResty版本：http://openresty.org/cn/download.html

> 支持 mac linux windows

- 选择安装目录(建议非中文)进行解压

> 双击nginx.exe运行即可



- 测试：打开浏览器访问 localhost

**2、快速入门**

**2.1 动态输出**

我们现在做个最简单的小例子，使用Lua脚本动态输出内容，打开openresty-1.13.6.2-win64目录下的conf/nginx.conf文件

在server中新增以下代码

```conf
location /hello {
  default_type text/html;
  content_by_lua 'ngx.say("<p>hello, world</p>")';
}

类似这样

http {
  server {
  listen 80;
  server_name localhost;
    location / {
      default_type text/html;
      content_by_lua 'ngx.say("<p>hello, world</p>")';
    }
  }
}
```



现在启动nginx，然后访问 http://localhost/hello，如果你之前启动了，那么需要nginx -s reload

通过ngx.say 我们可以往客户端输出响应文本，是不是跟咱们tomcat response.write很像嘻嘻，后期我们会使用它输出json。

还有一个输出的函数是ngx.print，同样也是输出响应内容。

**2.2 优化动态输出**

上面的代码直接把lua代码写到nginx配置里面了，维护起来不是很方便，我们把它拿出来一个单独的文件，并放到openresty-1.13.6.2-win64目录下面单独的lua目录下，方便管理

新建lua/hello.lua 文件，并编写代码

```
ngx.say("<p>hello, world</p>")

nginx.conf 改成这样

location / {
  default_type text/html;
  content_by_lua_file lua/hello.lua;
}
```



然后nginx -s reload 一下，页面效果是一样的



观察以上代码其实还会发现一个问题，如果我们想要处理很多个请求，那不是要在nginx里面配置N个location吗，我们肯定不会这么做，这里可以通过nginx动态匹配指定lua文件名。

nginx.conf 改成这样

```
location ~ /lua/(.+) {
  content_by_lua_file lua/$1.lua;
}
```



新建lua/china.lua 文件，并编写代码

```
ngx.print("<p>hello，china</p>")
```

然后nginx -s reload 一下

这个时候访问hello world的请求url就变成了 http://localhost/lua/hello 了

同理，hello，china就可以通过 http://localhost/lua/china 来访问了


**2.3 接收参数**

我们知道http请求通常分为两种，分别是GET，POST，在http协议中，GET参数通常会紧跟在uri后面，而POST请求参数则包含在请求体中。

使用OpenResty获取请求参数的方法也有二种,为了统一获取请求参数的方式，隐藏具体细节，提供一个更友好的api接口，我们可以简单的封装一下

创建lua/req.lua 文件

```
local _M = {}

-- 获取http get/post 请求参数
function _M.getArgs()
   -- 获取http请求方式 GET or POST
   local request_method = ngx.var.request_method
   -- 这里是一个table，包含所有get请求参数
   local args = ngx.req.get_uri_args()
   -- 如果是post参数获取
   if "POST" == request_method then
      -- 先读取请求体
      ngx.req.read_body()
      -- 这里也是一个table，包含所有post请求参数
      local postArgs = ngx.req.get_post_args()
      if postArgs then
         for k, v in pairs(postArgs) do
            args[k] = v
         end
      end
   end
   return args
end

return _M
```



这个模块就实现了参数的获取，而且支持GET，POST两种传参方式

接下来我们可以写一个简单的lua，来引入这个模块，然后测试一下效果

conf/nginx.conf 需要添加

```
http {
  lua_package_path C:\Users\Ys\Desktop\openresty\openresty-1.13.6.2-win64\lua\?.lua;

  \# 这里一定要指定package_path，否则会找不到引入的模块，然后会500
  server {
    listen 80;
    server_name localhost;
    lua_code_cache off;
    location ~ /lua/(.+) {
      default_type text/html;
      content_by_lua_file lua/$1.lua;
    }
  }
}
```





创建lua/test.lua 文件

-- 引入req模块

local req = require "req"

-- 获取请求参数列表

local args = req.getArgs()

-- 获取key为name的值

local name = args['name']

-- 如果不存在指定默认值

if name == nil or name == "" then

  name = "xiaozhi"

end

-- 输出结果

ngx.say("<p>hello " .. name .. "!</p>")

然后nginx -s reload 一下

访问 http://localhost/lua/test?name=jack


响应客户端默认的参数



参考链接：https://www.itcast.cn/news/20200315/16130698949.shtml





