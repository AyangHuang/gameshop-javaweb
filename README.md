##### 大一暑假学校实训独立完成的javaweb项目，也是我第一个项目，特此记录一下
##### 项目简介请看 “个人小项目答辩详解”
##### 直接clone然后在project struct里面设置一下就可以了，注意有两点需要修改
1. Tomacat 配置文件的要修改  Context useHttpOnly="false"， 这样前端才能通过js获取cookie里面的session，具体原因请百度
2. units包的jdbcUnits 里面的配置文件的路径要改成你的配置路径，我用的是绝对路径，所有那边需要改一下

