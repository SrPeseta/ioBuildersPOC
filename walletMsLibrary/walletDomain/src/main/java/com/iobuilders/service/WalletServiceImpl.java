package com.iobuilders.service;

import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.iobuilders.data.WalletDto;
import com.iobuilders.data.WalletStatsDto;
import com.iobuilders.ports.api.WalletServicePort;
import com.iobuilders.ports.spi.WalletBlockchainPort;
import com.iobuilders.ports.spi.WalletPersistencePort;

public class WalletServiceImpl implements WalletServicePort{

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
		
		wallet.setName("Wallet " + walletPersistentPort.getWalletCount());
		walletPersistentPort.addWallet(wallet);
		return wallet;
	}

	@Override
	public WalletStatsDto getWalletStats(long id) {
		WalletDto wallet = walletPersistentPort.getWalletById(id);
		return walletBlockchainPort.getWalletStats(wallet);
	}

	@Override
	public TransactionReceipt depositFunds(long id,BigInteger amount) {
		WalletDto wallet = walletPersistentPort.getWalletById(id);
		return walletBlockchainPort.deposit(wallet, amount);
	}

	@Override
	public TransactionReceipt withdrawFunds(long id,BigInteger amount) {
		WalletDto wallet = walletPersistentPort.getWalletById(id);
		return walletBlockchainPort.withdraw(wallet, amount);
	}

	@Override
	public TransactionReceipt transferFunds(long id, String to,BigInteger amount) {
		WalletDto from = walletPersistentPort.getWalletById(id);
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
		return walletBlockchainPort.getWalletTransactions(wallet);
	}

}
