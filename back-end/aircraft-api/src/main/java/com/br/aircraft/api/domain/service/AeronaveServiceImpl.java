package com.br.aircraft.api.domain.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.aircraft.api.assembler.AeronaveDtoAssembler;
import com.br.aircraft.api.assembler.AeronaveInputDissasembler;
import com.br.aircraft.api.domain.dto.AeronaveDTO;
import com.br.aircraft.api.domain.dto.input.AeronaveInput;
import com.br.aircraft.api.domain.exception.AeronaveMarcaInvalidException;
import com.br.aircraft.api.domain.exception.AeronaveNotFoundException;
import com.br.aircraft.api.domain.model.Aeronave;
import com.br.aircraft.api.domain.model.EnumMarca;
import com.br.aircraft.api.domain.repository.AeronaveRepository;

@Service
public class AeronaveServiceImpl implements AeronaveService{
	
	private static final String MSG_AERONAVE_NAO_ENCOTNADA = "Não existe um cadastro de aeronave com código %d";
	private static final String MSG_AERONAVE_MARCA_ERRADA = "O nome da marca '%s' foi digitado incorretamente, por favor corrija e tente novamente. ";
	
	@Autowired
	private AeronaveRepository aeronaveRepository;
	
	@Autowired
	private AeronaveInputDissasembler aeronaveInputDissasembler;
	
	@Autowired
	private AeronaveDtoAssembler aeronaveDtoAssembler;
	
	@Override
	@Transactional
	public List<AeronaveDTO> findAll() {
		return aeronaveDtoAssembler.toCollectionModel(aeronaveRepository.findAll());
	}	
	
	@Override
	@Transactional
	public AeronaveDTO findById(Long id) {
		return aeronaveDtoAssembler.toModel(BuscarOuFalhar(id));
	}
	
	@Override
	@Transactional
	public AeronaveDTO save(AeronaveInput aeronaveInput) {
		Aeronave aeronave = aeronaveInputDissasembler.toDomainObject(aeronaveInput);
		
		if (EnumMarca.validarMarca(aeronaveInput.getMarca())) {
			return  aeronaveDtoAssembler.toModel(aeronaveRepository.save(aeronave));
		}
		
		throw new AeronaveMarcaInvalidException(String.format(MSG_AERONAVE_MARCA_ERRADA, aeronaveInput.getMarca()));
	}
	
	@Override
	@Transactional
	public AeronaveDTO update(Long id, AeronaveInput aeronaveInput) {
		Aeronave aeronaveAtual = BuscarOuFalhar(id);
		aeronaveInputDissasembler.copyToDomainObject(aeronaveInput, aeronaveAtual);
		return  aeronaveDtoAssembler.toModel(aeronaveRepository.save(aeronaveAtual));
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		try {
		 aeronaveRepository.deleteById(id);
		
		}catch(EmptyResultDataAccessException e){
			throw new AeronaveNotFoundException(String.format(MSG_AERONAVE_NAO_ENCOTNADA, id)); 
			
		}
	}
	
	public Aeronave BuscarOuFalhar(Long id) {
		return aeronaveRepository.findById(id).orElseThrow(
				() -> new AeronaveNotFoundException(String.format(MSG_AERONAVE_NAO_ENCOTNADA, id)));
	}

}