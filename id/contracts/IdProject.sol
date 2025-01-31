//SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract IdProject {
    struct Game {
        uint[] blockId;
        string nickName;
        mapping(uint256 => uint) date;
        mapping(uint256 => string) gameName;
        mapping(uint256 => string) serverName;
        mapping(uint256 => string) className;
        mapping(uint256 => string) remarks;
    }

    mapping(address => Game) private games;

    uint256 public blockCount;

    //**유저 관련**
    struct Set {
        address[] values;
        mapping (address => bool) is_in;
    }

    uint public userCnt;
    Set userSet;

    function add(address a) public {
        if (!userSet.is_in[a]) {
            userSet.values.push(a);
            userSet.is_in[a] = true;
            userCnt++;
        }
    }

    function getUser() public view returns(address[]  memory){
        address[] memory userList = new address[](userCnt);
        for(uint i=0;i<userCnt;i++){
            userList[i]=userSet.values[i];
        }
        return userList;
    }


    //**블록 관련**
    //블록 생성하는 함수
    function insertInfo(string memory _nickName, string memory _gameName, string memory _serverName, string memory _className, string memory _remarks) public {
        require(bytes(_gameName).length > 0, "Game Name is required");
        require(bytes(_serverName).length > 0, "Server Name is required");
        require(bytes(_className).length > 0, "Class Name is required");
   
        blockCount++;
        add(msg.sender);

        games[msg.sender].blockId.push(blockCount);
        games[msg.sender].nickName = _nickName;
        games[msg.sender].gameName[blockCount] = _gameName;
        games[msg.sender].serverName[blockCount] = _serverName;
        games[msg.sender].className[blockCount] = _className;
        games[msg.sender].remarks[blockCount] = _remarks;
        games[msg.sender].date[blockCount] = block.timestamp;

    }

    //계정별 소유 블록 함수 배열
    function getIndexOf(address _address) public view returns(uint[] memory){
        return games[_address].blockId;
    }

    //블록별 정보 출력하는 함수
    function getInfo(address _address, uint _blockId) public view returns (string memory,string memory, string memory, string memory,string memory, uint) {
        return (
            games[_address].nickName,
            games[_address].gameName[_blockId],
            games[_address].serverName[_blockId],
            games[_address].className[_blockId],
            games[_address].remarks[_blockId],
            games[_address].date[_blockId]
        );
    }
    
    //**검색 관련**
    //키워드 검색
    function linearSearch(string memory _search) public view returns(string[] memory, address[] memory){
        uint resultCnt;
        address[] memory user = getUser();
        string[] memory users = new string[](userCnt);
        address[] memory userAddress = new address[](userCnt);
        for(uint i=0; i<userCnt; i++){
            uint[] memory blocks= getIndexOf(user[i]);
            for(uint j=0;j<blocks.length;j++)
            {
                if(keccak256(bytes(games[user[i]].gameName[blocks[j]])) == keccak256(bytes(_search))||keccak256(bytes(games[user[i]].serverName[blocks[j]])) == keccak256(bytes(_search))){
                users[resultCnt]=games[user[i]].nickName;
                userAddress[resultCnt]=user[i];
                resultCnt++;
                break;
                }
            }   
        }

        // 결과 배열 크기를 정확하게 설정
        string[] memory finalUsers = new string[](resultCnt);
        address[] memory finalUserAddresses = new address[](resultCnt);
        
        // 일치하는 값들만 새로운 배열에 복사
        for (uint i = 0; i < resultCnt; i++) {
            finalUsers[i] = users[i];
            finalUserAddresses[i] = userAddress[i];
        }
        return (finalUsers, finalUserAddresses);
   }

}