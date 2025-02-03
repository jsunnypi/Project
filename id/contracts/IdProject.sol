//SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

contract IdProject {
    struct Game {
        uint[] blockId;
        string nickname;
        mapping(uint256 => string) gameName;
        mapping(uint256 => string) serverName;
        mapping(uint256 => string) className;
        mapping(uint256 => string) remarks;
        mapping(uint256 => uint) date;
    }

    mapping(address => Game) public games;

    uint256 public blockCount;

    struct Set {
        address[] values;
        mapping(address => bool) is_in;
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

    function getUser() public view returns (address[] memory) {
        address[] memory userList = new address[](userCnt);
        for (uint i = 0; i < userCnt; i++) {
            userList[i] = userSet.values[i];
        }
        return userList;
    }

    //블록 생성
    function insertInfo(
        string memory _nickname,
        string memory _gameName,
        string memory _serverName,
        string memory _className,
        string memory _remarks
    ) public {
        require(bytes(_gameName).length > 0, "Game Name is required");
        require(bytes(_serverName).length > 0, "Server Name is required");
        require(bytes(_className).length > 0, "Class Name is required");

        blockCount++;
        add(msg.sender);

        games[msg.sender].blockId.push(blockCount);
        games[msg.sender].nickname = _nickname;
        games[msg.sender].gameName[blockCount] = _gameName;
        games[msg.sender].serverName[blockCount] = _serverName;
        games[msg.sender].className[blockCount] = _className;
        games[msg.sender].remarks[blockCount] = _remarks;
        games[msg.sender].date[blockCount] = block.timestamp;
    }

    //계정별 블록 넘버 모음 (배열) -->계정별 소유 블럭
    function getIndexOf(address _address) public view returns (uint[] memory) {
        return games[_address].blockId;
    }

    //블록별 정보 출력
    function getInfo(
        address _address,
        uint _blockId
    )
        public
        view
        returns (
            string memory,
            string memory,
            string memory,
            string memory,
            string memory,
            uint
        )
    {
        return (
            games[_address].nickname,
            games[_address].gameName[_blockId],
            games[_address].serverName[_blockId],
            games[_address].className[_blockId],
            games[_address].remarks[_blockId],
            games[_address].date[_blockId]
        );
    }

    //키워드 검색 --> 블럭아이디 배열뿐.
    function linearSearch(
        string memory _search
    ) public view returns (string[] memory, address[] memory) {
        address[] memory user = getUser();

        // 결과 저장을 위한 동적 배열 생성
        string[] memory tempUsers = new string[](userCnt);
        address[] memory tempAddresses = new address[](userCnt);
        uint cnt = 0;

        for (uint i = 0; i < userCnt; i++) {
            uint[] memory blocks = getIndexOf(user[i]);
            for (uint j = 0; j < blocks.length; j++) {
                if (
                    keccak256(bytes(games[user[i]].gameName[blocks[j]])) ==
                    keccak256(bytes(_search)) ||
                    keccak256(bytes(games[user[i]].serverName[blocks[j]])) ==
                    keccak256(bytes(_search))
                ) {
                    tempUsers[cnt] = games[user[i]].nickname;
                    tempAddresses[cnt] = user[i];
                    cnt++;
                    break; // 한 번 찾으면 중복 저장 방지
                }
            }
        }

        // 정확한 크기의 배열로 변환
        string[] memory users = new string[](cnt);
        address[] memory addresses = new address[](cnt);

        for (uint k = 0; k < cnt; k++) {
            users[k] = tempUsers[k];
            addresses[k] = tempAddresses[k];
        }

        return (users, addresses);
    }
}
