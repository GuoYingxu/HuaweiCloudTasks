package com.qst.extensions.huaweicloudtasks.data;

import com.alibaba.fastjson2.JSON;
import com.cloud.apigateway.sdk.utils.Client;
import com.cloud.apigateway.sdk.utils.Request;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.projectman.v4.region.ProjectManRegion;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;
import com.qst.extensions.huaweicloudtasks.service.HwTaskConfigService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import com.qst.extensions.huaweicloudtasks.config.HwTasksConfiguration;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.joni.Regex;
import com.huaweicloud.sdk.projectman.v4.*;
import com.huaweicloud.sdk.projectman.v4.model.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataUtil {
    public static String hwAccesskey;
    public static String hwSecretKey;
    public static String hwProjectIds;

    public static final HwTaskConfigService hwTaskConfigService = ApplicationManager.getApplication().getService(HwTaskConfigService.class);

    public static void getProjectsBySdk() {

        DataUtil.hwAccesskey = hwTaskConfigService.get(HwTasksConfiguration.HW_ACCESS_KEY, "");
        DataUtil.hwSecretKey = hwTaskConfigService.get(HwTasksConfiguration.HW_SECRET_KEY, "");
        if (DataUtil.hwAccesskey.isEmpty() || DataUtil.hwSecretKey.isEmpty()) {
            Messages.showMessageDialog("请检查插件配置", "错误", Messages.getErrorIcon());
            return;
        }
        ICredential auth = new BasicCredentials()
                .withAk(DataUtil.hwAccesskey)
                .withSk(DataUtil.hwSecretKey);

        ProjectManClient client = ProjectManClient.newBuilder()
                .withCredential(auth)
                .withRegion(ProjectManRegion.valueOf("cn-north-1"))
                .build();
        ListProjectsV4Request request = new ListProjectsV4Request();
        try {
            ListProjectsV4Response response = client.listProjectsV4(request);
            String configProjectIds = hwTaskConfigService.get(HwTasksConfiguration.HW_PROJECT_IDS,"");
            if(configProjectIds.isEmpty()) {
                DataCenter.setProjectDataList(response.getProjects());
            }else {
                String[] ids = configProjectIds.split(",");
                DataCenter.setProjectDataList(response.getProjects().stream().filter(projectData -> {
                    for(String id : ids) {
                        if(id.equals(projectData.getProjectId())) {
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toList()));
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getRequestId());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }

    }
//    public static void getProjects() {
//        Request request = new Request();
//        try {
//            DataUtil.hwAccesskey = hwTaskConfigService.get(HwTasksConfiguration.HW_ACCESS_KEY, "");
//            DataUtil.hwSecretKey = hwTaskConfigService.get(HwTasksConfiguration.HW_SECRET_KEY, "");
//            if (DataUtil.hwAccesskey.isEmpty() || DataUtil.hwSecretKey.isEmpty()) {
//                Messages.showMessageDialog("请检查插件配置", "错误", Messages.getErrorIcon());
//                return;
//            }
//            request.setKey(hwAccesskey);
//            request.setSecret(hwSecretKey);
//            request.setUrl("https://projectman-ext.cn-north-1.myhuaweicloud.com/v4/projects?offset=0&limit=100");
//            request.setMethod("GET");
//            request.addHeader("Host","projectman-ext.cn-north-1.myhuaweicloud.com");
//            request.addHeader("X-Domain-Id","ce5fb093a1824ffb89d1efdfe8838f2b");
//        }catch (Exception e) {
//            Messages.showMessageDialog("发送请求发生错误", "错误", Messages.getErrorIcon());
//            return;
//        }
//
//        CloseableHttpClient client = null;
//        try {
//            HttpRequestBase signedRequest= Client.sign(request, Constant.SIGNATURE_ALGORITHM_SDK_HMAC_SHA256);
//            client = (CloseableHttpClient) SSLCipherSuiteUtil.createHttpClient(Constant.INTERNATIONAL_PROTOCOL);
//            HttpResponse response = client.execute(signedRequest);
//            HttpEntity res = response.getEntity();
//            if(res!= null) {
//                String result = EntityUtils.toString(res, "UTF-8");
//                HwProjectResponse projectResponse = JSON.parseObject(result, HwProjectResponse.class);
//                String configProjectIds = hwTaskConfigService.get(HwTasksConfiguration.HW_PROJECT_IDS,"");
//                if(configProjectIds.isEmpty()) {
//                    DataCenter.setProjectDataList(projectResponse.getProjects());
//                }else {
//                    String[] ids = configProjectIds.split(",");
//                    DataCenter.setProjectDataList(projectResponse.getProjects().stream().filter(projectData -> {
//                        for(String id : ids) {
//                            if(id.equals(projectData.getProject_id())) {
//                                return true;
//                            }
//                        }
//                        return false;
//                    }).collect(Collectors.toList()));
//                }
//
//            }
//        } catch (Exception e) {
//            Messages.showMessageDialog("发送请求发生错误", "错误", Messages.getErrorIcon());
//        } finally {
//            if(client != null) {
//                try {
//                    client.close();
//                } catch (Exception e) {
//                    Messages.showMessageDialog("关闭连接发生错误", "错误", Messages.getErrorIcon());
//                }
//            }
//        }
//    }
    public static void getTasks(String projectId) {
        Request request = new Request();
        try {
            DataUtil.hwAccesskey = hwTaskConfigService.get(HwTasksConfiguration.HW_ACCESS_KEY, "");
            DataUtil.hwSecretKey = hwTaskConfigService.get(HwTasksConfiguration.HW_SECRET_KEY, "");
            if (DataUtil.hwAccesskey.isEmpty() || DataUtil.hwSecretKey.isEmpty()) {
                Messages.showMessageDialog("请检查插件配置", "错误", Messages.getErrorIcon());
                return;
            }
            request.setKey(hwAccesskey);
            request.setSecret(hwSecretKey);
            request.setUrl("https://projectman-ext.cn-north-1.myhuaweicloud.com/v4/projects/"+projectId+"/issues");
            request.setMethod("POST");
             Map<String,Object> map = new HashMap<>();
             map.put("offset",0);
                map.put("limit",100);
                map.put("status_ids", new int[]{1,2});
                map.put("tracker_ids",new int[]{2,3});
//                String body = JSON.toJSONString(map);
                String body = "{\'offset\':0,\'limit\':100,\'status_ids\':[1,2],\'tracker_ids\':[2,3]}";
            request.setBody(body);

            request.addHeader("Host","projectman-ext.cn-north-1.myhuaweicloud.com");
            request.addHeader("X-Domain-Id","ce5fb093a1824ffb89d1efdfe8838f2b");
            request.addHeader("Content-Length",String.valueOf(body.length()));
        }catch (Exception e) {
            Messages.showMessageDialog("发送请求发生错误", "错误", Messages.getErrorIcon());
            return;
        }

        CloseableHttpClient client = null;
        try {
            HttpRequestBase signedRequest= Client.sign(request, Constant.SIGNATURE_ALGORITHM_SDK_HMAC_SHA256);
            client = (CloseableHttpClient) SSLCipherSuiteUtil.createHttpClient(Constant.INTERNATIONAL_PROTOCOL);
            HttpResponse response = client.execute(signedRequest);
            HttpEntity res = response.getEntity();
            if(res!= null) {
                String result = EntityUtils.toString(res, "UTF-8");
                HwTaskResponse taskResponse = JSON.parseObject(result,HwTaskResponse.class);
                DataCenter.setTaskDataList(taskResponse.getIssues());
            }
        } catch (Exception e) {
            Messages.showMessageDialog("发送请求发生错误", "错误", Messages.getErrorIcon());
        } finally {
            if(client != null) {
                try {
                    client.close();
                } catch (Exception e) {
                    Messages.showMessageDialog("关闭连接发生错误", "错误", Messages.getErrorIcon());
                }
            }
        }
    }
}
