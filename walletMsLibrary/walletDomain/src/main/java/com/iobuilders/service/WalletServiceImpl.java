package com.iobuilders.service;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.iobuilders.data.WalletDto;
import com.iobuilders.data.WalletStatsDto;
import com.iobuilders.ports.api.WalletServicePort;
import com.iobuilders.ports.spi.WalletBlockchainPort;
import com.iobuilders.ports.spi.WalletPersistencePort;

public class WalletServiceImpl implements WalletServicePort{

	Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);
	
	private WalletPersistencePort walletPersistentPort;
	private WalletBlockchainPort walletBlockchainPort;
	
	public WalletServiceImpl(WalletPersistencePort walletPersistentPort, WalletBlockchainPort walletBlockchainPort) {
		this.walletPersistentPort = walletPersistentPort;
		this.walletBlockchainPort = walletBlockchainPort;
	}
	
	@Override
	public WalletDto createWallet() {
		WalletDto wallet = walletBlockchainPort.createWallet();
		
		wallet.setName("Wallet " + walletPersistentPort.getWalletCount());
		walletPersistentPort.addWallet(wallet);
		return wallet;
	}
	
	@Override
	public WalletDto createWalletWithKey(String key) {
		WalletDto wallet = walletBlockchainPort.createWalletWithKey(key);
		
		if(wallet == null) {
			logger.error("Invalid wallet key");
			return null;
		}
		
		wallet.setName("Wallet " + walletPersistentPort.getWalletCount());
		walletPersistentPort.addWallet(wallet);
		return wallet;
	}

	@Override
	public WalletStatsDto getWalletStats(long id) {
		WalletDto wallet = walletPersistentPort.getWalletById(id);
		if(wallet == null) {
			logger.error("Invalid wallet id");
			return null;
		}
			
		return walletBlockchainPort.getWalletStats(wallet);
	}

	@Override
	public TransactionReceipt depositFunds(long id,BigInteger amount) {
		WalletDto wallet = walletPersistentPort.getWalletById(id);
		if(wallet == null) {
			logger.error("Invalid wallet id");
			return null;
		}
		return walletBlockchainPort.deposit(wallet, amount);
	}

	@Override
	public TransactionReceipt withdrawFunds(long id,BigInteger amount) {
		WalletDto wallet = walletPersistentPort.getWalletById(id);
		if(wallet == null) {
			logger.error("Invalid wallet id");
			return null;
		}
		return walletBlockchainPort.withdraw(wallet, amount);
	}

	@Override
	public TransactionReceipt transferFunds(long id, String to,BigInteger amount) {
		WalletDto from = walletPersistentPort.getWalletById(id);
		if(from == null) {
			logger.error("Invalid wallet id");
			return null;
		}
		return walletBlockchainPort.transfer(from, to, amount);
	}

	@Override
	public List<WalletDto> getAllWallets() {
		return walletPersistentPort.getWallets();
	}

	@Override
	public BigInteger getContractBalance() {
		return walletBlockchainPort.getContractBalance();
	}

	@Override
	public List<EthBlock.TransactionResult> getWalletTransactions(long idLong) {
		WalletDto wallet = walletPersistentPort.getWalletById(idLong);
		if(wallet == null) {
			logger.error("Invalid wallet id");
			return null;
		}
		return walletBlockchainPort.getWalletTransactions(wallet);
	}

}
