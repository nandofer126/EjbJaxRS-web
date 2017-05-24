package py.pol.una.ii.pw.data;

import java.io.InputStream;

import javax.ejb.Local;
import javax.ejb.Singleton;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

@Singleton
@Local
public class SessionesMyBatis {
	String resource = "mybatis/myBatisConfig.xml";
	InputStream inputStream;
	SqlSessionFactory sqlSessionFactory;
    public SessionesMyBatis(){
    	try{
    	   	inputStream = Resources.getResourceAsStream(resource);
        	sqlSessionFactory = 
        			new SqlSessionFactoryBuilder().build(inputStream);
    	}catch (Exception e) {
			e.printStackTrace();
			
		}
 
    }
    //obterner sessiones
    public SqlSession getSession(){
    	return sqlSessionFactory.openSession();
    }
    
	
	
}
