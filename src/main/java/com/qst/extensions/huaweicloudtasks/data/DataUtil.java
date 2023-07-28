package com.qst.extensions.huaweicloudtasks.data;

import com.alibaba.fastjson2.JSON;
import com.cloud.apigateway.sdk.utils.Client;
import com.cloud.apigateway.sdk.utils.Request;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;
import com.qst.extensions.huaweicloudtasks.service.HwTaskConfigService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import com.qst.extensions.huaweicloudtasks.config.HwTasksConfiguration;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DataUtil {
    public static String hwAccesskey;
    public static String hwSecretKey;
    public static String hwProjectIds;

    public static final HwTaskConfigService hwTaskConfigService = ApplicationManager.getApplication().getService(HwTaskConfigService.class);

    public static void getProjects() {
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
            request.setUrl("https://projectman-ext.cn-north-1.myhuaweicloud.com/v4/projects?offset=0&limit=100");
            request.setMethod("GET");
            request.addHeader("Host","projectman-ext.cn-north-1.myhuaweicloud.com");
            request.addHeader("X-Domain-Id","ce5fb093a1824ffb89d1efdfe8838f2b");
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
                HwProjectResponse projectResponse = JSON.parseObject(result, HwProjectResponse.class);
                String configProjectIds = hwTaskConfigService.get(HwTasksConfiguration.HW_PROJECT_IDS,"");
                if(configProjectIds.isEmpty()) {
                    DataCenter.setProjectDataList(projectResponse.getProjects());
                }else {
                    String[] ids = configProjectIds.split(",");
                    DataCenter.setProjectDataList(projectResponse.getProjects().stream().filter(projectData -> {
                        for(String id : ids) {
                            if(id.equals(projectData.getProject_id())) {
                                return true;
                            }
                        }
                        return false;
                    }).collect(Collectors.toList()));
                }

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
            request.setUrl("https://projectman-ext.cn-north-1.myhuaweicloud.com/v4/projects/" + projectId + "/issues");
            request.setMethod("POST");
            Map<String, Object> map = new HashMap<>();
            map.put("offset", 0);
            map.put("limit", 100);
            map.put("status_ids", new int[]{1,2});
            map.put("tracker_ids",new int[]{2,3});
            String body = JSON.toJSONString(map);
            request.setBody(body);
            request.addHeader("Host","projectman-ext.cn-north-1.myhuaweicloud.com");
            request.addHeader("X-Domain-Id","ce5fb093a1824ffb89d1efdfe8838f2b");
            request.addHeader("Content-Type","application/json;charset=utf-8");
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
            System.out.println(e.getMessage());
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
