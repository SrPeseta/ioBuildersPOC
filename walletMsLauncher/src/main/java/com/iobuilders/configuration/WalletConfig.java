package com.iobuilders.configuration;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.iobuilders.adapters.WalletEthereumAdapter;
import com.iobuilders.adapters.WalletJpaAdapter;
import com.iobuilders.entity.EIP20;
import com.iobuilders.ports.api.WalletServicePort;
import com.iobuilders.ports.spi.WalletBlockchainPort;
import com.iobuilders.ports.spi.WalletPersistencePort;
import com.iobuilders.service.WalletServiceImpl;

@Configuration
public class WalletConfig {
	
	@Value("${contract.addr}")
	private String adress;
	
	@Value("${contract.privateKey}")
	private String privateKey;
	
	@Value("${web3j.client-address}")
	private String ethUrl;
	
	@Value("${gasLimit}")
	private int gasLimit;
	
	@Value("${gasPrice}")
	private String gasPrice;
	
	@Bean
	public Credentials createContractCreds() {
		return Credentials.create(privateKey);
	}
	
	@Bean
	public ContractGasProvider createProvider() {
		return new StaticGasProvider(new BigInteger(gasPrice, 16),BigInteger.valueOf(gasLimit));
	}
	
	@Bean
	public Web3j createWeb3j() {
		return Web3j.build(new HttpService(ethUrl));
	}

	@Bean
	public EIP20 blockchainContract() {
		return EIP20.load(adress, createWeb3j(), createContractCreds(), createProvider());
	}
	
	@Bean
	public WalletBlockchainPort blockChainPort() {
		return new WalletEthereumAdapter(blockchainContract(),createWeb3j(),createContractCreds());
	}
	
	@Bean
    public WalletPersistencePort persistencePort(){
        return new WalletJpaAdapter();
    }
	
	@Bean
	public WalletServicePort walletService() {
		return new WalletServiceImpl(persistencePort(),blockChainPort());
	}
}
