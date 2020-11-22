package dao.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.CommBean;

import static db.JdbcUtil.*;

public class CommDAO {
	// --------------싱글톤 패턴 활용---------------
	private CommDAO() {}
	
	private static CommDAO instance = new CommDAO();

	public static CommDAO getInstance() {
		return instance;
	}
	// --------------싱글톤 패턴 활용---------------
	Connection con; // Connection 객체를 전달받아 저장할 멤버변수

	// --------------setConnection()---------------
	// 외부(Service 클래스)로부터 Connection 객체를 전달받아
	// 멤버변수에 저장하는 setConnection() 메서드 정의
	public void setConnection(Connection con) {
		this.con = con;
	}
	// --------------setConnection()---------------
	// --------------insertArticle()---------------
	// 글 등록 작업
	public int insertArticle(CommBean commBean) {
		System.out.println("CommDAO - insertArticle()~");
		int insertCount = 0;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int num = 1; // 글 번호를 저장할 변수
		
		try {
			String sql = "SELECT max(num) FROM community";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			// 조회 값 있으면 + 1, 없으면 작성된 글이 없으므로 num = 1 그대로 사용
			if(rs.next()) {
				num = rs.getInt(1) + 1;
			}
			
			sql = "INSERT INTO community VALUES(?,?,?,?,?,?,now(),?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.setString(2, commBean.getUsername());
			ps.setString(3, commBean.getPass());
			ps.setString(4, commBean.getSubject());
			ps.setString(5, commBean.getContent());
			ps.setInt(6, commBean.getReadCount());
			ps.setString(7, commBean.getImg());
			insertCount = ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("insertArticle() 오류 "+e.getMessage() );
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		return insertCount;
	}
	// --------------insertArticle()---------------
	
	// --------------selectListCount()---------------
	// 전체 게시물 수 조회
	public int selectListCount() {
		System.out.println("CommDAO - selectListCount()~");
		
		int listCount = 0;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		try {
			String sql = "SELECT count(num) FROM community";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("selectListCount() 오류 "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		
		return listCount;
	}
	// --------------selectListCount()---------------
	// --------------selectArticleList()---------------
	// 게시물 목록 조회
	public ArrayList<CommBean> selectArticleList(int page, int limit){
		ArrayList<CommBean> articleList = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int startRow = (page - 1) * limit; // 조회를 시작할 레코드(행) 번호 계산
		
		try {
			String sql = "SELECT * FROM community ORDER BY num desc limit ?,?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, startRow);
			ps.setInt(2, limit);
			rs = ps.executeQuery();
			
			articleList = new ArrayList<CommBean>();
			
			while(rs.next()) {
				// 1개 게시물 정보를 저장할 CommBean 객체 생성 및 데이터 저장
				CommBean article = new CommBean();
				
				// 비밀번호는 제외
				article.setNum(rs.getInt(1));
				article.setUsername(rs.getString(2));
				article.setSubject(rs.getString(4));
				article.setContent(rs.getString(5));
				article.setReadCount(rs.getInt(6));
				article.setDate(rs.getTimestamp(7));
				article.setImg(rs.getString(8));
				
				// 1개 게시물을 전체 게시물 저장 객체에 추가
				articleList.add(article);
				
			}
			
		} catch (SQLException e) {
			System.out.println("selectArticleList() 오류 "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		return articleList;
	}
	// --------------selectArticleList()---------------
	// -------------------------- selectArticle() --------------------------------
		// 게시물 상세 내용 조회
		public CommBean selectArticle(int num) {
			// 글번호(num) 에 해당하는 레코드를 SELECT 
			// 조회 결과가 있을 경우 CommBean 객체에 저장한 뒤 리턴
			CommBean article = new CommBean();
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				
				String sql = "SELECT * FROM community WHERE num = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, num);
				rs = ps.executeQuery();
				
				// 게시물이 존재할 경우 CommBean 객체를 생성하여 게시물 내용 저장
				if(rs.next()) {
					article.setNum(rs.getInt("num"));
					article.setUsername(rs.getString("username"));
					article.setPass(rs.getString("pass"));
					article.setSubject(rs.getString("subject"));
					article.setContent(rs.getString("content"));
					article.setDate(rs.getTimestamp("date"));
					article.setImg(rs.getString("img"));
					article.setReadCount(rs.getInt("readcount"));
					
				}
				
			} catch (Exception e) {
				System.out.println("CommDAO - selectArticle() 오류 : "+e.getMessage());
				e.printStackTrace();
			} finally {
				close(ps);
				close(rs);
			}
			
			return article;
		}
		// -------------------------- selectArticle() --------------------------------
		// -------------------------- updateReadcount() --------------------------------
		// 게시물 조회 수 증가
		public int updateReadcount(int num) {
			// 글번호(num)에 해당하는 게시물의 조회수(readcount)를 1 증가
			int updateCount = 0;
			
			PreparedStatement ps = null;
			
			try {
			
				String sql = "UPDATE community SET readcount = readcount + 1 WHERE num = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, num);
				updateCount = ps.executeUpdate();
				
				// 임시 확인용 상세 내용 출력
//				System.out.println("조회수 증가 결과 : " + updateCount);
			} catch (SQLException e) {
				System.out.println("CommDAO - updateReadcount() 오류 : "+e.getMessage());
				e.printStackTrace();
			} finally {
				close(ps);
			}
			
			return updateCount;
		}
		// -------------------------- updateReadcount() --------------------------------
		// -------------------------- isArticleCommWriter() --------------------------------
		public boolean isArticleCommWriter(int num, String pass) {
			boolean isArticleWriter = false;
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				String sql = "SELECT pass FROM community WHERE num = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, num);
				rs = ps.executeQuery();
				
				if(rs.next()) {
//					if(rs.getString(1).equals(pass)) {
					if(rs.getString(1).equals("123")) {
						isArticleWriter = true;
					}
				}
				System.out.println("CommDAO - isArticleWrite : "+ isArticleWriter);
			} catch (SQLException e) {
				System.out.println("CommDAO - isArticleCommWriter : "+e.getMessage());
				e.printStackTrace();
			} finally {
				close(ps);
				close(rs);
			}
			
			return isArticleWriter;
		}
		// -------------------------- isArticleCommWriter() --------------------------------
		// -------------------------- updateArticle() --------------------------------
		public int updateArticle(CommBean article) {
			// CommBean 객체에 저장된 수정 내용(작성자, 제목, 내용
			int updateCount = 0;
			
			PreparedStatement ps = null;
			
			try {
				String sql = "UPDATE community SET subject=?, content=? WHERE num = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, article.getSubject());
				ps.setString(2, article.getContent());
				ps.setInt(3, article.getNum());
				updateCount = ps.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("CommDAO - updateArticle : "+e.getMessage());
				e.printStackTrace();
			} finally {
				close(ps);
			}
			return updateCount;
		}
		// -------------------------- updateArticle() --------------------------------
		// -------------------------- isDeleteArticle() --------------------------------
		// 글 삭제
		public int isDeleteArticle(int num) {
			int deleteCount = 0;
			PreparedStatement ps = null;
			
			try {
				String sql = "DELETE FROM community WHERE num = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, num);
				deleteCount = ps.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("CommDAO - isDeleteArticle : "+e.getMessage());
				e.printStackTrace();
			}
			
			return deleteCount;
		}
		// -------------------------- isDeleteArticle() --------------------------------
	
	
	
	
	
}