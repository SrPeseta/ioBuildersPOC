package com.iobuilders.ports.api;

import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.iobuilders.data.WalletDto;
import com.iobuilders.data.WalletStatsDto;

public interface WalletServicePort {

	WalletDto createWallet();
	
	WalletDto createWalletWithKey(String key);
	
	List<WalletDto> getAllWallets();

	WalletStatsDto getWalletStats(long id);

	TransactionReceipt depositFunds(long id, BigInteger amount);

	TransactionReceipt withdrawFunds(long id, BigInteger amount);

	TransactionReceipt transferFunds(long id, String to, BigInteger amount);

	BigInteger getContractBalance();

	List<EthBlock.TransactionResult> getWalletTransactions(long idLong);

}
