package py.pol.una.ii.pw.service;

import java.io.IOException;
import java.io.InputStream;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import py.pol.una.ii.pw.data.SessionesMyBatis;
import py.pol.una.ii.pw.model.Proveedores;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MyControladorProveedores {
	
	@Inject
	SessionesMyBatis sesiones;

	public void eliminar (Proveedores proveedor) throws IOException{
    	/*String resource = "mybatis/myBatisConfig.xml";
    	InputStream inputStream;
	    inputStream = Resources.getResourceAsStream(resource);
    	SqlSessionFactory sqlSessionFactory = 
    			new SqlSessionFactoryBuilder().build(inputStream);
    	SqlSession session = sqlSessionFactory.openSession();*/
		SqlSession session = sesiones.getSession();
		try{
    			session.selectOne("mybatis.ProveedorMapper.removeProveedor",proveedor.getId());
    	}finally{
    		session.close();
    	}
    	
    }
    
    public void register (Proveedores proveedor) throws IOException{
    	String resource = "mybatis/myBatisConfig.xml";
    	InputStream inputStream;
	    inputStream = Resources.getResourceAsStream(resource);
    	SqlSessionFactory sqlSessionFactory = 
    			new SqlSessionFactoryBuilder().build(inputStream);
    	SqlSession session = sqlSessionFactory.openSession();
    	try{
    			//long id = session.selectOne("mybatis.ProveedorMapper.getIdProveedor");
    			//proveedor.setId(id);
    			session.selectOne("mybatis.ProveedorMapper.createProveedor",proveedor);
    	}finally{
    		session.close();
    	}
    }
    
    public void actualizar (Proveedores proveedor) throws IOException{
    	String resource = "mybatis/myBatisConfig.xml";
    	InputStream inputStream;
	    inputStream = Resources.getResourceAsStream(resource);
    	SqlSessionFactory sqlSessionFactory = 
    			new SqlSessionFactoryBuilder().build(inputStream);
    	SqlSession session = sqlSessionFactory.openSession();
    	try{
    			
    			session.selectOne("mybatis.ProveedorMapper.updateProveedor",proveedor);
    	}finally{
    		session.close();
    	}	
    }
  
}