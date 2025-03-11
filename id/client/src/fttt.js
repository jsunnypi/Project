
const App = () => {
  const [account, setAccount] = useState("");
  const [contract, setContract] = useState(null);
  const [blocks, setBlocks] = useState([]); // 블록 상태 관리
  const [selectedUser, setSelectedUser] = useState(null); // 선택된 사용자
  const [searchedBlock, setSearchedBlocks] = useState([]);
  const [nickName, setNickName] = useState("");
  const [userTokenBalance, setUserTokenBalance] = useState(0); // 유저의 토큰 잔액 상태 관리

  // 메타마스크 및 스마트 계약 초기화
  useEffect(() => {
    const initWeb3 = async () => {
      if (window.ethereum) {
        const web3 = new Web3(window.ethereum);
        try {
          await window.ethereum.request({ method: "eth_requestAccounts" });
          const accounts = await web3.eth.getAccounts();
          if (accounts.length > 0) {
            const account = accounts[0];
            setAccount(account);
            const networkId = await web3.eth.net.getId();
            const deployedNetwork = IdProject.networks[networkId];
            const instance = new web3.eth.Contract(
              IdProject.abi,
              deployedNetwork.address
            );

            setContract(instance);

            // 계약에서 직접 데이터 불러오기
            fetchBlocks(instance, account);
            fetchTokenBalance(instance, account); // 사용자 잔액 불러오기
          } else {
            console.error("MetaMask 계정을 찾을 수 없습니다.");
          }
        } catch (error) {
          console.error("Web3 초기화 중 오류 발생:", error);
          alert(
            "MetaMask와의 연결에 문제가 발생했습니다. MetaMask가 설치되어 있고, 계정이 연결되었는지 확인해 주세요."
          );
        }
      } else {
        console.error("Ethereum object not found. Please install MetaMask.");
        alert("MetaMask가 설치되어 있지 않습니다. MetaMask를 설치해 주세요.");
      }
    };
    initWeb3();
    // 새로고침 시 로컬 스토리지에서 blocks 데이터 불러오기
    const savedBlocks = localStorage.getItem("blocks");
    if (savedBlocks) {
      setBlocks(JSON.parse(savedBlocks));
    }
  }, []);

  // blocks 상태 업데이트 시 로컬 스토리지에 저장
  useEffect(() => {
    if (blocks.length > 0) {
      localStorage.setItem("blocks", JSON.stringify(blocks));
    }
  }, [blocks]);

  // 블록 정보 가져오기 (스마트 계약에서 실시간으로 블록 데이터 불러오기)
  const fetchBlocks = async (contract, account) => {
    try {
      if (contract && account) {
        const blockCount = await contract.methods.getIndexOf(account).call();

        if (blockCount.length === 0n) {
          console.log("No blocks available.");
          return;
        }

        const fetchedBlockResults = [];
        for (let i = 0; i < blockCount.length; i++) {
          const block = await contract.methods
            .getInfo(account, blockCount[i])
            .call();
          fetchedBlockResults.push({
            nickName: block[0],
            gameName: block[1],
            serverName: block[2],
            className: block[3],
            remarks: block[4],
            date: new Date(Number(block[5]) * 1000).toLocaleString(),
          });
          if (i + 1 === blockCount.length) setNickName(block[0]);
        }

        setBlocks(fetchedBlockResults.reverse());
      }
    } catch (error) {
      console.error("블록 정보 불러오기 중 오류 발생:", error);
    }
  };

  // 사용자의 토큰 잔액 가져오기
  const fetchTokenBalance = async (contract, account) => {
    try {
      if (contract && account) {
        const balance = await contract.methods.getTokenBalance(account).call();
        setUserTokenBalance(balance);
      }
    } catch (error) {
      console.error("사용자 토큰 잔액 조회 중 오류 발생:", error);
    }
  };

  // 선택된 사용자 상태 업데이트
  const handleUserSelect = (user) => {
    setSelectedUser(user);
    fetchSearchedBlocks(user.userAddress);
  };

  const fetchSearchedBlocks = async (account) => {
    try {
      if (contract && account) {
        const blockCount = await contract.methods.getIndexOf(account).call();
        if (blockCount.length === 0n) {
          console.log("No blocks available.");
          return;
        }

        const fetchedBlockResults = [];
        for (let i = 0; i < blockCount.length; i++) {
          const block = await contract.methods
            .getInfo(account, blockCount[i])
            .call();
          fetchedBlockResults.push({
            nickName: block[0],
            gameName: block[1],
            serverName: block[2],
            className: block[3],
            remarks: block[4],
            date: new Date(Number(block[5]) * 1000).toLocaleString(),
          });
        }

        setSearchedBlocks(fetchedBlockResults.reverse());
      }
    } catch (error) {
      console.error("블록 정보 불러오기 중 오류 발생:", error);
    }
  };

  // 사용자 선택 취소 (검색 화면으로 돌아가기)
  const handleBackToSearch = () => {
    setSelectedUser(null);
  };

  // 채굴 버튼 클릭 시 사용자에게 토큰 전달
  const handleMine = async () => {
    if (contract && account) {
      try {
        // 발행자가 토큰을 사용자에게 지급하는 mint 함수 호출
        const gasPrice = Web3.utils.toWei("20", "gwei");
        await contract.methods
          .mine(account, Web3.utils.toWei("1", "ether"))
          .send({ from: account, gas: 5000000, gasPrice });
        alert("1 WID 토큰이 사용자에게 전달되었습니다.");
        // 토큰 잔액 새로고침
        fetchTokenBalance(contract, account);
      } catch (error) {
        console.error("토큰 채굴 중 오류 발생:", error);
        alert("토큰 채굴에 실패했습니다.");
      }
    } else {
      alert("계정을 연결할 수 없습니다.");
    }
  };

  return (
    <div className="main-container">
      <Header />
      <h1
        style={{
          textAlign: "center",
          margin: 0,
          padding: 0,
          marginBottom: "15px",
          marginTop: "15px",
        }}
      >
        {" "}
        Welcome, {nickName}!{" "}
      </h1>
      {!selectedUser ? (
        <Search contract={contract} onUserSelect={handleUserSelect} />
      ) : (
        <div className="user-history">
          <h2>
            {selectedUser.nickName}님의 기록{" "}
            <button onClick={handleBackToSearch} className="back-button" />
          </h2>

          <History user={selectedUser} blocks={searchedBlock} />
        </div>
      )}
      {!selectedUser && (
        <>
          <CreateBlock
            contract={contract}
            account={account}
            blocks={blocks}
            setBlocks={setBlocks}
            fetchBlocks={fetchBlocks}
          />
          <History blocks={blocks} />
        </>
      )}
      <Footer />
      <Stats />
      <div className="button-container">
        <ChatBTN nickName={nickName} />
        <HelpBTN />
        {/* 채굴 버튼 및 사용자 토큰 잔액 */}
        <div className="mining-section">
          <button
            className="mining-button"
            onClick={handleMine}
            style={{ position: "absolute", top: "20px", left: "20px" }}
          >
          </button>
          <p>보유한 토큰: {userTokenBalance} WID</p>          
        </div>
      </div>
    </div>
  );
};

export default App;
