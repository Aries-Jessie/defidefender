package org.bithacks.defidefender.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Certification extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610ea3806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063525cca9014610067578063543989a214610125578063665d552514610150578063d2589431146102e5575b600080fd5b34801561007357600080fd5b506100ce600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061053b565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156101115780820151818401526020810190506100f6565b505050509050019250505060405180910390f35b34801561013157600080fd5b5061013a6105fe565b6040518082815260200191505060405180910390f35b34801561015c57600080fd5b506102cf600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061060b565b6040518082815260200191505060405180910390f35b3480156102f157600080fd5b5061031060048036038101908080359060200190929190505050610a01565b60405180806020018060200180602001806020018060200186810386528b818151815260200191508051906020019080838360005b83811015610360578082015181840152602081019050610345565b50505050905090810190601f16801561038d5780820380516001836020036101000a031916815260200191505b5086810385528a818151815260200191508051906020019080838360005b838110156103c65780820151818401526020810190506103ab565b50505050905090810190601f1680156103f35780820380516001836020036101000a031916815260200191505b50868103845289818151815260200191508051906020019080838360005b8381101561042c578082015181840152602081019050610411565b50505050905090810190601f1680156104595780820380516001836020036101000a031916815260200191505b50868103835288818151815260200191508051906020019080838360005b83811015610492578082015181840152602081019050610477565b50505050905090810190601f1680156104bf5780820380516001836020036101000a031916815260200191505b50868103825287818151815260200191508051906020019080838360005b838110156104f85780820151818401526020810190506104dd565b50505050905090810190601f1680156105255780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b60606000826040518082805190602001908083835b6020831015156105755780518252602082019150602081019050602083039250610550565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208054806020026020016040519081016040528092919081815260200182805480156105f257602002820191906000526020600020905b8154815260200190600101908083116105de575b50505050509050919050565b6000600180549050905090565b6000610615610da2565b600060a0604051908101604052808981526020018881526020018781526020018681526020018581525091506106496105fe565b9050600182908060018154018082558091505090600182039060005260206000209060050201600090919290919091506000820151816000019080519060200190610695929190610dd2565b5060208201518160010190805190602001906106b2929190610dd2565b5060408201518160020190805190602001906106cf929190610dd2565b5060608201518160030190805190602001906106ec929190610dd2565b506080820151816004019080519060200190610709929190610dd2565b505050506000876040518082805190602001908083835b6020831015156107455780518252602082019150602081019050602083039250610720565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208190806001815401808255809150509060018203906000526020600020016000909192909190915055507f8d5336c84b5b0974bb8cb2dcb3ce75ac0f75319a50aa354dea6d026fb23dcbca888888888860405180806020018060200180602001806020018060200186810386528b818151815260200191508051906020019080838360005b838110156108185780820151818401526020810190506107fd565b50505050905090810190601f1680156108455780820380516001836020036101000a031916815260200191505b5086810385528a818151815260200191508051906020019080838360005b8381101561087e578082015181840152602081019050610863565b50505050905090810190601f1680156108ab5780820380516001836020036101000a031916815260200191505b50868103845289818151815260200191508051906020019080838360005b838110156108e45780820151818401526020810190506108c9565b50505050905090810190601f1680156109115780820380516001836020036101000a031916815260200191505b50868103835288818151815260200191508051906020019080838360005b8381101561094a57808201518184015260208101905061092f565b50505050905090810190601f1680156109775780820380516001836020036101000a031916815260200191505b50868103825287818151815260200191508051906020019080838360005b838110156109b0578082015181840152602081019050610995565b50505050905090810190601f1680156109dd5780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390a160019250505095945050505050565b6060806060806060610a11610da2565b600187815481101515610a2057fe5b906000526020600020906005020160a06040519081016040529081600082018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ad35780601f10610aa857610100808354040283529160200191610ad3565b820191906000526020600020905b815481529060010190602001808311610ab657829003601f168201915b50505050508152602001600182018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610b755780601f10610b4a57610100808354040283529160200191610b75565b820191906000526020600020905b815481529060010190602001808311610b5857829003601f168201915b50505050508152602001600282018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c175780601f10610bec57610100808354040283529160200191610c17565b820191906000526020600020905b815481529060010190602001808311610bfa57829003601f168201915b50505050508152602001600382018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610cb95780601f10610c8e57610100808354040283529160200191610cb9565b820191906000526020600020905b815481529060010190602001808311610c9c57829003601f168201915b50505050508152602001600482018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d5b5780601f10610d3057610100808354040283529160200191610d5b565b820191906000526020600020905b815481529060010190602001808311610d3e57829003601f168201915b505050505081525050905080600001518160200151826040015183606001518460800151849450839350829250819150809050955095509550955095505091939590929450565b60a06040519081016040528060608152602001606081526020016060815260200160608152602001606081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610e1357805160ff1916838001178555610e41565b82800160010185558215610e41579182015b82811115610e40578251825591602001919060010190610e25565b5b509050610e4e9190610e52565b5090565b610e7491905b80821115610e70576000816000905550600101610e58565b5090565b905600a165627a7a72305820e64be581d83ace6c5e2801397e699af9be487514fa111e8fd5addbbfe1866b6d0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"userId\",\"type\":\"string\"}],\"name\":\"getUserAllBlacklistEntitiesIndexs\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getBlacklistRecordsCount\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"entityId\",\"type\":\"string\"},{\"name\":\"weid\",\"type\":\"string\"},{\"name\":\"record\",\"type\":\"string\"},{\"name\":\"publisher\",\"type\":\"string\"},{\"name\":\"createdTime\",\"type\":\"string\"}],\"name\":\"addBlacklistEntity\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"}],\"name\":\"getBlacklistEntityByIndex\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"recordId\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"weid\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"record\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"publisher\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"createdTime\",\"type\":\"string\"}],\"name\":\"AddBlacklistEntityEvent\",\"type\":\"event\"}]";

    public static final String FUNC_GETUSERALLBLACKLISTENTITIESINDEXS = "getUserAllBlacklistEntitiesIndexs";

    public static final String FUNC_GETBLACKLISTRECORDSCOUNT = "getBlacklistRecordsCount";

    public static final String FUNC_ADDBLACKLISTENTITY = "addBlacklistEntity";

    public static final String FUNC_GETBLACKLISTENTITYBYINDEX = "getBlacklistEntityByIndex";

    public static final Event ADDBLACKLISTENTITYEVENT_EVENT = new Event("AddBlacklistEntityEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Certification(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Certification(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Certification(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Certification(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<List> getUserAllBlacklistEntitiesIndexs(String userId) {
        final Function function = new Function(FUNC_GETUSERALLBLACKLISTENTITIESINDEXS, 
                Arrays.<Type>asList(new Utf8String(userId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<BigInteger> getBlacklistRecordsCount() {
        final Function function = new Function(FUNC_GETBLACKLISTRECORDSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addBlacklistEntity(String entityId, String weid, String record, String publisher, String createdTime) {
        final Function function = new Function(
                FUNC_ADDBLACKLISTENTITY, 
                Arrays.<Type>asList(new Utf8String(entityId),
                new Utf8String(weid),
                new Utf8String(record),
                new Utf8String(publisher),
                new Utf8String(createdTime)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addBlacklistEntity(String entityId, String weid, String record, String publisher, String createdTime, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDBLACKLISTENTITY, 
                Arrays.<Type>asList(new Utf8String(entityId),
                new Utf8String(weid),
                new Utf8String(record),
                new Utf8String(publisher),
                new Utf8String(createdTime)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String addBlacklistEntitySeq(String entityId, String weid, String record, String publisher, String createdTime) {
        final Function function = new Function(
                FUNC_ADDBLACKLISTENTITY, 
                Arrays.<Type>asList(new Utf8String(entityId),
                new Utf8String(weid),
                new Utf8String(record),
                new Utf8String(publisher),
                new Utf8String(createdTime)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple5<String, String, String, String, String>> getBlacklistEntityByIndex(BigInteger i) {
        final Function function = new Function(FUNC_GETBLACKLISTENTITYBYINDEX, 
                Arrays.<Type>asList(new Uint256(i)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple5<String, String, String, String, String>>(
                new Callable<Tuple5<String, String, String, String, String>>() {
                    @Override
                    public Tuple5<String, String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue());
                    }
                });
    }

    public List<AddBlacklistEntityEventEventResponse> getAddBlacklistEntityEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ADDBLACKLISTENTITYEVENT_EVENT, transactionReceipt);
        ArrayList<AddBlacklistEntityEventEventResponse> responses = new ArrayList<AddBlacklistEntityEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AddBlacklistEntityEventEventResponse typedResponse = new AddBlacklistEntityEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recordId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.weid = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.record = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.createdTime = (String) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddBlacklistEntityEventEventResponse> addBlacklistEntityEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, AddBlacklistEntityEventEventResponse>() {
            @Override
            public AddBlacklistEntityEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ADDBLACKLISTENTITYEVENT_EVENT, log);
                AddBlacklistEntityEventEventResponse typedResponse = new AddBlacklistEntityEventEventResponse();
                typedResponse.log = log;
                typedResponse.recordId = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.weid = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.record = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.createdTime = (String) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddBlacklistEntityEventEventResponse> addBlacklistEntityEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDBLACKLISTENTITYEVENT_EVENT));
        return addBlacklistEntityEventEventFlowable(filter);
    }

    @Deprecated
    public static Certification load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Certification(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Certification load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Certification(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Certification load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Certification(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Certification load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Certification(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Certification> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Certification.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Certification> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Certification.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Certification> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Certification.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Certification> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Certification.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class AddBlacklistEntityEventEventResponse {
        public Log log;

        public String recordId;

        public String weid;

        public String record;

        public String publisher;

        public String createdTime;
    }
}
