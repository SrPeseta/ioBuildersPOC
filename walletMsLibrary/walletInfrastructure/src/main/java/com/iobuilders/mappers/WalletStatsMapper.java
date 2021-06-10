package com.iobuilders.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.iobuilders.data.WalletStatsDto;
import com.iobuilders.entity.WalletStats;

@Mapper
public interface WalletStatsMapper {
	WalletStatsMapper INSTANCE = Mappers.getMapper(WalletStatsMapper.class);

	WalletStatsDto walletStatsToDto(WalletStats walletStats);
	WalletStats walletStatsDtoToWalletStats(WalletStatsDto walletStats);
	List<WalletStatsDto> walletStatsListToDtoList(List<WalletStats> listWallet);
	List<WalletStats> walletStatsDtoListToList(List<WalletStatsDto> listWallet);
}
