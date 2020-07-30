package com.it.yanxuan.test;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/10 21:45
 */
public class FastDFSClientTest {
    public static void main(String[] args) throws IOException, MyException {

        //String path = FastDFSClientTest.getClass().getResource("/").getPath();


        //加载全局配置文件
        /*String conf_filename = "C:\\com_it\\jiguangyanxuan_parent\\jiguangyanxuan_seller_server\\src\\main\\resources\\fastdfs\\client.properties";
        ClientGlobal.init(conf_filename);
        TrackerClient trackerClient = new TrackerClient();

        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        //上传文件
        String[] pngs = storageClient.upload_file("C:\\Users\\smilecloud\\Desktop\\vim操作.png", "png", null);
        for (String png : pngs) {
            System.out.println(png);
        }*/

        /*
         // 加载配置文件
        ClientGlobal.init(confFileName);
        // 创建TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        // 创建连接，得到TrackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        // 获取StorageServer
        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
        // 创建StorageCLient
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 上传文件
        String[] strings = storageClient.upload_file("C:\\Users\\SCHOLAR\\Desktop\\极光学苑\\极光严选\\day05-商品录入\\资料\\素材/4c477d6b297e9512.jpg", "jpg", null);
        for (String result : strings ) {
            System.out.println(result);
        }
         */
    }
}
