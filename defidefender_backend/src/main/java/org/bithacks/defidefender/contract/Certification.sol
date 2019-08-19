pragma solidity ^0.4.0;

contract Certification {

    //event
    event AddBlacklistEntityEvent(string recordId, string weid, string record, string publisher, string createdTime);

    struct BlacklistEntity {
        string entityId; // 唯一id
        string weid; // 谁违信
        string record; // 违信记录
        string publisher; // 发布者
        string createdTime; // 创建时间
    }

    mapping(string => uint256[]) getBlacklistByUserId; // 获取该用户的黑名单记录

    BlacklistEntity[] blacklistRecords; // 所有黑名单记录

    function addBlacklistEntity(string entityId, string weid, string record, string publisher, string createdTime) public returns (uint256){
        BlacklistEntity memory entity = BlacklistEntity({
            entityId : entityId,
            weid : weid,
            record : record,
            publisher : publisher,
            createdTime : createdTime
            });
        uint256 count = getBlacklistRecordsCount();
        blacklistRecords.push(entity);
        getBlacklistByUserId[weid].push(count);
        emit AddBlacklistEntityEvent(entityId, weid, record, publisher, createdTime);
        return 1;
    }

    function getBlacklistRecordsCount() public view returns (uint256){
        return blacklistRecords.length;
    }

    function getBlacklistEntityByIndex(uint256 i) public view returns (string, string, string, string, string){
        BlacklistEntity memory entity = blacklistRecords[i];
        return (entity.entityId, entity.weid, entity.record, entity.publisher, entity.createdTime);
    }

    function getUserAllBlacklistEntitiesIndexs(string userId) public view returns (uint256[]){
        return getBlacklistByUserId[userId];
    }

}
