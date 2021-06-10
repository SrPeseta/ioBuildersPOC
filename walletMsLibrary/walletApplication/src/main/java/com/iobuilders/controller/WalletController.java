package com.iobuilders.controller;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.iobuilders.data.WalletDto;
import com.iobuilders.data.WalletStatsDto;
import com.iobuilders.ports.api.WalletServicePort;

@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	Logger logger = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	private WalletServicePort walletPort;
	
	@GetMapping(path = "/create",produces = "application/json")
	public WalletDto createWallet() {
		logger.info("--> createWallet");
		return walletPort.createWallet();
	}
	
	@PostMapping(path = "/createWalletWithKey", produces = "application/json")
	public WalletDto createWalletWIthKey(@RequestBody String  key) {
		logger.info("--> createWalletWIthKey");
		return walletPort.createWalletWithKey(key);
	}
	
	@GetMapping(path = "/get/{id}",produces = "application/json") //Balance y movimientos
	public @ResponseBody WalletStatsDto getWalletStats(@PathVariable String id) {
		logger.info("--> getWalletStats " + id);
		long idLong = Long.parseLong(id);
		
		return walletPort.getWalletStats(idLong);
	}
	
	@GetMapping(path = "/getContractBalance",produces = "application/json")
	public @ResponseBody BigInteger getContractBalance() {
		return walletPort.getContractBalance();
	}
	
	@GetMapping(path = "/getAllWallets",produces = "application/json")
	public List<WalletDto> getAllWallets() {
		logger.info("--> getAllWallets");
		return walletPort.getAllWallets();
	}
	
	@GetMapping(path = "/deposit",produces = "application/json")
	public TransactionReceipt depositFunds(@RequestParam(value = "id")String idStr,@RequestParam(value = "amount")String amount) {
		logger.info("--> depositFunds");
		long id = Long.parseLong(idStr);
		BigInteger amountBig = new BigInteger(amount);
		return walletPort.depositFunds(id,amountBig);
	} 
	
	@GetMapping(path = "/withdraw", produces = "application/json")
	public TransactionReceipt withdrawFunds(@RequestParam(value = "id")String idStr,@RequestParam(value = "amount")String amount) {
		logger.info("--> withdrawFunds");
		long id = Long.parseLong(idStr);
		BigInteger amountBig = new BigInteger(amount);
		return walletPort.withdrawFunds(id, amountBig);
	} 
	
	@GetMapping(path = "/transfer",produces = "application/json")
	public TransactionReceipt transferFunds(@RequestParam(value = "from")String idStr,@RequestParam(value = "to") String to,@RequestParam(value = "amount")String amount) {
		logger.info("--> transferFunds");
		long from = Long.parseLong(idStr);
		BigInteger amountBig = new BigInteger(amount);
		return walletPort.transferFunds(from, to, amountBig);
	}
}
