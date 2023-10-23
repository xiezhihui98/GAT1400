package com.cz.viid.framework.config;

public class Constants {
    public static class KAFKA_CONSUMER {
        public static final String APP_DEFAULT_GROUP = "GA1400_BACKEND";

        //消费失败重试topic
        public static final String FACE_INFO_RETRY_TOPIC = APP_DEFAULT_GROUP + "_FACE_INFO_RETRY";
        public static final String MOTOR_VEHICLE_RETRY_TOPIC = APP_DEFAULT_GROUP + "_MOTOR_VEHICLE_RETRY";
    }

    public static class DEFAULT_TOPIC_PREFIX {
        //卡口设备前缀
        public static final String TOLLGATE_DEVICE = "GA1400-TOLLGATE_DEVICE-";
        //车辆抓拍前缀
        public static final String MOTOR_VEHICLE = "GA1400-MOTOR_VEHICLE-";
        //采集设备前缀
        public static final String APE_DEVICE = "GA1400-APE_DEVICE-";
        //人脸抓拍前缀
        public static final String FACE_RECORD = "GA1400-FACE_RECORD-";
        //非机动车前缀
        public static final String NON_MOTOR_VEHICLE = "GA1400-NON_MOTOR_VEHICLE-";
        //人员抓拍前缀
        public static final String PERSON_RECORD = "GA1400-PERSON_RECORD-";

        public static final String RAW = "GA1400-RAW-";
    }

    public static class VIID_SERVER {
        public static class TRANSMISSION {
            public static final String HTTP = "http";
            public static final String WEBSOCKET = "websocket";
        }
        public static class SERVER_CATEGORY {
            //自己
            public static final String GATEWAY = "0";
            //下级
            public static final String DOWN_DOMAIN = "1";
            public static final String UP_DOMAIN = "2";
        }
        public static class RESOURCE_CLASS {
            //卡口ID
            public static final Integer TOLLGATE = 0;
            //视图库ID
            public static final Integer VIEW_LIBRARY = 4;
        }

        public static class SUBSCRIBE_DETAIL {
            //案(事)件目录
            public static final String AN_JIANG_DIR = "1";
            //单个案(事)件目录
            public static final String AN_JIANG = "2";
            //采集设备目录
            public static final String DEVICE_DIR = "3";
            //采集设备状态
            public static final String DEVICE_STATUS = "4";
            //采集系统目录
            public static final String SYSTEM_DIR = "5";
            //采集系统状态
            public static final String SYSTEM_STATUS = "6";
            //视频卡口目录
            public static final String VIDEO_TOLLGATE_DIR = "7";
            //单个卡口记录
            public static final String TOLLGATE_RECORD = "8";
            //车道目录
            public static final String CHE_DAO_DIR = "9";
            //单个车道记录
            public static final String CHE_DAO = "10";
            //自动采集的人员信息
            public static final String PERSON_INFO = "11";
            //自动采集的人脸信息
            public static final String FACE_INFO = "12";
            //自动采集的车辆信息
            public static final String PLATE_INFO = "13";
            //自动采集的非机动车辆信息
            public static final String PLATE_MIRCO_INFO = "14";
            //自动采集的物品信息
            public static final String SUBSTANCE_INFO = "15";
            //自动采集的文件信息
            public static final String FILE_INFO = "16";

            public static final String RAW = "999";
        }
    }

    public static class SUBSCRIBE {
        public static class OPERATE_TYPE {
            public static final Integer CONTINUE = 0;
            public static final Integer CANCEL = 1;
        }

        public static class STATUS {
            public static final Integer CONTINUE = 0;
            public static final Integer CANCEL = 1;
            public static final Integer EXPIRE = 2;
            public static final Integer INACTIVE = 9;
        }
    }
}
