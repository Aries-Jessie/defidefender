package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.Cpt;
import com.webank.weid.protocol.base.CptBaseInfo;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PresentationE;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.contract.Certification;
import org.bithacks.defidefender.dao.CPTRepository;
import org.bithacks.defidefender.dao.ContractRepository;
import org.bithacks.defidefender.dao.CredentialRepository;
import org.bithacks.defidefender.dao.RelationRepository;
import org.bithacks.defidefender.model.Po.CPT;
import org.bithacks.defidefender.model.Po.Contract;
import org.bithacks.defidefender.model.Po.Credential;
import org.bithacks.defidefender.model.Po.Relation;
import org.bithacks.defidefender.service.CommonService;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.utils.CommonUtils;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.PrivateKeyUtil;
import org.bithacks.defidefender.utils.SuperResult;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    private Web3j web3j;

    private Credentials credentials;

    public Web3j getWeb3j() {
        return web3j;
    }

    private void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    private void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Autowired
    private DIDService weIdService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CPTRepository cptRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public SuperResult createCPT(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String publisher = jsonObject.getString(ConstantFields.USER_CPT_PUBLISHER);
            String privateKey = relationRepository.findRelationsByWeid(publisher).get(0).getPrivateKey();
//            String privateKey = PrivateKeyUtil.getPrivateKeyByWeId(ConstantFields.KEY_DIR, publisher);
            JSONObject cptSchemaNode = (JSONObject) jsonObject.get(ConstantFields.USER_CPT_CPTSCHEMA);
            HashMap<String, Object> cptSchema = CommonUtils.convertJsonToMap(cptSchemaNode.toJSONString());
            ResponseData<CptBaseInfo> response = weIdService.registCpt(publisher, privateKey, cptSchema);
            cptRepository.save(new CPT(response.getResult().getCptId(), publisher));
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult registerIssueType(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String issuer = jsonObject.getString(ConstantFields.COMMON_ISSUE_ISSUER);
            String authorityName = jsonObject.getString(ConstantFields.COMMON_ISSUE_AUTHORITYNAME);
            ResponseData<Boolean> responseData = weIdService.registerIssuerType(issuer, authorityName);
            return SuperResult.ok(responseData);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult getCredential(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            int id = jsonObject.getIntValue("id");
            // 读取Credential
            Credential credential = credentialRepository.findOne(id);
            CredentialPojo credentialPojo = CommonUtils.getCredentialFromDB(credential);
//            CredentialPojo credentialPojo = CommonUtils.readObjectFromFile(weid, type);
            return SuperResult.ok(credentialPojo);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult getPresentation(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            String presentationJson = credentialRepository.findCredentialsByWeidAndType(weid, 2).get(0).getCredential();
            PresentationE presentationE = PresentationE.fromJson(presentationJson);
            return SuperResult.ok(presentationE);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public void initializeNetwork() {
        try {
            // init the Service
            @SuppressWarnings("resource")
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
            org.fisco.bcos.channel.client.Service service = context.getBean(org.fisco.bcos.channel.client.Service.class);
            service.run();

            ChannelEthereumService channelEthereumService = new ChannelEthereumService();
            channelEthereumService.setChannelService(service);
            Web3j web3j = Web3j.build(channelEthereumService, 1);

            // init Credentials
            Credentials credentials = Credentials.create(Keys.createEcKeyPair());

            setCredentials(credentials);
            setWeb3j(web3j);

        } catch (Exception e) {
            System.out.println("Initialize error");
        }
    }

    private static BigInteger gasPrice = new BigInteger("30000000");
    private static BigInteger gasLimit = new BigInteger("30000000");

    @Override
    public String deployCertification() {
        try {
            Certification certification = Certification.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
            System.out.println(" deploy Certification success, contract address is " + certification.getContractAddress());
            contractRepository.save(new Contract(ConstantFields.CONTRACT_CERTIFICATION, certification.getContractAddress()));
            return certification.getContractAddress();
        } catch (Exception e) {
            return "fail to deploy";
        }
    }

    @Override
    public Certification getCertification() {
        String address = contractRepository.findOne(ConstantFields.CONTRACT_CERTIFICATION).getAddress();
        System.out.println(address);
//        String address = "0xa87c85e4b5ac3860c619e82d909b17447d63916a";
        initializeNetwork();
        return Certification.load(address, getWeb3j(), getCredentials(), new StaticGasProvider(gasPrice, gasLimit));
    }

    @Override
    public SuperResult getCPTById(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            int cptId = jsonObject.getInteger("cptId");
            ResponseData<Cpt> response = weIdService.getCptById(cptId);
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult getCredentialsByWeid(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            // 读取Credential
            List<Integer> types = new ArrayList<>();
            types.add(0);
            types.add(1);
            List<Credential> credentials = credentialRepository.findCredentialsByWeidAndTypeIn(weid, types);
            List<CredentialPojo> result = new ArrayList<>();
            for (Credential entity : credentials) {
                result.add(CommonUtils.getCredentialFromDB(entity));
            }
            return SuperResult.ok(credentials);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }
}
