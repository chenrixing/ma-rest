package com.ma.rest.utils;

import org.csource.fastdfs.*;

import java.io.File;


public class FastDFSClient {
	    private TrackerClient trackerClient = null;
	    private TrackerServer trackerServer = null;
	    private StorageServer storageServer = null;
	    //使用StorageClient1进行上传
	    private StorageClient1 storageClient1 = null;
	    public FastDFSClient(String conf) throws Exception {
	        //conf直接写相对于classpath的位置，不需要写classpath:
	        String configPath = new File(FastDFSClient.class.getResource("/").getFile()).getCanonicalPath()+File.separator+conf;
	        ClientGlobal.init(configPath);
	        trackerClient = new TrackerClient();
	        trackerServer = trackerClient.getConnection();
				storageServer = trackerClient.getStoreStorage(trackerServer);
				storageClient1 = new StorageClient1(trackerServer, storageServer);
	    }

	    public String uploadFile(byte[] file_buff, String file_ext_name) throws Exception {

	        String result = storageClient1.upload_file1(file_buff, file_ext_name, null);

	        return result;
	    }

	    public String uploadFile(String local_filename, String file_ext_name) throws Exception {

	        String result = storageClient1.upload_file1(local_filename, file_ext_name, null);
	        return result;
	    }
	    
	    public int deleteOldFile(String local_filename) throws Exception {
			return storageClient1.delete_file1(local_filename);
		}
}
