# viid-boot

#### 平台介绍
viid-boot是参考<GA/T1400 2017>协议和<GA/T1400 2018试行>协议基于Java开发的视图库平台,支持国标采集设备接入、订阅/推送数据级联,
提供UI可视化操作。支持的结构化数据包含人脸、人员、机动车、非机动车、视频卡口目录、采集设备目录、车道目录
1. 设备对接结构化数据汇聚,支持标准协议设备接入
2. 下级应用视图库数据订阅，支持订阅下级应用视图库数据
3. 下级应用视图库数据发布，支持上级订阅请求,实时推送视图库数据给上级节点
4. 支持的结构化数据包含人脸、人员、机动车、非机动车、视频卡口目录、采集设备目录、车道目录

#### 落地场景
1. 公安领域数据应用1400视图库数据对接
2. 停车场抓拍机汇聚1400协议数据推送第三方
3. 设备数据(结构化数据/图片)汇聚到上层提供给业务应用

#### 软件技术
* 服务端基于Java(openjdk8)、SpringBoot2.7开发。
* UI页面基于Vue2、ElementUI开发。
* 可选中间件依赖包含数据库、MQ、图片存储,最少依赖下通过sqlite、内存MQ插件直接运行jar。

| 类型  | 插件            | 描述        |
|-----|---------------|-----------|
| 数据库 | MySQL8.x      | 支持        |
| 数据库 | Postgresql14  | 支持        |
| 数据库 | Sqlite        | 支持,内嵌无依赖  |
| 数据库 | Elasticsearch | 支持,大数据量优选 |
| MQ  | Kafka         | 支持,高性能优选  |
| MQ  | 内存队列          | 支持,内嵌无依赖  |
| 图片  | OSS           | 支持        |
| 图片  | 磁盘文件          | 支持,内嵌无依赖  |
| 图片  | 磁盘块文件         | 支持,内嵌无依赖  |

#### 特色功能
| 类型   | 能力          | 描述           |
|------|-------------|--------------|
| 订阅推送 | 国标采集通道推送数据  | 单向推送无订阅下发动作  |
| 订阅推送 | FTP文件推送采集数据 | 双网双平台数据过边界   |
| 订阅推送 | 自定义接口推送采集数据 | 非国标第三方平台推数据  |
| 全功能  | 接受各类设备定制兼容  | 实测生产环境功能定制兼容 |

#### 演示平台
演示地址：http://101.43.113.161/ \
账号：viid \
密码：xiezhihui_viid

#### 参考机器性能和接入设备数量
* 指标数据基于海康设备采集大图(800K/1920*1080)小图(200K)一次采集大约在1MB左右统计

| 配置            | 采集设备数    | 采集数据量数   | 描述          |
|---------------|----------|----------|-------------|
| 2核4G内存50G磁盘   | 20~100   | 日采集10W   | 最低可运行环境     |
| 8核16G内存4T磁盘   | 500~1000 | 日采集50W   | 基础运行环境,低频采集 |
| 8核16G内存8T磁盘   | 500~1000 | 日采集100W  | 基础运行环境,高频采集 |
| 32核32G内存32T磁盘 | 2000+    | 日采集2000W | 理想运行环境,高频采集 |
| 多节点分摊         | 远超2000   | 日采集亿级    | 多节点负载,高频采集  |

#### 页面展示

<img alt="drawing" src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/viid_instance.png"/>
<img alt="drawing" src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/viid_device.png"/>
<img alt="drawing" src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/viid_subscribe.png"/>
<img alt="drawing" src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/viid_collect.png"/>

#### 联系方式
有偿提供生产级别成品Java后端和vue前端代码，部署上开箱即用上下级平台对接、前端设备对接

<img src="https://gitee.com/xzh-dev/viid-server/raw/master/assets/information.jpg" alt="drawing" width="200" height="200"/>
