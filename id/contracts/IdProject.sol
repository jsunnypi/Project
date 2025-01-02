//SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract IdProject{
    
    struct Game{
        uint id;
        string gameName;
        string serverName;
        string className;      
        string remarks;
    }

    mapping(address => Game) public games;
   
    uint public blockCount;

    function insertInfo(string memory _gameName, string memory _serverName, string memory _className, string memory _remarks) public {
       
        require(bytes(_gameName).length > 0, "Game Name is required");
        require(bytes(_serverName).length > 0, "Server Name is required");
        require(bytes(_className).length > 0, "Class Name is required");

        blockCount++;
        games[msg.sender] = Game(blockCount, _gameName, _serverName, _className, _remarks);
       
    }

    function getInfo() public view returns(uint, string memory, string memory, string memory, string memory){
        return (games[msg.sender].id, games[msg.sender].gameName, games[msg.sender].serverName, games[msg.sender].className, games[msg.sender].remarks);
    }

}
