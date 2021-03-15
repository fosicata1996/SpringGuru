package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;

public class UnitOfMeasureServiceImplTest
{
	UnitOfMeasureToUnitOfMeasureCommand converter = new UnitOfMeasureToUnitOfMeasureCommand();
	UnitOfMeasureService service;
	
	@Mock
	UnitOfMeasureRepository repository;
	
	@BeforeEach
	void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		service = new UnitOfMeasureServiceImpl(repository, converter);
	}
	
	@Test
	void listAllUsers()
	{
		Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
		
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		unitOfMeasures.add(uom1);
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		unitOfMeasures.add(uom2);
		
		when(repository.findAll()).thenReturn(unitOfMeasures);
		
		Set<UnitOfMeasureCommand> commands = service.listAllUoms();
		
		assertEquals(2, commands.size());
		verify(repository).findAll();
	}
}
