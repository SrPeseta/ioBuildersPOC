package com.iobuilders.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.iobuilders.data.WalletDto;
import com.iobuilders.data.WalletStatsDto;
import com.iobuilders.entity.EIP20;
import com.iobuilders.ports.spi.WalletBlockchainPort;

import okhttp3.RequestBody;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;



@Service
public class WalletEthereumAdapter implements WalletBlockchainPort{

	
	Logger logger = LoggerFactory.getLogger(WalletEthereumAdapter.class);
	private EIP20 contract;

	private Web3j web3;
	
	@Value("${contract.addr}")
	private String contractAdress;
	
	@Value("${gasLimit}")
	private int gasLimitProperty;
	
	@Value("${gasPrice}")
	private String gasPriceProperty;
	
	private Credentials contractDeployer;
	
	public WalletEthereumAdapter(EIP20 contract,Web3j web3,Credentials contractDeployer) {
		this.contract = contract;
		this.web3 = web3;
		this.contractDeployer = contractDeployer;
	}
	
	@Override
	public WalletDto createWallet() {
		WalletDto retorno = new WalletDto();
		try {
	        ECKeyPair keyPair = Keys.createEcKeyPair();
	        
	        retorno.setPrivateKey(keyPair.getPrivateKey().toString());

	    } catch(Exception e) {
	        System.err.println("Error: " + e.getMessage());
	    }
		
		
		
		return retorno;
	}
	
	@Override
	public WalletDto createWalletWithKey(String key) {
		WalletDto retorno = new WalletDto();
		try {
	        
	        retorno.setPrivateKey(key);

	    } catch(Exception e) {
	        System.err.println("Error: " + e.getMessage());
	    }
		
		
		
		return retorno;
	}

	@Override
	public WalletStatsDto getWalletStats(WalletDto wallet) {
		WalletStatsDto dto = new WalletStatsDto();
		Credentials cres = Credentials.create(wallet.getPrivateKey());
		dto.setTokenBalance(this.getBalance(wallet));
		dto.setEtherBalance(this.getEtherBalance(wallet));
		dto.setName(wallet.getName());
		dto.setId(wallet.getId());
		dto.setAccount(cres.getAddress());
		return dto;
	}

	@Override
	public BigInteger getBalance(WalletDto wallet) {
		BigInteger value = null;
		Credentials creds = Credentials.create(wallet.getPrivateKey());
		try {
			value = contract.balanceOf(creds.getAddress()).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public TransactionReceipt deposit(WalletDto wallet, BigInteger amount) {
		TransactionReceipt receipt = null;
		try {
			Credentials creds = Credentials.create(wallet.getPrivateKey().toString());
			receipt = contract.transfer(creds.getAddress(),amount).send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(receipt != null)
			System.out.println(receipt.getTransactionHash());
		
		return receipt;
	}

	@Override
	public TransactionReceipt withdraw(WalletDto wallet, BigInteger amount) {
		Optional<TransactionReceipt> transactionReceipt = null;
		
		Credentials creds = Credentials.create(wallet.getPrivateKey().toString());
		
		
		// get the next available nonce
	    EthGetTransactionCount ethGetTransactionCount;
		try {
			ethGetTransactionCount = web3.ethGetTransactionCount(creds.getAddress(), DefaultBlockParameterName.LATEST).send();
			
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		    
		    // create transfer function
		    String encodedFunction = contract.transfer(contractDeployer.getAddress(), amount).encodeFunctionCall();
		    // Gas Parameters
		    BigInteger gasLimit = BigInteger.valueOf(gasLimitProperty); // you should get this from api
		    BigInteger gasPrice = new BigInteger(gasPriceProperty, 16); // decimal 3600000000
		    
		    // create the transaction
		    RawTransaction rawTransaction =
		            RawTransaction.createTransaction(
		                    nonce, gasPrice, gasLimit, contractAdress, encodedFunction);
		    
		    // sign the transaction
		    byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, creds);
		    String hexValue = Numeric.toHexString(signedMessage);
		    
		    // Send transaction
		    EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
		    String transactionHash = ethSendTransaction.getTransactionHash();
		    
		    do {
		      EthGetTransactionReceipt ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash).send();
		      transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();

		      Thread.sleep(3000); // Retry after 3 sec
		    } while(!transactionReceipt.isPresent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		return transactionReceipt.get();
	}

	@Override
	public TransactionReceipt transfer(WalletDto from, String to, BigInteger amount) {
		Optional<TransactionReceipt> transactionReceipt = null;
		try {
			Credentials creds = Credentials.create(from.getPrivateKey().toString());
			// get the next available nonce
		    EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(creds.getAddress(), DefaultBlockParameterName.LATEST).send();
		    BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		    
		    // create transfer function
		    String encodedFunction = contract.transfer( to, amount).encodeFunctionCall();
		    
		    // Gas Parameters
		    BigInteger gasLimit = BigInteger.valueOf(gasLimitProperty);
		    BigInteger gasPrice = new BigInteger(gasPriceProperty, 16); // decimal 3600000000
		    
		    // create the transaction
		    RawTransaction rawTransaction =
		            RawTransaction.createTransaction(
		                    nonce, gasPrice, gasLimit, contractAdress, encodedFunction);
		    
		    // sign the transaction
		    byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, creds);
		    String hexValue = Numeric.toHexString(signedMessage);
		    
		    // Send transaction
		    EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
		    String transactionHash = ethSendTransaction.getTransactionHash();
		    
		    do {
			      EthGetTransactionReceipt ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash).send();
			      transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();

			      Thread.sleep(3000); // Retry after 3 sec
			    } while(!transactionReceipt.isPresent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactionReceipt.get();
	}

	@Override
	public BigInteger getEtherBalance(WalletDto wallet) {
		EthGetBalance balanceResult = null;
		BigInteger balanceInWei = null;
		try {
			Credentials creds = Credentials.create(wallet.getPrivateKey().toString());
			balanceResult = web3.ethGetBalance(creds.getAddress(), 
				    DefaultBlockParameterName.LATEST).send();
			balanceInWei = balanceResult.getBalance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			//Obtain the BigInteger balance representation, in the wei unit.
			
		return balanceInWei;
	}

	@Override
	public BigInteger getContractBalance() {
		BigInteger value = null;
		try {
			value = contract.balanceOf(contractDeployer.getAddress()).send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public List<EthBlock.TransactionResult> getWalletTransactions(WalletDto wallet) {
		List<EthBlock.TransactionResult> finalTxs = new ArrayList<>();
		try {
			EthBlockNumber blocknum = web3.ethBlockNumber().send();
			BigInteger n = blocknum.getBlockNumber();
			Credentials creds = Credentials.create(wallet.getPrivateKey().toString());
			
			for(BigInteger i = BigInteger.valueOf(0); n.compareTo(i) >= 0; i = i.add(BigInteger.ONE)) {
				logger.info("Iterating block " + i);
				DefaultBlockParameter defaultBlockParameter = DefaultBlockParameter.valueOf(i);
				EthBlock block = web3.ethGetBlockByNumber(defaultBlockParameter , true).send();
				List<EthBlock.TransactionResult> transactions = block.getResult().getTransactions();
				transactions.forEach(tx -> {
					  EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();

					  if(transaction.getFrom().equalsIgnoreCase(creds.getAddress()) || transaction.getTo().equalsIgnoreCase(creds.getAddress()))
						  finalTxs.add(tx);
					});
			}
		} catch (IOException e) {
			logger.error("Error en getWalletTransactions",e);
		}
		return finalTxs;
	}
}
