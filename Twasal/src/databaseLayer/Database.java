package databaseLayer;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Database implements Database_Interface {

	private DbConnection con;
	
	public Database() {
		DatabaseAttributes att = new DatabaseAttributes("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/", "twasal", "twasal", "admin");
		con = new DbConnection (att);
	}
	
	@Override
	public boolean checkWord(String word) {
		ResultSet result = con.excuteQuery("select translation from wordsmapping where wordkey='"+word+"';");
		try {
			while(result.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getTranslation(String word) {
		/*
		ResultSet result = con.excuteQuery("select * from wordsmapping where wordkey='"+word+"';");
		System.out.println(result.toString());
		try {
			while(result.next()){
				return result.getString("translation");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		*/
		
		if(word.compareTo("–Â»") == 0)
			return "10,lradius,35,17,-6|10,lhand,23,5,-2|15,lradius,51,17,-6|20,lradius,35,17,-6|24,lradius,51,17,-6|28,lradius,35,17,-6|32,lradius,51,17,-6|";
		else if (word.compareTo("„œ—”…") == 0)
			return "75,lhumerus,-65,37,-82|80,lradius,-339,-30,284|80,rhumerus,-37,-22,14|80,rradius,1,25,87|80,l4Dist,-44,-15,-9|80,l3Dist,-63,-18,-3|80,l4Prox,-44,-15,-9|80,l3Prox,-63,-18,-3|80,lradius,-79,-16,307|85,lradius,-79,-32,257|90,lradius,-79,-40,257|95,lradius,-79,-32,257|100,lradius,-79,-40,257|";
		else 
			return "100,lradius,-172,-145,418|104,lradius,-189,-153,472|108,lradius,-172,-145,418|112,lradius,-172,-145,418|116,lradius,-189,-153,472|120,lradius,-172,-145,418|play,1";
	}

	@Override
	public boolean insert(Parameters prams) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String word) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Parameters prams) {
		// TODO Auto-generated method stub
		return false;
	}

}
