package com.iobuilders.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iobuilders.data.WalletDto;
import com.iobuilders.entity.Wallet;
import com.iobuilders.mappers.WalletMapper;
import com.iobuilders.ports.spi.WalletPersistencePort;
import com.iobuilders.repository.WalletRepository;

@Service
public class WalletJpaAdapter implements WalletPersistencePort{
	
	@Autowired
	private WalletRepository repository;

	@Override
	public WalletDto addWallet(WalletDto dto) {
		Wallet wallet = WalletMapper.INSTANCE.walletDtoToWallet(dto);

        Wallet walletSaved = repository.save(wallet);

        return WalletMapper.INSTANCE.walletToDto(walletSaved);
	}

	@Override
	public void deleteWalletById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WalletDto updateWallet(WalletDto bookDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WalletDto> getWallets() {
		List<Wallet> lista = repository.findAll();
		List<WalletDto> listaDto = null;
		if(lista != null)
			listaDto = WalletMapper.INSTANCE.walletListToDtoList(lista);
		return listaDto;
	}

	@Override
	public WalletDto getWalletById(long id) {
		Optional<Wallet> entityOpt = repository.findById(id);
		Wallet entity = null;
		WalletDto dto = null;
		
		if(entityOpt.isPresent()) {
			entity = entityOpt.get();
			dto = WalletMapper.INSTANCE.walletToDto(entity);
		}
		
		
		return dto;
	}

	@Override
	public long getWalletCount() {
		return repository.count();
	}

}
