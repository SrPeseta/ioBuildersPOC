package com.iobuilders.ports.spi;

import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.iobuilders.data.WalletDto;
import com.iobuilders.data.WalletStatsDto;

public interface WalletBlockchainPort {
	WalletDto createWallet();
	WalletDto createWalletWithKey(String key);
	WalletStatsDto getWalletStats(WalletDto wallet);
	BigInteger getBalance(WalletDto wallet);
	BigInteger getEtherBalance(WalletDto wallet);
	TransactionReceipt deposit(WalletDto wallet,BigInteger amount);
	TransactionReceipt withdraw(WalletDto wallet,BigInteger amount);
	TransactionReceipt transfer(WalletDto from,String to,BigInteger amount);
	BigInteger getContractBalance();
	List<EthBlock.TransactionResult> getWalletTransactions(WalletDto wallet);
}
