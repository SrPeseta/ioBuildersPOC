package com.iobuilders.data;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class WalletStatsDto {

	private Long id;
	private String name;
	private BigInteger etherBalance;
	private BigInteger tokenBalance;
	private String account;
	
	public WalletStatsDto( Long id, String name, BigInteger etherBalance, BigInteger tokenBalance,String account) {
		super();
		this.id = id;
		this.name = name;
		this.etherBalance = etherBalance;
		this.tokenBalance = tokenBalance;
		this.setAccount(account);
	}
	
	public WalletStatsDto() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigInteger getEtherBalance() {
		return etherBalance;
	}
	public void setEtherBalance(BigInteger etherBalance) {
		this.etherBalance = etherBalance;
	}
	public BigInteger getTokenBalance() {
		return tokenBalance;
	}
	public void setTokenBalance(BigInteger tokenBalance) {
		this.tokenBalance = tokenBalance;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
