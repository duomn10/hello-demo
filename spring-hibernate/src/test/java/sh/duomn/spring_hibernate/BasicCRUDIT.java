package sh.duomn.spring_hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sh.duomn.spring_hibernate.example.model.MyEntity;
import sh.duomn.spring_hibernate.example.service.EntityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class BasicCRUDIT {
	
	private final static Logger logger = Logger.getLogger("TEST");

	private EntityService entityService;
	
	private static SessionFactory sf;
	
	@BeforeClass
	public static void beforeClass() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	@AfterClass
	public static void afterClass() {
		sf.close();
	}
	
	
	@Test
	public void testEntityCRUD() {
		
		String entityId = null;

		try {
			// This little trick turns on traces for both the connector *and* the
			// entity manager because they share the same underlying connection object.
			// When trace is on, you'll see the SOAP requests and responses on stdout.

			MyEntity entity = new MyEntity();
			entity.setName("A Test Entity");

			entityService.save(entity);
			
			assertNotNull(entity.getId());
			entityId = entity.getId();

			entity = entityService.findEntity(entityId);
			
			assertEquals("A Test Entity", entity.getName());
			
			entity.setName("A Modified Test Entity");
			
			entityService.save(entity);

			entity = entityService.findEntity(entityId);
			
			assertEquals("A Modified Test Entity",entity.getName());

			entityService.delete(entityId);
			
			entity = entityService.findEntity(entityId);
			
			assertNull(entity);
			
			// In case the entity is not null, the finally block will try to clean up using
			// the native connection
			if(entity==null) entityId = null;
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(entityId!=null) {
				try {
					
					sf.getCurrentSession().delete(new String[] {entityId });
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
