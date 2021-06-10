package com.iobuilders.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.iobuilders.data.WalletDto;
import com.iobuilders.entity.Wallet;

@Mapper
public interface WalletMapper {
	WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

	WalletDto walletToDto(Wallet wallet);
	Wallet walletDtoToWallet(WalletDto wallet);
	List<WalletDto> walletListToDtoList(List<Wallet> listWallet);
	List<Wallet> walletDtoListToList(List<WalletDto> listWallet);
}
