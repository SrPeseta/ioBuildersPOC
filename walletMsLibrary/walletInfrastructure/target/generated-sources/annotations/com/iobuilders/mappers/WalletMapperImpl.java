package com.iobuilders.mappers;

import com.iobuilders.data.WalletDto;
import com.iobuilders.data.WalletDto.WalletDtoBuilder;
import com.iobuilders.entity.Wallet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-09T00:54:18+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
public class WalletMapperImpl implements WalletMapper {

    @Override
    public WalletDto walletToDto(Wallet wallet) {
        if ( wallet == null ) {
            return null;
        }

        WalletDtoBuilder walletDto = WalletDto.builder();

        walletDto.id( wallet.getId() );
        walletDto.privateKey( wallet.getPrivateKey() );
        walletDto.name( wallet.getName() );

        return walletDto.build();
    }

    @Override
    public Wallet walletDtoToWallet(WalletDto wallet) {
        if ( wallet == null ) {
            return null;
        }

        Wallet wallet1 = new Wallet();

        wallet1.setId( wallet.getId() );
        wallet1.setPrivateKey( wallet.getPrivateKey() );
        wallet1.setName( wallet.getName() );

        return wallet1;
    }

    @Override
    public List<WalletDto> walletListToDtoList(List<Wallet> listWallet) {
        if ( listWallet == null ) {
            return null;
        }

        List<WalletDto> list = new ArrayList<WalletDto>( listWallet.size() );
        for ( Wallet wallet : listWallet ) {
            list.add( walletToDto( wallet ) );
        }

        return list;
    }

    @Override
    public List<Wallet> walletDtoListToList(List<WalletDto> listWallet) {
        if ( listWallet == null ) {
            return null;
        }

        List<Wallet> list = new ArrayList<Wallet>( listWallet.size() );
        for ( WalletDto walletDto : listWallet ) {
            list.add( walletDtoToWallet( walletDto ) );
        }

        return list;
    }
}
