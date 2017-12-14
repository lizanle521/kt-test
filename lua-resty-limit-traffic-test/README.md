###安装openresty

         wget http://luajit.org/download/LuaJIT-2.1.0-beta1.tar.gz
         tar -xvf LuaJIT-2.1.0-beta1.tar.gz
         cd LuaJIT-2.1.0-beta1
         make
         sudo make install

      下载：  http://openresty.org/cn/download.html
      安装依赖: apt-get install libreadline-dev libncurses5-dev libpcre3-dev \
        libssl-dev perl make build-essential
      安装: tar xzvf openresty-1.11.2.5.tar.gz
            cd openresty-1.11.2.5
            ./configure --prefix=/opt/openresty\
            --with-luajit

        make
        make install

        报错：
        error: ‘SSL_R_NO_CIPHERS_PASSED’ undeclared (first use in this function)
        || n == SSL_R_NO_CIPHERS_PASSED

      openssl 版本过高 ，手动制定openssl版本1.0.1
        ./configure   --with-openssl=/opt/openssl-1.0.1g




### lua-resty-limit-traffic
        https://github.com/openresty/lua-resty-limit-traffic


        limit_req：通过令牌桶原理来限制 用户的连接频率，提供请求的速率限制和调整的基础上的“漏桶”的方法。
        (这个模块允许你去限制单个地址 指定会话或特殊需要 的请求数 )

        conn_limit： 功能是限制一个客户端的并发连接数。(这个模块可以限制单个地址 的指定会话 或者特殊情况的并发连接数)，
        常用来做限制客户端并发连接数量。

        limit.traffic：提供一个聚合器将多个实例的resty.limit.req或resty.limit.conn类，组合limit_req和conn_limit联合使用




