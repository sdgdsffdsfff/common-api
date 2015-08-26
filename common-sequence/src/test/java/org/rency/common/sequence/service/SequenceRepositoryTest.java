package org.rency.common.sequence.service;


import org.junit.Before;
import org.junit.Test;
import org.rency.common.sequence.beans.Sequence;
import org.rency.common.utils.exception.CoreException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SequenceRepositoryTest {
	
	private ApplicationContext ctx;
	private SequenceRepository sequenceRepository;
	
	@Before
	public void before(){
		ctx = new FileSystemXmlApplicationContext("src/test/resources/applicationContext.xml");
		sequenceRepository = ctx.getBean(SequenceRepository.class);
	}
	
	@Test
	public void testSave() throws CoreException{
		Sequence seq = new Sequence();
		seq.setName("test");
		seq.setCurrentValue(10000000L);
		seq.setIncrement(1);
		seq.setTotal(20);
		seq.setThreshold(10);
		boolean saveResult = (sequenceRepository.find(seq.getName()) == null) ? sequenceRepository.save(seq) : true;
		System.out.println("junit sequence[test] save...:"+saveResult);
	}

	@Test
	public void test() {
		for(int i=0;i<10;i++){
			System.out.println(i+"---"+sequenceRepository.next("test"));
		}
		
	}
	
	@Test
	public void testDelete() throws CoreException{
		boolean deleteResult = sequenceRepository.delete("test");
		System.out.println("junit sequence[test] delete...:"+deleteResult);
	}

}
