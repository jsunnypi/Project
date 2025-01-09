//SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract IdProject {
    struct Game {
        uint[] blockId;
        mapping(uint256 => string) gameName;
        mapping(uint256 => string) serverName;
        mapping(uint256 => string) className;
        mapping(uint256 => string) remarks;
    }

    mapping(address => Game) private games;

    uint256 public blockCount;

    function insertInfo(
        string memory _gameName,
        string memory _serverName,
        string memory _className,
        string memory _remarks
    ) public {
        require(bytes(_gameName).length > 0, "Game Name is required");
        require(bytes(_serverName).length > 0, "Server Name is required");
        require(bytes(_className).length > 0, "Class Name is required");
        blockCount++;
        games[msg.sender].blockId.push(blockCount);
        games[msg.sender].gameName[blockCount] = _gameName;
        games[msg.sender].serverName[blockCount] = _serverName;
        games[msg.sender].className[blockCount] = _className;
        games[msg.sender].remarks[blockCount] = _remarks;
    }

    event Test(uint);

    function getIndexOf(address _address) public view returns(uint[] memory){
        return games[_address].blockId;
    }

    function getInfo(address _address, uint _blockId)
        public
        view
        returns (
            string memory,
            string memory,
            string memory,
            string memory
        )
    {
        return (
            games[_address].gameName[_blockId],
            games[_address].serverName[_blockId],
            games[_address].className[_blockId],
            games[_address].remarks[_blockId]
        );
    }
}
