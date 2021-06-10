package com.iobuilders.ports.spi;

import java.util.List;

import com.iobuilders.data.WalletDto;

public interface WalletPersistencePort {

	WalletDto addWallet(WalletDto dto);
	
	void deleteWalletById(Long id);

	WalletDto updateWallet(WalletDto dto);

    List<WalletDto> getWallets();

    WalletDto getWalletById(long id);
    
    long getWalletCount();
}
