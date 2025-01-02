//SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract ProductMarketplace{
    struct Product{
        uint id;
        string name;
        uint price;
        address payable owner;
        bool purchased;
    }

    mapping(uint => Product) public products;
    uint public productCount;

    event ProductCreated(uint id, string name, uint price, address owner);
    event ProductPurchased(uint id, address buyer);

    function createProduct(string memory _name, uint _price) public{
        require(bytes(_name).length > 0, "Product name is required");
        require(_price > 0, "Price must be greater than 0");

        productCount++;
        products[productCount] = Product(productCount, _name, _price, payable(msg.sender), false);
        emit ProductCreated(productCount, _name, _price, msg.sender);
    }

    function purchaseProduct(uint _id) public payable{
        Product memory _product = products[_id];
        
        require(_product.id > 0 && _product.id <= productCount, "Product not found");
        require(msg.value >= _product.price, "Not enough Ether");
        require(!_product.purchased, "Product already purchased");

        _product.owner.transfer(msg.value);
        _product.purchased = true;
        products[_id] = _product;

        emit ProductPurchased(_id, msg.sender);
    }

}