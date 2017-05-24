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
import py.pol.una.ii.pw.model.Productos;;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MyControladorProductos {

	@Inject
	SessionesMyBatis sesiones;
	
	public void eliminar (Productos producto) throws IOException{
    	/*String resource = "mybatis/myBatisConfig.xml";
    	InputStream inputStream;
	    inputStream = Resources.getResourceAsStream(resource);
    	SqlSessionFactory sqlSessionFactory = 
    			new SqlSessionFactoryBuilder().build(inputStream);
    	SqlSession session = sqlSessionFactory.openSession();*/
		SqlSession session = sesiones.getSession();
		try{
    			session.selectOne("mybatis.ProductoMapper.removeProducto",producto.getId());
    	}finally{
    		session.close();
    	}
    	
    }
    
    public void register (Productos producto) throws IOException{
    	/*String resource = "mybatis/myBatisConfig.xml";
    	InputStream inputStream;
	    inputStream = Resources.getResourceAsStream(resource);
    	SqlSessionFactory sqlSessionFactory = 
    			new SqlSessionFactoryBuilder().build(inputStream);
    	SqlSession session = sqlSessionFactory.openSession();*/
    	SqlSession session = sesiones.getSession();
    	try{
    			session.selectOne("mybatis.ProductoMapper.createProducto",producto);
    	}finally{
    		session.close();
    	}
    }
    
    public void actualizar (Productos producto) throws IOException{
    	/*String resource = "mybatis/myBatisConfig.xml";
    	InputStream inputStream;
	    inputStream = Resources.getResourceAsStream(resource);
    	SqlSessionFactory sqlSessionFactory = 
    			new SqlSessionFactoryBuilder().build(inputStream);
    	SqlSession session = sqlSessionFactory.openSession();*/
    	SqlSession session = sesiones.getSession();
    	try{
    			session.selectOne("mybatis.ProductoMapper.updateProducto", producto);
    	}finally{
    		session.close();
    	}	
    }

	
}
