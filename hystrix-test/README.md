###Hystrix内部处理逻辑


        1、构建Hystrix的Command对象，调用执行方法
        2、Hystrix检查当前服务器的熔断器开关是否开启。若开启，则执行降级服务getFallback方法
        3、若熔断器开关关闭，则Hystrix检查当前服务的线程池是否能接收新请求，若超过线程池已满，则执行降级服务getFallback方法
        4、若线程池接收请求，则Hystrix开始执行服务调用具体逻辑run方法
        5、若服务执行失败，则执行降级服务getFallback方法，并将执行结果上报Mertics更新服务健康状况
        6、若服务执行超时，则执行降级服务getFallback方法，并将执行结果上报Mertics更新服务健康状况
        7、若服务执行成功，返回正常结果
        8、若服务降级方法getFallback执行成功，则返回执行降级结果
        9、若服务降级方法getFallback执行失败，则抛出异常


        http://blog.csdn.net/harris135/article/details/77879148?locationNum=3&fps=1