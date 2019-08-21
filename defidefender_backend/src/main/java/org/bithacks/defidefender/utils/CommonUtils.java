package org.bithacks.defidefender.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.webank.weid.constant.JsonSchemaConstant;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PresentationE;
import org.bithacks.defidefender.model.Po.Credential;
import org.bithacks.defidefender.model.Po.LoanRecord;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Component
public class CommonUtils {

    public static HashMap<String, Object> buildCptJsonSchema() {

        HashMap<String, Object> cptJsonSchemaNew = new HashMap<String, Object>(3);
        cptJsonSchemaNew.put(JsonSchemaConstant.TITLE_KEY, "Government CPT");
        cptJsonSchemaNew.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is CPT issued by government");

        HashMap<String, Object> propertitesMap1 = new HashMap<String, Object>(2);
        propertitesMap1.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap1.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is name");

        String[] genderEnum = {"F", "M"};
        HashMap<String, Object> propertitesMap2 = new HashMap<String, Object>(2);
        propertitesMap2.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap2.put(JsonSchemaConstant.DATA_TYPE_ENUM, genderEnum);

        HashMap<String, Object> propertitesMap3 = new HashMap<String, Object>(2);
        propertitesMap3.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap3.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is birthday");

        HashMap<String, Object> propertitesMap4 = new HashMap<String, Object>(2);
        propertitesMap4.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap4.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is address");

        HashMap<String, Object> propertitesMap5 = new HashMap<String, Object>(2);
        propertitesMap5.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap5.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is identityNumber");

        HashMap<String, Object> propertitesMap6 = new HashMap<String, Object>(2);
        propertitesMap6.put(JsonSchemaConstant.TYPE_KEY, JsonSchemaConstant.DATA_TYPE_STRING);
        propertitesMap6.put(JsonSchemaConstant.DESCRIPTION_KEY, "this is weid");

        HashMap<String, Object> cptJsonSchema = new HashMap<String, Object>(3);
        cptJsonSchema.put("name", propertitesMap1);
        cptJsonSchema.put("gender", propertitesMap2);
        cptJsonSchema.put("birthday", propertitesMap3);
        cptJsonSchema.put("address", propertitesMap4);
        cptJsonSchema.put("identityNumber", propertitesMap5);
        cptJsonSchema.put("weid", propertitesMap6);

        cptJsonSchemaNew.put(JsonSchemaConstant.PROPERTIES_KEY, cptJsonSchema);

        String[] genderRequired = {"name", "gender", "birthday", "address", "identityNumber"};
        cptJsonSchemaNew.put(JsonSchemaConstant.REQUIRED_KEY, genderRequired);

        return cptJsonSchemaNew;
    }


    public static void writeObjectToFile(CredentialPojo obj, String weid, int type) {
        String path = ConstantFields.CREDENTIAL_DIR + weid.substring(11) + "/";
        FileUtil.checkDir(path);
        File file = new File(type == 0 ? path + "credential.dat" : path + "selectiveCredential.dat");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    public static CredentialPojo getCredentialFromDB(Credential credential) {
        String credentialJson = credential.getCredential();
        CredentialPojo credentialPojo = CredentialPojo.fromJson(credentialJson);
        return credentialPojo;
    }

    public static CredentialPojo readObjectFromFile(String weid, int type) {
        String path = ConstantFields.CREDENTIAL_DIR + weid.substring(11) + "/";
        CredentialPojo temp = null;
        File file = new File(type == 0 ? path + "credential.dat" : path + "selectiveCredential.dat");
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = (CredentialPojo) objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static HashMap<String, Object> convertJsonToMap(String jsonStr) {
        HashMap map = JSON.parseObject(jsonStr, HashMap.class);
        return map;
    }

    public static String generateDateStr() {
        // 获取当前时间
        Date date = new Date();
        // 转换格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        // 转换时间为相应格式
        String format = sdf.format(date);
        return format;
    }

    public static String getEndTime(String createdTime, int month) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Date date = df.parse(createdTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, month);
            Date endTimeDate = calendar.getTime();
            String endTime = df.format(endTimeDate);
            return endTime;
        } catch (Exception e) {
            return "";
        }
    }

    public static String generateBlacklistRecord(LoanRecord record) {
        return "应该于" + record.getEndTime() + "之前还款" + record.getAmount() + "万元，但逾期未换";
    }

}
