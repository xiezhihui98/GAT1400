# viid-server

#### 介绍
GA/T1400视图库结构化数据(人脸、人员、物品、场景、机动车、非机动车等)对接。
1 .支持前端感知设备直接接入视图库
2. 支持数据汇聚给上级视图库
3. 提供可视化操作界面、即可直接使用也可二次开发集成

#### 软件架构
Java、Kafka、Redis、MySQL
1. 依赖Kafka做数据缓冲实现数据发布能力
2. 业务库存储MySQL，总共几张表可以自己改别的库

#### 落地场景
1. 公安数据应用1400视图库数据对接
2. 停车场抓拍机汇聚1400协议数据推送第三方
3. 设备数据(结构化数据/图片)汇聚到上层提供给业务应用

#### 页面展示
<img alt="drawing" src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/viid_instance.png"/>
<img alt="drawing" src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/viid_device.png"/>
<img alt="drawing" src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/viid_subscribe.png"/>
<img alt="drawing" src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/viid_collect.png"/>

#### 使用说明

1.  mvn clean install
2.  java -jar viid-server.jar

#### 打赏和联系
可提供生产级别成品Java后端和vue前端样例代码

<img src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/information.jpg" alt="drawing" width="200" height="200"/>
