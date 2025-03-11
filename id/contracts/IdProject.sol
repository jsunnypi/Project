//SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

contract IdProject {
    string public name = "WhatsID";
    string public symbol = "WID";
    uint8 public decimals = 18;
    uint256 public totalSupply;
    address public owner;

    mapping(address => uint256) public balanceOf;
    mapping(address => mapping(address => uint256)) public allowance;

    struct Game {
        uint[] blockId;
        mapping(uint256 => string) nickname;
        mapping(uint256 => string) gameName;
        mapping(uint256 => string) serverName;
        mapping(uint256 => string) className;
        mapping(uint256 => string) remarks;
        mapping(uint256 => uint) date;
    }

    mapping(address => Game) games;

    uint256 public blockCount;

    struct Set {
        address[] values;
        mapping(address => bool) is_in;
    }

    uint public userCnt;
    Set userSet;

    // 채굴 관련 변수
    mapping(address => uint256) public lastMineTime; // 마지막 채굴 시간 기록
    uint256 public miningReward = 1 * 10 ** 3; // 채굴 시 지급되는 토큰 보상 (1 WID)

    constructor() {
        owner = msg.sender;
        totalSupply = 100000000000 * (10 ** decimals); // 1000억 개 초기 발행
        balanceOf[owner] = totalSupply; // 발행자는 모든 토큰을 보유
    }

    event Transfer(address indexed from, address indexed to, uint256 value);

    // 토큰 전송을 위한 함수 수정: 발행자만 전송 가능
    function transfer(
        address _to,
        uint256 _value
    ) public returns (bool success) {
        require(msg.sender == owner, "Only owner can transfer tokens");
        require(balanceOf[owner] >= _value, "Insufficient balance");
        balanceOf[owner] -= _value;
        balanceOf[_to] += _value;
        return true;
    }

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
        games[msg.sender].nickname[blockCount] = _nickname;
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
            games[_address].nickname[_blockId],
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
                    tempUsers[cnt] = games[user[i]].nickname[blocks[j]];
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

    // 통계 --> 1.누적 사용자 수 조회
    function getTotalUserCount() public view returns (uint) {
        return userCnt;
    }

    // 통계 --> 2.누적 블록 수 조회
    function getTotalBlockCount() public view returns (uint) {
        return blockCount;
    }

    // 통계 --> 3. 최근 사용자 10명
    // UserBlock 구조체 정의
    struct UserBlock {
        address user;
        uint lastBlockTimestamp;
    }

    function getRecentUser()
        public
        view
        returns (string[] memory, address[] memory)
    {
        uint maxCount = 10; // 최신 10명을 추출
        address[] memory allUsers = getUser();
        uint totalUsers = allUsers.length;

        // 블록 생성 시간을 기준으로 사용자들의 데이터를 저장
        UserBlock[] memory userBlocks = new UserBlock[](totalUsers); // UserBlock 배열을 미리 생성

        for (uint i = 0; i < totalUsers; i++) {
            uint[] memory blocks = getIndexOf(allUsers[i]);
            uint latestBlockTimestamp = 0;

            for (uint j = 0; j < blocks.length; j++) {
                uint blockId = blocks[j];
                uint timestamp = games[allUsers[i]].date[blockId];
                if (timestamp > latestBlockTimestamp) {
                    latestBlockTimestamp = timestamp;
                }
            }

            userBlocks[i] = UserBlock(allUsers[i], latestBlockTimestamp);
        }

        // 최신 블록 생성자들을 정렬 (시간이 최신인 순서대로)
        for (uint i = 0; i < totalUsers; i++) {
            for (uint j = i + 1; j < totalUsers; j++) {
                if (
                    userBlocks[i].lastBlockTimestamp <
                    userBlocks[j].lastBlockTimestamp
                ) {
                    UserBlock memory temp = userBlocks[i];
                    userBlocks[i] = userBlocks[j];
                    userBlocks[j] = temp;
                }
            }
        }

        // 최근 10명만 닉네임으로 반환
        uint resultCount = totalUsers < maxCount ? totalUsers : maxCount;
        string[] memory recentNicknames = new string[](resultCount);
        address[] memory recentAddresses = new address[](resultCount);

        for (uint i = 0; i < resultCount; i++) {
            // 가장 최근의 블록 ID를 찾기
            uint lastBlockId = 0;
            uint latestTimestamp = 0;

            // 블록 ID 배열을 순회하면서 최신 블록을 찾는다
            uint[] memory blocks = getIndexOf(userBlocks[i].user);
            for (uint j = 0; j < blocks.length; j++) {
                uint blockId = blocks[j];
                uint timestamp = games[userBlocks[i].user].date[blockId];
                if (timestamp > latestTimestamp) {
                    latestTimestamp = timestamp;
                    lastBlockId = blockId; // 최신 블록 ID를 기록
                }
            }

            // 최신 블록 ID를 기반으로 닉네임 가져오기
            recentNicknames[i] = games[userBlocks[i].user].nickname[
                lastBlockId
            ];
            recentAddresses[i] = userBlocks[i].user;
        }

        return (recentNicknames, recentAddresses);
    }

    // 채굴 기능 추가: 사용자에게 토큰을 지급하는 기능
    function mine() public {
        require(msg.sender != owner, "Owner cannot mine tokens."); // 발행자는 채굴 불가
        require(
            block.timestamp >= lastMineTime[msg.sender] + 1,
            "Must wait at least 1 second before mining again." // 1초 제한
        );
        require(
            balanceOf[owner] >= miningReward,
            "Insufficient tokens for mining reward"
        ); // 발행자의 잔액 확인

        uint256 randomValue = uint256(
            keccak256(
                abi.encodePacked(block.timestamp, msg.sender, block.prevrandao)
            )
        ) % 100;

        if (randomValue < 10) {
            // 10% 확률로 성공
            balanceOf[owner] -= miningReward; // 발행자의 토큰 감소
            balanceOf[msg.sender] += miningReward; // 채굴자의 토큰 증가
            lastMineTime[msg.sender] = block.timestamp; // 마지막 채굴 시간 갱신
            emit Transfer(owner, msg.sender, miningReward);
        } else {
            revert("Mining attempt failed. Try again.");
        }
    }

    // 사용자별 토큰 보유 개수 조회
    function getTokenBalance(address _user) public view returns (uint256) {
        return balanceOf[_user];
    }
}

