package com.iobuilders.data;


import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class WalletDto {

	private Long id;
	private String privateKey;
	private String name;
	
	public WalletDto( Long id, String privateKey, String name) {
		super();
		this.id = id;
		this.privateKey = privateKey;
		this.name = name;
	}
	
	public WalletDto() {
		super();
	}
	
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
