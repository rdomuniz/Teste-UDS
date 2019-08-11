package br.com.teste;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({ 
	DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
public class GeralTests {

	@PersistenceContext
	protected EntityManager entityManager;

	@Before
	public void setDefaultUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	protected Date getData(String value) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(value);
		} catch (Exception e) {}
		return null;
	}
	
	protected Date getDataComHora(String value) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(value);
		} catch (Exception e) {}
		return null;
	}
	
}
