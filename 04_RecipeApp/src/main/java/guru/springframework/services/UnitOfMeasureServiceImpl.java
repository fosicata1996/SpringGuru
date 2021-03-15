package guru.springframework.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService
{
	private final UnitOfMeasureToUnitOfMeasureCommand converter;
	private final UnitOfMeasureRepository repository;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository repository, UnitOfMeasureToUnitOfMeasureCommand converter)
	{
		this.repository = repository;
		this.converter = converter;
	}
	
	@Override
	public Set<UnitOfMeasureCommand> listAllUoms()
	{
		return StreamSupport.stream(repository.findAll().spliterator(), false)
				.map(converter::convert)
				.collect(Collectors.toSet());
	}
	
}
