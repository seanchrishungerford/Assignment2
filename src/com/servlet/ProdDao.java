package com.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.product.Product;

public class ProdDao {

	public static Connection getConnection(){
		Connection con=null;
		try{
			Class.forName("org.h2.Driver");
			con=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa","");
		}catch(Exception e){System.out.println(e);}
		return con;
	}
	public static int save(Product e){
		int status=0;
		try{
			Connection con=ProdDao.getConnection();
			PreparedStatement ps=con.prepareStatement("insert into product(id, name) values (?,?)");
			ps.setString(1, e.getId());
			ps.setString(2,e.getName());

			status=ps.executeUpdate();
			
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return status;
	}
	public static int update(Product e){
		int status=0;
		try{
			Connection con=ProdDao.getConnection();
			PreparedStatement ps=con.prepareStatement("update product set name=?, where id=?");
			ps.setString(1,e.getName());
			ps.setString(2,e.getId());
			
			status=ps.executeUpdate();
			
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return status;
	}
	public static int delete(int id){
		int status=0;
		try{
			Connection con=ProdDao.getConnection();
			PreparedStatement ps=con.prepareStatement("delete from product where id=?");
			ps.setInt(1,id);
			status=ps.executeUpdate();
			
			con.close();
		}catch(Exception e){e.printStackTrace();}
		
		return status;
	}
	public static Product getProductById(String string){
		Product e=new Product();
		
		try{
			Connection con=ProdDao.getConnection();
			PreparedStatement ps=con.prepareStatement("select * from product where id=?");
			ps.setString(1,string);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				e.setId(rs.getString(1));
				e.setName(rs.getString(2));

			}
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return e;
	}
	public static List<Product> getAllProducts(){
		List<Product> list=new ArrayList<Product>();
		
		try{
			Connection con=ProdDao.getConnection();
			PreparedStatement ps=con.prepareStatement("select * from product");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Product e=new Product();
				e.setId(rs.getString(1));
				e.setName(rs.getString(2));

				list.add(e);
			}
			con.close();
		}catch(Exception e){e.printStackTrace();}
		
		return list;
	}
}