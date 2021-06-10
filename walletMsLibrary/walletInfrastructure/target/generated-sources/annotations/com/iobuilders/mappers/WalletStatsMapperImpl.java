package com.iobuilders.mappers;

import com.iobuilders.data.WalletStatsDto;
import com.iobuilders.data.WalletStatsDto.WalletStatsDtoBuilder;
import com.iobuilders.entity.WalletStats;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-09T00:54:18+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
public class WalletStatsMapperImpl implements WalletStatsMapper {

    @Override
    public WalletStatsDto walletStatsToDto(WalletStats walletStats) {
        if ( walletStats == null ) {
            return null;
        }

        WalletStatsDtoBuilder walletStatsDto = WalletStatsDto.builder();

        walletStatsDto.id( walletStats.getId() );
        walletStatsDto.name( walletStats.getName() );
        walletStatsDto.etherBalance( walletStats.getEtherBalance() );
        walletStatsDto.tokenBalance( walletStats.getTokenBalance() );
        walletStatsDto.account( walletStats.getAccount() );

        return walletStatsDto.build();
    }

    @Override
    public WalletStats walletStatsDtoToWalletStats(WalletStatsDto walletStats) {
        if ( walletStats == null ) {
            return null;
        }

        WalletStats walletStats1 = new WalletStats();

        walletStats1.setName( walletStats.getName() );
        walletStats1.setEtherBalance( walletStats.getEtherBalance() );
        walletStats1.setTokenBalance( walletStats.getTokenBalance() );
        walletStats1.setId( walletStats.getId() );
        walletStats1.setAccount( walletStats.getAccount() );

        return walletStats1;
    }

    @Override
    public List<WalletStatsDto> walletStatsListToDtoList(List<WalletStats> listWallet) {
        if ( listWallet == null ) {
            return null;
        }

        List<WalletStatsDto> list = new ArrayList<WalletStatsDto>( listWallet.size() );
        for ( WalletStats walletStats : listWallet ) {
            list.add( walletStatsToDto( walletStats ) );
        }

        return list;
    }

    @Override
    public List<WalletStats> walletStatsDtoListToList(List<WalletStatsDto> listWallet) {
        if ( listWallet == null ) {
            return null;
        }

        List<WalletStats> list = new ArrayList<WalletStats>( listWallet.size() );
        for ( WalletStatsDto walletStatsDto : listWallet ) {
            list.add( walletStatsDtoToWalletStats( walletStatsDto ) );
        }

        return list;
    }
}
